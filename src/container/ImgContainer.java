package src.container;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.util.LinkedHashMap;

//���ͼƬ������
public class ImgContainer {
    public LinkedHashMap<String, BufferedImage> imgcontainer = new LinkedHashMap<>();
    public ImgContainer(){

    }
    public void imgIn(String imgname,BufferedImage img){
        imgcontainer.put(imgname,img);
    }

    public void showmapnum(){
        System.out.print(imgcontainer.size());
    }

    public String getmapkey(int index) {
        String key="û��ͼƬ";
        if (imgcontainer.size() > 0) {
            Object[] keys = imgcontainer.keySet().toArray();
            key=(String)keys[index-1];
            return key;
        }
            return key;
    }

}



