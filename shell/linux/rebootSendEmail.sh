#!/bin/bash

# crontab -e
# @reboot sleep 30 && sh /home/changjinwei/rebootSendEmail.sh



date | s-nail -r "nuc8i7 <2468080401@qq.com>" -s "nuc8i7 reboot time" 469753862@qq.com
        