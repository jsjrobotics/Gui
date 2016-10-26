package nyc.c4q;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class TextStyle {

    private final GraphicsEnvironment mEnvironment;

    public TextStyle(){
        mEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    }
    public Stream<Font> getAllFonts(){
        return Arrays.stream( mEnvironment.getAllFonts() );
    }

    public Stream<String> getAvailableFontFamilies(){
        return Arrays.stream( mEnvironment.getAvailableFontFamilyNames());
    }
}
