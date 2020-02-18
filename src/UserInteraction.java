import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserInteraction {
    private JButton crop, flipV, flipH, overlap, stitch;

    public UserInteraction() throws IOException {
        JFrame a = new JFrame();
        a.setVisible(true);
        a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        a.getContentPane().setSize(700,500);
        this.crop = new JButton("Crop");
        this.stitch = new JButton("Stitch");
        a.add(crop);
        a.add(stitch);
        crop.setLocation(10,10);
        stitch.setLocation(20,20);
        a.setLayout(new GridLayout(1,2));
        a.setSize(1000,500);
        crop.addActionListener(this::actionPerformed);
        stitch.addActionListener(this::actionPerformed);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == crop) {
            File[] cropList = Main.fileArray(Main.chooseDirectory());
            String coords = JOptionPane.showInputDialog("Enter the first X coordinate, then the first Y coordinate, and then the second X coordinate, followed by the second Y coordinate, all separated by a comma (3,5,543,958 etc.)");
            String[] splitCoords = coords.split(",");

            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            LocalDateTime currentDate = LocalDateTime.now();
            String curDate = currentDate.format(date) + "-";

            try {
                for (int i = 0; i < cropList.length; i++) {
                    Main.imgCrop(Integer.parseInt(splitCoords[0]), Integer.parseInt(splitCoords[1]), Integer.parseInt(splitCoords[2]), Integer.parseInt(splitCoords[3]), ImageIO.read(cropList[i]), curDate + String.valueOf(i));
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(null,"An error occurred, please try again.");
            }
        }
        if(e.getSource() == stitch) {
            try {
                Main.stitchFolder();
            } catch (Exception error) {
                //JOptionPane.showMessageDialog(null,"An error occurred, please make sure that all the files in the folder selected are image files of the same width");
                System.out.println(error);
            }
        }

    }

}
