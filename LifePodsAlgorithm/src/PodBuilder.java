import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
import java.util.Map.Entry;


public class PodBuilder{
  private ArrayList<User> users;
  private static int POD_SIZE = 5;
  int maxPods; 
  ArrayList<LifePod> workPods = new ArrayList<>();
  ArrayList<LifePod> gradPods = new ArrayList<>();
  ArrayList<LifePod> podList = new ArrayList<>();
  boolean haveExtraPod = true;
  LifePod extraPod;
  String extraType;
  int extraUsers = 0;
  
  HashMap<Integer, User> usersMapped = new HashMap<>();
  ArrayList<User> mappedUsers = new ArrayList<>();
  
  private HashMap<Integer, LinkedList<User>> gradPool = new HashMap<>();
  private HashMap<Integer, LinkedList<User>> workPool = new HashMap<>();
 
  
  public PodBuilder(ArrayList<User> users) {
    this.users = users;
    Divide div = new Divide(users);
    gradPool = div.gradPool;
    workPool = div.workPool;
    
    maxPods = 0;
    int totalUsers = users.size();
    extraUsers = totalUsers % 5;
    if (extraUsers > 3 || extraUsers == 0) haveExtraPod = false; 
    
    while (totalUsers > 0) {
      totalUsers -= POD_SIZE;
      maxPods++;
    }
    
  }
  
  
  public ArrayList<LifePod> buildPods() {
    int gradSize = 0;
    for (Entry<Integer, LinkedList<User>> e : gradPool.entrySet()) gradSize += e.getValue().size();
    
    int workSize = 0;
    for (Entry<Integer, LinkedList<User>> e : workPool.entrySet()) workSize += e.getValue().size();
    
    int totalSize = gradSize + workSize;
    int num = ThreadLocalRandom.current().nextInt(0, 2);
    
    
    while (totalSize > 0) {
      LifePod pod = new LifePod();
      
      switch(num){
        case 0: 
          if (gradSize > 0) {
            pod.setType("grad");
            gradPods.add(pod);
            addMembers(pod, gradPool);
            
            gradSize -= POD_SIZE;
            totalSize -= POD_SIZE;
            if (haveExtraPod && totalSize <= 0) extraPod = gradPods.remove(gradPods.size() -1);
            num = 1;
          }
          else num = 1;
          break;
          
        case 1:
          if (workSize > 0) {
            pod.setType("work");
            workPods.add(pod);
            addMembers(pod, workPool);
            
            workSize -= POD_SIZE;
            totalSize -= POD_SIZE;
            if (haveExtraPod && totalSize <= 0) extraPod = workPods.remove(workPods.size() -1);
            num = 0;
          }
          else num = 0;
          break;
      }
      
//      podList.add(pod);
    }
    
    podList.addAll(gradPods);
    podList.addAll(workPods);
    
    if (haveExtraPod) {
      redistribute(extraPod);
      podList = new ArrayList<LifePod>();
      podList.addAll(gradPods);
      podList.addAll(workPods);
    }
    
    return podList;
  }
  
  
  private void redistribute(LifePod extraPod) {
    int gradCounter = 0;
    int workCounter = 0;
    
    while (extraUsers > 0) {
      User u = extraPod.removeMember(0);
      String userPlans = u.getPlans();
      boolean added = false;
      boolean switched = false;
      int choice = (userPlans.equals("undecided")) ? choice = ThreadLocalRandom.current().nextInt(0, 2) : 999;
      
      while (added == false) {
        
        if (userPlans.equals("grad school") || choice == 0) {
          if (gradCounter < gradPods.size()) {
            gradPods.get(gradCounter).addUser(u);
            added = true;
            gradCounter++;
          }
          else if (userPlans.equals("undecided") && switched == false){
            choice = 1;
            switched = true;
          }
          else if (gradPods.size() > 0) gradCounter = 0;
          else userPlans = "work";
        }
        
        else if (userPlans.equals("work") || userPlans.equals("looking for work") || choice == 1) {
          if (workCounter < workPods.size()) {
            workPods.get(workCounter).addUser(u);
            added = true;
            workCounter++; 
          }
          else if (userPlans.equals("undecided") && switched == false) {
            choice = 0;
            switched = true;
          }
          else if (workPods.size() > 0) workCounter = 0;
          else userPlans = "grad school";
        }
        
      }
      
      extraUsers--; 
    }
  }

  
  private void addMembers(LifePod pod, HashMap<Integer, LinkedList<User>> pool) {
    int num = 0;
    boolean ignoreBan = false;
    boolean checkOtherPool = false;
    boolean nobodyLeft = false;
    
    while (num < POD_SIZE && nobodyLeft == false) {
      boolean added = false;
      
      User u = null;
      int[] keys = new int[5];
      
      if (num == 0) keys = new int[] {1, 2, 3, 4, 0};  
      else keys = new int[] {0, 4, 3, 2, 1};          
      
      int i = 0;
      while (i < keys.length && added == false) {
        
        if (pool.get(keys[i]).isEmpty() == false) {
          if (ignoreBan) u = pool.get(keys[i]).pollFirst();
          else u = findUser(pod, pool.get(keys[i]));
        }
        
        if (u != null) {
          mappedUsers.add(u);
          if (pod.size == 0) pod.addLeader(u);
          else pod.addUser(u);
          
          added = true;
          ignoreBan = false;
          num++;
        }
        i++;
      }
      
      
      if (added == true) {
        User podMember = pod.getMember(num-1);
        User friend = findFriend(podMember, pod);
        
        if (friend != null) {
          mappedUsers.add(friend);
          pod.addUser(friend);
          removeFromPool(friend, pool);
          num++;
        }
      }
      else if (ignoreBan == false) {
        ignoreBan = true;
      }
      else if (checkOtherPool == false) {
        if (pool == workPool) pool = gradPool;
        else pool = workPool;
        
        ignoreBan = false;
        checkOtherPool = true;
      }
      else nobodyLeft = true;
    }
  
  }

  
  public ArrayList<LifePod> getWorkPods(){
    return workPods;
  }
  
