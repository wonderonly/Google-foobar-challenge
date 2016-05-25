/***
Zombit pandemic
===============

The nefarious Professor Boolean is up to his usual tricks. This time he is using social engineering to achieve his twisted goal of infecting all the rabbits and turning them into zombits! Having studied rabbits at length, he found that rabbits have a strange quirk: when placed in a group, each rabbit nudges exactly one rabbit other than itself. This other rabbit is chosen with uniform probability. We consider two rabbits to have socialized if either or both of them nudged the other. (Thus many rabbits could have nudged the same rabbit, and two rabbits may have socialized twice.) We consider two rabbits A and B to belong to the same rabbit warren if they have socialized, or if A has socialized with a rabbit belonging to the same warren as B.

For example, suppose there were 7 rabbits in Professor Boolean's nefarious lab. We denote each rabbit using a number. The nudges may be as follows:

1 nudges 2
2 nudges 1
3 nudges 7
4 nudges 5
5 nudges 1
6 nudges 5
7 nudges 3

This results in the rabbit warrens {1, 2, 4, 5, 6} and {3, 7}.

Professor Boolean realized that by infecting one rabbit, eventually it would infect the rest of the rabbits in the same warren! Unfortunately, due to budget constraints he can only infect one rabbit, thus infecting only the rabbits in one warren. He ponders, what is the expected maximum number of rabbits he could infect?

Write a function answer(n), which returns the expected maximum number of rabbits Professor Boolean can infect given n, the number of rabbits. n will be an integer between 2 and 50 inclusive. Give the answer as a string representing a fraction in lowest terms, in the form "numerator/denominator". Note that the numbers may be large.

For example, if there were 4 rabbits, he could infect a maximum of 2 (when they pair up) or 4 (when they're all socialized), but the expected value is 106 / 27. Therefore the answer would be "106/27".

Test cases
==========

Inputs:
    (int) n = 4
Output:
    (string) "106/27"

Inputs:
    (int) n = 2
Output:
    (string) "2/1"
***/
//------------------------------------------------------
/* This problem is equivalent to "How to calculate the expected maximum tree size in a pseudoforest", see the link:
 * http://math.stackexchange.com/questions/1090498/how-to-calculate-the-expected-maximum-tree-size-in-a-pseudoforest
 * i.e., how to calculate the expected maximum tree size in a randomly generated pseudoforest of NN labelled nodes where  
 * self-loops are not permitted. Empty and single-node trees are also not permitted.
 */
package com.google.challenges; 

import java.util.ArrayList;
import java.util.Arrays;
import java.math.BigInteger;

public class Answer {   
	private static BigInteger[][] binom;
	// the number of pseudoforests with exactly one connected component
	private static BigInteger[] T;

    public static String answer(int n) { 
    	binom = new BigInteger[n + 1][n + 1];
    	T = new BigInteger[n + 1];
        BigInteger denominator = BigInteger.valueOf(n - 1).pow(n);
        BigInteger numerator = computeNumerator(n);
        BigInteger gcd = denominator.gcd(numerator);
        return numerator.divide(gcd) + "/" + denominator.divide(gcd);
    } 
    
    private static BigInteger computeNumerator(int n) { 
        ArrayList<ArrayList<Integer>> partitions = generatePartition(n);
        BigInteger ret = BigInteger.ZERO;
        for (ArrayList<Integer> partition : partitions) {
        	ret = ret.add(partitionNumber(partition, n));
        }
        return ret;
    } 
    
    private static ArrayList<ArrayList<Integer>> generatePartition(int n) {
    	// generate all unique partitions of an integer n
    	// i.e., write n as a sum of positive integers
    	ArrayList<ArrayList<Integer>> partitions = new ArrayList<>();
    	// store the partition in non-increasing order, each value should be larger than 1
    	Integer[] partition = new Integer[n];
    	// the first partition is n itself
    	partition[0] = n;
    	partitions.add(new ArrayList<Integer>(Arrays.asList(Arrays.copyOf(partition, 1))));
    	// if all value are 2, then it can not partition any further
    	int k = 0;
    	while (partition[0] > 2) {
    		// generate next partition
    		int remain = 0;
    		// find the rightmost value which is larger than 2
    		while (partition[k] < 3) {
    			remain += partition[k];
    			k--;
    		}
    		partition[k]--;
    		remain++;
    		// if remain is more, divide it in different values of size partition[k]
    		while (remain > partition[k]) {
    			partition[k + 1] = partition[k];
    			remain -= partition[k];
    			k++;
    		}
    		partition[k + 1] = remain;
    		k++;
    		// ignore all partitions contain value 1
    		if (partition[k] > 1) {
    			partitions.add(new ArrayList<Integer>(Arrays.asList(Arrays.copyOf(partition, k + 1))));
    		}
    	}
    	return partitions;
    }
    
    private static BigInteger partitionNumber(ArrayList<Integer> partition, int n) {
	// check the algorithm at "http://math.stackexchange.com/q/1097730"
    	BigInteger ret = BigInteger.ONE;
    	int max = 0;
    	for (int i = 0; i < partition.size(); i++) {
    		int value = partition.get(i);
    		max = Math.max(max, value);
    		int num = 1;
    		while (i + 1 < partition.size() && value == partition.get(i + 1)) {
    			num++;
    			i++;
    		}
    		for (int j = 0; j < num; j++) {
    			ret = ret.multiply(binomial(n, value)).divide(BigInteger.valueOf(j + 1));
    			n -= value;
    		}
    		ret = ret.multiply(singleTree(value).pow(num));
    	}
    	return ret.multiply(BigInteger.valueOf(max));
    }
    
    private static BigInteger binomial(int n, int k) {
        // Calculate the binomial coefficient for "n choose k"
        BigInteger ret = new BigInteger("1");
        if (k > n) {
            return BigInteger.ZERO;
        } else {
        	if (binom[n][k] != null) {
        		return binom[n][k];
        	}
            if (k > n / 2) {
                k = n - k;
            }
            // (n choose i+1) = (n choose i) * (n - i) / (i + 1)
            for (int i = 0; i < k; i++) {
                ret = ret.multiply(BigInteger.valueOf(n - i)).divide(BigInteger.valueOf(i + 1));
            }
        }
        return binom[n][k] = ret;
    }
    
    private static BigInteger singleTree(int n) {
    	// returns the number of pseudoforests with exactly one connected component
    	// check the sequence "OEIS A001864 / n"
    	if (T[n] != null) {
    		return T[n];
    	}
    	BigInteger ret = BigInteger.ZERO;
    	for (int i = 1; i < n; i++) {
    		ret = ret.add(binomial(n, i).multiply(BigInteger.valueOf(n - i).pow(n - i))
    				 .multiply(BigInteger.valueOf(i).pow(i)));
    	}
    	return T[n] = ret.divide(BigInteger.valueOf(n));
    }
}
