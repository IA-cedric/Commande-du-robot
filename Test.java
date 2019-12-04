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
		while(x) {
			if(buttons.getButtons()==Keys.ID_ENTER) {
				System.out.println(test.attraperPalet(55.0));
			}
			if(buttons.getButtons()==Keys.ID_DOWN)
				x=false;
		}
		
	}
}
