package robotV2;

import java.util.ArrayList;
import java.util.Arrays;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lejos.hardware.port.MotorPort;
import lejos.utility.Delay;
import lejos.hardware.sensor.EV3IRSensor;


/**
 * 
 * Cette classe permet d utiliser le capteur a ultrasons (detecter les distances). 
 * Cette classe est utlisee par la classe Robot.
 */
public class CU {
	private SensorModes sensor;
	private float lastDistance;

	
	/**
	 * Constructeur par defaut, initialise le capteur à S3 par défaut
	 */
	public CU () {
		this(LocalEV3.get().getPort("S3"));
	}

	/**
	 *  Constructeur avec parametre,  initialise le capteur a ultrasons pour un port donne
	 * @param pcu nom du port a utiliser
	 */
	public CU(Port pcu) {
		sensor=new EV3UltrasonicSensor(pcu);
		lastDistance=0;
	}

	/**
	 * Permet de verifier si le capteur a ultrasons du robot detecte une distance 
	 * @return 1 si la distance est entre 1 et 255cm (spectre de detection), -1 si il ne detecte rien. 
	 */
	public int detectInfinity() {
		if(getDistance()>1 && getDistance()<255) {
			return 1;
		}
		else {
			return -1;
		}
	}

	/**
	 * Permet de donner la distance en cm de l objet detecte par le capteur a ultrasons
	 * @return lastDistance qui est un float correspondant a la distance de l objet detecte
	 */
	public float getDistance() {	
		SampleProvider distance= sensor.getMode("Distance");
		float[] sample = new float[distance.sampleSize()];
		try {
			distance.fetchSample(sample, 0);	
		} catch (Throwable t) { 
			t.printStackTrace();
			System.exit(0);
		}
		lastDistance = sample[0]*100;
		return (lastDistance);
	}

	/**
	 * Permet d avoir acces a l attribut lastDistance
	 * @return lastDistance
	 */
	public float getlastDistance() {
		return lastDistance;
	}

}

