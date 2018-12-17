import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.prompt.PromptSupport;

public class Portscanner1 {
	/* For the progress */
	public static double progress = 0;
	/* For the progress */
	public static double current = 0 ;
	/* default start port value */
	private int fport = 1;
	/* default stop port value */
	private int lport = 100;
	public Portscanner1(){
		/* Creating the frame */
		JFrame myFrame = new JFrame("Portscanner v0.90");
		/* Exit the frame when the user press exit button */
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/* set frame layout */
		myFrame.setLayout(new BorderLayout());
		/* preparing the root menu*/
		JMenuBar menuBar = new JMenuBar();
		/* preparing the user menu */
		JMenu fileMenu = new JMenu("File");
		JMenu helpMenu = new JMenu("Help");
		JMenu portMenu = new JMenu("Ports");
		/* preparing the Filemenu's items */
		JMenuItem ITFL = new JMenuItem("Import target from list");
		JMenuItem exit = new JMenuItem("Exit");
		/* preparing the helpmenu's items */
		JMenuItem about = new JMenuItem("About");
		/* preparing the portMenu's items */
		JMenuItem changePorts = new JMenuItem("Change");
		/* add items to the filemenu */
		fileMenu.add(ITFL);
		fileMenu.add(exit);
		/* add items to the helpmenu */
		helpMenu.add(about);
		/* add items to the portmenu */
		portMenu.add(changePorts);
		/* add the user's menus to the root menu */
		menuBar.add(fileMenu);
		menuBar.add(portMenu);
		menuBar.add(helpMenu);
		/* set the root menu as visible */
		menuBar.setVisible(true);
		/* adding the root menu to the frame */
		myFrame.add(menuBar,BorderLayout.NORTH);
		/* Creating text targetsArea */
		JTextArea targetsArea = new JTextArea();
		/* adding the targetsArea */
		myFrame.add(new JScrollPane(targetsArea), BorderLayout.CENTER);
		/* Creating scan button */
		JButton scanButton = new JButton("Scan");
		/* add the button to the frame */
		myFrame.add(scanButton, BorderLayout.SOUTH);
		/* add placeholder text for the targetsArea */
		PromptSupport.setPrompt("Target/Targets", targetsArea);
		/* change the font of the targetsArea */
		targetsArea.setFont(new Font("Roman", Font.BOLD, 12));
		/* ACTIONS (need to be down) */
				/*		File -> exit     */
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				myFrame.dispose();
			}
			
		});
				/*		Help -> About	*/  
		about.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "programmed by omanroot\nEmail:om-root@hotmail.com","About",JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		changePorts.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JTextField startPortField = new JTextField(5);
			    JTextField stopPortField = new JTextField(5);
			    JPanel myPanel = new JPanel();
			    myPanel.add(new JLabel("Start port : "));
			    myPanel.add(startPortField);
			    
			    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			    
			    myPanel.add(new JLabel("Stop port : "));
			    myPanel.add(stopPortField);
			    PromptSupport.setPrompt(String.valueOf(fport), startPortField);
			    PromptSupport.setPrompt(String.valueOf(lport), stopPortField);
			    int result = JOptionPane.showConfirmDialog(null, myPanel, 
			               "Please Enter start port/stop port Values", JOptionPane.OK_CANCEL_OPTION);
			    if(result == JOptionPane.OK_OPTION){
			    	if(!Portscanner1.isNumeric(startPortField.getText()) || !Portscanner1.isNumeric(stopPortField.getText()) ){
			    		JOptionPane.showMessageDialog(null, "Please enter valid ports ","ERROR",JOptionPane.ERROR_MESSAGE);
			    		return ;
			    	}
			    	if(Integer.parseInt(startPortField.getText()) > Integer.parseInt(stopPortField.getText()) ){
			    		JOptionPane.showMessageDialog(null, "invalid port : \"Start port > Stop port\" !","ERROR",JOptionPane.ERROR_MESSAGE);
			    		return ;
			    	}
			    	fport  = Integer.parseInt(startPortField.getText());
			    	lport  = Integer.parseInt(stopPortField.getText());
			    	
			    }
			}
		});
		ITFL.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser finput = new JFileChooser();
				int result = finput.showOpenDialog(ITFL);
				if (result == JFileChooser.APPROVE_OPTION){
					if(targetsArea.getText() != "" || targetsArea.getText() != null){
						targetsArea.setText("");
					}
					File ffile = finput.getSelectedFile();
					String currline;
					BufferedReader br = null;
					try {
						br = new BufferedReader(new FileReader(ffile.getAbsolutePath()));
					} catch (Exception ee) {
						JOptionPane.showMessageDialog(null,
							    "Error has ouccr when opening the file.",
							    "Error !",
							    JOptionPane.ERROR_MESSAGE);
						return;
					}
	
					try {
						while ((currline = br.readLine()) != null) {
							targetsArea.append(currline+"\n");
							}
					} catch (IOException eee) {
						eee.printStackTrace();
					}
				}
			}
			
			
		});
		scanButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				/** progress bar **/
				JProgressBar pbar = new JProgressBar();
				JFrame barWindow = new JFrame("Scanning progress ");
				barWindow.setLayout(new BorderLayout());
				barWindow.add(pbar,BorderLayout.CENTER);
				pbar.setValue(1);
				barWindow.setSize(500,80);
				pbar.setVisible(true);
				Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			    int x = (int) ((dimension.getWidth() - barWindow.getWidth()) / 2);
			    int y = (int) ((dimension.getHeight() - barWindow.getHeight()) / 2);
			    barWindow.setLocation(x, y);
				barWindow.setVisible(true);
				barWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				/** **/
				/** pbar helper **/
				Runnable forProgress = new Runnable(){
					public void run(){
						double i = 0.00;
						while(i != 100){
							try {
								Thread.sleep(1000);
								i = (current/progress)*100;
								System.out.println("i="+i);
								int progressValue = (int)i;
								pbar.setStringPainted(true);
								pbar.setString(progressValue+"%");
								pbar.setValue((int)i);
								System.out.println("current="+current+"\nprogress="+progress);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				};
				Thread thread2 = new Thread(forProgress,"test2");
				thread2.start();
				
				/** **/
				Runnable theRun  = new Runnable(){
					public void run(){
						String targets = targetsArea.getText();
						if(targets.contains(",")){
							String [] target = targets.split(",");
							String latestResult = "";
							for(int i = 0 ; i < target.length ; i++){
								latestResult += ScanThis.start(target[i], fport, lport);
								Thread n = new Thread(forProgress,"new");
								n.start();
							}
							outputMe(latestResult);
							barWindow.setVisible(false);
						}else if(targets.contains("-")){
							String [] target = targets.split("-");
							String startNum = target[0].split("\\.")[3];
							String stopNum = target[1].split("\\.")[3];
							ArrayList<String> secList = new ArrayList<String>();
							secList.add(target[1].split("\\.")[0]);
							secList.add(target[1].split("\\.")[1]);
							secList.add(target[1].split("\\.")[2]);
							secList.add(target[1].split("\\.")[3]);
							secList.remove(3);
							String globalIP = "";
							for(String b : secList){
								globalIP += b+".";
							}
							String latestResult = "";
							for(int i = Integer.parseInt(startNum);i<=Integer.parseInt(stopNum);i++){
								latestResult += ScanThis.start(globalIP+i,fport,lport);
								Thread n = new Thread(forProgress,"new");
								n.start();
							}
							outputMe(latestResult);
							barWindow.setVisible(false);
						}else{
							String latestResult = ScanThis.start(targets, fport, lport);
							outputMe(latestResult);
							barWindow.setVisible(false);
						}
				
					}
					
				};
				Thread r = new Thread(theRun,"run");
				r.start();
			}
		});
		
		
		
		
		
		/*  Set the size of the myFrame  */
		myFrame.setSize(400,200);
		/* Centering the frame */
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - myFrame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - myFrame.getHeight()) / 2);
	    myFrame.setLocation(x, y);
		/* frame things here */
		myFrame.setVisible(true);
	}
	public static void main(String []args){
		new Portscanner1();
	}
	public void outputMe(String result){
		/* User Output Section */
		JFrame resFrame = new JFrame("Result");
		JTextArea resArea = new JTextArea();
		/* Result area properties */
		resArea.setText(result);
		resArea.setBackground(Color.LIGHT_GRAY);
		resArea.setEditable(false);
		resFrame.add(new JScrollPane(resArea));
		resFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		resFrame.setSize(400,400);
		/* Centering the frame */
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - resFrame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - resFrame.getHeight()) / 2);
	    resFrame.setLocation(x, y);
		resFrame.setVisible(true);
	}
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
	
}
