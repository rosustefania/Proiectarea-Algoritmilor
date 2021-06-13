package homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Crypto {
	/**
	 * Gigel's number of computers
	 **/
	private int number;
	/**
	 * number of computers with different mining power
	 */
	private int actualNumber;
	/**
	 * Gigel's budget
	 **/
	private int budget;
	/**
	 * 2D array that stores the computers: the first column represents the power of the given
	 * computer, the second one represents the update's price
	 **/
	private int[][] computers;

	/**
	 * method that gets the minimum mining power per hour
	 *
	 * @return minimum mining power
	 */
	public int getMinimumPower() {
		return computers[0][0];
	}

	/**
	 * method that does the update: raises the mining power with one and updates the remaining
	 * budget
	 *
	 * @param computer the given computer, the line's number in the computers 2D array
	 */
	public void doUpdate(int computer) {
		computers[computer][0] = computers[computer][0] + 1;
		budget -= computers[computer][1];
	}

	/**
	 * method that swaps two given lines in a matrix
	 *
	 * @param matrix given matrix
	 * @param i      first line
	 * @param j      second line
	 */
	public static void swap(int[][] matrix, int i, int j) {
		final int aux1 = matrix[i][0];
		final int aux2 = matrix[i][1];

		matrix[i][0] = matrix[j][0];
		matrix[j][0] = aux1;

		matrix[i][1] = matrix[j][1];
		matrix[j][1] = aux2;
	}

	/**
	 * method that takes a computer as pivot, places the pivot at its correct position in sorted
	 * matrix, and places all the computers with smaller mining power (smaller than pivot)
	 * above the pivot and all the computers with greater mining power under the pivot
	 *
	 * @param data given matrix
	 * @param first first line index
	 * @param last last line index
	 * @return right index
	 */
	public static int partition(int[][] data, int first, int last) {
		int pivot = data[first][0];
		int left = first, right = last;

		while (left < right) {
			// Finds an element bigger than the pivot from the left
			while (data[left][0] <= pivot && left < right) {
				left++;
			}
			// Finds an element smaller than the pivot from the right
			while (data[right][0] > pivot) {
				right--;
			}
			// Swaps the two lines found
			if (left < right) {
				swap(data, left, right);
			}
		}

		// move the pivot element to the middle
		swap(data, first, right);
		return right;
	}

	/**
	 * method that implements quick sort
	 *
	 * @param matrix matrix to be sorted
	 * @param low    starting line
	 * @param high   ending line
	 */
	public static void quickSort(int[][] matrix, int low, int high) {
		if (low < high) {

			int pi = partition(matrix, low, high);

			quickSort(matrix, low, pi - 1);
			quickSort(matrix, pi + 1, high);
		}
	}

	/**
	 * method that buys new computers with the given budget
	 *
	 * @return the maximum number of crypto currencies mined per hour
	 */
	public int buyComputers() {
		// sorts the computers by their mining power
		if (actualNumber != 1) {
			quickSort(computers, 0, actualNumber - 1);

		}
		// gets the minimum mining power
		int minimumPower = getMinimumPower();

		// verifies if Gigel can buy another computer
		while (budget > 0) {
			for (int i = 0; i < actualNumber; i++) {
				// in case it finds a computer that has the minimum mining power
				if (computers[i][0] == minimumPower) {
					//verifies if Gigel has money to buy it
					if (budget - computers[i][1] >= 0) {
						doUpdate(i);
					} else {
						// if he hasn't there are no more updates to be made
						return minimumPower;
					}
				} else {
					break;
				}
			}
			minimumPower++;
		}
		return minimumPower;
	}

	public static void main(String[] args) throws IOException {
		Crypto crypto = new Crypto();
		BufferedReader reader = new BufferedReader((new FileReader("crypto.in")));
		final FileWriter writer = new FileWriter("crypto.out");

		String line = reader.readLine();
		String[] info = line.trim().split("\\s+");

		// reads the number of computers and the given budget
		crypto.number = Integer.parseInt(info[0]);
		crypto.budget = Integer.parseInt(info[1]);

		crypto.computers = new int[crypto.number][2];
		// reads the list of computers
		for (int i = 0; i < crypto.number; i++) {
			line = reader.readLine();
			info = line.trim().split("\\s+");

			// if two consecutive computers have the same mining power, sums their price and stores
			// them as one
			if (i != 0 && crypto.computers[crypto.actualNumber - 1][0]
				== Integer.parseInt(info[0])) {
				crypto.computers[crypto.actualNumber - 1][1] =
					crypto.computers[crypto.actualNumber - 1][1] + Integer.parseInt(info[1]);
			} else {
				crypto.computers[i][0] = Integer.parseInt(info[0]);
				crypto.computers[i][1] = Integer.parseInt(info[1]);
				//writer.write(Integer.toString(crypto.computers[i][0] ));
				crypto.actualNumber++;
			}
		}

		writer.write(Integer.toString(crypto.buyComputers()));

		writer.close();
		reader.close();


	}
}