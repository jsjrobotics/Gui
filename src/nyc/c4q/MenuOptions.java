package nyc.c4q;

import java.io.IOException;

public enum MenuOptions {
    SHUTDOWN("Shutdown", () -> {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("sudo shutdown now");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }),
    REBOOT("Reboot", () -> {
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("sudo shutdown -r now");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
