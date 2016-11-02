#!/bin/bash
if pgrep X > /dev/null; then
 sudo kill $(pgrep X)
fi
sudo startx & 
export DISPLAY=:0.0
sudo java -jar /home/pi/Gui.jar&
