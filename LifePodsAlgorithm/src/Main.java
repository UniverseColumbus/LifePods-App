import java.util.*;
import java.util.Map.Entry;


public class Main{
  
  public static GUI gui;

  public static void main(String[] args) {
    Main m = new Main();
    gui = new GUI(m); 
    gui.launch();
    
//    runWithoutGUI();
  }
  
  public void finish() {
    
    ArrayList<User> users;
    ReadInput2 ri = new ReadInput2(gui.fileName);
    users = ri.readFile();    
    if (ri.message != null) {
      gui.message.setText(ri.message);
      gui.timer.start();
    }
    else {
      PodBuilder gb = new PodBuilder(users);
      HashMap<Integer, LifePod> pods = gb.buildPods();
      ArrayList<LifePod> podList = new ArrayList<>();
      
      for (Entry<Integer, LifePod> p : pods.entrySet()) {
        System.out.println(p.getKey() + " " + p.getValue().toString());
        podList.add(p.getValue());
      }
      
      String[] directories = gui.field2.getText().split("/");
      gui.podsFileName = directories[directories.length-1];
      
  
      ExcelWriter ew = new ExcelWriter(podList, gui.directoryName, gui.podsFileName);
      ew.write(); 
      gui.message.setText(ew.message);
      gui.timer.start();
    }
    
  }
  
  
  public static void runWithoutGUI() {
    
    ArrayList<User> users;
    String fileName = "src/source/users.csv";
    ReadInput2 ri = new ReadInput2(fileName);
    users = ri.readFile();  
    
//  prints all the users
//  for (User u : users) {
//    System.out.println(u.toString());
//  }
//  System.out.println("***********************************");
    
    PodBuilder gb = new PodBuilder(users);
    HashMap<Integer, LifePod> pods = gb.buildPods();
    ArrayList<LifePod> podList = new ArrayList<>();
    
    for (Entry<Integer, LifePod> p : pods.entrySet()) {
      System.out.println(p.getKey() + " " + p.getValue().toString());
      podList.add(p.getValue());
    }
    
    String directoryName = "/Users/michaelorr/Desktop";
    String podsFileName = "LifePods.csv";
    
    ExcelWriter ew = new ExcelWriter(podList, directoryName, podsFileName);
    ew.write();
  }




















}




