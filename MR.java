/**
 * 
 */
package robotv33;





import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.chassis.*;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;


/**
 * @author Cédric
 * Cette classe est utilisé par la classe Robot. 
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
	 * Constructeur avec Port par défaut.
	 */
	public MR() {
		this(LocalEV3.get().getPort("C"),LocalEV3.get().getPort("A"));
	}

	/**
	 * Constructeur avec choix des Ports en paramètre
	 * @param pmg Port Moteur Gauche
	 * @param pmd Port Moteur Gauche
	 */
	public MR(Port pmg, Port pmd) {
		mg = new EV3LargeRegulatedMotor(pmg);
		md = new EV3LargeRegulatedMotor(pmd);
		wheelg = WheeledChassis.modelWheel(mg, 5.56).offset(-6.15);
		wheeld = WheeledChassis.modelWheel(md, 5.56).offset(6.15);
		chassis = new WheeledChassis(new Wheel[] { wheelg, wheeld }, WheeledChassis.TYPE_DIFFERENTIAL);
		pilot = new MovePilot(chassis);
		orientation = 0.0;
		pilot.setLinearSpeed(0.5 * pilot.getMaxLinearSpeed());
		pilot.setAngularSpeed(0.5 * pilot.getMaxAngularSpeed());
	}

	/**
	 * Le robot avance de d centimetre. Si x vaut true, 
	 * alors il peut exécuter d'autre instruction pendant son mouvement,
	 * sinon il doit attendre de finir son mouvement
	 * 
	 * @param d distance en centimetre strictement superieur à 0
	 * @param x booleen
	 *
	 */
	public void avancer(double d, boolean x) {
		pilot.travel(d,x);
	}

	/**
	 * Le robot avance. Il faut créer une condition d'arret avec la methode arret()
	 * dans une boucle while(this.getEnMouvement) par exemple ou après un Delay
	 */
	public void avancer() {
		pilot.forward();
	}

	/**
	 * Le robot avance pendant t millisecondes.
	 * 
	 * @param t temps en millisecondes supérieur à 100
	 */
	public void avancer(int t) {
		pilot.forward();
		Delay.msDelay(t);
	}

	/**
	 * Le robot recule de d centimetre.
	 * 
	 * @param d distance strictement superieur à 0
	 */
	public void reculer(double d) {
		pilot.travel(-d);
	}

	/**
	 * Le robot recule. Il faut créer une condition d'arret avec la methode arret()
	 * dans une boucle while(this.getEnMouvement)
	 */
	public void reculer() {
		pilot.backward();
	}

	/**
	 * Le robot recule de t millisecondes.
	 * 
	 * @param t temps en millisecondes superieur à 100
	 */
	public void reculer(int t) {
		pilot.backward();
		Delay.msDelay(t);
	}

	/**
	 * Le robot tourne de a degres vers la droite.
	 * 
	 * @param a angle en degres strictement superieur à 0
	 */
	public void tournerD(double a) {
		pilot.rotate(a);
		orientation = (orientation + a) % 360;
	}
	
	/**
	 * Le robot effectue une rotation de a degrès dans la direction dir tout en pouvant exécuter
	 * d'autres actions en même temps.
	 * @param a angle en degrès
	 * @param dir si vaut -1 rotation à gauche si vaut 1 rotation a droite
	 */
	public void tournerRecherche(double a, int dir) {
		pilot.rotate(a*dir,true);
		orientation =(orientation + 360 +a*dir) % 360;
	}
	
	/**
	 * Le robot effectue une rotation de a degrès dans la direction dir sans pouvoir exécuter
	 * d'autres actions en même temps.
	 * @param a angle en degrès
	 * @param dir si vaut -1 rotation à gauche si vaut 1 rotation a droite
	 */
	public void tourner(double a, int direction) {
		pilot.rotate(a*direction,false);
		orientation =(orientation + 360 +a*direction) % 360;
	}
	
	/**
	 * Le robot effectue une rotation de a degrès dans la direction dir tout en pouvant exécuter
	 * d'autres actions en même temps. Celle-ci ne modifie pas l'orientation !
	 * @param a angle en degrès
	 * @param dir si vaut -1 rotation à gauche si vaut 1 rotation a droite
	 */
	public void tournerSO(double a, int direction) {
		pilot.rotate(a*direction,false);
	}

	/**
	 * Le robot tourne de a degres vers la gauche.
	 * 
	 * @param a angle en degres strictement superieur à 0
	 */
	public void tournerG(double a) {
		pilot.rotate(-a);
		orientation = (orientation + 360 - a) % 360;
	}

	/**
	 * Le robot effectue un virage pour reprendre son orientation initiale. Le robot
	 * fera le virage le plus court possible (vers la droite ou vers la gauche), si
	 * celui-ci est bien orienté il ne fait rien.
	 */
	public void sorienterVersEnBut() {
		if (orientation > 180.0)
			tournerD(360 - orientation);
		else
			tournerG(orientation);
		orientation = 0.0;
	}
	/**
	 * Le robot effectue un virage pour reprendre une orientation opposée à celle initiale. Le robot
	 * fera le virage le plus court possible (vers la droite ou vers la gauche), si
	 * celui-ci est bien orienté il ne fait rien.
	 */
	public void sorienterOpposeEnBut() {
		if(orientation > 180.0) 
			tournerG(orientation-180);
		else 
			tournerD(180-orientation);
		
	}

	/**
	 * Le robot arrete tout mouvement en cours.
	 */
	public void arret() {
		pilot.stop();
	}

	/**
	 * Définit le vitesse angulaire et la vitesse linéaire du robot. En fonction de
	 * sa vitesse maximum
	 * 
	 * @param pourcent pourcentage de la vitesse maximum
	 */
	public void setSpeed(double pourcent) {
		if (pourcent >= 100)
			pourcent = 100;
		else if (pourcent < 10)
			pourcent = 10;
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

