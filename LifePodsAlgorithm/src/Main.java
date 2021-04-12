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
  
  public void finish(String readType, String writeType) {
    
    ArrayList<User> users = null;
    String message = "";
    if (readType.equals("csv")) {
      CsvReader cr = new CsvReader(gui.fileName);
      users = cr.readFile();  
      message = cr.message;
    }
    else if (readType.equals("xlsx")) {
      ExcelReader er = new ExcelReader(gui.fileName);
      users = er.readFile();
      message = er.message;
    }

        
    if (message != null) {
      gui.message.setText(message);
      gui.timer.start();
    }
    else if (users != null){
      PodBuilder gb = new PodBuilder(users);
      HashMap<Integer, LifePod> pods = gb.buildPods();
      ArrayList<LifePod> podList = new ArrayList<>();
      
      for (Entry<Integer, LifePod> p : pods.entrySet()) {
        System.out.println(p.getKey() + " " + p.getValue().toString());
        podList.add(p.getValue());
      }
      
      String[] directories = gui.field2.getText().split("/");
      gui.podsFileName = directories[directories.length-1];
      
      
      if (writeType.equals("csv")) {
        CsvWriter cw = new CsvWriter(podList, gui.directoryName, gui.podsFileName);
        cw.write(); 
        message = cw.message;
      }
      else if (writeType.equals("xlsx")) {
        ExcelWriter ew = new ExcelWriter(podList, gui.directoryName, gui.podsFileName); 
        ew.write();
        message = ew.message;
      }
      
      gui.message.setText(message);
      gui.timer.start();
    }
    
  }
  
  
  public static void runWithoutGUI() {
    
    ArrayList<User> users;
    String fileName = "src/source/test8.xlsx";
    
//    CsvReader cr = new CsvReader(fileName);
//    users = cr.readFile();
    
    ExcelReader er = new ExcelReader(fileName);
    users = er.readFile();  
    
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
    
//    String podsFileName = "LifePods.csv";
//    CsvWriter cw = new CsvWriter(podList, directoryName, podsFileName);
//    cw.write();
    
    String podsFileName = "LifePods.xlsx";
    ExcelWriter ew = new ExcelWriter(podList, directoryName, podsFileName); 
    ew.write();
    
  }


  


















}




