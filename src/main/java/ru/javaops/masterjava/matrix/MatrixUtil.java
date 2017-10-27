package ru.javaops.masterjava.matrix;

import ru.javaops.masterjava.service.MailService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

    public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB, ExecutorService executor) throws Exception {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        class MatrixResult {
            private int line;
            private int[] column;

            private MatrixResult(int line, int[] column) {
                this.line = line;
                this.column = column;
            }
        }

        final CompletionService<MatrixResult> completionService = new ExecutorCompletionService<>(executor);

        final int[] columnB = new int[matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            final int raw = i;
            for (int k = 0; k < matrixSize; k++) {
                columnB[k] = matrixB[k][raw];
            }

            completionService.submit(new Callable<MatrixResult>() {
                @Override
                public MatrixResult call() throws Exception {
                    final int[] res = new int[matrixSize];
                    for (int j = 0; j < matrixSize; j++) {
                        int sum = 0;
                        final int rowA[] = matrixA[j];
                        for (int k = 0; k < matrixSize; k++) {
                            sum += rowA[k] * columnB[k];
                        }
                        res[j] = sum;
                    }

                    return new MatrixResult(raw, res);
                }
            });
        }

        for (int i = 0; i < matrixSize; i++) {
            MatrixResult res = completionService.take().get();
            for (int j = 0; j < matrixSize; j++) {
                matrixC[j][res.line] = res.column[j];
            }
        }

        return matrixC;
    }
    
    public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        final int[][] matrixC = new int[matrixSize][matrixSize];

        final int[] column = new int[matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int k = 0; k < matrixSize; k++) {
                column[k] = matrixB[k][i];
            }

            for (int j = 0; j < matrixSize; j++) {
                int sum = 0;
                final int thisRow[] = matrixA[j];
                for (int k = 0; k < matrixSize; k++) {
                    sum += thisRow[k] * column[k];
                }
                matrixC[j][i] = sum;
            }
        }

        return matrixC;
    }

    public static int[][] create(int size) {
        int[][] matrix = new int[size][size];
        Random rn = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = rn.nextInt(10);
            }
        }
        return matrix;
    }

    public static boolean compare(int[][] matrixA, int[][] matrixB) {
        final int matrixSize = matrixA.length;
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (matrixA[i][j] != matrixB[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
