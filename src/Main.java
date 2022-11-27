import File.FileHandler;
import Genetic.GeneticAlgorithm;
import Genetic.Individual;

public class Main {
    public static void main(String[] args) {

        //Se pusieron los atributos de GeneticAlgoritm en public para hacer las pruebas
        GeneticAlgorithm genAlg = new GeneticAlgorithm();
        genAlg.n = 5;
        genAlg.generation[0] = new Individual(16,1,120,0.11,0.02 );
        genAlg.generation[1] = new Individual(18,3,100,0.2,0.2);
        genAlg.generation[2] = new Individual(11,2,110,0.2,0.2);
        genAlg.generation[3] = new Individual(17,2,90,0.11,0.17);
        genAlg.generation[4] = new Individual(8,2,90,0.11,0.02);
        System.out.println();
        //genAlg.evaluate();
        genAlg.selectPairs();
        /*System.out.println("Ordenamiento de accuracy");
        for (int i = 0; i < genAlg.n; i++) {
            System.out.println(genAlg.generation[i].getAccuracy());
        }
        System.out.println(genAlg.generation[0]);
        */
        /*Individual ind = new Individual();
        ind.setNumNeuronsInt(10);
        System.out.println(ind.getNumNeuronsString());
        System.out.println(ind.getNumNeuronsInt());*/
        /*try {
            GeneticAlgorithm gen = new GeneticAlgorithm("population0.txt");
            gen.lectura();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
    }
}