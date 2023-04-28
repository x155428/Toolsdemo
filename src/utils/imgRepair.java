package src.utils;
//图片修复

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//放大图片，二值化，降噪
public class imgRepair {

    public static int YZ = 120;//rgb阈值115

    //放大图片
    public static BufferedImage changeBig(BufferedImage input) throws IOException {
        BufferedImage inputImage=input;

        // 计算新的宽度和高度
        int newWidth = inputImage.getWidth() * 5;
        int newHeight = inputImage.getHeight() * 5;

        // 创建缓存图片
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());

        // 绘制缩放后的图片到缓存图片中
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return outputImage;

        // 保存缩放后的图片
        //File outputFile = new File(output);
        //ImageIO.write(outputImage, "jpg", outputFile);
    }
/*
    //锐化图片
    public static BufferedImage sharpen(BufferedImage image) {
        // define sharpening kernel
        int width=image.getWidth();
        int height=image.getHeight();

        // 创建卷积核
        int[][] kernelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] kernelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        // 创建输出图片
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 对每个像素进行卷积运算
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                // 计算卷积值
                int valueX = 0;
                int valueY = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int pixel = image.getRGB(x + i, y + j);
                        int gray = (int) (0.299 * ((pixel >> 16) & 0xff) + 0.587 * ((pixel >> 8) & 0xff) + 0.114 * (pixel & 0xff));
                        valueX += gray * kernelX[i + 1][j + 1];
                        valueY += gray * kernelY[i + 1][j + 1];
                    }
                }
                // 计算卷积结果
                int value = (int) Math.sqrt(valueX * valueX + valueY * valueY);
                // 对结果进行截断
                value = Math.min(255, Math.max(0, value));
                // 将结果存入输出图片
                output.setRGB(x, y, (value << 16) | (value << 8) | value);
            }
        }
        return output;

    }

 */

    //图片二值化
    public static BufferedImage binarization(BufferedImage inputimg) throws IOException {
        BufferedImage bi =inputimg;
        // 获取当前图片的高,宽,ARGB
        int h = bi.getHeight();
        int w = bi.getWidth();
        int arr[][] = new int[w][h];

        // 获取图片每一像素点的灰度值
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // getRGB()返回默认的RGB颜色模型(十进制)
                arr[i][j] = getImageGray(bi.getRGB(i, j));// 该点的灰度值
            }
        }

        // 构造一个类型为预定义图像类型,BufferedImage
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);

        // 和预先设置的阈值大小进行比较，大的就显示为255即白色，小的就显示为0即黑色
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (getGray(arr, i, j, w, h) > YZ) {
                    int white = new Color(255, 255, 255).getRGB();
                    bufferedImage.setRGB(i, j, white);
                } else {
                    int black = new Color(0, 0, 0).getRGB();
                    bufferedImage.setRGB(i, j, black);
                }
            }

        }
        return bufferedImage;
    }

    private static int getImageGray(int rgb) {
        String argb = Integer.toHexString(rgb);// 将十进制的颜色值转为十六进制
        // argb分别代表透明,红,绿,蓝 分别占16进制2位
        int r = Integer.parseInt(argb.substring(2, 4), 16);// 后面参数为使用进制
        int g = Integer.parseInt(argb.substring(4, 6), 16);
        int b = Integer.parseInt(argb.substring(6, 8), 16);
        int gray = (int) (r*0.3 + g*0.59 + b*0.11);
        return gray;
    }

    /**
     * 自己加周围8个灰度值再除以9，算出其相对灰度值
     *gray
     x 要计算灰度的点的横坐标
     y 要计算灰度的点的纵坐标
     w 图像的宽度
     h 图像的高度
     */
    public static int getGray(int gray[][], int x, int y, int w, int h) {
        int rs = gray[x][y] + (x == 0 ? 255 : gray[x - 1][y]) + (x == 0 || y == 0 ? 255 : gray[x - 1][y - 1])
                + (x == 0 || y == h - 1 ? 255 : gray[x - 1][y + 1]) + (y == 0 ? 255 : gray[x][y - 1])
                + (y == h - 1 ? 255 : gray[x][y + 1]) + (x == w - 1 ? 255 : gray[x + 1][y])
                + (x == w - 1 || y == 0 ? 255 : gray[x + 1][y - 1])
                + (x == w - 1 || y == h - 1 ? 255 : gray[x + 1][y + 1]);
        return rs / 9;
    }

    /*
      二值化后的图像的开运算：先腐蚀再膨胀（用于去除图像的小黑点）
     */
    public static BufferedImage opening(BufferedImage inputimg) throws IOException {
        BufferedImage bi =inputimg;
        // 获取当前图片的高,宽,ARGB
        int h = bi.getHeight();
        int w = bi.getWidth();
        int arr[][] = new int[w][h];
        // 获取图片每一像素点的灰度值
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // getRGB()返回默认的RGB颜色模型(十进制)
                arr[i][j] = getImageGray(bi.getRGB(i, j));// 该点的灰度值
            }
        }

        int black = new Color(0, 0, 0).getRGB();
        int white = new Color(255, 255, 255).getRGB();
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        // 临时存储腐蚀后的各个点的亮度
        int temp[][] = new int[w][h];
        // 1.先进行腐蚀操作
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                /*
                 * 为0表示改点和周围8个点都是黑，则该点腐蚀操作后为黑
                 * 由于公司图片态模糊，完全达到9个点全为黑的点太少，最后效果很差，故改为了小于30
                 * （写30的原因是，当只有一个点为白，即总共255，调用getGray方法后得到255/9 = 28）
                 */
                if (getGray(arr, i, j, w, h) < 30) {
                    temp[i][j] = 0;
                } else{
                    temp[i][j] = 255;
                }
            }
        }

        // 2.再进行膨胀操作
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                bufferedImage.setRGB(i, j, white);
            }
        }
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // 为0表示改点和周围8个点都是黑，则该点腐蚀操作后为黑
                if (temp[i][j] == 0) {
                    bufferedImage.setRGB(i, j, black);
                    if(i > 0) {
                        bufferedImage.setRGB(i-1, j, black);
                    }
                    if (j > 0) {
                        bufferedImage.setRGB(i, j-1, black);
                    }
                    if (i > 0 && j > 0) {
                        bufferedImage.setRGB(i-1, j-1, black);
                    }
                    if (j < h-1) {
                        bufferedImage.setRGB(i, j+1, black);
                    }
                    if (i < w-1) {
                        bufferedImage.setRGB(i+1, j, black);
                    }
                    if (i < w-1 && j > 0) {
                        bufferedImage.setRGB(i+1, j-1, black);
                    }
                    if (i < w-1 && j < h-1) {
                        bufferedImage.setRGB(i+1, j+1, black);
                    }
                    if (i > 0 && j < h-1) {
                        bufferedImage.setRGB(i-1, j+1, black);
                    }
                }
            }
        }
        return inputimg;
    }

    public static void main(String[] args) throws IOException {
        BufferedImage image1 = ImageIO.read(new File("d:/test.png"));
        BufferedImage image2=changeBig(image1);
        BufferedImage image3=binarization(image2);
        ImageIO.write(image3,"png", new File("d:/erzhihua.png"));
    }


}

