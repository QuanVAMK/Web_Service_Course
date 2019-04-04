package ws.customer.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Hashtable;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import ws.customer.data.Customer;

public class CustomerService {
	// Database info
	private static String dbUsername = "e1601120";
	private static String dbPassword = "";
	private static String dbName = "e1601120_customers";
	private static String tableName = "customer";
	private static String url = "jdbc:mysql://mysql.cc.puv.fi:3306/" + dbName;
	
	// Here we define a collection for customers.
	Hashtable<Integer, Customer> customers = new Hashtable<Integer, Customer>();

//	public static void main(String[] args) {
//		System.out.println("Hello!!!!");
//		CustomerService cs = new CustomerService();
//		Customer cus = new Customer();
//		System.out.println(cs.writeToDatabase(cus));
//		Object[] cus2 = cs.readAllFromDatabase();
//		System.out.println("hello2");
//		for (int i = 0; i < cus2.length; ++i) {
//			Customer ourCus = (Customer) cus2[i];
//			if (ourCus == null) {
//				System.out.println("rip");
//				continue;
//			}
//			System.out.println(ourCus.getCustomerName());
//		}
//	}
	
	public int setCustomer(Customer customer) {
		customers.put(customer.getCustomerID(), customer);
		return customers.size();
	}

	public Customer getCustomer(int id) {
		Customer customer = customers.get(id);
		if (customer == null)
			customer = new Customer();
		return customer;
	}

	// Note: SOA doesn't support overriden methods.
	/**
	 * Get all the cusomers in range [lowerBound, upperBound]
	 * @param lowerBound inclusive of lowest shopping amount a customer could have.
	 * @param upperBound inclusive of highest shopping amount a customer could have.
	 * @return a List of Customer obj sent as an array of type Object. Be careful that the list could be empty.
	 */
	public Object[] getCustomerFromShoppingAmount(float lowerBound, float upperBound) {
		List<Customer> ret = new ArrayList<Customer>();
		Iterator<Entry<Integer, Customer>> it = customers.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Integer, Customer> key = it.next();
			if (key.getValue().getShoppingAmount() <= upperBound &&
				key.getValue().getShoppingAmount() >= lowerBound) {
				ret.add(key.getValue());
			}
		}
		return ret.toArray();
	}
	
	/**
	 * write a Customer obj to our database.
	 * @param customer
	 * @return a message informs that the operation was successful, else an exception message.
	 */
	public String writeToDatabase(Customer customer) {
		// Open a connection.
		try (Connection conn = DriverManager.getConnection(CustomerService.url, CustomerService.dbUsername, CustomerService.dbPassword)) {
			// Create preparedStatement obj with specified query.
			String query="INSERT INTO " + tableName + 
					" (customer_id, name, shoppingAmount, privileged, discountPercentage)"
					+ " VALUES (?, ?, ?, ?, ?)";
			PreparedStatement updateCustomer = conn.prepareStatement(query);
			
			// Supply values for preparedStatement param.
			updateCustomer.setInt(1, customer.getCustomerID());
			updateCustomer.setString(2, customer.getCustomerName());
			updateCustomer.setFloat(3, customer.getShoppingAmount());
			updateCustomer.setBoolean(4, customer.getPrivileged());
			updateCustomer.setInt(5, customer.getDiscountPercentage());
			
			// Execute the final query.
			updateCustomer.executeUpdate();
			return "Update successful";
		} catch (SQLException e) {
			return e.getMessage();
		}
	}
	
	/**
	 * Get all customers available from the database. 
	 * @return List of Customer obj sent as array of type Object. Be careful that it would return an empty list if exception thrown.
	 */
	public Object[] readAllFromDatabase() {
		List<Customer> customers = new ArrayList<Customer>();
		// Open a connection.
		try (Connection conn = DriverManager.getConnection(CustomerService.url, CustomerService.dbUsername, CustomerService.dbPassword)) {
			// Create preparedStatement obj with specified query.
			String query = "SELECT * FROM " + CustomerService.tableName;
			PreparedStatement getAllCustomer = conn.prepareStatement(query);
			
			// Execute query, get a ResultSet.
			ResultSet retQuery = getAllCustomer.executeQuery();
			
			// Iterate over ResultSet, get all fields (columns) from each row of data.
			while (retQuery.next()) {
				Customer cus = new Customer();
				cus.setCustomerID(retQuery.getInt(1));
				cus.setCustomerName(retQuery.getString(2));
				cus.setShoppingAmount(retQuery.getFloat(3));
				cus.setPrivileged(retQuery.getBoolean(4));
				cus.setDiscountPercentage(retQuery.getInt(5));
				customers.add(cus);
			}
			return customers.toArray();
		} catch (SQLException e) {
			return customers.toArray();
		} 
	}
}