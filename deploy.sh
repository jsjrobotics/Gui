REMOTE_FILE=/home/pi/Gui.jar
TEMP_FILE=/home/pi/temp.jar

if [ -z $PI_ADDRESS ]; then 
  echo "PI_ADDRESS is unset";
  exit;
else 
  echo "Uploading Gui.jar to $OUTPUT_FILE"
fi

scp -o CheckHostIP=no ./out/artifacts/Gui_jar/Gui.jar pi@$PI_ADDRESS:$TEMP_FILE
echo "mv $TEMP_FILE $REMOTE_FILE && sudo reboot" | ssh -T pi@$PI_ADDRESS
