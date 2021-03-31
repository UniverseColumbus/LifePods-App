import java.util.*;
import java.util.Map.Entry;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;


public class Main implements ActionListener{
  
  public static JFrame frame;
  public static Container panel;  
  public static JTextField field1;
  public static JTextField field2;
  public static JButton button1;
  public static JButton button2;
  public static JLabel label;
  public static GridBagConstraints c;
  public static boolean fileChosen = false;
  public static boolean directoryChosen = false;
  public static String fileName = "";
  public static String directoryName = "";
  public static String podsFileName = "LifePods.csv";

  public static void main(String[] args) {
    
    Main s = new Main(); 
    
    frame = new JFrame("LifePods Builder");
    frame.setMinimumSize(new Dimension(350, 200));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel = frame.getContentPane();
    panel.setLayout(new GridBagLayout());
    c = new GridBagConstraints();
    
    ////////////
    field1 = new JTextField("");
    field1.setEditable(false);
    field1.setBackground(Color.decode("#D0D0D0"));
    field1.setBorder(BorderFactory.createLineBorder(Color.decode("#D0D0D0")));
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(20, 10, 0, 10);
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 5;
    c.gridwidth = 1;
    c.ipadx = 200;
    c.ipady = 5;
    panel.add(field1, c);
    
    button1 = new JButton("Choose Input");
    button1.addActionListener(s);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(20, 0, 0, 10);
    c.ipadx = 0;
    c.ipady = 5;
    c.gridx = 1;
    c.gridy = 0;
    c.weightx = 1;
    panel.add(button1, c);
    
    ////////////
    JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
    separator1.setForeground(Color.decode("#a8afba"));
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 2;
    c.insets = new Insets(10, 10, 0, 10);
    panel.add(separator1, c);
    
    field2 = new JTextField("");
    field2.setEditable(false);
    field2.setBackground(Color.decode("#D0D0D0"));
    field2.setBorder(BorderFactory.createLineBorder(Color.decode("#D0D0D0")));
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(10, 10, 0, 10);
    c.gridx = 0;
    c.gridy = 2;
    c.weightx = 5;
    c.gridwidth = 1;
    c.ipadx = 200;
    c.ipady = 5;
    panel.add(field2, c);
    
    button2 = new JButton("Choose Output"); 
    button2.addActionListener(s);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(10, 0, 0, 10);
    c.ipadx = 0;
    c.ipady = 5;
    c.gridx = 1;
    c.gridy = 2;
    c.weightx = 1;
    panel.add(button2, c);
    
    ////////////
    JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
    separator2.setForeground(Color.decode("#a8afba"));
    c.gridx = 0;
    c.gridy = 3;
    c.gridwidth = 2;
//    c.weighty = 1;
    c.insets = new Insets(10, 10, 0, 10);
    panel.add(separator2, c);
    
    label = new JLabel();
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(25, 10, 15, 10);
    c.ipadx = 0;
    c.ipady = 5;
    c.gridx = 0;
    c.gridy = 4;
    c.weightx = 1;
    panel.add(label, c); 
    
    JButton button3 = new JButton("Create Pods");
    button3.setForeground(Color.decode("#148229"));
    button3.setFont(button3.getFont().deriveFont(Font.BOLD, 14f));
    button3.addActionListener(s); 
    c.fill = GridBagConstraints.HORIZONTAL;
    c.insets = new Insets(25, 0, 15, 10);
    c.ipadx = 0;
    c.ipady = 5;
    c.gridx = 1;
    c.gridy = 4;
    c.weightx = 1;
    panel.add(button3, c); 
    
    
    frame.pack();
    frame.setVisible(true);
  }
  
  
  @Override
  public void actionPerformed(ActionEvent e){ 
    String s = e.getActionCommand(); 
    
    if (s.equals("Choose Input")) { 
      
      button1.setForeground(Color.RED);
      FileDialog fd = new FileDialog(frame,"Select file");
      fd.setFilenameFilter((File dir, String name) -> name.endsWith(".csv"));
      fd.setVisible(true);
      button1.setForeground(Color.BLACK);
      
      if (fd.getFile() == null) fileChosen = false;
      else {
        fileName = fd.getDirectory() + fd.getFile();
        field1.setText(fd.getFile());
        fileChosen = true;
        
        if (directoryChosen == false) {
          directoryName = fd.getDirectory();
          String displayDirectory = customDirectory(directoryName);
          textListener(displayDirectory);
        }
      }
      
    } 
    
    
    if (s.equals("Choose Output")) {
      
      button2.setForeground(Color.RED);
      FileDialog fd = new FileDialog(frame,"Select file");
      fd.setMode(FileDialog.SAVE);
      fd.setFile("LifePods.csv");
      fd.setVisible(true);
      button2.setForeground(Color.BLACK);
      
      if (fd.getDirectory() == null) directoryChosen = false;
      else {
        directoryName = fd.getDirectory();
        podsFileName = fd.getFile();
        String displayDirectory = customDirectory(directoryName);
        textListener(displayDirectory);
      }
      
    }
   
    
    if (s.equals("Create Pods")) {
      if (!fileChosen || !directoryChosen) {
        label.setText("Error: Missing Fields");
      }
      else {
        label.setText("Success!!!");
        System.out.println("file: " + fileName);
        System.out.println("directory: " + directoryName);
        System.out.println();
        finish();
      }
    }
  
  } 
  
  
  private static String customDirectory(String directoryName) {
    Path path = Paths.get(directoryName);
    String parent = "";
    if (path.getParent() != null) parent = path.getParent().toString();
    String displayDirectory = directoryName.replace(parent, "");
    directoryChosen = true;
    
    field2.setEditable(true);
    field2.setBackground(Color.WHITE);
    field2.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
    field2.setText(displayDirectory + podsFileName);
    
    return displayDirectory;
  }
  
  
  private static void textListener(String displayDirectory) {
    int n = displayDirectory.length();
    
    field2.addKeyListener(new KeyListener(){
      @Override
      public void keyPressed(KeyEvent arg0){
        if (field2.getCaret().getDot() <= n){
          field2.setText(displayDirectory + field2.getText().substring(n));
          arg0.consume();
        }
      }

      @Override
      public void keyReleased(KeyEvent arg0){}

      @Override
      public void keyTyped(KeyEvent arg0){}
    });
    
  }
  
  
  private static void finish() {
    ArrayList<User> users;
    users = FileReader.readFile(fileName);    
    
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
    
    String[] directories = field2.getText().split("/");
    podsFileName = directories[directories.length-1];
    ExcelWriter ew = new ExcelWriter(podList, directoryName, podsFileName);
    ew.write(); 
  }
  











}




