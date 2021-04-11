import java.util.*;


public class UserIterator {
 
  private int index;
  private List<User> users;
  
  
  public UserIterator(List<User> users) {
    index = 0;
    this.users = users;
  }
  
  public UserIterator(String choice) {
    if (choice.equals("linkedlist")) users = new LinkedList<User>();
    else if (choice.equals("arraylist")) users = new ArrayList<User>();
    else users = new LinkedList<User>();
  }
  
  public UserIterator() {
    users = new LinkedList<User>();
  }
  
  
  
  public boolean hasNext() {
    boolean hasNext = false;
    int totalUsers = users.size();
    
    if (index < totalUsers) hasNext = true;
    return hasNext;
  }

  public User next() {
    User nextUser = null;
    nextUser = users.get(index);
    index++;
    
    return nextUser;
  }
  
  public int nextIndex() {
    return index;
  }

  public void remove() {
    users.remove(index);
    if (index > 0) index--;
  }

  

















}



