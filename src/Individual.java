public class Individual {
    // TOPOLOGY HYPER-PARAMETERS
    private String numNeurons;
    private String numHiddenLayers;
    private String numEpochs;
    private String learningRate;
    private String momentum;

    // VALID RANGES OF THE TOPOLOGY OF THE MULTILAYER PERCEPTRON NEURAL NETWORK
    private byte minNumNeurons;
    private byte maxNumNeurons;
    private byte minNumHiddenLayers;
    private byte maxNumHiddenLayers;
    private short minNumEpochs;
    private short maxNumEpochs;
    private double minLearningRate;
    private double maxLearningRate;
    private double minMomentum;
    private double maxMomentum;

    private double accuracy;

    public Individual(){
        this.numNeurons = new String();
        this.numHiddenLayers = new String();
        this.numEpochs = new String();
        this.learningRate = new String();
        this.momentum = new String();

        this.setDefaultRanges();
    }

    public Individual(String numNeurons, String numHiddenLayers, String numEpochs, String learningRate, String momentum){
        this.numNeurons = numNeurons;
        this.numHiddenLayers = numHiddenLayers;
        this.numEpochs = numEpochs;
        this.learningRate = learningRate;
        this.momentum = momentum;

        this.setDefaultRanges();
    }

    /***********START OF IMPORTANT SECTION**************/
    /**
     * validates whether the geneticSequence of this individual is within the
     * range of the previously specified range of the topology of the Neural Network.
     * The values used for this project are specified in setDefaultRanges()
     * @return boolean that indicates whether the geneticSequence is valid
     */
    public boolean validate(){
        // Converts binary to decimal
        byte neurons = binaryStringToByte(this.numNeurons);
        byte hiddenLayers = binaryStringToByte(this.numHiddenLayers);
        short epochs = binaryStringToShort(this.numEpochs);
        // The following parameters are divided by 100 because binary numbers
        // don't handle decimals by default.
        // So [0, 100] (binaryString) --> [0.00, 1.00] with the following operations:
        double LR = binaryStringToInt(this.learningRate) / 100.0d;
        double moment = binaryStringToInt(this.momentum) / 100.0d;

        if(!(neurons >= minNumNeurons && neurons <= maxNumNeurons))
            return false;

        if(!(hiddenLayers >= minNumHiddenLayers && hiddenLayers <= maxNumHiddenLayers))
            return false;

        if (!(epochs >= minNumEpochs && epochs <= maxNumEpochs))
            return false;

        if (Double.compare(LR, 0.11d) != 0 || Double.compare(LR, 0.13d) != 0
                || Double.compare(LR, 0.15d) != 0 || Double.compare(LR, 0.17d) != 0)
            return false;

        if(Double.compare(moment, 0.08d) != 0 || Double.compare(moment, 0.10d) != 0
                || Double.compare(moment, 0.12d) != 0 || Double.compare(moment, 0.14d) != 0
                || Double.compare(moment, 0.16d) != 0|| Double.compare(moment, 0.18d) != 0)
            return false;

        return true;
    }

    public byte binaryStringToByte(String binaryString){
        return Byte.parseByte(binaryString, 2);
    }

    public short binaryStringToShort(String binaryString){
        return Short.parseShort(binaryString, 2);
    }

    public int binaryStringToInt(String binaryString){
        return Integer.parseInt(binaryString, 2);
    }

    public String getGeneticSequence(){
        return this.numNeurons + this.numHiddenLayers + this.numEpochs + this.learningRate + this.momentum;
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


    public void setNumNeurons(String numNeurons) {
        this.numNeurons = numNeurons;
    }

    public void setNumHiddenLayers(String numHiddenLayers) {
        this.numHiddenLayers = numHiddenLayers;
    }

    public void setNumEpochs(String numEpochs) {
        this.numEpochs = numEpochs;
    }

    public void setLearningRate(String learningRate) {
        this.learningRate = learningRate;
    }

    public void setMomentum(String momentum) {
        this.momentum = momentum;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getNumNeurons() {
        return numNeurons;
    }

    public String getNumHiddenLayers() {
        return numHiddenLayers;
    }

    public String getNumEpochs() {
        return numEpochs;
    }

    public String getLearningRate() {
        return learningRate;
    }

    public String getMomentum() {
        return momentum;
    }

    public double getAccuracy() {
        return accuracy;
    }

}