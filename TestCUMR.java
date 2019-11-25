package robotv2;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.utility.Delay;

public class TestCUMR {
	public static void main(String args[]) {
		CU yeux = new CU();
		System.out.println("YEUX OK");
		MR roues = new MR();
		System.out.println("ROUES OK");
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		System.out.print(yeux.getDistance());
		Delay.msDelay(2000);
		/*System.out.print(yeux.getDistance());
		Delay.msDelay(2000);
		System.out.print(yeux.getLastDistance());
		Delay.msDelay(2000);
		System.out.print(yeux.getDistance());
		Delay.msDelay(2000);
		roues.tournerD(70);
		roues.sorienterVersEnBut();*/
		roues.avancer(25.0);
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
