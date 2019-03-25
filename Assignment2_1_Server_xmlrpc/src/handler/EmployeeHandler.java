package handler;

import model.Employee;

import java.util.Hashtable;
import java.util.Enumeration;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class EmployeeHandler implements IEmployeeHandler {
	Hashtable<String, Employee> employees;
	private static final String filePath = "/var/lib/tomcat7/webapps/public_files/e1601120_Employee21.txt";
	
	public EmployeeHandler() {
		this.employees = deserializeFromDisk(EmployeeHandler.filePath);
	}
	
	public EmployeeHandler(Hashtable<String, Employee> employees) {
		this.employees = employees;
		serializeToDisk(EmployeeHandler.filePath, this.employees);
	}
	
	@Override
	public String getEmployees() {
		this.employees = deserializeFromDisk(EmployeeHandler.filePath);
		if (this.employees.isEmpty()) return "There are no employees!";
		
		Enumeration<String> keysEnum = this.employees.keys();
		int size = this.employees.size();
		String[] keys = new String[size];
		StringBuilder sb = new StringBuilder();
		
		// Enumeration is in reverse order, i.e., descending order. However, we wish to print them in ascending order!
		for (int i = size - 1; i >= 0 && keysEnum.hasMoreElements(); --i) {
			keys[i] = keysEnum.nextElement();
		}
		for (int i = 0; i < size; ++i) {
			Employee employee = this.employees.get(keys[i]);
			sb.append(employee.toString());			
		}

		return sb.toString();
	}
	
	@Override
	public String getEmployee(int id) {
		this.employees = deserializeFromDisk(EmployeeHandler.filePath);
		if (!this.employees.containsKey("" + id)) {
			return "The employee you requested doesn't exist for retrieving!";
		}
		
		return this.employees.get("" + id).toString();
	}
	
	@Override
	public String updateEmployee(String name, String job, double salary) {
		this.employees = deserializeFromDisk(EmployeeHandler.filePath);
		
		Employee newEmployee = new Employee(name, job, salary);
		
		// See if any id in between is empty
		int i = 1;
		while (this.employees.containsKey("" + i)) {
			++i;
		}
		newEmployee.setId(i);
		this.employees.put("" + newEmployee.getId(), newEmployee);
		
		serializeToDisk(EmployeeHandler.filePath, this.employees);
//		String ret = serializeToDisk(EmployeeHandler.filePath, this.employees);
//		return ret;
		
		return "Update successful!";
	}
	
	@Override
	public String deleteEmployee(int id) {
		this.employees = deserializeFromDisk(EmployeeHandler.filePath);
		if (!this.employees.containsKey("" + id)) {
			return "The employee you requested doesn't exist for deletion!";
		}
		
		Employee removedEmployee = this.employees.remove("" + id);
		
		serializeToDisk(EmployeeHandler.filePath, this.employees);
		
		StringBuilder sb = new StringBuilder("Info of employee you removed: ");
		sb.append(removedEmployee.toString());
		return sb.toString();
	}
	
	public boolean serializeToDisk(String filePath, Hashtable<String, Employee> employees) {
		boolean ret = false;
		
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
			System.out.println("Writing to file...");
			oos.writeObject(employees);
			ret = true;
			System.out.println("Done writing.");
//			return "Done writing";
		} catch (IOException e) {
//			StringBuilder stb = new StringBuilder();
//			e.printStackTrace();
//			for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
//				stb.append(ste);
//			}
//			stb.append(e.getMessage());
//			return stb.toString();
			e.getMessage();
		}
		
		return ret;
	}
	
	// Supress Type-safety warning when casting from obj to Hashtable<String, Employee>
	@SuppressWarnings("unchecked")
	public Hashtable<String, Employee> deserializeFromDisk(String filePath) {
		Hashtable<String, Employee> ret = new Hashtable<String, Employee>();
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
			ret = (Hashtable<String, Employee>)ois.readObject();
		} catch (IOException e) {
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			e.getMessage();
		}
		
		return ret;
	}
	
	// Function used for testing
	public static Hashtable<String, Employee> createTestData() {
		Employee e = new Employee("Harry", "Programmer", 2000.0f);
		e.setId(1);
		Employee e2 = new Employee("John", "Engineer", 1500.0f);
		e2.setId(2);
		Employee e3 = new Employee("Dennis", "Musician", 500.0f);
		e3.setId(3);
		Employee e4 = new Employee("Joseph", "Doctor", 4000.0f);
		e4.setId(4);
		
		Hashtable<String, Employee> ret = new Hashtable<String, Employee>();
		ret.put("" + 1, e);
		ret.put("" + 2, e2);
		ret.put("" + 3, e3);
		ret.put("" + 4, e4);
				
		return ret;
	}
	
}
