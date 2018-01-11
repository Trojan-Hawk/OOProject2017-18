// Student Name:		Timothy Cassidy
// Student Number:		G00333333
package ie.gmit.sw;
/** @author Timothy Cassidy
 * The shingle class takes in a document id and
 * a hashCode.
 */
public class Shingle {
	// Member variable(s)
	private int docId;
	private int hashcode;
	
	/**
	 * Default constructor
	 */
	Shingle(){	
	}// Shingle(Default Constructor)
	
	/**
	 * Parameterised constructor
	 * @param docId int
	 * @param hashCode int
	 */
	Shingle(int docId, int hashCode){
		this.docId = docId;
		this.hashcode = hashCode;
	}// Shingle(Constructor)
	
	/**
	 * Getter for the docId.
	 * @return docId
	 */
	// Getters and Setters
	public int getDocId() {
		return docId;
	}// getDocId
	/**
	 * Setter for the docId.
	 * @param docId
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}// setDocId
	/**
	 * Getter for the hashCode.
	 * @return hashCode
	 */
	public int getHashcode() {
		return hashcode;
	}// getHashcode
	/**
	 * Setter for the hashcode.
	 * @param hashcode
	 */
	public void setHashcode(int hashcode) {
		this.hashcode = hashcode;
	}// setHashcode
	
}// Shingle