package src.utils;

import javafx.stage.FileChooser;

public class createFileChooser {
    public createFileChooser(){

    }

    //�ṩ���⡢�ļ����͡��ļ���׺�������ļ�ѡ����
    public FileChooser create(String title,String filetype,String filefuzz){
        FileChooser filechoose=new FileChooser();
        filechoose.setTitle(title);
        FileChooser.ExtensionFilter extfilter=new FileChooser.ExtensionFilter(filetype,filefuzz);
        filechoose.getExtensionFilters().add(extfilter);
        return filechoose;
    }

}
