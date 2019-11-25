package robotv2;

public class Routine {
	public enum ETAT {TEST,PREMIER_PALET,ROTATION_RECHERCHE, AVANCER_VERS, CORRECTION_TRAJECTOIRE, ATTRAPER_PALET, DEPOSER_PALET_ENBUT, ERROR, END;}
	public static void main(String[] args) {
		Robot cedric = new Robot();
		ETAT etat = ETAT.PREMIER_PALET;
		TypeLastDistance type = new TypeLastDistance();
		boolean routine = true;
		while(routine) {
			switch(etat) {
				case TEST:
				case PREMIER_PALET:
					cedric.premierPalet();
					etat = ETAT.ROTATION_RECHERCHE;
				case ROTATION_RECHERCHE: 
					cedric.rotationRecherche();
					etat = ETAT.AVANCER_VERS;
				case AVANCER_VERS:
					type=cedric.avancerVers();
					if(type.getType()=='m') etat=ETAT.ROTATION_RECHERCHE;
					else if(type.getType()=='d') etat=ETAT.CORRECTION_TRAJECTOIRE;
					else if(type.getType()=='p') etat=ETAT.ATTRAPER_PALET;
					else etat = ETAT.ERROR;
				case CORRECTION_TRAJECTOIRE:
					type=cedric.correctionTrajectoire(type);
					if(type.getType()=='a') etat=ETAT.AVANCER_VERS;
				case ATTRAPER_PALET:
					cedric.attraperPaler();
				case DEPOSER_PALET_ENBUT:
				case ERROR:
					etat=ETAT.ROTATION_RECHERCHE;
					break;
				case END:
					routine=false;
					break;	
			}
		}
	}

}
