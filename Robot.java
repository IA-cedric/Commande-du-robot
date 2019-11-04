import lejos.utility.Delay;

public class Robot {
	public CapteurUltrason yeux = new CapteurUltrason("S3");
	public MoteursRoues roue = new MoteursRoues();
	public enum ETAT {ROTATION_RECHERCHE, AVANCER_VERS, CORRECTION_TRAJECTOIRE, ATTRAPER_PALET;}
	
	public TypeLastDistance AvancerVers() {//avance vers un palais ou mur et quand est sur de ce qui est devant lui, s'arrete et renvoi l'objet
		TypeLastDistance type = new TypeLastDistance();
		try {
		
		
		if(yeux.detectInfinity()==1) {//ca veut dire qu'il detecte quelque chose
			float PremDist=yeux.getDistance();
			roue.avancer(); //avance et s'arrete pas 
			while(roue.getEnMouvement()) {
						float DeuxDistance=yeux.getDistance();
						if(DeuxDistance<32) {
							type.set('m', PremDist);
							roue.arret();
						}
						else if(DeuxDistance>PremDist && PremDist<33) {
							type.set('p', PremDist);
							roue.arret();
						}
						else if(DeuxDistance>PremDist && PremDist>33) {
							type.set('d', PremDist);
							roue.arret();
						}
						PremDist=DeuxDistance;
						Delay.msDelay(100);;//attend avant de relancer le while pour eviter de boucler dans rien
						
						//s'avancer vers s'arrête quand la distance augmente: arrete si c'est unmur, si c'est un palais si je suis décalé si précédente valeur était à 32 c'est palais mais si c'atit 50 c'ets que décalé
						// tant que méthode teste les 3 conditions. percevoir environnement toute les 50milliseconde, sinon bouvle trop vite. tant que vrai, si return
				}
			 //je suis sois à 32cm du palais si type=p, need tourner si décalé et si mur tourner aussi
		}	
		
		} catch(IllegalArgumentException i) {
			System.out.print("probleme de sensor mode");
			Delay.msDelay(3000);
		System.exit(0);
		}
		return type; 
}
	//tourner pour detect palais
	

	
	public void RotationRecherche() {
		try {
			double tab[]=new double[1000];
			//instruction de touner
			if ((yeux.detectInfinity()==-1)) {
				
			}
			else {
				int compteur=0; //donnera le nombre de valeur effectiveou un array avec des add comme ca direct
				roue.tournerD(360);
				while(roue.getEnMouvement()) {
					//je met dans mon tableau les différence de distance
					tab[compteur]=yeux.getDistance();
					//mettre un temps de 5millisec pour pas avoir trop de données rajout d'un compteur
					compteur++;
					Delay.msDelay(50);
				}
				//mtn je trie mon tableau en dégageant les valeurs <32,5 car c'est pas 
				//des palets et je vais vers la plus petite après
				int indice=-1;
				double min=1000;
				for(int j=0; j<tab.length;j++) {
					if(tab[j]<min && tab[j]>32.5) {
						min=tab[j];
						indice=j;
					}
				}
				//donc indice de tableau par rapport au minimum, le convertir en angle
				roue.tournerD(180/compteur*indice);
				//180 car que des demi tour pour détecter palais, 
				//on lance tourner moteur de l'angle et avancer!
			}
		} catch(IllegalArgumentException i) {
			System.out.print("probleme de sensor mode");
			Delay.msDelay(3000);
			System.exit(0);
		}
	}

	public TypeLastDistance CorrectionTrajectoire(float lastDistance) {
		TypeLastDistance type = new TypeLastDistance();
		float newDistance;
		roue.tournerD(5);
		newDistance=yeux.getDistance();
		if(newDistance<=lastDistance+2&&newDistance>=lastDistance-2) type.set('a', newDistance);
		else {
			roue.tournerG(10);
			newDistance=yeux.getDistance();
			if(newDistance<=lastDistance+2&&newDistance>=lastDistance-2) type.set('a', newDistance);
		}
		return type;
	}
	
}
