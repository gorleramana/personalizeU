/**
 * 
 */
package com.rg.ds.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Input: I Am Not A String
 * Output: g ni rtS A toNmAI
 */
public class ReverseSpaceString {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String input = "I Am Not A String";
        System.out.println("Input: " + input);
        
        char[] chars = input.toCharArray();//o(n) space
        int left = 0;
        int right = chars.length - 1;
        //O(n) - time 
        while (left < right) {
            // Skip spaces from left
            while (left < right && chars[left] == ' ') left++;
            // Skip spaces from right
            while (left < right && chars[right] == ' ') right--;
            
            // Swap non-space characters
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
        
        String output = new String(chars);
        System.out.println("Output: " + output);
	}

}
