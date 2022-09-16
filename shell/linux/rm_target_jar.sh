#!/bin/sh

find ~/ -path "*/target/*.jar" -exec rm -rf {} \;
