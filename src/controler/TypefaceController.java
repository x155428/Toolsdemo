package src.controler;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TypefaceController {
    //����
    @FXML
    private ComboBox<String> Cb1;
    //����
    @FXML
    private ComboBox<String> Cb2;
    //��С
    @FXML
    private ComboBox<String> Cb3;
    @FXML
    private Button SureBtn;
    @FXML
    private TextArea ta;

    public Font Txtfont=Font.font(12);
    private Font font=Font.font(12);

    //����
    String type="����";
    //����
    String special="����";
    //��С
    int size=12;

    @FXML
    void initialize() {

        List<String> getFontName = Font.getFontNames();
        Cb1.getItems().addAll(getFontName);

        Cb2.getItems().addAll("����","����","б��","��б��");

        for(int i=2;i<=32;i=i+2)
        {
            Cb3.getItems().add(i+"");
        }

        Cb1.setOnAction(e->{
            type = Cb1.getValue();
            setFont();
        });

        Cb2.setOnAction(e->{
            special = Cb2.getValue();
            setFont();
        });

        Cb3.setOnAction(e->{
            size = Integer.parseInt(Cb3.getValue());
            setFont();
        });
    }

    //����������ʽ
    public void setFont() {
        font = Font.font(type, size);
        if(special.equals("����"))
            font=Font.font(type, FontWeight.NORMAL, FontPosture.REGULAR,size);//����
        else if(special.equals("����"))
            font=Font.font(type, FontWeight.BOLD, FontPosture.REGULAR,size);//����
        else if(special.equals("б��"))
            font=Font.font(type, FontWeight.NORMAL, FontPosture.ITALIC,size);//б��
        else if(special.equals("��б��"))
            font=Font.font(type, FontWeight.BOLD, FontPosture.ITALIC,size);//��б��
        ta.setFont(font);
    }


    @FXML
    void onSureBtn(ActionEvent event) {
        Txtfont=font;
        Scene scene = SureBtn.getScene();
        //��ѡ���������¼���û�������
        scene.setUserData(Txtfont);
        //���ȷ����,�رմ���
        Stage stage=(Stage)SureBtn.getScene().getWindow();
        stage.close();
    }

}


