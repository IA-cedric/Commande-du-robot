package robotv33;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;


/** Classe de gestion du bouton tactile en LEJOS EV3
* version 1.0
* @author Mitton Benjamin
*
* Ce module est utilisé par la classe Robot 
*/

public class CT {
	private EV3TouchSensor touchSensor;
	
	 /**
	   * Initialise le bouton tactile pour un port donnée
	   */
	public CT(){
		this(LocalEV3.get().getPort("S4"));
	}
	
	public CT(Port pct) {
		touchSensor = new EV3TouchSensor(pct);
	}
	
	 /**
	   * Execute la fonction PresseFin
	   * 
	   * 
	   */
	public boolean estPresse(boolean mode) {
			return PresseFin();
	}
	
	
	 /**
	   * Regarde 5 fois seulement si le bouton est enclencher 
	   * 
	   * @return true/false 
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
