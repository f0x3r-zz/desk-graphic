import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

public class ContrastWindow extends JDialog implements ActionListener {
    @Serial
    private static final long serialVersionUID = 8024113866784914664L;

    private final JSpinner spinner;
    private final JButton apply;
    private final JButton decline;

    private boolean isApplied;

    public ContrastWindow(JFrame frame) {
        super(frame, "Contrast Setting", true);

        setSize(200, 180);
        setLayout(null);

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

        int width = getSize().width;
        int height = getSize().height;

        int lWidth = (size.width - width) / 2;
        int lHeight = (size.height - height) / 2;

        setLocation(lWidth, lHeight);

        JLabel label = new JLabel("Contrast", JLabel.CENTER);
        label.setBounds(10, 10, 160, 30);
        add(label);

        SpinnerModel model = new SpinnerNumberModel(0, 0, 5, 0.01);
        spinner = new JSpinner(model);
        spinner.setLocation(45, 50);
        spinner.setSize(100, 25);
        add(spinner);

        apply = new JButton("Apply");
        apply.setBounds(10, 85, 75, 30);
        add(apply);
        apply.addActionListener(this);

        decline = new JButton("Decline");
        decline.setBounds(95, 85, 75, 30);
        add(decline);
        decline.addActionListener(this);
    }

    public double getValue() {
        return (double) spinner.getValue();
    }

    public boolean getIsApplied() {
        return isApplied;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source =  actionEvent.getSource();

        if (source == decline) {
            isApplied = false;
            dispose();
        } else if (source == apply) {
            isApplied = true;
            dispose();
        }
    }
}
