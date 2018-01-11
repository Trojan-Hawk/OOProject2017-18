// Student Name:		Timothy Cassidy
// Student Number:		G00333333
package ie.gmit.sw;
/** @author Timothy Cassidy
 * DocParser is responsible for parsing a given file,
 * Creating n size shingles from these files,
 * Then adding these shingles to a blocking queue.
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class DocParser implements Runnable {
	// Instance variable(s)
	BufferedReader br = null; 
	
	// Member variable(s)
	private String doc;
	private int docId;
	private BlockingQueue<Shingle> q;
	private Queue<String> buffer = new LinkedList<String>();
	private int shingleSize;
	private String[] words;
	
	/**
	 * The constructor for the DocParser class
	 * @param doc document name
	 * @param q BlockingQueue that takes in Shingles
	 * @param shingleSize
	 */
	DocParser(String doc, BlockingQueue<Shingle> q, int shingleSize){
		this.doc = doc;
		this.q = q;
		this.shingleSize = shingleSize;
		// using the filename to generate unique document id
		this.docId = doc.hashCode();
	}// DocParser(consructor)
	
	@Override
	/**
	 * Run method needed for the implemented Runnable.
	 * This method reads in each line of the file, 
	 * removes all characters not in the regex specified, 
	 * splits them on each space and then adds them the 
	 * linkedlist queue.
	 * Then the getNextShingle and flushBuffer methods are called. 
	 */
	public void run(){
		try {
			// open the file to be read
			br = new BufferedReader(new InputStreamReader(new FileInputStream(doc)));
			String line = null;
			// reading in each line until the EOF is reached
			while((line = br.readLine()) != null){
				// removing non alphabetic characters
				line.replaceAll("[^a-zA-Z]", "");
				// adding the line of words split at each space
				// setting all words to lowercase
				words = line.toLowerCase().split(" ");
				// call the addWordsToBuffer method
				addWordsToBuffer(words);
			}// while
			
			// getting the next shingle(initial read)
			Shingle s = getNextShingle();
			// repeat until new shingle is null
			while(s != null) {
				// add it to the blocking queue if not null
				q.put(s);
				// There seems to be an issue here with larger files,
				// once the queue.put method is called on them it completely halts
				// getting the next shingle(subsequent read) 
				s = getNextShingle();
			}// while
			// flushing the buffer
			flushBuffer();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}// try/catch
	}// run
	
	/**
	 * Adds each element in the string array to the buffer. The buffer is
	 * a LinkedList Queue of Strings.
	 * @param words string array of each parsed line from the file
	 */
	private void addWordsToBuffer(String[] words){
		for(String s : words){
			if(s != null)
				buffer.add(s);
		}// for
	}// addWordsToBuffer
	
	/**
	 * The getNextShingle polls strings off the LinkedList Queue
	 * and appends them onto the stringbuilder,
	 * which is then, if not null, passed to the shingle class
	 * and a Shingle object is created using the document Id and
	 * the stringbuilder, after calling the .toString().toHashCode()
	 * methods
	 * @return either a populated shingle or null
	 */
	private Shingle getNextShingle(){
		// making an instance of stringbuilder
		StringBuilder sb = new StringBuilder();
		// counter to track words added to shingle
		int counter = 0;
		while(counter < shingleSize){
			if (buffer.peek()!= null){
				sb.append(buffer.poll());
				counter++;
			}// if
			else {
				break;
			}// else
		}// while
		
		// if there is nothing on the string builder return null
		if (sb.length() == 0) {return null;}// if
		else {return (new Shingle(docId, sb.toString().hashCode()));}
	}// Shingle
	
	/**
	 * The FlushBuffer method adds any strings left in the 
	 * buffer to a new Shingle object and then it poisons the 
	 * queue by adding a poison object, which extends shingle
	 * class, to the queue so we can identify when the end of 
	 * the document is reached on the queue.
	 */
	private void flushBuffer() {
		while(buffer.size() > 0){
			Shingle s = getNextShingle();
			try {
				if(s != null) {q.put(s);}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}// try/catch
		}// while
		
		// poison to signify the end of a document
		try {
			q.put(new Poison(0,0));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}// try/catch
	}// flushBuffer
	
}// DocParser