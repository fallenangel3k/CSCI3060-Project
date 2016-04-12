#!/bin/bash

# change BankAccount_Weekly and MasterAccounts_Weekly to BankAccount and MasterAccounts
mv Weekly_MasterAccounts.txt MasterAccounts.txt 
mv Weekly_BankAccount.txt BankAccount.txt


for i in `seq 1 2`
do
    for file in Day$i/*
    do
        cat "$file" | ./FrontEnd/FrontEnd
    done
    
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
    mv MasterAccounts.txt Weekly_MasterAccounts.txt 
    mv BankAccount.txt Weekly_BankAccount.txt

    mv MergedTransactionFile.txt Weekly_Day$i_MergedTransactionFile.txt
    mv new_BankAccounts.txt Weekly_Day$i_New_BankAccount.txt
    mv new_MasterAccounts.txt Weekly_Day$i_New_MasterAccounts.txt

    
done
