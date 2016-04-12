#!/bin/bash

# change BankAccount_Weekly and MasterAccounts_Weekly to BankAccount and MasterAccounts
for i in `seq 1 4`
do
    mv Weekly_Day$((i-1))_MasterAccounts.txt MasterAccounts.txt 
    mv Weekly_Day$((i-1))_BankAccount.txt BankAccount.txt
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
    
    # change BankAccount and MasterAccounts to BankAccount_Weekly and MasterAccounts_Weekly
    mv MasterAccounts.txt Weekly_Day$((i-1))_MasterAccounts.txt
    mv BankAccount.txt Weekly_Day$((i-1))_BankAccount.txt
    
    mv MergedTransactionFile.txt Weekly_Day"$i"_MergedTransactionFile.txt
    mv new_BankAccounts.txt Weekly_Day"$i"_BankAccount.txt
    mv new_MasterAccounts.txt Weekly_Day"$i"_MasterAccounts.txt
    
    # Delete the newline
    truncate -s -1 Weekly_Day"$i"_MasterAccounts.txt Weekly_Day"$i"_BankAccount.txt
    
done
