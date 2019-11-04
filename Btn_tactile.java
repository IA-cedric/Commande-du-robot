package cedric;

/**
 * Classe de gestion du bouton tactile en LEJOS EV3
 * version 1.0
 * @author Mitton Benjamin
 *
 */

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class Btn_tactile {
	EV3TouchSensor touchSensor;
	
	Btn_tactile(){
		 /**
		   * Initialise le bouton tactile pour un port donnée
		   */
		touchSensor = new EV3TouchSensor(SensorPort.S4);
	}
	

	public boolean estPresse(boolean mode) {
		 /**
		   * Execute soit la fonction PresseSansFin soit la fonction PresseFin
		   * 
		   * 
		   */
		if(mode == true) {
			return PresseSansFin();
		}
		else {
			return PresseFin();
		}
	}
	
	public boolean PresseSansFin() {
		 /**
		   * Cherche en continue si le bouton est appuyer 
		   * s'arrete une fois un signal recu 
		   * 
		   * @return vrai si appuie
		   * 
		   */
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
	
	public boolean PresseFin() {
		 /**
		   * Regarde 5 fois seulement si le bouton est enclencher 
		   * 
		   * @return vrai si le bouton est enclencher 
		   */
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