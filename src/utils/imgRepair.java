package src.utils;
//ͼƬ�޸�

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;

//�Ŵ�ͼƬ����ֵ��������
public class imgRepair {

    public static int YZ = 115;//115

    //�Ŵ�ͼƬ
    public static void twobig(String input,String output) throws IOException {
        File inputFile = new File(input);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // �����µĿ�Ⱥ͸߶�
        int newWidth = inputImage.getWidth() * 4;
        int newHeight = inputImage.getHeight() * 4;

        // ��������ͼƬ
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, inputImage.getType());

        // �������ź��ͼƬ������ͼƬ��
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, newWidth, newHeight, null);
        g2d.dispose();

        // �������ź��ͼƬ
        File outputFile = new File(output);
        ImageIO.write(outputImage, "jpg", outputFile);
    }

    //��ͼƬ
    public BufferedImage sharpen(BufferedImage image) {
        // define sharpening kernel
        float[] sharpenKernel = {
                -0.125f, -0.125f, -0.125f,
                -0.125f,  1.25f, -0.125f,
                -0.125f, -0.125f, -0.125f
        };

        // create convolution operator with sharpening kernel
        Kernel kernel = new Kernel(3, 3, sharpenKernel);
        ConvolveOp op = new ConvolveOp(kernel);

        // apply the operator on the image and return the result
        return op.filter(image, null);
    }

    //ͼƬ��ֵ��
    public static void binarization(String filePath, String fileOutputPath) throws IOException {
        File file = new File(filePath);
        BufferedImage bi = ImageIO.read(file);
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
        ImageIO.write(bufferedImage, "jpg", new File(fileOutputPath));
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

    /**
     * ��ֵ�����ͼ��Ŀ����㣺�ȸ�ʴ�����ͣ�����ȥ��ͼ���С�ڵ㣩
     *
     *  filePath Ҫ�����ͼƬ·��
     *  fileOutputPath ������ͼƬ���·��
     */
    public static void opening(String filePath, String fileOutputPath) throws IOException {
        File file = new File(filePath);
        BufferedImage bi = ImageIO.read(file);
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

        ImageIO.write(bufferedImage, "jpg", new File(fileOutputPath));
    }


}

