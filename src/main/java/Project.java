import org.la4j.Matrix;
import org.la4j.decomposition.EigenDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

            System.out.println("Valor Próprio: ");
            System.out.println(eigen_value(leslie));
            
            System.out.println("Vetor Próprio: ");
//            System.out.printf("%.2f", eigen_value(leslie));

            double[][] population = new double[dim][1];
            for(int i = 0; i < dim; i++) {
            	population[i][0] = i+1;
            }
            for(int i = 0; i < 1; i++) {
            	population[0][i] = i+2;
            }
            Matrix populationResult = dimPopulationinT(leslie, population, gen);
            System.out.println("Dimensão da população");
            System.out.println(populationResult);
            
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
	            		graphResults[i] = populationResult.get(i, 0);
	            	}
	            	graphTitle = "Número Total De Individuos";
	                resulType = "Número Total De Individuos";
	                xLine = "Momento";
	                yLine = "Dimensão da população";
	            	break;
	            case 2:
	            	break;
	            case 3:
	            	break;
	            case 4:
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
        }

        //FALTA UM MÉTODO PARA VER A DIMENSÃO DE CADA LINHA NO FICHEIRO PQ NÃO É SUPOSTO PERGUNTAR QUANDO ARGS.LENGTH == 2 E > 2
        // -> SUBSTITUIR NO MÉTODO INTERATIVO E NÃO INTERATIVO COM FICHEIRO

        //Modo interativo com ficheiro: java -jar nome_programa.jar -n nome_ficheiro_entrada.txt
        if (args.length == 2) {
            do {
                System.out.println("Número de grupos etários (dimensão): ");
                dim = in.nextInt();
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
            int generations = Integer.parseInt(generations_aux);
            String format_gnuplot_files_aux = args[3];
            int format_gnuplot_files = Integer.parseInt(format_gnuplot_files_aux);

            do {
                System.out.println("Número de grupos etários (dimensão): ");
                dim = in.nextInt();
            } while (dim < 0);

            //Só usar isto para imprimir quando tiver os métodos prontos:
            // 1). Calcular valor e vetor próprio;
            // Eu
            // 2). Calcular dimensão da população a cada geração;
            // Tiago
            // 3). Calcular variação da população entre gerações;
            // Tiago

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
                    writer.write("Formato ficheiro gnuplot: " + format_gnuplot_files + "\n");
                    if (vec[0] == 1) {
                        writer.write("Valor Próprio: " + eigen_value(MatrixWriteFile(args[args.length - 2], dim)) + "\n");
                    }
                    if (vec[1] == 1) {
                        writer.write("Dimensão da População: " + "\n");
                    }
                    if (vec[2] == 1) {
                        writer.write("Variação da População: " + "\n");
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
//                        Função
                    }
                    if (vec[2] == 1) {
                        writer.write("Variação da População: " + "\n");
//                        Função
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

    public static double[][] MatrixWriteFile(String filename, int dim) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        String line = scanner.nextLine();
        String[] quantity_population = line.split(",");
        int len_quantity_population = quantity_population.length;

        for (int i = 0; i < len_quantity_population; i++) {
            quantity_population[i] = quantity_population[i].trim();
            quantity_population[i] = quantity_population[i].substring(4);
        }

        line = scanner.nextLine();
        String[] survival = line.split(",");
        int len_survival = survival.length;

        for (int i = 0; i < len_survival; i++) {
            survival[i] = survival[i].trim();
            survival[i] = survival[i].substring(3);
        }

        line = scanner.nextLine();
        String[] fecundity = line.split(",");
        int len_fecundity = fecundity.length;

        for (int i = 0; i < len_fecundity; i++) {
            fecundity[i] = fecundity[i].trim();
            fecundity[i] = fecundity[i].substring(3);
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

        return matrixleslie;
    }

    public static Matrix LeslieMatrixFile(String filename, int dim) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        String line = scanner.nextLine();
        String[] quantity_population = line.split(",");
        int len_quantity_population = quantity_population.length;

        System.out.println("Quantidade de população: ");
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

        System.out.println("Matriz de Leslie: ");
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
     * Dimensão da população Reprodutora - Posso ter que multiplicar o valor por 2;
     * Recebe matrix e calcula a sua soma
     */
    public static double totaldimPopulatotion(Matrix population){
        return population.sum();
    }


    /***
     * Número de gerações a estimar
     *
     */
    public int numberOfGerations(){
        Scanner in = new Scanner(System.in);

        System.out.print("Número de gerações a estimar: ");
        int geracoes = in.nextInt();

        return geracoes;
    }

    /***
     * Calculo da distribuição da população
     *
     */


    /***
     * Variação da população nos entre o inicio e os ano final dado
     * Parametros:População inicial, Matrix leslie e t final
     *
     * Taxa de variação segue a formula ((população ano t - população inicial) - 1) *100
     *
     * Esta função apenas imprime os valores, para guardar podemos dar update a esta função e colocar a retornar uma lista
     * Output : taxa de variação ao longo dos anos - Lista de valores entre anos
     */

    public static void rateofchange(double[][] leslie,double[][] population, int t ){

        Matrix initialpopulation = convertToMatrix(population);

        for(int i = 1; i<t;i++){
            System.out.println(((totaldimPopulatotion(dimPopulationinT(leslie,population,i)) - totaldimPopulatotion(initialpopulation))-1.0) * 100);
        }

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
    		data.write(String.format("%d %.2f\n", i, matrix[i]));
    	}
    	data.close();
    }
    
    public static void creatingTxtFileGraph(Matrix lesliMatrix, int gen, double dimPopulation, 
    		double rateOfChange, double classes, double eigenvalue, double vector) throws IOException {
    	FileWriter result = new FileWriter("TextGraphResult.txt");
    }
}
