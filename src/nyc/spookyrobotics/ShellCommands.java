package nyc.spookyrobotics;

/**
 * Created by spooky on 10/28/16.
 */
public class ShellCommands {
    public static final String GET_WLAN0_ADDRESS =  "sudo ifconfig wlan0 | grep 'inet addr'";
    public static final String GET_ETH0_ADDRESS =  "sudo ifconfig eth0 | grep 'inet addr'";

}
