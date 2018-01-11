// Student Name:		Timothy Cassidy
// Student Number:		G00333333
package ie.gmit.sw;
/** @author Timothy Cassidy
 * The Launcher is responsible for the creation of threads,
 * The joining of these threads,
 * The calling the DocParser,
 * And finally calling the consumer.
 */
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Launcher {
	// member variables
	BlockingQueue<Shingle> q = new LinkedBlockingQueue(1000000);
	private int shingleSize = 7;
	private int numOfHashes = 15;
	private int poolSize = 100;
	
	/**
	 * The launch method takes in two validated files.
	 * It creates two threads and passes each document a thread.
	 * It then joins these threads.
	 * When they have exited their run methods a Third thread
	 * is created, an instance of Consumer is called and passed
	 * the Queue, the number of hashes, the pool size, and the 
	 * filenames of both documents.
	 * @param doc1 String filename
	 * @param doc2 String filename
	 */
	public void launch(String doc1, String doc2) {
		
		// DocParser Threads
		Thread t1 = new Thread(new DocParser(doc1, q, shingleSize));
		Thread t2 = new Thread(new DocParser(doc2, q, shingleSize));
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
			
			// Consumer Thread
			Thread t3 = new Thread(new Consumer(q, numOfHashes, poolSize, doc1, doc2));
			t3.start();
			t3.join();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}// try//catch
		
	}// launch
	
}// Launcher