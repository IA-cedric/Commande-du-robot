package robotV2;

import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;

public class testCC {
	 
	/**
	 * test la classe de capteur couleur. 
	 */
	public static void main(String[]args){
	
		
		CC colorSensor = new CC();
		
		System.out.println("Appuyez sur n'importe quel bouton à chaque fois");
		Button.waitForAnyPress();
		
		System.out.println("Posez le robot sur la couleur bleu");
		Button.waitForAnyPress();
		System.out.println("la couleur est : "+colorSensor.getColor() +"= bleu?");
		Button.waitForAnyPress();
		
		System.out.println("Posez le robot sur la couleur jaune");
		Button.waitForAnyPress();
		System.out.println("la couleur est : "+colorSensor.getColor() +"= jaune?");
		Button.waitForAnyPress();
		
		System.out.println("Posez le robot sur la couleur vert");
		Button.waitForAnyPress();
		System.out.println("la couleur est : "+colorSensor.getColor() +"= vert?");
		Button.waitForAnyPress();
		
		System.out.println("Posez le robot sur la couleur rouge");
		Button.waitForAnyPress();
		System.out.println("la couleur est : "+colorSensor.getColor() +"= rouge?");
		Button.waitForAnyPress();
		
		System.out.println("Posez le robot sur la couleur gris");
		Button.waitForAnyPress();
		System.out.println("la couleur est : "+colorSensor.getColor() +"= gris");
		Button.waitForAnyPress();
		
		System.out.println("Posez le robot sur la couleur noir");
		Button.waitForAnyPress();
		System.out.println("la couleur est : "+colorSensor.getColor() +"= noir?");
		Button.waitForAnyPress();
		
		System.out.println("Posez le robot sur la couleur blanc");
		Button.waitForAnyPress();
		System.out.println("la couleur est : "+colorSensor.getColor() +"= blanc?");
		Button.waitForAnyPress();
	
		colorSensor.close();

	}
}
