import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Knapsack_Pascucci {

	/**
	 * @return The maximum achievable profit of selecting a subset of the elements
	 *         such that the capacity of the knapsack is not exceeded
	 */
	public static List<Integer> worth_List = new ArrayList<Integer>();
	public static List<Integer> weight_List = new ArrayList<Integer>();
	public static int knapsack(int capacity, List<Integer>  weight_list, List<Integer> worth_list) {

		if (weight_list == null || worth_list == null || weight_list.size() != worth_list.size() || capacity < 0)
			throw new IllegalArgumentException("Invalid input");

		final int N = weight_list.size();

// Initialize a table where individual rows represent items
// and columns represent the weight of the knapsack
		int[][] DP = new int[N + 1][capacity + 1];

		for (int i = 1; i <= N; i++) {

// Get the value and weight of the item
			int w = weight_list.get(i-1), v = worth_list.get(i-1);
			//int w = W[i - 1], v = V[i - 1];

			for (int sz = 1; sz <= capacity; sz++) {

// Consider not picking this element
				DP[i][sz] = DP[i - 1][sz];

// Consider including the current element and
// see if this would be more profitable
				if (sz >= w && DP[i - 1][sz - w] + v > DP[i][sz])
					DP[i][sz] = DP[i - 1][sz - w] + v;
			}
		}

		int sz = capacity;
		List<Integer> itemsSelected = new ArrayList<>();

// Using the information inside the table we can backtrack and determine
// which items were selected during the dynamic programming phase. The idea
// is that if DP[i][sz] != DP[i-1][sz] then the item was selected
		for (int i = N; i > 0; i--) {
			if (DP[i][sz] != DP[i - 1][sz]) {
				int itemIndex = i - 1;
				itemsSelected.add(itemIndex);
				sz -= weight_list.get(itemIndex);
				//sz -= W[itemIndex];
			}
		}

// Return the items that were selected
// java.util.Collections.reverse(itemsSelected);
// return itemsSelected;

// Return the maximum profit
		return DP[N][capacity];
	}

	public static void main(String[] args) {
		try {

			List<String> allLines = Files.readAllLines(Paths.get("Shopping_List.txt"));
			for (String line : allLines) {
				String[] factor = line.split("	");
				worth_List.add(Integer.parseInt(factor[0]) * Integer.parseInt(factor[1]) / Integer.parseInt(factor[2]));
				weight_List.add(Integer.parseInt(factor[2]));
			}
			//check 
			System.out.println(worth_List);
			System.out.println(weight_List);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int capacity = 50;
		System.out.println(knapsack(capacity, weight_List, worth_List));
	}
}
