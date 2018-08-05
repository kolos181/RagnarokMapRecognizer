package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller {

    static Color pointerCenterColor = new Color(214, 0, 0);
    static Color mapPlusColor = new Color(24, 115, 231);
    static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    static Rectangle rect;


    public static void main(String[] args) throws AWTException, InterruptedException, IOException {
        while (true) {
//            Thread.sleep(1000);
            dispCharCoords(locateMapIcon());
        }
    }

    public static Rectangle locateMapIcon() {
        BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        int width = bufferedImage.getWidth();
        int heigth = bufferedImage.getHeight();
        int image[][] = new int[width][heigth];

        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                image[i][j] = bufferedImage.getRGB(i, j);
            }
        }

        Rectangle rectMap = new Rectangle();

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                //locate plus on the screen
                if ((image[i][j] == mapPlusColor.getRGB()) &&
                        (image[i][j] == image[i + 1][j]) &&
                        (image[i][j] == image[i + 2][j]) &&
                        (image[i][j] == image[i + 3][j]) &&
                        (image[i][j] == image[i][j - 1]) &&
                        (image[i][j] == image[i][j - 2]) &&
                        (image[i][j] == image[i][j - 3])
                ) {
                    rectMap = new Rectangle(i - 120, j - 6, 128, 128);
                }
            }
        }
        return rectMap;
    }

    public static void dispCharCoords(Rectangle rect) throws InterruptedException, IOException {
        while (true) {
            locateMapIcon();
            for (int k = 0; true; k++) {
                Thread.sleep(500);
                BufferedImage bufferedImage = robot.createScreenCapture(rect);
                int width = bufferedImage.getWidth();
                int heigth = bufferedImage.getHeight();
                int image[][] = new int[width][heigth];


                for (int i = 0; i < bufferedImage.getWidth(); i++) {
                    for (int j = 0; j < bufferedImage.getHeight(); j++) {
                        image[i][j] = bufferedImage.getRGB(i, j);
                    }
                }
                for (int i = 0; i < image.length; i++) {
                    for (int j = 0; j < image[i].length; j++) {
                        if (image[i][j] == pointerCenterColor.getRGB() && image[i][j - 2] == -1 || image[i][j] == pointerCenterColor.getRGB() && image[i][j + 2] == -1) {
                            System.out.println(i + " " + j);
                            paintXYDotsOnMap(i, j);
                        }
                    }
                }
            }
        }
    }

    public static void paintXYDotsOnMap(int x, int y) throws IOException {
        BufferedImage image = ImageIO.read(new File("C:\\Users\\Yevhenii Kolosenko\\Desktop\\RagnarokRobotFiles\\img\\imgOutput\\VocalMap.png"));
        image.createGraphics().fill(new Ellipse2D.Float(x - 3, y - 4, 8, 8));
        image.setRGB(x + 1, y, new Color(255, 105, 180).getRGB());
        File f = new File("C:\\Users\\Yevhenii Kolosenko\\Desktop\\RagnarokRobotFiles\\img\\imgOutput\\VocalMap.png");
        ImageIO.write(image, "png", f);
    }
}
