import org.la4j.Matrix;

class ProjectTest {

    static double[][] matrix_leslie = {{0, 3.5, 1.5, 0.39},
            {0.4, 0, 0, 0},
            {0, 0.6, 0, 0},
            {0, 0, 0.5, 0}};
    
    public double expectedMaxEigenValue = 0.0;

    public int dim = 4;

    boolean leslieMatrix() {
        double[][] matrix_leslie;

        return true;
    }


    public static boolean test_convertToMatrix() {
        return true;

    }


    public static boolean test_getPopulationfromFile() {
        return true;

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