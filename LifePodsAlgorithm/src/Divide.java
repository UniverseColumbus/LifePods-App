import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.*;


public class Divide {
  
  public HashMap<Integer, LinkedList<User>> gradPool = new HashMap<>();
  public HashMap<Integer, LinkedList<User>> workPool = new HashMap<>();
  public LinkedList<User> undList = new LinkedList<>();
  private static int POD_SIZE = 5;
  
  public Divide(ArrayList<User> users) {
    
    gradPool.put(0, new LinkedList<User>());
    gradPool.put(1, new LinkedList<User>());
    gradPool.put(2, new LinkedList<User>());
    gradPool.put(3, new LinkedList<User>());
    gradPool.put(4, new LinkedList<User>());
    
    workPool.put(0, new LinkedList<User>());
    workPool.put(1, new LinkedList<User>());
    workPool.put(2, new LinkedList<User>());
    workPool.put(3, new LinkedList<User>());
    workPool.put(4, new LinkedList<User>());
    
    segregate(users);
  }

  /**
   * Segregates users into separate lists based on their plans and leaderPoolhip.
   */
  public void segregate(ArrayList<User> users) {
    
    int gradSize = 0, workSize = 0;
    
    for (User u : users) { 
      String plans = u.getPlans();
      String willLead = u.getWillLead();
      int key = 0;
      
      if (plans.equals("undecided")) undList.add(u);
      
      else if (plans.equals("grad school")) {
        if (willLead.equals("yes")) key = 1;
        else if (willLead.equals("maybe")) key = 3;
        
        gradPool.get(key).add(u);
        u.setPoolKey(key);
        gradSize++;
      }
      
      else {
        if (willLead.equals("yes")) key = 1;
        else if (willLead.equals("maybe")) key = 3;
        
        workPool.get(key).add(u);
        u.setPoolKey(key);
        workSize++;
      }
      
    }
    
    
    Collections.sort(undList, new UserComp());
    int gradExtra = POD_SIZE - (gradSize % POD_SIZE);
    int workExtra = POD_SIZE - (workSize % POD_SIZE);
    int reqGL = 0;
    int reqWL = 0;
    while (gradSize > 0) {
      gradSize -= POD_SIZE;
      reqGL++;
    }
    while (workSize > 0) {
      workSize -= POD_SIZE;
      reqWL++;
    }
    int extraGL = reqGL - (gradPool.get(1).size() + gradPool.get(3).size());
    int extraWL = reqWL - (workPool.get(1).size() + workPool.get(3).size());
    
    int key = 0;
    User u = null;
    
    while (undList.isEmpty() == false) {
      
      while (gradExtra > 0 && undList.isEmpty() == false) {
        
        if (extraGL > 0) u = undList.pollLast();
        else u = undList.pollFirst();
          
        if(u.getWillLead().equals("yes")) key = 2;
        else if(u.getWillLead().equals("maybe")) key = 4;
        else key = 0;
        
        if (key == 2 || key == 4) extraGL--;
        
        gradPool.get(key).add(u);
        u.setPoolKey(key);
        u.setType("grad");
        gradExtra--;
      }
      
      
      while (workExtra > 0 && undList.isEmpty() == false) {
        
        if (extraWL > 0) u = undList.pollLast();
        else u = undList.pollFirst();
          
        if(u.getWillLead().equals("yes")) key = 2;
        else if(u.getWillLead().equals("maybe")) key = 4;
        else key = 0;
        
        if (key == 2 || key == 4) extraWL--;
        
        workPool.get(key).add(u);
        u.setPoolKey(key);
        u.setType("work");
        workExtra--;
      }
      
      gradExtra = POD_SIZE;
      workExtra = POD_SIZE;
    }
    
    
    Collections.shuffle(gradPool.get(0));
    Collections.shuffle(gradPool.get(1));
    Collections.shuffle(gradPool.get(2));
    Collections.shuffle(gradPool.get(3));
    Collections.shuffle(gradPool.get(4));
    
    Collections.shuffle(workPool.get(0));
    Collections.shuffle(workPool.get(1));
    Collections.shuffle(workPool.get(2));
    Collections.shuffle(workPool.get(3));
    Collections.shuffle(workPool.get(4));
  }


}


class UserComp implements Comparator<User>{
  @Override
  public int compare(User u1, User u2) {
    String one = u1.getWillLead();
    String two = u2.getWillLead();
    int val1 = 0, val2 = 0;
    val1 = one.equals("no") ? 1 : (one.equals("maybe") ? 2 : 3);
    val2 = two.equals("no") ? 1 : (two.equals("maybe") ? 2 : 3);
    
    if (val1 < val2) {
      return -1;
    }
    else return 1;
  }
}



















