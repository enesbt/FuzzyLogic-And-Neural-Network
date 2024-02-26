package Project;

import java.net.URISyntaxException;
import java.util.Scanner;

import Project.Pil;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

public class Program {

	public static void main(String[] args) 
	{
		Scanner in = new Scanner(System.in);
		System.out.print("Voltaj V[2.5-4.2]:");
		double voltaj = in.nextDouble();
		System.out.print("Sicaklik C°[0-45]:");
		double sicaklik = in.nextDouble();
		if(voltaj>4.2||voltaj<2.5||sicaklik>45||sicaklik<0)
			System.out.println("Gecerli aralik giriniz");
		else 
		{
			try {
				
				Pil pil = new Pil(voltaj,sicaklik);
				
				var kurallar = pil.getmodel().getFunctionBlock("Pil").getFuzzyRuleBlock("kuralBlok1").getRules();
				for(var kural:kurallar)
				{
					if(kural.getDegreeOfSupport()>0)System.out.println(kural);
				}
				FIS fis = pil.getmodel();
				System.out.println(pil);
				JFuzzyChart.get().chart(fis);
				JFuzzyChart.get().chart(fis.getVariable("doluluk").getDefuzzifier(),"doluluk",true); //sadece tarali alan
				
				
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

	}

}
