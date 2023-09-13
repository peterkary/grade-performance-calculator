import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame{
    
	private ArrayList<String> filedata;
	private JLabel lbl;
	private JButton loadBtn;
	private JButton calcBtn;
	private JButton expBtn;

	public MainFrame() {
		filedata = new ArrayList<String>();
		lbl = new JLabel("Grade Performance Calculator");
		loadBtn = new JButton("Choose File"); 
		calcBtn = new JButton("Calculate");
		expBtn = new JButton("Download");
        
		// buttons setup
        loadBtn.setBounds(50,100,130,30);				//x axis, y axis, width, height  
        this.add(loadBtn);								//adding button in JFrame
        
        calcBtn.setBounds(180,100,130,30);
        this.add(calcBtn);
        
        expBtn.setBounds(310,100,130,30);
        this.add(expBtn);
        
        // label setup
        lbl.setBounds(0, 0, 250, 30);
        lbl.setBackground(new Color(102, 119, 205));
        lbl.setLocation(125, 0);
        lbl.setHorizontalAlignment(JLabel.CENTER);
        lbl.setVerticalAlignment(JLabel.CENTER);
        lbl.setOpaque(true);
        this.add(lbl);
        
        // frame setup
        this.setTitle("Grade Performance");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 250);					// 500 width & 250 height  
        this.setLayout(null);						// using no layout managers  
        this.setVisible(true);						// frame visible
        
        // button listeners
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	loadFromFile();
            }
        });
        
        calcBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	writeToFile();
            }
        });
        
        expBtn.addActionListener(new ActionListener() {
        	  @Override
        	  public void actionPerformed(ActionEvent e) {
        		  	downloadFile();
        	  }
        });
	}
	
	// methods
    private void loadFromFile() {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(MainFrame.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fileName = fc.getSelectedFile().getPath();
            try { // open file
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line = "";
                while (reader.ready()) {
                    line = reader.readLine();
                    filedata.add(line);
                    System.out.println(line);
                }
                reader.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(loadBtn,
                        "Can't access " + fileName,
                        "File not found error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(loadBtn,
                        "Can't write to " + fileName,
                        "File I/O error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void writeToFile() {
        int[] grades = new int[3];
        double f_grade;
        String[] arr_spl = new String[5];
        try
            {
        			if (filedata.isEmpty()) {
        				System.out.println("Please choose a file");
        				return;
        			}
               PrintWriter newfile = new PrintWriter("grades_calc.txt", "UTF-8");
               // TODO: Add support for more than 3 grades per person
               // TODO: Add support for float grades
               for(int i=0;i<filedata.size();i++)
               {
            	   arr_spl = ((filedata.get(i)).split(" "));
                   grades[0] = Integer.valueOf(arr_spl[2]);
                   grades[1] = Integer.valueOf(arr_spl[3]);
                   grades[2] = Integer.valueOf(arr_spl[4]);
                   f_grade = ((0.4*grades[0])+(0.4*grades[1])+(0.2*grades[2]))/3;
                   newfile.print(arr_spl[0]+" "+arr_spl[1]+" ");
                   newfile.format("%.2f", f_grade);
                   newfile.print("\n");
               }
               newfile.close();
               JOptionPane.showMessageDialog(null, "Performance calculation completed","",JOptionPane.INFORMATION_MESSAGE);
            }
        	catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(loadBtn,
                        "Can't access ",
                        "File not found error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(loadBtn,
                        "Can't write to " ,
                        "File I/O error",
                        JOptionPane.ERROR_MESSAGE);
            }
    }
    
    private void downloadFile() {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showSaveDialog(MainFrame.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String fileName = fc.getSelectedFile().getPath();
            try {
            	File file = new File("grades_calc.txt");
            	File newfile = new File(fc.getSelectedFile().getPath());
                Files.copy(file.toPath(), newfile.toPath());
                JOptionPane.showMessageDialog(null, "File has been saved","File Saved",JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(loadBtn,
                        "Can't access " + fileName,
                        "File not found error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(loadBtn,
                        "Can't write to " + fileName,
                        "File I/O error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}