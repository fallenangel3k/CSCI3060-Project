#!/bin/bash

# change BankAccount_Daily and MasterAccounts_Daily to BankAccount and MasterAccounts
mv Daily_MasterAccounts.txt MasterAccounts.txt 
mv Daily_BankAccount.txt BankAccount.txt

# run test cases input
for file in TestCases/*
do
    cat "$file" | ./FrontEnd/FrontEnd
done

./FrontEnd/FrontEnd # run interactive input

# merge
truncate -s 0 MergedTransactionFile.txt # clear the contents
DATE=`date "+%m%d%Y"` # get the current date
for file in TransactionFiles/$DATE*
do
    cat "$file" >> MergedTransactionFile.txt
done

rm TransactionFiles/*


# run the backend
java BackEnd.Main MergedTransactionFile.txt

# change BankAccount and MasterAccounts to BankAccount_Daily and MasterAccounts_Daily
mv MasterAccounts.txt Daily_MasterAccounts.txt 
mv BankAccount.txt Daily_BankAccount.txt

mv MergedTransactionFile.txt Daily_MergedTransactionFile.txt
mv new_BankAccounts.txt Daily_New_BankAccount.txt
mv new_MasterAccounts.txt Daily_New_MasterAccounts.txt

# Delete the newline
truncate -s -1 Daily_New_MasterAccounts.txt Daily_New_BankAccount.txt

