package Project;
import java.io.IOException;
import java.util.Scanner;

import java.io.FileWriter;


public class program 
{

	public static void main(String[] args) throws IOException 
	{
		
		
		Ysa ysa = null;
		Scanner in = new Scanner(System.in);
		int araKatmanNoronSayisi;
		double momentum,ogrenmeKatsayisi,maxError;
		int epoch,sec;
		do{
			System.out.println("1.Egitim ve test");
			System.out.println("2. Tek satir test");
			System.out.println("3. hatalar");
			
			System.out.println("4. cikis");
			sec = in.nextInt();
			switch(sec)
			{
				case 1:
					System.out.println("arakatman noron sayisi");
					araKatmanNoronSayisi = in.nextInt();

					System.out.println("momentum");
					momentum = in.nextDouble();

					System.out.println("ogrenme katsayisi");
					ogrenmeKatsayisi = in.nextDouble();
					
					System.out.println("max hata");
					maxError = in.nextDouble();
					
					System.out.println("epoch");
					epoch = in.nextInt();
					
					ysa = new Ysa(araKatmanNoronSayisi,momentum,ogrenmeKatsayisi,maxError,epoch);
					
					
					ysa.egit();
					ysa.egitMomentumsuz();
					String egitimHataFormatli = String.format("%.10f", ysa.egitimHata());
				    String testHataFormatli = String.format("%.10f", ysa.test());

					String egitimHataFormatliMomentumsuz = String.format("%.10f", ysa.egitimHataMomentumsuz());
				    String testHataFormatliMomentumsuz = String.format("%.10f", ysa.testMomentumsuz());

			        System.out.println("Egitimdeki hata Momentumlu: " + egitimHataFormatli);
			        System.out.println("Test hata Momentumlu: " + testHataFormatli);
			        System.out.println("Egitimdeki hata Momentumsuz: " + egitimHataFormatliMomentumsuz);
			        System.out.println("Test hata Momentumsuz: " + testHataFormatliMomentumsuz);
					break;
				case 2 :
					if(ysa!=null)
					{
						double[] inputs = new double[2];
						System.out.println("Voltaj[2.5-4.2]V:");
						
						inputs[0] = in.nextDouble();

						System.out.println("Sicaklik[0-45]C:");

						inputs[1] = in.nextDouble();

						double cikti = ysa.tekSatirTest(inputs);
						double cikti2  =ysa.tekSatirTestMomentumsuz(inputs);
						System.out.println("Momentumlu Cikti Doluluk%: "+ cikti);
						System.out.println("Momentumsuz Cikti Doluluk%: "+ cikti2);
					}
					break;
				case 3:
					String csvFile = "hatalar.csv"; 

			        try (FileWriter writer = new FileWriter(csvFile)) //hatalar .csv dosyasına eklenir
			        {
			 
			            writer.append("Hata\n");
			            double[] hatalar = ysa.getHatalar();
						for(double hata :hatalar)
						{
							
							 writer.append(String.format("%.10f\n", hata));
							System.out.println(String.format("%.10f\n", hata));
						}
						
			        }
					
					break;
			
			}	
		}while(sec!=4);
	}
	
}
