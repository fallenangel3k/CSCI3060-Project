
//	CSCI 3060/ SOFE 3980 Course Project back end
//	Group: Untitled
//	Member: Calvin Lo, Albert Fung, Karan Chandwaney
//	Purpose: processes and applies all transactions from merged Bank Account Transaction files

package BackEnd;

import java.util.*;

// Store the account information
class account {
	public String acc_holder;
	public String acc_num;
	public char acc_status;
	public String acc_balance;
	public char acc_type;
	public int num_trans;
}

// Store the transaction information
class trans {
	public String code;
	public String acc_holder;
	public String acc_num;
	public String amount;
	public String mis_info;
}

public class Transactions {

	List<account> all_accounts;
	List<trans> all_trans;
	FileReader FR;
	// store the current index of the transactions list
	int trans_index;
	// store the current login mode
	int curr_login_mode;
	
	
	// empty constructor for testing
	public Transactions() {
            curr_login_mode = 'S';
            all_accounts = new ArrayList<account>();
            all_trans = new ArrayList<trans>();
            FR = new FileReader();
            trans_index = 0;
	}
	
	public Transactions(String mergedPath) {
		// set the default login mode as standard
		curr_login_mode = 'S';
		all_accounts = new ArrayList<account>();
		all_trans = new ArrayList<trans>();
		FR = new FileReader();
		parseMaster("MasterAccounts.txt");
		parseMerged(mergedPath);

		// init trans_index to zero;
		trans_index = 0;

		// loop all transaction and execute corresponding commands
		while (trans_index < all_trans.size()) {
                        trans current_trans = all_trans.get(trans_index);
			execute(current_trans.code, current_trans);

			// add 1 to trans_index;
			trans_index++;
		}

		// calculate the trans fee
		for (int i = 0; i < all_accounts.size(); i++) {
			float trans_fee = all_accounts.get(i).num_trans * getTransactionFee(i);
			String trans_fee_S = Float.toString(trans_fee);
			minus(i, trans_fee_S);
		}

		// create new current bank account file
		createNewCurrent(all_accounts);
		// create new master bank account file
		createNewMaster(all_accounts);
	}

	// execute the commands based on the code
	public boolean execute(String code, trans current_trans) {
		if (code.equals("10")) {
			return login(current_trans);
		} else if (code.equals("01")) {
			return withdrawal(current_trans);
		} else if (code.equals("02")) {
                        trans_index++;
                        trans next_trans = all_trans.get(trans_index);
			return transfer(current_trans, next_trans);
		} else if (code.equals("03")) {
			return paybill(current_trans);
		} else if (code.equals("04")) {
			return deposit(current_trans);
		} else if (code.equals("05")) {
			return create(current_trans);
		} else if (code.equals("06")) {
			return delete(current_trans);
		} else if (code.equals("07")) {
			return disable(current_trans);
		} else if (code.equals("08")) {
			return changeplan(current_trans);
		} else if (code.equals("09")) {
			return enable(current_trans);
		} else if (code.equals("00")) {
			return logout(current_trans);
		} else {
			System.out.println("ERROR: Invalid transaction code!");
		}
		
		return false;
	}

	// read Master bank account file
	
	public boolean parseMaster(String path) {
		// get the file from the file reader
		List<String> masterFile = new ArrayList<String>();
		masterFile = FR.readFile(path);
		for (int i = 0; i < masterFile.size(); i++) {
			account temp_account = new account();
			// Bank account number start at pos 0 and end at 5.
			temp_account.acc_num = masterFile.get(i).substring(0, 5);
			// Bank account holder name start at 6 and end at 26;
			temp_account.acc_holder = masterFile.get(i).substring(6, 26);
			// Account status (Active or Disabled) at 27
			temp_account.acc_status = masterFile.get(i).charAt(27);
			// Accpunt balance start at 29 and end at 37
			temp_account.acc_balance = masterFile.get(i).substring(29, 37);
			// Account type (Student or Non Student) at 38
			temp_account.acc_type = masterFile.get(i).charAt(38);
			// number of transaction start at 40 to 43
			//temp_account.num_trans = Integer.parseInt(masterFile.get(i).substring(40, 44));
			temp_account.num_trans = 0;
			// Remove all the white space after the last character
			temp_account.acc_holder = temp_account.acc_holder.trim();

			// put the temp account to the list
			all_accounts.add(temp_account);
		}
		
		return true;
	}

