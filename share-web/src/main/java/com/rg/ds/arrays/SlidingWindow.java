/**
 * Sliding window programs
 */
package com.rg.practice.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Problem: Maximum Sum of Subarray of Size K
Statement:
Given an array of integers and a number k, find the maximum sum of a subarray of size k.

Example:

text
Input: arr = [2, 1, 5, 1, 3, 2], k = 3
Output: 9
Explanation: Subarray with maximum sum is [5, 1, 3]
 */
/**
 * @author gorle
 */
public class SlidingWindow {

	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		int[] arr = { 2, 1, 5, 1, 3, 2 };
		System.out.println(maxSum(arr, 3)); // Output: 9
		System.out.println(lengthOfLongestSubstring("sfsfsfljfsafuosjeabclwsdlkjfsf"));//11
		System.out.println(minSubArrayLen(4, arr));
	}

	/**
	 * Other Common Sliding Window Interview Challenges:
	 * 
	 * Longest Substring Without Repeating Characters
	 * 
	 * Minimum Size Subarray Sum
	 * 
	 * Longest Substring with At Most K Distinct Characters
	 * 
	 * Find All Anagrams in a String
	 */
	public static int maxSum(int[] arr, int k) {
		int maxSum = 0, windowSum = 0;

		// Build the first window
		for (int i = 0; i < k; i++) {
			windowSum += arr[i];
		}
		maxSum = windowSum;

		// Slide the window
		for (int end = k; end < arr.length; end++) {
			windowSum += arr[end] - arr[end - k];
			maxSum = Math.max(maxSum, windowSum);
		}
		return maxSum;
	}
	
	/**
	 * Problem: Return the length of the longest substring without duplicate characters.
	 * 
	 * @param s
	 * @return
	 */
	public static int lengthOfLongestSubstring(String s) {
	    Set<Character> set = new HashSet<>();
	    int left = 0, maxLen = 0;
	    for (int right = 0; right < s.length(); right++) {
	        while (set.contains(s.charAt(right))) {
	            set.remove(s.charAt(left++));
	        }
	        set.add(s.charAt(right));
	        maxLen = Math.max(maxLen, right - left + 1);
	    }
	    return maxLen;
	}
	
	/**
	 * Problem: Find the minimal length of a contiguous subarray of which the sum ≥ a given target.
	 * @param target
	 * @param nums
	 * @return
	 */
	public static int minSubArrayLen(int target, int[] nums) {
	    int left = 0, sum = 0, minLen = Integer.MAX_VALUE;
	    for (int right = 0; right < nums.length; right++) {
	        sum += nums[right];
	        while (sum >= target) {
	            minLen = Math.min(minLen, right - left + 1);
	            sum -= nums[left++];
	        }
	    }
	    return (minLen == Integer.MAX_VALUE) ? 0 : minLen;
	}
	
	/**
	 * Problem: Find all start indices of p's anagrams in s.
	 * 
	 * @param s
	 * @param p
	 * @return
	 */
	public static List<Integer> findAnagrams(String s, String p) {
	    List<Integer> result = new ArrayList<>();
	    int[] pCount = new int[26], sCount = new int[26];
	    for (char c : p.toCharArray()) pCount[c - 'a']++;
	    for (int i = 0; i < s.length(); i++) {
	        sCount[s.charAt(i) - 'a']++;
	        if (i >= p.length()) sCount[s.charAt(i - p.length()) - 'a']--;
	        if (Arrays.equals(pCount, sCount)) result.add(i - p.length() + 1);
	    }
	    return result;
	}
	
	/**
	 * Problem: Find the length of the longest substring containing at most K distinct characters.
	 * 
	 * @param s
	 * @param k
	 * @return
	 */
	public static int lengthOfLongestSubstringKDistinct(String s, int k) {
	    Map<Character, Integer> map = new HashMap<>();
	    int left = 0, maxLen = 0;
	    for (int right = 0; right < s.length(); right++) {
	        map.put(s.charAt(right), map.getOrDefault(s.charAt(right), 0) + 1);
	        while (map.size() > k) {
	            map.put(s.charAt(left), map.get(s.charAt(left)) - 1);
	            if (map.get(s.charAt(left)) == 0) map.remove(s.charAt(left));
	            left++;
	        }
	        maxLen = Math.max(maxLen, right - left + 1);
	    }
	    return maxLen;
	}
	
	/**
	 * Problem: Find the maximum value in every sliding window of size k.
	 * 
	 * @param nums
	 * @param k
	 * @return
	 */
	public static int[] maxSlidingWindow(int[] nums, int k) {
	    if (nums.length == 0) return new int[0];
	    int[] res = new int[nums.length - k + 1];
	    Deque<Integer> dq = new LinkedList<>();
	    for (int i = 0; i < nums.length; i++) {
	        if (!dq.isEmpty() && dq.peek() < i - k + 1) dq.poll();
	        while (!dq.isEmpty() && nums[dq.peekLast()] < nums[i]) dq.pollLast();
	        dq.offer(i);
	        if (i >= k - 1) res[i - k + 1] = nums[dq.peek()];
	    }
	    return res;
	}
}
