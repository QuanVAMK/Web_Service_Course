package client;


import javax.xml.namespace.QName;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import ws.customer.data.Customer;

public class CustomerRPCClient {
	public static void main(String[] args1) throws AxisFault {

		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		// EndpointReference targetEPR = new
//		EndpointReference targetEPR = new EndpointReference("http://localhost:9081/axis2/services/e1601120_As4_POJO_service");
		EndpointReference targetEPR = new EndpointReference("http://app3.cc.puv.fi/axis2/services/e1601120_As4_POJO_service");
		options.setTo(targetEPR);

		// Data for testing.
		int[] ids = { 34000, 35000, 36000 };
		String[] names = { "Siirus", "Sasha", "Lizzie" };
		float[] shoppinGAmounts = { 123.54f, 320.40f, 58.89f };
		boolean[] priviliged = { true, true, false };
		int[] discountPercentages = { 3, 10, 2 };

		// setCustomer() testing
		QName opName = new QName("http://service.customer.ws", "setCustomer");
		Object[] arg;
		Class[] returnTypes;
		Object[] response;
		for (int i = 0; i < ids.length; i++) {
			Customer customer = new Customer();
			customer.setCustomerName(names[i]);
			customer.setCustomerID(ids[i]);
			customer.setShoppingAmount(shoppinGAmounts[i]);
			customer.setPrivileged(priviliged[i]);
			customer.setDiscountPercentage(discountPercentages[i]);
			arg = new Object[] { customer };
			returnTypes = new Class[] { Integer.class };
			
			// We would call invokeRobust() method if we wouldn't expect any response from the saerver
			// serviceClient.invokeRobust(opSetCustomer, opSetCustomerArgs);
			// We call invokeBlocking() method to get server response
			response = serviceClient.invokeBlocking(opName, arg, returnTypes);
			System.out.println("Number of customers on the server: " + (Integer) response[0]);
		}
		System.out.println("-------------------------------------------");

		// getCustomer() testing
		int id = 34000;
		opName = new QName("http://service.customer.ws", "getCustomer");
		returnTypes = new Class[] { Customer.class };
		arg = new Object[] { id };
		response = serviceClient.invokeBlocking(opName, arg, returnTypes);
		Customer result = (Customer) response[0];
		
		System.out.println("Customer name : " + result.getCustomerName());
		System.out.println("Customer ID : " + result.getCustomerID());
		System.out.println("Shopping amount : " + result.getShoppingAmount());
		System.out.println("Customer is privileged? : " + result.getPrivileged());
		System.out.println("Discount amount : " + result.getDiscount());
		System.out.println("-------------------------------------------");
		
		// getCustomerFromShoppingAmount() testing
		{
			opName = new QName("http://service.customer.ws", "getCustomerFromShoppingAmount");
			returnTypes = new Class[] { Customer[].class };
			float lowerBound = 50.0f;
			float upperBound = 123.54f;
			arg = new Object[] { lowerBound, upperBound };
			response = serviceClient.invokeBlocking(opName, arg, returnTypes);
			
			Customer[] ourResp = (Customer[]) response[0];
			
			for (int i = 0; i < ourResp.length; ++i) {
				Customer cus = ourResp[i];
				if (cus == null) {
					System.out.println("Failure!!");
					continue;
				}
				System.out.println(i + ": " + cus.getCustomerName());
				System.out.println(cus.getCustomerID());
				System.out.println("Shopping amount : " + cus.getShoppingAmount());
				System.out.println("Customer is privileged? : " + cus.getPrivileged());
				System.out.println("Discount amount : " + cus.getDiscount());
			}
		}
		System.out.println("-------------------------------------------");
		
		// writeToDatabase() testing
		{
			opName = new QName("http://service.customer.ws", "writeToDatabase");
			returnTypes = new Class[] { String.class };
			Customer cus = new Customer();
//			cus.setCustomerID(486);
//			cus.setCustomerName("Oliver");
//			cus.setShoppingAmount(75.0f);
//			cus.setPrivileged(true);
//			cus.setDiscountPercentage(7);

			cus.setCustomerID(576);
			cus.setCustomerName("Harry");
			cus.setShoppingAmount(23.0f);
			cus.setPrivileged(false);
			cus.setDiscountPercentage(8);
			arg = new Object[] { cus };
			response = serviceClient.invokeBlocking(opName, arg, returnTypes);
			String ourResp = (String) response[0];
			System.out.println(ourResp);
			System.out.println("-------------------------------------------");
		}
		
		// readAllFromDatabase() testing
		{
			opName = new QName("http://service.customer.ws", "readAllFromDatabase");
			returnTypes = new Class[] { Customer[].class };
			response = serviceClient.invokeBlocking(opName, new Object[] {}, returnTypes);
			Customer[] ourResp = (Customer[]) response[0];
			
			for (int i = 0; i < ourResp.length; ++i) {
				Customer cus = ourResp[i];
				if (cus == null) {
					System.out.println("Failure");
					continue;
				}
				
				System.out.println(i + ": " + cus.getCustomerName());
				System.out.println(cus.getCustomerID());
				System.out.println("Shopping amount : " + cus.getShoppingAmount());
				System.out.println("Customer is privileged? : " + cus.getPrivileged());
				System.out.println("Discount amount : " + cus.getDiscount());
			}
			System.out.println("-------------------------------------------");
		}
		
	}
}