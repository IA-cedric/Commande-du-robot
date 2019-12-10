/**
 * 
 */
package robotv33;

/**
 * @author Cédric
 *
 */
public class TypeLastDistance {
	private float lastDistance=-1;
	private char type='r';
	/**
	 * @return the lastDistance
	 */
	public float getLastDistance() {
		return lastDistance;
	}
	/**
	 * @param lastDistance the lastDistance to set
	 */
	public void setLastDistance(float lastDistance) {
		this.lastDistance = lastDistance;
	}
	/**
	 * @return the type
	 */
	public char getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(char type) {
		this.type = type;
	}
	
	public void set(char type, float lastDistance) {
		setLastDistance(lastDistance);
		setType(type);
	}
	
	public String toString() {
		return ""+ lastDistance +  " "  + type;
	}
}
