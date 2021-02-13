package ActuallyPong;

import java.awt.Color;

public class RGBcycle {

    public static int rgbR;
    public static int rgbG = 0;
    public static int rgbB = 255;
    public static int phaseRGB = 0;

    public RGBcycle(int R, int G, int B, int phase) {

        RGBcycle.rgbR = R;
        RGBcycle.rgbG = G;
        RGBcycle.rgbB = B;
        RGBcycle.phaseRGB = phase;

    }

    public void cycle() {

        if (RGBcycle.phaseRGB == 1) {
            RGBcycle.rgbR++;
        } else if (RGBcycle.phaseRGB == 2) {
            RGBcycle.rgbB--;
        } else if (RGBcycle.phaseRGB == 3) {
            RGBcycle.rgbG++;
        } else if (RGBcycle.phaseRGB == 4) {
            RGBcycle.rgbR--;
        } else if (RGBcycle.phaseRGB == 5) {
            RGBcycle.rgbB++;
        } else if (RGBcycle.phaseRGB == 6) {
            RGBcycle.rgbG--;
        }

        if (RGBcycle.phaseRGB == 1 && RGBcycle.rgbR == 255) {
            RGBcycle.phaseRGB = 2;
        } else if (RGBcycle.phaseRGB == 2 && RGBcycle.rgbB == 0) {
            RGBcycle.phaseRGB = 3;
        } else if (RGBcycle.phaseRGB == 3 && RGBcycle.rgbG == 255) {
            RGBcycle.phaseRGB = 4;
        } else if (RGBcycle.phaseRGB == 4 && RGBcycle.rgbR == 0) {
            RGBcycle.phaseRGB = 5;
        } else if (RGBcycle.phaseRGB == 5 && RGBcycle.rgbB == 255) {
            RGBcycle.phaseRGB = 6;
        } else if (RGBcycle.phaseRGB == 6 && RGBcycle.rgbG == 0) {
            RGBcycle.phaseRGB = 1;
        }
    }
    
    public Color color () {
        return new Color(rgbR, rgbG, rgbB);
    }

    public int makeColor(int R, int G, int B) {
        return (R << 16) + (G << 8) + (B);
    }

    public int getColorR(int color) {
        return (color >> 16);
    }

    public int getColorG(int color) {
        return (color >> 8) & 255;
    }

    public int getColorB(int color) {
        return color & 255;
    }
}
