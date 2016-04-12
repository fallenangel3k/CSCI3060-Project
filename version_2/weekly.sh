#!/bin/bash
# CSCI 3060/ SOFE 3980 Course Project Daily Script
# Group: Blissfully Elephant
# Member: Calvin Lo, Matthew Mccormick

for i in `seq 1 5`
do    
    Cases=Day$i
    Master=Weekly_Day$((i-1))_MasterAccounts.txt
    Bank=Weekly_Day$((i-1))_BankAccount.txt
    Merged=Weekly_Day"$i"_MergedTransactionFile.txt
    NewMaster=Weekly_Day"$i"_MasterAccounts.txt
    NewBank=Weekly_Day"$i"_BankAccount.txt
    
    ./daily.sh $Cases $Master $Bank $Merged $NewMaster $NewBank 0
    
done
 
