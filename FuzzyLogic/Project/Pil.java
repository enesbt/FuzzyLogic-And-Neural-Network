package Project;

import java.io.File;
import java.net.URISyntaxException;

import net.sourceforge.jFuzzyLogic.FIS;

public class Pil 
{
	private FIS fis;
	private double voltaj;
	private double sicaklik;
	public Pil(double voltaj,double sicaklik) throws URISyntaxException
	{
		this.voltaj = voltaj;
		this.sicaklik = sicaklik;
		
		File file = new File(getClass().getResource("Pil.fcl").toURI());
		fis = FIS.load(file.getPath(),true);
		fis.setVariable("voltaj", voltaj);
		fis.setVariable("sicaklik",sicaklik);
		fis.evaluate();
	}
	public FIS getmodel()
	{
		return fis;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Doluluk Orani: "+fis.getVariable("doluluk").getValue()+"%";
	}

}
