package robotv2;

import lejos.utility.Delay;

public class TestCU {
	public static void main(String args[]) {
		CU yeux = new CU();
		System.out.println(yeux.getDistance());
		Delay.msDelay(2000);
		System.out.println(yeux.getDistance());
		Delay.msDelay(2000);
		System.out.println(yeux.getLastDistance());
		Delay.msDelay(2000);
		System.out.println(yeux.getDistance());
		Delay.msDelay(2000);
	}
}
