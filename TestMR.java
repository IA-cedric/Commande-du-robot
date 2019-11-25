package robotv2;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;

public class TestMR {

	public static void main(String[] args) {
		MR roues = new MR();
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		//roues.tournerD(90);
		//roues.sorienterVersEnBut();
		roues.setSpeed(25);
		roues.esquiveD();
		/*roues.setSpeed(100);
		roues.avancer(50);
		roues.setSpeed(0);
		roues.demiTourD();
		roues.avancer(75);
		roues.sorienterVersEnBut();
		roues.avancer();
		while (roues.getEnMouvement()) {
			if (buttons.getButtons() == Keys.ID_ENTER)
				roues.arret();
			buttons.waitForAnyPress();
		}*/

	}

}
