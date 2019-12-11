package robotv33;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;

public class TestRetablirPince {
	public static void main(String args[]) {
		Robot cedric = new Robot();
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		boolean test = true;
		System.out.println("Appuyez sur le bouton central");
		System.out.println("pour (re)lancer le test.");
		System.out.println("Appuyez sur le bouton du bas");
		System.out.println("pour quitter le programme.");
		while(test) {
			if (buttons.getButtons() == Keys.ID_ENTER)
				cedric.retablirPince();
			if(buttons.getButtons() == Keys.ID_DOWN) 
				test=false;
		}
	}
}
