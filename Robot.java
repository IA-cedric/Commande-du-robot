package robotv33;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.utility.Delay;
import robotv33.CC.Couleur;



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
	public double rotationRecherche() {
		roues.setSpeed(10);
		try {
			//double tab[]=new double[5000];
			ArrayList<Double> tab = new ArrayList<Double>(0);
			//instruction de touner
			
				roues.tournerRecherche(110, direction);
				while(roues.getEnMouvement()) {
					//je met dans mon tableau les différence de distance
					tab.add((double) yeux.getDistance());
					Delay.msDelay(10);
					//mettre un temps de 5millisec pour pas avoir trop de données rajout d'un compteur
				}
				//mtn je trie mon tableau en dégageant les valeurs <32,5 car c'est pas 
				//des palets et je vais vers la plus petite après
				int indice=-1;
				double min=1000;
				for(int j=0; j<tab.size();j++) {
					if(tab.get(j)<min && tab.get(j)>32) {
						min=tab.get(j);
						indice=j;
					}
				}
				double angle = 110.0*indice/tab.size();
				double distance = tab.get(indice);
				System.out.println(indice);
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
				roues.tourner(105.0-angle,-direction);
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
			if (direction==1) {
				roues.setSpeed(100);
				roues.avancer();
				pince.ouvrirPP();
				while(roues.getEnMouvement()) {
					if(contact.estPresse(false)) {
						roues.arret();
					}
				}
				roues.tournerD(30);
				pince.fermerPP();
				roues.avancer(60.0,false);
				roues.tournerG(30);
				roues.avancer();
				while(roues.getEnMouvement()) {
					if(zone.getColor()==Couleur.Blanc)
						roues.arret();
				}
				pince.ouvrirPP();
				roues.reculer(20.0);
				pince.fermerPP();
				roues.tournerD(115);
			}else {
				roues.setSpeed(100);
				roues.avancer();
				pince.ouvrirA();
				while(roues.getEnMouvement()) {
					if(contact.estPresse(false)) {
						roues.arret();
					}
				}
				roues.tournerG(30);
				pince.fermerA();
				roues.avancer(60.0,false);
				roues.tournerD(30);
				roues.avancer();
				while(roues.getEnMouvement()) {
					if(zone.getColor()==Couleur.Blanc)
						roues.arret();
				}
				pince.ouvrirA();
				roues.reculer(20.0);
				pince.fermerA();
				roues.tournerG(115);
			}
		}

	public boolean attraperPalet(double distance) {
		roues.setSpeed(50);
		roues.avancer(1.3*distance,true);
		pince.ouvrirA();
		Couleur color;
		boolean jaunerouge = false;
		while(roues.getEnMouvement()) { // temps a se lancer peu poser probleme 
			color = zone.getColor();
			System.out.println(color);
			if(color==Couleur.Blanc) {
				roues.avancer(10.0,true);
				roues.arret();
				pince.fermerA();
				System.out.print("blanc");
				return false;
			}else if(contact.estPresse(false)) {
				roues.arret();
				pince.fermerA();
				return true;
			}else if(color==Couleur.Jaune||color==Couleur.Rouge) {
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
		System.out.print("rien");
		return false;
		
	
	}
	public void ajustementErreur(double distance) {
		roues.reculer(distance);
		roues.sorienterOpposeEnBut();
		roues.avancer(50.0, false);
		roues.tourner(110-roues.getOrientation(), direction);
	}
	public void ajustementDPEB(Couleur c) {
		int dir=1;
		if((c==Couleur.Jaune&&casier==true)||c==Couleur.Rouge&&casier==false) {
			dir=-1;
		}
		roues.tournerSO(90, dir);
		roues.avancer(20.0, false);
		roues.tournerSO(80, -dir);
	}
	
	public Couleur deposerPaletEnBut() {
		roues.setSpeed(50);
		roues.sorienterVersEnBut();
		roues.avancer();
		Couleur color;
		while (roues.getEnMouvement()) {
			color = zone.getColor();
			System.out.println("je suis telle : "+color);
			if(color==Couleur.Blanc) {
				roues.arret();
				pince.ouvrirA();
				roues.reculer(20.0);
				pince.fermerA();
				roues.tourner(115, direction);
				palet=false;
				return null;
			}else if(color==Couleur.Rouge) {
				return Couleur.Rouge;
			}else if(color==Couleur.Jaune) {
				roues.arret();
				color = zone.getColor();
				if(color == Couleur.Blanc) {
					pince.ouvrirA();
					roues.reculer(20.0);
					pince.fermerA();
					roues.tourner(115, direction);
					palet=false;
					return null;
				}
				else {
					return Couleur.Jaune;
				}
			}
		}
		return null;
		
		
	}
}

