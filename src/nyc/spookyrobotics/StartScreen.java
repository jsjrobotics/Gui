package nyc.spookyrobotics;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class StartScreen {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setPreferredSize(new Dimension(480, 320));
        frame.setLayout(new BorderLayout());

        JPanel topPanel = buildTopPanel();
        JPanel middlepanel = buildMiddlePanel();
        JPanel bottomPanel = buildBottomPanel();

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlepanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private static JPanel buildBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(new JButton("Launch Website"));
        bottomPanel.add(new JButton("Check Temperature"));
        return bottomPanel;
    }

    private static JPanel buildMiddlePanel() {
        JPanel middlepanel = new JPanel();
        middlepanel.setLayout(new BoxLayout(middlepanel, BoxLayout.Y_AXIS));
        middlepanel.add(new JLabel("Welcome to the SpookyBot"));
        middlepanel.add(buildShowIpButton(middlepanel));
        return middlepanel;
    }

    private static JPanel buildTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(buildButton(MenuOptions.SHUTDOWN));
        topPanel.add(buildButton(MenuOptions.REBOOT));
        topPanel.add(buildButton(MenuOptions.KILL_X));
        return topPanel;
    }

    private static Component buildShowIpButton(final Component parent) {
        JButton button = new JButton("Show Ip dialog");
        button.addActionListener(ignored -> {
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                StringBuilder builder = new StringBuilder();
                while (interfaces.hasMoreElements()){
                    NetworkInterface currentInterface = interfaces.nextElement();
                    if( !(currentInterface.getDisplayName().equals("eth0") ||
                        currentInterface.getDisplayName().equals("wlan0"))) {
                        continue;
                    }
                    builder.append("\n");
                    builder.append(currentInterface.getDisplayName());
                    builder.append(":");
                    Enumeration<InetAddress> addresses = currentInterface.getInetAddresses();
                    while (addresses.hasMoreElements()){
                        InetAddress address = addresses.nextElement();
                        if (address.toString().isEmpty()){
                            continue;
                        }

                        String value = address.toString().replaceAll("[^0-9.]", "");
                        value = value.replaceAll("\\d{4}", "");
                        builder.append(value);
                        builder.append("#");
                    }
                }
                JOptionPane.showMessageDialog(parent, builder.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        return button;
    }


    private static String runAndGetOutput(String command) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(command);
        int result = p.waitFor();
        BufferedReader output = new BufferedReader( new InputStreamReader(p.getInputStream()));
        StringBuilder terminalOut = new StringBuilder();
        String in;
        while ( (in = output.readLine()) != null){
            terminalOut.append(in);
        }
        return terminalOut.toString();
    }
    private static Component buildButton(MenuOptions option) {
        JButton button = new JButton(option.label());
        button.addActionListener(e -> option.onSelected().run());
        return button;
    }

    public static void main() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
