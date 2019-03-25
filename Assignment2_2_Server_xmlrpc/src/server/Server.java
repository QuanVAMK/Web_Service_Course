package server;

import java.io.File;
import java.io.IOException;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

//import java.util.Hashtable;
//import java.util.Set;
//import handlers.WordOperations;
//import java.util.Enumeration;

public class Server {
	public static void main(String[] args) {
//		WordOperations wo = new WordOperations();
//		Hashtable<String, File> ourFileList = wo.getFileList();

		// Print a Hashtable through a Set
//		Set<String> keys = ourFileList.keySet();
//		for (String key : keys) {
//			System.out.println(key + " --> " + ourFileList.get(key));
//		}

		// Print a Hashtable through an iterator
//		Iterator<Entry<String, File>> it = ourFileList.entrySet().iterator();
//		while (it.hasNext()) {
//			Entry<String, String> entry = iterator.next();
//			System.out.println("%-10s%5s\n", entry.getKey(), entry.getValue());
//		}
		
//		Hashtable<String, String> ourWordList = wo.getFullWordList();
//		// Print a Hashtable through an enumeration
//		Enumeration<String> keysEnum = ourWordList.keys();
//		String key, value;
//		System.out.printf("%-10s%5s\n", "Word", "Occurrences");
//		while (keysEnum.hasMoreElements()) {
//			key = keysEnum.nextElement();
//			value = ourWordList.get(key);
//			System.out.printf("%-10s%5s\n", key, value);
//		}
	
		if (args.length < 1) {
			System.out.println("Usage: java Server [port]");
			System.exit(-1);
		}

		try {
			// Here we define a mapping of action handlers
			PropertyHandlerMapping phm = new PropertyHandlerMapping();
			
			final String propertyFileLoc = "resources" + File.separator + "WordOperationsMapping.properties";
			// A class loader is an obj responsible for loading classes.
			// Given the name of a class, class loader attempt to locate/generate data that constitutes
			// a definition of the class.
			// getContextClassLoader() retuns the context ClassLoader for this Thread.
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			phm.load(classLoader, propertyFileLoc);
			
			WebServer webServer = new WebServer(Integer.parseInt(args[0]));
			XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();

			// Here we append the handler mapping to the server
			xmlRpcServer.setHandlerMapping(phm);
			
			XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
			serverConfig.setEnabledForExtensions(true);
			serverConfig.setContentLengthOptional(false);

			// Here we start the server using built-in version
			System.out.println("Attempting to start XML-RPC server...");
			// webServer.setParanoid(false);
			webServer.start();
			System.out.println("Server is started at port " + args[0]);
		} catch (XmlRpcException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Now accepting requests. (Terminate the program to stop!)");
	}
}
