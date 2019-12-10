package robotv33;

import lejos.utility.Delay;
/**
* Test chaque ouverture pour verifier 
* que l'ouverture correspond 
* 
* 
* 
*/
public class MP_test {

	
	
	public static void main(String [] args) {
		MP p = new MP();
		p.retablir();
		p.ouvrirZ();
		Delay.msDelay(2000);
		p.retablir();
		p.fermerZ();
		Delay.msDelay(2000);
		p.retablir();
		p.ouvrirZ();
		p.fermerZ();
		Delay.msDelay(2000);
		p.retablir();
		p.fermerA();
		Delay.msDelay(2000);
		p.retablir();
		p.ouvrirA();
		Delay.msDelay(2000);
		p.retablir();
		p.ouvrirA();
		p.fermerA();
		Delay.msDelay(2000);
		p.ouvrirPP();
		p.fermerPP();
		Delay.msDelay(2000);
		p.retablir();
	}
}