  public ArrayList<LifePod> getGradPods(){
    return gradPods;
  }
  
  
  
  /**
   * Asks if the user in question can be added. 
   */
  private User findUser(LifePod pod, LinkedList<User> list) {
    
    UserIterator li = new UserIterator(list);
    User outsider = null;
    boolean banned = true;
    int index = 0;
    
    while (li.hasNext() && banned == true) {
      index = li.nextIndex();
      outsider = li.next();
      banned = checkBan(pod, outsider);
    }
    
    if (banned == false && outsider != null) {
      list.remove(index);
      return outsider;
    }
    
    return null;
  }
  

  /**
   * Adds one user to the pod if they are
   * on a newly added member's friend list
   */
  public User findFriend(User u, LifePod pod) {
    if (pod.size >= POD_SIZE) return null;
    
    int random = ThreadLocalRandom.current().nextInt(0, 2);
    int friendId = u.getFriends()[random];
    int totalFriends = u.getFriends().length;
    User friend = getUser(friendId);
    
    for (int i=0; i<totalFriends; i++) {
      
      if (friend == null || valid(friend) == false || checkBan(pod, friend) == true || isMutual(u, friend) == false) {
        friendId = u.getFriends()[random^1];
        friend = getUser(friendId);
        i++;
      }
      
      if (friend != null && valid(friend) == true && checkBan(pod, friend) == false && isMutual(u, friend) == true) {
        String type = friend.getType();
        if (!type.equals(pod.getType())) friend = null;
        else return friend;
      }
    }
    
    return null;
  }
  
  
  /**
   * Tests if two users are mutual friends
   */
  public boolean isMutual(User u, User friend) {
    boolean isMutual = false;
    
    if (friend != null) {
      int[] fof = friend.getFriends();
      
      for (int j=0; j<fof.length; j++) {
        if (fof[j] == u.getId()) isMutual = true;
      }
    }
    
    return isMutual;
  }
  
  
  /**
   * Removes any user from their respective pool
   */
  public void removeFromPool(User user, HashMap<Integer, LinkedList<User>> pool) {
    int id = user.getId();
    int pk = user.getPoolKey();
    int index = 0;
    
    for (int i=0; i<pool.get(pk).size(); i++) {
      User poolUser = pool.get(pk).get(i);
      index = i;
      
      if (poolUser.getId() == id) {
        pool.get(pk).remove(index);
        break;
      }
    }
  }
  
  
  /**
   * Checks to see if the user is banned from entering the pod
   */
  public boolean checkBan(LifePod pod, User u) {
    boolean banned = false;
    
    for (User member : pod.getMembers()) {
      
      for (int userBan : u.getEnemies()) {
        if (userBan == member.getId()) {
          banned = true;
          break;
        }
      }
      if (banned == true) break;
      
      
      for (int podBan : member.getEnemies()) {
        if (podBan == u.getId()) {
          banned = true;
          break;
        }
      }
      if (banned == true) break;
    }
    
    return banned;
  }
  
  
  /**
   * Finds the user with the matching id.
   */
  public User getUser(int id) {
    User user = null;
    
    for (User u : users) {
      if (u.getId() == id) user = u;
    }
    
    return user;
  }
  
  
  /**
   * Tests to see if the user has already been added.
   */
  public boolean valid(User testUser) {
    boolean valid = true;
    if (mappedUsers.contains(testUser) || testUser == null) valid = false;
        
    return valid;
  }
  
  
  
  
  


  
  
  
  
  




















}