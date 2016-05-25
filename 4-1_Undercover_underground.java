/***
Undercover underground
======================

As you help the rabbits establish more and more resistance groups to fight against Professor Boolean, you need a way to pass messages back and forth.

Luckily there are abandoned tunnels between the warrens of the rabbits, and you need to find the best way to use them. In some cases, Beta Rabbit wants a high level of interconnectedness, especially when the groups show their loyalty and worthiness. In other scenarios the groups should be less intertwined, in case any are compromised by enemy agents or zombits.

Every warren must be connected to every other warren somehow, and no two warrens should ever have more than one tunnel between them. Your assignment: count the number of ways to connect the resistance warrens.

For example, with 3 warrens (denoted A, B, C) and 2 tunnels, there are three distinct ways to connect them:

A-B-C
A-C-B
C-A-B

With 4 warrens and 6 tunnels, the only way to connect them is to connect each warren to every other warren.

Write a function answer(N, K) which returns the number of ways to connect N distinctly labelled warrens with exactly K tunnels, so that there is a path between any two warrens. 

The return value must be a string representation of the total number of ways to do so, in base 10.
N will be at least 2 and at most 20. 
K will be at least one less than N and at most (N * (N - 1)) / 2

Test cases
==========

Inputs:
    (int) N = 2
    (int) K = 1
Output:
    (string) "1"

Inputs:
    (int) N = 4
    (int) K = 3
Output:
    (string) "16"
***/
//--------------------------------------------
public class Answer {   
    private static BigInteger[][] graph;
    
    public static String answer(int N, int K) { 
        graph = new BigInteger[N + 1][K + 1];
        graph[N][K] = count(N, K);
        return graph[N][K].toString();
    }
    
    private static BigInteger count(int N, int K) {
        // return the number of simple connected graphs with K undirected edges and 
        // N distinctly labelled vertices
        if (graph[N][K] != null) {
            return graph[N][K];
        }
        BigInteger ans = new BigInteger("0");
        if (K < N - 1 || K > N * (N - 1) / 2) {
            return graph[N][K] = ans;
        }
        if (K == N - 1) {
            // Cayley's formular: the number of trees on N labelled vertices
            ans = BigInteger.valueOf((long)Math.pow(N, N - 2));
        } else if (K == N * (N - 1) / 2) {
            // complete graph
            ans = BigInteger.valueOf(1);
        } else {
            int M = N * (N - 1) / 2;
            // all possible graphs
            ans = binomial(M, K);
            if (K < M - N + 2) {
                // there may be duplicates or unconnected graphs
                for (int i = 1; i < N; i++) {
                    BigInteger x = binomial(N - 1, i - 1);
                    int y = Math.min(i * (i - 1) / 2, K);
                    for (int j = i - 1; j < y + 1; j++) {
                        // exclude invalid grahs
                        ans = ans.subtract(x.multiply(binomial((N - i) * (N - i -1 ) / 2, K - j))
                                 .multiply(count(i, j)));
                    }
                }
            }
        }
        return graph[N][K] = ans;
    } 
    
    private static BigInteger binomial(int n, int k) {
        // Calculate the binomial coefficient for "n choose k"
        BigInteger ret = new BigInteger("1");
        if (k > n) {
            ret = BigInteger.valueOf(0);
        } else {
            if (k > n / 2) {
                k = n - k;
            }
            // (n choose i+1) = (n choose i) * (n - i) / (i + 1)
            for (int i = 0; i < k; i++) {
                ret = ret.multiply(BigInteger.valueOf(n - i))
                         .divide(BigInteger.valueOf(i + 1));
            }
        }
        return ret;
    }
}
