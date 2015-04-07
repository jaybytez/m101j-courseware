#!/bin/bash          
export MONGODB=D:/Development/tools/MongoDB/Server/3.0/bin/
export PATH=$PATH:$MONGODB
mongo localhost:27017/test addPost.js