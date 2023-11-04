package Simulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class ConnectionToServer implements ActionListener{

	@SuppressWarnings("rawtypes")
	private ArrayList<JComboBox> boxes;
	private ArrayList<String>elements;
	private String deviceID, deviceName;
	private boolean flag;
	 
	public ConnectionToServer(@SuppressWarnings("rawtypes") ArrayList<JComboBox> boxes, ArrayList<String>elements, 
			String deviceID, String deviceName, JButton connect, JButton disconnect){
		
		this.boxes=boxes;
		this.elements=elements;
		this.deviceName=deviceName;
		this.deviceID=deviceID;
		flag=false;
		
		connectOrDisconnect(connect,disconnect);	
	}
	
	public void connectOrDisconnect(JButton connect, JButton disconnect) {
		
		connect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) { 
					
				HttpPostClass http_connect = new HttpPostClass();
				String payload ="{\"device_id\":\""+deviceID+"\",\"connect_state\":\"1\"} ";
				
				try {
					
					http_connect.iotServerConnect(payload,"connected",deviceName);
					flag=true;
					connect.setEnabled(false);
					disconnect.setEnabled(true);
					
				} catch (Exception ex) {
					System.out.println("The server is down");
				}  
			}	 
		 });
		
		disconnect.addActionListener(new ActionListener(){
	
			@Override
			public void actionPerformed(ActionEvent e) {
				
				flag=false;
				
				connect.setEnabled(true);
				disconnect.setEnabled(false);
				
				HttpPostClass http_connect = new HttpPostClass();
				String payload ="{\"device_id\":\""+deviceID+"\",\"connect_state\":\"0\"} ";
				
				try {
					http_connect.iotServerConnect(payload,"disconnected", deviceName);
				} catch (Exception ex) {
					System.out.println("The server is down");
				}
			}	 
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(flag==true)
		{
			int size=boxes.size()+elements.size();
			String allData[]=new String [size];
			
			createJSONArray(allData);
			createJSONMessage(allData);		
		}
		else
		{
			JOptionPane.showMessageDialog(null, "You must connect to the server first.", "Error", 2);
		}	
	}
	
	public void createJSONArray(String arr[]) {
		
		int elementCounter=0, boxesCounter=0;
		
		for(int i=0; i<arr.length; i++)
		{
			if(i%2==0)
			{
				for(int j=elementCounter; j<elements.size();)
				{
					arr[i]=elements.get(j);
					elementCounter++;
					break;
				}
			}
			else
			{
				for(int j=boxesCounter; j<boxes.size();)
				{
					arr[i]=boxes.get(j).getSelectedItem().toString();
					boxesCounter++;
					break;
				}
			}
		}
	}
	
	public void createJSONMessage(String arr[]) {
		
		HttpPostClass http_update = new HttpPostClass();	    
		StringBuilder payload = new StringBuilder();
		    
		payload.append("{\"device_id\":\"" + deviceID + "\"");
	    
	   for (int i=0, j=1; i < arr.length; i+=2, j++)
	    {
	    	payload.append(",\"state_name" + j + "\":\"" + arr[i]);
	    	payload.append("\",\"state" + j + "\":\""  + arr[i+1] + "\"");
	    }
	   
	   payload.append("}");
		    			
	   try {
		    http_update.iotServerUpdate(payload.toString(),deviceName);
		} catch (Exception ex) {
			System.out.println("The server is down");
		} 
	}
}	
