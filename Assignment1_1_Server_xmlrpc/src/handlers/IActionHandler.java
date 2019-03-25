package handlers;

import java.util.Vector;
/*
 * The Server provides a service that get min/max/avg numbers.
 * Client needs to send a vector of Integer to perform these operations.
 */
public interface IActionHandler {
	public Integer getMinNum(Vector<Integer> numbers);
	
	public Integer getMaxNum(Vector<Integer> numbers);
	
	public Integer getAvgNum(Vector<Integer> numbers);

	public String processNumbers(Vector<Integer> numbers);
}
