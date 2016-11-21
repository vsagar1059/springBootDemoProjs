package com.cg.assign01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Customer consumes items from the menu. Typically from the input file. For the
 * given T minutes, customer will start to consume dishes and this will happen
 * until the summation of the time he takes to consume N dishes is less than the
 * given T minutes.
 * 
 * @author mageyh
 *
 */
public class Customer {

	private String customerName = null;
	// Time in seconds
	private int totalTimeToEat;
	// Time in seconds
	private int sumTimeToEatAllItem = 0;

	private Boolean hasCrossedTime = Boolean.FALSE;

	private Menu menu = null;

	public Customer(String customerName, int totalTimeToEat, Menu menu) {
		this.customerName = customerName;
		this.totalTimeToEat = totalTimeToEat * 60;
		this.menu = menu;
		calcNoOfItemsToEat();
	}

	private int calcNoOfItemsToEat() {

		nallaKottiko();
		printAllSatisfactoryLimit();
		return 1;
	}

	private void printAllSatisfactoryLimit() {
		Item[] finalItems = menu.getItemList();

		findMaxSatisfactoryLevel(finalItems);

	}

	/**
	 * Sort the Item array with the help of customized comparator class.
	 * 
	 * Identifies the max satisfactory Level that consumer has experienced with
	 * the particular dish item.
	 * 
	 * @param itemList
	 */
	private void findMaxSatisfactoryLevel(Item[] itemList) {
		Arrays.sort(itemList, new SatisfactoryComparator());
		System.out.print("[ ");
		for (int j = 0; j < itemList.length; j++) {
			System.out.print(itemList[j].getSatisfactoryLimit() + " ");
		}
		System.out.print(" ]");
		System.out.println();
		System.out.println(customerName + " had " + " max satisfaction " + itemList[0].getSatisfactoryLimit()
				+ " for the item '"+ itemList[0].getName()+"' in Menu " );

	}

	/**
	 * This method will read the input data and tokenize the data populate the
	 * Item class to corresponding data members.
	 */
	private void nallaKottiko() {

		BufferedReader br = null;
		FileReader reader = null;
		int itemCount = 0;
		try {
			reader = new FileReader(
					new File("D:\\software\\Eclipse_WS1\\Assign01\\src\\com\\cg\\assign01\\assignment01.txt"));
			br = new BufferedReader(reader);

			String line;
			while ((line = br.readLine()) != null) {

				if (!hasCrossedTime) {
					tokenizeLine(line, itemCount);
					itemCount++;

				} else {
					break;
				}
			}
			// Set only the items that consumer has consumed in the menu.
			menu.setItemList(Arrays.copyOf(menu.getItemList(), itemCount - 1));

		} catch (IOException e) {
			System.err.println("IOException while reading input data.. " + e.getStackTrace());
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				System.err.println("IOException while shuting down resources.. " + ex.getStackTrace());
			}
		}
	}

	/**
	 * This will tokenize each line and will sum the time taken for each dish
	 * and compares with the given input time. It will break out of the loop the
	 * moment the value reaches its the given input time.
	 * 
	 * @param line
	 * @param itemCount
	 */
	private void tokenizeLine(String line, int itemCount) {

		itemCount = itemCount + 1;
		int timeTakenPerDish = 0;
		StringTokenizer tokens = new StringTokenizer(line, " ");

		
		menu.getItemList()[itemCount].setSatisfactoryLimit(Integer.parseInt(tokens.nextToken()));
		if (tokens.hasMoreElements()) {
			timeTakenPerDish = Integer.parseInt(tokens.nextToken());
			menu.getItemList()[itemCount].setTimeTaken(timeTakenPerDish);
		}
		sumTimeToEatAllItem = sumTimeToEatAllItem + timeTakenPerDish;
		if (sumTimeToEatAllItem > totalTimeToEat) {
			hasCrossedTime = Boolean.TRUE;
			return;
		}
		System.out.println(customerName + " had " + menu.getItemList()[itemCount].getName() + " for about "
				+ menu.getItemList()[itemCount].getTimeTaken() + " secs " + " and has got satisfactory level of "
				+ menu.getItemList()[itemCount].getSatisfactoryLimit());
		// System.out.println("sumTimeToEatAllItem ==> "+sumTimeToEatAllItem);

	}

}
