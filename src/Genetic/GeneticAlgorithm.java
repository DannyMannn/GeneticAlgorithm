package Genetic;

import File.FileHandler;
import Genetic.Individual;

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

    public void evaluate(){//Evalua todos los hiperparametros del arreglo generation

    }

    public void sortGeneration(){//Ordena la lista de accurracy de mejor a peor

    }
}
