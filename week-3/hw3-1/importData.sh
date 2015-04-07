#!/bin/bash          
export MONGODB=D:/Development/tools/MongoDB/Server/3.0/bin/
export PATH=$PATH:$MONGODB
#mongoimport -d school -c students < students.json
mongo localhost:27017/test importAndVerify.js