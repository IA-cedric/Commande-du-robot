/**
 * 
 */
package robotV2;





import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;


/**
 * @author C�dric
 *
 */
public class MR {
	private static RegulatedMotor mg;
	private static RegulatedMotor md;
	private Wheel wheelg;
	private Wheel wheeld;
	private Chassis chassis;
	private MovePilot pilot;
	private double orientation;


	/**
	 * Constructeur avec Port par d�faut.
	 */
	public MR() {
		this(LocalEV3.get().getPort("C"),LocalEV3.get().getPort("A"));
	}

	/**
	 * Constructeur avec choix des Port en param�tre
	 * @param pmg Port Moteur Gauche
	 * @param pmd Port Moteur Gauche
	 */
	public MR(Port pmg, Port pmd) {
		mg = new EV3LargeRegulatedMotor(pmg);
		md = new EV3LargeRegulatedMotor(pmd);
		wheelg = WheeledChassis.modelWheel(mg, 5.6).offset(-6.15);
		wheeld = WheeledChassis.modelWheel(md, 5.6).offset(6.15);
		chassis = new WheeledChassis(new Wheel[] { wheelg, wheeld }, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		orientation = 0.0;
		pilot.setLinearSpeed(0.5 * pilot.getMaxLinearSpeed());
		pilot.setAngularSpeed(0.5 * pilot.getMaxAngularSpeed());
	}

	/**
	 * Le robot avance de d centimetre.
	 * 
	 * @param d distance en centimetre strictement superieur � 0
	 */
	public void avancer(double d) {
		pilot.travel(d);
	}

	/**
	 * Le robot avance. Il faut cr�er une condition d'arret avec la methode arret()
	 * dans une boucle while(this.getEnMouvement)
	 */
	public void avancer() {
		pilot.forward();
	}

	/**
	 * Le robot avance pendant t millisecondes.
	 * 
	 * @param t temps en millisecondes sup�rieur � 100
	 */
	public void avancer(int t) {
		pilot.forward();
		Delay.msDelay(t);
	}

	/**
	 * Le robot recule de d centimetre.
	 * 
	 * @param d distance strictement superieur � 0
	 */
	public void reculer(double d) {
		pilot.travel(-d);
	}

	/**
	 * Le robot recule. Il faut cr�er une condition d'arret avec la methode arret()
	 * dans une boucle while(this.getEnMouvement)
	 */
	public void reculer() {
		pilot.backward();
	}

	/**
	 * Le robot recule de t millisecondes.
	 * 
	 * @param t temps en millisecondes superieur � 100
	 */
	public void reculer(int t) {
		pilot.backward();
		Delay.msDelay(t);
	}

	/**
	 * Le robot tourne de a degres vers la droite.
	 * 
	 * @param a angle en degres strictement superieur � 0
	 */
	public void tournerD(double a) {
		pilot.rotate(a);
		orientation = (orientation + a) % 360;
	}

	/**
	 * Le robot tourne de a degres vers la gauche.
	 * 
	 * @param a angle en degres strictement superieur � 0
	 */
	public void tournerG(double a) {
		pilot.rotate(-a);
		orientation = (orientation + 360 - a) % 360;
	}

	/**
	 * Le robot effectue un virage pour reprendre son orientation initiale. Le robot
	 * fera le virage le plus court possible (vers la droite ou vers la gauche), si
	 * celui-ci est bien orient� il ne fait rien.
	 */
	public void sorienterVersEnBut() {
		if (orientation > 180.0)
			tournerD(360 - orientation);
		else if (orientation <= 180.0)
			tournerG(orientation);
		orientation = 0.0;
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
	
	public void esquiveD() {
		pilot.arc(50, 15);
		pilot.arc(-50, 15);
	}
	
	public void esquiveG() {
		pilot.arc(-50, 15);
		pilot.arc(50, 15);
	}

	/**
	 * Le robot arrete tout mouvement en cours.
	 */
	public void arret() {
		pilot.stop();
	}

	/**
	 * D�finit le vitesse angulaire et la vitesse lin�aire du robot. En fonction de
	 * sa vitesse maximum
	 * 
	 * @param pourcent pourcentage de la vitesse maximum
	 */
	public void setSpeed(double pourcent) {
		if (pourcent >= 100)
			pourcent = 100;
		else if (pourcent < 20)
			pourcent = 20;
		pilot.setLinearSpeed((pourcent / 100) * pilot.getMaxLinearSpeed());
		pilot.setAngularSpeed((pourcent / 100) * pilot.getMaxAngularSpeed());
	}

	/**
	 * Retourne l'orientation du robot en degres.
	 * 
	 * @return attribut orientation
	 */
	public double getOrientation() {
		return orientation;
	}

	/**
	 * Retourne true si le robot est en mouvement, false sinon
	 * 
	 * @return true si le robot est en mouvement, false sinon
	 */
	public boolean getEnMouvement() {
		return pilot.isMoving();
	}

}