	// read the merged Transaction file
	public boolean parseMerged(String path) {
		// get the file from the file reader
		List<String> transFile = new ArrayList<String>();
		transFile = FR.readFile(path);
		//transFile = FR.readFile("merged.trans");
		//transFile = FR.readFile("MergedBankAccountTrans.trans");
		// loop all the transaction
		for (int i = 0; i < transFile.size(); i++) {
			trans temp_trans = new trans();
			// code start at pos 0 to 2;
			temp_trans.code = transFile.get(i).substring(0, 2);
			// account holder name start at pos 3 and end at 22
			temp_trans.acc_holder = transFile.get(i).substring(3, 23);
			// account number start at pos 23 and end at 28
			temp_trans.acc_num = transFile.get(i).substring(24, 29);
			// amount start at pos 29 and end at 37
			temp_trans.amount = transFile.get(i).substring(30, 38);
			// mis info start at pos 38 and end at 40
			temp_trans.mis_info = transFile.get(i).substring(39, 41);

			// Remove all the white space after the last character
			temp_trans.acc_holder = temp_trans.acc_holder.trim();

			// put the temp account to the list
			all_trans.add(temp_trans);
		}
		
                return true;

	}

        // Write Current Bank Account File
	public void createNewCurrent(List<account> updated_accounts) {
		FileWriter FW = new FileWriter("new_BankAccounts.txt");
		FW.writeCurrent(updated_accounts);
		FW.close();
	}

	// Write Master Bank Account File
	public void createNewMaster(List<account> updated_accounts) {
		FileWriter FW = new FileWriter("new_MasterAccounts.txt");
		FW.writeMaster(updated_accounts);
		FW.close();
	}
	
	
	// search the target account by name
	// return the position of the account
	// return -1 if not found
	int searchName(String name) {
		for (int i = 0; i < all_accounts.size(); i++) {
			if (all_accounts.get(i).acc_holder.equals(name)) {
				return i;
			}
		}
		// if the account doesn't exist
		System.out.println("ERROR: Account doesn't exist.");
		return -1;
	}

	// search the target account by name AND account number
	// return the position of the account
	// return -1 if not found
	int searchNameAcc(String name, String acc_num) {
		for (int i = 0; i < all_accounts.size(); i++) {
			if (all_accounts.get(i).acc_holder.equals(name) && all_accounts.get(i).acc_num.equals(acc_num)) {
				return i;
			}
		}
                // if the account doesn't exist
		System.out.println("ERROR: Account doesn't exist.");
		return -1;
	}

	// minus the amount from a specfic account position
	// return true if succesfully minus, else return false
	boolean minus(int pos, String value) {
                
		// convert value to float
                float amount = Float.parseFloat(value);
                float balance = Float.parseFloat(all_accounts.get(pos).acc_balance);
		
		// minus the amount and transaction fee
		float new_balance = balance - amount;
		
		// if the new balance is less than 0
		if (new_balance < 0) {
			System.out.println("ERROR: Below 0.");
			return false;
		} else {
			// convert back the new balance to string and save it the account
			all_accounts.get(pos).acc_balance = String.format("%.2f", new_balance);
		}

		return true;
	}

	// add the amount from a specfic account position
	// return true if succesfully add, else return false
	boolean add(int pos, String value) {

		// convert value to float
                float amount = Float.parseFloat(value);
                float balance = Float.parseFloat(all_accounts.get(pos).acc_balance);
		
		// add the amount and minus the transaction fee
		float new_balance = balance + amount;
		// if the new balance is greater than 99999
		if (new_balance > 99999) {
			System.out.println("ERROR: Over 99999.");
			return false;
		}
		else {
			// convert back the new balance to string and save it the account
			all_accounts.get(pos).acc_balance = String.format("%.2f", new_balance);
		}
		return true;
	}

	
	// login
	public boolean login(trans current_trans) {

            if (current_trans.mis_info.charAt(0) == 'A') {
                curr_login_mode = 'A';
            }
            return true;

	}

	// withdrawal
 	public boolean withdrawal(trans current_trans) {
                boolean result = false;
		// search the position that match the account name and account number
		int pos = searchNameAcc(current_trans.acc_holder, current_trans.acc_num);

		// reduce the amount from that account
		if (pos != -1) {
			minus(pos, current_trans.amount);
			// increase the number of transaction if in standard mode
			if (curr_login_mode == 'S') {
				all_accounts.get(pos).num_trans++;
			}
			result = true;

		}
		return result;

	}

