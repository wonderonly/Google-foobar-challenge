/***
Binary bunnies
==============

As more and more rabbits were rescued from Professor Booleans horrid laboratory, you had to develop a system to track them, since some habitually continue to gnaw on the heads of their brethren and need extra supervision. For obvious reasons, you based your rabbit survivor tracking system on a binary search tree, but all of a sudden that decision has come back to haunt you.

To make your binary tree, the rabbits were sorted by their ages (in days) and each, luckily enough, had a distinct age. For a given group, the first rabbit became the root, and then the next one (taken in order of rescue) was added, older ages to the left and younger to the right. The order that the rabbits returned to you determined the end pattern of the tree, and herein lies the problem.

Some rabbits were rescued from multiple cages in a single rescue operation, and you need to make sure that all of the modifications or pathogens introduced by Professor Boolean are contained properly. Since the tree did not preserve the order of rescue, it falls to you to figure out how many different sequences of rabbits could have produced an identical tree to your sample sequence, so you can keep all the rescued rabbits safe.

For example, if the rabbits were processed in order from [5, 9, 8, 2, 1], it would result in a binary tree identical to one created from [5, 2, 9, 1, 8]. 

You must write a function answer(seq) that takes an array of up to 50 integers and returns a string representing the number (in base-10) of sequences that would result in the same tree as the given sequence.

Test cases
==========

Inputs:
    (int list) seq = [5, 9, 8, 2, 1]
Output:
    (string) "6"

Inputs:
    (int list) seq = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
Output:
    (string) "1"
***/
//--------------------------------------------------------
package com.google.challenges; 
import java.util.*;
import java.math.*;

public class Answer {   
    public static String answer(int[] seq) { 
    	ArrayList<Integer> list = new ArrayList<Integer>();
    	for (int i : seq) {
    		list.add(i);
    	}
        BigInteger ans = count(list);
        return ans.toString();
    } 
    
    private static BigInteger count(ArrayList<Integer> seq) { 
        if (seq == null || seq.size() <= 2) {
            return BigInteger.ONE;
        }
        ArrayList<Integer> large = new ArrayList<>();
        ArrayList<Integer> small = new ArrayList<>();
        for (int i = 1; i < seq.size(); i++) {
            if (seq.get(i) > seq.get(0)) {
                large.add(seq.get(i));
            } else {
                small.add(seq.get(i));
            }
        }
        BigInteger curr = binomial(large.size() + small.size(), large.size());
        return curr.multiply(count(large)).multiply(count(small));
    } 
    
    private static BigInteger binomial(int n, int k) {
        if (k > n) {
            return BigInteger.ZERO;
        }
        BigInteger ret = BigInteger.ONE;
        for (int i = 0; i < k; i++) {
            ret = ret.multiply(BigInteger.valueOf(n - i)).divide(BigInteger.valueOf(i + 1));
        }
        return ret;
    }
}
