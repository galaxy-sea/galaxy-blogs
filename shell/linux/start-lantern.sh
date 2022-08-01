#!/bin/bash

# ubuntu 脚本

lanternPid=$(pidof lantern)

if [ "$lanternPid" ]
then
    echo "lantern is in normal operation ..."
else
    echo "start lantern ..."
    nohup lantern >~/lantern.log 2>&1 &
    sleep 20
    kill -9 $(pidof firefox)
fi
