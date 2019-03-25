package handlers;

import java.util.Vector;

public interface IActionHandler {
	public Integer getMinNum(Vector<Integer> numbers);
	
	public Integer getMaxNum(Vector<Integer> numbers);
	
	public Integer getAvgNum(Vector<Integer> numbers);
	
	public String processNumbers(Vector<Integer> numbers);
}
