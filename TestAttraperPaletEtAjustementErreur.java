package robotv33;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;

public class TestAttraperPaletEtAjustementErreur {
	public enum ETAT{ATTRAPER,AJUSTEMENT;}
	public static void main(String args[]) {
		Robot cedric = new Robot();
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		ETAT etat =ETAT.ATTRAPER;
		boolean test = true;
		boolean attraper;
		double distance;
		System.out.println("Appuyez sur le bouton central");
		System.out.println("pour (re)lancer le test.");
		System.out.println("Appuyez sur le bouton du bas");
		System.out.println("pour quitter le programme.");
		while(test) {
			if (buttons.getButtons() == Keys.ID_ENTER) {
				etat=ETAT.ATTRAPER;
				attraper=true;
				cedric.setPalet(false);
				distance=30.0+70.0*Math.random();
				while(attraper) {
					switch(etat) {
					case ATTRAPER:
						cedric.setPalet(cedric.attraperPalet(distance));
						if(cedric.isPalet()) 
							attraper=false;
						else
							etat=etat.AJUSTEMENT;
						System.out.println(etat);	
						break;
		
					case AJUSTEMENT:
						cedric.ajustementErreur(distance);
						attraper=false;
						break;
					}
				}
			}
				
			if(buttons.getButtons() == Keys.ID_DOWN) 
				test=false;
		}
	}
}
