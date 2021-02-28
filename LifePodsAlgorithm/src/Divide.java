import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.*;

public class Divide {
  private ArrayList<User> users;
  private static int POD_SIZE = 5;
  int maxPods;
  
  public HashMap<Integer, LinkedList<User>> gradPool = new HashMap<>();
  public HashMap<Integer, LinkedList<User>> workPool = new HashMap<>();
  public LinkedList<User> undList = new LinkedList<>();
  
  
  public Divide(ArrayList<User> users) {
    this.users = users;
    maxPods = users.size() / POD_SIZE;
    
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
    
    int reqGL = 0, reqWL = 0;
    int gradSize = 0, workSize = 0;
    
    for (User u : users) { 
      String plans = u.getPlans();
      String willLead = u.getWillLead();
      
      if (plans.equals("undecided")) undList.add(u);
      
      
      else if (plans.equals("grad school")) {
        if (gradSize%5 == 0) reqGL++; 
        gradSize++;
        
        if (willLead.equals("yes")) {
          gradPool.get(1).add(u);
          reqGL--;
        }
        else if (willLead.equals("maybe")) {
          gradPool.get(2).add(u);
          reqGL--;
        }
        else {
          gradPool.get(0).add(u);
        }
      }
      
      else {
        if (workSize%5 == 0) reqWL++; 
        workSize++;
        
        if (willLead.equals("yes")) {
          workPool.get(1).add(u);
          reqWL--;
        }
        else if (willLead.equals("maybe")) {
          workPool.get(2).add(u);
          reqWL--;
        }
        else {
          workPool.get(0).add(u);
        }
      }
    }
    
    Collections.sort(undList, new UserComp());
    int gradExtra = 5 - (gradSize % 5);
    int workExtra = 5 - (workSize % 5);
    
    while (undList.isEmpty() == false) {
      
      while (gradExtra > 0) {
        boolean added = false;
        
        if (reqGL > 0) {
          if(undList.peekLast().getWillLead().equals("yes")) {
            gradPool.get(3).add(undList.pollLast());
            reqGL--;
            added = true;
          }
          else if(undList.peekLast().getWillLead().equals("maybe")) {
            gradPool.get(4).add(undList.pollLast());
            reqGL--;
            added = true;
          }
        }
        else if (added == false && undList.isEmpty() == false) gradPool.get(0).add(undList.pollFirst());
        
        gradExtra--;
      }
      
      
      while (workExtra > 0) {
        boolean added = false;
        
        if (reqWL > 0) {
          if(undList.peekLast().getWillLead().equals("yes")) {
            workPool.get(3).add(undList.pollLast());
            reqWL--;
            added = true;
          }
          else if(undList.peekLast().getWillLead().equals("maybe")) {
            workPool.get(4).add(undList.pollLast());
            reqWL--;
            added = true;
          }
        }
        else if (added == false && undList.isEmpty() == false) workPool.get(0).add(undList.pollFirst());
        
        workExtra--;
      }
      
      gradExtra = 5;
      workExtra = 5;
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



















