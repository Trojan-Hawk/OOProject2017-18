// Student Name:		Timothy Cassidy
// Student Number:		G00333333
package ie.gmit.sw;
/** @author Timothy Cassidy
 * The poison class is an extention of shingle, is a 
 * shingle, it just sets the shingle to a zero value,
 * it is used when parsing the blocking queue to let the
 * program know when the end of a file has been reached.
 */
public class Poison extends Shingle{
	
	Poison(int id, int hash){
		this.setDocId(id);
		this.setHashcode(hash);
	}// Poison(Constructor)
	
}// Poison