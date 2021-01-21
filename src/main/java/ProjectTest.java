import org.la4j.Matrix;

import java.io.FileNotFoundException;
import java.util.List;

class ProjectTest {

    static double[][] matrix_leslie = {{0, 3.5, 1.5, 0.39},
            {0.4, 0, 0, 0},
            {0, 0.6, 0, 0},
            {0, 0, 0.5, 0}};

    static String filename = "test.txt";

    static double [][] population ={{20,10,40,30}};

    public int t = 0;

    static String[] vec={"f","s","x"};

    public double sum=100;

    public double expectedMaxEigenValue = 0.0;

    public int dim = 4;

    public List<String> distribution ; //Qual é o resultado espectável?

    public double [] rateofchange; //Qual é o resultado espectável?

    static Matrix leslie_matrix = Project.convertToMatrix(matrix_leslie); //Alterar esta inicialização

    /*
    Alterar valores e verificar se é necessario e qual a melhor maneira de fazer testes unitários para funções void
    public int outputType;
    public String graphTitle;
    public String resultType;
    public String xLine;
    public String yLine;
    public String outputFileName;
    */

    public int length ; //Qual o resultado espectável?

    public boolean check=true;

    boolean leslieMatrix() {
        double[][] matrix_leslie;

        return true;
    }


    public static boolean test_convertToDouble(Matrix leslie_matrix,double[][]matrix_leslie){
        double [][] test_matrix = Project.convertToDouble(leslie_matrix);

        if(test_matrix==matrix_leslie)
            return true;
        else    return false;
    }

    public static boolean test_LeslieMatrix(int dim, double[][] matrix_leslie){
        double[][] test_matrix_leslie = Project.LeslieMatrix(dim);

        if(test_matrix_leslie==matrix_leslie)
            return true;
        else    return false;

    }

    public static boolean test_convertToMatrix(double[][] matrix_leslie, Matrix leslie_matrix) {
        Matrix test_matrix = Project.convertToMatrix(matrix_leslie);

        if(test_matrix==leslie_matrix)
            return true;
        else    return false;

    }


    public static boolean test_getPopulationfromFile(String filename,int dim, double[][] population) throws FileNotFoundException {
        double[][] matrixpop = Project.getPopulationfromFile(filename,dim);

        if(matrixpop==population)
        return true;
        else return false;

    }


    public static boolean test_matrixWriteFile(String filename,int dim, double[][] matrix_leslie) throws FileNotFoundException {
        double[][] test_matrix=Project.MatrixWriteFile(filename,dim);

        if (test_matrix==matrix_leslie)
            return true;
        else    return false;
    }


    public static boolean test_leslieMatrixFile(String filename,int dim, Matrix leslie_matrix) throws FileNotFoundException {
        Matrix test_matrix=Project.LeslieMatrixFile(filename,dim);

        if(test_matrix==leslie_matrix)
            return true;
        else    return false;
    }


    public static boolean test_eigen_value(double[][]matrix_leslie, double expectedMaxEigenValue) {

        double maxEigenvalue = Project.eigen_value(matrix_leslie);
        if (expectedMaxEigenValue == maxEigenvalue)
            return true;
        else
            return false;

    }


    public static boolean test_dimPopulationinT(double[][] matrix_leslie,double[][] population,int t, Matrix leslie_matrix) {
        Matrix test_matrix = Project.dimPopulationinT(matrix_leslie,population,t);

        if(test_matrix==leslie_matrix)
            return true;
        else    return false;
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