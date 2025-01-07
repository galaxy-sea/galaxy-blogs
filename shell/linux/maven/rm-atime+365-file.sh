#!/bin/bash

find ~/.m2/repository -type d -atime +365 -exec rm -rf {} \;

