import java.util.Arrays;

public class User { 
  private int id;
  private String plans;
  private String willLead;
  private int friends [];
  private int enemies [];
  private boolean hasGroup;
  private boolean isLeader = false;

  public User(int id, String plans, String willLead, int[] friends, int[] enemies) {
    super();
    this.id = id;
    this.plans = plans;
    this.willLead = willLead;
    this.friends = friends;
    this.enemies = enemies;
    this.hasGroup = false;
  }
  
  //id
  public int getId() { 
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
  
  //post-grad plans
  public String getPlans() {
    return plans;
  }

  public void setPlans(String plans) {
    this.plans = plans;
  }

  //pod leader
  public String getWillLead() {
    return willLead;
  }

  public void setWillLead(String willLead) {
    this.willLead = willLead;
  }
  
  public boolean isLeader() {
    return isLeader;
  }
  
  public void makeLeader() {
    this.isLeader = true;
  }
  
  //preference list
  public int[] getFriends() {
    return friends;
  }

  public void setFriends(int[] friends) {
    this.friends = friends;
  }

  public int[] getEnemies() {
    return enemies;
  }

  public void setEnemies(int[] enemies) {
    this.enemies = enemies;
  }

  //has group
  public boolean hasGroup() {
    return hasGroup;
  }

  public void setHasGroup(boolean hasGroup) {
    this.hasGroup = hasGroup;
  }

  @Override
  public String toString() {
    String pCopy = "";
    if(plans.equals("work")) pCopy = plans + "            ";
    else if(plans.equals("undecided")) pCopy = plans + "       ";
    else if(plans.equals("grad school")) pCopy = plans + "     ";
    else pCopy = plans;
    
    String wlCopy = "";
    if(willLead.equals("yes")) wlCopy = willLead + "  ";
    else if (willLead.equals("no")) wlCopy = willLead + "   ";
    else wlCopy = willLead;
    
    String idCopy = "";
    if(id%10 == id) idCopy = "0" + String.valueOf(id);
    else idCopy = String.valueOf(id);
    
    return "User {  id= [" + idCopy + "],  plans= [" + pCopy + "],  willLead= [" + wlCopy
        + "],  friends= " + Arrays.toString(friends) + ",  enemies= "
        + Arrays.toString(enemies) + "  }";
  }
}

