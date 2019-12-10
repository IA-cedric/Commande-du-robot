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

/**
 * 
 * Cette classe est utilise pour construire l'ensemble des methodes 
 * utilisant un ensemble de capteurs et ou moteurs. 
 * 
 * 
 * 
 * @author benjamin
 * @see MR,CU,CT,CC,MP
 * Ce module est utilisé dans les classes Routine et RoutineCompetition
 */

public class Robot {
	
	private MR roues;
	private CU yeux;
	private CT contact;
	private CC zone;
	private MP pince;
	private int direction;
	private boolean casier;
	private boolean palet;
	
	/**
	 * Getters de l'attribut palet
	 * @return palet 
	 */
	public boolean isPalet() {
		return palet;
	}
	
	/**
	 * Setters de l'attribut palet
	 * @param palet
	 */
	public void setPalet(boolean palet) {
		this.palet = palet;
	}
	
	/**
	 * Getters de l'attribut casier
	 * @return casier
	 */
	public boolean isCasier() {
		return casier;
	}

	/**
	 * Setters de l'attribut casier
	 * @param casier
	 */
	public void setCasier(boolean casier) {
		this.casier = casier;
	}
	/**
	 * Getter de l'attribut direction
	 * 
	 * @return direction 
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Setters de l'attribut direction
	 * @param direction
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * Initialise les differents capteurs et moteurs 
	 * necessaire au fonctionnement du robot. 
	 * 
	 */
	public Robot() {
		roues = new MR();
		contact = new CT();
		pince = new MP();
		yeux = new CU();
		zone = new CC();
		palet = false;
	}
	
	/**
	 * Permet d'utiliser la fonction retablir depuis une autre classe
	 * 
	 */
	public void retablirPince() {
		pince.retablir();
	}
	
	/**
	 * Realise un arc de recherche pour prendre des valeurs 
	 * grace au capteur a ultrason , il choisis la plus petite superrieur a 32
	 * et fais une rotation inverse pour se positionner en face de celle ci.
	 * 
	 * @return distance 
	 */
	public double rotationRecherche() {
		roues.setSpeed(10);
		try {
			ArrayList<Double> tab = new ArrayList<Double>(0);
				roues.tournerRecherche(110, direction);
				while(roues.getEnMouvement()) {
					tab.add((double) yeux.getDistance());
					Delay.msDelay(10);
				}
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
				
				System.out.println("angle : "+angle);
				System.out.println("Vmin : "+ distance);
				roues.tourner(105.0-angle,-direction);
				return distance;
		} catch(IllegalArgumentException i) {
			System.out.print("probleme de sensor mode");
			Delay.msDelay(3000);
			System.exit(0);
		}
		return 0;
		
		
	}
	/**
	 * Permet de marquer le premier palet 
	 * 
	 */
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

	/**
	 * 
	 * Permet d'aller attraper un palet sans sortir des lignes
	 * jaunes et rouges et si il sort m'anoeuvre d'esquive pour ne pas
	 * rater un palet. 
	 * @param distance
	 * @return true/false
	 */
	public boolean attraperPalet(double distance) {
		roues.setSpeed(50);
		roues.avancer(1.3*distance,true);
		pince.ouvrirA();
		Couleur color;
		boolean jaunerouge = false;
		while(roues.getEnMouvement()) { 
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
	/**
	 * Permet de se remettre a une position central en cas d'echec de
	 * la recherche 
	 * @param distance
	 */
	public void ajustementErreur(double distance) {
		roues.reculer(distance);
		roues.sorienterOpposeEnBut();
		roues.avancer(50.0, false);
		roues.tourner(110-roues.getOrientation(), direction);
	}
	/**
	 * Permet de rester entre les lignes jaunes et rouges
	 * @param c
	 */
	public void ajustementDPEB(Couleur c) {
		int dir=1;
		if((c==Couleur.Jaune&&casier==true)||c==Couleur.Rouge&&casier==false) {
			dir=-1;
		}
		roues.tournerSO(90, dir);
		roues.avancer(20.0, false);
		roues.tournerSO(80, -dir);
	}
	
	/**
	 * Permet d'aller marquer un palet une fois qu'il est
	 * attrapé 
	 * @return Couleur {null,jaune} 
	 */
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
				palet = false;
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
					palet = false; 
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

