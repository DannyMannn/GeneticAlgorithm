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
    public Individual[] generation;
    public int n;
    public FileInputStream entrada;

    public FileOutputStream salida;

    public int numGeneracion;

    public MultilayerPerceptron mlp;

    public GeneticAlgorithm(String file) throws FileNotFoundException {
        this.entrada = new FileInputStream(file);
        this.f = new FileHandler(file);         //Nombre del primer archivo
        this.generation = new Individual[45];   //30 por generación + 15 hijos (Podría cambiarse por dos variables -generation e hijos-)
        this.n = 15;                            //Numero de hiperparametros (Inicialmente 15)
        this.mlp = new MultilayerPerceptron();
        this.numGeneracion = 1;
        for(int i=0;i<generation.length;i++){
            generation[i] = new Individual(0,0,0,0.0,0.0);
        }
    }

    public GeneticAlgorithm(){
        this.generation = new Individual[45];   //30 por generación + 15 hijos (Podría cambiarse por dos variables -generation e hijos-)
        this.n = 15;                            //Numero de hiperparametros (Inicialmente 15)
        this.mlp = new MultilayerPerceptron();
        for(int i=0;i<generation.length;i++){
            generation[i] = new Individual(0,0,0,0.0,0.0);
        }
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

    public void creacionArchivo(int generacion) {

    }

    public void lectura() throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(entrada));
        String linea;
        String aa = buf.readLine();
        //System.out.println("Encabezado " + aa);
        for (int i = 0; i < n; i++) {
            linea = buf.readLine();
            String linea1 = linea.replaceAll("\\|", " ");    //El doble \\ es para que no tome en cuenta | como un meta character (Java ocupa eso para regex)
            String lineaCorrect = linea1.trim().replaceAll(" +", " ");  //El " +" es para cuando hay mas de un espacio convertirlo a uno solo y el trim elimina espacios
            System.out.println("Linea sin split: " + lineaCorrect);
            String[] temp = lineaCorrect.split(" ");
            //System.out.println("Numero neuronas: " + temp[0]);
            //System.out.println("Los temps son : " + temp[0] + " " + temp[5]);
            generation[i] = new Individual(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), Double.parseDouble(temp[3]), Double.parseDouble(temp[4]));
            generation[i].setAccuracy(Double.parseDouble(temp[5]));
        }
        for (int i = 0; i < n; i++) {
            System.out.println("El accuracy del indice " + i + " es " + generation[i].getAccuracy());
        }
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
    private void mutate(Individual[][] couples){
        Individual p = new Individual(); //COMENTARIOS EN ESPAÑOL PARA EVITAR CONFUSIÓN AL PROFESOR POR FAVOR
        //(Y DE PASO TAMBIÉN EVITAR LA PENA DE CORREGIRLES)
        int n = 0;
        String temp;
        int random_int,random_int2;
        int max = 4; int min = 0; int k;
        temp="";

        //modificar un bit de 2 hijos aleatorios

        for(int i=0;i<2; i++) {
            random_int = (int)Math.floor(Math.random()*(max-min+1)+min);//para la fila a mutar
            random_int2 = (int)(Math.random()*5+1); //descriptor
            switch (random_int2) {
                case 1://numNeurons
                    n = couples[random_int][2].getNumNeuronsString().length()-1;
                    temp = couples[random_int][2].getNumNeuronsString();
                    random_int2 = (int) (Math.random() * n + 0);
                    if (temp.charAt(random_int2) == '1') {
                        String newString = temp.substring(0, random_int2) + '0' + temp.substring(random_int2 + 1);
                    } else {
                        String newString = temp.substring(0, random_int2) + '1' + temp.substring(random_int2 + 1);
                    }
                    p.setNumNeuronsString(temp);

                    couples[i][2] = p;
                    break;
                case 2://HiddenLayers
                    n = couples[random_int][2].getNumHiddenLayersString().length()-1;
                    temp = couples[random_int][2].getNumHiddenLayersString();
                    random_int2 = (int) (Math.random() * n + 0);
                    if (temp.charAt(random_int2) == '1') {
                        String newString = temp.substring(0, random_int2) + '0' + temp.substring(random_int2 + 1);
                    } else {
                        String newString = temp.substring(0, random_int2) + '1' + temp.substring(random_int2 + 1);
                    }
                    p.setNumHiddenLayersString(temp);

                    couples[i][2] = p;
                    break;
                case 3://Epochs
                    n = couples[random_int][2].getNumEpochsString().length()-1;
                    temp = couples[random_int][2].getNumEpochsString();
                    random_int2 = (int) (Math.random() * n + 0);
                    if (temp.charAt(random_int2) == '1') {
                        String newString = temp.substring(0, random_int2) + '0' + temp.substring(random_int2 + 1);
                    } else {
                        String newString = temp.substring(0, random_int2) + '1' + temp.substring(random_int2 + 1);
                    }
                    p.setNumEpochsString(temp);

                    couples[i][2] = p;
                    break;
                case 4://L.R.
                    n = couples[random_int][2].getLearningRateString().length()-1;
                    temp = couples[random_int][2].getLearningRateString();
                    random_int2 = (int) (Math.random() * n + 0);
                    if (temp.charAt(random_int2) == '1') {
                        String newString = temp.substring(0, random_int2) + '0' + temp.substring(random_int2 + 1);
                    } else {
                        String newString = temp.substring(0, random_int2) + '1' + temp.substring(random_int2 + 1);
                    }
                    p.setLearningRateString(temp);

                    couples[i][2] = p;
                    break;
                case 5://Momentum
                    n = couples[random_int][2].getMomentumString().length()-1;
                    temp = couples[random_int][2].getMomentumString();
                    random_int2 = (int) (Math.random() * n + 0);
                    if (temp.charAt(random_int2) == '1') {
                        String newString = temp.substring(0, random_int2) + '0' + temp.substring(random_int2 + 1);
                    } else {
                        String newString = temp.substring(0, random_int2) + '1' + temp.substring(random_int2 + 1);
                    }
                    p.setMomentumString(temp);

                    couples[i][2] = p;
                    break;
                default:
                    System.out.println("ERROR");
                    break;

            }
            if(!couples[random_int][2].validate()){
                i--;
            }else{
                //nothing
            }
            //if de couples[random_2][2] llamar a validate si es falso decrementar la variable de control del ciclo
        }
    }

    public void selectPairs(){//select the couples, lol
        //[parent1][parent2][child]
        Individual[][] couples = new Individual[15][3]; //15 rows and  3 cols
        int max = 14; int min = 0; int k=0; //15 bc there would be 15 parents (best scenario)
        int random_int;
        //Creating the couples
        System.out.println(couples.length);
        for(int i=0; i<couples.length;i++){//15 couples created
            for(int j=0; j<2;j++){
                random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
//                System.out.println("ran "+random_int);
                couples[i][j] = generation[random_int];
            }
        }

        mate(couples);

        Individual p;//aux
        while(k<generation.length){
            p = generation[k];
            if(p.getLearningRateDouble() == 0.0 && p.getMomentumDouble() == 0.0){
                break;
            }
            k++;
        }
        k--;
        for(int i=0; i<couples.length;i++){ //impresion antes
            System.out.println(couples[i][2]);
        }
//        System.out.println(k);//DEBUG
        //asignando hijos a gen
        int j=0;
        for(int i=k; i<k+couples.length;i++){
            generation[i] = couples[j][2];
            j++;
        }
        quicksortGeneration(generation,0,k+couples.length);

        mutate(couples);
        //mutados
        j=0;
        for(int i=k; i<k+couples.length;i++){
            generation[i] = couples[j][2];
            j++;
        }
        for(int i=0; i<k+couples.length;i++){//15 couples created
            System.out.println(generation[i]);
        }
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

    public static void quicksortGeneration(Individual[] A, int izq, int der) {
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
