package com.cg.assign01;

/**
 * Menu has list of items/dishes that a restaurant can serve for the customer.
 * 
 * @author mageyh
 *
 */
public class Menu {

	public Item[] items = new Item[101];

	public Menu() {
		populateItems();
	}

	public void setItemList(Item[] items) {
		this.items = items;
	}

	public Item[] getItemList() {
		return items;
	}

	public void populateItems() {
		Item item;

		for (int i = 0; i < 100; i++) {
			item = new Item(i, "Dish-" + i);
			items[i] = item;
		}

	}

}
