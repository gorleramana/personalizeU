/**
 * 
 */
package com.rg.ds.systemdesign;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gorle
 */
public class RestaurantDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Map<Integer, Boolean> tables = new HashMap<>();
		tables.put(2, false);
		tables.put(8, false);
		tables.put(11, false);
		tables.put(4, false);

		System.out.println("Sending 9 customers:");
		assignTableToCustomers(tables, 9);
		
		System.out.println("Sending 1 customers:");
		assignTableToCustomers(tables, 1);
		
		System.out.println("Sending 6 customers:");
		assignTableToCustomers(tables, 6);
		
		System.out.println("Sending 3 customers:");
		assignTableToCustomers(tables, 3);
		
		System.out.println("Sending 5 customers:");
		assignTableToCustomers(tables, 5);
	}

	private static void assignTableToCustomers(Map<Integer, Boolean> tables, int noOfCustomers) {
		int min = 100;
		int diff = 0;
		int rotate = 0;
		for (Map.Entry<Integer, Boolean> map : tables.entrySet()) {
			rotate++;
			if (map.getKey() >= noOfCustomers && !map.getValue()) {
				diff = map.getKey() - noOfCustomers;
				if (diff > 0 && diff < min) {
					min = diff;

				}
			}

			if (rotate == tables.size()) {
				if(min >= 0 && min != 100) {
					tables.put(map.getKey(), true);
					System.out
							.println("Customers group: " + noOfCustomers + " has been assigned to table: " + map.getKey());
				} else {
					System.out.println("Tables are not available, Customer group is waiting");
				}
				
			} 
		}
	}
}
