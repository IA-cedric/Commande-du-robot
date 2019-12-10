package robotv33;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import robotv33.CC.Couleur;
/**
 * Cette classe test la RoutineCompetition 
 * @author benjamin
 * @see Robot 
 * 
 */

public class RoutineCompetition_Test {
	public enum ETAT {TEST,PREMIER_PALET,ROTATION_RECHERCHE, ATTRAPER_PALET, DEPOSER_PALET_ENBUT, ERROR, FIN;}
	
	public static void main(String[] args) {
		Robot cedric = new Robot();
		boolean start = true;
		double distance =0;
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		ETAT etat = ETAT.PREMIER_PALET;
		boolean routine = true;
		boolean y=true;
		Couleur color = null;
		System.out.println("Cedric is ready to go !");
		while(start) {
			if(buttons.getButtons()==Keys.ID_UP) {
				cedric.setCasier(true);
			}
			if(buttons.getButtons()==Keys.ID_DOWN) {
				cedric.setCasier(false);
			}
			if (buttons.getButtons() == Keys.ID_ENTER) {
				cedric.retablirPince();
			}
			if (buttons.getButtons() == Keys.ID_LEFT) {
				start=false;
				cedric.setDirection(-1);
			} else if (buttons.getButtons() == Keys.ID_RIGHT) {
				start=false;
				cedric.setDirection(1);
			}
		}
		System.out.println(etat);
		while(routine) {
			switch(etat) {
				case PREMIER_PALET:
					y=true;
					cedric.premierPalet();
					etat=ETAT.ROTATION_RECHERCHE;
					System.out.println(etat);
					while(y) {
						if(buttons.getButtons()==Keys.ID_ENTER) {
							y=false;
						}else if(buttons.getButtons()==Keys.ID_DOWN) {
							y=false;
							etat=ETAT.FIN;
						}
					}
					break;
				case ROTATION_RECHERCHE: 
					y=true;
					distance = cedric.rotationRecherche();
//					direction=direction*-1;
					etat = ETAT.ATTRAPER_PALET;
					System.out.println(etat);
					while(y) {
						if(buttons.getButtons()==Keys.ID_ENTER) {
							y=false;
						}else if(buttons.getButtons()==Keys.ID_DOWN) {
							y=false;
							etat=ETAT.FIN;
						}
					}
					break;
				case ATTRAPER_PALET:
					y=true;
					cedric.setPalet(cedric.attraperPalet(distance));
					if(cedric.isPalet()) 
						etat=ETAT.DEPOSER_PALET_ENBUT;
					else
						etat=ETAT.ERROR;
					System.out.println(etat);
					while(y) {
						if(buttons.getButtons()==Keys.ID_ENTER) {
							y=false;
						}else if(buttons.getButtons()==Keys.ID_DOWN) {
							y=false;
							etat=ETAT.FIN;
						}
					}
					break;
				case DEPOSER_PALET_ENBUT:
					y=true;
					color=cedric.deposerPaletEnBut();
					if(color==null) {
						etat=ETAT.ROTATION_RECHERCHE;
					}else {
						System.out.println(color);
						cedric.ajustementDPEB(color);
						etat=ETAT.DEPOSER_PALET_ENBUT;
					}
					System.out.println(etat);
					while(y) {
						if(buttons.getButtons()==Keys.ID_ENTER) {
							y=false;
						}else if(buttons.getButtons()==Keys.ID_DOWN) {
							y=false;
							etat=ETAT.FIN;
						}
					}
					break;
				case ERROR:
					y=true;
					cedric.ajustementErreur(distance);
					etat=ETAT.ROTATION_RECHERCHE;
					System.out.println(etat);
					while(y) {
						if(buttons.getButtons()==Keys.ID_ENTER) {
							y=false;
						}else if(buttons.getButtons()==Keys.ID_DOWN) {
							y=false;
							etat=ETAT.FIN;
						}
					}
					break;
				case FIN:
					System.out.println(etat);
					routine=false;
					break;
			}
		}
	}

}
