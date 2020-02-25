import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class imageFile {
    public BufferedImage imgImage;
    public File imgFile;
    public File lastEditFile;
    public String filepath;
    public String savePath;
    public static int imgCount = 0;
    private String imgNum;

    public imageFile(String path) {
        filepath = path;
        imgNum = genImgIdentity();
        imgCount++;
        imgFile = new File(path);
        try {
            imgImage = ImageIO.read(imgFile);
        } catch(IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public imageFile(File enterFile, String savepath) {
        imgFile = enterFile;
        savePath = savepath;
        imgNum = genImgIdentity();
        imgCount++;
        try{
            imgImage = ImageIO.read(imgFile);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public imageFile(File enterFile) {
        imgFile = enterFile;
        savePath = System.getProperty("user.home");
        imgNum = genImgIdentity();
        imgCount++;
        try{
            imgImage = ImageIO.read(imgFile);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public imageFile(String path, String savepath) {
        filepath = path;
        savePath = savepath;
        imgNum = genImgIdentity();
        imgCount++;
        imgFile = new File(path);
        try {
            imgImage = ImageIO.read(imgFile);
        } catch(IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public String genImgIdentity() {
        String randChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890-_";
        String randStr = "";
        for(int i = 0; i < 16; i++) {
            randStr += randChar.charAt((int) (Math.random() * 1000) % 64);
        }
        return randStr;
    }

    public File getImgFile(){
        return imgFile;
    }

    public void setSavePath(String filepath){
        savePath = filepath;
    }

    public String imgSavePath() {
        return savePath;
    }

    public int imgHeight(){
        return imgImage.getHeight();
    }

    public int imgWidth(){
        return imgImage.getWidth();
    }

    public String imgPath(){
        return filepath;
    }

    public BufferedImage imgBuffered(){
        return null;
    }

    public int imgRGB(int x, int y){
        return imgImage.getRGB(x,y);
    }

    public File imgLastEdit() { return lastEditFile; }

    public void imgCrop(int xPosOne, int yPosOne, int xPosTwo, int yPosTwo){
        File tempDir = new File(savePath + File.separator + "temp");
        String tempPath = savePath + File.separator + "temp";
        if(!tempDir.exists()){
            tempDir.mkdir();
        }
        if(xPosOne > xPosTwo) {
            int temp = xPosOne;
            xPosTwo = xPosOne;
            xPosOne = temp;
        }
        if(yPosOne > yPosTwo) {
            int temp = yPosOne;
            yPosTwo = yPosOne;
            yPosOne = temp;
        }
        BufferedImage cropImage = new BufferedImage((xPosTwo-xPosOne), (yPosTwo-yPosOne), BufferedImage.TYPE_INT_ARGB);
        for(int y = yPosOne, yt = 0; y < yPosTwo; y++, yt++) {
            for(int x = xPosOne, xt = 0; x < xPosTwo; x++, xt++) {
                int curColour = imgImage.getRGB(x, y);
                cropImage.setRGB(xt, yt, curColour);
            }
        }
        imgSave(cropImage, tempPath, "crop" + imgNum, "png");
    }

    public void imgMirrorH(){
        File tempDir = new File(savePath + File.separator + "OutputJ");
        String tempPath = savePath + File.separator + "OutputJ";
        if(!tempDir.exists()){
            tempDir.mkdir();
        }
        BufferedImage mirrorImage = new BufferedImage(imgImage.getWidth(),imgImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int y = 0; y < imgImage.getHeight(); y++) {
            for(int x = 0, x2 = imgImage.getWidth() - 1; x < imgImage.getWidth(); x++, x2--) {
                int curColour = imgImage.getRGB(x,y);
                mirrorImage.setRGB(x2,y,curColour);
            }
        }
        imgSave(mirrorImage, tempPath, "mirror" + imgNum, "png");
    }

    public void imgMirrorV(){
        File tempDir = new File(savePath + File.separator + "OutputJ");
        String tempPath = savePath + File.separator + "OutputJ";
        if(!tempDir.exists()){
            tempDir.mkdir();
        }
        BufferedImage mirrorImage = new BufferedImage(imgImage.getWidth(),imgImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for(int y = 0, y2 = imgImage.getHeight() - 1; y < imgImage.getHeight(); y++, y2--) {
            for(int x = 0; x < imgImage.getWidth(); x++) {
                int curColour = imgImage.getRGB(x,y);
                mirrorImage.setRGB(x, y2,curColour);
            }
        }
        imgSave(mirrorImage, tempPath, "mirror" + imgNum, "png");
    }

    public BufferedImage imgPaste(BufferedImage orig, BufferedImage paste, int x1, int y1) {
        for(int y = 0, ynew = y1; y < paste.getHeight(); y++, ynew++) {
            for(int x = 0, xnew = x1; x < paste.getWidth();x++, xnew++ ) {
                int curColour = paste.getRGB(x,y);
                if(xnew < orig.getWidth() && ynew < orig.getHeight()) {
                    orig.setRGB(xnew, ynew, curColour);
                }
            }
        }
        return orig;
    }

    public void imgPaste(BufferedImage paste, int x1, int y1) {
        File tempDir = new File(savePath + File.separator + "OutputJ");
        String tempPath = savePath + File.separator + "OutputJ";
        if(!tempDir.exists()){
            tempDir.mkdir();
        }
        BufferedImage returnImg = imgPaste(imgImage, paste, x1, y1);
        imgSave(returnImg, tempPath, "paste" + imgNum, "png");
    }

    public void imgSave(BufferedImage saveImage, String savepath, String fileName, String fileType){
        try {
            File save = new File(savepath + File.separator + fileName + "." + fileType);
            ImageIO.write(saveImage, fileType, save);
            lastEditFile = save;
        } catch(IOException e){
            System.out.println("Error: " + e);
        }
    }
}