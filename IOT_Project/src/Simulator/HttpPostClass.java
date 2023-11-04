package Simulator;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
  
public class HttpPostClass {
	
	 public void iotServerConnect(String connect_state, String state, String deviceName) throws Exception{

	   StringEntity entity = new StringEntity(connect_state);
	
	   HttpClient httpClient = HttpClientBuilder.create().build(); 
	   HttpPost request = new HttpPost("http://localhost:8082/iot_device/connect");
	   request.setEntity(entity);
	   request.setHeader("Content-type", "application/json");
	  
	   HttpResponse response = httpClient.execute(request);
	   
	   if(response.getStatusLine().getStatusCode()==200)
	   {   
		   if(state=="connected")
		   {
			   System.out.println(deviceName+" connected to server\n"); 
		   } 
		   else
		   {
			   System.out.println(deviceName+" disconnected from server\n");
		   }
	   }
	   else
	   {
		   if(state=="connected")
		   {
			   System.out.println("connection to "+deviceName+" failed\n");
		   }
		   else
		   {
			   System.out.println("disconnection from "+deviceName+" failed\n");
		   }
	   }
	}
	  
	public void iotServerUpdate(String payload, String deviceName) throws Exception{
	
	     StringEntity entity = new StringEntity(payload);
	
	     HttpClient httpClient = HttpClientBuilder.create().build(); 
	     HttpPost request = new HttpPost("http://localhost:8082/iot_device/update");
	     request.setEntity(entity);
	     request.setHeader("Content-type", "application/json");
	
	     HttpResponse response = httpClient.execute(request);
	     
	     if(response.getStatusLine().getStatusCode()==200)
	     {
	    	 System.out.println(deviceName+" update succeeded\n");
	     }
	     else
	     {
	    	 System.out.println(deviceName+" update failed with code "+response.getStatusLine().getStatusCode());
	     }
   }
}
