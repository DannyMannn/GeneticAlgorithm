package Genetic;

public class Individual {
    // TOPOLOGY HYPER-PARAMETERS
    private String numNeuronsString;
    private String numHiddenLayersString;
    private String numEpochsString;
    private String learningRateString;
    private String momentumString;

    // TOPOLOGY HYPER-PARAMETERS
    private int numNeuronsInt;
    private int numHiddenLayersInt;
    private int numEpochsInt;
    private double learningRateDouble;
    private double momentumDouble;

    // VALID RANGES OF THE TOPOLOGY OF THE MULTILAYER PERCEPTRON NEURAL NETWORK
    private int minNumNeurons;
    private int maxNumNeurons;
    private int minNumHiddenLayers;
    private int maxNumHiddenLayers;
    private int minNumEpochs;
    private int maxNumEpochs;
    private double minLearningRate;
    private double maxLearningRate;
    private double minMomentum;
    private double maxMomentum;

    // Número de bits que se usarán para cada parámetro
    // esto es útil para agregarle los bits mayores (0s) a los binaryStrings
    // ya que Integer.toBinraryString() no retorna 0s en su parte izquierda (no leading zeros)
    // Son strings que se proporcionarán al String.format()
    private String neuronBits = "%4s";
    private String hiddenLayerBits = "%2s";
    private String epochBits = "%6s";
    private String learningRateBits = "%2s";
    private String momentumBits = "%3s";

    private double accuracy;

    public Individual(){
        this.numNeuronsString = new String();
        this.numHiddenLayersString = new String();
        this.numEpochsString = new String();
        this.learningRateString = new String();
        this.momentumString = new String();

        this.setDefaultRanges();
    }

    public Individual(String numNeurons, String numHiddenLayers, String numEpochs, String learningRate, String momentum){
        this.setTopologyBinaryString(numNeurons, numHiddenLayers, numEpochs, learningRate, momentum);
        this.setDefaultRanges();
    }



    public Individual(int numNeurons, int numHiddenLayers, int numEpochs, double learningRate, double momentum){
        this.setTopologyMLP(numNeurons, numHiddenLayers, numEpochs, learningRate, momentum);
        this.setDefaultRanges();
    }

    public Individual(double accuracy){
        this.accuracy = accuracy;
        this.setDefaultRanges();
    }

    //Set de values as mlp request
    public void setTopologyMLP(int numNeurons, int numHiddenLayers, int numEpochs, double learningRate, double momentum){
        this.setNumNeuronsInt(numNeurons);
        this.setNumHiddenLayersInt(numHiddenLayers);
        this.setNumEpochsInt(numEpochs);
        this.setLearningRateDouble(learningRate);
        this.setMomentumDouble(momentum);
    }

    public void setTopologyBinaryString(String numNeurons, String numHiddenLayers, String numEpochs, String learningRate, String momentum){
        this.setNumNeuronsString(numNeurons);
        this.setNumHiddenLayersString(numHiddenLayers);
        this.setNumEpochsString(numEpochs);
        this.setLearningRateString(learningRate);
        this.setMomentumString(momentum);
    }

    /***********START OF IMPORTANT SECTION**************/
    /**
     * validates whether the geneticSequence of this individual is within the
     * range of the previously specified range of the topology of the Neural Network.
     * The values used for this project are specified in setDefaultRanges()
     * @return boolean that indicates whether the geneticSequence is valid
     */
    public boolean validate(){
        if(!(numNeuronsInt >= minNumNeurons && numNeuronsInt <= maxNumNeurons))
            return false;

        if(!(numHiddenLayersInt >= minNumHiddenLayers && numHiddenLayersInt <= maxNumHiddenLayers))
            return false;

        if (!(numEpochsInt >= minNumEpochs && numEpochsInt <= maxNumEpochs))
            return false;

//        if (Double.compare(learningRateDouble, 0.11d) != 0 || Double.compare(learningRateDouble, 0.13d) != 0
//                || Double.compare(learningRateDouble, 0.15d) != 0 || Double.compare(learningRateDouble, 0.17d) != 0)
//            return false;
//
//        if(Double.compare(momentumDouble, 0.08d) != 0 || Double.compare(momentumDouble, 0.10d) != 0
//                || Double.compare(momentumDouble, 0.12d) != 0 || Double.compare(momentumDouble, 0.14d) != 0
//                || Double.compare(momentumDouble, 0.16d) != 0|| Double.compare(momentumDouble, 0.18d) != 0)
//            return false;
        if(!(learningRateDouble >= minLearningRate && learningRateDouble <= maxLearningRate))
            return false;

        if(!(momentumDouble >= minMomentum && momentumDouble <= maxMomentum))
            return false;

        return true;
    }

    public String getGeneticSequence(){
        return this.numNeuronsString + this.numHiddenLayersString + this.numEpochsString + this.learningRateString + this.momentumString;
    }

    public String doubleToBinaryString(double number, String numBits){
        int numberInt = (int) number;
        return String.format(numBits, Integer.toBinaryString(numberInt)).replace(' ', '0');
        //return Integer.toBinaryString(numberInt);
    }

    /***********END OF IMPORTANT SECTION**************/

    private void setDefaultRanges(){
        this.minNumNeurons = 8;
        this.maxNumNeurons = 18;
        this.minNumHiddenLayers = 1;
        this.maxNumHiddenLayers = 3;
        this.minNumEpochs = 90;
        this.maxNumEpochs = 140;
        this.minLearningRate = 0.11;
        this.maxLearningRate = 0.17;
        this.minMomentum = 0.08;
        this.maxMomentum = 0.18;
    }

