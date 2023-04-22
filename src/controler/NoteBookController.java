package src.controler;

import java.io.File;
import java.io.IOException;

import src.utils.FileTools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NoteBookController {

    @FXML
    private MenuItem SaveMenu;
    @FXML
    private MenuItem FindMenu;
    @FXML
    private CheckMenuItem WrapMenu;
    @FXML
    private AnchorPane layoutPane;
    @FXML
    private MenuItem ReplaceMenu;
    @FXML
    private CheckMenuItem StateMenu;
    @FXML
    private MenuItem OpenMenu;
    @FXML
    private MenuItem TypefaceMenu;
    @FXML
    private MenuItem NewMenu;
    @FXML
    private TextArea ta;
    @FXML
    private Label label;
    @FXML
    private MenuItem Redo;
    @FXML
    private MenuItem Undo;

    private File result;

    // ��ʼ������λ��
    int startIndex = 0;
    // textarea�й���λ��
    int position = 0;

    // �Ҷȿ���
    public void initialize() {
        // ��ʼ״̬�²���ʹ�ò������滻����
        FindMenu.setDisable(true);
        ReplaceMenu.setDisable(true);
        //��ʼ״̬����ʹ�ó�������������
        Redo.setDisable(true);
        Undo.setDisable(true);

        // ״̬��������
        label.setVisible(false);

        ta.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                position = ta.getCaretPosition();
                label.setText("��" + position + "���ַ�");
            }
        });

        // ��textarea�������Ƿ�ı���м���
        ta.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // ���testarea�����ݲ�λ��,�����ʹ�ò������滻
                if (ta.getLength() > 0) {
                    FindMenu.setDisable(false);
                    ReplaceMenu.setDisable(false);
                } // ������ò������滻
                else {
                    FindMenu.setDisable(true);
                    ReplaceMenu.setDisable(true);
                }
                Redo.setDisable(false);
                Undo.setDisable(false);
                // ���λ��
                position = ta.getCaretPosition();
                label.setText("��" + position + "���ַ�");
            }
        });
    }

    // �޸�ǰ��
    void saveadvance() {
        if (result != null && ta.getLength() > 0) {

            FileTools.writeFile(result, ta.getText());
        } else if (result == null && ta.getLength() > 0) {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setTitle("���浱ǰ����");
            result = fileChooser.showSaveDialog(null);
            if (result != null) {
                FileTools.writeFile(result, ta.getText());
            }
        }
    }

    // �½�����
    @FXML
    void onNewMenu(ActionEvent event) {

        // �½�ǰ����
        saveadvance();

        ta.clear();
        result = null;
    }

    // �򿪹���
    @FXML
    void onOpenMenu(ActionEvent event) {

        // ��ǰ����
        saveadvance();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        result = fileChooser.showOpenDialog(null);
        if (result != null) {
            ta.setText(FileTools.readFile(result));
        }
    }

    // ���湦��
    @FXML
    void onSaveMenu(ActionEvent event) throws IOException {
        if (result != null)// ����Ѿ����ڱ���·��
        {
            FileTools.writeFile(result, ta.getText());
        } else// ��������ڱ����·��
        {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);
            result = fileChooser.showSaveDialog(null);
            if (result != null) {
                FileTools.writeFile(result, ta.getText());
            }
        }
    }

    //����
    @FXML
    void onUndoMenu(ActionEvent event) {
        ta.undo();
        Undo.setDisable(true);
    }

    //����
    @FXML
    void onRedoMenu(ActionEvent event) {
        ta.redo();
        Redo.setDisable(true);
    }

    // ���ҹ���
    @FXML
    void onFindMenu(ActionEvent event) throws IOException {
        HBox h1 = new HBox();
        h1.setPadding(new Insets(20, 5, 20, 5));
        h1.setSpacing(5);
        Label lable1 = new Label("��������(N):");
        TextField tf1 = new TextField();
        h1.getChildren().addAll(lable1, tf1);

        VBox v1 = new VBox();
        v1.setPadding(new Insets(20, 5, 20, 10));
        Button btn1 = new Button("������һ��(F)");
        v1.getChildren().add(btn1);

        HBox findRootNode = new HBox();
        findRootNode.getChildren().addAll(h1, v1);

        Stage findStage = new Stage();
        Scene scene1 = new Scene(findRootNode, 450, 90);
        findStage.setTitle("����");
        findStage.setScene(scene1);
        findStage.setResizable(false); // �̶����ڴ�С
        findStage.show();

        btn1.setOnAction((ActionEvent e) -> {
            String textString = ta.getText(); // ��ȡ���±��ı�����ַ���
            String tfString = tf1.getText(); // ��ȡҪ���ҵ��ַ���
            if (!tf1.getText().isEmpty()) {
                if (textString.contains(tfString)) {
                    // ���ҷ���
                    if (startIndex == -1) {// not found
                        Alert alert1 = new Alert(AlertType.WARNING);
                        alert1.titleProperty().set("��ʾ");
                        alert1.headerTextProperty().set("�Ѿ��Ҳ�����������ˣ�");
                        alert1.show();
                    }
                    startIndex = ta.getText().indexOf(tf1.getText(), startIndex);
                    if (startIndex >= 0 && startIndex < ta.getText().length()) {
                        ta.selectRange(startIndex, startIndex + tf1.getText().length());
                        startIndex += tf1.getText().length();
                    }
                }
                if (!textString.contains(tfString)) {
                    Alert alert1 = new Alert(AlertType.WARNING);
                    alert1.titleProperty().set("��ʾ");
                    alert1.headerTextProperty().set("�Ҳ���������ݣ�");
                    alert1.show();
                }
            } else if (tf1.getText().isEmpty()) {
                Alert alert1 = new Alert(AlertType.WARNING);
                alert1.titleProperty().set("������");
                alert1.headerTextProperty().set("��������Ϊ��");
                alert1.show();
            }
        });
    }

    // �滻����
    @FXML
    void onReplaceMenu(ActionEvent event) throws IOException {
        HBox h1 = new HBox();
        h1.setPadding(new Insets(20, 5, 10, 8));
        h1.setSpacing(5);
        Label label1 = new Label("������һ��(F)");
        TextField tf1 = new TextField();
        h1.getChildren().addAll(label1, tf1);

        HBox h2 = new HBox();
        h2.setPadding(new Insets(5, 5, 20, 8));
        h2.setSpacing(5);
        Label label2 = new Label("�滻����(N):");
        TextField tf2 = new TextField();
        h2.getChildren().addAll(label2, tf2);

        VBox v1 = new VBox();
        v1.getChildren().addAll(h1, h2);

        VBox v2 = new VBox();
        v2.setPadding(new Insets(21, 5, 20, 10));
        v2.setSpacing(13);
        Button btn1 = new Button("������һ��");
        Button btn2 = new Button("�滻Ϊ");
        v2.getChildren().addAll(btn1, btn2);

        HBox replaceRootNode = new HBox();
        replaceRootNode.getChildren().addAll(v1, v2);

        Stage replaceStage = new Stage();
        Scene scene = new Scene(replaceRootNode, 430, 120);
        replaceStage.setTitle("�滻");
        replaceStage.setScene(scene);
        replaceStage.setResizable(false); // �̶����ڴ�С
        replaceStage.show();

        btn1.setOnAction((ActionEvent e) -> {
            String textString = ta.getText(); // ��ȡ���±��ı�����ַ���
            String tfString = tf1.getText(); // ��ȡ�������ݵ��ַ���
            if (!tf1.getText().isEmpty()) {
                if (textString.contains(tfString)) {
                    if (startIndex == -1) {// not found
                        Alert alert1 = new Alert(AlertType.WARNING);
                        alert1.titleProperty().set("��ʾ");
                        alert1.headerTextProperty().set("�Ѿ��Ҳ�����������ˣ�");
                        alert1.show();
                    }
                    startIndex = ta.getText().indexOf(tf1.getText(), startIndex);
                    if (startIndex >= 0 && startIndex < ta.getText().length()) {
                        ta.selectRange(startIndex, startIndex + tf1.getText().length());
                        startIndex += tf1.getText().length();
                    }
                    btn2.setOnAction((ActionEvent e2) -> {
                        ta.replaceSelection(tf2.getText());
                    });
                }
                if (!textString.contains(tfString)) {
                    Alert alert1 = new Alert(AlertType.WARNING);
                    alert1.titleProperty().set("��ʾ");
                    alert1.headerTextProperty().set("�Ҳ���������ݣ�");
                    alert1.show();
                }

            } else if (tf1.getText().isEmpty()) {
                Alert alert1 = new Alert(AlertType.WARNING);
                alert1.titleProperty().set("������");
                alert1.headerTextProperty().set("��������Ϊ��");
                alert1.show();
            }
        });
    }

    // �Զ�����
    @FXML
    void onWrapMenu(ActionEvent event) {
        ta.setWrapText(WrapMenu.isSelected());
    }

    // ����
    @FXML
    void onTypefaceMenu(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Typeface.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        // �ȴ����ڱ��ر���������һ����Ӧ
        stage.showAndWait();
        if (scene.getUserData() != null)// ����û����趨�������ʽ,�򽫼��±��е������Ϊ����ʽ
        {
            Font font = (Font) scene.getUserData();
            ta.setFont(font);
        }
    }

    // ״̬��
    @FXML
    void onStateMenu(ActionEvent event) {
        label.setVisible(StateMenu.isSelected());
    }

}

