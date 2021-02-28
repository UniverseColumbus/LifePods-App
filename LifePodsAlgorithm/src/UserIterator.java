import java.util.*;

public class UserIterator {
 
  private int index;
  private LinkedList<User> users;
  
  public UserIterator(LinkedList<User> users) {
    index = 0;
    this.users = users;
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



