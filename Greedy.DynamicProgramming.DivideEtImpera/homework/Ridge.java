package homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Ridge {
	/**
	 * given number of ridges
	 */
	private int numberOfRidges;
	/**
	 * array that stores every ridge's height and fuel cost of the excavator
	 */
	private int[][] ridges;

	/**
	 * method that gives the best cost by using a dynamic approach which stores the costs in a
	 * 2D array (line number represent the ridge's number and has 3 columns because a ridge can
	 * be excavated once, twice or not at all)
	 * @return the best cost
	 */
	public long dig() {
		final long inf = 10000000000000000L;

		long[][] excavations = new long[numberOfRidges][3];

		// for the first ridge, matrix will store its cost for 0, 1 and 2 diggings
		excavations[0][1] = ridges[0][1];
		excavations[0][2] = 2L * ridges[0][1];

		// go through the rest of the ridges
		for (int i = 1; i < numberOfRidges; i++) {
			// for every ridge calculates the best cost for not digging it at all, digging it
			// once or twice and compares to the previous one
			for (int j = 0; j < 3; j++) {
				long bestCost = inf;

				// verifies if we can still dig the current ridge
				if (ridges[i][0] - j >= 0) {
					// verifies if it's ok to not dig the previous ridge
					if (ridges[i][0] - j != ridges[i - 1][0]) {
						// if so, gets the best cost
						bestCost = Math.min(bestCost, excavations[i - 1][0]);
					}

					// verifies if we can still dig the previous ridge
					if (ridges[i - 1][0] - 1 >= 0) {
						// verifies if it's ok to dig the previous ridge once
						if (ridges[i][0] - j != ridges[i - 1][0] - 1) {
							// if so, gets the best cost
							bestCost = Math.min(bestCost, excavations[i - 1][1]);
						}
					}

					// verifies if we can still dig the previous ridge
					if (ridges[i - 1][0] - 2 >= 0) {
						// verifies if it's ok to dig the previous ridge twice
						if (ridges[i][0] - j != ridges[i - 1][0] - 2) {
							// if so, gets the best cost
							bestCost = Math.min(bestCost, excavations[i - 1][2]);
						}
					}
				}

				// adds the cost of the best solution found
				excavations[i][j] = (long) j * ridges[i][1] + bestCost;
			}
		}

		long min = inf;

		// the minimum cost it's represented by the last line's minimum, after digging every ridge
		for (int i = 0 ; i < 3; i++) {
			min = Math.min(min, excavations[numberOfRidges - 1][i]);
		}

		return min;
	}

	public static void main(String[] args) throws IOException {
		Ridge ridge = new Ridge();
		BufferedReader reader = new BufferedReader((new FileReader("ridge.in")));
		final FileWriter writer = new FileWriter("ridge.out");

		// reads the number of ridges
		String line = reader.readLine();
		ridge.numberOfRidges = Integer.parseInt(line);

		// reads very ridge's height and fuel cost of the excavator
		ridge.ridges = new int[ridge.numberOfRidges][2];
		for (int i = 0; i < ridge.numberOfRidges; i++) {
			line = reader.readLine();
			String[] info = line.trim().split("\\s+");
			ridge.ridges[i][0] = Integer.parseInt(info[0]);
			ridge.ridges[i][1] = Integer.parseInt(info[1]);
		}

		writer.write(Long.toString(ridge.dig()));

		reader.close();
		writer.close();
	}
}