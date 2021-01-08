import org.la4j.*;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Project {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        //Modo interativo sem ficheiro
        
        System.out.println("Número de grupos etários (dimensão): ");
        int dim = in.nextInt();
        
        if (args.length == 0) {
//            System.out.println("NÃºmero de geraÃ§Ãµes: ");
//            int ger = in.nextInt();

            //Criar Matriz Leslie

        }
        if (args.length == 2) {

        }

        double[][] leslie = LesliMatrix(dim);
        System.out.printf("%s", convertToMatrix(leslie));
    }

    public static double[][] LesliMatrix (int dim) {
        Scanner in = new Scanner(System.in);
        double[][] leslie_matrix = new double[dim][dim];

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

//        for (int i = 0; i < dim; i++) {
//            for (int j = 0; j < dim; j++) {
//                System.out.println(leslie_matrix[i][j]);
//            }
//        }

        return leslie_matrix;
    }

    public static Matrix convertToMatrix(double[][] leslie) {
        Matrix leslie_matrix = new Basic2DMatrix(leslie);
        return leslie_matrix;
    }
}
