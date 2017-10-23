package ru.javaops.masterjava;

import ru.javaops.masterjava.matrix.MatrixUtil;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello MasterJava!");

        int[][] matrixA = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 5, 2, 1 }
        };

        int[][] matrixB = {
                { 2, 2, 7 },
                { 4, 8, 6 },
                { 2, 3, 1 }
        };

        int[][] matrixC = MatrixUtil.singleThreadMultiply(matrixA, matrixB);

        System.out.println();
        for (int i = 0; i < matrixC.length; i++) {
            for (int j = 0; j < matrixC[i].length; j++) {
                System.out.print(matrixC[i][j] + " ");
            }
            System.out.println();
        }
    }
}
