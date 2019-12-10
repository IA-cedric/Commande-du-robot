package robotv33;
	import lejos.hardware.BrickFinder;
	import lejos.hardware.Button;
	import lejos.hardware.ev3.LocalEV3;
	import lejos.hardware.lcd.GraphicsLCD;
	import lejos.hardware.motor.EV3LargeRegulatedMotor;
	import lejos.hardware.port.Port;
	import lejos.hardware.sensor.EV3UltrasonicSensor;
	import lejos.hardware.sensor.SensorModes;
	import lejos.robotics.SampleProvider;
	import lejos.utility.Delay;
	import lejos.hardware.port.MotorPort;
	import lejos.utility.Delay;
public class yeux {
		
		public static void main(String args[]) {
		// get a port instance
		Port port = LocalEV3.get().getPort("S2");

		// Get an instance of the Ultrasonic EV3 sensor
		SensorModes sensor = new EV3UltrasonicSensor(port);

		// get an instance of this sensor in measurement mode
		SampleProvider distance= sensor.getMode("Distance");

		// initialize an array of floats for fetching samples. 
		// Ask the SampleProvider how long the array should be
		float[] sample = new float[distance.sampleSize()];

		// fetch a sample
		int x=0;
		while(x<10) {
		  
		try {
			distance.fetchSample(sample, 0);
			//Button.ENTER.waitForPress();		
			} catch (Throwable t) {
				t.printStackTrace();
				System.exit(0);
			}
		
		System.out.println(sample[0]);
		Delay.msDelay(2000);
		x++;
		}
	}
	}

