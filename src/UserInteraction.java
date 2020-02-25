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
    private JButton crop, cropFile, flipV, flipH, overlap, stitch, split;
    public UserInteraction() throws IOException {
        JFrame a = new JFrame();
        a.setVisible(true);
        a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        a.getContentPane().setSize(700,500);
        this.crop = new JButton("Crop (Folder)");
        this.stitch = new JButton("Stitch");
        this.split = new JButton("Split");
        this.cropFile = new JButton("Crop (File)");
        this.flipH = new JButton("Reflect Horizontally");
        this.flipV = new JButton("Reflect Vertically");
        a.add(crop);
        a.add(stitch);
        a.add(split);
        a.add(cropFile);
        a.add(flipH);
        a.add(flipV);
        crop.setLocation(10,10);
        stitch.setLocation(20,20);
        split.setLocation(30,20);
        cropFile.setLocation(40,20);
        flipH.setLocation(50,20);
        flipV.setLocation(60,20);
        a.setLayout(new GridLayout(2,3));
        a.setSize(1000,500);
        crop.addActionListener(this::actionPerformed);
        stitch.addActionListener(this::actionPerformed);
        split.addActionListener(this::actionPerformed);
        cropFile.addActionListener(this::actionPerformed);
        flipH.addActionListener(this::actionPerformed);
        flipV.addActionListener(this::actionPerformed);
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
        if(e.getSource() == split) {
            try {
                String numSplit = JOptionPane.showInputDialog("Enter number of images to split into");
                int splitNum = Integer.parseInt(numSplit);
                Main.split(ImageIO.read(Main.chooseFile()),splitNum);
            } catch (Exception error) {
                System.out.println(error);

            }
        }
        if(e.getSource() == cropFile) {
            try{
                BufferedImage buff = ImageIO.read(Main.chooseFile());
                String coords = JOptionPane.showInputDialog("Enter the first X coordinate, then the first Y coordinate, and then the second X coordinate, followed by the second Y coordinate, all separated by a comma (3,5,543,958 etc.)");
                String[] splitCoords = coords.split(",");

                DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
                LocalDateTime currentDate = LocalDateTime.now();
                String curDate = currentDate.format(date) + "-";
                Main.imgCrop(Integer.parseInt(splitCoords[0]), Integer.parseInt(splitCoords[1]), Integer.parseInt(splitCoords[2]), Integer.parseInt(splitCoords[3]), buff, curDate + "crop");

            } catch (Exception error) {
                JOptionPane.showMessageDialog(null, "An error occurred, please try again.");
            }
        }

        if(e.getSource() == flipH) {
            try {
                Main.imgMirrorH(ImageIO.read(Main.chooseFile()));
            } catch (Exception error) {
                System.out.println(error);
            }
        }
        if(e.getSource() == flipV) {
            try {
                Main.imgMirrorV(ImageIO.read(Main.chooseFile()));
            } catch (Exception error) {
                System.out.println(error);
            }
        }
    }
}
