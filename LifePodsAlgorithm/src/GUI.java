import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;


public class GUI implements ActionListener{
  public JFrame frame;
  public Container panel;  
  public JTextField field1;
  public JTextField field2;
  public JButton button1;
  public JButton button2;
  public JTextField message;
  public GridBagConstraints c;
  public boolean fileChosen = false;
  public boolean directoryChosen = false;
  public String fileName = "";
  public String directoryName = "";
  public String podsFileName = "LifePods.csv";
  public String readType = "";
  public String writeType = "";
  public Timer timer;
  private Main main;
  
  public GUI(Main m) {
    main = m;
  }

  public void launch() {
    
    frame = new JFrame("LifePods Creator");
    frame.setMinimumSize(new Dimension(300, 260));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    panel = frame.getContentPane();
    panel.setLayout(new GridBagLayout());
    panel.setBackground(Color.decode("#f0f0f0"));
    c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;
    createTimer();
    
    ////////////
    JLabel label1 = new JLabel("Input Data: ");
    c.insets = new Insets(20, 14, -1, 0);
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = 5;
    c.gridwidth = 1;
    
    panel.add(label1, c);
    
    field1 = new JTextField("");
    field1.setEditable(false);
    field1.setBackground(Color.decode("#D0D0D0"));
    field1.setBorder(BorderFactory.createLineBorder(Color.decode("#D0D0D0")));
    field1.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    c.insets = new Insets(0, 14, 0, 10);
    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 5;
    c.gridwidth = 1;
    c.ipadx = 200;
    c.ipady = 10;
    panel.add(field1, c);
    
    button1 = new JButton("Select File");
    button1.addActionListener(this);
    c.insets = new Insets(0, 0, 0, 10);
    c.ipadx = 0;
    c.ipady = 5;
    c.gridx = 1;
    c.gridy = 1;
    c.weightx = 1;
    panel.add(button1, c);
    
    ////////////
    JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
    separator1.setForeground(Color.decode("#a8afba"));
    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 2;
    c.insets = new Insets(5, 14, 0, 10);
    panel.add(separator1, c);
    
    JLabel label2 = new JLabel("Pods Location:");
    c.insets = new Insets(10, 14, -4, 0);
    c.gridx = 0;
    c.gridy = 3;
    c.weightx = 5;
    c.gridwidth = 1;
    panel.add(label2, c);
    
    field2 = new JTextField("");
    field2.setEditable(false);
    field2.setBackground(Color.decode("#D0D0D0"));
    field2.setBorder(BorderFactory.createLineBorder(Color.decode("#D0D0D0")));
    field2.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
    c.insets = new Insets(0, 14, 0, 10);
    c.gridx = 0;
    c.gridy = 4;
    c.weightx = 5;
    c.gridwidth = 1;
    c.ipadx = 200;
    c.ipady = 10;
    panel.add(field2, c);
    
    button2 = new JButton("Create Pods"); 
    button2.setForeground(Color.decode("#148229"));
    button2.setFont(button2.getFont().deriveFont(Font.BOLD, 14f));
    button2.addActionListener(this);
    button2.setEnabled(false);
    c.insets = new Insets(0, 0, 0, 10);
    c.ipadx = 0;
    c.ipady = 5;
    c.gridx = 1;
    c.gridy = 4;
    c.weightx = 1;
    panel.add(button2, c);
    
    ////////////
    JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
    separator2.setForeground(Color.decode("#a8afba"));
    c.gridx = 0;
    c.gridy = 5;
    c.gridwidth = 2;
    c.insets = new Insets(5, 14, 0, 10);
    panel.add(separator2, c);
    
    message = new JTextField("");
    message.setEditable(false);
    message.setBackground(Color.decode("#f0f0f0"));
    message.setBorder(BorderFactory.createLineBorder(Color.decode("#f0f0f0")));
    c.insets = new Insets(20, 14, 10, 10);
    c.ipadx = 0;
    c.ipady = 10;
    c.gridx = 0;
    c.gridy = 6;
    c.weightx = 1;
    panel.add(message, c); 
    
    
    frame.pack();
    frame.setVisible(true);
  }
  
  
  @Override
  public void actionPerformed(ActionEvent e){ 
    message.setText("");
    String s = e.getActionCommand(); 
    
    if (s.equals("Select File")) { 
      field1.setText("");
      field2.setText("");
      button2.setEnabled(false);
      
      
      button1.setForeground(Color.RED);
      FileDialog fd = new FileDialog(frame,"Select file");
      fd.setFilenameFilter(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return false || name.toLowerCase().endsWith(".csv")
              || name.toLowerCase().endsWith(".xlsx");
        }
      });
      fd.setVisible(true);
      button1.setForeground(Color.BLACK);
      
      String file = fd.getFile();
      if (file == null) fileChosen = false;
      else {
        button2.setEnabled(true);
        
        fileName = fd.getDirectory() + file;
        field1.setText(file);
        fileChosen = true;
        
        readType = file.split("\\.")[1];
      }
      
    } 
    
    
    if (s.equals("Create Pods")) {
      field2.setText("");
      message.setText("processing...");
      button2.setForeground(Color.RED);
      FileDialog fd = new FileDialog(frame,"Create Pods");
      fd.setMode(FileDialog.SAVE);
      fd.setFile("LifePods.xlsx");
      fd.setVisible(true);
      button2.setForeground(Color.decode("#148229"));
      
      if (fd.getDirectory() == null) {
        directoryChosen = false;
        message.setText("");
      }
      else {
        directoryChosen = true;
        directoryName = fd.getDirectory();
        podsFileName = fd.getFile();
        
        if (podsFileName.matches(".*[.].*") == false) podsFileName += ".csv";
        displayDirectory(directoryName);
        writeType = podsFileName.split("\\.")[1];
        
        
        main.finish(readType, writeType);
      }
    }
  } 
  
  
  private void displayDirectory(String directoryName) {
    Path path = Paths.get(directoryName);
    String parent = "";
    if (path.getParent() != null) parent = path.getParent().toString();
    String display = directoryName.replace(parent, "");
    String last = display.substring(display.length() -1);
    if (!last.equals("/")) {
      display += "/";
    }
    
    field2.setText(display + podsFileName);
  }
  
  
  private void createTimer() {
    timer = new Timer(3000, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        message.setText("");
      }
    });
    timer.setRepeats(false);
  }


  
















}
