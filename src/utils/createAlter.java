package src.utils;

import javafx.scene.control.Alert;

public class createAlter {
    createAlter(){

    }

    public static Alert createalter(String title,String head){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(head);
        return alert;
    }
}
