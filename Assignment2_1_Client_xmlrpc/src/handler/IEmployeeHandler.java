package handler;

public interface IEmployeeHandler {
	public String getEmployees();
	
	public String getEmployee(int id);
	
	public String updateEmployee(String name, String job, double salary);
	
	public String deleteEmployee(int id);
}
