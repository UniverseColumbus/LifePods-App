import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
import java.util.Map.Entry;


public class PodBuilder{
  private ArrayList<User> users;
  private static int POD_SIZE = 5;
  int maxPods;
  ArrayList<LifePod> pods = new ArrayList<>();
  HashMap<Integer, User> usersMapped = new HashMap<>();
  ArrayList<User> mappedUsers = new ArrayList<>();
  
  private HashMap<Integer, LinkedList<User>> gradPool = new HashMap<>();
  private HashMap<Integer, LinkedList<User>> workPool = new HashMap<>();
  public HashMap<Integer, LifePod> podPool = new HashMap<>();
  
  
  public PodBuilder(ArrayList<User> users) {
    this.users = users;
    maxPods = users.size() / POD_SIZE;
    
    Divide div = new Divide(users);
    gradPool = div.gradPool;
    workPool = div.workPool;
    
    for (int i=1; i<=maxPods; i++) {
      podPool.put(i, new LifePod());
    }
  }
  
  
  public HashMap<Integer, LifePod> buildPods() {
    int gradSize = 0;
    for (Entry<Integer, LinkedList<User>> e : gradPool.entrySet()) {
      gradSize += e.getValue().size();
    }
    
    int workSize = 0;
    for (Entry<Integer, LinkedList<User>> e : workPool.entrySet()) {
      workSize += e.getValue().size();
    }
    
    int key = 1;
    
    for (int i=0; i < gradSize/5; i++) {
      podPool.get(key).setType("grad");
      addMembers(podPool.get(key), gradPool);
      key++;
    }
    
    for (int i=0; i < workSize/5; i++) {
      podPool.get(key).setType("work");
      addMembers(podPool.get(key), workPool);
      key++;
    }
    
    return podPool;
  }
  

  private void addMembers(LifePod pod, HashMap<Integer, LinkedList<User>> pool) {
    int num = 0;
    boolean ignoreBan = false;
    
    while (num < 5) {
      boolean added = false;
      User u = null;
      int[] keys = new int[5];
      
      if (num == 0) keys = new int[] {1, 2, 3, 4, 0};  
      else keys = new int[] {0, 4, 3, 2, 1};          
      
      
      for (int i=0; i<keys.length; i++) {
        
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
          break;
        }
      }
      
      
      if (added == true) {
        User podMember = pod.getMember(num-1);
        User friend = findFriend(podMember, pod);
        
        if (friend != null) {
          mappedUsers.add(friend);
          pod.addUser(friend);
//          System.out.println("added id: " + friend.getId());
          removeFromPool(friend, pool);
//          System.out.println();
          num++;
        }
      }
      else ignoreBan = true;
    }
  }


  /**
   * Adds one user to the pod if they are
   * on a newly added member's friend list
   */
  public User findFriend(User u, LifePod pod) {
    if (pod.size >= 5) return null;
    
    int random = ThreadLocalRandom.current().nextInt(0, 2);
    int friendId = u.getFriends()[random];
//    System.out.println("friendId is: " + friendId);
    int totalFriends = u.getFriends().length;
    User friend = getUser(friendId);
    
    for (int i=0; i<totalFriends; i++) {
      
      if (friend == null || valid(friend) == false || checkBan(pod, friend) == true || isMutual(u, friend) == false) {
        friendId = u.getFriends()[random^1];
//        System.out.println("id switched to: " + friendId + "\n");
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
   * Asks if the user in question can be added. 
   */
  private User findUser(LifePod pod, LinkedList<User> list) {
    
    UserIterator li = new UserIterator(list);
    User outsider = null;
    boolean banned = true;
    int index = 0;
    
    while (li.hasNext() && banned == true) {
      banned = false;
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
//        System.out.println("removed id: " + pool.get(pk).get(index).getId());
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
  
  
  
  
  


  
  
  
  
  
  
  
  
  
  
  /**
   * Add users with no friend or enemy randomly into groups
   */
//  public void checkPreferences(){
//    for (User u : users) {
//      if (usersMapped.containsKey(u.getId())) continue;
//      
//      boolean haveFriend = false;
//      for (int id : u.getFriends()) {
//        if (usersMapped.containsKey(id)) haveFriend = true;
//      }
//      
//      boolean haveEnemy = false;
//      for (int id : u.getEnemies()) {
//        if (usersMapped.containsKey(id)) haveEnemy = true;
//      }
//      
//      if (haveFriend == false && haveEnemy == false) {
//        int randomPodIndex = ThreadLocalRandom.current().nextInt(0, pods.size());
//        LifePod podToTry = pods.get(randomPodIndex);
//        
//        while (welcome(podToTry, u) == false) {
//          randomPodIndex = ThreadLocalRandom.current().nextInt(0, pods.size());
//          podToTry = pods.get(randomPodIndex);
//          System.out.println("not allowed");
//        }
//        pods.get(randomPodIndex).addUser(u);
//      }
//    }
//  }


  /**
   * checks if user is welcome in a certain LifePod
   * @return false if the user is not welcome
   */
//  private boolean welcome(LifePod pod, User user) {
//    boolean isWelcome = true;
//    for (User u : pod.getMembers()) {
//      for (int id : u.getEnemies()) {
//        if (id == user.getId()) isWelcome = false;
//      }
//    }   
//    return isWelcome;
//  }














}




















