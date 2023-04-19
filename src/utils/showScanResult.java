package src.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
//展示二维码扫描结果
public class showScanResult {
    public showScanResult(String scanResult){
        //传入扫描结果，创建弹窗。
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("扫描结果");
        alert.setHeaderText("结果已复制到剪切板,注意核对是否正确！");
        alert.setContentText("扫描结果："+scanResult);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.showAndWait();

    }
}
