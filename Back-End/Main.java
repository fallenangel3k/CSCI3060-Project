//	CSCI 3060/ SOFE 3980 Course Project back end
//	Group: Untitled
//	Member: Calvin Lo, Albert Fung, Karan Chandwaney
//	Purpose: Main class to run the back end
//	Input files: Transaction files, old master bank accounts file
//	Output files: Merged transaction files, new current bank accounts file, new master bank accounts file
// 	How this is intended to run: Compile all other classes and run this as the main class

public class Main {

	public static void main(String args[]) {
		// stores the directory of where the merged files will be outputed
		String mergedPath = "";
		// stores the directory of where the transaction files from front - end will be read in from
		String transDir = "";

		// mergedPath = "/home/vm/Code/Untitled/Phase4/";
		// transDir = "/home/vm/Code/Untitled/Phase3/";
		
		// try catch in case the user does not enter a valid path
		try {
			// first argument given is the merged path
			mergedPath = args[0];
			// second argument given is the transaction directory
			transDir = args[1];
			// if exception/error occurs, print out error messages
		} catch (Exception e) {
			System.out.println("No arguments specified, please enter the merge directory followed by the transaction file directory.");
			System.out.println("Ex. java Main /home/vm/Code/Untitled/Phase4/ /home/vm/Code/Untitled/Phase3/");
		}
		
		// create a new file reader
		FileReader fr = new FileReader(transDir, mergedPath);
		// merge the transaction files
		fr.mergeTrans();

		// create a new file writer
		FileWriter wr = new FileWriter("MergedBackAccountTrans.trans");
		// get the transactions from the file reader and write them to the new file
		wr.writeTransaction(fr.get());
		// close the file
		wr.close();

	

		Transactions T = new Transactions(transDir, mergedPath);

	}
}
