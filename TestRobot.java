package robotv2;

public class TestRobot {

	public static void main(String[] args) {
		MR roues = new MR();
		System.out.println("ROUES OK");
		CU yeux = new CU();
		System.out.println("YEUX OK");
		CT contact = new CT();
		System.out.println("CONTACT OK");
		CC zone = new CC();
		System.out.println("ZONE OK");
		MP pince = new MP();
		System.out.println("PINCE OK");
		
		int i =0;
		roues.avancer();
		while(roues.getEnMouvement() && i<100) {
			if(contact.estPresse(false)) {
				roues.arret();
			}
			i++;
			
		}
		roues.sorienterVersEnBut();
		roues.avancer(20.0);

	}

}
