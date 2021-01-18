import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic1DMatrix;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Project {
	
	static String pathGnuplot = "C:\\Program Files\\gnuplot\\bin\\gnuplot";
	static String dataNameFile = "Data.txt";
	static String plotNameFile = "Template.gp";
	static String outputDir = "outputs";
	
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int dim = -1;
        int gen = -1;
        int graph = -1;
        int save = -1;
        String fileName = "";
        
        //Modo interativo sem ficheiro
        if (args.length == 0) {
            do {
                System.out.println("Número de grupos etários (dimensão): ");
                dim = in.nextInt();
                System.out.println("Número de gerações a calcular: ");
                gen = in.nextInt();
            } while (dim < 0 && gen < 0);
            
            //Criar Matriz Leslie
            double[][] leslie = LeslieMatrix(dim);
            System.out.println("Matriz de Leslie: ");
            Matrix matrix_leslie = convertToMatrix(leslie);
            System.out.println(matrix_leslie);
            
            double eigenvalue = eigen_value(leslie);
            System.out.println("Valor Próprio: ");
            System.out.println(eigenvalue);
            
            double [] eigenvector = eigen_vec(leslie);
            System.out.println("Vetor Próprio: ");
            System.out.println(Arrays.toString(eigenvector));
            
            System.out.println("Valores da população Inicial: ");
            double[][] population = new double[dim][1];
            for(int i = 0; i < dim; i++) {
            	population[i][0] = in.nextDouble();
            }
            
            double[] totalPopulationChange = new double[gen+1];
            for(int i = 0; i <= gen; i++) {
	            Matrix populationResult = dimPopulationinT(leslie, population, i);
	            totalPopulationChange[i] = totaldimPopulation(populationResult);
	            System.out.println("Dimensão da população em t = " + i);
	            System.out.println(populationResult);
	            System.out.println("Total da população em t = " + i);
	            System.out.println(totalPopulationChange[i]);
            }
            
            double [] rateOfChange = new double [gen];
            rateOfChange = rateofchange(leslie, population, gen);
            System.out.println("Taxa de variação ao longo dos anos: ");
            for(int i = 0; i < gen; i++) {
            	System.out.println(rateOfChange[i]);
            }
            
            System.out.println("Valor de classes: ");
            double [][] numberOfClasses = new double[gen+1][dim];
            for(int i = 0; i <= gen; i++) {
            	numberOfClasses [i] = dimPopulationinT(leslie, population, i).getColumn(0).toDenseVector().toArray();
            	System.out.println(Arrays.toString(numberOfClasses[i]));
            }
            
            double [] graphResults = new double[gen];
            String graphTitle = "";
            String resulType = "";
            String xLine = "";
            String yLine = "";
            while (graph == -1){
	            System.out.println("Qual dos gráficos deseja gerar: ");
	            System.out.println(" 1-Número total de individuos");
	            System.out.println(" 2-Crescimento da população");
	            System.out.println(" 3-Numero por classe (não normalizado)");
	            System.out.println(" 4-Numero por classe (normalizado)");
	            graph = in.nextInt();
	
	            switch(graph) {
	            case 1:
	            	graphResults = new double[gen+1];
	            	for(int i = 0; i < gen+1; i++) {
	            		graphResults[i] = totalPopulationChange[i];
	            	}
	            	graphTitle = "Número Total De Individuos";
	                resulType = "Número Total De Individuos";
	                xLine = "Momento";
	                yLine = "Dimensão da população";
	            	break;
	            case 2:
	            	for(int i = 0; i < gen; i++) {
	            		graphResults[i] = rateOfChange[i];
	            	}
	            	graphTitle = "Crescimento da população";
	                resulType = "Crescimento da população";
	                xLine = "Momento";
	                yLine = "Variação";
	            	break;
	            case 3:
	            	graphResults = new double[gen+1];
	            	for(int i = 0; i < gen+1; i++) {
	            		graphResults[i] = numberOfClasses[0][i];
	            	}
	            	graphTitle = "Número por Classe (não normalizado)";
	                resulType = "Número por Classe";
	                xLine = "Momento";
	                yLine = "Classe";
	            	break;
	            case 4:
	            	graphResults = new double[gen+1];
	            	for(int i = 0; i < gen+1; i++) {
	            		graphResults[i] = numberOfClasses[0][i];
	            	}
	            	graphTitle = "Número por Classe (normalizado)";
	                resulType = "Número por Classe";
	                xLine = "Momento";
	                yLine = "Classe";
	            	break;
	            default:
	            	System.out.println("Escolha inválida.");
	            	graph = -1;
	            	break;
	            }
            }
            createGraph(graphResults, 0, graphTitle, resulType, xLine, yLine, "");
            
            while (save == -1){
	            System.out.println("Guardar como: ");
	            System.out.println(" 1-png");
	            System.out.println(" 2-txt");
	            System.out.println(" 3-eps");
	            System.out.println(" 0-Sair sem guardar");
	            save = in.nextInt();
	            
	            if(save < 0 || save > 3) {
	            	System.out.println("Escolha inválida.");
	            	save = -1;
	            }
            }
            if(save != 0) {
            	System.out.println("Nome do ficheiro: ");
            	if(in.hasNextLine()) {
            		in.nextLine();
            	}
            	fileName = in.nextLine();
            	
            	String defaultExtension = "";
            	switch (save) {
            	case 1:
            		defaultExtension = ".png";
            		break;
            	case 2:
            		defaultExtension = ".txt";
            		break;
            	case 3:
            		defaultExtension = ".eps";
            		break;
            	}
            	if(!fileName.toLowerCase().endsWith(defaultExtension)) {
            		fileName = fileName + defaultExtension;
            	}
            	createGraph(graphResults, save, graphTitle, resulType, xLine, yLine, fileName);
            }
            creatingTxtFileGraph(leslie, gen, totalPopulationChange, rateOfChange, numberOfClasses, eigenvalue, eigenvector);
        }

        //FALTA UM MÉTODO PARA VER A DIMENSÃO DE CADA LINHA NO FICHEIRO PQ NÃO É SUPOSTO PERGUNTAR QUANDO ARGS.LENGTH == 2 E > 2
        // -> SUBSTITUIR NO MÉTODO INTERATIVO E NÃO INTERATIVO COM FICHEIRO

        //Modo interativo com ficheiro: java -jar nome_programa.jar -n nome_ficheiro_entrada.txt
        if (args.length == 2) {
            do {
                System.out.println("Número de grupos etários (dimensão): ");
                dim = getdimfromLeslieMatrixFile(args[1]);
                System.out.println(dim); //Imprime mas não pede
                System.out.println("Número de gerações a calcular: ");
                gen = in.nextInt();
            } while (dim < 0 && gen < 0);

            //Criar Matriz Leslie com ficheiro
            System.out.println("Matriz de Leslie com ficheiro: ");
            System.out.println(LeslieMatrixFile(args[1], dim));            
        }

        //Modo não interativo com ficheiro: java -jar nome_programa.jar -t XXX -g Y -e -v -r nome_ficheiro_entrada.txt nome_ficheiro_saida.txt
        if (args.length > 2) {
            String generations_aux = args[1];
            int generations = Integer.parseInt(generations_aux); //gerações a calcular
            String format_gnuplot_files_aux = args[3];
            int format_gnuplot_files = Integer.parseInt(format_gnuplot_files_aux); //formato do ficheiro a executar

            do {
                System.out.println("Número de grupos etários (dimensão): ");
                dim = getdimfromLeslieMatrixFile(args[args.length-2]);
                System.out.println(dim); //Grupos etários
            } while (dim < 0);


            // Verificar quais os calculos a serem pedidos entre( -e,-v e -r)
            int[] vec = new int[3];
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-e")) {
                    vec[0] = 1;
                }
                if (args[i].equals("-v")) {
                    vec[1] = 1;
                }
                if (args[i].equals("-r")) {
                    vec[2] = 1;
                }
            }

            System.out.println("Matriz de Leslie com ficheiro - modo não interativo: ");

            try {
                File file = new File(args[args.length - 1]);
                FileWriter writer = new FileWriter(args[args.length - 1]);
                if (file.createNewFile()) {
                    System.out.println("Ficheiro criado: " + file.getName());
                    writer.write("Gerações: " + generations + "\n");
                    writer.write("\n");
                    writer.write("Formato ficheiro gnuplot: " + format_gnuplot_files + "\n");
                    writer.write("\n");

                    if (vec[0] == 1) {
                        writer.write("Valor Próprio: " + eigen_value(MatrixWriteFile(args[args.length - 2], dim)) + "\n");
                        writer.write("\n");

                    }
                    if (vec[1] == 1) {

                        /*
                        * Refazer para ficar igual a ficheiro de saida
                        * */
                        writer.write("Dimensão da População: " + "\n");
                        //Escrever cabeçalho gerações
                        int i=-1;
                        writer.write("( " );
                        for(i =0;i<dim-1;i++){
                            writer.write(i +", " );
                        }
                        writer.write(""+ i +")" + "\n" );
                        double temp = -1.0;

                        //Começar a escrever linha da dimensão
                        writer.write("( " );
                        for(i=0;i<dim-1;i++){
                            temp = totaldimPopulation(dimPopulationinT((MatrixWriteFile(args[args.length - 2], dim)),(getPopulationfromFile(args[args.length-2],dim)),i));
                            writer.write(temp +", " );
                        }
                        temp = totaldimPopulation(dimPopulationinT((MatrixWriteFile(args[args.length - 2], dim)),(getPopulationfromFile(args[args.length-2],dim)),i));
                        writer.write(temp +") " + "\n" );
                    }
                    if (vec[2] == 1) {

                        /*
                         * Refazer para ficar igual a ficheiro de saida
                         * */
                        writer.write("Variação da População: " + "\n");
                        //Escrever cabeçalho gerações
                        int i=-1;
                        writer.write("( " );
                        for(i =1;i<dim;i++){
                            writer.write(i +", " );
                        }
                        writer.write(""+ i +")" + "\n" );

                        //Função vai receber array de double
                        //Começar a escrever linha da dimensão

                        double [] temp = new double [dim];
                        temp=rateofchange((MatrixWriteFile(args[args.length - 2], dim)),(getPopulationfromFile(args[args.length-2],dim)),dim);
                        writer.write("( " );
                        for(i=0;i<dim-2;i++){
                            writer.write(temp[i] + ", ");
                        }
                        writer.write(temp[i] +") " + "\n" );
                    }
                    writer.close();
                } else {
                    System.out.println("Ficheiro já existente. Será atualizado.");
                    writer.write("Gerações: " + generations + "\n");
                    writer.write("Formato ficheiro gnuplot: " + format_gnuplot_files + "\n");
                    if (vec[0] == 1) {
                        writer.write("Valor Próprio: " + eigen_value(MatrixWriteFile(args[args.length - 2], dim)) + "\n");
                    }
                    if (vec[1] == 1) {
                        writer.write("Dimensão da População: " + "\n");
                        //Escrever cabeçalho gerações
                        int i=-1;
                        writer.write("( " );
                        for(i =0;i<dim-1;i++){
                            writer.write(i +", " );
                            }
                        writer.write(""+ i +")" + "\n" );
                        double temp = -1.0;

                        //Começar a escrever linha da dimensão
                        writer.write("( " );
                        for(i=0;i<dim-1;i++){
                            temp = totaldimPopulation(dimPopulationinT((MatrixWriteFile(args[args.length - 2], dim)),(getPopulationfromFile(args[args.length-2],dim)),i));
                            writer.write(temp +", " );
                        }
                        temp = totaldimPopulation(dimPopulationinT((MatrixWriteFile(args[args.length - 2], dim)),(getPopulationfromFile(args[args.length-2],dim)),i));
                        writer.write(temp +") " + "\n" );
                    }
                    if (vec[2] == 1) {
                        writer.write("Variação da População: " + "\n");
                        //Escrever cabeçalho gerações
                        int i=-1;
                        writer.write("( " );
                        for(i =1;i<dim;i++){
                            writer.write(i +", " );
                        }
                        writer.write(""+ i +")" + "\n" );

                        //Função vai receber array de double
                        //Começar a escrever linha da dimensão

                        double [] temp = new double [dim];
                        temp=rateofchange((MatrixWriteFile(args[args.length - 2], dim)),(getPopulationfromFile(args[args.length-2],dim)),dim);
                        writer.write("( " );
                        for(i=0;i<dim-2;i++){
                            writer.write(temp[i] + ", ");
                        }
                        writer.write(temp[i] +") " + "\n" );
                    }
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Ocorreu um erro ao escrever/criar o ficheiro.");
                e.printStackTrace();
            }
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

    /***
     *
     * @param filename
     * @param dim
     * @return Matrix com a população inicial
     */

    public static double[][] getPopulationfromFile(String filename,int dim) throws FileNotFoundException {
        String[] vec = initial_vec(filename);

        String[] quantity_population = new String[dim];

        Scanner scanner = new Scanner(new File(filename));
        String line = "";

        for (int k = 0; k < vec.length; k++) {
            if (vec[k].equals("x")) {
                line = scanner.nextLine();
                quantity_population = line.split(",");

                for (int i = 0; i < quantity_population.length; i++) {
                    quantity_population[i] = quantity_population[i].trim();
                    quantity_population[i] = quantity_population[i].substring(4);
                }
            }
        }

        double [][] matrixpop = new double[quantity_population.length][1];

        for(int i =0; i<quantity_population.length; i++){
        	matrixpop[i][0] = Double.parseDouble(quantity_population[i]);
        }

        return matrixpop;
    }

    public static double[][] MatrixWriteFile(String filename, int dim) throws FileNotFoundException {
        String[] vec = initial_vec(filename);

        String[] survival = new String[dim];
        String[] fecundity = new String[dim];
        String[] quantity_population = new String[dim];

        Scanner scanner = new Scanner(new File(filename));
        String line = "";

        for (int k = 0; k < vec.length; k++) {
            if (vec[k].equals("x")) {
                line = scanner.nextLine();
                quantity_population = line.split(",");

                for (int i = 0; i < quantity_population.length; i++) {
                    quantity_population[i] = quantity_population[i].trim();
                    quantity_population[i] = quantity_population[i].substring(4);
                }
            } else if (vec[k].equals("s")) {
                line = scanner.nextLine();
                survival = line.split(",");

                for (int i = 0; i < survival.length; i++) {
                    survival[i] = survival[i].trim();
                    survival[i] = survival[i].substring(3);
                }
            } else if (vec[k].equals("f")) {
                line = scanner.nextLine();
                fecundity = line.split(",");

                for (int i = 0; i < fecundity.length; i++) {
                    fecundity[i] = fecundity[i].trim();
                    fecundity[i] = fecundity[i].substring(3);
                }
            }
        }

        double[][] matrixleslie = new double[fecundity.length][fecundity.length];

        //Sobrevivência:
        for (int i = 0; i < dim - 1; i++) {
            matrixleslie[i + 1][i] = Double.parseDouble(survival[i]);
        }
        //Fecundidade
        for (int j = 0; j < dim; j++) {
            matrixleslie[0][j] = Double.parseDouble(fecundity[j]);
        }

        return matrixleslie;
    }

    public static Matrix LeslieMatrixFile(String filename, int dim) throws FileNotFoundException {

        String[] vec = initial_vec(filename);

        String[] survival = new String[0];
        String[] fecundity = new String[0];
        String[] quantity_population = new String[0];

        Scanner scanner = new Scanner(new File(filename));
        String line = "";

        for (int k = 0; k < vec.length; k++) {
            if (vec[k].equals("x")) {
                line = scanner.nextLine();
                System.out.println("Quantidade de população: ");
                quantity_population = line.split(",");

                for (int i = 0; i < quantity_population.length; i++) {
                    quantity_population[i] = quantity_population[i].trim();
                    quantity_population[i] = quantity_population[i].substring(4);

                    System.out.println(quantity_population[i]);
                }
            } else if (vec[k].equals("s")) {
                line = scanner.nextLine();
                System.out.println("Sobrevivência: ");
                survival = line.split(",");

                for (int i = 0; i < survival.length; i++) {
                    survival[i] = survival[i].trim();
                    survival[i] = survival[i].substring(3);
                    System.out.println(survival[i]);
                }
            } else if (vec[k].equals("f")) {
                line = scanner.nextLine();
                System.out.println("Fecundidade: "); //Dimensão
                fecundity = line.split(",");

                for (int i = 0; i < fecundity.length; i++) {
                    fecundity[i] = fecundity[i].trim();
                    fecundity[i] = fecundity[i].substring(3);
                    System.out.println(fecundity[i]);
                }
            }
        }

        double[][] matrixleslie = new double[fecundity.length][fecundity.length];

        //Sobrevivência:
        for (int i = 0; i < dim - 1; i++) {
            matrixleslie[i + 1][i] = Double.parseDouble(survival[i]);
        }
        //Fecundidade
        for (int j = 0; j < dim; j++) {
            matrixleslie[0][j] = Double.parseDouble(fecundity[j]);
        }

        Matrix matrix = convertToMatrix(matrixleslie);

        System.out.println("Matriz de Leslie: ");
        return matrix;
    }

    public static int getdimfromLeslieMatrixFile(String filename) throws FileNotFoundException {

        String[] vec = initial_vec(filename);

        String[] survival = new String[0];
        String[] fecundity = new String[0];
        String[] quantity_population = new String[0];

        Scanner scanner = new Scanner(new File(filename));
        String line = "";

        for (int k = 0; k < vec.length; k++) {
            if (vec[k].equals("x")) {
                line = scanner.nextLine();
                quantity_population = line.split(",");

                for (int i = 0; i < quantity_population.length; i++) {
                    quantity_population[i] = quantity_population[i].trim();
                    quantity_population[i] = quantity_population[i].substring(4);

                }
            } else if (vec[k].equals("s")) {
                line = scanner.nextLine();
                survival = line.split(",");

                for (int i = 0; i < survival.length; i++) {
                    survival[i] = survival[i].trim();
                    survival[i] = survival[i].substring(3);
                }
            } else if (vec[k].equals("f")) {
                line = scanner.nextLine();
                fecundity = line.split(",");

                for (int i = 0; i < fecundity.length; i++) {
                    fecundity[i] = fecundity[i].trim();
                    fecundity[i] = fecundity[i].substring(3);
                }
            }
        }

        return quantity_population.length;
    }

    public static String[] initial_vec(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        String[] vec = new String[3];

        String line = scanner.nextLine();
        String[] firstLine = line.split("0");

        line = scanner.nextLine();
        String[] secondLine = line.split("0");

        line = scanner.nextLine();
        String[] thirdLine = line.split("0");

        vec[0] = firstLine[0];
        vec[1] = secondLine[0];
        vec[2] = thirdLine[0];

        return vec;
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

    public static double[] eigen_vec(double[][] matrix_leslie) {
        int count = 0;

        // Criar objeto do tipo Matriz
        Matrix a = new Basic2DMatrix(matrix_leslie);

        //Obtem valores e vetores próprios fazendo "Eigen Decomposition"
        EigenDecompositor eigenD = new EigenDecompositor(a);
        Matrix[] mattD = eigenD.decompose();

        //Converte objeto Matrix (duas matrizes) para array Java
        double[][] matA = mattD[0].toDenseMatrix().toArray();
        double[][] matB = mattD[1].toDenseMatrix().toArray();

        double max_eigen_value = -1;

        //Faz a contagem para saber em que posição está o valor próprio
        for (int i = 0; i < matB.length; i++) {
            for (int j = 0; j < matB.length; j++) {
                if (max_eigen_value < matB[i][j]) {
                    count++;
                    max_eigen_value = matB[i][j];
                }
            }
        }
        //Matriz A:
        double[] eigen_vec = new double[matA.length];

        if (count > 0) {
            for (int i = 0; i < matA.length; i++) {
                eigen_vec[i] = matA[i][count - 1];
            }
        } else {
            System.out.println("Line for eigen vector not found.");
        }

        return eigen_vec;
    }

    /***
     * Calcular dimensão de População em Determinado momento
     * Parametros : População inicial, taxa de sobrevivencia, taxa de fecundidade e valor de tempo ou
     * Matrix Leslie + Matriz população incial e valor de tempo
     *
     * Output: Valor da dimensão da população em t
     */
    public static Matrix dimPopulationinT(double[][] leslie, double[][] population, int t ){

        //Criação da Matrix em T
        double [][] populationinT = new double[1][population.length];
        Matrix populationinTMatrix = convertToMatrix(populationinT);
    	
        //Conversao em Matrizes para facilitar calculos
        Matrix lesliematrix = convertToMatrix(leslie);
        Matrix populationInicial = convertToMatrix(population);

        populationinTMatrix  = (lesliematrix.power(t)).multiply(populationInicial);
        return populationinTMatrix;
    }

    /***
     * Dimensão da população Reprodutora - Posso ter que multiplicar o valor por 2;
     * Recebe matrix e calcula a sua soma
     */
    public static double totaldimPopulation(Matrix population){
        return population.sum();
    }


    /***
     * Calculo da distribuição da população
     *
     */
    public List<String> distriPopulation(double[][] leslie, double[][] population, int t){

        //Criação da Matrix em T
        double [][] populationinT = new double[population.length][1];
        Matrix populationDistribution = convertToMatrix(populationinT);

        //Conversao em Matrizes para facilitar calculos
        Matrix lesliematrix = convertToMatrix(leslie);
        Matrix populationInicial = convertToMatrix(population);

        List <String> distribution= new ArrayList<>();

        for (int i = 1; i <= t ; i++){

            populationDistribution  = (lesliematrix.power(i)).multiply(populationInicial);

            distribution.add(populationDistribution.toString());

            populationInicial = populationDistribution;
        }

        return distribution;

    }


    /***
     * Variação da população nos entre o inicio e os ano final dado
     * Parametros:População inicial, Matrix leslie e t final
     *
     * Taxa de variação segue a formula população ano t+1/população no ano t
     *
     * Output : taxa de variação ao longo dos anos - Lista de valores entre anos
     */
    
    public static double [] rateofchange(double[][] leslie,double[][] population, int t ){
    	double result [] = new double [t];
    	
    	for(int i = 0; i < t; i++) {
	    	double value1 = 0;
	    	double value2 = 0;
	    	
	    	value1 = totaldimPopulation(dimPopulationinT(leslie,population,i));
	    	value2 = totaldimPopulation(dimPopulationinT(leslie,population,i+1));
	    	
	    	if(value1 == 0) {
	    		result [i] = 0;
	    	} else {
	    		result [i] = value2/value1;
	    	}
    	}
    			
        return result;
    }
    
    public static void createGraph(double[] matrix, int outputType, String graphTitle,
    		String resultType, String xLine, String yLine, String outputFileName) throws IOException {
    	creatingDataFile(matrix);
    	gnuplotGraph(outputType, graphTitle, resultType, xLine, yLine, outputFileName);
    	startingGnuplot();
    }
    
    /***
     * Desenho da representação gráfica da dimensão da populaçãao (total de indíviduos), 
     * a taxa de variação e a evolução da distribuição da população,
     * por classe, ao longo do tempo.
     * 
     * Esta função desenha os gráficos respetivos em formato png, txt e eps.
     * @throws IOException 
     */
    public static void gnuplotGraph(int outputType, String graphTitle, String dataDescription, 
    		String xLine, String yLine, String outputFileName) throws IOException {
    	FileWriter plot = new FileWriter(plotNameFile);
    	String terminalOutput = "";
    	
    	switch(outputType) {
    	case 1:
    		terminalOutput = "png";
    		break;
    	case 2:
    		terminalOutput = "dumb";
    		break;
    	case 3:
    		terminalOutput = "postscript";
    		break;
    	default:
    		terminalOutput = "qt";
    		break;
    	}
    	
    	plot.write(String.format("set terminal %s\n", terminalOutput));
    	if(outputType >= 1 && outputType <= 3) {
    		plot.write(String.format("system \"mkdir %s\"\n", outputDir));
    		plot.write(String.format("set output \"%s/%s\"\n", outputDir, outputFileName));
    	}
    	plot.write(String.format("set title \"%s\"\n", graphTitle));
    	plot.write(String.format("set xlabel \"%s\"\n", xLine));
    	plot.write(String.format("set ylabel \"%s\"\n", yLine));
    	plot.write(String.format("set style data linespoints\n"));
    	plot.write(String.format("plot \"%s\" title \"%s\"\n", dataNameFile, dataDescription));
    	plot.close();
    }
    
    public static void startingGnuplot() throws IOException {
    	String result = String.format("\"%s\" -p \"%s\"", pathGnuplot, plotNameFile);
    	Runtime  rt = Runtime.getRuntime(); 
    	Process prcs = rt.exec(result);
    }
    
    public static void creatingDataFile(double[] matrix) throws IOException {
    	FileWriter data = new FileWriter(dataNameFile);
    	for(int i = 0; i < matrix.length; i++) {
    		data.write(String.format(Locale.US, "%d %.2f\n", i, matrix[i]));
    	}
    	data.close();
    }
    
    public static void creatingTxtFileGraph(double[][] lesliMatrix, int gen, double[] dimPopulation, 
    		double[] rateOfChange, double[][] classes, double eigenvalue, double[] eigenvector) throws IOException {
    	FileWriter result = new FileWriter("TextGraphResult.txt");
    	
    	result.write(String.format("K=%d\n", gen));
    	result.write(String.format("Matriz de Leslie\n"));
    	for(int i = 0; i < lesliMatrix.length; i++) {
    		for(int j = 0; j < lesliMatrix.length; j++) {
    			if(j > 0) {
    				result.write(", ");
    			}
    			result.write(String.format(Locale.US, "%.2f", lesliMatrix[i][j]));
    		}
    		result.write(String.format("\n"));
    	}
    	result.write(String.format("\nNumero total de individuos\n"));
    	result.write(String.format("(t, Nt)\n"));
    	for(int i = 0; i < gen+1; i++) {
    		result.write(String.format(Locale.US, "(%d, %.2f)\n", i, dimPopulation[i]));
    	}
    	result.write(String.format("\n\nCrescimento da população\n"));
    	result.write(String.format("(t, delta_t)\n"));
    	for(int i = 0; i < gen; i++) {
    		result.write(String.format(Locale.US, "(%d, %.2f)\n", i, rateOfChange[i]));
    	}
    	result.write(String.format("\n\nNumero por classe (não normalizado)\n"));
    	result.write(String.format("%s\n", getHeader(lesliMatrix.length)));
    	for(int i = 0; i < gen+1; i++) {
    		result.write(String.format("(%d", i));
    		for(int j = 0; j < lesliMatrix.length; j++) {
    			result.write(String.format(Locale.US, ", %.2f", classes[i][j]));
    		}
    		result.write(String.format(")\n"));
    	}
    	result.write(String.format("\n\nNumero por classe (normalizado)\n"));
    	result.write(String.format("%s\n", getHeader(lesliMatrix.length)));
    	for(int i = 0; i < gen+1; i++) {
    		result.write(String.format("(%d", i));
    		for(int j = 0; j < lesliMatrix.length; j++) {
    			result.write(String.format(Locale.US, ", 100*%.2f/%.2f", classes[i][j], dimPopulation[i]));
    		}
    		result.write(String.format(")\n"));
    	}
    	result.write(String.format("\n\nMaior valor próprio e vetor associado\n"));
    	result.write(String.format(Locale.US, "lambda=%.4f\n", eigenvalue));
    	result.write(String.format("vetor proprio associado=("));
    	for(int i = 0; i < eigenvector.length; i++) {
    		if(i > 0) {
    			result.write(", ");
    		}
    		result.write(String.format(Locale.US, "%.2f", eigenvector[i]));
    	}
    	result.write(")\n");
    	result.close();
    }
    
    public static String getHeader(int length) {
    	String header = "(t";
    	for(int i = 0; i < length; i ++) {
    		header += String.format(", x%d", i+1);
    	}
    	header += ")";
    	
    	return header;
    }
}
