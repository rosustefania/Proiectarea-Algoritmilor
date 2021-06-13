package homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Valley {
	/**
	 * given numbers of ridges
	 */
	private int numberOfRidges;
	/**
	 * array of ridges that need to represent a valley
	 */
	private int[] valley;
	private long time;

	/**
	 * gets minimum element from an array
	 * @param left starting index
	 * @param right ending index
	 * @return the minimum element
	 */
	public long[][] minimumRidge(long left, long right) {
		long[][] minRidge = new long[1][2];
		minRidge[0][0] = 1000000000L;
		minRidge[0][1] = numberOfRidges;

		for (int i = (int) left; i <= right; i++) {
			if (valley[i] < minRidge[0][0]) {
				minRidge[0][1] = i;
				minRidge[0][0] = valley[i];
			}
		}
		return minRidge;
	}

	/**
	 * calculates the time needed to make a decreasing sequnce starting from index left to
	 * index right
	 * @param left left index
	 * @param right right index
	 */
	public void calculateTimeDecreasingSequence(long left, long right) {
		for (int i = (int) left; i < right - 1; i++) {
			if (valley[i + 1] > valley[i]) {
				time += valley[i + 1] - valley[i];
				valley[i + 1] = valley[i];
			}
		}
	}

	/**
	 * calculates the time needed to make an increasing sequnce starting from index left to
	 * index right
	 * @param left left index
	 * @param right right index
	 */
	public void calculateTimeIncreasingSequence(long left, long right) {
		for (int i = (int) right; i > left + 1; i--) {
			if (valley[i] < valley[i - 1]) {
				time += valley[i - 1] - valley[i];
				valley[i - 1] = valley[i];
			}
		}
	}

	public void bestTime(long left, long right) {
		long[][] minRidge = minimumRidge(left, right);

		// the case where the pivot is the first element
		if (minRidge[0][1] == left && left == 0) {
			time = valley[(int) (left + 1)] - minRidge[0][0];
			return;
		}

		// the case where the pivot is the last element
		if (minRidge[0][1] == right && right == numberOfRidges - 1) {
			System.out.println("ultimul");
			time += valley[(int) (right - 1)] - minRidge[0][0];
			return;
		}

		//sums the time
		calculateTimeDecreasingSequence(left, minRidge[0][1]);
		calculateTimeIncreasingSequence(minRidge[0][1], right);


	}

	public static void main(String[] args) throws IOException {
		Valley valley = new Valley();

		BufferedReader reader = new BufferedReader((new FileReader("valley.in")));
		final FileWriter writer = new FileWriter("valley.out");

		// reads the number of ridges
		String line = reader.readLine();
		valley.numberOfRidges = Integer.parseInt(line);

		// reads very ridge's height and fuel cost of the excavator
		valley.valley = new int[valley.numberOfRidges];

		line = reader.readLine();
		String[] info = line.trim().split("\\s+");

		for (int i = 0; i < valley.numberOfRidges; i++) {
			valley.valley[i] = Integer.parseInt(info[i]);
		}

		valley.bestTime(0, valley.numberOfRidges - 1);
		writer.write(Long.toString(valley.time));

		reader.close();
		writer.close();

	}

}

