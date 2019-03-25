package client;

import handlers.IWordOperations;

import java.net.URL;
import java.util.Scanner;

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
			
			IWordOperations wordOperationsInterface= (IWordOperations) clientFactory.newInstance(IWordOperations.class);

			// Find occurrence of a word.
			Scanner scanner = new Scanner(System.in);
			String keyword = scanner.nextLine();
			System.out.println(wordOperationsInterface.findOccurrences(keyword));
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
		}
		
	}
}
