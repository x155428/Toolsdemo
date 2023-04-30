package src.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;

//展示二维码扫描结果
public class showScanResult {
    public showScanResult(String scanResult){
        //传入扫描结果，创建弹窗。
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("扫描结果");
        alert.setHeaderText("结果已复制到剪切板,粘贴后请核对是否正确！");
        alert.setContentText("扫描结果："+scanResult);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> alert.setResult(ButtonType.CLOSE)));
        timeline.play();
        alert.showAndWait();

    }
}
