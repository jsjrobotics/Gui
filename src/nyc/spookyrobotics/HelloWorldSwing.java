package nyc.spookyrobotics;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStreamReader;

public class HelloWorldSwing {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {

        JFrame frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setPreferredSize(new Dimension(480, 320));
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(buildButton(MenuOptions.SHUTDOWN));
        topPanel.add(buildButton(MenuOptions.REBOOT));
        topPanel.add(buildButton(MenuOptions.KILL_X));

        JPanel middlepanel = new JPanel();
        middlepanel.add(new JLabel("Welcome to the SpookyBot"));
        middlepanel.add(buildShowIpButton(middlepanel));

        JPanel bottomPanel = new JPanel();

        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(new JButton("Launch Website"));
        bottomPanel.add(new JButton("Check Temperature"));

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(middlepanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private static Component buildShowIpButton(final Component parent) {
        JButton button = new JButton("Show Ip dialog");
        button.addActionListener(ignored -> {
            try {
                StringBuilder result = new StringBuilder("Wlan0:\t");
                result.append(runAndGetOutput(ShellCommands.GET_WLAN0_ADDRESS));
                result.append("\nEth0:\t");
                result.append(runAndGetOutput(ShellCommands.GET_ETH0_ADDRESS));
                JOptionPane.showMessageDialog(parent, result.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return button;
    }


    private static String runAndGetOutput(String command) throws IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(command);
        int result = p.waitFor();
        InputStreamReader output = new InputStreamReader(p.getInputStream());
        StringBuilder terminalOut = new StringBuilder();
        char[] buffer = new char[1024];
        int bytesRead = output.read(buffer);
        while (bytesRead != -1){
            terminalOut.append(buffer, 0, bytesRead);
            bytesRead = output.read(buffer);
        }
        return terminalOut.toString();
    }
    private static Component buildButton(MenuOptions option) {
        JButton button = new JButton(option.label());
        button.addActionListener(e -> option.onSelected().run());
        return button;
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
