import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Project {
    public static void main(String[] args) throws IOException {
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

            System.out.println("TESTE");
            System.out.println(eigen_value(leslie));

        }

        //FALTA UM MÉTODO PARA VER A DIMENSÃO DE CADA LINHA NO FICHEIRO PQ NÃO É SUPOSTO PERGUNTAR QUANDO ARGS.LENGTH == 2 E > 2
        // -> SUBSTITUIR NO MÉTODO INTERATIVO E NÃO INTERATIVO COM FICHEIRO

        //Modo interativo com ficheiro: java -jar nome_programa.jar -n nome_ficheiro_entrada.txt
        if (args.length == 2) {
            do {
                System.out.println("Número de grupos etários (dimensão): ");
                dim = in.nextInt();
            } while (dim < 0);

            //Criar Matriz Leslie com ficheiro
            System.out.println("Matriz de Leslie com ficheiro: ");
            System.out.println(LeslieMatrixFile(args[1], dim)); //Testar isto através do terminal
        }

        //Modo não interativo com ficheiro: java -jar nome_programa.jar -t XXX -g Y -e -v -r nome_ficheiro_entrada.txt nome_ficheiro_saida.txt
        if (args.length > 2) {
            String generations_aux = args[1];
            int generations = Integer.parseInt(generations_aux);
            String format_gnuplot_files_aux = args[3];
            int format_gnuplot_files = Integer.parseInt(format_gnuplot_files_aux);

            do {
                System.out.println("Número de grupos etários (dimensão): ");
                dim = in.nextInt();
            } while (dim < 0);

            System.out.println(LeslieMatrixFile(args[args.length - 1], dim)); //TESTAR!!!!!!

            //Só usar isto para imprimir quando tiver os métodos prontos:
            // 1). Calcular valor e vetor próprio;
            // Eu
            // 2). Calcular dimensão da população a cada geração;
            // Tiago
            // 3). Calcular variação da população entre gerações;
            // Tiago

            // Controlar quando tem apenas 1, 2 ou 3 argumentos
            int[] vec = new int[3];
            if (args[4].equals("-e")) {
                vec[0] = 1;
                System.out.println("Valor Próprio: ");
//                eigen_value()
            }
            if (args[5].equals("-v")) {
                vec[1] = 1;
            }
            if (args[6].equals("-r")) {
                vec[2] = 1;
            }

            System.out.println("Matriz de Leslie com ficheiro - modo não interativo: ");
            System.out.println(LeslieMatrixFile(args[7], dim));

//            FileWriter writer = new FileWriter(args[8]); //Para escrever o ficheiro de saída, falta métodos
//            writer.close();
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

    public static double eigen_value(double[][] matrix_leslie) {
        // Criar objeto do tipo Matriz
        Matrix a = new Basic2DMatrix(matrix_leslie);

        //Obtem valores e vetores próprios fazendo "Eigen Decomposition"
        EigenDecompositor eigenD = new EigenDecompositor(a);
        Matrix[] mattD = eigenD.decompose();

        //Converte objeto Matrix (duas matrizes) para array Java
        double[][] matA = mattD[0].toDenseMatrix().toArray();
        double[][] matB = mattD[1].toDenseMatrix().toArray(); //Dá-nos o valor próprio

        double max_eigen_value = -1;

        for (int i = 0; i < matB.length; i++) {
            for (int j = 0; j < matB.length; j++) {
                if (max_eigen_value < matB[i][j]) {
                    max_eigen_value = matB[i][j];
                }
            }
        }

        return max_eigen_value;
    }

    /***
     * Calcular dimensão de População em Determinado momento
     * Parametros : População inicial, taxa de sobrevivencia, taxa de fecundidade e valor de tempo ou
     * Matrix Leslie + Matriz população incial e valor de tempo
     *
     * Output: Valor da dimensão da população em t
     */
    public static Matrix dimPopulationinT(double[][] leslie,double[][] population, int t ){

        //Criação da Matrix em T
        double [][] populationinT = new double[population.length][1];
        Matrix populationinTMatrix = convertToMatrix(populationinT);

        //Conversao em Matrizes para facilitar calculos
        Matrix lesliematrix = convertToMatrix(leslie);
        Matrix populationInicial = convertToMatrix(population);

        populationinTMatrix  = (lesliematrix.power(t)).multiply(populationInicial);

        return populationinTMatrix;
    }

    /***
     * Teste Unitário dimPopulation - AFAZER!!
     */
}
