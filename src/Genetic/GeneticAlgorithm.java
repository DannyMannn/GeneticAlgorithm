package Genetic;

import File.FileHandler;
import Genetic.Individual;
import java.util.List;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.classifiers.evaluation.Evaluation;

import javax.swing.text.html.parser.Parser;
import java.io.*;

public class GeneticAlgorithm {

    private FileHandler f;
    private Individual generation[];
    private int n;
    private FileInputStream entrada;

    private FileOutputStream salida;

    private MultilayerPerceptron mlp;

    public GeneticAlgorithm(String file) throws FileNotFoundException {
        this.entrada = new FileInputStream(file);
        this.f = new FileHandler(file);         //Nombre del primer archivo
        this.generation = new Individual[45];   //30 por generación + 15 hijos (Podría cambiarse por dos variables -generation e hijos-)
        this.n = 15;                            //Numero de hiperparametros (Inicialmente 15)
        this.mlp = new MultilayerPerceptron();
    }

    public GeneticAlgorithm(){
        this.generation = new Individual[45];   //30 por generación + 15 hijos (Podría cambiarse por dos variables -generation e hijos-)
        this.n = 15;                            //Numero de hiperparametros (Inicialmente 15)
        this.mlp = new MultilayerPerceptron();
    }

    public void generarPoblacion(){
        /*
        Dentro de un ciclo
            - Ciclo para almacenar los valores del archivo en el arreglo generation
                n cambiará a lo largo de la ejecucion (Para manejar cuantos hiperparametros hay)
                for(int i=0;i<n;i++){
                        //Llamada a FileHandler para obtener renglon por renglon del archivo inicial
                        //Almacenamiento de atributos de hiperparametros en la variable generation (En clase Individual)
                    }
            - Llamada a selectPair() (Primero llama a la función para seleccionar parejas a nuestros primeros 15 hiperparámetros)
                - selectPair() almacena la linea en un array o la estructura de datos que se quiera (Tal vez arreglo de Individual)
                - Selecciona los 15 mejores
                - mate() Empareja al azar los 15 mejores y genera 1 hijo por pareja. Almacena a los hijos en una estructura de datos diferente al de los padres (Para distingirlos, es una idea)
                    - llamada a validate() de Individual para verificar si está dentro del rango, en caso contrario, repetir
                - mutate()
                    - llamada a validate()
                - Una vez terminado, lo agrega a un archivo (Usando la clase FileHandler)
            - llamada a evaluate()
            - Llamada a sortGeneration() (A partir del arreglo generation, ordenar de mejor a peor)
            - Llamada a controlPopulation() Elimina los peores resultados auxiliandose de la variable n,
                        es decir, mientras n>30, eliminar los peores resultados (Para que no se eliminen hiperparametros de la primera generacion)
            - Llamada a FileHandler para que agregue los datos de generation a un nuevo archivo con la sitaxis numNeuronas,numHiddenLayers, numEpochs,learningRate,momentum,accuracy
        - Termina ciclo
        */
    }
    public void lectura() throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(entrada));
        String linea, encabezado;
        int tam;
        encabezado = buf.readLine();
        buf.readLine();  //Saltarse los titulos de cada parametro
        System.out.println("El encabezadoadada " + encabezado + " tamano " + encabezado.length());
        if (encabezado.length() % 2 == 0) {
            tam = Integer.parseInt(String.valueOf(encabezado.charAt(encabezado.length()-1)));
            System.out.println("Tamano para 1 digito: " + tam);
        }
        else {
            tam = Integer.parseInt(String.valueOf(encabezado.substring(encabezado.length()-2)));
            System.out.println("Tamano para 2 digitos: " + tam);
        }
        if (tam == 0) {
            for (int i=0; i<15; i++) {
                linea = buf.readLine();
                String linea1 = linea.replaceAll("\\|", " ");    //El doble \\ es para que no tome en cuenta | como un meta character (Java ocupa eso para regex)
                String lineaCorrect = linea1.trim().replaceAll(" +", " ");  //El " +" es para cuando hay mas de un espacio convertirlo a uno solo y el trim elimina espacios
                System.out.println("Linea sin split: " + lineaCorrect);
                String[] temp = lineaCorrect.split(" ");
                int size = temp.length;
                for (i = 0; i < size; i++) {
                    System.out.println("indice " + i + ": " + temp[i] + " ");
                }
            }
        }
        /*String linea1 = linea.replaceAll("\\|", " ");    //El doble \\ es para que no tome en cuenta | como un meta character (Java ocupa eso para regex)
        String lineaCorrect = linea1.trim().replaceAll(" +", " ");  //El " +" es para cuando hay mas de un espacio convertirlo a uno solo y el trim elimina espacios
        System.out.println("Linea sin split: " + lineaCorrect);
        String[] temp = lineaCorrect.split(" ");
        int size = temp.length;
        for (int i = 0; i < size; i++) {System.out.println("indice " + i + ": " + temp[i] + " ");
        }*/
        entrada.close();
    }


    private void mate(Individual[][] couples){
        Individual p = new Individual(); //auxiliar individual for assign it to the matrix
        int n = 0; //getting the middle of the hiperparam
        String temp; //temporal string for "bit exchange"

        //getting just one child per couple
        //each hiperParam gets an one-point crossover
        for(int i=0;i< couples.length; i++){ //iterate trough the rows
            temp="";
               //numNeurons
            n = couples[i][0].getNumNeuronsString().length();
            temp = couples[i][0].getNumNeuronsString().substring(0,n/2);

            n = couples[i][1].getNumNeuronsString().length();
            temp.concat(couples[i][1].getNumNeuronsString().substring((n/2),n));
            p.setNumNeuronsString(temp);
                //HiddenLayers
            n= couples[i][0].getNumHiddenLayersString().length();
            temp = couples[i][0].getNumHiddenLayersString().substring(0,n/2);

            n = couples[i][1].getNumHiddenLayersString().length();
            temp.concat(couples[i][1].getNumHiddenLayersString().substring((n/2),n));
            p.setNumHiddenLayersString(temp);
                //Epochs
            n= couples[i][0].getNumEpochsString().length();
            temp = couples[i][0].getNumEpochsString().substring(0,n/2);

            n = couples[i][1].getNumEpochsString().length();
            temp.concat(couples[i][1].getNumEpochsString().substring((n/2),n));
            p.setNumEpochsString(temp);
            //L.R.
            n = couples[i][0].getLearningRateString().length();
            temp = couples[i][0].getLearningRateString().substring(0,n/2);

            n = couples[i][1].getLearningRateString().length();
            temp.concat(couples[i][1].getLearningRateString().substring((n/2),n));
            p.setLearningRateString(temp);
            //Momentum
            n = couples[i][0].getMomentumString().length();
            temp = couples[i][0].getMomentumString().substring(0,n/2);

            n = couples[i][1].getMomentumString().length();
            temp.concat(couples[i][1].getMomentumString().substring((n/2),n));
            p.setMomentumString(temp);

            couples[i][2] = p; //child allocated in [i][2]
        }


    }
    public void selectPairs(List<Individual> parents){//select the couples, lol
        //[parent1][parent2][child]
        Individual [][]couples = new Individual[15][3]; //15 rows and  3 cols
        int max = 30; int min = 0; int k; //30 bc there would be 30 instances on parents (best scenario)
        int random_int;
        //Creating the couples
        for(int i=0; i<15;i++){//15 couples created
            k=0;
            for(int j=0; j<2;j++){
                random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
                couples[i][k] = parents.get(random_int);
                k++;
            }
        }
        mate(couples);
    }
    public void evaluate(){
        try {
            Instances train;
            Evaluation eval;
            FileReader file1,file2;
            for (int i = 0; i < n; i++) {
                file1 = new FileReader("fold1.arff");
                train = new Instances(file1);
                train.setClassIndex(train.numAttributes()-1);
                mlp.setLearningRate(generation[i].getLearningRateDouble());
                mlp.setMomentum(generation[i].getMomentumDouble());
                mlp.setTrainingTime(generation[i].getNumEpochsInt());
                mlp.setHiddenLayers(generation[i].getNumHiddenLayersString());
                mlp.buildClassifier(train);
                file1.close();
                file2 = new FileReader("fold2.arff");
                train = new Instances(file2);
                train.setClassIndex(train.numAttributes()-1);
                eval = new Evaluation(train);
                eval.evaluateModel(mlp,train);
                System.out.println("-"+generation[i].getNumNeuronsInt() + " "+ eval.pctCorrect());
                generation[i].setAccuracy(eval.pctCorrect());
                file2.close();
            }
            quicksortGeneration(generation,0,n-1);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    public static void quicksortGeneration(Individual A[], int izq, int der) {
        Individual aux,piv = A[izq];
        int i=izq,j=der;
        while(i < j){
            while(A[i].getAccuracy() >= piv.getAccuracy() && i < j) i++;
            while(A[j].getAccuracy() < piv.getAccuracy()) j--;
            if (i < j) {
                aux = A[i];
                A[i] = A[j];
                A[j] = aux;
            }
        }
        A[izq] = A[j];
        A[j] = piv;
        if(izq < j-1)
            quicksortGeneration(A,izq,j-1);
        if(j+1 < der)
            quicksortGeneration(A,j+1,der);
    }
}
