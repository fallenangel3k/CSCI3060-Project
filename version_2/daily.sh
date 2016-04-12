#!/bin/bash

# run test Cases
cd FrontEnd
for file in ../TestCases/*
do
    cat "$file" | ./FrontEnd
done

./FrontEnd
cd ..

# merge
truncate -s 0 MergedTransactionFile.txt # clear the contents
DATE=`date "+%m%d%Y"`
for file in TransactionFiles/$DATE*
do
    cat "$file" >> MergedTransactionFile.txt
done


# run the backend
cd BackEnd
java Main ../MergedTransactionFile.txt