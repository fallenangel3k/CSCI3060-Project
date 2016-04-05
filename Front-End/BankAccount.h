#pragma once
#include <string>
#include <iostream>
using namespace std;

// Used to store and perform actions on
// bank account information
class BankAccount
{
private: 
	int number;
	string name;
	double balance;
	bool active;
	string type;
public:
	BankAccount();
	BankAccount(string accountnum, string newname,string status, string change, string type);
	~BankAccount();
	void updateBalance(double amount);
	void enable();
	void disable();
	void changeType();
	string getName();
	int getAccNumber();
	double getBalance();
	double transactionfee();
	bool getActivity();
	string getType();
};

