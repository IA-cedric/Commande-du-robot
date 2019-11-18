package prog_robot;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;

public class CapteurCouleur extends EV3ColorSensor {

	double[] tabDoubleBlue = new double[3];
	double[] tabDoubleRed = new double[3];
	double[] tabDoubleGreen = new double[3];
	double[] tabDoubleBlack = new double[3];
	double[] tabDoubleWhite = new double[3];
	double[] tabDoubleGray = new double[3];
	double[] tabDoubleYellow = new double[3];
	
	double[] tabScalaires = new double[7];

	static SampleProvider average;

	/*public enum Couleur {
		LUNDI, MARDI, MERCREDI, JEUDI, VENDREDI, SAMEDI, DIMANCHE;
	}*/
	
	public CapteurCouleur(Port p) {
		super(p);
	}

	//initialise les vecteurs
	public void init() throws IOException { 


		//initialisation du port


		//ouverture du flux en lecture
		FileInputStream input = new FileInputStream("colorFinal.txt"); 
		Properties prop = new Properties();

		try{
			prop.load(input);
		}finally{
			input.close();
		}

	//renvoie chaque ligne du fichier colorFinal sous forme de chaine de caractère
		String blue = prop.getProperty("blue");
		//System.out.println("blue is" +blue);
		String red = prop.getProperty("red");
		//System.out.println("red is" +red);
		String green = prop.getProperty("green");
		//System.out.println("green is" +green);
		String black = prop.getProperty("black");
		String white = prop.getProperty("white");
		String gray = prop.getProperty("gray");
		String yellow = prop.getProperty("yellow");

	// renvoie un tableau de string sous forme : "blue" ,"blue[0]", "blue[1]", "blue[2]"
		String[] blueDetail = blue.split(",");
		String[] redDetail = red.split(",");
		String[] greenDetail = green.split(",");
		String[] blackDetail = black.split(",");
		String[] whiteDetail = white.split(",");
		String[] grayDetail = gray.split(",");
		String[] yellowDetail = yellow.split(",");

	//convertir tableau de string en tableau de float
		for(int i=0; i<tabDoubleBlue.length; i++) {
			this.tabDoubleBlue[i]= Double.parseDouble(blueDetail[i]);
		}

		for(int i=0; i<tabDoubleRed.length; i++) {
			tabDoubleRed[i]= Double.parseDouble(redDetail[i]);
		}

		for(int i=0; i<tabDoubleGreen.length; i++) {
			tabDoubleGreen[i]= Double.parseDouble(greenDetail[i]);
		}

		for(int i=0; i<tabDoubleBlack.length; i++) {
			tabDoubleBlack[i]= Double.parseDouble(blackDetail[i]);
		}

		for(int i=0; i<tabDoubleWhite.length; i++) {
			tabDoubleWhite[i]= Double.parseDouble(whiteDetail[i]);
		}

		for(int i=0; i<tabDoubleGray.length; i++) {
			tabDoubleGray[i]= Double.parseDouble(grayDetail[i]);
		}

		for(int i=0; i<tabDoubleYellow.length; i++) {
			tabDoubleYellow[i]= Double.parseDouble(yellowDetail[i]);
		}

	}

	
	//METHODE RENVOIE LE INT CORRESPONDANT A LA COULEUR
	
	//public Couleur getColor() {
	public int getColor() {
		
	//récupération des données issues du capteur
		float[] sample = new float[average.sampleSize()]; //création d'un tableau d'échantillons 
		//System.out.println("\nPress enter to detect a color...");
		//Button.ENTER.waitForPressAndRelease();
		average.fetchSample(sample, 0);
		double minscal = Double.MAX_VALUE;
		String color = "";


		//création d'un tableau contenant les scalaires des couleurs

		double scalaireBlue = CapteurCouleur.scalaire(sample, tabDoubleBlue);
		//System.out.println("saclaire bleu=" +scalaireBlue);
		//	Button.waitForAnyPress();

		double scalaireRed = CapteurCouleur.scalaire(sample, tabDoubleRed);
		//System.out.println("saclaire rouge=" +scalaireRed);
		//Button.waitForAnyPress();

		double scalaireGreen = CapteurCouleur.scalaire(sample, tabDoubleGreen);
		//System.out.println("saclaire vert=" +scalaireGreen);
		//Button.waitForAnyPress();

		double scalaireBlack = CapteurCouleur.scalaire(sample, tabDoubleBlack);
		//System.out.println("saclaire noir=" +scalaireBlack);
		//Button.waitForAnyPress();

		double scalaireWhite = CapteurCouleur.scalaire(sample, tabDoubleWhite);
		//System.out.println("saclaire blanc=" +scalaireWhite);
		//Button.waitForAnyPress();

		double scalaireGray = CapteurCouleur.scalaire(sample, tabDoubleGray);
		//System.out.println("saclaire gris=" +scalaireGray);
		//Button.waitForAnyPress();

		double scalaireYellow = CapteurCouleur.scalaire(sample, tabDoubleYellow);
		//System.out.println("saclaire jaune=" +scalaireYellow);
		//Button.waitForAnyPress();


		tabScalaires[0]=scalaireBlue;
		tabScalaires[1]=scalaireRed;
		tabScalaires[2]=scalaireGreen;
		tabScalaires[3]=scalaireBlack;
		tabScalaires[4]=scalaireWhite;
		tabScalaires[5]=scalaireGray;
		tabScalaires[6]=scalaireYellow;

		
		//faire un type énuméré
		String[] tabcolString={"Blue","Red","Green","Black","White","Gray","Yellow"};

		
		int j = 0;

		for(int i=0; i<tabScalaires.length ;i++) {
		//	System.out.println("i="+i);
			if (tabScalaires[i]<minscal) {
				minscal=tabScalaires[i];
				color = tabcolString[i];
				j = i;
				//	System.out.println("col:"+color);
				//Button.waitForAnyPress();
				//System.out.println("i="+i);
				//Button.waitForAnyPress();
				//System.out.println("minscal="+minscal);
				//Button.waitForAnyPress();
		}  	
		System.out.println(tabcolString[i]+"pas inferieur à "+color+ "|ou j="+j);
		Button.waitForAnyPress();


	}
		
		//créer un tableau correspondant aux couleurs
		int[] tabcol = {0,1,2,3,4,5,6};

		//retourne le numéro correspondant à la couleur
		System.out.println("couleur finale="+tabcolString[j]);
		Button.waitForAnyPress();
		return tabcol[j];

	}


	//METHODE DU CALCUL DES SCALAIRES
	public static double scalaire(float[] v1, double[] v2) { //
		return Math.sqrt (Math.pow(v1[0] - v2[0], 2.0) +
				Math.pow(v1[1] - v2[1], 2.0) +
				Math.pow(v1[2] - v2[2], 2.0));
	}



	public static void main(String[]args) throws IOException {
		//CalibrationCapteurCouleur calibration = new CalibrationCapteurCouleur();
		
		Port port = LocalEV3.get().getPort("S2");
		CapteurCouleur colorSensor = new CapteurCouleur(port);
		//colorSensor.close();

		average = new MeanFilter(colorSensor.getRGBMode(), 1);
		colorSensor.setFloodlight(Color.WHITE);
		
		
		colorSensor.init();
		System.out.println("la couleur est : "+colorSensor.getColor());
		Button.waitForAnyPress();

		colorSensor.close();
		
	}
}





