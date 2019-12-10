package robotv33;

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



public class CU {
	private SensorModes sensor;
	private float LastDistance;
	
	public CU () {
		this(LocalEV3.get().getPort("S3"));
	}
	
	public CU(Port pcu) {
		sensor=new EV3UltrasonicSensor(pcu);
		LastDistance=0;
	}

	public int detectInfinity() {
		if(getDistance()>1 && getDistance()<255) {
			return 1;
		}
		else {
			return -1;
		}
	}

	public float getDistance() {	
		SampleProvider distance= sensor.getMode("Distance");
		float[] sample = new float[distance.sampleSize()];
		try {
			distance.fetchSample(sample, 0);
			//Button.ENTER.waitForPress();		
		} catch (Throwable t) { //need lever une illegalargumentExcetion, sensor mode
			t.printStackTrace();
			System.exit(0);
		}
		LastDistance = sample[0]*100;
		return (LastDistance);//converti en cm et marche
	}
	
	public float getLastDistance() {
		return LastDistance;
	}


	//palais à 32,5 cm
	//comparer les distances

	public static void main(String args[]) {

		//CapteurUltrason a= new CapteurUltrason("S3");

		/*float distance;
		distance=a.getDistance();
		System.out.println(distance);
		Delay.msDelay(3000);*/
		//a.detectPalais();
		//System.out.print(a.detectInfinity());
		//Delay.msDelay(3000);


		//System.out.print(a.getDistance());
		//Delay.msDelay(3000);
		//System.out.print(a.avancerPalaisMur());
		/*System.out.println();
	Delay.msDelay(3000);*/

	}
}
