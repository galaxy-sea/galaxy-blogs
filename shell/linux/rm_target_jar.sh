#!/bin/sh

find ~/my-workbench -path "*/target/*.jar" -exec rm -rf {} \;
