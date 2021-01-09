import org.la4j.*;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Project {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        int dim = -1;
        //Modo interativo sem ficheiro
        if (args.length == 0) {
            do {
                System.out.println("Número de grupos etários (dimensão): ");
                dim = in.nextInt();
            } while (dim < 0);

            //Criar Matriz Leslie
            double[][] leslie = LeslieMatrix(dim);
            System.out.println("Matriz de Leslie: ");
            Matrix matrix_leslie = convertToMatrix(leslie);
            System.out.println(matrix_leslie);

        }

        //Modo interativo com ficheiro: java -jar nome_programa.jar -n nome_ficheiro_entrada.txt
        if (args.length == 2) {
            do {
                System.out.println("Número de grupos etários (dimensão): ");
                dim = in.nextInt();
            } while (dim < 0);

            //Criar matriz leslie
            LeslieMatrixFile("src/main/resources/test.txt", 4);
        }

        //Modo não interativo com ficheiro
        if (args.length > 2) {

        }
    }

    public static double[][] LeslieMatrix (int dim) {
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

        return leslie_matrix;
    }

    public static Matrix convertToMatrix(double[][] leslie) {
        Matrix leslie_matrix = new Basic2DMatrix(leslie);
        return leslie_matrix;
    }

    public static Matrix LeslieMatrixFile(String filename, int dim) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        String line = scanner.nextLine();
        String[] quantity_population = line.split(",");
        int len_quantity_population = quantity_population.length;
        System.out.println("IMPRIMIR O QUE ESTÁ NO ARRAY BITCHEEEEES");

        for (int i = 0; i < len_quantity_population; i++) {
            quantity_population[i] = quantity_population[i].trim();
            quantity_population[i] = quantity_population[i].substring(4);
            System.out.println(quantity_population[i]);
        }

        System.out.println("Sobrevivência: ");
        line = scanner.nextLine();
        String[] survival = line.split(",");
        int len_survival = survival.length;

        for (int i = 0; i < len_survival; i++) {
            survival[i] = survival[i].trim();
            survival[i] = survival[i].substring(3);
            System.out.println(survival[i]);
        }

        System.out.println("Fecundidade: "); //Dimensão
        line = scanner.nextLine();
        String[] fecundity = line.split(",");
        int len_fecundity = fecundity.length;

        for (int i = 0; i < len_fecundity; i++) {
            fecundity[i] = fecundity[i].trim();
            fecundity[i] = fecundity[i].substring(3);
            System.out.println(fecundity[i]);
        }

        double[][] matrixleslie = new double[len_fecundity][len_fecundity];

        //Sobrevivência:
        for (int i = 0; i < dim - 1; i++) {
            matrixleslie[i + 1][i] = Double.parseDouble(survival[i]);
        }
        //Fecundidade
        for (int j = 0; j < dim; j++) {
            matrixleslie[0][j] = Double.parseDouble(fecundity[j]);
        }

        Matrix matrix = convertToMatrix(matrixleslie);

        return matrix;
    }
}
