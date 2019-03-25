package client;

import handlers.IActionHandler;

import java.net.URL;
import java.util.Vector;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.apache.xmlrpc.client.util.ClientFactory;

public class Client {
	public static void main(String[] args) {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();

		try {
			config.setServerURL(new URL("http://127.0.0.1:" + Integer.parseInt(args[0]) + "/"));
			XmlRpcClient client = new XmlRpcClient();
			client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
			client.setConfig(config);
			
			// Add this for dynamic proxy
			ClientFactory clientFactory = new ClientFactory(client);
			
			Vector<Integer> numbers = new Vector<Integer>();
			numbers.add(1);	
			numbers.add(3);
			numbers.add(9);
			numbers.add(20);
			numbers.add(10);
			numbers.add(200);
			numbers.add(-5);
			
//			Object[] params = new Object[]{new Integer(0), 
//					new Integer(15), 
//					new Integer(10), 
//					new Integer(25), 
//					new Integer(30), 
//					new Integer(95), 
//					new Integer(67)};
//			Vector numbers = new Vector();
//			numbers.addElement(params);
			
			// Map ActionHandler from server to IActionHandler of client.
			IActionHandler actionHandlerInterface= (IActionHandler) clientFactory.newInstance(IActionHandler.class);
			
//			System.out.println("The minimum value: " + actionHandlerInterface.getMinNum(numbers));
//			System.out.println("The maximum value: " + actionHandlerInterface.getMaxNum(numbers));
//			System.out.println("The average value: " + actionHandlerInterface.getAvgNum(numbers));
			System.out.println(actionHandlerInterface.processNumbers(numbers));
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
}
