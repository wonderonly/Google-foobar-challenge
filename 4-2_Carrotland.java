/***
Carrotland
==========

The rabbits are free at last, free from that horrible zombie science experiment. They need a happy, safe home, where they can recover. 

You have a dream, a dream of carrots, lots of carrots, planted in neat rows and columns! But first, you need some land. And the only person who's selling land is Farmer Frida. Unfortunately, not only does she have only one plot of land, she also doesn't know how big it is - only that it is a triangle. However, she can tell you the location of the three vertices, which lie on the 2-D plane and have integer coordinates.

Of course, you want to plant as many carrots as you can. But you also want to follow these guidelines: The carrots may only be planted at points with integer coordinates on the 2-D plane. They must lie within the plot of land and not on the boundaries. For example, if the vertices were (-1,-1), (1,0) and (0,1), then you can plant only one carrot at (0,0).

Write a function answer(vertices), which, when given a list of three vertices, returns the maximum number of carrots you can plant.

The vertices list will contain exactly three elements, and each element will be a list of two integers representing the x and y coordinates of a vertex. All coordinates will have absolute value no greater than 1000000000. The three vertices will not be collinear.

Test cases
==========

Inputs:
    (int) vertices = [[2, 3], [6, 9], [10, 160]]
Output:
    (int) 289

Inputs:
    (int) vertices = [[91207, 89566], [-88690, -83026], [67100, 47194]]
Output:
    (int) 1730960165
***/
//-----------------------------------------------------
package com.google.challenges; 

public class Answer {   
    public static int answer(int[][] vertices) { 
        // apply Pick's Theorem: A = i + b/2 -1
        // where A: area of triangle, i: interior points, b: boundary points
        if (vertices == null || vertices.length != 3 || vertices[0].length != 2) {
            return 0;
        }
        int area = (int)Math.round(computeArea(vertices));
        int boundaryPoints = computeBoundaryPoints(vertices);
        int ans = area > 0 ? area - boundaryPoints / 2 + 1 : 0;
        return ans;
    } 
    
    private static double computeArea(int[][] vertices) {
        // A = 1/2 * determine{[x1, y1, 1], [x2, y2, 1], [x3, y3, 1]}
        double x1 = vertices[0][0], y1 = vertices[0][1];
        double x2 = vertices[1][0], y2 = vertices[1][1];
        double x3 = vertices[2][0], y3 = vertices[2][1];
        double a = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        double b = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        double c = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
        //return (x1 * (y2 - y3) - x2 * ( y1 - y3) + x3 * (y1 - y2)) / 2;
    }
    
    private static int computeBoundaryPoints(int[][] vertices) {
        // b = gcd(abs(x2 - x1), abs(y2 - y1)) +
        //     gcd(abs(x3 - x1), abs(y3 - y1)) +
        //     gcd(abs(x3 - x2), abs(y3 - y2))
        int x1 = vertices[0][0], y1 = vertices[0][1];
        int x2 = vertices[1][0], y2 = vertices[1][1];
        int x3 = vertices[2][0], y3 = vertices[2][1];
        int ret = gcd(Math.abs(x1 - x2), Math.abs(y1 - y2)) +
                   gcd(Math.abs(x1 - x3), Math.abs(y1 - y3)) +
                   gcd(Math.abs(x2 - x3), Math.abs(y2 - y3));
        return ret;
    }
    
    private static int gcd(int x, int y) {
        // Euclidean algorithm: gcd(x, y) = gcd(y, x % y)
        if (y == 0) {
            return x;
        } else {
            return gcd(y, x % y);
        }
    }
}
