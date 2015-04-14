#!/bin/bash          
export MONGODB=D:/Development/tools/MongoDB/Server/3.0/bin/
export PATH=$PATH:$MONGODB
mongoimport -d blog -c posts < posts.json