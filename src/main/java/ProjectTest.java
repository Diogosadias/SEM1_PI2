import org.la4j.Matrix;

import java.io.FileNotFoundException;
import java.util.List;

class ProjectTest {

    static double[][] matrix_leslie = {{0, 3.5, 1.5, 0.39},
            {0.5, 0, 0, 0},
            {0, 0.8, 0, 0},
            {0, 0, 0.5, 0}};

    static double[][] matrix_leslie_file = {{0.50, 2.40, 1.00, 0.00},
            {0.5, 0, 0, 0},
            {0, 0.8, 0, 0},
            {0, 0, 0.5, 0}};

    static double[][] population = {{20,0,0,0},
            {10,0,0,0},
            {40,0,0,0},
            {30,0,0,0}};

    static Matrix leslie_matrix = Project.convertToMatrix(matrix_leslie_file);




    int t = 0;
    String[] vec={"x","s","f"};
    double sum=100;
    double expectedMaxEigenValue = 0.0;
    int dim = 4;
    List<String> distribution;
    double [] rateofchange;

    public static void main(String[] args) throws FileNotFoundException {
//        System.out.println("Test_LeslieMatrix: ");
//        System.out.println(test_LeslieMatrix(4, matrix_leslie));
//        System.out.println("Test_GetPopulationFromFile: ");
//        System.out.println(test_getPopulationfromFile("src/main/resources/test.txt", 4, population));
//        System.out.println("Test_MatrixWriteFile: ");
//        System.out.println(test_matrixWriteFile("src/main/resources/test.txt", 4, matrix_leslie_file));
//        System.out.println("Test_EigenValue: ");
//        System.out.println(test_eigen_value(matrix_leslie_file, 1.4876));
//        System.out.println("Test_LeslieMatrixFile: ");
//        System.out.println(test_leslieMatrixFile("src/main/resources/test.txt", 4, leslie_matrix));
        System.out.println("Test_DimPopulationinT: ");

    }
    /*
    Alterar valores e verificar se é necessario e qual a melhor maneira de fazer testes unitários para funções void
    public int outputType;
    public String graphTitle;
    public String resultType;
    public String xLine;
    public String yLine;
    public String outputFileName;
    */

    /*public static boolean test_convertToDouble(Matrix leslie_matrix, double[][] matrix_leslie) {
        double [][] test_matrix = Project.convertToDouble(leslie_matrix);
    }*/

    /*public static boolean test_convertToMatrix(double[][] matrix_leslie, Matrix leslie_matrix) {
        Matrix test_matrix = Project.convertToMatrix(matrix_leslie);

        if(test_matrix==leslie_matrix)
            return true;
        else    return false;

    }*/

    public static boolean test_LeslieMatrix(int dim, double[][] matrix_leslie){
        double[][] test_matrix_leslie = Project.LeslieMatrix(dim);

        for (int i = 0; i < test_matrix_leslie.length; i++) {
            for (int j = 0; j < test_matrix_leslie.length; j++) {
                if (test_matrix_leslie[i][j] != matrix_leslie[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean test_getPopulationfromFile(String filename, int dim, double[][] population) throws FileNotFoundException {
        double[][] matrixpop = Project.getPopulationfromFile(filename, dim);

        for (int i = 0; i < matrixpop.length; i++) {
            if (matrixpop[i][0] == population[i][0]) {
                return true;
            }
        }
        return false;
    }


    public static boolean test_matrixWriteFile(String filename,int dim, double[][] matrix_leslie_file) throws FileNotFoundException {
        double[][] test_matrix = Project.MatrixWriteFile(filename, dim);

        for (int i = 0; i < test_matrix.length; i++) {
            for (int j = 0; j < test_matrix.length; j++) {
                if (test_matrix[i][j] != matrix_leslie_file[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean test_leslieMatrixFile(String filename,int dim, Matrix leslie_matrix) throws FileNotFoundException {
        Matrix test_matrix = Project.LeslieMatrixFile(filename, dim);

        for (int i = 0; i < test_matrix.rows(); i++) {
            for (int j = 0; j < test_matrix.columns(); j++) {
                if (test_matrix.get(i, j) != leslie_matrix.get(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean test_eigen_value(double[][]matrix_leslie, double expectedMaxEigenValue) {
        double maxEigenvalue = Project.eigen_value(matrix_leslie);

        System.out.println(maxEigenvalue);

        if (expectedMaxEigenValue == maxEigenvalue)
            return true;
        else
            return false;

    }


    public static boolean test_dimPopulationinT(double[][] matrix_leslie,double[][] population,int t, Matrix leslie_matrix) {
        Matrix test_matrix = Project.dimPopulationinT(matrix_leslie, population, t);


        return true;
    }


    public static boolean test_totaldimPopulation(Matrix leslie_matrix,double sum) {
        double test_sum=Project.totaldimPopulation(leslie_matrix);

        if(test_sum==sum)
            return true;
        else    return false;
    }


    public static boolean test_distriPopulation(double[][] matrix_leslie, double[][] population, int t, List<String> distribution) {
        List<String> test_distribution = Project.distriPopulation(matrix_leslie,population,t);

        if(test_distribution==distribution)
            return true;
        else    return false;
    }


    public static boolean test_rateofchange(double[][] matrix_leslie,double[][] population, int t , double [] rateofchange) {
        double[] test_rate = Project.rateofchange(matrix_leslie,population,t);

        if(test_rate==rateofchange)
            return true;
        else    return false;
    }


    public static boolean test_createGraph(double[][] matrix_leslie, int outputType, String graphTitle,
                                           String resultType, String xLine, String yLine, String outputFileName) {
        return true;
    }


    public static boolean test_gnuplotGraph() {
        return true;
    }


    public static boolean test_startingGnuplot() {
        return true;
    }


    public static boolean test_creatingDataFile() {
        return true;
    }


    public static boolean test_creatingTxtFileGraph() {
        return true;
    }

    public static boolean test_getHeader(int length,String header){
        String test_header = Project.getHeader(length);

        if(header==test_header)
            return true;
        else    return false;
    }

    public static boolean test_order_class(String filename,boolean check) throws FileNotFoundException {
        boolean test_check = Project.order_class(filename);

        if(test_check==check)
            return true;
        else    return false;
    }
}