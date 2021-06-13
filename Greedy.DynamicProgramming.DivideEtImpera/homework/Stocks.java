package homework;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class StockInfo {
	/**
	 * the stock's value at the moment
	 */
	private final int value;
	/**
	 * the stock's possible minimum value at the end of the year
	 */
	private final int minValue;
	/**
	 * the stock's possible maximum value at the end of the year
	 */
	private final int maxValue;

	public StockInfo(int value, int minValue, int maxValue) {
		this.value = value;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	public int getValue() {
		return value;
	}

	public int getMinValue() {
		return minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

}

public class Stocks {
	/**
	 * number of given stocks
	 */
	private int numberOfStocks;
	/**
	 * Gigel's budget
	 */
	private int budget;
	/**
	 * maximum allowed loss
	 */
	private static int loss;
	/**
	 * list of given stocks
	 */
	private final List<StockInfo> stocks = new ArrayList<>();

	/**
	 * method that uses dynamic programming to get the best profit Gigel can make at the end of the
	 * year with a loss constraint
	 * @param numberOfStocks number of stocks Gigel can choose from
	 * @param budget Gigel's budget
	 * @param stocks stocks' list
	 * @return maximum profit
	 */
	public static int invest(int numberOfStocks, int budget, List<StockInfo> stocks) {
		//initializes the matrix that stores all the best profits
		int[][][] acquisitions = new int[numberOfStocks + 1][budget + 1][loss + 1];
		for (int i = 1; i <= numberOfStocks; i++) {
			for (int j = 1; j <= budget; j++) {
				for (int k = 1; k <= loss; k++) {
					// verifies if there's enough budget
					if (stocks.get(i - 1).getValue() <= j) {
						// verifies if the loss constraint is satisfied
						if (stocks.get(i - 1).getValue() - stocks.get(i - 1).getMinValue() <= k) {
							int newAcquisition = acquisitions[i - 1]
								[j - stocks.get(i - 1).getValue()][k - (stocks.get(i - 1).getValue()
								- stocks.get(i - 1).getMinValue())]
								+ (stocks.get(i - 1).getMaxValue() - stocks.get(i - 1).getValue());

							// if it's better, buys the current stock
							acquisitions[i][j][k] = Math.max(acquisitions[i - 1][j][k],
								newAcquisition);
						} else {
							acquisitions[i][j][k] = acquisitions[i - 1][j][k];
						}
					} else {
						acquisitions[i][j][k] = acquisitions[i - 1][j][k];
					}
				}
			}
		}
		// the maximum best profit will be stored into the last cell of our 3D array
		return acquisitions[numberOfStocks][budget][loss];
	}


	public static void main(String[] args) throws IOException {
		Stocks stock = new Stocks();

		BufferedReader reader = new BufferedReader((new FileReader("stocks.in")));
		final FileWriter writer = new FileWriter("stocks.out");

		String line = reader.readLine();
		String[] info = line.trim().split("\\s+");

		// reads the number of stocks, the given budget and the maximum loss
		stock.numberOfStocks = Integer.parseInt(info[0]);
		stock.budget = Integer.parseInt(info[1]);
		loss = Integer.parseInt(info[2]);

		// reads the stocks' list
		for (int i = 0; i < stock.numberOfStocks; i++) {
			line = reader.readLine();
			info = line.trim().split("\\s+");

			stock.stocks.add(new StockInfo(Integer.parseInt(info[0]), Integer.parseInt(info[1]),
				Integer.parseInt(info[2])));
		}

		// gets the maximum profit Gigel can have at the end of the year
		writer.write(Integer.toString(invest(stock.numberOfStocks, stock.budget, stock.stocks)));

		writer.close();
		reader.close();
	}
}


