import java.util.*;

public class Main {

  public static void main(String[] args) {
    // file path
    String file = "src/source/users.csv";
    ArrayList<User> users;
    
    //read file and assign Users to the users ArrayList
    users = FileReader.readFile(file);    
    
    //prints all the users
//    for (User u : users) {
//      System.out.println(u.toString());
//    }
//    System.out.println("***********************************");
    
    GroupBuilder gb = new GroupBuilder(users);
    ArrayList<LifePod> pods = gb.buildPods();
    for (LifePod p : pods) {
      System.out.println(p.toString());
    }
    
    ExcelWriter ew = new ExcelWriter(pods);
    ew.write();
  }

}
