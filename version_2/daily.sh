#!/bin/bash
# CSCI 3060/ SOFE 3980 Course Project Daily Script
# Group: Blissfully Elephant
# Member: Calvin Lo, Matthew Mccormick
# Usage: ./daily.sh <directory of test cases> <MasterAccounts> <BankAccount> <MergedFile> <New BankAccount> <new MasterAccounts> <Interactive (0 or 1)>
# eg. 
# ./daily.sh Day0 Daily_MasterAccounts.txt Daily_BankAccount.txt Daily_MergedTransactionFile.txt Daily_New_MasterAccounts.txt Daily_New_BankAccount.txt 0

if [ $# != 7 ]; then
    echo "Usage: ./daily.sh <directory of test cases> <MasterAccounts> <BankAccount> <MergedFile> <New BankAccount> <new MasterAccounts> <Interactive (0 or 1)>"
    exit
fi

# change BankAccount input and MasterAccounts input to BankAccount and MasterAccounts
mv $2 MasterAccounts.txt 
mv $3 BankAccount.txt

# run test cases input
for file in $1/*
do
    cat "$file" | ./FrontEnd/FrontEnd
done

if [ $7 = 1 ]; then
    ./FrontEnd/FrontEnd # run interactive input
fi

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

# change BankAccount and MasterAccounts to original name
mv MasterAccounts.txt $2 
mv BankAccount.txt $3

mv MergedTransactionFile.txt $4
mv new_MasterAccounts.txt $5
mv new_BankAccounts.txt $6

# Delete the newline
truncate -s -1 $5 $6

