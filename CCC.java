package robotv33;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.MeanFilter;

public class CCC {
	
		SampleProvider average;
		public static void main(String []args) {
			CCC c = new CCC();
		}

	//Constructeur de calibration des couleurs :
		
		public CCC() { 

			//initialisation du capteur couleur

			Port port = LocalEV3.get().getPort("S2");
			EV3ColorSensor colorSensor = new EV3ColorSensor(port);
			
			this.average = new MeanFilter(colorSensor.getRGBMode(), 1);
			colorSensor.setFloodlight(Color.WHITE);

			//création d'un tableau de float prenant les échantillons au nombre de 3 pour chaque couleur
				float[] blue = new float[3];
				System.out.println("Press enter to calibrate blue...");
				Button.ENTER.waitForPressAndRelease();
				float[] value = new float[average.sampleSize()];
				average.fetchSample(value, 0);

				blue[0] += value[0];
				blue[1] += value[1];
				blue[2] += value[2];
				
				System.out.println("blue[0]="+blue[0]+"blue[1]="+blue[1]+"blue[2]="+blue[2]);
				System.out.println("Blue calibrated");
			


			float[] red = new float[3];
			System.out.println("Press enter to calibrate red...");
			Button.ENTER.waitForPressAndRelease();
			float[] value1 = new float[average.sampleSize()];
			average.fetchSample(value1, 0);

				red[0] += value1[0];
				red[1] += value1[1];
				red[2] += value1[2];
		
			System.out.println("red[0]="+red[0]+"red[1]="+red[1]+"red[2]="+red[2]);
			System.out.println("Red calibrated");


			float[] green = new float[3];
				System.out.println("Press enter to calibrate green...");
				Button.ENTER.waitForPressAndRelease();
				float[] value11 = new float[average.sampleSize()];
				average.fetchSample(value11, 0);

				green[0] += value11[0];
				green[1] += value11[1];
				green[2] += value11[2];
				
			System.out.println("green[0]="+green[0]+"green[1]="+green[1]+"green[2]="+green[2]);
			System.out.println("Green calibrated");

			
			float[] black = new float[3];
				System.out.println("Press enter to calibrate black...");
				Button.ENTER.waitForPressAndRelease();
				float[] value3 = new float[average.sampleSize()];
				average.fetchSample(value3, 0);

				black[0] += value3[0];
				black[1] += value3[1];
				black[2] += value3[2];
		
			System.out.println("green[0]="+green[0]+"green[1]="+green[1]+"green[2]="+green[2]);
			System.out.println("Black calibrated");


			float[] white = new float[3];
				System.out.println("Press enter to calibrate white...");
				Button.ENTER.waitForPressAndRelease();
				float[] value12 = new float[average.sampleSize()];
				average.fetchSample(value12, 0);

				white[0] += value12[0];
				white[1] += value12[1];
				white[2] += value12[2];
			
			System.out.println("white[0]="+white[0]+"white[1]="+white[1]+"white[2]="+white[2]);
			System.out.println("white calibrated");


			float[] gray = new float[3];
				System.out.println("Press enter to calibrate gray...");
				Button.ENTER.waitForPressAndRelease();
				float[] value13 = new float[average.sampleSize()];
				average.fetchSample(value13, 0);

				gray[0] += value13[0];
				gray[1] += value13[1];
				gray[2] += value13[2];
			
			System.out.println("gray[0]="+gray[0]+"gray[1]="+gray[1]+"gray[2]="+gray[2]);
			System.out.println("gray calibrated");



			float[] yellow = new float[3];
				System.out.println("Press enter to calibrate yellow...");
				Button.ENTER.waitForPressAndRelease();
				float[] value14 = new float[average.sampleSize()];
				average.fetchSample(value14, 0);

				yellow[0] += value14[0];
				yellow[1] += value14[1];
				yellow[2] += value14[2];
			
			System.out.println("yellow[0]="+yellow[0]+"yellow[1]="+yellow[1]+"yellow[2]="+yellow[2]);	
			System.out.println("yellow calibrated");


			// STOCKAGE DANS UN FICHIER DES DONNEES ISSUES DU CAPTEUR COULEUR

			Properties colors = new Properties(); 

			colors.setProperty("yellow", yellow[0] + "," + yellow[1] + "," + yellow[2]);

			colors.setProperty("gray", gray[0] + "," + gray[1] + "," + gray[2]);

			colors.setProperty("green", green[0] + "," + green[1] + "," + green[2]);

			colors.setProperty("blue", blue[0] + "," + blue[1] + "," + blue[2]);

			colors.setProperty("red", red[0] + "," + red[1] + "," + red[2]);

			colors.setProperty("black", black[0] + "," + black[1] + "," + black[2]);

			colors.setProperty("white", white[0] + "," + white[1] + "," + white[2]);

			try {
				colors.store(new FileOutputStream("colorTESTFINAL.txt"), "");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			colorSensor.close();

		}

		//FIN DU CONSTRUCTEUR DE CALIBRATION


}
