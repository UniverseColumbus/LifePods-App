import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

public class ReadInput2 {
  public String message = null;
  public String fileName = "";
  public ArrayList<User> users = new ArrayList<User>();
  public HashMap<Integer, String> map = new HashMap<>();
  
  public ReadInput2(String fileName) {
    this.fileName = fileName;
  }
    
    
  public ArrayList<User> readFile() {
    
    try (Scanner scanner = new Scanner(new File(fileName));) {
      findHeadings(scanner);
      
      while (scanner.hasNextLine()) {
          
        String row = scanner.nextLine();
        String[] splitRow = row.split(",");
        User u = new User();
        users.add(u);
        int[] friends = new int[2];
        int fIndex = 0;
        int[] enemies = new int[4];
        int eIndex = 0;
        
        for (int key : map.keySet()) {
          String val = map.get(key);
            
          if (val.equals("id")) u.setId(Integer.parseInt(splitRow[key])); 
          else if (val.equals("names")) u.setName(splitRow[key]);
          else if (val.equals("plans")) u.setPlans(splitRow[key]);
          else if (val.equals("willLead")) u.setWillLead(splitRow[key]);
          else if (val.equals("friends")) {
            if (splitRow[key].isEmpty()) friends[fIndex] = -1;
            else friends[fIndex] = Integer.parseInt(splitRow[key]);
            fIndex++;
          }
          else if (val.equals("enemies")) {
            if (splitRow[key].isEmpty()) enemies[eIndex] = -1;
            else enemies[eIndex] = Integer.parseInt(splitRow[key]);
            eIndex++;
          }
          
          if (fIndex == 2) u.setFriends(friends);
          if (eIndex == 4) u.setEnemies(enemies);
        }
        

      }
    } 
    catch (FileNotFoundException e) {
      System.out.println(e.toString());
      message = "Error: Input File Not Found.";
    }
    catch (NumberFormatException e) {
      System.out.println(e.toString());
      message = "Error: Wrong Input Format.";
    }
      
    return users;
  }
  
  
  private void findHeadings(Scanner scanner) {
    boolean canStart = false;
    
    while (scanner.hasNextLine() && canStart == false) {
      String row = scanner.nextLine();
      String[] splitRow = row.split(",");
      int key = 0;
      int total = 0;
      int index = 0;
      
      while (index < splitRow.length) {
        String str = splitRow[index];
        
        if (str.isEmpty()) map.put(key, "ignore");
        else if (str.equals("User ID")) map.put(key, "id");
        else if (str.equals("Names")) map.put(key, "names");
        else if (str.equals("Post Grad Plans")) map.put(key, "plans");
        else if (str.equals("Willing to be pod leader?")) map.put(key, "willLead");
        else if (str.matches("Preference [1-2]")) map.put(key, "friends");
        else if (str.matches("Anti-preference [1-4]")) map.put(key, "enemies");
        else map.put(key, "ignore");
         
        
        if (!map.get(key).equals("ignore")) total++;
        if (total >= 10) {
          canStart = true;
        }
        key++;
        index++;
      }
    }
    if (canStart == false) {
      message = "Error: Missing Field in Input Data.";
    }
  }



















}
