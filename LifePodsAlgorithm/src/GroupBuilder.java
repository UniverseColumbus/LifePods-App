import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class GroupBuilder {
  
  private static int POD_SIZE = 5;
  private ArrayList<User> users;
  
  public GroupBuilder(ArrayList<User> users) {
    this.users = users;
  }
  
  public ArrayList<LifePod> buildPods() {
    // We can write our logic here and return a list of LifePods
    ArrayList<LifePod> pods = new ArrayList<>();
    HashMap<Integer, User> usersMapped = new HashMap<>();
    int maxPods = users.size() / POD_SIZE;

    // Checks for Users who wants to be leader
    for (User u : users) {
      if (u.getWillingPodLeader().equals("yes") && pods.size() < maxPods) {
        LifePod newPod = new LifePod();
        newPod.addUser(u);
        newPod.addLeader(u);
        usersMapped.put(u.getId(), u);
        
        if (u.getPostGradPlans().equals("work") || u.getPostGradPlans().equals("looking for work")) newPod.setType("work");
        else if (u.getPostGradPlans().equals("grad school")) newPod.setType("grad school");
        
        pods.add(newPod);
      }
    }
    
    // Add users with no preference and anti-preference randomly into groups
    for (User u : users) {
      if (usersMapped.containsKey(u.getId())) continue;
      
      boolean havePref = false;
      for (int id : u.getPreferenceList()) {
        if (usersMapped.containsKey(id)) havePref = true;
      }
      
      boolean haveAntiPref = false;
      for (int id : u.getAntiPreferenceList()) {
        if (usersMapped.containsKey(id)) haveAntiPref = true;
      }
      
      if (havePref == false && haveAntiPref == false) {
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

    return pods;
  }
  
  /**
   * checks if user is welcome in a certain LifePod
   * @return false if the user is not welcome
   */
  private boolean welcome(LifePod pod, User user) {
    boolean isWelcome = true;
    for (User u : pod.getMembers()) {
      for (int id : u.getAntiPreferenceList()) {
        if (id == user.getId()) isWelcome = false;
      }
    }   
    return isWelcome;
  }
  
  






}
