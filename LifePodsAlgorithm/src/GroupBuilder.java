import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
import java.util.Map.Entry;


public class GroupBuilder{
  private ArrayList<User> users;
  private static int POD_SIZE = 5;
  int maxPods;
  ArrayList<LifePod> pods = new ArrayList<>();
  HashMap<Integer, User> usersMapped = new HashMap<>();
  
  private HashMap<Integer, LinkedList<User>> gradPool = new HashMap<>();
  private HashMap<Integer, LinkedList<User>> workPool = new HashMap<>();
  private boolean added = false;
  
  
  public GroupBuilder(ArrayList<User> users) {
    this.users = users;
    maxPods = users.size() / POD_SIZE;
    
    Divide div = new Divide(users);
    gradPool = div.gradPool;
    workPool = div.workPool;
  }
  
  
  public ArrayList<LifePod> buildPods() {
    int gradSize = 0;
    for (Entry<Integer, LinkedList<User>> e : gradPool.entrySet()) {
      gradSize += e.getValue().size();
    }
    
    int workSize = 0;
    for (Entry<Integer, LinkedList<User>> e : workPool.entrySet()) {
      workSize += e.getValue().size();
    }
    
    for (int i=0; i < gradSize/5; i++) {
      LifePod pod = new LifePod();
      pod.setType("grad");
      addMembers(pod, gradPool);
      pods.add(pod);
    }
    
    for (int i=0; i < workSize/5; i++) {
      LifePod pod = new LifePod();
      pod.setType("work");
      addMembers(pod, workPool);
      pods.add(pod);
    }
    
    
    return pods;
  }
  

  private void addMembers(LifePod pod, HashMap<Integer, LinkedList<User>> pool) {
    int num = 1;
    boolean ignoreBan = false;
    
    while (num <= 5) {
      added = false;
      
      if (num <= 4) {
        if(pool.get(0).isEmpty() == false) add(pod, pool.get(0), ignoreBan);
        else if(pool.get(4).isEmpty() == false) add(pod, pool.get(4), ignoreBan);
        else if(pool.get(3).isEmpty() == false) add(pod, pool.get(3), ignoreBan);
        else if(pool.get(2).isEmpty() == false) add(pod, pool.get(2), ignoreBan);
        else if(pool.get(1).isEmpty() == false) add(pod, pool.get(1), ignoreBan);
      }
      
      else {
        if(pool.get(1).isEmpty() == false) add(pod, pool.get(1), ignoreBan);
        else if(pool.get(2).isEmpty() == false) add(pod, pool.get(2), ignoreBan);
        else if(pool.get(3).isEmpty() == false) add(pod, pool.get(3), ignoreBan);
        else if(pool.get(4).isEmpty() == false) add(pod, pool.get(4), ignoreBan);
        else if(pool.get(0).isEmpty() == false) add(pod, pool.get(0), ignoreBan);
      }
      
      if (added == true) {
        num++;
        ignoreBan = false;
      }
      else ignoreBan = true;
    }
  
  }
  
  
  /**
   * Asks if the user in question can be added. 
   */
  private void add(LifePod pod, LinkedList<User> list, boolean ignoreBan) {
    added = false;
    
    if (ignoreBan == true) {
      if (pod.size < 4) pod.addUser(list.pollFirst());
      else pod.addLeader(list.pollFirst());
      added = true;
    }
    
    UserIterator li = new UserIterator(list);
    User outsider = null;
    boolean banned = true;
    int index = 0;
    
    while (li.hasNext() && banned == true) {
      banned = false;
      index = li.nextIndex();
      outsider = li.next();
      
      for (User member : pod.getMembers()) {
        
        for (int userBan : outsider.getFriends()) {
          if (userBan == member.getId()) {
            banned = true;
            break;
          }
        }
        if (banned == true) break;
        
        
        for (int podBan : member.getFriends()) {
          if (podBan == outsider.getId()) {
            banned = true;
            break;
          }
        }
        if (banned == true) break;
      }
    }
    
    if (banned == false && outsider != null) {
      if (pod.size < 4) pod.addUser(outsider);
      else pod.addLeader(outsider);
      
      list.remove(index);
      added = true;
    }
    
  }
 

  /**
   * Adds one user to the pod 
   * if they are on a newly added member's friend list
   */
  public void addFriend() {
    
  }

  /**
   * Adds multiple users to the pod
   * based on post-grad plans
   */
  public void addMembers(String type) {
    
  }


  /**
   * Add users with no friend or enemy randomly into groups
   */
  public void checkPreferences(){
    for (User u : users) {
      if (usersMapped.containsKey(u.getId())) continue;
      
      boolean haveFriend = false;
      for (int id : u.getFriends()) {
        if (usersMapped.containsKey(id)) haveFriend = true;
      }
      
      boolean haveEnemy = false;
      for (int id : u.getEnemies()) {
        if (usersMapped.containsKey(id)) haveEnemy = true;
      }
      
      if (haveFriend == false && haveEnemy == false) {
        int randomPodIndex = ThreadLocalRandom.current().nextInt(0, pods.size());
        LifePod podToTry = pods.get(randomPodIndex);
        
        while (welcome(podToTry, u) == false) {
          randomPodIndex = ThreadLocalRandom.current().nextInt(0, pods.size());
          podToTry = pods.get(randomPodIndex);
          System.out.println("not allowed");
        }
        pods.get(randomPodIndex).addUser(u);
      }
    }
  }


  /**
   * checks if user is welcome in a certain LifePod
   * @return false if the user is not welcome
   */
  private boolean welcome(LifePod pod, User user) {
    boolean isWelcome = true;
    for (User u : pod.getMembers()) {
      for (int id : u.getEnemies()) {
        if (id == user.getId()) isWelcome = false;
      }
    }   
    return isWelcome;
  }














}



















