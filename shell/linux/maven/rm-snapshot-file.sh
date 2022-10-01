#!/bin/bash

find ~/.m2 -type d -name "*SNAPSHOT" -exec rm -rf {} \;

