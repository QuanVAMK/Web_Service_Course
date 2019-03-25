package model;

import java.io.Serializable;

public class Employee implements Serializable  {
	private static final long serialVersionUID = 20190318;
	private int id;
	private String name;
	private String job;
	private double salary;
	
	public Employee(String name, String job, double salary) {
		this.name = name;
		this.job = job;
		this.salary = salary;
	}
	
	// Getters/Setters
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getJob() {
		return this.job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	
	public double getSalary() {
		return this.salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	@Override
	public int hashCode() {
		// Compute the hash of 1st "significant" field (fields that used for comparison in equals() method
		// for every other significant fields, compute the hash code
		int ret = Integer.hashCode(this.id);
		ret = 31 * ret + this.name.hashCode();
		ret = 31 * ret + this.job.hashCode();
		ret = 31 * ret + Double.hashCode(this.salary);
		
		return ret;
	}
	
	@Override
	public boolean equals(Object o) {
		// Check if referencing the same obj
		if (o == this) {
			return true;
		}
		
		// Check if o is instance of Employee, "null instanceof type" also return false
		if (!(o instanceof Employee)) {
			return false;
		}
		
		// type conversion to compare data members.
		Employee other = (Employee) o;
		return (this.id == other.id) 
			&& (this.name.compareTo(other.name) == 0)
			&& (this.job.compareTo(other.job) == 0)
			&& (Double.compare(this.salary, other.salary) == 0);
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder("Employee {");
		strBuilder.append("id=").append(this.id)
			.append(", name=").append(this.name)
			.append(", job=").append(this.job)
			.append(", salary=").append(this.salary)
			.append("}\n");
		return strBuilder.toString();
	}
}
