package ir.sk.algorithm.mathematic;

import ir.sk.helper.complexity.BCR;
import ir.sk.helper.complexity.SpaceComplexity;
import ir.sk.helper.complexity.TimeComplexity;
import ir.sk.helper.paradigm.BruteForce;

/**
 * Matrix library (2D Array maths)
 * @author <a href="sad.keyvanfar@gmail.com">Saeid Keyvanfar</a> on 2/7/2020.
 */
public class Matrix {

    public static final int N = 4;
    public static final int M = 4;
    public static final int P = 4;

    /**
     * return n-by-n identity matrix I
     *
     * @param n
     * @return
     */
    public static double[][] identity(int n) {
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++)
            a[i][i] = 1;
        return a;
    }

    /**
     * This function adds A[][] and B[][], and stores
     *
     * @param a
     * @param b
     * @return
     */
    public static int[][] addMatrix(int a[][], int b[][]) {
        int m = a.length;
        int n = a[0].length;
        int[][] c = new int[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                c[i][j] = a[i][j] + b[i][j];
        }
        return c;
    }

    // return c = a - b
    public static double[][] subtract(double[][] a, double[][] b) {
        int m = a.length;
        int n = a[0].length;
        double[][] c = new double[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] - b[i][j];
        return c;
    }


    /**
     * vector dot product
     * the dot product or scalar product[note 1] is an algebraic operation that takes two equal-length sequences of numbers (usually coordinate vectors), and returns a single number.
     *
     * @param x
     * @param y
     * @return
     */
    @TimeComplexity("O(n)")
    @SpaceComplexity("O(1)")
    public static double dot(double[] x, double[] y) {
        if (x.length != y.length) throw new IllegalArgumentException("Illegal vector dimensions.");
        double sum = 0.0;
        for (int i = 0; i < x.length; i++)
            sum += x[i] * y[i];
        return sum;
    }

    /**
     * Given two square matrices a and b of size n x m each, find their multiplication matrix.
     * @param a
     * @param b
     */
    @TimeComplexity("O(n^3)")
    @BruteForce
    public static int[][] multiplyMatrix(int a[][], int b[][]) {
        int m1 = a.length;
        int n1 = a[0].length;
        int m2 = b.length;
        int n2 = b[0].length;
        if (n1 != m2) throw new IllegalArgumentException("Illegal matrix dimensions.");

        int[][] c = new int[m1][n2];
        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n2; j++)
                for (int k = 0; k < n1; k++) // Compute dot product of row i and column j.
                    c[i][j] += a[i][k] * b[k][j];
        return c;
    }

    /**
     * matrix-vector multiplication (y = A * x)
     *
     * @param a
     * @param x
     * @return
     */
    public static double[] multiply(double[][] a, double[] x) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != n) throw new IllegalArgumentException("Illegal matrix dimensions.");
        double[] y = new double[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                y[i] += a[i][j] * x[j];
        return y;
    }

    // vector-matrix multiplication (y = x^T A)
    public static double[] multiply(double[] x, double[][] a) {
        int m = a.length;
        int n = a[0].length;
        if (x.length != m) throw new IllegalArgumentException("Illegal matrix dimensions.");
        double[] y = new double[n];
        for (int j = 0; j < n; j++)
            for (int i = 0; i < m; i++)
                y[j] += a[i][j] * x[i];
        return y;
    }

    /**
     * the transpose of a matrix is an operator which flips a matrix over its diagonal
     * that is, it switches the row and column indices of the matrix A by producing another matrix, often denoted by AT (among other notations).
     *
     * return C = A^T
     *
     * @param a
     * @return
     */
    @TimeComplexity("O(n * m)")
    @SpaceComplexity("O(n * m)")
    public static int[][] transposeMatrix(int a[][]) {
        int m = a.length;
        int n = a[0].length;
        int[][] c = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                c[j][i] = a[i][j];
            }
        }
        return c;
    }

    /**
     * @param a
     */
    public static void transposeMatrix2(int a[][]) {
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j < a[0].length; j++) {
                int temp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = temp;
            }
        }
    }

    /**
     * a method to rotate the image by 90 degrees
     * <p>
     * we're rotating the matrix by 90 degrees, the easiest way to do this is to implement the rotation in
     * layers. We perform a circular rotation on each layer, moving the top edge to the right edge, the right edge
     * to the bottom edge, the bottom edge to the left edge, and the left edge to the top edge.
     *
     * @param matrix
     * @return
     */
    @BCR(bigOTime = "O(n^2)")
    @TimeComplexity("O(n^2)")
    @SpaceComplexity("O(1)")
    public static boolean rotateMatrix90Degree(int[][] matrix) {
        if (matrix.length == 0 || matrix.length != matrix[0].length) return false;
        int n = matrix.length;
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            for (int i = first; i < last; i++) {
                int offset = i - first;

                int top = matrix[first][i]; //save top
                // left ->top
                matrix[first][i] = matrix[last - offset][first];
                // bottom ->left
                matrix[last - offset][first] = matrix[last][last - offset];
                // right ->bottom
                matrix[last][last - offset] = matrix[i][last];
                // top ->right
                matrix[i][last] = top; // right<-saved top
            }
        }
        return true;

    }

    public static void displayMatrix(int[][] matrix) {
        System.out.print("Result matrix is \n");
        for (int i = 0; i < Matrix.N; i++) {
            for (int j = 0; j < Matrix.M; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.print("\n");
        }
    }
}
