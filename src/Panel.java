import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;

public class Panel extends JPanel {
    @Serial
    private static final long serialVersionUID = -7726921692333838831L;

    protected BufferedImage canvas;

    private int mnRed = 255;
    private int mnGreen = 255;
    private int mnBlue = 255;

    private int mxRed = 0;
    private int mxGreen = 0;
    private int mxBlue = 0;

    public Panel() {
        super();

        setLayout(new GridLayout(1, 1));
        setSize(new Dimension(400, 400));
        clear();
    }

    /**
     * Set size of canvas
     */
    @Override
    public void setSize(Dimension d) {
        canvas = new BufferedImage(
                (int) d.getWidth(),
                (int) d.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );
        setPreferredSize(d);
        setMaximumSize(d);
    }

    /**
     * Upload image, set up canvas and re-render
     */
    public void uploadImage(String filePath) {
        File file = new File(filePath);

        try {
            canvas = ImageIO.read(file);
            Dimension size = new Dimension(canvas.getWidth(), canvas.getHeight());

            setPreferredSize(size);
            setMaximumSize(size);
            setBorder(BorderFactory.createLineBorder(Color.black));

            repaint();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Couldn't read file: " + filePath);
        }
    }

    /**
     * Export image to path
     */
    public void exportImage(String filePath) {
        try {
            if (canvas != null) {
                if (!ImageIO.write(canvas, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath))) {
                    JOptionPane.showMessageDialog(null, "Couldn't export image in: " + filePath);
                }
            } else {
                JOptionPane.showMessageDialog(null, "There isn't image to export.");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Couldn't export image in: " + filePath);
        }
    }

    /**
     * Change one of the color
     */
    public void component(BufferedImage image, String color) {
        setSize(new Dimension(image.getWidth(), image.getHeight()));

        Color gColor;

        if (color.equals("R")) {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    gColor = new Color(image.getRGB(i, j));
                    int gray = gColor.getRed();
                    canvas.setRGB(i, j, new Color(gray, gray, gray).getRGB());
                }
            }
        } else if (color.equals("G")) {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    gColor = new Color(image.getRGB(i, j));
                    int gray = gColor.getGreen();
                    canvas.setRGB(i, j, new Color(gray, gray, gray).getRGB());
                }
            }
        } else {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    gColor = new Color(image.getRGB(i, j));
                    int gray = gColor.getBlue();
                    canvas.setRGB(i, j, new Color(gray, gray, gray).getRGB());
                }
            }
        }

        repaint();
    }

    public void average(BufferedImage image) {
        setSize(new Dimension(image.getWidth(), image.getHeight()));

        Color gColor;
        int red, green, blue;

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                gColor = new Color(image.getRGB(i, j));
                red = gColor.getRed();
                green = gColor.getGreen();
                blue = gColor.getBlue();
                int gray = (red + green + blue) / 3;
                canvas.setRGB(i, j, new Color(gray, gray, gray).getRGB());
            }
        }

        repaint();
    }

    /**
     * YUV Model Function
     */
    public void modYUV(BufferedImage image) {
        setSize(new Dimension(image.getWidth(), image.getHeight()));

        Color gColor;
        int red, green, blue;

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                gColor = new Color(image.getRGB(i, j));
                red = gColor.getRed();
                green = gColor.getGreen();
                blue = gColor.getBlue();
                int gray = (int) ((0.299 * red) + (0.587 * green) + (0.114 * blue));
                canvas.setRGB(i, j, new Color(gray, gray, gray).getRGB());
            }
        }

        repaint();
    }

    /**
     * Negation Function
     */
    public void negation(BufferedImage image) {
        setSize(image.getWidth(), image.getHeight());

        Color gColor;
        int red, green, blue;

        mxMn(image);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                gColor = new Color(image.getRGB(i, j));
                red = mxRed - gColor.getRed();
                green = mxGreen - gColor.getGreen();
                blue = mxBlue - gColor.getBlue();

                canvas.setRGB(i, j, new Color(red, green, blue).getRGB());
            }
        }

        repaint();
    }

    /**
     * Brightness Function
     */
    public void brightness(BufferedImage image, int k) {
        setSize(new Dimension(image.getWidth(), image.getHeight()));

        Color gColor;
        int red, green, blue;

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                gColor = new Color(image.getRGB(i, j));
                red = gColor.getRed();
                green = gColor.getGreen();
                blue = gColor.getBlue();

                red += k;
                green += k;
                blue += k;

                setRGB(red, green, blue, i, j);
            }
        }

        repaint();
    }

    public void contrast(BufferedImage image, double k) {
        setSize(new Dimension(image.getWidth(), image.getHeight()));

        Color gColor;
        int red, green, blue;

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                gColor = new Color(image.getRGB(i, j));

                red = gColor.getRed();
                green = gColor.getGreen();
                blue = gColor.getBlue();

                red *= k;
                green *= k;
                blue *= k;

                setRGB(red, green, blue, i, j);
            }
        }

        repaint();
    }

    private void setRGB(int red, int green, int blue, int i, int j) {
        if (red > 255) red = 255;
        else if (red < 0) red = 0;

        if (green > 255) green = 255;
        else if (green < 0) green = 0;

        if (blue > 255) red = 255;
        else if (blue < 0) blue = 0;

        canvas.setRGB(i, j, new Color(red, green, blue).getRGB());
    }

    public void brightnessInterval(BufferedImage image) {
        setSize(new Dimension(image.getWidth(), image.getHeight()));

        Color gColor;
        int red, green, blue;

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                gColor = new Color(image.getRGB(i, j));

                red = gColor.getRed();
                green = gColor.getGreen();
                blue = gColor.getBlue();

                red = ((red - mnRed) * 255) / (mxRed - mnRed);
                green = ((green - mnGreen) * 255) / (mxGreen - mnGreen);
                blue = ((blue - mnBlue) * 255) / (mxBlue - mnBlue);

                setRGB(red, green, blue, i, j);
            }
        }

        repaint();
    }

    /**
     * Reset Max/Min RGB
     */
    public void resetMxMn() {
        mnRed = 255;
        mnGreen = 255;
        mnBlue = 255;
        mxRed = 0;
        mxGreen = 0;
        mxBlue = 0;
    }

    /**
     * Set min and max RGB
     */
    public void mxMn(BufferedImage image) {
        resetMxMn();

        Color gColor;
        int red, green, blue;

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                gColor = new Color(image.getRGB(i, j));
                red = gColor.getRed();
                green = gColor.getGreen();
                blue = gColor.getBlue();

                if (red > mxRed) mxRed = red;
                if (green > mxGreen) mxGreen = green;
                if (blue > mxBlue) mxBlue = blue;

                if (red < mnRed) mnRed = red;
                if (green < mnGreen) mnGreen = green;
                if (blue < mnBlue) mnBlue = blue;
            }
        }

        System.out.println("Min red: " + mnGreen);
        System.out.println("Max red: " + mxRed);
        System.out.println("Min green: " + mnGreen);
        System.out.println("Max green: " + mxGreen);
        System.out.println("Min blue: " + mnBlue);
        System.out.println("Max blue: " + mxBlue);
    }

    /**
     * Clear canvas
     */
    public void clear() {
        Graphics2D g2d = (Graphics2D) canvas.getGraphics();
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(canvas, 0, 0, this);
    }
}
