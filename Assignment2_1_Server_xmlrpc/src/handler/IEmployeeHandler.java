package handler;

/*
 * All methods have the same concept: fill the local Hashtable of EmployeeHandler with contents from the disk (deserializeFromDisk),
 * then do operations to the local Hashtable. If the hashtable is modified (updateEmployee, deleteEmployee), it is serialized to disk (serializeToDisk).
 * The location of the file contains in filePath hard coded.
 */
public interface IEmployeeHandler {
	public String getEmployees();
	
	public String getEmployee(int id);
	
	public String updateEmployee(String name, String job, double salary);
	
	public String deleteEmployee(int id);
}
