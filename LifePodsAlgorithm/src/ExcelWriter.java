import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

public class ExcelWriter {
  private ArrayList<LifePod> pods;
  String directory;
  String podsFileName;
  
  public ExcelWriter(ArrayList<LifePod> pods, String directory, String podsFileName) {
    this.pods = pods;
    this.directory = directory;
    this.podsFileName = podsFileName;
  }
  
  public void write() {
//  File csvFile = new File(System.getProperty("user.home") + "/Desktop", "LifePods.csv");
    
    File csvFile = new File(directory, podsFileName);
    
    try{
      FileWriter writer = new FileWriter(csvFile);
      writer.append("List of LifePods");
      writer.append("\n");
      writer.append("LifePod");
      writer.append(",");
      writer.append("User ID");
      writer.append(",");
      writer.append("Post Grad Plans");
      writer.append(",");
      writer.append("Status");
      writer.append("\n\n");
      
      for (int i=0; i<pods.size(); i++) {
        LifePod pod = pods.get(i);
        ArrayList<User> members = pod.getMembers();
        
        writer.append(pod.getType().toUpperCase() + " POD");
        writer.append("\n");
        writer.append(",");
        
        
        
        for (User member : members) {
          writer.append(String.valueOf(member.getId()));
          writer.append(",");
          writer.append(member.getPlans());
          writer.append(",");
          if (member.isLeader()) writer.append("Pod Leader");
          writer.append("\n");
          writer.append(",");
        }
        
        writer.append("\n");
      }
      
      writer.flush();
      writer.close();
    } 
    catch (IOException ex) {
      System.out.println(ex.toString());
      System.out.println("Could not find file.");
    }
     
  }
  

}
