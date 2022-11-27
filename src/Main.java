import File.FileHandler;
import Genetic.GeneticAlgorithm;
import Genetic.Individual;

public class Main {
    public static void main(String[] args) {
        //Se pusieron los atributos de GeneticAlgoritm en public para hacer las pruebas
        try {
            GeneticAlgorithm genAlg = new GeneticAlgorithm();
            genAlg.generarPoblacion(3);
            //System.out.println(genAlg.generation[0].getNumNeuronsString());
            //System.out.println(genAlg.generation[0].getNumHiddenLayersString());
            //System.out.println(genAlg.generation[0].getNumEpochsString());
            //System.out.println(genAlg.generation[0].getLearningRateString());
            //System.out.println(genAlg.generation[0].getMomentumString());
            /*
            genAlg.generation[0] = new Individual(16,1,120,0.11,0.08 );
            genAlg.generation[1] = new Individual(18,3,100,0.17,0.08);
            genAlg.generation[2] = new Individual(11,2,110,0.17,0.08);
            genAlg.generation[3] = new Individual(17,2,90,0.11,0.17);
            genAlg.generation[4] = new Individual(8,2,90,0.11,0.08);
            genAlg.generation[5] = new Individual(18,3,100,0.17,0.08);
            genAlg.generation[6] = new Individual(11,2,110,0.17,0.08);
            genAlg.generation[7] = new Individual(17,2,90,0.11,0.17);
            genAlg.generation[8] = new Individual(8,2,90,0.11,0.08);
            genAlg.generation[9] = new Individual(18,3,100,0.17,0.08);
            genAlg.generation[10] = new Individual(11,2,110,0.17,0.08);
            genAlg.generation[11] = new Individual(17,2,90,0.11,0.17);
            genAlg.generation[12] = new Individual(8,2,90,0.11,0.08);
            genAlg.generation[13] = new Individual(18,3,100,0.17,0.08);
            genAlg.generation[14] = new Individual(11,2,110,0.17,0.08);


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
        /*
        try {
            GeneticAlgorithm gen = new GeneticAlgorithm("population0.txt");
            gen.lectura();
            gen.creacionArchivo(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
        }catch (Exception ex){
            System.out.println(ex);
        }

    }
}