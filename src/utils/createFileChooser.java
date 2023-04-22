package src.utils;

import javafx.stage.FileChooser;

public class createFileChooser {
    public createFileChooser(){

    }

    //提供标题、文件类型、文件后缀，创建文件选择器
    public FileChooser create(String title,String filetype,String filefuzz){
        FileChooser filechoose=new FileChooser();
        filechoose.setTitle(title);
        FileChooser.ExtensionFilter extfilter=new FileChooser.ExtensionFilter(filetype,filefuzz);
        filechoose.getExtensionFilters().add(extfilter);
        return filechoose;
    }

}
