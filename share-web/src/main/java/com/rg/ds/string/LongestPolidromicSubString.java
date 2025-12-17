/**
 * 
 */
package com.rg.ds.string;

/**
 * Input: s = "babad" Output: "bab"
 */
public class LongestPolidromicSubString {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input = "babad";
		String output = "";

		for (int i = 0; i < input.length(); i++) {
			for (int j = i + 1; j <= input.length(); j++) {
				String sub = input.substring(i, j);

				if (sub.length() <= output.length())
					continue;
				
				
				String reversed = new StringBuilder(sub).reverse().toString();
                if (sub.equals(reversed))
                    output = sub;
			}
		}
		System.out.println("Output: " + output);
		
		//second approach: 
		 System.out.println("Output: " + longestPalindrome(input));
	}
 
	public static String longestPalindrome(String s) {
        if (s == null || s.length() < 2) {
            return s;
        }

        int start = 0;
        int end = 0;

        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);     // odd length
            int len2 = expandAroundCenter(s, i, i + 1); // even length
            int len = Math.max(len1, len2);

            if (len > end - start + 1) {
                start = i - (len - 1) / 2;
                end   = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length()
               && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // when loop breaks, left and right are one step beyond the palindrome
        return right - left - 1;
    }
}
