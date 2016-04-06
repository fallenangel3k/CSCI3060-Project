
//	CSCI 3060/ SOFE 3980 Course Project back end
//	Group: Untitled
//	Member: Calvin Lo, Albert Fung, Karan Chandwaney
//	Purpose: writes out files for the back end

import java.util.*;
import java.io.*;

public class FileWriter {
	java.io.FileWriter FW;

	public FileWriter(String filename) {
		try {
			FW = new java.io.FileWriter(new File(filename));
		} catch (IOException e) {
			System.out.println("ERROR: Cannot open File Writer.");
		}
	}
	
	// produces a new Merged Transaction File
	public void writeTransaction(List<String> transactions) { 
		// loop through all transactions
		for (int i = 0; i < transactions.size(); i++) { 
			// try to write to the file
			try {
				FW.write(transactions.get(i) + '\n');
				// catch the error if for some reason we cannot write to the file
			} catch (IOException e) {
				System.out.println("ERROR: Cannot write file");
			}
		}
	}
	
	// produces a new Current Bank Account File
	public void writeCurrent(List<account> updated_accounts) {
		// loop all the updated accounts and write the information to the new
		// file
		for (int i = 0; i < updated_accounts.size(); i++) {
			// init variables
			int index = 0;
			String line = "";
			// fill in the account number
			line += updated_accounts.get(i).acc_num + " ";
			// fill in the account holder
			// if the account holder name is less than 20 characters
			if (updated_accounts.get(i).acc_holder.length() < 20) {
				// fill out the name first and space separator
				line += updated_accounts.get(i).acc_holder + " ";
				// fill out the remaining spaces
				for (int j = 0; j < 20 - updated_accounts.get(i).acc_holder.length(); j++) {
					line += " ";
				}
			}
			// if the account holder name had exactly 20 characters
			else if (updated_accounts.get(i).acc_holder.length() == 20) {
				line += updated_accounts.get(i).acc_holder + " ";
			}
			// fill in the account status
			line += updated_accounts.get(i).acc_status + " ";
			// fill in the account balance
			String balance = updated_accounts.get(i).acc_balance;
			// check the decimal
			if (balance.contains(".")) {
				if (balance.indexOf('.') == (balance.length() - 2)) {
					balance += '0';
				}

			}
			// make sure the account balance have a length of 8
			if (balance.length() < 8) {
				// fill out the 0 first
				for (int j = 0; j < 8 - balance.length(); j++) {
					line += "0";
				}
				// fill out the actual balance
				line += balance + " ";
			} else if (balance.length() == 8) {
				line += balance + " ";
			}
			// fill in teh account type
			line += updated_accounts.get(i).acc_type;

			// Write the information
			try {
				FW.write(line + '\n');
			} catch (IOException e) {
				System.out.println("ERROR: Cannot write file");
			}
		}
	}

	// produces a new master bank account file
	public void writeMaster(List<account> updated_accounts) {
		// loop all the updated accounts and write the information to the new
		// file
		for (int i = 0; i < updated_accounts.size(); i++) {
			// init variables
			int index = 0;
			String line = "";
			// fill in the account number
			line += updated_accounts.get(i).acc_num + " ";
			// fill in the account holder
			// if the account holder name is less than 20 characters
			if (updated_accounts.get(i).acc_holder.length() < 20) {
				// fill out the name first and space separator
				line += updated_accounts.get(i).acc_holder + " ";
				// fill out the remaining spaces
				for (int j = 0; j < 20 - updated_accounts.get(i).acc_holder.length(); j++) {
					line += " ";
				}
			}
			// if the account holder name had exactly 20 characters
			else if (updated_accounts.get(i).acc_holder.length() == 20) {
				line += updated_accounts.get(i).acc_holder + " ";
			}
			// fill in the account status
			line += updated_accounts.get(i).acc_status + " ";
			// fill in the account balance
			String balance = updated_accounts.get(i).acc_balance;
			// check the decimal

			if (balance.contains(".")) {
				if (balance.indexOf('.') == (balance.length() - 2)) {
					balance += '0';
				}

			}

			// make sure the account balance have a length of 8
			if (balance.length() < 8) {
				// fill out the 0 first
				for (int j = 0; j < 8 - balance.length(); j++) {
					line += "0";
				}
				// fill out the actual balance
				line += balance + " ";
			} else if (balance.length() == 8) {
				line += balance + " ";
			}
			// fill in the account type
			line += updated_accounts.get(i).acc_type + " ";

			// fill in the number of transactions
			String num_trans = Integer.toString(updated_accounts.get(i).num_trans);
			// fill in 0 if the length of the num_trans is less than 4
			if (num_trans.length() < 4) {
				// fill in 0 first
				for (int j = 0; j < 4 - num_trans.length(); j++) {
					line += "0";
				}
				// fill in the actual number
				line += num_trans;
			} else if (num_trans.length() == 4) {
				line += num_trans;
			}
			// if the num of trans is greater than 4 digits
			else if (num_trans.length() > 4) {
				line += "9999";
			}

			// Write the information
			try {
				FW.write(line + '\n');
			} catch (IOException e) {
				System.out.println("ERROR: Cannot write file");
			}
		}
	}

	// close the FileWriter
	public void close() {
		try {
			FW.close();
		} catch (IOException e) {
			System.out.println("ERROR: Cannot close File Writer");
		}
	}
}