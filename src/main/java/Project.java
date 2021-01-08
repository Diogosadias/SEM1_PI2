import org.la4j.*;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Project {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        //Modo interativo sem ficheiro
        if (args.length == 0) {
//            System.out.println("Número de gerações: ");
//            int ger = in.nextInt();

            System.out.println("Número de grupos etários (dimensão): ");
            int dim = in.nextInt();

            //Criar Matriz Leslie

        }
        if (args.length == 2) {

        }

//        Double[][] leslie = LesliMatrix(4);
//        System.out.println(leslie);
//        System.out.println(convertToMatrix(leslie));
    }

    public static Double[][] LesliMatrix (int dim) {
        Scanner in = new Scanner(System.in);
        Double[][] leslie_matrix = new Double[dim][dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                leslie_matrix[i][j] = 0.0;
            }
        }

        System.out.println("Introduza a taxa de sobrevivência pela ordem de grupos etários: ");

        for (int i = 0; i < dim - 1; i++) {
            double survival = in.nextDouble();
            leslie_matrix[i + 1][i] = survival;
        }

        System.out.println("Introduza a taxa de fecundidade pela ordem de grupos etários: ");

        for (int j = 0; j < dim; j++) {
            double fecundity = in.nextDouble();
            leslie_matrix[0][j] = fecundity;
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                System.out.println(leslie_matrix[i][j]);
            }
        }

        return leslie_matrix;
    }

//    public static Matrix convertToMatrix(Double[][] matrix) {
//        Matrix leslie_matrix = new Basic2DMatrix(matrix);
//
//        System.out.println(leslie_matrix);
//        return leslie_matrix;
//    }
}
