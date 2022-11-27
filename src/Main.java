import File.FileHandler;
import Genetic.GeneticAlgorithm;
import Genetic.Individual;

public class Main {
    public static void main(String[] args) {
        //Se pusieron los atributos de GeneticAlgoritm en public para hacer las pruebas
        try {
            GeneticAlgorithm genAlg = new GeneticAlgorithm();
            genAlg.generarPoblacion(3);
            //genAlg.lectura();
            //genAlg.selectPairs();

            //genAlg.generation[0] = new Individual(16,1,120,0.17,0.18 );
//            System.out.println(genAlg.generation[0].validate());
            //genAlg.generation[0].setLearningRateString("110");
            //System.out.println(genAlg.generation[0].validate());
            //System.out.println(genAlg.generation[0]);
            /*genAlg.generation[1] = new Individual(18,3,100,0.17,0.18);
            Individual matrix[][] = new Individual[1][3];
            matrix[0][0] = genAlg.generation[0];
            matrix[0][1] = genAlg.generation[1];
            genAlg.mate(matrix,0);*/
        }catch (Exception ex){
            ex.printStackTrace();
            //System.out.println(ex);
        }

    }
}