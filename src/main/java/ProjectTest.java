import org.junit.jupiter.api.Test;

class ProjectTest {

    static double[][] matrix_leslie = {{0, 3.5, 1.5, 0.39},
            {0.4, 0, 0, 0},
            {0, 0.6, 0, 0},
            {0, 0, 0.5, 0}};

    public double expectedMaxEigenValue = 0.0;

    @Test
    boolean leslieMatrix() {
        double[][] matrix_leslie;

        return true;
    }

    @Test
     boolean test_convertToMatrix() {
        return true;

    }

    @Test
     boolean test_getPopulationfromFile() {
        return true;

    }

    @Test
     boolean test_matrixWriteFile() {
        return true;
    }

    @Test
    boolean test_leslieMatrixFile() {
        return true;
    }

    @Test
    boolean test_eigen_value(matrix_leslie, double expectedMaxEigenValue) {

        double maxEigenvalue = Project.eigen_value(matrix_leslie);
        if(expectedMaxEigenValue==maxEigenvalue)
            return true;
        else
            return false;

    }

    @Test
    boolean test_dimPopulationinT() {
        return true;
    }

    @Test
     boolean test_totaldimPopulation() {
        return true;
    }

    @Test
     boolean test_distriPopulation() {
        return true;
    }

    @Test
     boolean test_rateofchange() {
        return true;
    }

    @Test
     boolean test_createGraph() {
        return true;
    }

    @Test
     boolean test_gnuplotGraph() {
        return true;
    }

    @Test
    boolean test_startingGnuplot() {
        return true;
    }

    @Test
     boolean test_creatingDataFile() {
        return true;
    }

    @Test
     boolean test_creatingTxtFileGraph() {
        return true;
    }
}