package src.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
//չʾ��ά��ɨ����
public class showScanResult {
    public showScanResult(String scanResult){
        //����ɨ����������������
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ɨ����");
        alert.setHeaderText("����Ѹ��Ƶ����а�,ע��˶��Ƿ���ȷ��");
        alert.setContentText("ɨ������"+scanResult);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.showAndWait();

    }
}