    public void setRanges(byte minNumNeurons, byte maxNumNeurons,
                          byte minNumHiddenLayers, byte maxNumHiddenLayers,
                          short minNumEpochs, short maxNumEpochs,
                          double minLearningRate, double maxLearningRate,
                          double minMomentum, double maxMomentum){
        this.minNumNeurons = minNumNeurons;
        this.maxNumNeurons = maxNumNeurons;
        this.minNumHiddenLayers = minNumHiddenLayers;
        this.maxNumHiddenLayers = maxNumHiddenLayers;
        this.minNumEpochs = minNumEpochs;
        this.maxNumEpochs = maxNumEpochs;
        this.minLearningRate = minLearningRate;
        this.maxLearningRate = maxLearningRate;
        this.minMomentum = minMomentum;
        this.maxMomentum = maxMomentum;
    }

    // Los BinaryStrings siempré estáran en el rango de (0, maxNumParametro - minNumParametro)
    // lo cual significa que a versiones numéricas decimales de los parámetros se le debe sumar
    // su respectivo delta, es decir minNumParametro.
    // Ej. de conversión: minNumNeurons = 8, maxNumNeurons = 18.
    // numNeuronsString solo puede estar dentro del rango (0, 10)
    // al hacer un setNeuronsString se le debe de sumar minNumNeurons a la versión decimal del hiperparámetro
    // al hacer un setNumNeuronsInt se le debe de restar minNumNeurons al binario. Esto es por lo siguiente:
    // SE ESPERA QUE LOS STRINGS DE INDIVIDUAL SIEMPRE SE PROPORCIONEN DENTRO DEL RANGO [0, maxNumParametro - minNumParametro]
    // SE ESPERA QUE LOS DECIMALES DE INDIVIDUAL SIEMPRE SE PROPORCIONEN DE TAL MANERA QUE WEKA LOS PUEDA INTERPRETAR [minNumParametro, maxNumParametro)]

    public void setNumNeuronsString(String numNeurons) {
        this.numNeuronsString = numNeurons;
        this.numNeuronsInt = Integer.parseInt(numNeurons, 2) + minNumNeurons;
    }

    public void setNumHiddenLayersString(String numHiddenLayers) {
        this.numHiddenLayersString = numHiddenLayers;
        this.numHiddenLayersInt = Integer.parseInt(numHiddenLayers, 2) + minNumHiddenLayers;
    }

    public void setNumEpochsString(String numEpochs) {
        this.numEpochsString = numEpochs;
        this.numEpochsInt = Integer.parseInt(numEpochs, 2) + minNumEpochs;
    }

    public void setLearningRateString(String learningRate) {
        this.learningRateString = learningRate;
        this.learningRateDouble = Integer.parseInt(learningRate, 2) + minLearningRate;
    }

    public void setMomentumString(String momentum) {
        this.momentumString = momentum;
        this.momentumDouble = Integer.parseInt(momentum, 2) + minMomentum;
    }



    public void setNumNeuronsInt(int numNeurons) {
        this.numNeuronsInt = numNeurons;
        this.numNeuronsString = this.doubleToBinaryString(numNeurons - minNumNeurons, neuronBits);
    }

    public void setNumHiddenLayersInt(int numHiddenLayers) {
        this.numHiddenLayersInt = numHiddenLayers;
        this.numHiddenLayersString = this.doubleToBinaryString(numHiddenLayers - minNumHiddenLayers, hiddenLayerBits);
    }

    public void setNumEpochsInt(int numEpochs) {
        this.numEpochsInt = numEpochs;
        this.numEpochsString = this.doubleToBinaryString(numEpochs - minNumEpochs, epochBits);
    }

    public void setLearningRateDouble(double learningRate) {
        this.learningRateDouble = learningRate;
        this.learningRateString = this.doubleToBinaryString(learningRate - minLearningRate, learningRateBits);
    }

    public void setMomentumDouble(double momentum) {
        this.momentumDouble = momentum;
        this.momentumString = this.doubleToBinaryString(momentum - minMomentum, momentumBits);
    }



    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getNumNeuronsString() {
        return numNeuronsString;
    }

    public String getNumHiddenLayersString() {
        return numHiddenLayersString;
    }

    public String getNumEpochsString() {
        return numEpochsString;
    }

    public String getLearningRateString() {
        return learningRateString;
    }

    public String getMomentumString() {
        return momentumString;
    }


    public double getNumNeuronsInt() {
        return numNeuronsInt;
    }

    public double getNumHiddenLayersInt() {
        return numHiddenLayersInt;
    }

    public int getNumEpochsInt() {
        return numEpochsInt;
    }

    public double getLearningRateDouble() {
        return learningRateDouble;
    }

    public double getMomentumDouble() {
        return momentumDouble;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public String getNumHiddenLayersStringMLP(){
        String numHiddenLayersMLP="";
        for (int i = 0; i < this.numHiddenLayersInt; i++) {
            numHiddenLayersMLP += Integer.toString(this.numNeuronsInt);
            if(i!=numHiddenLayersInt-1){
                numHiddenLayersMLP += ",";
            }
        }
        return numHiddenLayersMLP;
    }

    @Override
    public String toString(){
        return "Binary Strings: "+numNeuronsString+", "+numHiddenLayersString+", "+numEpochsString+", "+learningRateString+", "+momentumString+", \n"+
                "MLP values: "+numNeuronsInt+", "+numHiddenLayersInt+" ( "+getNumHiddenLayersStringMLP()+" ), "+numEpochsInt+", "+learningRateDouble+", "+momentumDouble+", ";
    }

}