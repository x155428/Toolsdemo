package src.container;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.util.LinkedHashMap;

//´æ·ÅÍ¼Æ¬µÄÈİÆ÷
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
        String key="Ã»ÓĞÍ¼Æ¬";
        if (imgcontainer.size() > 0) {
            Object[] keys = imgcontainer.keySet().toArray();
            key=(String)keys[index-1];
            return key;
        }
            return key;
    }

}



