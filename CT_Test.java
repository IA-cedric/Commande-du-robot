package robotv33;

/**
 * Test les deux modes de recherche d'appuie sur le bouton 
 *  
 * 
 * @see CT 
 * 
 */

public class CT_Test {

	public static void main(String [] args) {
		CT btn = new CT();
		if(btn.estPresse(false)) {
			System.out.println("presserFin fonctionne");
		}
	}
}
