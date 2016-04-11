#!/bin/bash

# Simulated 2d array that contains the filenames for the tests to run each day
tests=("ValidAdminCreateAccountNumber.txt" "DepositAdminSuccessful.txt" "EnableBankAccount.txt" "DisableAccountSuccessful.txt" "SuccessfulDeposit.txt"
       "2 1" "2 2" "2 3" "2 4" "2 5"
       "3 1" "3 2" "3 3" "3 4" "3 5"
       "4 1" "4 2" "4 3" "4 4" "4 5"
       "5 1" "5 2" "5 3" "5 4" "5 5")

for i in `seq 0 4`;
do
    # Accesses the elements from the simulated 2d array
    echo
    echo ${tests[$((($1 - 1) * 5 + $i))]}
    cat ./TestCases/${tests[$((($1 - 1) * 5 + $i))]} | ./FrontEnd/FrontEnd
    mv ./TransactionFile.txt ./Day${1}/TransactionFile$((1 + $i)).txt
done

for i in `seq 0 4`;
do
    # Merge the transaction files
    cat ./Day${1}/TransactionFile$((1 + $i)).txt >> ./Day${1}/MergedTransactionFile.txt
done

cd ./BackEnd
java Main ../MergedTransactionFile.txt
