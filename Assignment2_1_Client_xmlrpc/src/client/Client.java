package client;

import handler.IEmployeeHandler;

import java.util.Scanner;

import java.net.URL;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.apache.xmlrpc.client.XmlRpcCommonsTransportFactory;
import org.apache.xmlrpc.client.util.ClientFactory;

public class Client {
	public static void main(String[] args) {
		try {
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			// config.setServerURL(new
			// URL("http://localhost:8080/mg_XmlRpcServlet/xmlrpc"));
			config.setServerURL(new URL("http://app3.cc.puv.fi/Assignment2_1_Server_xmlrpc/xmlrpc"));
			XmlRpcClient client = new XmlRpcClient();
//			client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
			client.setConfig(config);

			// In the following we use dynamic proxy
			ClientFactory clientFactory = new ClientFactory(client);
			IEmployeeHandler serviceHandler = (IEmployeeHandler) clientFactory.newInstance(IEmployeeHandler.class);
			
//			System.out.println(serviceHandler.updateEmployee("Harry", "CTO", 2002.0f));
//			System.out.println(serviceHandler.updateEmployee("Jack", "Streamer", 3500.0f));
//			System.out.println(serviceHandler.updateEmployee("John", "Electrical Engineer", 1567.0f));
			
			System.out.println(serviceHandler.updateEmployee("Jane", "Scientist", 3022.0f));
			
			System.out.println(serviceHandler.getEmployees());
			
			Scanner scanner = new Scanner(System.in);
			System.out.println("id of the employee you wish to delete: ");
			int id = Integer.parseInt(scanner.nextLine());
			System.out.println(serviceHandler.deleteEmployee(id));
			
			System.out.println(serviceHandler.getEmployees());
			System.out.println("search an employee of id: ");
			id = Integer.parseInt(scanner.nextLine());
			System.out.println(serviceHandler.getEmployee(id));
			
			scanner.close();
		}
		catch (Exception e)	{
			System.out.println("Exception: " + e.getMessage());
		}

	}

}
