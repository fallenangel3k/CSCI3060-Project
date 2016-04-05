#include "stdafx.h"
#include "BankAccount.h"
#include "stdafx.h"
#include "BankAccount.h"
#include <string>
#include <iostream>
using namespace std;


BankAccount::BankAccount() {
	name = "Default";
	balance = 0.00;
	type = "NP";
}
// Creaes a new BankAccount type with a specified name and balance
BankAccount::BankAccount(string accountnum, string newname,string status, string change, string
	accounttype) {
	number =  stoi(accountnum);
	name = newname;
	balance = stod(change);
	type = accounttype;
	if (status.compare("E") == 0){
		active = true;
	}
	else{
		active = false;
	}

}

// Destructs a BankAccount
BankAccount::~BankAccount() {
}

// adds or subtracts an amount to the balance. Amount should be negative for deposits
void BankAccount::updateBalance(double amount) {
	balance += amount;
}

// Enables an account
void BankAccount::enable() {
	if (!active) {
		active = true;
	}
}

// Disables an account
void BankAccount::disable() {
	if (active) {
		active = false;
	}
}

// Changes the account plan type
void BankAccount::changeType() {
	if (type.compare("SP") == 0) {
		type.assign("NP");
	}
	else if (type.compare("NP") == 0) {
		type.assign("SP");
	}
}

// Returns the name on the account
string BankAccount::getName() {
	return name;
}

// Returns the account number
int BankAccount::getAccNumber() {
	return number;
}

// Returns the balance on the account
double BankAccount::getBalance() {
	return balance;
}

// Returns the transaction fee on the account
double BankAccount::transactionfee() {
	if (type.compare("SP") == 0) {
		return 0.05;
	}
	else if (type.compare("NP") == 0) {
		return 0.10;
	}
}

// Returns whether the account is active or disabled
bool BankAccount::getActivity() {
	return active;
}

// Returns the account plan type (student or non-student)
string BankAccount::getType() {
	return type;
}


