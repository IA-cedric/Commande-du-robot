package robotv33;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;
/** 
 * 
 * @author benjamin
 * Cette classe gere toutes les fonctions associé a l'utilisation 
 * des pinces 
 * 
 *Ce module est utlisé par : la classe robot 
 */

public class MP{
	private RegulatedMotor m3;
	
	/**
	   * Iniatilise une pince pour un port donnée à une vitesse max 
	   * 
	   * 
	   * 
	   */
	public MP(){
		this(LocalEV3.get().getPort("D"));
	}
	
	
	/**
	   * Iniatilise une pince pour un port donnée à 80% de la vitesse max
	   * 
	   * 
	   * 
	   */
	public MP(Port pmp) {
		m3 = new EV3LargeRegulatedMotor(pmp);
		m3.setSpeed((int) (m3.getMaxSpeed()*0.8));
	}
	
	 /**
	   * Return true si la pince est bloqué dans une position
	   * 
	   * @return true/false
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
	   * retablis une ouverture de la pince correct  
	   * En passant par la valeur d'ouverture maximale
	   */
	public  void retablir() {
		ouvrirZ();
		fermerZ();
	}
	
	/**
	   * Ouvre la pince de 3500 tour
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
	   * Ferme la pince de 2900 tour
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
	   * ouvre la pince de 800 tour
	   */
	public void ouvrirA() {
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
	
	
	 /**
	   * ferme la pince de 820 tour
	   */
	public void fermerA() {
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
	
	 /**
	   * ferme la pince de 750 tour
	   */
	public void fermerPP() {
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
	
	/**
	   * ouvre la pince de 700 tour
	   */
	public void ouvrirPP() {
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
	
	 /**
	   * Renvoie le compte de tour de la pince
	   * 
	   * @return tachometer count 
	   */
	public int tacho() {
		return m3.getTachoCount();
	}
	
}
