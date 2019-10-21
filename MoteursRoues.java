package coo;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;
public class MoteursRoues {
	static RegulatedMotor mg;
	static RegulatedMotor md;
	private Wheel wheelg;
	private Wheel wheeld;
	private Chassis chassis;
	private MovePilot pilot;
	private double orientation;
	public MoteursRoues() {
		mg = new EV3LargeRegulatedMotor(MotorPort.C);
		md = new EV3LargeRegulatedMotor(MotorPort.A);
		wheelg = WheeledChassis.modelWheel(mg, 5.6).offset(-6.15);
		wheeld = WheeledChassis.modelWheel(md, 5.6).offset(6.15);
		chassis = new WheeledChassis(new Wheel[] { wheelg, wheeld }, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		orientation = 0.0;
		pilot.setLinearSpeed(0.5*pilot.getMaxLinearSpeed());
		pilot.setAngularSpeed(0.5*pilot.getMaxAngularSpeed());
	}
	
	public void avancer(double distance) { pilot.travel(distance); }
	public void avancerTantQue() { pilot.forward(); }
	public void avancerT(int ms) {
		pilot.forward();
		Delay.msDelay(ms);
	}
	public void reculer(double distance) { pilot.travel(-distance); }
	public void reculerTantQue() { pilot.backward(); }
	public void reculerT(int ms) {
		pilot.backward();
		Delay.msDelay(ms);
	}
	public void tournerD(double angle) { 
		pilot.rotate(angle); 
		orientation=(orientation+angle)%360;
	}
	public void tournerG(double angle) { 
		pilot.rotate(-angle); 
		orientation=(orientation+360-angle)%360;
	}
	public void sorienterVersEnBut(){
		if(orientation>180.0) tournerD(360-orientation);
		else if(orientation<=180.0) tournerG(orientation);
		orientation=0.0;
	}
	public void demiTourG() { tournerG(180); }
	public void demiTourD() { tournerD(180); }
	public void arret() { pilot.stop(); }
	public void setSpeed(double pourcent) {
		if(pourcent>=100) pourcent=100;
		else if(pourcent<20) pourcent=20;
		pilot.setLinearSpeed((pourcent/100)*pilot.getMaxLinearSpeed());
		pilot.setAngularSpeed((pourcent/100)*pilot.getMaxAngularSpeed());
	}
	public double getOrientation() { return orientation; }
	public boolean getEnMouvement() { return pilot.isMoving(); }
	
	public static void main(String args[]) {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		MoteursRoues roues = new MoteursRoues();
		roues.tournerD(70);
		roues.sorienterVersEnBut();
		roues.avancer(25);
		roues.setSpeed(100);
		roues.avancer(50);
		roues.setSpeed(0);
		roues.demiTourD();
		roues.avancer(75);
		roues.sorienterVersEnBut();
		roues.avancerTantQue();
		while(roues.getEnMouvement()) {
			if(buttons.getButtons() == Keys.ID_ENTER) roues.arret();
			buttons.waitForAnyPress();
		}
	}
	
}
