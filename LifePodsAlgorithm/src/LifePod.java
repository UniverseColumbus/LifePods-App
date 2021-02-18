import java.util.ArrayList;

public class LifePod {  
  private ArrayList<User> members;
  private int leader;
  private double score;
  private String podType = "undecided";
  
  //constructor
  public LifePod() {
    members = new ArrayList<>();
  }
  
  //pod
  public void addUser(User user) {
    members.add(user);
    user.setHasGroup(true);
  }

  public ArrayList<User> getMembers() {
    return members;
  }

  public void setMembers(ArrayList<User> members) {
    this.members = members;
  }
  
  //leader
  public void addLeader(User user) {
    this.leader = user.getId();
    user.makeLeader();
  }

  public int getLeader() {
    return leader;
  }

  public void setLeader(int leader) {
    this.leader = leader;
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
