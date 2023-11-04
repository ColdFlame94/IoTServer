package Simulator;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<String>devices=new ArrayList<String>();
		devices.add(0, "Devices//Alarm System Config.xml");
		devices.add(1, "Devices//Flood Detector Config.xml");
		devices.add(2, "Devices//Electricity Detector Config.xml");
		
		new IoT_GUI_Menu(devices);
	}
} 



