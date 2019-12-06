package robotv3;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import robotv3.CC.Couleur;

public class Test {
	public static void main(String args[]) {
		Robot test = new Robot();
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		boolean x = true;
		double distance;
		System.out.println("OK");
		test.retablirPince();
		test.setDirection(1);
		while(x) {
			if(buttons.getButtons()==Keys.ID_ENTER) {
				test.setPalet(false);
				distance=test.rotationRecherche(110.0);
				test.attraperPalet(distance);
			}
			if(buttons.getButtons()==Keys.ID_DOWN)
				x=false;
		}
		
	}
}
