package robotV2;

import lejos.hardware.ev3.LocalEV3;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;


/**
 * Cette classe permet d utiliser les pinces du robot pour a terme attraper des objets (palets en l occurence)
 */
public class MP{
	private RegulatedMotor m3;


	/**
	 *  Constructeur par défaut, initialise le port D pour les pinces 
	 */
	public MP(){
		this(LocalEV3.get().getPort("D"));
	}

	/**
	 * Constructeur avec parametre, initialise le port en fonction de celui donne avec une vitesse max
	 * @param pmp correspond au capteur correspondant
	 */
	public MP(Port pmp) {
		m3 = new EV3LargeRegulatedMotor(pmp);
		m3.setSpeed((int) (m3.getMaxSpeed()*0.8));
	}

	/**
	 * Permet de savoir si la pince est bloquee dans une position
	 * @return vrai si elle est bloquee, faux sinon
	 */
	public boolean tachobug() {
		boolean res ; 
		int j = m3.getTachoCount();
		Delay.msDelay(150);
		if(j == m3.getTachoCount()) {
			res = true;
		}
		else {
			res = false;
		}
		return res;
	}


	/**
	 * Permet d'ouvrir la pince (retablir son etat initial) en passant par la valeur d ouverture maximale
	 */
	public  void retablir() {
		ouvrirZ();
		fermerZ();
	}

	/**
	 * ouvre la pince au maximum
	 */
	public void ouvrirZ() {
		m3.resetTachoCount();
		while(m3.getTachoCount() < 3500) {
			m3.forward();
			System.out.println(m3.getTachoCount());
			if(tachobug() == true) {
				m3.stop();
				break; 
			}
		}
		m3.stop();

	}

	/**
	 * ferme la pince de 2900 tours de moteur
	 */
	public void fermerZ() {
		m3.resetTachoCount();
		while(m3.getTachoCount() > -2900) {
			m3.backward();
			System.out.println(m3.getTachoCount());
			if(tachobug() == true) {
				m3.stop();
				break;
			}
		}
		m3.stop();
	}



	/**
	 * ouvre la pince de 500 tours (taille palet)
	 */
	public void ouvrirA() {
		m3.resetTachoCount();
		while(m3.getTachoCount() < 500) {
			m3.forward();
			System.out.println(m3.getTachoCount());
			if(tachobug() == true) {
				m3.stop();
				break;
			}
		}
		System.out.println(m3.getTachoCount());
		m3.stop();
	}

	/**
	 * ferme la pince de 500 tours (taille palet)
	 */
	public void fermerA() {
	
		m3.resetTachoCount();
		while(m3.getTachoCount() > -490) {
			m3.backward();
			System.out.println(m3.getTachoCount());
			if(tachobug() == true) {
				m3.stop();
				break;
			}

		}
		System.out.println(m3.getTachoCount());
		m3.stop();
	}

	/**
	 * Permet de compter le compte de tours de la pince
	 * @return tachometer count soit le nombre de tours
	 */
	public int tacho() {
		return m3.getTachoCount();
	}
}
