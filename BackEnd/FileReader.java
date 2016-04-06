
//	CSCI 3060/ SOFE 3980 Course Project back end
//	Group: Untitled
//	Member: Calvin Lo, Albert Fung, Karan Chandwaney
//	Purpose: reads in files for the back end and merges the Bank Account Transaction Files

import java.util.*;
import java.io.*;

public class FileReader {
	// stores the transaction file paths
	List<String> transFilePaths = new ArrayList<String>();

	// stores the content of all the merged transaction files

	List<String> transFileContent = new ArrayList<String>();

	// stores the filepath of the transaction file directory and merged
	// transaction files output
	String filePath, mergedPath;

	// constructor, sets global variables from that was just created
	public FileReader(String path, String merge) {
		filePath = path;
		mergedPath = merge;
	}

	// read file
	// arguments: filename of the file to read
	public List<String> readFile(String filename) {
		// store everything in an ArrayList
		List<String> results = new ArrayList<String>();
		// try catch, read the file line by line
		try {
			BufferedReader br = new BufferedReader(new java.io.FileReader(filename));
			String s;
			// while there is still more to read
			while ((s = br.readLine()) != null) {
				// add the current line to the array list
				results.add(s);
			}
			// print out error message
		} catch (IOException e) {
			System.out.println("ERROR: Input/Output error");
		}

		// return the list
		return results;
	}

	// matcher
	// arguments: filePath of where to search, toFind: file or directory to find
	public File[] match(String filePath, String toFind) {
		// create filenamefilter object to search directory
		FilenameFilter text = new FilenameFilter() {
			// returns true if the file or directory is found
			public boolean accept(File directory, String name) {
				// convert to lowercase to ensure consistency
				String lowercase = name.toLowerCase();
				// if the string contains the string that we are trying to find,
				// return true
				if (lowercase.contains(toFind)) {
					return true;
				} else {
					return false;
				}
			}
		};

		// create a file object with the directory of where we are searching
		File f = new File(filePath);
		// list all the files in the current directory and store it in an array
		File[] files = f.listFiles(text);
		// return an array of all the files or folders with the name specified
		return files;
	}

	// merger
	public void mergeTrans() {
		// search array stores keywords that we are looking for
		// in this case, we are looking for the sub-folders of all the commands
		// that contain the transaction files
		String search[] = { "login", "withdrawal", "transfer", "paybill", "deposit", "create", "delete", "disable",
				"enable", "changeplan" };

		// iterate through all the keywords/commands
		for (int i = 0; i < search.length; i++) {
			// call match method to find if the sub-folders exist, if they do
			// store in files array
			File[] files = match(filePath, search[i]);

			// try catch in case directories don't exist
			try {
				// if found, the directory should be located in the first
				// element of the array, set current path
				String path = files[0].toString();

				// searches and stores in array containing the filepaths to all
				// the transaction files
				File[] transFiles = match(path, ".trans");

				// iterate through all the transaction files
				for (int j = 0; j < transFiles.length; j++) {
					// add the transaction file path to the global list
					this.transFilePaths.add(transFiles[j].toString());
				}
				// print out error message
			} catch (Exception e) {
				System.out.println("Keyword directory " + search[i] + " not found.");
			}
		}
		// call read transactions and pass in the global list
		readTransactions(this.transFilePaths);
	}

	// returns the global list containing the transaction files
	public List<String> get() {
		return this.transFileContent;
	}

	// append the current list to the global list
	public void readTransactions(List<String> list) {
		// iterate through the list
		for (int i = 0; i < list.size(); i++) {
			// append to the global list of transaction file content
			this.transFileContent.addAll(readFile(list.get(i)));
		}

	}
}