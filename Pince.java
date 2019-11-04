package cedric;

/**
 * Classe de gestion de la pince en LEJOS ev3
 * version 1.0
 * @author Mitton Benjamin
 *
 */

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class Pince{
	RegulatedMotor m3;
	
	Pince(){

		  /**
		   * Iniatilise une pince pour un port donnée à une vitesse max 
		   * 
		   * 
		   * 
		   */
		
		m3 = new EV3LargeRegulatedMotor(MotorPort.D);
		m3.setSpeed((int) m3.getMaxSpeed());
	}
	
	
	public boolean tachobug() {

		  /**
		   * Return true si la pince est bloqué dans une position
		   * 
		   * @return vrai/faux
		   */
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
	
	public  void retablir() {
		 /**
		   * retablis l'ouverture de la pince 
		   * En passant par la valeur d'ouverture maximale
		   */
		ouvrirZ();
		fermerZ();
	}
	
	public void ouvrirZ() {
		 /**
		   * Ouvre la pince au maximum 
		   */
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
	
		
	public void fermerZ() {
		 /**
		   * Ferme la pince de 2900 tour
		   */
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
	
	public void ouvrirA() {
		 /**
		   * ouvre la pince de 500 tour
		   */
		m3.resetTachoCount();
		while(m3.getTachoCount() < 500) {
			m3.forward();
			System.out.println(m3.getTachoCount());
			if(tachobug() == true) {
				m3.stop();
				break;
			}
		}
		m3.stop();
	}
	
	public void fermerA() {
		 /**
		   * ferme la pince de 500 tour
		   */
		m3.resetTachoCount();
		while(m3.getTachoCount() > -550) {
			m3.backward();
			System.out.println(m3.getTachoCount());
			if(tachobug() == true) {
				m3.stop();
				break;
			}
		}
		m3.stop();
	}
		
	public int tacho() {
		 /**
		   * Renvoie le compte de tour de la pince
		   * 
		   * @return tachometer count 
		   */
		return m3.getTachoCount();
	}
}

