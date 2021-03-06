import java.util.*;
import java.io.*;
import java.util.Map.Entry;

public class Main{

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
    HashMap<Integer, LifePod> pods = gb.buildPods();
    ArrayList<LifePod> podList = new ArrayList<>();
    
    for (Entry<Integer, LifePod> p : pods.entrySet()) {
      System.out.println(p.getKey() + " " + p.getValue().toString());
      podList.add(p.getValue());
    }
    
    ExcelWriter ew = new ExcelWriter(podList);
    ew.write(); 
  }




















}




