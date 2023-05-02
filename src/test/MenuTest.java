package src.test;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import src.container.ImgContainer;
import src.utils.createAlter;
import src.utils.dealString;
import src.utils.imgRepair;
import src.utils.showScanResult;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MultiResolutionImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class MenuTest extends Application {
    private VBox menu;  //���˵�����
    BorderPane content;  //�Ҳ���������
    Stage primary;    //������
    Stage stage;    //��ͼ�ɰ��´���
    //ImageView iv;//ͼƬ��ʾ����
    HBox view;//��ѡ�ĺ����ʾ
    TabPane ChoseImgPane;//ͼƬչʾ��
    private double sceneX_start;
    private double sceneY_start;
    private double sceneX_end;
    private double sceneY_end;
    //��ȡͼƬ���
    int imgw;
    int imgh;
    int index=0;//��ű�ʶ

    //�˵���ť
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;

    //��ͼ�ײ����߰�ť
    Button cutBtn;
    Button scanBtn;
    Button copyBtn;
    Button importBtn;
    Button editBtn;
    Button delBtn;
    Button clearBtn;
    Button saveBtn;
    Button openallbtn;

    //��ͼƬ������
    ImgContainer imgmap=new ImgContainer();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primary=primaryStage; //������
        BorderPane root = new BorderPane(); //������

        // �������˵���
        menu = new VBox();
        menu.setPrefWidth(100);
        menu.setAlignment(Pos.TOP_LEFT);
        menu.setStyle("-fx-background-color: #87CEFA; -fx-padding: 5px;");
        Label title = new Label("����");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px;");
        btn1 = new Button("��ͼɨ��");
        btn1.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn7 = new Button("��ǩ");
        btn7.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn3 = new Button("�ַ�����");
        btn3.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn4 = new Button("�ı��Ա�");
        btn4.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn5 = new Button("IP����");
        btn5.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn2=new Button("���±�");
        btn2.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn6 = new Button("�����");
        btn6.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");

        // �󶨰�ť1����¼�
        btn1.setOnAction(actionEvent -> {
            try {
                setContent(1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btn2.setOnAction(actionEvent -> {
            try {
                setContent(2);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        btn3.setOnAction(actionEvent -> {
            try {
                setContent(3);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        btn4.setOnAction(actionEvent -> {
            try {
                setContent(4);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        menu.getChildren().addAll(title, btn1, btn2,btn7, btn3, btn4, btn5, btn6);
        root.setLeft(menu);

        // �����м���������
        content = new BorderPane();
        content.setStyle("-fx-background-color: #f4f4f4;");
        Label label = new Label("��������");
        label.setStyle("-fx-font-size: 24px;");
        content.getChildren().add(label);
        root.setCenter(content);

        //����������
        Scene scene = new Scene(root, 800, 650);
        primaryStage.setScene(scene);
        primary.getIcons().add(new javafx.scene.image.Image("src/resource/img/����˼������.png"));
        primaryStage.setTitle("����demo");
        primaryStage.show();
        scene.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.ESCAPE){
                stage.close();
                primary.setIconified(false);
            }
        });

    }

    // ����������л��Ҳ�����
    private void setContent(int menunumber) throws IOException {
        // ���ԭ����
        System.out.println(menunumber);
        // ����Ŀ¼������������
        switch (menunumber){
            case 1://���ؽ�ͼ����
                //���ð�ť��������
                btn1.setDisable(true);
                btn2.setDisable(false);
                btn3.setDisable(false);
                btn4.setDisable(false);
                btn5.setDisable(false);
                btn6.setDisable(false);
                content.getChildren().clear();
                cutBtn=new Button("��ͼ");
                scanBtn=new Button("ɨ��");
                copyBtn=new Button("����");
                importBtn=new Button("����");
                editBtn=new Button("�༭");
                delBtn=new Button("ɾ��");
                clearBtn=new Button("���");
                saveBtn=new Button("����");
                openallbtn=new Button("������ͼƬ");

                editBtn.setDisable(true);
                openallbtn.setDisable(true);
                ToolBar BottonToolsBar=new ToolBar(cutBtn,scanBtn,copyBtn,importBtn,editBtn,delBtn,clearBtn,saveBtn,openallbtn);
                content.setBottom(BottonToolsBar);
                System.out.println(imgmap.imgcontainer.size());
                ChoseImgPane=new TabPane(); //���ѡ����

                //���ԣ���ӡѡ�е�ѡ�
                ChoseImgPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
                ChoseImgPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
                    if (newTab != null) {
                        System.out.println("ѡ�е�Tab������Ϊ��" + newTab.getText());
                    }
                });
                //����tab,�ر�tabʱɾ��ͼƬ(������Ӧֵ�ÿգ�
                ChoseImgPane.getTabs().addListener((ListChangeListener<Tab>) change -> {
                    while (change.next()){
                        if(change.wasRemoved()){
                            for(Tab tab:change.getRemoved()){
                                String s=tab.getText();
                                ChoseImgPane.getTabs().remove(tab);
                                System.out.println("ɾ����"+s);
                                imgmap.imgcontainer.put(s,null);
                                System.out.println(imgmap.imgcontainer.get(s));
                            }
                        }
                    }
                });

                //borderpane�м�չʾͼƬ�����湤��������
                content.setCenter(ChoseImgPane);

                //��ͼ��ť
                cutBtn.setOnAction(actionEvent -> show());

                //ɨ�谴ť(��ͼ̫Сʱ�޷�ɨ�������
                scanBtn.setOnAction(actionEvent -> {
                    if(imgmap.imgcontainer.size()>0){
                        try {
                            // ����ͼƬ
                            if(ChoseImgPane.getSelectionModel().getSelectedItem()!=null){
                                String ImgName=ChoseImgPane.getSelectionModel().getSelectedItem().getText();//��ȡ��ǰtab���֣����Ҷ�ӦͼƬ
                                System.out.println(ImgName);
                                BufferedImage img=imgmap.imgcontainer.get(ImgName);
                                // ������ά���ȡ��
                                MultiFormatReader reader = new MultiFormatReader();
                                // ��ͼƬת��Ϊ������λͼ
                                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(img)));
                                // ������ά��
                                Result result = reader.decode(binaryBitmap);
                                // �����ά�����ݵ����а�
                                Clipboard clipboard = Clipboard.getSystemClipboard();
                                ClipboardContent content = new ClipboardContent();
                                content.putString(result.getText());
                                clipboard.setContent(content);
                                System.out.println(result.getText());
                                //������ʾ
                                new showScanResult(result.getText());

                            }else{
                                System.out.println("û��ѡ��ͼƬ��");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            // ������ʧ��ʱ����ͼƬ�����������޸���ά����ɨ��
                            System.out.println("����ʧ�ܣ������޸���ά��...");
                            //��ȡ��ǰѡ��ͼƬ
                            String ImgName=ChoseImgPane.getSelectionModel().getSelectedItem().getText();
                            BufferedImage oldimg=imgmap.imgcontainer.get(ImgName);
                            //��һ���޸�--�Ŵ���
                            BufferedImage  newimg;
                            try {
                                if(oldimg.getWidth()<80|oldimg.getHeight()<80){
                                    newimg=imgRepair.changeBig(oldimg);
                                }else{
                                    newimg=oldimg;
                                }

                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            try {
                                newimg=imgRepair.binarization(newimg);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                            try {
                                newimg= imgRepair.opening(newimg);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }

                            // ������ά���ȡ��
                            MultiFormatReader reader = new MultiFormatReader();
                            // ��ͼƬת��Ϊ������λͼ
                            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(newimg)));
                            // ������ά��
                            try {
                                Result result = reader.decode(binaryBitmap);
                                System.out.println(result.getText());
                                Clipboard clipboard = Clipboard.getSystemClipboard();
                                ClipboardContent content = new ClipboardContent();
                                content.putString(result.getText());
                                clipboard.setContent(content);
                                new showScanResult(result.getText());
                            } catch (NotFoundException ex) {
                               Alert failAlter= createAlter.createalter("��ʾ��","");
                                failAlter.setContentText("ɨ��ʧ�ܣ�������ͼ��С������������ɨ�裡");
                                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> failAlter.setResult(ButtonType.CLOSE)));
                                timeline.play();
                                failAlter.showAndWait();
                            }
                        }
                    }else {
                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("��ʾ��");
                        alert.setHeaderText("");
                        alert.setContentText("û��ͼƬ��");
                        alert.getButtonTypes().remove(ButtonType.CANCEL);
                        alert.showAndWait();
                    }
                });

                //���ư�ť
                copyBtn.setOnAction(actionEvent -> {
                    if(imgmap.imgcontainer.size()>0){
                        String ImgName=ChoseImgPane.getSelectionModel().getSelectedItem().getText();//��ȡ��ǰtab���֣����Ҷ�ӦͼƬ
                        BufferedImage img=imgmap.imgcontainer.get(ImgName);
                        // ��ȡϵͳ���а�
                        Clipboard clipboard = Clipboard.getSystemClipboard();
                        ClipboardContent content = new ClipboardContent();
                        content.putImage(SwingFXUtils.toFXImage(img,null));
                        clipboard.setContent(content);
                    }else {
                        Alert noimgAlert=new Alert(Alert.AlertType.CONFIRMATION);
                        noimgAlert.setTitle("��ʾ��");
                        noimgAlert.setHeaderText("");
                        noimgAlert.setContentText("û��ͼƬ");
                        noimgAlert.getButtonTypes().remove(ButtonType.CANCEL);
                        noimgAlert.showAndWait();

                    }



                });

                //���밴ť
                importBtn.setOnAction(actionEvent -> {
                    FileChooser file_image_chose=new FileChooser();
                    file_image_chose.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                    File file_img=file_image_chose.showOpenDialog(primary);
                    if(file_img!=null){
                        try {
                            BufferedImage tmpbufferedimage=ImageIO.read(file_img);
                            WritableImage writableimg= SwingFXUtils.toFXImage(tmpbufferedimage,null);
                            index++;
                            String tmpbufferedimageName="ͼƬ"+index;
                            imgmap.imgcontainer.put(tmpbufferedimageName,tmpbufferedimage);
                            Tab tmptab=new Tab(tmpbufferedimageName);
                            System.out.println(tmptab.getText());
                            ImageView iv=new ImageView();
                            iv.setFitWidth(680);
                            iv.setFitHeight(580);
                            iv.setPreserveRatio(true);
                            iv.setImage(writableimg);
                            tmptab.setContent(iv);
                            ChoseImgPane.getTabs().add(tmptab);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });

                //�༭��ť����δʵ�֣�
                editBtn.setOnAction(actionEvent -> {

                });

                //ɾ����ť
                delBtn.setOnAction(actionEvent -> {
                    //ɾ��������ͼƬ���ر�tab��
                    String ImgName=ChoseImgPane.getSelectionModel().getSelectedItem().getText();
                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("��ʾ��");
                    alert.setHeaderText("");
                    alert.setContentText("��ɾ��"+ImgName+"����ɾ������ɾ�������ļ���");
                    alert.getButtonTypes().remove(ButtonType.CANCEL);
                    ChoseImgPane.getTabs().remove(ChoseImgPane.getSelectionModel().getSelectedItem());
                    alert.showAndWait();

                    imgmap.imgcontainer.put(ImgName,null);
                });

                //��հ�ť
                clearBtn.setOnAction(actionEvent -> {
                    if(imgmap.imgcontainer.size()>0){
                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("���棡");
                        alert.setHeaderText("");
                        alert.setContentText("ȷ��Ҫ�������ջ�ɾ������ͼƬ��");
                        Optional<ButtonType> result=alert.showAndWait();
                        if(result.get()==ButtonType.OK){
                            imgmap.imgcontainer.clear();
                            index=0;
                            ChoseImgPane.getTabs().clear();
                        }
                    }else{
                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("��ʾ��");
                        alert.setHeaderText("");
                        alert.setContentText("û��ͼƬ��ɾ����");
                        alert.getButtonTypes().remove(ButtonType.CANCEL);
                        alert.showAndWait();
                    }

                });

                //���水ť(������)
                saveBtn.setOnAction(actionEvent -> {
                    if(imgmap.imgcontainer.size()>0){
                        //��õ�ǰѡ�е�tab,�ҵ�tab���֣���img�����в鵽��ӦͼƬ����
                        String ImgName=ChoseImgPane.getSelectionModel().getSelectedItem().getText();
                        System.out.println("����ͼƬ��"+ImgName);
                        BufferedImage selectTabimg=imgmap.imgcontainer.get(ImgName);
                        FileChooser filechoose=new FileChooser();
                        filechoose.setTitle("����ͼƬ");
                        FileChooser.ExtensionFilter extfilter=new FileChooser.ExtensionFilter("ͼƬ��*.jpg)","*.png","*.gif");
                        filechoose.getExtensionFilters().add(extfilter);
                        File imgfile=filechoose.showSaveDialog(primary);
                        if(imgfile!=null){
                            String filepath=imgfile.getAbsolutePath();
                            //�ж��ļ��������ͣ��ļ���׺
                            // if(filepath)
                            try {
                                ImageIO.write(selectTabimg,"png",imgfile);
                                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("������ʾ��");
                                alert.setHeaderText("");
                                alert.getButtonTypes().remove(ButtonType.CANCEL);
                                alert.setContentText("����ɹ����ļ������ڣ�"+filepath);
                                alert.showAndWait();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }else {
                          Alert  alert= createAlter.createalter("��ʾ��","");
                          alert.getButtonTypes().remove(ButtonType.CANCEL);
                          alert.setContentText("�ļ�δ���棡");
                          alert.showAndWait();
                        }

                    }
                });

                //������ͼƬ����δʵ�֣�
                openallbtn.setOnAction(actionEvent -> {

                });
                break;
            case 2:
                System.out.println("���±�");
                btn2.setDisable(true);
                btn1.setDisable(false);
                btn3.setDisable(false);
                btn4.setDisable(false);
                btn5.setDisable(false);
                btn6.setDisable(false);
                content.getChildren().clear();
                imgmap.imgcontainer.clear();
                index=0;
                //����������
                primary.setIconified(true);
                //�½��³���,���ؼ��±�����
                Stage notepadStage=new Stage();
                Parent root= FXMLLoader.load(getClass().getResource("/src/resource/FXML/NoteBook.fxml"));
                Scene notebookScene=new Scene(root,600,400);
                notepadStage.setScene(notebookScene);
                notepadStage.show();
                notepadStage.setOnCloseRequest(windowEvent -> {
                    primary.setIconified(false);
                    btn2.setDisable(false);
                });
                notebookScene.setOnKeyPressed(event -> {
                    if(event.getCode()== KeyCode.ESCAPE){
                        notepadStage.close();
                        btn2.setDisable(false);
                        primary.setIconified(false);
                    }
                });
                break;
            case 3:
                System.out.println("�ַ�����");
                btn2.setDisable(false);
                btn1.setDisable(false);
                btn3.setDisable(true);
                btn4.setDisable(false);
                btn5.setDisable(false);
                btn6.setDisable(false);
                content.getChildren().clear();
                imgmap.imgcontainer.clear();
                index=0;
                //��־�������
                TextArea originaldataview=new TextArea();
                originaldataview.setWrapText(true);
                content.setCenter(originaldataview);
                Button dealbth=new Button("ȷ��");
                ToolBar handlestrtoolbar=new ToolBar(dealbth);
                content.setBottom(handlestrtoolbar);
                originaldataview.setPromptText("������־��Ϣ�󵥻�ȷ��......");
                //ȷ����ť����¼�
                dealbth.setOnAction(new EventHandler<>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        primary.setIconified(true);
                        //�½��³���,����չʾ����
                        Stage stringdeal = new Stage();
                        stringdeal.setResizable(false);
                        Parent root = null;
                        FXMLLoader loader;
                        try {
                            loader = new FXMLLoader(getClass().getResource("/src/resource/FXML/stringhandle.fxml"));
                            root = loader.load();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Scene stringhandleresult = new Scene(root, 800, 650);

                        stringdeal.setScene(stringhandleresult);
                        stringdeal.show();
                        String originaldata = originaldataview.getText();
                        //Stringhandle an = loader.getController();
                        //��ȡoriginaltextarea����������
                        TextArea originaldataview = (TextArea) root.lookup("#originaldataview");
                        originaldataview.setWrapText(true);
                        dealString deal = new dealString(originaldata);
                        originaldataview.appendText("\n" + originaldata);


                        //��ȡpayloadtextarea��������
                        TextArea payloaddataview = (TextArea) root.lookup("#payloaddataview");
                        payloaddataview.setWrapText(true);
                        if(deal.resultmap.get("�����غɣ�")!=null&&deal.resultmap.get("�����غɣ�")!=""){
                            payloaddataview.appendText("\n" + dealString.decodeStr(deal.resultmap.get("�����غɣ�")));
                            payloaddataview.appendText("\n" + "**********************************************************");
                            payloaddataview.appendText("\nԭpayload:");
                            payloaddataview.appendText("\n" + deal.resultmap.get("�����غɣ�"));

                        }

                        //��ȡbasetextarea��������
                        TextArea basetataview = (TextArea) root.lookup("#basetataview");
                        basetataview.setWrapText(true);
                        basetataview.appendText(deal.head);
                        for (String key : deal.resultmap.keySet()) {
                            String value = deal.resultmap.get(key);
                            //���˵����ֶ�
                            if (!match(value)&&key!="�����غɣ�") {
                                basetataview.appendText("\n" + key + value);
                            }

                        }
                        //�������ڹرգ���ʾ�����档
                        stringdeal.setOnCloseRequest(windowEvent -> primary.setIconified(false));
                        stringhandleresult.setOnKeyPressed(event -> {
                            if (event.getCode() == KeyCode.ESCAPE) {
                                stringdeal.close();
                                primary.setIconified(false);
                            }
                        });
                    }
                });
                break;
            case 4:




                break;
            case 5:
                Label label5 = new Label("5");
                label5.setStyle("-fx-font-size: 24px;");
                content.getChildren().add(label5);
                break;
            case 6:
                Label label6 = new Label("6");
                label6.setStyle("-fx-font-size: 24px;");
                content.getChildren().add(label6);
                break;
        }
    }

    //��ͼ����ӵ�չʾ�������tabpane
    private void addTab() {

    }

    //�ַ���������ȥ�����������еĿ��ֶ�
    public boolean match(String str) {
        String pattern = ".*\\(\\d{4}\\)=$";
        String pattern1="^.*\\(\\d{4}\\)=--$";
        if(str.matches(pattern)|str.matches(pattern1)){
            return true;
        }
        return false;
    }


    private void show() {
        //�����ť����������С
        primary.setIconified(true);
        //�����´���
        stage=new Stage();
        //�����ɰ棬��סȫ��
        AnchorPane newpane=new AnchorPane();
        newpane.setStyle("-fx-background-color: #B5B5B522");//�ɰ�͸��
        Scene scene=new Scene(newpane);
        scene.setFill(Paint.valueOf("#ffffff00"));//����͸��
        stage.setFullScreenExitHint("");//����ɰ�����
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        drag(newpane);

        //������������esc�ر��´��ڣ���ʾ�ɴ���
        scene.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.ESCAPE){
                stage.close();
                primary.setIconified(false);
            }
        });

    }

    public void drag(AnchorPane an) {
        an.setOnMousePressed(event -> {
            an.getChildren().clear();
            view=new HBox();
            view.setBackground(null);
            view.setBorder(new Border(new BorderStroke(Paint.valueOf("#CD3700"), BorderStrokeStyle.SOLID,null,new BorderWidths(2))));
            sceneX_start = event.getSceneX();
            sceneY_start = event.getSceneY();

            an.getChildren().add(view);
            AnchorPane.setLeftAnchor(view,sceneX_start);
            AnchorPane.setTopAnchor(view,sceneY_start);
        });
        an.setOnDragDetected(event -> an.startFullDrag());

        an.setOnMouseDragOver(event -> {
            sceneX_end=event.getSceneX();
            sceneY_end= event.getSceneY();
            double width=sceneX_end-sceneX_start;
            double height=sceneY_end-sceneY_start;
            imgw=(int)width;
            imgh=(int)height;
            view.setPrefWidth(width);
            view.setPrefHeight(height);
            //���ϽǱ�
            Label label=new Label();
            label.setAlignment(Pos.CENTER);
            label.setPrefWidth(100);
            label.setPrefHeight(30);
            label.setText("��:"+width+","+"��:"+height);
            AnchorPane.setLeftAnchor(label,sceneX_start);
            AnchorPane.setTopAnchor(label,sceneY_start-label.getPrefHeight());
            label.setTextFill(Paint.valueOf("#ffffff"));
            label.setStyle("-fx-background-color: #000000");
            an.getChildren().add(label);
        });
        an.setOnMouseDragExited(event -> {
            Button truebtn=new Button("ȷ��");
            view.getChildren().add(truebtn);
            view.setAlignment(Pos.BOTTOM_RIGHT);
            truebtn.setOnAction(event1 -> {
                try {
                    getScreenImage();
                } catch (AWTException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        });
    }

    //ѡ��ȷ������ͼƬ
    public void getScreenImage() throws AWTException, IOException {
        stage.close();
        int startx=(int)Math.min(sceneX_start,sceneX_end);
        int starty=(int)Math.min(sceneY_start,sceneY_end);
        System.out.println(imgw +"--"+ imgh);
        Robot robot = new Robot();
        System.out.println(sceneX_start+"--"+sceneY_start+"--"+sceneX_end+"--"+sceneY_end);
        Rectangle rec = new Rectangle(startx, starty, imgw, imgh);
        MultiResolutionImage mrImage = robot.createMultiResolutionScreenCapture(rec);
        java.util.List<Image> resultimgs=mrImage.getResolutionVariants();
        BufferedImage buffimg;
        if(resultimgs.size()>1){
            buffimg=(BufferedImage) resultimgs.get(1);
        }else{
            buffimg=(BufferedImage) resultimgs.get(0);
        }
        //����ͼƬ���֣�����ͼƬ����
        index++;
        String tmpimgname="ͼƬ"+index;
        System.out.println(tmpimgname);
        imgmap.imgIn(tmpimgname,buffimg);
        imgmap.showmapnum();
        WritableImage wi= SwingFXUtils.toFXImage(buffimg,null);
        //File imgfile=new File("d://1111.png");
        //ImageIO.write(buffimg,"png",imgfile);
        //�½�tab��ʾͼƬ
        Tab tmptab=new Tab(imgmap.getmapkey(index));
        System.out.println(tmptab.getText());
        ImageView iv=new ImageView();
        iv.setImage(wi);
        //�޶�imageview��ߣ��ȱ�����
        iv.setFitWidth(680);
        iv.setFitHeight(580);
        iv.setPreserveRatio(true);
        tmptab.setContent(iv);
        ChoseImgPane.getTabs().add(tmptab);
        //��ȡtabpaneģ�ͣ�������ӵ�tab��Ϊѡ��״̬
        SingleSelectionModel<Tab> selectionModel = ChoseImgPane.getSelectionModel();
        selectionModel.select(tmptab);
        primary.setIconified(false);
    }


    public static void main(String[] args) {
        String modulePath = "D:/idea/openjfx-20_windows-x64_bin-sdk/javafx-sdk-20/lib";
        String addModules = "javafx.controls,javafx.fxml";
        System.setProperty("java.class.path", modulePath);
        System.setProperty("java.module.path", modulePath);
        System.setProperty("java.addModules", addModules);
        launch(args);
    }
}
