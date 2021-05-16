import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

public class Window extends JFrame implements ActionListener {
    @Serial
    private static final long serialVersionUID = 2452722075055814608L;

    private final Panel left = new Panel();
    private final Panel right = new Panel();

    private final Menu menu = new Menu();

    private String filePath;

    private ContrastWindow contrastWindow;
    private BrightnessWindow brightnessWindow;

    public Window() {
        super("Mykyta Melnyk - Lab 01");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));

        setJMenuBar(menu);

        add(right);
        add(left);

        eventListener();
        adjustToContent();

        setVisible(true);
    }

    public void eventListener() {
        menu.openFile.addActionListener(this);
        menu.saveFile.addActionListener(this);
        menu.exit.addActionListener(this);

        menu.clrLeft.addActionListener(this);
        menu.clrRight.addActionListener(this);

        menu.R.addActionListener(this);
        menu.G.addActionListener(this);
        menu.B.addActionListener(this);

        menu.average.addActionListener(this);
        menu.modYUV.addActionListener(this);

        menu.brightness.addActionListener(this);
        menu.contrast.addActionListener(this);
        menu.negation.addActionListener(this);
        menu.brightnessInterval.addActionListener(this);
    }

    public void openFile() {
        JFileChooser open = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & BMP & PNG Images",
                "jpg", "bmp", "png"
        );

        open.setFileFilter(filter);

        int result = open.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            filePath = open.getSelectedFile().getPath();
            int w = left.canvas.getWidth();
            int h = left.canvas.getHeight();

            left.uploadImage(filePath);
            if (w != left.canvas.getWidth() || h != left.canvas.getHeight()) adjustToContent();
        }
    }

    public void exportFile() {
        JFileChooser export;

        if (filePath != null) export = new JFileChooser(filePath);
        else export = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & BMP & PNG Images",
                "jpg", "bmp", "png"
        );

        export.setFileFilter(filter);

        int result = export.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            filePath = export.getSelectedFile().getPath();
            right.exportImage(filePath);
        }
    }

    public void adjustToContent() {
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String label = actionEvent.getActionCommand();

        switch (label) {
            case "Open" -> openFile();
            case "Save" -> exportFile();
            case "Exit" -> System.exit(0);
            case "Clear left" -> left.clear();
            case "Clear right" -> right.clear();
            case "Average" -> {
                int w = right.canvas.getWidth();
                int h = right.canvas.getHeight();

                right.average(left.canvas);

                if (w != right.canvas.getWidth() || h != right.canvas.getHeight()) adjustToContent();
            }
            case "YUV Model" -> {
                int w = right.canvas.getWidth();
                int h = right.canvas.getHeight();

                right.modYUV(left.canvas);

                if (w != right.canvas.getWidth() || h != right.canvas.getHeight()) adjustToContent();
            }
            case "R" -> {
                int w = right.canvas.getWidth();
                int h = right.canvas.getHeight();

                right.component(left.canvas, "R");

                if (w != right.canvas.getWidth() || h != right.canvas.getHeight()) adjustToContent();
            }
            case "G" -> {
                int w = right.canvas.getWidth();
                int h = right.canvas.getHeight();

                right.component(left.canvas, "G");

                if (w != right.canvas.getWidth() || h != right.canvas.getHeight()) adjustToContent();
            }
            case "B" -> {
                int w = right.canvas.getWidth();
                int h = right.canvas.getHeight();

                right.component(left.canvas, "B");

                if (w != right.canvas.getWidth() || h != right.canvas.getHeight()) adjustToContent();
            }
            case "Negation" -> {
                int w = right.canvas.getWidth();
                int h = right.canvas.getHeight();

                right.negation(left.canvas);

                if (w != right.canvas.getWidth() || h != right.canvas.getHeight()) adjustToContent();
            }
            case "Brightness" -> {
                if (brightnessWindow == null) brightnessWindow = new BrightnessWindow(this);
                brightnessWindow.setVisible(true);
                brightnessWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                if (brightnessWindow.getIsApplied()) {
                    int w = right.canvas.getWidth();
                    int h = right.canvas.getHeight();

                    right.brightness(left.canvas, brightnessWindow.getValue());

                    if (w != right.canvas.getWidth() || h != right.canvas.getHeight()) adjustToContent();
                }
            }
            case "Contrast" -> {
                if (contrastWindow == null) contrastWindow = new ContrastWindow(this);
                contrastWindow.setVisible(true);
                contrastWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                if (contrastWindow.getIsApplied()) {
                    int w = right.canvas.getWidth();
                    int h = right.canvas.getHeight();

                    right.contrast(left.canvas, contrastWindow.getValue());

                    if (w != right.canvas.getWidth() || h != right.canvas.getHeight()) adjustToContent();
                }
            }
            case "Brightness interval" -> {
                int w = right.canvas.getWidth();
                int h = right.canvas.getHeight();

                right.brightnessInterval(left.canvas);

                if (w != right.canvas.getWidth() || h != right.canvas.getHeight()) adjustToContent();
            }
        }
    }
}
