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
		
		System.out.println("Bonjour, bienvenue dans le programme de Test de la classe MR.");
		System.out.println("Il suffit d'appuyer sur n'importe quel bouton pour commencer ou passer à l'étape suivante.");
		
		System.out.println("Le robot doit avancer durant 2000ms (2 seconde).");
		buttons.waitForAnyPress();
		
		roues1.avancer();
		Delay.msDelay(2000);
		roues1.arret();
		
		System.out.println("Le robot doit avancer durant 2000ms (2 seconde).");
		buttons.waitForAnyPress();
		
		
		roues1.avancer(2000);
		
		System.out.println("Le robot doit avancer de 50cm, si le bouton central est actionné, le robot arrete d'avancer.");
		buttons.waitForAnyPress();
		
		roues1.avancer(50.0, true);
		while(roues1.getEnMouvement()) {
			if(buttons.getButtons()==Keys.ID_ENTER) 
				roues1.arret();
		}
		
		System.out.println("Le robot doit avancer de 50cm, si le bouton central est actionné, le robot continue d'avancer.");
		buttons.waitForAnyPress();
		
		roues1.avancer(50.0, false);
		while(roues1.getEnMouvement()) {
			if(buttons.getButtons()==Keys.ID_ENTER) 
				roues1.arret();
		}
		
		System.out.println("Le robot doit reculer pendant 2000ms (2 seconde).");
		buttons.waitForAnyPress();
		
		roues1.reculer();
		Delay.msDelay(2000);
		roues1.arret();
		
		System.out.println("Le robot doit reculer de 50cm.");
		buttons.waitForAnyPress();
		
		roues1.reculer(50.0);
		
		System.out.println("Le robot doit reculer pendant 2000ms (2 seconde).");
		buttons.waitForAnyPress();
		
		roues1.reculer(2000);
		
		System.out.println("Le robot doit tourner de 90 degrès vers la droite.");
		buttons.waitForAnyPress();
		
		roues1.tournerD(90.0);
		
		System.out.println("Le robot doit tourner de 180 degrès vers la droite.");
		buttons.waitForAnyPress();
		
		roues1.tournerD(180.0);
		
		System.out.println("Le robot doit tourner de 90 degrès vers la gauche.");
		buttons.waitForAnyPress();
		
		roues1.tournerG(90.0);
		
		System.out.println("Le robot doit tourner de 180 degrès vers la gauche.");
		buttons.waitForAnyPress();
		
		roues1.tournerG(180.0);
		
		System.out.println("Le robot doit s'orienter à l'opposée, de son orientation initiale.");
		buttons.waitForAnyPress();
		
		roues1.sorienterOpposeEnBut();
		
		System.out.println("Le robot doit s'orienter comme son orientation initiale.");
		buttons.waitForAnyPress();
		
		roues1.sorienterVersEnBut();
		
		System.out.println("Le robot doit tourner de 90 degrès vers la gauche.");
		buttons.waitForAnyPress();
		
		roues1.tourner(90.0, -1);
		
		System.out.println("Le robot doit tourner de 90 degrès vers la droite.");
		buttons.waitForAnyPress();
		
		roues1.tourner(90.0, 1);
		
		System.out.println("Le robot doit tourner de 90.0 vers la gauche, si le bouton central est actionné, le robot arrete de tourner.");
		buttons.waitForAnyPress();
		
		roues1.tournerRecherche(90.0, -1);
		while(roues1.getEnMouvement()) {
			if(buttons.getButtons()==Keys.ID_ENTER) 
				roues1.arret();
		}
		
		System.out.println("Le robot doit tourner de 90.0 vers la droite, si le bouton central est actionné, le robot arrete de tourner.");
		buttons.waitForAnyPress();
		
		roues1.tournerRecherche(90.0, 1);
		while(roues1.getEnMouvement()) {
			if(buttons.getButtons()==Keys.ID_ENTER) 
				roues1.arret();
		}
		
		System.out.println("Le robot doit tourner de 90.0 degres vers la gauche mais ne doit pas s'orienter par la suite car l'orientation n'a pas été modifiée.");
		buttons.waitForAnyPress();
		
		roues1.tournerSO(90.0, -1);
		roues1.sorienterVersEnBut();
		
		System.out.println("Le robot doit tourner de 45.0 degres vers la droite mais ne doit pas s'orienter par la suite car l'orientation n'a pas été modifiée.");
		buttons.waitForAnyPress();
		
		roues1.tournerSO(45.0, 1);
		roues1.sorienterVersEnBut();
		
		System.out.println("Le robot doit afficher son orientation.");
		buttons.waitForAnyPress();
		
		System.out.println(roues1.getOrientation());
		
		System.out.println("Le robot doit afficher s'il est en mouvement ou non.");
		buttons.waitForAnyPress();
		
		System.out.println(roues1.getEnMouvement());
		

	}
}
