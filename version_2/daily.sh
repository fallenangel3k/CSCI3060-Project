#!/bin/bash

cd FrontEnd
cat  ../ValidAdminCreateAccountNumber.txt | ./FrontEnd
cd ..

cd BackEnd
java Main ../TransactionFile.txt