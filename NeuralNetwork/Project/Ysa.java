package Project;
import java.io.*;
import java.util.Scanner;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;


public class Ysa 
{

	public static File veriDosya = new File(Ysa.class.getClassLoader().getResource("veri.csv").getFile());


	
	private double[] minumumlar;
	private double[] maksimumlar;
	
	private DataSet veriSeti;
	private DataSet egitimVeriSeti;
	private DataSet testVeriSeti;
	private double[] hatalar;
	private int araKatmanNoronSayisi;
	private int epoch;
	private MomentumBackpropagation mbp;
	private BackPropagation bp;
	
	public Ysa(int araKatmanNoronSayisi,double momentum,double ogrenmeKatsayisi,double maxHata,int epoch) throws FileNotFoundException
	{
		this.araKatmanNoronSayisi = araKatmanNoronSayisi;
		this.epoch = epoch;

		hatalar = new double[epoch];
		minumumlar = new double[3];
		maksimumlar = new double[3];
		for(int i=0;i<3;i++)
		{
			minumumlar[i] = Double.MAX_VALUE;
			maksimumlar[i] = Double.MIN_VALUE;
		}
		minimumVeMaksimumlarBul(veriDosya);
		veriSeti = veriSetiOku(veriDosya);
		

	    DataSet[] trainTestSplit = veriSeti.split(0.75, 0.25); //train test split.
	    egitimVeriSeti = trainTestSplit[0];
	    testVeriSeti = trainTestSplit[1];
	    
	    //backpropagation
	    bp = new BackPropagation();
	    bp.setLearningRate(ogrenmeKatsayisi);
	    bp.setMaxError(maxHata);
	    bp.setMaxIterations(epoch);
	    

		
	    //momentum backpropagation
		mbp = new MomentumBackpropagation();
		mbp.setLearningRate(ogrenmeKatsayisi);
		mbp.setMaxError(maxHata);
		mbp.setMaxIterations(epoch);
		mbp.setMomentum(momentum);
	}
	
	public void egit() //momentumlu backpropagation
	{
		MultiLayerPerceptron sinirselAg = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,2,araKatmanNoronSayisi,1);
		sinirselAg.setLearningRule(mbp);
		sinirselAg.learn(egitimVeriSeti);
		System.out.println("egitim tamamlandi momentumlu.");
		sinirselAg.save("model.nnet");
	}
	public void egitMomentumsuz()//momentumsuz backpropagation
	{
		MultiLayerPerceptron sinirselAg = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,2,araKatmanNoronSayisi,1);
		sinirselAg.setLearningRule(bp);
		int epochCount=0;
		for(int i =0;i<this.epoch;i++)
		{
			sinirselAg.getLearningRule().doOneLearningIteration(egitimVeriSeti);
			hatalar[i] = sinirselAg.getLearningRule().getPreviousEpochError();	
			epochCount++;
		}
		sinirselAg.learn(egitimVeriSeti);
		System.out.println("egitim tamamlandi momentumsuz.");
		System.out.println("epoch sayisisi:"+epochCount);
		sinirselAg.save("modelbp.nnet");
	}
	public double[] getHatalar()
	{
		return hatalar;
	}
	
	public double test() //momentumlu backpropagation
	{
		NeuralNetwork sinirselAg  =   NeuralNetwork.createFromFile("model.nnet");
		double toplamHata =0;
		var satirlar =testVeriSeti.getRows();
		for(var satir : satirlar)
		{
			sinirselAg.setInput(satir.getInput());
			sinirselAg.calculate();
			toplamHata += mse(satir.getDesiredOutput(),sinirselAg.getOutput());	
		}
		return toplamHata/testVeriSeti.size();
	}
	public double testMomentumsuz()//momentumsuz backpropagation
	{
		NeuralNetwork sinirselAg  =   NeuralNetwork.createFromFile("modelbp.nnet");
		double toplamHata =0;
		var satirlar =testVeriSeti.getRows();
		for(var satir : satirlar)
		{
			sinirselAg.setInput(satir.getInput());
			sinirselAg.calculate();
			toplamHata += mse(satir.getDesiredOutput(),sinirselAg.getOutput());	
		}
		return toplamHata/testVeriSeti.size();
	}
	private double mse(double[] beklenen,double[] cikti)
	{
		
		return  Math.pow(beklenen[0]- cikti[0],2);
	}
	public double egitimHata() //momentumlu
	{
		
		return mbp.getTotalNetworkError();
	}
	public double egitimHataMomentumsuz()
	{		
		return bp.getTotalNetworkError();
	}
	
	public Double tekSatirTest(double[] inputs)//momentumlu
	{ 
		double[] inputD = new double[inputs.length];
		for(int i=0;i<inputs.length;i++)
		{
			inputD[i] = minMax(inputs[i],minumumlar[i],maksimumlar[i]);
		}
		NeuralNetwork sinirselAg =  NeuralNetwork.createFromFile("model.nnet");
		sinirselAg.setInput(inputD);
		
		sinirselAg.calculate();
		return gercekCikti(sinirselAg.getOutput());
	}
	public Double tekSatirTestMomentumsuz(double[] inputs)
	{
		double[] inputD = new double[inputs.length];
		for(int i=0;i<inputs.length;i++)
		{
			inputD[i] = minMax(inputs[i],minumumlar[i],maksimumlar[i]);
		}
		NeuralNetwork sinirselAg =  NeuralNetwork.createFromFile("modelbp.nnet");
		sinirselAg.setInput(inputD);
		
		sinirselAg.calculate();
		return gercekCikti(sinirselAg.getOutput());
	}
	private Double gercekCikti(double[] output) //normalize edilen ciktiyi gercek degerine convert 
	{
		 return (output[0]*(maksimumlar[2]-minumumlar[2]))+minumumlar[2];
	}
	
	
	private DataSet veriSetiOku(File dosya) throws FileNotFoundException
	{
		Scanner in = new Scanner(dosya);
	    DataSet ds = new DataSet(2, 1); 

	    while (in.hasNextLine()) {
	        String line = in.nextLine();
	        String[] values = line.split(","); 

	        double[] input = new double[2]; 
	        for (int i = 0; i < 2; i++) {
	            double d = Double.parseDouble(values[i]);
	            input[i] = minMax(d, minumumlar[i], maksimumlar[i]);
	        }

	        double[] output = new double[1];
	        double o = Double.parseDouble(values[2]);
	        output[0] = minMax(o, minumumlar[2], maksimumlar[2]);

	        DataSetRow satir = new DataSetRow(input, output);
	        ds.add(satir);
	    }
	    in.close();
	    return ds;
	    
	}
	private double minMax(double d,double min,double max)
	{
		return (d-min)/(max-min);
	}
	
	private void minimumVeMaksimumlarBul(File dosya) throws FileNotFoundException
	{
		Scanner in = new Scanner(dosya);
		
		while (in.hasNextLine()) {
	        String line = in.nextLine();
	        String[] values = line.split(","); // Virgülle ayrılmış değerleri al

	        for (int i = 0; i < 3; i++) {
	            double d = Double.parseDouble(values[i]);
	            if (d < minumumlar[i]) minumumlar[i] = d;
	            if (d > maksimumlar[i]) maksimumlar[i] = d;
	        }
	    }
	}
	
}
