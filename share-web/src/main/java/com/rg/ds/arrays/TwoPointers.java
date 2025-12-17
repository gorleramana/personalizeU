/**
 * Two Pointers examples
 */
package com.rg.practice.arrays;

/**
 * Problem Statement:
Given a 1-indexed array of integers numbers that is already sorted in non-decreasing order, find two numbers such that they add up to a specific target number. Return the indices of the two numbers (1-indexed) as an integer array answer of length 2.

You may assume that each input would have exactly one solution and you may not use the same element twice.

You must use only constant extra space.

Example:
text
Input: numbers = [2,7,11,15], target = 9  
Output: [1,2]  
Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2.
 */
/**
 * @author gorle
 */
public class TwoPointers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TwoPointers solution = new TwoPointers();
		int[] result = solution.twoSum(new int[] { 2, 7, 11, 15 }, 9);
		System.out.println("Indices: " + result[0] + ", " + result[1]);

	}

	/**
	 * Other Common Two Pointers Problems: Reverse a String – Leetcode #344
	 * 
	 * Move Zeroes – Leetcode #283
	 * 
	 * Remove Duplicates from Sorted Array – Leetcode #26
	 * 
	 * Container With Most Water – Leetcode #11
	 * 
	 * Valid Palindrome – Leetcode #125
	 * 
	 * 3Sum – Leetcode #15 (uses two pointers within a loop)
	 */
	private int[] twoSum(int[] numbers, int target) {
		int left = 0;
		int right = numbers.length - 1;

		while (left < right) {
			int sum = numbers[left] + numbers[right];

			if (sum == target) {
				// Return 1-based index
				return new int[] { left + 1, right + 1 };
			} else if (sum < target) {
				left++; // Move left pointer to increase sum
			} else {
				right--; // Move right pointer to decrease sum
			}
		}

		// Should never reach here if exactly one solution is guaranteed
		return new int[] { -1, -1 };
	}

}
