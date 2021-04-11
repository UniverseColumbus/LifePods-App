import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
//jar files from: https://poi.apache.org/download.html
//xmlbeans-4.0.0.jar 
//poi-ooxml-lite-5.0.0.jar 
//poi-ooxml-5.0.0.jar 
//poi-5.0.0.jar
//commons-compress-1.20.jar 
//commons-collections4-4.4.jar
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelWriter {
  
  private ArrayList<LifePod> pods;
  private String directory;
  private String podsFileName;
  public String message = "";
  
  public ExcelWriter(ArrayList<LifePod> pods, String directory, String podsFileName) {
    this.pods = pods;
    this.directory = directory;
    this.podsFileName = podsFileName;
  }
  
  public void write(){
  
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("LifePods");
    sheet.setColumnWidth(0,3000);
    sheet.setColumnWidth(2,5000);
    sheet.setColumnWidth(3,4000);
    XSSFRow row = sheet.createRow(0);
    Cell cell = row.createCell(0);
    cell.setCellValue("List of LifePods");
    row = sheet.createRow(1);
    cell = row.createCell(0);
    cell.setCellValue("LifePod");
    cell = row.createCell(1);
    cell.setCellValue("User ID");
    cell = row.createCell(2);
    cell.setCellValue("Name");
    cell = row.createCell(3);
    cell.setCellValue("Post Grad Plans");
    cell = row.createCell(4);
    cell.setCellValue("Status");
    
   
    int rowNum = 3;
    for (int i=0; i<pods.size(); i++) {
      LifePod pod = pods.get(i);
      ArrayList<User> members = pod.getMembers();
      
      rowNum++;
      row = sheet.createRow(rowNum);
      cell = row.createCell(0);
      cell.setCellValue(pod.getType().toUpperCase() + " POD");
      rowNum++;
      row = sheet.createRow(rowNum);
      
      for (User member : members) {
        cell = row.createCell(1);
        cell.setCellValue(member.getId());
        cell = row.createCell(2);
        cell.setCellValue(member.getName());
        cell = row.createCell(3);
        cell.setCellValue(member.getPlans());
        
        if (member.isLeader()) {
          cell = row.createCell(4);
          cell.setCellValue("Pod Leader");
        }
        rowNum++;
        row = sheet.createRow(rowNum);
      }
      
      rowNum++;
    }
    
    try {
      File excelFile = new File(directory, podsFileName);
      FileOutputStream out = new FileOutputStream(excelFile);
      workbook.write(out);
      workbook.close();
      message = "'" + podsFileName + "'  created.";
    }
    catch (IOException ex) {
      System.out.println(ex.toString());
      message = "Error: Folder Permission Denied.";
    }
    catch (Exception ex) {
      System.out.println(ex.toString());
      message = "Error: Failed to Create Pods.";
    }
  }



























}