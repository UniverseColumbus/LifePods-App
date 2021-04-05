import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadInput {
  public String message = null;
  public String fileName = "";
  
  public ReadInput(String fileName) {
    this.fileName = fileName;
  }
  
  
  public ArrayList<User> readFile() {

    int rowCount = 0;
    ArrayList<User> users = new ArrayList<User>();
    
    try (Scanner scanner = new Scanner(new File(fileName));) {
      while (scanner.hasNextLine()) {
        if (rowCount == 0) {
          rowCount++;
          scanner.nextLine();
          continue;
        }
        rowCount++;
        String row = scanner.nextLine();
        try (Scanner rowScanner = new Scanner(row)) {
          rowScanner.useDelimiter(",");
          int id = Integer.parseInt(rowScanner.next());
          String plans = rowScanner.next();
          String willLead = rowScanner.next();
          int friends [] = new int [2];
          friends[0] = Integer.parseInt(rowScanner.next());
          friends[1] = Integer.parseInt(rowScanner.next());
          
          int enemies [] = new int [4];
          enemies[0] = Integer.parseInt(rowScanner.next());
          enemies[1] = Integer.parseInt(rowScanner.next());
          enemies[2] = Integer.parseInt(rowScanner.next());
          enemies[3] = Integer.parseInt(rowScanner.next());
          
          users.add(new User(id, plans, willLead, friends, enemies));
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
}
