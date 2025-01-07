#!/bin/bash

# ubuntu 脚本

lanternPid=$(pidof lantern)

if [ "$lanternPid" ]
then
    echo "lantern is in normal operation ..."
else
    export DISPLAY=:0
    echo "start lantern ..."
    nohup lantern >~/lantern.log 2>&1 &

    echo "kill firefox and wait for 5 seconds ..."
    sleep 5
    kill -9 $(pidof firefox)
fi
