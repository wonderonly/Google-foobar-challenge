/***
Breeding like rabbits
=====================

As usual, the zombie rabbits (zombits) are breeding... like rabbits! But instead of following the Fibonacci sequence like all good rabbits do, the zombit population changes according to this bizarre formula, where R(n) is the number of zombits at time n:

R(0) = 1
R(1) = 1
R(2) = 2
R(2n) = R(n) + R(n + 1) + n (for n > 1)
R(2n + 1) = R(n - 1) + R(n) + 1 (for n >= 1)

(At time 2, we realized the difficulty of a breeding program with only one zombit and so added an additional zombit.)

Being bored with the day-to-day duties of a henchman, a bunch of Professor Boolean's minions passed the time by playing a guessing game: when will the zombit population be equal to a certain amount? Then, some clever minion objected that this was too easy, and proposed a slightly different game: when is the last time that the zombit population will be equal to a certain amount? And thus, much fun was had, and much merry was made.

(Not in this story: Professor Boolean later downsizes his operation, and you can guess what happens to these minions.)

Write a function answer(str_S) which, given the base-10 string representation of an integer S, returns the largest n such that R(n) = S. Return the answer as a string in base-10 representation. If there is no such n, return "None". S will be a positive integer no greater than 10^25.

Test cases
==========

Inputs:
    (string) str_S = "7"
Output:
    (string) "4"

Inputs:
    (string) str_S = "100"
Output:
    (string) "None"
***/
//-----------------------------------------------

package com.google.challenges; 
import java.util.*;
import java.math.*;

public class Answer {   
    // Memorize (n, R(n)) in map
	private static HashMap<BigInteger, BigInteger> map = new HashMap<>();

    public static String answer(String str_5) { 
        BigInteger end = new BigInteger(str_5);
        // end = end + 1, for special case, R(7) = 6
        BigInteger odd = search(BigInteger.ZERO, end.add(BigInteger.ONE), end, 1);
        if (odd.compareTo(BigInteger.valueOf(-1)) == 1) {
        	return odd.toString();
        }
        BigInteger even = search(BigInteger.ZERO, end.add(BigInteger.ONE), end, 0);
        if (even.compareTo(BigInteger.valueOf(-1)) == 1) {
        	return even.toString();
        }
        return "None";
    } 
    
    private static BigInteger search(BigInteger start, BigInteger end, BigInteger target, int parity) {
        // binary search to find target
        while (start.compareTo(end) == -1) {
            // note: if start == end, --> mid = end + 1 --> next_end = mid -1 = end
            // it may cause endless loop
        	BigInteger mid = start.add(end).divide(BigInteger.valueOf(2));
        	mid = mid.remainder(BigInteger.valueOf(2)).equals(BigInteger.valueOf(parity)) ? 
        		  mid : mid.add(BigInteger.ONE);
        	BigInteger curr = R(mid);
        	if (curr.equals(target)) {
        		return mid;
        	} else if (curr.compareTo(target) == -1) {
        		start = mid.add(BigInteger.ONE);
        	} else {
        		end = mid.subtract(BigInteger.ONE);
        	}
        }
        // if start >= end, return -1
        return BigInteger.valueOf(-1);
    } 
    
    private static BigInteger R(BigInteger n) {
        // calculate R(n)
        if (map.containsKey(n)) {
        	return map.get(n);
        }
        BigInteger ret = BigInteger.ONE;
        BigInteger two = BigInteger.valueOf(2);
        if (n.equals(two)) {
        	ret = two;
        }
        if (n.compareTo(two) == 1){
        	BigInteger m = n.divide(two);
        	if (n.remainder(two).equals(BigInteger.ZERO)) {
        		ret = R(m).add(R(m.add(BigInteger.ONE))).add(m);
        	} else {
        		ret = R(m.subtract(BigInteger.ONE)).add(R(m)).add(BigInteger.ONE);
        	}
        }
        map.put(n, ret);
        return ret;
    }
}
