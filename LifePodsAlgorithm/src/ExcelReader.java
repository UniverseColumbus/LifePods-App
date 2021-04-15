import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
//jar files from: https://poi.apache.org/download.html
//xmlbeans-4.0.0.jar 
//poi-ooxml-lite-5.0.0.jar 
//poi-ooxml-5.0.0.jar 
//poi-5.0.0.jar
//commons-compress-1.20.jar 
//commons-collections4-4.4.jar
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelReader {
  
  private String fileName = "";
  private ArrayList<User> users = new ArrayList<User>();
  private HashMap<Integer, String> map = new HashMap<>();
  private XSSFRow row;
  private int testKey = 0;
  public String message = null;
  
  public ExcelReader(String fileName) {
    this.fileName = fileName;
  }
    
  
  public ArrayList<User> readFile(){
    
    try {
      FileInputStream fis = new FileInputStream(new File(fileName));
      XSSFWorkbook workbook = new XSSFWorkbook(fis);
      XSSFSheet sheet = workbook.getSheetAt(0);
      Iterator<Row>  ri = sheet.iterator();
      findHeadings(ri);
      
      while (ri.hasNext()) {
        row = (XSSFRow)ri.next();
        Cell testCell = row.getCell(testKey);
        if (testCell.getNumericCellValue() <= 0) break;
        
        
        User u = new User();
        users.add(u);
        int[] friends = new int[2];
        int fIndex = 0;
        int[] enemies = new int[4];
        int eIndex = 0;
        
        for (int key : map.keySet()) {
          String str = "";
          String val = map.get(key);
          Cell cell = row.getCell(key);
          
          if (cell != null) {
            CellType type = cell.getCellType();
            
            if (type == CellType.NUMERIC) {
              double dval = cell.getNumericCellValue();
              int ival = (int)dval;
              str = String.valueOf(ival);
            }
            else if (type == CellType.STRING) str = cell.getStringCellValue();
            else if (type == CellType.BOOLEAN) str = Boolean.toString(cell.getBooleanCellValue());
            else if (type == CellType.BLANK) str = cell + "";
          }
          
             
          if (val.equals("id")) u.setId((int)Integer.parseInt(str));
          else if (val.equals("names")) u.setName(str);
          else if (val.equals("plans")) u.setPlans(str);
          else if (val.equals("willLead")) u.setWillLead(str);
          else if (val.equals("friends")) {
            if (str.isEmpty()) friends[fIndex] = -1;
            else friends[fIndex] = (int)Integer.parseInt(str);
            fIndex++;
          }
          else if (val.equals("enemies")) {
            if (str.isEmpty()) enemies[eIndex] = -1;
            else enemies[eIndex] = (int)Integer.parseInt(str);
            eIndex++;
          }
           
          if (fIndex == 2) u.setFriends(friends);
          if (eIndex == 4) u.setEnemies(enemies);
        }
      }
      fis.close();
      workbook.close();
    }
    catch (FileNotFoundException ex) {
      System.out.println(ex.toString());
      message = "Error: Input File Not Found.";
    }
    catch (NumberFormatException ex) {
      System.out.println(ex.toString());
      message = "Error: Wrong Input Format.";
    }
    catch (Exception ex) {
      System.out.println(ex.toString());
      message = "Error: Failed to Read Input Data.";
    }
    
    return users;
  }


  private void findHeadings(Iterator<Row> ri) {
    boolean canStart = false;
    
    while (ri.hasNext() && canStart == false) {
      row = (XSSFRow)ri.next();
      int size = row.getLastCellNum();
      int key = 0;
      int total = 0;
      int index = 0;
      
      while (index < size) {
        XSSFCell cell = row.getCell(index);
        
        if (cell == null) map.put(key, "ignore");
        else {
          String str = cell.toString();
          
          if (str.isEmpty()) map.put(key, "ignore");
          else if (str.equals("User ID")) {
            map.put(key, "id");
            testKey = key;
          }
          else if (str.equals("Names")) map.put(key, "names");
          else if (str.equals("Post Grad Plans")) map.put(key, "plans");
          else if (str.equals("Willing to be pod leader?")) map.put(key, "willLead");
          else if (str.matches("Preference [1-2]")) map.put(key, "friends");
          else if (str.matches("Anti-preference [1-4]")) map.put(key, "enemies");
          else map.put(key, "ignore");
        }
        
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
