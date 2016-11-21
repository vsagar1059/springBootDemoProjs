package com.cg.assign01;

import java.util.Comparator;


/**
 * Custom comparator to sort based on Satisfactory level in descending order.
 * @author mageyh
 *
 */
public class SatisfactoryComparator implements Comparator<Item> {

	@Override
	public int compare(Item o1, Item o2) {
		// Sort in descending order.
		return o2.getSatisfactoryLimit() - o1.getSatisfactoryLimit();
	}

}
