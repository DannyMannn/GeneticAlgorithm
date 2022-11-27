package Genetic;

import File.FileHandler;
import Genetic.Individual;
import java.util.List;

import jdk.swing.interop.SwingInterOpUtils;
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

    //public FileOutputStream salida;

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

    public GeneticAlgorithm() throws FileNotFoundException{
        this.entrada = new FileInputStream("population0.txt");
        this.generation = new Individual[45];   //30 por generación + 15 hijos (Podría cambiarse por dos variables -generation e hijos-)
        this.n = 15;                            //Numero de hiperparametros (Inicialmente 15)
        this.mlp = new MultilayerPerceptron();
        this.numGeneracion = 1;
        for(int i=0;i<generation.length;i++){
            generation[i] = new Individual(0,0,0,0.0,0.0);
        }
    }

    public void generarPoblacion(int it){
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
        try {
            if (numGeneracion == 1) {
                System.out.println("Leyendo primer archivo");
                lectura();
            }
            for (int i = 0; i < it; i++) {
                selectPairs();
                evaluate();
                controlPopulation();
                System.out.println("Ordenando poblacion");
                quicksortGeneration(generation,0,this.n-1);
                numGeneracion++;
                creacionArchivo(numGeneracion);
            }
        }catch(Exception ex){
            ex.printStackTrace();
            //System.out.println(ex);
        }
    }

    public void creacionArchivo(int generacion) throws IOException {
        String nombreFile = "population" + String.valueOf(generacion) + ".txt";
        FileOutputStream salida = new FileOutputStream(nombreFile);
        BufferedWriter buf = new BufferedWriter(new OutputStreamWriter(salida));
        buf.write("Neuronas  |  Capas  |  Epocas | Learning Rate | Momentum | Accuracy\n");
        for (int i = 0; i < this.n; i++) {
            buf.write("    " + Double.toString(generation[i].getNumNeuronsInt()) + "    |    " + Double.toString(generation[i].getNumHiddenLayersInt()) + "   |    " + Integer.toString(generation[i].getNumEpochsInt()) + "  |      " + Double.toString(generation[i].getLearningRateDouble()) + "      |     " + Double.toString(generation[i].getMomentumDouble()) + "   | " + Double.toString(generation[i].getAccuracy()) + "\n");
        }

        double a=0.0,min=0.0,max=0.0;
        double standardDeviation = 0.0;
        double med=0.0,med2=0.0;
        for (int i = 0; i < 30; i++) {
            a+=generation[i].getAccuracy();

        }
        med=a/30;

        double b=0.0;

        for (int i = 0; i < 15; i++) {
            b+=generation[i].getAccuracy();

        }
        med2=b/15;

        for (int i = 0; i < 15; i++) {
            standardDeviation  += Math.pow((generation[i].getAccuracy() - med2), 2);
        }
        double raiz = standardDeviation / 45;
        double des = Math.sqrt(raiz);

        buf.write("\n");
        buf.write("\n");
        buf.write("\n");
        buf.write("\n");
        buf.write("Estadisticas de la generacion: \n");
        buf.write("Max= "+ generation[0].getAccuracy()+"\n");
        buf.write("Min= "+ generation[29].getAccuracy()+"\n");
        buf.write("Avg= "+ med +"\n");
        buf.write("Dev Std= " + des + "\n");
        buf.close();
        salida.close();
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
            //System.out.println("Linea sin split: " + lineaCorrect);
            String[] temp = lineaCorrect.split(" ");
            //System.out.println("Numero neuronas: " + temp[0]);
            //System.out.println("Los temps son : " + Integer.parseInt(temp[0]) + " " + Integer.parseInt(temp[1])+ " " + Integer.parseInt(temp[2])+ " " + Double.parseDouble(temp[3])/100.0+ " " + Double.parseDouble(temp[4])/100.0);
            generation[i] = new Individual(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), Double.parseDouble(temp[3])/100.0, Double.parseDouble(temp[4])/100.0);
            //generation[i].setAccuracy(Double.parseDouble(temp[5]));
        }
        /*
        for (int i = 0; i < n; i++) {
            System.out.println("El accuracy del indice " + i + " es " + generation[i].getAccuracy());
        }*/
        entrada.close();
    }


    private void mate(Individual[][] couples,int i){
        Individual p = new Individual(); //auxiliar individual for assign it to the matrix
        int n = 0; //getting the middle of the hiperparam
        String temp; //temporal string for "bit exchange"

        //getting just one child per couple
        //each hiperParam gets an one-point crossover
//        for(int i=0;i< couples.length; i++){ //iterate trough the rows
        temp="";
        //numNeurons
        n = couples[i][0].getNumNeuronsString().length();
        temp = couples[i][0].getNumNeuronsString().substring(0, (n / 2) + 1);

        n = couples[i][1].getNumNeuronsString().length();
        if(n==1) {
            temp.concat(Character.toString(couples[i][1].getNumNeuronsString().charAt(0)));
        }else{
            temp.concat(couples[i][1].getNumNeuronsString().substring((n / 2) + 1, n));
        }
        p.setNumNeuronsString(temp);

        //HiddenLayers
        n= couples[i][0].getNumHiddenLayersString().length();
        if(n==1) {
            temp = (Character.toString(couples[i][0].getNumHiddenLayersString().charAt(0)));
        }else {
            temp = couples[i][0].getNumHiddenLayersString().substring(0, n / 2);
        }
        //Acá no hay problema si n/2 = 0, pues n al menos será 1
        n = couples[i][1].getNumHiddenLayersString().length();
        temp.concat(couples[i][1].getNumHiddenLayersString().substring((n/2),n));

        p.setNumHiddenLayersString(temp);

        //Epochs
        n= couples[i][0].getNumEpochsString().length();
        if(n<=2) {
            temp = (Character.toString(couples[i][0].getNumEpochsString().charAt(0)));
        }else {
            temp = couples[i][0].getNumEpochsString().substring(0, (n / 2) - 1);
        }

        n = couples[i][1].getNumEpochsString().length();
        if(n==1) {
            temp.concat(Character.toString(couples[i][1].getNumEpochsString().charAt(0)));
        }else {
            temp.concat(couples[i][1].getNumEpochsString().substring((n / 2) - 1, n));
        }
        p.setNumEpochsString(temp);

        //L.R.
        n = couples[i][0].getLearningRateString().length();
        if(n==1) {
            temp = (Character.toString(couples[i][0].getLearningRateString().charAt(0)));
        }else {
            temp = couples[i][0].getLearningRateString().substring(0, n / 2);
        }

        n = couples[i][1].getLearningRateString().length();
        temp.concat(couples[i][1].getLearningRateString().substring((n/2),n));
        p.setLearningRateString(temp);

        //Momentum
        n = couples[i][0].getMomentumString().length();
        if(n==1) {
            temp = (Character.toString(couples[i][0].getNumNeuronsString().charAt(0)));
        }else {
            temp = couples[i][0].getMomentumString().substring(0, (n / 2));
        }

        n = couples[i][1].getMomentumString().length();
        temp.concat(couples[i][1].getMomentumString().substring((n/2),n));
        p.setMomentumString(temp);


        couples[i][2] = p; //child allocated in [i][2]
        this.n++;
//            System.out.println(couples[i][2]+" i="+i);//DEBUG childs created correctly (with changes)
//        }
    }
    private void mutate(Individual[][] couples){
        Individual p = new Individual();
        int n = 0;
        String temp,aux,newString;
        int random_int,random_int2,descriptor;
        int max = 14; int min = 0,rep=-1;
        int k;
        temp="";
        aux="";

        //modificar un bit de 2 hijos aleatorios

        for(int i=0;i<2; i++) {
            do {    //Controla si ya se aplicó mutación a ese hijo
                random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);//para la fila a mutar
            }while(rep==random_int);
            descriptor = (int)(Math.random()*5+1); //descriptor
            switch (descriptor) {
                case 1://numNeurons
                    n = couples[random_int][2].getNumNeuronsString().length();
                    //System.out.println("Hijo de pareja "+random_int+" Numero de neuronas: "+couples[random_int][2].getNumNeuronsString()+"\tn: "+n);
                    temp = couples[random_int][2].getNumNeuronsString();
                    random_int2 = (int) (Math.random() * n + 0);
                    if (temp.charAt(random_int2) == '1') {
                        newString = temp.substring(0, random_int2) + '0' + temp.substring(random_int2 + 1);
                    } else {
                        newString = temp.substring(0, random_int2) + '1' + temp.substring(random_int2 + 1);
                    }
                    aux = couples[random_int][2].getNumNeuronsString();
                    couples[random_int][2].setNumNeuronsString(newString);
                    //p.setNumNeuronsString(temp);
                    //couples[i][2] = p;
                    //System.out.println("Hecho numNeurons");
                    break;
                case 2://HiddenLayers
                    n = couples[random_int][2].getNumHiddenLayersString().length();
                    //System.out.println("Hijo de pareja "+random_int+" Capas ocultas: "+couples[random_int][2].getNumHiddenLayersString()+"\tn: "+n);
                    temp = couples[random_int][2].getNumHiddenLayersString();
                    random_int2 = (int) (Math.random() * n + 0);
                    if (temp.charAt(random_int2) == '1') {
                        newString = temp.substring(0, random_int2) + '0' + temp.substring(random_int2 + 1);
                    } else {
                        newString = temp.substring(0, random_int2) + '1' + temp.substring(random_int2 + 1);
                    }
                    //p.setNumHiddenLayersString(temp);
                    aux = couples[random_int][2].getNumHiddenLayersString();
                    couples[random_int][2].setNumHiddenLayersString(newString);
                    //couples[i][2] = p;
                    //System.out.println("Hecho capas ocultas");
                    break;
                case 3://Epochs
                    n = couples[random_int][2].getNumEpochsString().length();
                    //System.out.println("Hijo de pareja "+random_int+" Numero de epocas: "+couples[random_int][2].getNumEpochsString()+"\tn: "+n);
                    temp = couples[random_int][2].getNumEpochsString();
                    random_int2 = (int) (Math.random() * n + 0);
                    if (temp.charAt(random_int2) == '1') {
                        newString = temp.substring(0, random_int2) + '0' + temp.substring(random_int2 + 1);
                    } else {
                        newString = temp.substring(0, random_int2) + '1' + temp.substring(random_int2 + 1);
                    }
                    //p.setNumEpochsString(temp);
                    aux = couples[random_int][2].getNumEpochsString();
                    couples[random_int][2].setNumEpochsString(newString);
                    //couples[i][2] = p;
                    //System.out.println("Hecho numero de epocas");
                    break;
                case 4://L.R.
                    n = couples[random_int][2].getLearningRateString().length();
                    //System.out.println("Hijo de pareja "+random_int+" Learning rate: "+couples[random_int][2].getLearningRateString()+"\tn: "+n);
                    temp = couples[random_int][2].getLearningRateString();
                    random_int2 = (int) (Math.random() * n + 0);
                    if (temp.charAt(random_int2) == '1') {
                        newString = temp.substring(0, random_int2) + '0' + temp.substring(random_int2 + 1);
                    } else {
                        newString = temp.substring(0, random_int2) + '1' + temp.substring(random_int2 + 1);
                    }
                    // p.setLearningRateString(temp);
                    aux = couples[random_int][2].getLearningRateString();
                    couples[random_int][2].setLearningRateString(newString);
                    //couples[i][2] = p;
                    //System.out.println("Hecho learning rate");
                    break;
                case 5://Momentum
                    n = couples[random_int][2].getMomentumString().length();
                    //System.out.println("Hijo de pareja "+random_int+" Momentum: "+couples[random_int][2].getMomentumString()+"\tn: "+n);
                    temp = couples[random_int][2].getMomentumString();
                    random_int2 = (int) (Math.random() * n + 0);
                    if (temp.charAt(random_int2) == '1') {
                        newString = temp.substring(0, random_int2) + '0' + temp.substring(random_int2 + 1);
                    } else {
                        newString = temp.substring(0, random_int2) + '1' + temp.substring(random_int2 + 1);
                    }
                    //p.setMomentumString(temp);
                    aux = couples[random_int][2].getMomentumString();
                    couples[random_int][2].setMomentumString(newString);
                    //couples[i][2] = p;
                    //System.out.println("Hecho momentum");
                    break;
                default:
                    System.out.println("ERROR");
                    break;

            }
            if(!couples[random_int][2].validate()){
                //System.out.println("Hijo "+random_int+" no salio mutacion");
                switch(descriptor){
                    case 1:
                        couples[random_int][2].setNumNeuronsString(aux);
                        break;
                    case 2:
                        couples[random_int][2].setNumHiddenLayersString(aux);
                        break;
                    case 3:
                        couples[random_int][2].setNumEpochsString(aux);
                        break;
                    case 4:
                        couples[random_int][2].setLearningRateString(aux);
                        break;
                    case 5:
                        couples[random_int][2].setMomentumString(aux);
                        break;
                }
                i--;
            }else{
                //System.out.println("Hijo "+random_int+" mutado");
                rep = random_int;
            }
            temp="";
            aux="";
            newString = "";
        }
    }

    public void selectPairs(){//select the couples, lol
        //[parent1][parent2][child]
        Individual[][] couples = new Individual[15][3]; //15 rows and  3 cols
        for(int i =0;i<15;i++){
            for(int j=0;j<3;j++){
                couples[i][j] = new Individual();
            }
        }
        int max = 14; int min = 0; int k=0; //15 bc there would be 15 parents (best scenario)
        int random_int;
        //Creating the couples
        System.out.println(couples.length);
        for(int i=0; i<couples.length;i++){//15 couples created
            for(int j=0; j<2;j++){
                random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
//                System.out.println("ran "+random_int);//DEBUG
                couples[i][j] = generation[random_int];
            }
        }
        for(int i=0; i<couples.length;i++){//creating here the for loop, for an expected output :p
            mate(couples,i);
        }

        //Obtiene la ubicación
        Individual p;//aux
        k=0;
        while(k<generation.length){
            p = generation[k];
            if(p.getLearningRateDouble() == 0.0 && p.getMomentumDouble() == 0.0){
                break;
            }
//            System.out.println(p + "k= "+k);//DEBUG
            k++;
        }
        k--;

        System.out.println("\nHijos creados: ");
        for(int i=0; i<couples.length;i++){ //impresion antes
            System.out.println(couples[i][2]);
        }
        System.out.println(k);//DEBUG
//        //asignando hijos a gen
        int j=0;
        for(int i=k; i<k+couples.length;i++){
            generation[i] = couples[j][2];
            j++;
        }
        quicksortGeneration(generation,0,k+couples.length);

        mutate(couples);
//        //mutados
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
        System.out.println("Evaluando poblacion");
        try {
            Instances train;
            Evaluation eval;
            FileReader file1,file2;
            for (int i = 0; i < n; i++) {
                if(generation[i].getAccuracy()==0.0) {
                    file1 = new FileReader("fold1.arff");
                    train = new Instances(file1);
                    train.setClassIndex(train.numAttributes() - 1);
                    mlp.setLearningRate(generation[i].getLearningRateDouble());
                    mlp.setMomentum(generation[i].getMomentumDouble());
                    mlp.setTrainingTime(generation[i].getNumEpochsInt());
                    mlp.setHiddenLayers(generation[i].getNumHiddenLayersString());
                    mlp.buildClassifier(train);
                    file1.close();
                    file2 = new FileReader("fold2.arff");
                    train = new Instances(file2);
                    train.setClassIndex(train.numAttributes() - 1);
                    eval = new Evaluation(train);
                    eval.evaluateModel(mlp, train);
                    System.out.println("-" + (i + 1) + " " + eval.pctCorrect());
                    generation[i].setAccuracy(eval.pctCorrect());
                    file2.close();
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            //System.out.println(ex);
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

    public void controlPopulation(){
        System.out.println("Controlando poblacion");
        for(int i=this.n-1;i>=30;i--){
            this.generation[i] = new Individual();
            this.n--;
        }
    }
}