	// transfer
	public boolean transfer(trans current_trans, trans next_trans) {
	
                boolean result = false;
		// withdrawal money from the current index account
		withdrawal(current_trans);
		
		// deposit money to the next index account
		// search the position that match the account name and account number
		int pos = searchNameAcc(next_trans.acc_holder, next_trans.acc_num);

		// add the amount from that account
		if (pos != -1) {
			add(pos, next_trans.amount);
			result = true;
		}
		// if the account doesn't exist
		// deposit the amount back
		else {
			int pos2 = searchNameAcc(current_trans.acc_holder, current_trans.acc_num);
			// used - the transaction fee to add back the transaction fee
			add(pos2, current_trans.amount);
		}
		
		return result;
	}

	// paybill
	public boolean paybill(trans current_trans) {
		// withdraw money from the current index account
		return (withdrawal(current_trans));
	}

	// deposit
	public boolean deposit(trans current_trans) {
                boolean result = false;
		// search the position that match the account name and account number
		int pos = searchNameAcc(current_trans.acc_holder, current_trans.acc_num);

		// reduce the amount from that account
		if (pos != -1) {
			add(pos, current_trans.amount);
			// increase the number of transaction if in standard mode
			if (curr_login_mode == 'S') {
				all_accounts.get(pos).num_trans++;
			}
			
                    result = true;

		}
                return result;
	}

	// create
	public boolean create(trans current_trans) {
                
                boolean result = false;
                
		account acc = new account();
		acc.acc_holder = current_trans.acc_holder;
		acc.acc_num = current_trans.acc_num;
		acc.acc_balance = current_trans.amount;
		if (current_trans.mis_info.charAt(0) == 'A')
                    acc.acc_type = 'A';
                else 
                    acc.acc_type = 'S';
		acc.acc_status = 'E';
		acc.num_trans = 0;

		all_accounts.add(acc);
		
		result = true;
		return result;

	}

	// delete
	public boolean delete(trans current_trans) {

                boolean result = false;
                
		// search the position that match the account name and account number
		int pos = searchNameAcc(current_trans.acc_holder, current_trans.acc_num);
		
		// remove the account from the all accounts
		if (pos != -1) {
			all_accounts.remove(pos);
			result = true;
		}
		
		return result;
		
		
	}

	// enable
	public boolean enable(trans current_trans) {
	
                boolean result = false;
                // search the position that match the account name and account number
		int pos = searchNameAcc(current_trans.acc_holder, current_trans.acc_num);
		
		// change the account status to active
		if (pos != -1) {
			all_accounts.get(pos).acc_status = 'E';
			result = true;
		}
		
		return result;
	}

	// Disable
	public boolean disable(trans current_trans) {
                
                boolean result = false;
		// search the position that match the account name and account number
		int pos = searchNameAcc(current_trans.acc_holder, current_trans.acc_num);
		
		// change the account status to disable
		if (pos != -1) {
			all_accounts.get(pos).acc_status = 'D';
			result = true;
		}
		
		return result;
		
		
	}

	// changeplan
	public boolean changeplan(trans current_trans) {
	
                boolean result = false;
                
		// search the position that match the account name and account number
		int pos = searchNameAcc(current_trans.acc_holder, current_trans.acc_num);

		// change the plan
		if (pos != -1) {
			if (all_accounts.get(pos).acc_type == 'N') {
				all_accounts.get(pos).acc_type = 'S';
			}
			else if (all_accounts.get(pos).acc_type == 'S') {
				all_accounts.get(pos).acc_type = 'N';
			}
			result = true;
		}
		return result;
		
	}

	// logout
	public boolean logout(trans current_trans) {
		// set the current login mode to standard as default
		curr_login_mode = 'S';
		return true;
	}
	
	// minus the transaction fee
	float getTransactionFee(int pos) {
            
            if (all_accounts.get(pos).acc_type == 'S') {
                    return 0.05f;
            } else if (all_accounts.get(pos).acc_type == 'N') {
                    return 0.10f;
            }
            System.out.println("ERROR: Invalid account type.");
            return 0.0f;
    }

}