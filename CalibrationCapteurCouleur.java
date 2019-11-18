package prog_robot;


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

public class CalibrationCapteurCouleur {

		SampleProvider average;


	//Constructeur de calibration des couleurs :
		
		public CalibrationCapteurCouleur() { 

			//initialisation du capteur couleur

			Port port = LocalEV3.get().getPort("S2");
			EV3ColorSensor colorSensor = new EV3ColorSensor(port);
			this.average = new MeanFilter(colorSensor.getRGBMode(), 1);
			colorSensor.setFloodlight(Color.WHITE);

			//création d'un tableau de float prenant les échantillons au nombre de 3 pour chaque couleur
			float[] blue = new float[3];
			for (int j = 0; j < 3; j++) {
				System.out.println("Press enter to calibrate blue...");
				Button.ENTER.waitForPressAndRelease();
				float[] value = new float[average.sampleSize()];
				average.fetchSample(value, 0);

				blue[0] += value[0];
				//System.out.println("blue[0]="+blue[0]+", value[0]="+value[0]+ ", j="+j);
				blue[1] += value[1];
				blue[2] += value[2];
			}
			blue[0] = blue[0]/3;
			//System.out.println("moyenne bleu="+blue[0]);
			blue[1] = blue[1]/3;
			blue[2] = blue[2]/3;
			//System.out.println("Blue calibrated"+", blue[0]="+blue[0]);


			float[] red = new float[3];
			for (int j = 0; j < 3; j++) {
				System.out.println("Press enter to calibrate red...");
				Button.ENTER.waitForPressAndRelease();
				float[] value = new float[average.sampleSize()];
				average.fetchSample(value, 0);

				red[0] += value[0];
				red[1] += value[1];
				red[2] += value[2];
			}
			red[0] = red[0]/3;
			red[1] = red[1]/3;
			red[2] = red[2]/3;
			System.out.println("Red calibrated");


			float[] green = new float[3];
			for (int j = 0; j < 3; j++) {
				System.out.println("Press enter to calibrate green...");
				Button.ENTER.waitForPressAndRelease();
				float[] value = new float[average.sampleSize()];
				average.fetchSample(value, 0);

				green[0] += value[0];
				green[1] += value[1];
				green[2] += value[2];
			}
			green[0] = green[0]/3;
			green[1] = green[1]/3;
			green[2] = green[2]/3;
			System.out.println("Green calibrated");

			float[] black = new float[3];
			for (int j = 0; j < 3; j++) {
				System.out.println("Press enter to calibrate black...");
				Button.ENTER.waitForPressAndRelease();
				float[] value = new float[average.sampleSize()];
				average.fetchSample(value, 0);

				black[0] += value[0];
				black[1] += value[1];
				black[2] += value[2];
			}
			black[0] = black[0]/3;
			black[1] = black[1]/3;
			black[2] = black[2]/3;
			System.out.println("Black calibrated");


			float[] white = new float[3];
			for (int j = 0; j < 3; j++) {
				System.out.println("Press enter to calibrate white...");
				Button.ENTER.waitForPressAndRelease();
				float[] value = new float[average.sampleSize()];
				average.fetchSample(value, 0);

				white[0] += value[0];
				white[1] += value[1];
				white[2] += value[2];
			}
			white[0] = white[0]/3;
			white[1] = white[1]/3;
			white[2] = white[2]/3;
			System.out.println("white calibrated");


			float[] gray = new float[3];
			for (int j = 0; j < 3; j++) {
				System.out.println("Press enter to calibrate gray...");
				Button.ENTER.waitForPressAndRelease();
				float[] value = new float[average.sampleSize()];
				average.fetchSample(value, 0);

				gray[0] += value[0];
				gray[1] += value[1];
				gray[2] += value[2];
			}
			gray[0] = gray[0]/3;
			gray[1] = gray[1]/3;
			gray[2] = gray[2]/3;
			System.out.println("gray calibrated");



			float[] yellow = new float[3];
			for (int j = 0; j < 3; j++) {
				System.out.println("Press enter to calibrate yellow...");
				Button.ENTER.waitForPressAndRelease();
				float[] value = new float[average.sampleSize()];
				average.fetchSample(value, 0);

				yellow[0] += value[0];
				yellow[1] += value[1];
				yellow[2] += value[2];
			}
			yellow[0] = yellow[0]/3;
			yellow[1] = yellow[1]/3;
			yellow[2] = yellow[2]/3;
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
				colors.store(new FileOutputStream("colorFinal.txt"), "");
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
