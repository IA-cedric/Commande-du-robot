package robotv33;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class MP{
	private RegulatedMotor m3;
	
	public MP(){

		  /**
		   * Iniatilise une pince pour un port donnée à une vitesse max 
		   * 
		   * 
		   * 
		   */
		
		this(LocalEV3.get().getPort("D"));
	}
	
	public MP(Port pmp) {
		m3 = new EV3LargeRegulatedMotor(pmp);
		m3.setSpeed((int) (m3.getMaxSpeed()*0.8));
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
	
	public void attraper() {
		m3.resetTachoCount();
		while(m3.getTachoCount() < 515) {
			m3.forward();
			System.out.println(m3.getTachoCount());
			if(tachobug() == true) {
				m3.stop();
				break;
			}
		}
			System.out.println(m3.getTachoCount());
			m3.stop();
			int value = -1*m3.getTachoCount();
			
			while(m3.getTachoCount() > value) {
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
	
		
	
	public void ouvrirA() {
		 /**
		   * ouvre la pince de 500 tour
		   */
		m3.resetTachoCount();
		while(m3.getTachoCount() < 800) {
			m3.forward();
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
		while(m3.getTachoCount() > -820) {
			m3.backward();
			if(tachobug() == true) {
				m3.stop();
				break;
			}
			
		}
		m3.stop();
	}
	
	public void fermerPP() {
		 /**
		   * ferme la pince de 500 tour
		   */
		m3.resetTachoCount();
		while(m3.getTachoCount() > -750) {
			m3.backward();
			if(tachobug() == true) {
				m3.stop();
				break;
			}
			
		}
		m3.stop();
	}
	
	public void ouvrirPP() {
		 /**
		   * ouvre la pince de 500 tour
		   */
		m3.resetTachoCount();
		while(m3.getTachoCount() < 700) {
			m3.forward();
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
	public static void main(String args[]) {
		MP pince = new MP();
		pince.retablir();
	}
}
