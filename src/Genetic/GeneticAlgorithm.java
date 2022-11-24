package Genetic;

import File.FileHandler;
import Genetic.Individual;

import java.util.List;

public class GeneticAlgorithm {

    private FileHandler f;
    private Individual generation[];
    private int n;

    public GeneticAlgorithm(String file){
        this.f = new FileHandler(file);         //Nombre del primer archivo
        this.generation = new Individual[45];   //30 por generación + 15 hijos (Podría cambiarse por dos variables -generation e hijos-)
        this.n = 15;                            //Numero de hiperparametros (Inicialmente 15)
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
            temp.concat(couples[i][1].getNumNeuronsString().substring((n/2)+1,n));
            p.setNumNeuronsString(temp);
                //HiddenLayers
            n= couples[i][0].getNumHiddenLayersString().length();
            temp = couples[i][0].getNumHiddenLayersString().substring(0,n/2);

            n = couples[i][1].getNumNeuronsString().length();
            temp.concat(couples[i][1].getNumHiddenLayersString().substring((n/2)+1,n));
            p.setNumHiddenLayersString(temp);
                //Epochs
            n= couples[i][0].getNumEpochsString().length();
            temp = couples[i][0].getNumEpochsString().substring(0,n/2);

            n = couples[i][1].getNumEpochsString().length();
            temp.concat(couples[i][1].getNumEpochsString().substring((n/2)+1,n));
            p.setNumEpochsString(temp);
            //L.R.
            n = couples[i][0].getLearningRateString().length();
            temp = couples[i][0].getLearningRateString().substring(0,n/2);

            n = couples[i][1].getLearningRateString().length();
            temp.concat(couples[i][1].getLearningRateString().substring((n/2)+1,n));
            p.setLearningRateString(temp);
            //Momentum
            n = couples[i][0].getMomentumString().length();
            temp = couples[i][0].getMomentumString().substring(0,n/2);

            n = couples[i][1].getMomentumString().length();
            temp.concat(couples[i][1].getMomentumString().substring((n/2)+1,n));
            p.setMomentumString(temp);

            couples[i][2] = p;
        }


    }
    public void selectPairs(List<Individual> parents){//select the couples, lol
        //[parent1][parent2][child]
        Individual [][]couples = new Individual[15][3]; //15 rows and  3 cols
        int max = 15; int min = 0; int k;
        int random_int;
        //Creating the couples
        for(int i=0; i<30;i++){//30 couples created
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

    }
    public void sortGeneration(){//Ordena la lista de accurracy de mejor a peor

    }
}
