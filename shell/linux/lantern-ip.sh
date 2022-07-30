#!/bin/bash

# ubuntu
cp ~/.lantern/settings.yaml ~/.lantern/settings.yaml.bak

# mac
# cp ~/Library/Application\ Support/Lantern/settings.yaml ~/Library/Application\ Support/Lantern/settings.yaml.bak

sed -i 's|^addr: .*$|addr: 10.168.1.100:33733|g' ~/.lantern/settings.yaml
sed -i 's|^socksAddr: .*$|socksAddr: 10.168.1.100:42167|g' ~/.lantern/settings.yaml
sed -i 's|^uiAddr: .*$|uiAddr: 10.168.1.100:43313|g' ~/.lantern/settings.yaml

