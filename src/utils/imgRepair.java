package src.utils;
//ͼƬ�޸�

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//�Ŵ�ͼƬ����ֵ��������
public class imgRepair {

    public static int YZ = 120;//rgb��ֵ115

    //�Ŵ�ͼƬ
    public static BufferedImage changeBig(BufferedImage input) throws IOException {
        BufferedImage inputImage=input;

        // �����µĿ�Ⱥ͸߶�
        int newWidth = inputImage.getWidth() * 5;
        int newHeight = inputImage.getHeight() * 5;

        // ��������ͼƬ
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());

        // �������ź��ͼƬ������ͼƬ��
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();
        return outputImage;

        // �������ź��ͼƬ
        //File outputFile = new File(output);
        //ImageIO.write(outputImage, "jpg", outputFile);
    }
/*
    //��ͼƬ
    public static BufferedImage sharpen(BufferedImage image) {
        // define sharpening kernel
        int width=image.getWidth();
        int height=image.getHeight();

        // ���������
        int[][] kernelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] kernelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

        // �������ͼƬ
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // ��ÿ�����ؽ��о������
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                // ������ֵ
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
                // ���������
                int value = (int) Math.sqrt(valueX * valueX + valueY * valueY);
                // �Խ�����нض�
                value = Math.min(255, Math.max(0, value));
                // ������������ͼƬ
                output.setRGB(x, y, (value << 16) | (value << 8) | value);
            }
        }
        return output;

    }

 */

    //ͼƬ��ֵ��
    public static BufferedImage binarization(BufferedImage inputimg) throws IOException {
        BufferedImage bi =inputimg;
        // ��ȡ��ǰͼƬ�ĸ�,��,ARGB
        int h = bi.getHeight();
        int w = bi.getWidth();
        int arr[][] = new int[w][h];

        // ��ȡͼƬÿһ���ص�ĻҶ�ֵ
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // getRGB()����Ĭ�ϵ�RGB��ɫģ��(ʮ����)
                arr[i][j] = getImageGray(bi.getRGB(i, j));// �õ�ĻҶ�ֵ
            }
        }

        // ����һ������ΪԤ����ͼ������,BufferedImage
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);

        // ��Ԥ�����õ���ֵ��С���бȽϣ���ľ���ʾΪ255����ɫ��С�ľ���ʾΪ0����ɫ
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
        String argb = Integer.toHexString(rgb);// ��ʮ���Ƶ���ɫֵתΪʮ������
        // argb�ֱ����͸��,��,��,�� �ֱ�ռ16����2λ
        int r = Integer.parseInt(argb.substring(2, 4), 16);// �������Ϊʹ�ý���
        int g = Integer.parseInt(argb.substring(4, 6), 16);
        int b = Integer.parseInt(argb.substring(6, 8), 16);
        int gray = (int) (r*0.3 + g*0.59 + b*0.11);
        return gray;
    }

    /**
     * �Լ�����Χ8���Ҷ�ֵ�ٳ���9���������ԻҶ�ֵ
     *gray
     x Ҫ����Ҷȵĵ�ĺ�����
     y Ҫ����Ҷȵĵ��������
     w ͼ��Ŀ��
     h ͼ��ĸ߶�
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
      ��ֵ�����ͼ��Ŀ����㣺�ȸ�ʴ�����ͣ�����ȥ��ͼ���С�ڵ㣩
     */
    public static BufferedImage opening(BufferedImage inputimg) throws IOException {
        BufferedImage bi =inputimg;
        // ��ȡ��ǰͼƬ�ĸ�,��,ARGB
        int h = bi.getHeight();
        int w = bi.getWidth();
        int arr[][] = new int[w][h];
        // ��ȡͼƬÿһ���ص�ĻҶ�ֵ
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // getRGB()����Ĭ�ϵ�RGB��ɫģ��(ʮ����)
                arr[i][j] = getImageGray(bi.getRGB(i, j));// �õ�ĻҶ�ֵ
            }
        }

        int black = new Color(0, 0, 0).getRGB();
        int white = new Color(255, 255, 255).getRGB();
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        // ��ʱ�洢��ʴ��ĸ����������
        int temp[][] = new int[w][h];
        // 1.�Ƚ��и�ʴ����
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                /*
                 * Ϊ0��ʾ�ĵ����Χ8���㶼�Ǻڣ���õ㸯ʴ������Ϊ��
                 * ���ڹ�˾ͼƬ̬ģ������ȫ�ﵽ9����ȫΪ�ڵĵ�̫�٣����Ч���ܲ�ʸ�Ϊ��С��30
                 * ��д30��ԭ���ǣ���ֻ��һ����Ϊ�ף����ܹ�255������getGray������õ�255/9 = 28��
                 */
                if (getGray(arr, i, j, w, h) < 30) {
                    temp[i][j] = 0;
                } else{
                    temp[i][j] = 255;
                }
            }
        }

        // 2.�ٽ������Ͳ���
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                bufferedImage.setRGB(i, j, white);
            }
        }
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // Ϊ0��ʾ�ĵ����Χ8���㶼�Ǻڣ���õ㸯ʴ������Ϊ��
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

