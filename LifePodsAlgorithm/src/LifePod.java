import java.util.ArrayList;


public class LifePod {  
  private ArrayList<User> members;
  private User leader;
  private double score;
  private String podType = "undecided";
  public int size;
  
  //constructor
  public LifePod() {
    members = new ArrayList<>();
    size = 0;
  }
  
  //pod
  public void addUser(User user) {
    members.add(user);
    user.setHasGroup(true);
    size++;
  }
  
  public User getMember(int index) {
    User u = null;
    if (index < size) u = members.get(index);
    
    return u;
  }

  public ArrayList<User> getMembers() {
    return members;
  }

  public void setMembers(ArrayList<User> members) {
    this.members = members;
  }
  
  public User removeMember(int index) {
    return members.remove(index);
  }
  
  //leader
  public void addLeader(User user) {
    members.add(user);
    this.leader = user;
    user.makeLeader();
    size++;
  }

  public User getLeader() {
    return leader;
  }


  //score
  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }
  
  //type
  public void setType(String podType) {
    this.podType = podType;
  }
  
  public String getType() {
    return podType;
  }
  
  @Override
  public String toString() {
    StringBuilder strPod = new StringBuilder();
    strPod.append(podType + " LifePod:\n");
    
    for (User u : members) {
      strPod.append(u.toString() + "\n");
    }
    
    return strPod.toString();
  }
}
