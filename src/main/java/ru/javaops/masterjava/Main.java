package ru.javaops.masterjava;

import ru.javaops.masterjava.matrix.MainMatrix;
import ru.javaops.masterjava.matrix.MatrixUtil;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    private static final int THREAD_NUMBER = 10;

    private final static ExecutorService executor = Executors.newFixedThreadPool(Main.THREAD_NUMBER);

    public static void main(String[] args) throws Exception {
        System.out.format("Hello MasterJava!");

        int[][] matrixA = {
                {2, 2, 3},
                {1, 2, 3},
                {2, 1, 2}
        };

        int[][] matrixB = {
                {1, 3, 4},
                {2, 4, 3},
                {1, 5, 3}
        };

        int[][] matrixC = MatrixUtil.concurrentMultiply(matrixA, matrixB, executor);
        show(matrixC);

        matrixC = MatrixUtil.singleThreadMultiply(matrixA, matrixB);
        show(matrixC);
    }

    public static void show(int[][] matrixC) {
        System.out.println();
        for (int i = 0; i < matrixC.length; i++) {
            for (int j = 0; j < matrixC[i].length; j++) {
                System.out.print(matrixC[i][j] + " ");
            }
            System.out.println();
        }
    }
}
