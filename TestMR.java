package robotv33;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.utility.Delay;
import robotv33.Routine.ETAT;

public class TestMR {
	public static void main(String args[]) {
		MR roues1 = new MR();
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit avancer durant 2000ms (2 seconde).");
		
		roues1.avancer();
		Delay.msDelay(2000);
		roues1.arret();
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit avancer durant 2000ms (2 seconde).");
		
		
		roues1.avancer(2000);
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit avancer de 50cm, si le bouton central est actionné, le robot arrete d'avancer.");
		
		roues1.avancer(50.0, true);
		while(roues1.getEnMouvement()) {
			if(buttons.getButtons()==Keys.ID_ENTER) 
				roues1.arret();
		}
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit avancer de 50cm, si le bouton central est actionné, le robot continue d'avancer.");
		
		roues1.avancer(50.0, false);
		while(roues1.getEnMouvement()) {
			if(buttons.getButtons()==Keys.ID_ENTER) 
				roues1.arret();
		}
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit reculer pendant 2000ms (2 seconde).");
		
		roues1.reculer();
		Delay.msDelay(2000);
		roues1.arret();
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit reculer de 50cm.");
		
		roues1.reculer(50.0);
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit reculer pendant 2000ms (2 seconde).");
		
		roues1.reculer(2000);
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit tourner de 90 degrès vers la droite.");
		
		roues1.tournerD(90.0);
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit tourner de 180 degrès vers la droite.");
		
		roues1.tournerD(180.0);
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit tourner de 90 degrès vers la gauche.");
		
		roues1.tournerG(90.0);
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit tourner de 180 degrès vers la gauche.");
		
		roues1.tournerG(180.0);
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit s'orienter à l'opposée, de son orientation initiale.");
		
		roues1.sorienterOpposeEnBut();
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit s'orienter comme son orientation initiale.");
		
		roues1.sorienterVersEnBut();
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit tourner de 90 degrès vers la gauche.");
		
		roues1.tourner(90.0, -1);
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit tourner de 90 degrès vers la droite.");
		
		roues1.tourner(90.0, 1);
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit tourner de 90.0 vers la gauche, si le bouton central est actionné, le robot arrete de tourner.");
		
		roues1.tournerRecherche(90.0, -1);
		while(roues1.getEnMouvement()) {
			if(buttons.getButtons()==Keys.ID_ENTER) 
				roues1.arret();
		}
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit tourner de 90.0 vers la droite, si le bouton central est actionné, le robot arrete de tourner.");
		
		roues1.tournerRecherche(90.0, 1);
		while(roues1.getEnMouvement()) {
			if(buttons.getButtons()==Keys.ID_ENTER) 
				roues1.arret();
		}
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit tourner de 90.0 degres vers la gauche mais ne doit pas s'orienter par la suite car l'orientation n'a pas été modifiée.");
		
		roues1.tournerSO(90.0, -1);
		roues1.sorienterVersEnBut();
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit tourner de 45.0 degres vers la droite mais ne doit pas s'orienter par la suite car l'orientation n'a pas été modifiée.");
		
		roues1.tournerSO(45.0, 1);
		roues1.sorienterVersEnBut();
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit afficher son orientation.");
		
		System.out.println(roues1.getOrientation());
		
		buttons.waitForAnyPress();
		System.out.println("Le robot doit afficher s'il est en mouvement ou non.");
		
		System.out.println(roues1.getEnMouvement());
		

	}
}
