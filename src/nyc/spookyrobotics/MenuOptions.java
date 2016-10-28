package nyc.spookyrobotics;

import java.io.IOException;

public enum MenuOptions {
    SHUTDOWN("Shutdown", () -> {
        exec("sudo shutdown now");
        System.exit(0);
    }),
    KILL_X("Kill X", () -> {
        exec("sudo pkill x");
    }),

    REBOOT("Reboot", () -> {
        exec("sudo shutdown -r now");
        System.exit(0);
    });


    private final String mLabel;
    private final Runnable mOnSelected;

    MenuOptions(String label, Runnable onSelected){
        mLabel = label;
        mOnSelected = onSelected;
    }

    public String label() {
        return mLabel;
    }

    public Runnable onSelected() {
        return mOnSelected;
    }

    private static void exec(String command){
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
