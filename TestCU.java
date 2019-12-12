package robotV2;

import lejos.utility.Delay;

/**
 * Cette classe permet de tester toutes les fonctions de la classe CU
 * @see CU
 *
 */
public class TestCU {
	public static void main(String args[]) {
		CU yeux = new CU();
		//tester pour trois distances différentes, et notamment dans l'angle, et à partir de quand détecte plus le palet
		System.out.println(yeux.getDistance());
		Delay.msDelay(2000);
		System.out.println(yeux.getDistance());
		Delay.msDelay(2000);
		System.out.println(yeux.getDistance());
		Delay.msDelay(2000);
		//test le get de la fonction
		System.out.println(yeux.getlastDistance());
		Delay.msDelay(2000);
		//test infinity pour deux cas, un cas ou il est censé voir et un cas ou y'a rien devant lui ou très loin
		System.out.println(yeux.detectInfinity());
		Delay.msDelay(2000);
		System.out.println(yeux.detectInfinity());
		Delay.msDelay(2000);
	}
}
