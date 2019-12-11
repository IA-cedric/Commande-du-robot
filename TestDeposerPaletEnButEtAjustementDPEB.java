package robotv33;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import robotv33.CC.Couleur;

public class TestDeposerPaletEnButEtAjustementDPEB {
	public static void main(String args[]) {
		Robot cedric = new Robot();
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		boolean test = true;
		boolean attraper;
		boolean start=true;
		Couleur color = Couleur.Blanc;
		System.out.println("Appuyez sur le bouton central");
		System.out.println("pour (re)lancer le test.");
		System.out.println("Appuyez sur le bouton du bas");
		System.out.println("pour quitter le programme.");
		while(start) {
			if(buttons.getButtons()==Keys.ID_UP) {
				cedric.setCasier(true);
				System.out.println(cedric.isCasier());
			}
			if(buttons.getButtons()==Keys.ID_DOWN) {
				cedric.setCasier(false);
				System.out.println(cedric.isCasier());
			}
			if (buttons.getButtons() == Keys.ID_ENTER) {
				start=false;
			}
		}
		while(test) {
			if (buttons.getButtons() == Keys.ID_ENTER) {
				attraper=true;
				cedric.setPalet(true);
				while(attraper) {
					color = cedric.deposerPaletEnBut();
					if(color==null) 
						attraper=false;	
					else
						cedric.ajustementDPEB(color);	
				}
			}

			if(buttons.getButtons() == Keys.ID_DOWN) 
				test=false;
		}
	}
}
