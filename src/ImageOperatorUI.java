import ImageOperators.BilinearInterpolationOperator;
import ImageOperators.ImageOperator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class ImageOperatorUI {
    private JButton generateBtn;
    private JPanel panelMain;
    private JTextField targetDirectoryTextField;
    private JLabel imageSource;
    private JSlider horizontalRatioSlider;
    private JSlider verticalRatioSlider;

    private Float wPortion = 1f, hPortion = 1f;

    private static final String generateText  = "Generate Fit in and Fill in Images";

    private ImageOperatorUI() {

        imageSource.setSize(512, 512);

        generateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateImage();
            }
        });

        generateText();

        horizontalRatioSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                if (!slider.getValueIsAdjusting()) {
                    hPortion = (100f + slider.getValue())/100f;
                    generateText();
                }
            }
        });

        verticalRatioSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                if (!slider.getValueIsAdjusting()) {
                    wPortion = (100f + slider.getValue())/100f;
                    generateText();
                }
            }
        });
    }

    private void generateText() {
        generateBtn.setText(String.format(generateText + " by scaling " + "%.2f x %.2f", hPortion, wPortion ));
    }

    private void generateImage() {
        String fileDestination = targetDirectoryTextField.getText();
        if (fileDestination == null || fileDestination.isEmpty()) {
            JOptionPane.showMessageDialog(panelMain, "Please enter a valid file destination");
            return;
        }

        File destination = new File(fileDestination);

        if (destination.exists() && destination.isDirectory()) {
            URL imageFile = this.getClass().getResource("res/Lenna100.jpg");
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(imageFile);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(panelMain, e.getLocalizedMessage());
                return;
            }
            if (bufferedImage != null) {
                BilinearInterpolationOperator biOp = new BilinearInterpolationOperator();
                ImageOperator<BilinearInterpolationOperator> op = new ImageOperator<>(bufferedImage, biOp);
                BufferedImage filled = op.scaleToFill(wPortion, hPortion);
                BufferedImage fit = op.scaleToFit(wPortion, hPortion);

                // Write the the directory of interest
                String filledFileName = String.format("filled_%.1f_by_%.1f", wPortion, hPortion);
                String fittedFileName = String.format("fitted_%.1f_by_%.1f", wPortion, hPortion);
                File filledFile = new File(String.format(fileDestination + "%s.jpg", filledFileName));
                File fittedFile = new File(String.format(fileDestination + "%s.jpg", fittedFileName));
                try {
                    ImageIO.write(filled, "jpg", filledFile);
                    ImageIO.write(fit, "jpg", fittedFile);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(panelMain, e.getLocalizedMessage());
                }
            } else {
                JOptionPane.showMessageDialog(panelMain, "Image cannot be read");
            }
        } else {
            JOptionPane.showMessageDialog(panelMain, "No such folder found");
        }
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("ImageOperatorUI");
        jFrame.setContentPane(new ImageOperatorUI().panelMain);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private void createUIComponents() {
        imageSource = new JLabel(new ImageIcon(this.getClass().getResource("res/Lenna100.jpg")));
    }
}
