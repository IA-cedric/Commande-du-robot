package robotv2;

public class TestCTMP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CT contact = new CT();
		System.out.println("CONTACT OK");
		MP pince = new MP();
		System.out.println("PINCE OK");
		pince.fermerA();
		pince.ouvrirA();
		while(true) {
			if(contact.estPresse(false)) {
				break;
			}
		}
		
		pince.fermerA();
	}

}
