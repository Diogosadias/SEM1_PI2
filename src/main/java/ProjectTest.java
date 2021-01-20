import org.la4j.Matrix;

import java.io.FileNotFoundException;

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

    boolean leslieMatrix() {
        double[][] matrix_leslie;

        return true;
    }


    public static boolean test_convertToDouble(Matrix matrix,double[][]matrix_leslie){ //fazer

        return true;
    }

    public static boolean test_LeslieMatrix(int dim, double[][] matrix_leslie){
        double[][] test_matrix_leslie = Project.LeslieMatrix(dim);

        if(test_matrix_leslie==matrix_leslie)
            return true;
        else    return false;

    }

    public static boolean test_convertToMatrix() {
        return true;

    }


    public static boolean test_getPopulationfromFile(String filename,int dim, double[][] population) throws FileNotFoundException {
        double[][] matrixpop = Project.getPopulationfromFile(filename,dim);

        if(matrixpop==population)
        return true;
        else return false;

    }


    public static boolean test_matrixWriteFile() {
        return true;
    }


    public static boolean test_leslieMatrixFile() {
        return true;
    }


    public static boolean test_eigen_value(double[][]matrix_leslie, double expectedMaxEigenValue) {

        double maxEigenvalue = Project.eigen_value(matrix_leslie);
        if (expectedMaxEigenValue == maxEigenvalue)
            return true;
        else
            return false;

    }


    public static boolean test_dimPopulationinT() {
        return true;
    }


    public static boolean test_totaldimPopulation() {
        return true;
    }


    public static boolean test_distriPopulation() {
        return true;
    }


    public static boolean test_rateofchange() {
        return true;
    }


    public static boolean test_createGraph() {
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
}