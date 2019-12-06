package robotv3;


import java.util.ArrayList;

import lejos.utility.Delay;
import robotv3.CC.Couleur;



public class Robot {
	private MR roues;
	private CU yeux;
	private CT contact;
	private CC zone;
	private MP pince;
	private boolean palet;
	private int direction;
	private boolean casier;
	
	public boolean isCasier() {
		return casier;
	}

	public void setCasier(boolean casier) {
		this.casier = casier;
	}

	public boolean isPalet() {
		return palet;
	}

	public void setPalet(boolean palet) {
		this.palet = palet;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public Robot() {
		roues = new MR();
		contact = new CT();
		pince = new MP();
		yeux = new CU();
		zone = new CC();
		palet=false;
	}
	
	public void retablirPince() {
		pince.retablir();
	}
	//tourner pour detect palais
	public double rotationRecherche(double angleRecherche) {
		roues.setSpeed(10);
		try {
			//double tab[]=new double[5000];
			ArrayList<Double> tab = new ArrayList<Double>(0);
			ArrayList<Double> tab2 = new ArrayList<Double>(0);
			double angle;
			double distance;
			int indice=-1;
			double min=1000;
			//instruction de touner
				roues.tournerRecherche(angleRecherche, direction);
				while(roues.getEnMouvement()) {
					//je met dans mon tableau les différence de distance
					tab.add((double) yeux.getDistance());
					Delay.msDelay(10);
					//mettre un temps de 5millisec pour pas avoir trop de données rajout d'un compteur
				}
				//mtn je trie mon tableau en dégageant les valeurs <32,5 car c'est pas 
				//des palets et je vais vers la plus petite après
				indice=-1;
				min=1000;
				for(int j=0; j<tab.size();j++) {
					if(tab.get(j)<min && tab.get(j)>32) {
						min=tab.get(j);
						indice=j;
					}
				}
				angle = angleRecherche*indice/tab.size();
				distance = min;
				/*BufferedWriter f;
				String ch ="";
				int i =0;
				f= new BufferedWriter(new FileWriter("res",true));
				while(i<compteur) {
					ch=Double.toString(tab[i]);
					f.write(ch);
					f.newLine();
					i++;
				}
				f.close();*/
				//donc indice de tableau par rapport au minimum, le convertir en angle
				System.out.println("angle : "+angle);
				System.out.println("Vmin : "+ distance);
				roues.tourner((angleRecherche-angle),-direction);
				return distance;
				//180 car que des demi tour pour détecter palais, 
				//on lance tourner moteur de l'angle et avancer!

		} catch(IllegalArgumentException i) {
			System.out.print("probleme de sensor mode");
			Delay.msDelay(3000);
			System.exit(0);
		}
		return 0;
		
		
	}
	
	public void premierPalet() {
		roues.setSpeed(100);
		roues.avancer();
		pince.ouvrirA();
		while(roues.getEnMouvement()) {
			if(contact.estPresse(false)) {
				roues.arret();
			}
		}
		pince.fermerA();
		roues.tourner(30,direction);
		roues.avancer(50.0,false);
		roues.tourner(30,-direction);
		roues.avancer();
		while(roues.getEnMouvement()) {
			if(zone.getColor()==Couleur.Blanc) {
				roues.arret();
				pince.ouvrirA();
				roues.reculer(20.0);
				pince.fermerA();
				roues.tourner(115, direction);
			}
		}
	}

	public boolean attraperPalet(double distance) {
		roues.setSpeed(100);
		roues.avancer(1.3*distance,true);
		pince.ouvrirA();
		boolean jaunerouge = false;
		while(roues.getEnMouvement()) {
			if(zone.getColor()==Couleur.Blanc) {
				roues.arret();
				pince.fermerA();
				System.out.print("blanc");
				roues.reculer(distance);
				return false;
			}else if(contact.estPresse(false)) {
				roues.arret();
				pince.fermerA();
				return true;
			}else if(zone.getColor()==Couleur.Jaune||zone.getColor()==Couleur.Rouge) {
				roues.arret();
				pince.fermerA();
				System.out.print("jaunerouge");
				jaunerouge=true;
			}
		}
		if(jaunerouge) {
			roues.setSpeed(100.0);
			roues.reculer(15.0);
			pince.ouvrirA();
			roues.reculer(5.0);
			roues.avancer(20.0,true);
			while(roues.getEnMouvement()) {
				if(contact.estPresse(false)) {
					roues.arret();
					pince.fermerA();
					return true;
				}
			}
		}
		roues.arret();
		pince.fermerA();
		roues.reculer(1.3*distance);
		System.out.print("rien");
		return false;
		
	
	}
	
	public void ajustementErreur(double distance, int cool) {
		if(cool<4)
			roues.sorienterOpposeEnBut();
		else if(cool==4){
			
			direction=-direction;
		}else
			roues.sorienterVersEnBut();
			
		roues.avancer(30.0, false);
		roues.tourner(115-roues.getOrientation(), direction);
	}
	
	public void ajustementDPEB(Couleur c) {
		int dir=1;
		if((c==Couleur.Jaune&&casier==true)||c==Couleur.Rouge&&casier==false) {
			dir=-1;
		}
		roues.tournerSO(90, dir);
		roues.avancer(20.0, false);
		roues.tournerSO(70, -dir);
	}
	
	public Couleur deposerPaletEnBut(double speed) {
		roues.setSpeed(speed);
		roues.sorienterVersEnBut();
		roues.avancer();
		Couleur color;
		while (roues.getEnMouvement()) {
			color = zone.getColor();
			if(color==Couleur.Blanc) {
				roues.arret();
				pince.ouvrirA();
				roues.reculer(20.0);
				pince.fermerA();
				roues.tourner(115, direction);
				palet=false;
				return null;
			}else if(color==Couleur.Rouge) {
				roues.arret();
				return Couleur.Rouge;
			}else if(color==Couleur.Jaune) {
				roues.arret();
				System.out.println(color);
				if(zone.getColor()==Couleur.Blanc) {
					pince.ouvrirA();
					roues.reculer(20.0);
					pince.fermerA();
					roues.tourner(115, direction);
					palet=false;
					return null;
				}else if(zone.getColor()==Couleur.Jaune)
					return Couleur.Jaune;
			}
		}
		return null;
		
		
	}
}

