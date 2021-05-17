package processor;

import java.util.*;

public class Main {
    // static variables
    static Scanner sc = new Scanner(System.in);
    static int row1;
    static int col1;
    static int row2;
    static int col2;
    static double scalar;
    static int j;
    static int i;
    static int k;
    static double[][] matrix1;
    static double[][] matrix2;
    static double[][] resultMatrix;

    // static methods
    public static void takeInput1() {
        // taking input for matrix 1
        System.out.println("Enter size of first matrix:");
        row1 = sc.nextInt();
        col1 = sc.nextInt();
        matrix1 = new double[row1][col1];
        System.out.println("Enter first matrix:");
        for (i = 0; i < row1; i++) {
            for (j = 0; j < col1; j++) {
                matrix1[i][j] = sc.nextDouble();
            }
        }
    }

    public static void takeInput2() {
        // taking input for matrix 2
        System.out.println("Enter size of second matrix:");
        row2 = sc.nextInt();
        col2 = sc.nextInt();
        matrix2 = new double[row2][col2];
        System.out.println("Enter second matrix:");
        for (i = 0; i < row2; i++) {
            for (j = 0; j < col2; j++) {
                matrix2[i][j] = sc.nextDouble();
            }
        }
    }

    public static void printMatrix(double[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        for (i = 0; i < row; i++) {
            for (j = 0; j < col; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // addition of matrices
    public static void addMatrices() {
        takeInput1();
        takeInput2();
        resultMatrix = new double[row1][col1];
        // checking the dimensions
        if (row1 == row2 && col1 == col2) {
            for (i = 0; i < row1; i++) {
                for (j = 0; j < col1; j++) {
                    resultMatrix[i][j] = matrix1[i][j] + matrix2[i][j];
                }
            }
            // printing output
            System.out.println("The result is:");
            printMatrix(resultMatrix);
        } else {
            System.out.println("ERROR");
        }
    }

    // scalar multiplication
    public static void scalarMultiplication() {
        takeInput1();
        // taking the scalar
        System.out.println("Enter constant:");
        scalar = sc.nextInt();
        resultMatrix = new double[row1][col1];
        // multiplying with scalar
        for (i = 0; i < row1; i++) {
            for (j = 0; j < col1; j++) {
                resultMatrix[i][j] = scalar * matrix1[i][j];
            }
        }
        printMatrix(resultMatrix);
    }

    // matrix multiplication
    public static void matrixMultiplication() {
        double sum = 0;
        takeInput1();
        takeInput2();
        if (col1 == row2) {
            resultMatrix = new double[row1][col2];
            for (i = 0; i < row1; i++) {
                for (j = 0; j < col2; j++) {
                    for (k = 0; k < row2; k++) {
                        sum += matrix1[i][k] * matrix2[k][j];
                    }
                    resultMatrix[i][j] = sum;
                    sum = 0;
                }
            }
            printMatrix(resultMatrix);
        } else {
            System.out.println("The operation cannot be performed");
        }
    }
    // transpose matrix
    public static void transposeMatrix() {
        int choice;
        System.out.println("Your choice:");
        choice = sc.nextInt();
        takeInput1();
        switch (choice) {
            case 1:
                transposeMain();
                break;
            case 2:
                transposeSide();
                break;
            case 3:
                transposeVertical();
                break;
            case 4:
                transposeHorizontal();
        }
    }

    private static void transposeHorizontal() {
        int split = row1 / 2;
        double temp;
        for (i = 0; i < split; i++) {
            for (j = 0; j < col1; j++) {
                temp = matrix1[i][j];
                matrix1[i][j] = matrix1[row1 - 1 - i][j];
                matrix1[row1 - 1 - i][j] = temp;
            }
        }
        printMatrix(matrix1);
    }

    private static void transposeVertical() {
        int split = col1 / 2;
        double temp;
        for (i = 0; i < split; i++) {
            for (j = 0; j < row1; j++) {
                temp = matrix1[j][i];
                matrix1[j][i] = matrix1[j][col1 - 1 - i];
                matrix1[j][col1 - 1 - i] = temp;
            }
        }
        printMatrix(matrix1);
    }

    private static void transposeSide() {
        resultMatrix = new double[col1][row1];
        for (i = 0; i < col1; i++) {
            for (j = 0; j < row1; j++) {
                resultMatrix[i][j] = matrix1[col1 - j - 1][row1 - i - 1];
            }
        }
        printMatrix(resultMatrix);
    }

    private static void transposeMain() {
        resultMatrix = new double[col1][row1];
        for (i = 0; i < col1; i++) {
            for (j = 0; j < row1; j++) {
                resultMatrix[i][j] = matrix1[j][i];
            }
        }
        printMatrix(resultMatrix);
    }

    // adjoint function
    static void adjoint(double[][] A, double[][] adj) {
        int N = A.length;
        if (N == 1) {
            adj[0][0] = 1;
            return;
        }
        // temp is used to store cofactors of A[][]
        int sign;
        double[][] temp = new double[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // Get cofactor of A[i][j]
                getCofactor(A, temp, i, j, N);

                // sign of adj[j][i] positive if sum of row
                // and column indexes is even.
                sign = ((i + j) % 2 == 0) ? 1 : -1;

                // Interchanging rows and columns to get the
                // transpose of the cofactor matrix
                adj[j][i] = (sign) * (determinantOfMatrix(temp, N - 1));
            }
        }
    }
    // cofactor function
    static void getCofactor(double[][] mat, double[][] temp,
                            int p, int q, int n) {
        int i = 0, j = 0;

        // Looping for each element
        // of the matrix
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                // Copying into temporary matrix
                // only those element which are
                // not in given row and column
                if (row != p && col != q) {
                    temp[i][j++] = mat[row][col];
                    // Row is filled, so increase
                    // row index and reset col index
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }
    // determinant of matrix
    public static double determinantOfMatrix(double[][] mat, int n) {

        double D = 0; // Initialize result
        // Base case : if matrix
        // contains single element
        if (n == 1)
            return mat[0][0];

        // To store cofactors
        double[][] temp = new double[n][n];

        // To store sign multiplier
        int sign = 1;

        // Iterate for each element of first row
        for (int f = 0; f < n; f++) {
            // Getting Cofactor of mat[0][f]
            getCofactor(mat, temp, 0, f, n);
            D += sign * mat[0][f]
                    * determinantOfMatrix(temp, n - 1);

            // terms are to be added
            // with alternate sign
            sign = -sign;
        }
        return D;
    }
    // inverse function
    static void inverse(double[][] A)
    {

        // Find determinant of A[][]
        int N = A.length;
        double det = determinantOfMatrix(A, N);
        if (det == 0)
        {
            System.out.print("Singular matrix, can't find its inverse");
            return;
        }
        // Find adjoint
        double [][]adj = new double[N][N];
        adjoint(A, adj);
        double [][]inverse = new double[N][N];
        // Find Inverse using formula "inverse(A) = adj(A)/det(A)"
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                inverse[i][j] = adj[i][j]/det;
            }
        }
        printMatrix(inverse);
    }
    // main method
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("1. Add matrices\n" +
                    "2. Multiply matrix by a constant\n" +
                    "3. Multiply matrices\n" +
                    "4. Transpose matrix\n" +
                    "5. Calculate a determinant\n" +
                    "6. Inverse matrix\n" +
                    "0. Exit");
            System.out.print("Your choice:");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    addMatrices();
                    break;
                case 2:
                    scalarMultiplication();
                    break;
                case 3:
                    matrixMultiplication();
                    break;
                case 4:
                    transposeMatrix();
                    break;
                case 5:
                    takeInput1();
                    System.out.println(determinantOfMatrix(matrix1, matrix1.length));
                    break;
                case 6:
                    takeInput1();
                    inverse(matrix1);
                    break;
                case 0:
                    break;
            }
        } while (choice != 0);
    }
}
