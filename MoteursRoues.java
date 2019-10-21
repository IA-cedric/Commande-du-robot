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
	
	/**
	 * Le robot avance de d centimetre.
	 * @param d distance en centimetre strictement superieur à 0 
	 */
	public void avancerD(double d) { 
		pilot.travel(d); 
	}
	
	/**
	 * Le robot avance.
	 * Il faut créer une condition d'arret avec la methode arret()
	 * dans une boucle while(this.getEnMouvement)
	 */
	public void avancer() { 
		pilot.forward(); 
	}
	
	/**
	 * Le robot avance pendant t millisecondes.
	 * @param t temps en millisecondes supérieur à 100 
	 */
	public void avancerT(int t) {
		pilot.forward();
		Delay.msDelay(t);
	}
	
	/**
	 * Le robot recule de d centimetre.
	 * @param d distance strictement superieur à 0 
	 */
	public void reculerD(double d) { 
		pilot.travel(-d); 
	}
	
	/**
	 * Le robot recule.
	 * Il faut créer une condition d'arret avec la methode arret()
	 * dans une boucle while(this.getEnMouvement)
	 */
	public void reculer() { 
		pilot.backward(); 
	}
	
	/**
	 * Le robot recule de t millisecondes.
	 * @param t temps en millisecondes superieur à 100 
	 */
	public void reculerT(int t) {
		pilot.backward();
		Delay.msDelay(t);
	}
	
	/**
	 * Le robot tourne de a degres vers la droite.
	 * @param a angle en degres strictement superieur à 0 
	 */
	public void tournerD(double a) { 
		pilot.rotate(a); 
		orientation=(orientation+a)%360;
	}
	
	/**
	 * Le robot tourne de a degres vers la gauche.
	 * @param a angle en degres strictement superieur à 0 
	 */
	public void tournerG(double angle) { 
		pilot.rotate(-angle); 
		orientation=(orientation+360-angle)%360;
	}
	
	/**
	 * Le robot effectue un virage pour reprendre son orientation initiale.
	 * Le robot fera le virage le plus court possible (vers la droite ou
	 * vers la gauche), si celui-ci est bien orienté il ne fait rien.
	 */
	public void sorienterVersEnBut(){
		if(orientation>180.0) tournerD(360-orientation);
		else if(orientation<=180.0) tournerG(orientation);
		orientation=0.0;
	}
	
	/**
	 * Le robot effectue un demi-tour par la gauche.
	 */
	public void demiTourG() { 
		tournerG(180); 
	}
	
	/**
	 * Le robot effectue un demi-tour par la droite.
	 */
	public void demiTourD() { 
		tournerD(180); 
	}
	
	/**
	* Le robot effectue un tour par la gauche.
	*/
	public void tourG() { 
		tournerG(360); 
	}
	
	/**
	* Le robot effectue un tour par la droite.
	*/
	public void tourD() { 
		tournerD(360); 
	}
	
	/**
	 * Le robot arrete tout mouvement en cours.
	 */
	public void arret() { 
		pilot.stop(); 
	}
	
	/**
	 * Définit le vitesse angulaire et la vitesse linéaire du robot.
	 * En fonction de sa vitesse maximum
	 * @param pourcent pourcentage de la vitesse maximum
	 */
	public void setSpeed(double pourcent) {
		if(pourcent>=100) pourcent=100;
		else if(pourcent<20) pourcent=20;
		pilot.setLinearSpeed((pourcent/100)*pilot.getMaxLinearSpeed());
		pilot.setAngularSpeed((pourcent/100)*pilot.getMaxAngularSpeed());
	}
	
	/**
	 * Retourne l'orientation du robot en degres.
	 * @return attribut orientation 
	 */
	public double getOrientation() { 
		return orientation; 
	}
	
	/**
	 * Retourne true si le robot est en mouvement, false sinon
	 * @return 
	 */
	public boolean getEnMouvement() { 
		return pilot.isMoving(); 
	}
	
	public static void main(String args[]) {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys buttons = ev3brick.getKeys();
		MoteursRoues roues = new MoteursRoues();
		roues.tournerD(70);
		roues.sorienterVersEnBut();
		roues.avancerD(25);
		roues.setSpeed(100);
		roues.avancerD(50);
		roues.setSpeed(0);
		roues.demiTourD();
		roues.avancerD(75);
		roues.sorienterVersEnBut();
		roues.avancer();
		while(roues.getEnMouvement()) {
			if(buttons.getButtons() == Keys.ID_ENTER) roues.arret();
			buttons.waitForAnyPress();
		}
	}
	
}
