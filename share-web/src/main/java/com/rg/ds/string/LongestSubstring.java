/**
 * 
 */
package com.rg.ds.string;

/**
 * Longest substring repeated 
 * 
 * Input: abcabcbb
 * Output: abc
 */
public class LongestSubstring {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String input = "abcabcbb";
		//String output = input.substring(0,8);//while getting max  substring, you can go to length + 1 index
		String output = "";
		
		for (int i = 0; i < input.length(); i++) {             // start index
            for (int j = i + 1; j <= input.length(); j++) {    // end index (exclusive)
                String sub = input.substring(i, j);

             // skip if shorter than current best
                if (sub.length() <= output.length()) continue;
                
                // check if this substring appears again later in the string
                int nextIndex = input.indexOf(sub, i + 1);
                if (nextIndex != -1) {
                    output = sub;
                }
            }
        }
		System.out.println(output);
	}

}
