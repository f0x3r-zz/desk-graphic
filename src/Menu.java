import javax.swing.*;
import java.io.Serial;

public class Menu extends JMenuBar {
    @Serial
    private static final long serialVersionUID = -3429010304896039963L;

    JMenu file = new JMenu("File");
    JMenuItem openFile = new JMenuItem("Open");
    JMenuItem saveFile = new JMenuItem("Save");
    JMenuItem exit = new JMenuItem("Exit");

    JMenu panel = new JMenu("Panel");
    JMenuItem clrLeft = new JMenuItem("Clear left");
    JMenuItem clrRight = new JMenuItem("Clear right");

    JMenu grayRGB = new JMenu("W&B");
    JMenuItem average = new JMenuItem("Average");
    JMenuItem modYUV = new JMenuItem("YUV Model");

    JMenu oneOfComponents = new JMenu("RGB");
    JMenuItem R = new JMenuItem("R");
    JMenuItem G = new JMenuItem("G");
    JMenuItem B = new JMenuItem("B");

    JMenu effects = new JMenu("Effects");
    JMenuItem brightness = new JMenuItem("Brightness");
    JMenuItem contrast = new JMenuItem("Contrast");
    JMenuItem brightnessInterval = new JMenuItem("Brightness interval");
    JMenuItem negation = new JMenuItem("Negation");

    public Menu() {
        // File
        file.add(openFile);
        file.add(saveFile);
        // Separator
        file.add(new JSeparator());
        // << File >> Exit
        file.add(exit);
        add(file);

        // Panel
        panel.add(clrLeft);
        panel.add(clrRight);
        add(panel);

        // RGB
        oneOfComponents.add(R);
        oneOfComponents.add(G);
        oneOfComponents.add(B);

        // Gray
        grayRGB.add(oneOfComponents);
        grayRGB.add(average);
        grayRGB.add(modYUV);
        add(grayRGB);

        // Effects
        effects.add(brightness);
        effects.add(contrast);
        effects.add(brightnessInterval);
        effects.add(negation);
        add(effects);
    }
}
