package handlers;

import java.util.Collections;
import java.util.Vector;

public class ActionHandler implements IActionHandler {
	public Integer getMinNum(Vector<Integer> numbers) {
		if (numbers.isEmpty())
			return new Integer(-1);

		Integer min = Collections.min(numbers);
		return min;
	}

	public Integer getMaxNum(Vector<Integer> numbers) {
		if (numbers.isEmpty())
			return new Integer(-1);

		Integer max = Collections.max(numbers);
		return max;
	}

	public Integer getAvgNum(Vector<Integer> numbers) {
		if (numbers.isEmpty())
			return new Integer(-1);

		int sum = 0;
		for (int i = 0; i < numbers.size(); ++i) {
			sum += numbers.get(i);
		}

		int avg = sum / numbers.size();
		return avg;
	}

	public String processNumbers(Vector<Integer> numbers) {
		if (numbers.isEmpty())
			return "The vector you passed in is empty";

		return String
				.format("The min num is: %s\nThe max num is: %s\nThe avg num is: %s\n",
						getMinNum(numbers).toString(), getMaxNum(numbers)
								.toString(), getAvgNum(numbers).toString());
	}
}
