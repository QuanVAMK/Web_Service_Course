package client;

import handlers.IFileOperations;

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
			config.setServerURL(new URL("http://shell.puv.fi:" + Integer.parseInt(args[0]) + "/"));
			XmlRpcClient client = new XmlRpcClient();
			client.setTransportFactory(new XmlRpcCommonsTransportFactory(client));
			client.setConfig(config);
			
			// Add this for dynamic proxy
			ClientFactory clientFactory = new ClientFactory(client);
			
			IFileOperations fileOperationsInterface= (IFileOperations) clientFactory.newInstance(IFileOperations.class);

			// Scan list of files.
			System.out.println(fileOperationsInterface.getFileList());

			// Read file.
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please insert the name of the file you wish to read: ");
			String fileName = scanner.nextLine();
			System.out.println(fileOperationsInterface.readFromFile(fileName));
			
			// Write file.
			System.out.println("Please insert a string to write/append to the file: ");
			String inputStr = scanner.nextLine();
			System.out.println("Please insert the name of file you wish to write to: ");
			fileName = scanner.nextLine();
			System.out.println(fileOperationsInterface.writeToFile(fileName, inputStr));
			
			scanner.close();			
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
	}
}
