package src.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;

//չʾ��ά��ɨ����
public class showScanResult {
    public showScanResult(String scanResult){
        //����ɨ����������������
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ɨ����");
        alert.setHeaderText("����Ѹ��Ƶ����а�,ճ������˶��Ƿ���ȷ��");
        alert.setContentText("ɨ������"+scanResult);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.setResult(ButtonType.CLOSE)));
        timeline.play();
        alert.showAndWait();

    }
}
