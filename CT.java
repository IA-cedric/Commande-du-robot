package robotV2;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;

/** Classe de gestion du bouton tactile en LEJOS EV3
* version 1.0
* @author Mitton Benjamin
*
*/

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

/**
 * Cette classe a pour objectif d utiliser le capteur tactile du robot, elle permet de detecter quand le robot touche quelque chose, en l occurence un palet ici
 *
 */
public class CT {
	private EV3TouchSensor touchSensor;
	
	
	/**
	 * Constructueur par defaut, initialise le bouton tactile pour le port S4
	 */
	public CT(){
		this(LocalEV3.get().getPort("S4"));
	}
	
	/**
	 * Constructeur avec parametre, initialise le bouton tactile pour un port donne
	 * @param pct nom du port a utiliser pour initialiser le CT
	 */
	public CT(Port pct) {
		touchSensor = new EV3TouchSensor(pct);
	}
	
	/**
	 * Cette methode utilise PresseSansFin ou PresseFin selon le choix de l utilisateur (bouton presse ou non : true ou false)
	 * @param mode est l etat courant 
	 * @return vrai ou faux en fonction des deux methodes.
	 */
	public boolean estPresse(boolean mode) {

		if(mode == true) {
			return PresseSansFin();
		}
		else {
			return PresseFin();
		}
	}
	
	
	/**
	 * Cherche en continu si le bouton est appuye, s arrete une fois le signal recu
	 * @return vrai si le bouton est presse, faux sinon
	 */
	public boolean PresseSansFin() {
		SensorMode touch = touchSensor.getTouchMode();
		float[] sample = new float[touch.sampleSize()];
		int i =0;
		while(i != 0) {
			touch.fetchSample(sample, 0);	
			if(sample[0] != 0) {
				return(true);

			}
		}
		return false;
	}
	
	
	/**
	 * Regarde 5 fois si le bouton est enclenche, toutes les 50ms
	 * @return vrai si le bouton est enclenche
	 */
	public boolean PresseFin() {
		SensorMode touch = touchSensor.getTouchMode();
		float[] sample = new float[touch.sampleSize()];
		int i=5;
		while(i>0) {
			touch.fetchSample(sample, 0);
			Delay.msDelay(50);
			if(sample[0]!=0) {
				return true;
			}
			i--;
		}
		return false;
	}
}
