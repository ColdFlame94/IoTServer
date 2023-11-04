package Simulator;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;
  
@SuppressWarnings("serial")
public class IoT_GUI_Menu extends JFrame {
	
	private JPanel IoT_Devices_Panel;
	private String deviceID[];
	private String title;
	private JButton devices[];
	
	public IoT_GUI_Menu(ArrayList<String> list){
		 		
		IoT_Devices_Panel=new JPanel();
        IoT_Devices_Panel.setBorder(BorderFactory.createTitledBorder("Devices Menu"));
        
        createDevices(list);

        for(int i=0; i<devices.length; i++)
        {
           devices[i].addActionListener(new Device(list.get(i), this, deviceID[i], devices[i].getText())); 
        }
        
        add(IoT_Devices_Panel);
        setTitle("IoT Devices");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(560,100);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false); 
	} 
	
	public void createDevices(ArrayList<String> list){
		
		devices=new JButton [list.size()];
		deviceID=new String[list.size()];
				
	    for(int i=0; i<list.size(); i++)
        { 
	      try{ 
	        	File inputFile = new File(list.get(i));
		        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		        Document doc = dBuilder.parse(inputFile);
		        NodeList nList = doc.getElementsByTagName("Name");
		        Node nNode = nList.item(0);
		        title = nNode.getFirstChild().getNodeValue();  
		        
		        NodeList nList_id = doc.getElementsByTagName("Device_ID");
		        Node nNode_id = nList_id.item(0);
		        deviceID[i] = nNode_id.getFirstChild().getNodeValue();  
		        devices[i]=new JButton(title);
		        IoT_Devices_Panel.add(devices[i]);
		        
	         }catch(Exception e){
	        	 System.out.println(e.getMessage());
	         }
        } 
	}	 	
}