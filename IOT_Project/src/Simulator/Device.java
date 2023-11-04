package Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;
      
@SuppressWarnings("serial")
public class Device extends JFrame implements ActionListener{
	 
	private JPanel mainPanel;
	private JPanel componentsPanel;
	private JFrame IoT_Devices_Frame;
	private JMenuItem submit, back;
	private JButton Connect,Disconnect;
	@SuppressWarnings("rawtypes")
	private ArrayList<JComboBox> boxes;
	private ArrayList<String> optionsNames;

	public Device(String fileName, JFrame frame, String deviceID, String deviceName) {
		
	   IoT_Devices_Frame=frame;
	   mainPanel=new JPanel();
	   mainPanel.setLayout(new BorderLayout());
	   componentsPanel=new JPanel();
	    
	   createMenu();
	   createConnectionButtons();
	   createDeviceData(fileName);
	   mainPanel.add(componentsPanel,BorderLayout.CENTER);     
	    
	   addActionListeners(deviceID,deviceName);
	  
	   if(optionsNames.size()==1)
	   {
		   setSize(268,184);
	   }
	   else if(optionsNames.size()>1)
	   {
		   int addHeight=10*(optionsNames.size()-1);
		   setSize(268,194+addHeight);
	   } 
	
	   add(mainPanel);
	   setDefaultCloseOperation(EXIT_ON_CLOSE);
	   setLocationRelativeTo(null);
	   setResizable(false);
	   setTitle(deviceName);
	}
	
	public void createMenu() {
		
		JMenu menu=new JMenu("Options");
	    JMenuBar menuBar=new JMenuBar();
	    submit=new JMenuItem("submit data");
	    submit.setIcon(new ImageIcon("Icons//send.png"));
	    back=new JMenuItem("back");
	    back.setIcon(new ImageIcon("Icons//backward.png"));
	    menu.add(submit);
	    menu.add(back);
	    menuBar.add(menu); 
	    mainPanel.add(menuBar,BorderLayout.NORTH);
	}
	
	public void createConnectionButtons() {
		
		JPanel con_dis_panel=new JPanel();
	  	con_dis_panel.setBorder(BorderFactory.createTitledBorder("Connection with the server"));
	  	Connect=new JButton("Connect");
	  	Connect.setEnabled(true);
	  	Disconnect=new JButton("Disconnect");
	  	Disconnect.setEnabled(false);
		con_dis_panel.add(Connect);
		con_dis_panel.add(Disconnect);
		componentsPanel.add(con_dis_panel);	
	}
	
	@SuppressWarnings("rawtypes")
	public void createDeviceData(String fileName) {
		
		boxes=new ArrayList<JComboBox>();
		optionsNames=new ArrayList<String>();
		JPanel dataPanel=new JPanel();
		String option1, option2;
	    
	    try{ 
	           File inputFile = new File(fileName);
	           DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	           DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	           Document doc = dBuilder.parse(inputFile);
	           NodeList nListItem = doc.getElementsByTagName("Item"); 
	           
	           for(int i=0; i<nListItem.getLength(); i++)
	           {
	        	  JPanel tempPanel=new JPanel();
	        	  tempPanel.setLayout(new GridLayout(1,2));
	        	  Node nNodeItem = nListItem.item(i);
         	      Element eElement = (Element) nNodeItem;
         	      optionsNames.add(i, eElement.getFirstChild().getNextSibling().getFirstChild().getTextContent());
         	      tempPanel.add(new JLabel(eElement.getFirstChild().getNextSibling().getFirstChild().getTextContent()));
         	      option1 = eElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().getFirstChild().getTextContent();
         	      option2 = eElement.getFirstChild().getNextSibling().getNextSibling().getNextSibling().
         	    		  getNextSibling().getNextSibling().getFirstChild().getTextContent();
         	      String options[]={option1, option2};
         	      boxes.add(i,new JComboBox<String>(options));
         	      tempPanel.add(boxes.get(i));
         	      dataPanel.add(tempPanel); 
	           }    
	       }catch (Exception e) {
	    	   System.out.println(e.getMessage());
	       }
	    
	    dataPanel.setBorder(BorderFactory.createTitledBorder("Device Data"));
		dataPanel.setLayout(new GridLayout(optionsNames.size(),1));
		componentsPanel.add(dataPanel);
	}
	
	public void addActionListeners(String deviceID, String deviceName) {
		
		 submit.addActionListener(new ConnectionToServer(boxes,optionsNames,deviceID,deviceName,Connect,Disconnect));
		    
		    back.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					
					setVisible(false);
					IoT_Devices_Frame.setVisible(true);
				}
		    });
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
       setVisible(true);
	   IoT_Devices_Frame.setVisible(false);  
	}
}