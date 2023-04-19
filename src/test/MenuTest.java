package src.test;

//import cv2;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import src.container.ImgContainer;
import src.utils.showScanResult;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MultiResolutionImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

public class MenuTest extends Application {
    private VBox menu;  //左侧菜单容器
    BorderPane content;  //右侧内容区域
    Stage primary;    //主窗口
    Stage stage;    //截图蒙版新窗口
    //ImageView iv;//图片显示区域
    HBox view;//框选的红框显示
    TabPane ChoseImgPane;//图片展示区
    private double sceneX_start;
    private double sceneY_start;
    private double sceneX_end;
    private double sceneY_end;
    //截取图片宽高
    int imgw;
    int imgh;
    int index=0;//序号标识

    //菜单按钮
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;

    //截图底部工具按钮
    Button cutBtn;
    Button scanBtn;
    Button copyBtn;
    Button importBtn;
    Button editBtn;
    Button delBtn;
    Button clearBtn;
    Button saveBtn;
    Button openallbtn;

    //放图片的容器
    ImgContainer imgmap=new ImgContainer();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primary=primaryStage; //主窗口
        BorderPane root = new BorderPane(); //根容器

        // 创建左侧菜单栏
        menu = new VBox();
        menu.setPrefWidth(100);
        menu.setAlignment(Pos.TOP_LEFT);
        menu.setStyle("-fx-background-color: #87CEFA; -fx-padding: 5px;");
        Label title = new Label("功能");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-padding: 10px;");
        btn1 = new Button("截图扫描");
        btn1.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn7 = new Button("便签");
        btn7.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn3 = new Button("字符处理");
        btn3.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn4 = new Button("文本对比");
        btn4.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn5 = new Button("IP计算");
        btn5.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn2=new Button("记事本");
        btn2.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        btn6 = new Button("待添加");
        btn6.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");

        // 绑定按钮1点击事件
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

        menu.getChildren().addAll(title, btn1, btn2,btn7, btn3, btn4, btn5, btn6);
        root.setLeft(menu);

        // 创建中间内容区域
        content = new BorderPane();
        content.setStyle("-fx-background-color: #f4f4f4;");
        Label label = new Label("内容区域");
        label.setStyle("-fx-font-size: 24px;");
        content.getChildren().add(label);
        root.setCenter(content);

        //启动主程序
        Scene scene = new Scene(root, 800, 650);
        primaryStage.setScene(scene);
        primary.getIcons().add(new javafx.scene.image.Image("src/resource/img/悬空思考团子.png"));
        primaryStage.setTitle("工具demo");
        primaryStage.show();
        scene.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.ESCAPE){
                stage.close();
                primary.setIconified(false);
            }
        });

    }

    // 根据左侧点击切换右侧内容
    private void setContent(int menunumber) throws IOException {
        // 清空原内容
        System.out.println(menunumber);
        // 根据目录序号添加新内容
        switch (menunumber){
            case 1://加载截图界面
                //禁用按钮，清空组件
                btn1.setDisable(true);
                btn2.setDisable(false);
                btn3.setDisable(false);
                btn4.setDisable(false);
                btn5.setDisable(false);
                btn6.setDisable(false);
                content.getChildren().clear();
                cutBtn=new Button("截图");
                scanBtn=new Button("扫描");
                copyBtn=new Button("复制");
                importBtn=new Button("导入");
                editBtn=new Button("编辑");
                delBtn=new Button("删除");
                clearBtn=new Button("清空");
                saveBtn=new Button("保存");
                openallbtn=new Button("打开所有图片");

                editBtn.setDisable(true);
                openallbtn.setDisable(true);
                ToolBar BottonToolsBar=new ToolBar(cutBtn,scanBtn,copyBtn,importBtn,editBtn,delBtn,clearBtn,saveBtn,openallbtn);
                content.setBottom(BottonToolsBar);
                System.out.println(imgmap.imgcontainer.size());
                ChoseImgPane=new TabPane(); //添加选项卡面板

                //测试，打印选中的选项卡
                ChoseImgPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
                ChoseImgPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
                    if (newTab != null) {
                        System.out.println("选中的Tab的名称为：" + newTab.getText());
                    }
                });
                //监听tab,关闭tab时删除图片(容器对应值置空）
                ChoseImgPane.getTabs().addListener((ListChangeListener<Tab>) change -> {
                    while (change.next()){
                        if(change.wasRemoved()){
                            for(Tab tab:change.getRemoved()){
                                String s=tab.getText();
                                ChoseImgPane.getTabs().remove(tab);
                                System.out.println("删除了"+s);
                                imgmap.imgcontainer.put(s,null);
                                System.out.println(imgmap.imgcontainer.get(s));
                            }
                        }
                    }
                });

                //borderpane中间展示图片，下面工具栏不变
                content.setCenter(ChoseImgPane);

                //截图按钮
                cutBtn.setOnAction(actionEvent -> show());

                //扫描按钮(截图太小时无法扫描出来）
                scanBtn.setOnAction(actionEvent -> {
                    if(imgmap.imgcontainer.size()>0){
                        try {
                            // 加载图片
                            if(ChoseImgPane.getSelectionModel().getSelectedItem()!=null){
                                String ImgName=ChoseImgPane.getSelectionModel().getSelectedItem().getText();//获取当前tab名字，查找对应图片
                                System.out.println(ImgName);
                                BufferedImage img=imgmap.imgcontainer.get(ImgName);
                                // 创建二维码读取器
                                MultiFormatReader reader = new MultiFormatReader();
                                // 将图片转换为二进制位图
                                BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(img)));
                                // 解析二维码
                                Result result = reader.decode(binaryBitmap);
                                // 输出二维码内容
                                Clipboard clipboard = Clipboard.getSystemClipboard();
                                ClipboardContent content = new ClipboardContent();
                                content.putString(result.getText());
                                clipboard.setContent(content);
                                System.out.println(result.getText());
                                //弹窗提示
                                //showScanResult showresult=new showScanResult(result.getText());
                                new showScanResult(result.getText());

                            }else{
                                System.out.println("没有选择图片！");
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            // 当解码失败时，将图片清晰化处理，修复二维码再扫描
                            System.out.println("解码失败，开始二维码修复...");
                            String ImgName=ChoseImgPane.getSelectionModel().getSelectedItem().getText();
                            BufferedImage img=imgmap.imgcontainer.get(ImgName);
                            int imgwidth=img.getWidth();
                            int imgheight=img.getHeight();
/*
                            Mat qrCodeMat = new Mat(qrCodeHeight, qrCodeWidth, CvType.CV_8UC3);
                            qrCodeMat.depth = CvType.CV_8U;
                            qrCodeMat.setTo(0);

                            // QRCode.create 函数的实现
                            Mat qrCodeMatCopy = new Mat(qrCodeHeight, qrCodeWidth, CvType.CV_8UC3);
                            qrCodeMat.copyTo(qrCodeMatCopy);
                            QRCode qrCode = new QRCode(qrCodeMatCopy);

                            // 修复 QRCode 图像
                            Mat defectsMat = new Mat();
                            qrCode.getDefects(defectsMat);
                            for (int i = 0; i < defectsMat.rows(); i++) {
                                for (int j = 0; j < defectsMat.cols(); j++) {
                                    if (defectsMat.at<uchar>(i, j) > 0) {
                                        qrCodeMat.at<uchar>(i, j) = 255;
                                    }
                                }
                            }

 */

                            //ConvolutionFilter filter = new ConvolutionFilter(ConvolutionFilter.Type.KERNEL8, ConvolutionFilter.Mode.AVERAGE);
                           // Enhancer enhancer = new Enhancer();
                           // enhancer.setStrength(0.8f);
                            //image = enhancer.enhance(image, filter);
                            /*image = QRCodeUtils.repairQRCode(image);
                            LuminanceSource source = new RGBLuminanceSource(image);
                            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                            Reader reader = new MultiFormatReader();
                            try {
                                Result result = reader.decode(bitmap);
                                System.out.println("修复后二维码内容：" + result.getText());
                            } catch (NotFoundException e1) {
                                e1.printStackTrace();
                            }*/



                        }
                    }else {
                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("提示！");
                        alert.setHeaderText("");
                        alert.setContentText("没有图片！");
                        alert.getButtonTypes().remove(ButtonType.CANCEL);
                        alert.showAndWait();
                    }
                });

                //复制按钮
                copyBtn.setOnAction(actionEvent -> {
                    if(imgmap.imgcontainer.size()>0){
                        String ImgName=ChoseImgPane.getSelectionModel().getSelectedItem().getText();//获取当前tab名字，查找对应图片
                        BufferedImage img=imgmap.imgcontainer.get(ImgName);
                        // 获取系统剪切板
                        Clipboard clipboard = Clipboard.getSystemClipboard();
                        ClipboardContent content = new ClipboardContent();
                        content.putImage(SwingFXUtils.toFXImage(img,null));
                        clipboard.setContent(content);
                    }else {
                        Alert noimgAlert=new Alert(Alert.AlertType.CONFIRMATION);
                        noimgAlert.setTitle("提示！");
                        noimgAlert.setHeaderText("");
                        noimgAlert.setContentText("没有图片");
                        noimgAlert.getButtonTypes().remove(ButtonType.CANCEL);
                        noimgAlert.showAndWait();

                    }



                });

                //导入按钮
                importBtn.setOnAction(actionEvent -> {
                    FileChooser file_image_chose=new FileChooser();
                    file_image_chose.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
                    File file_img=file_image_chose.showOpenDialog(primary);
                    if(file_img!=null){
                        try {
                            BufferedImage tmpbufferedimage=ImageIO.read(file_img);
                            WritableImage writableimg= SwingFXUtils.toFXImage(tmpbufferedimage,null);
                            index++;
                            String tmpbufferedimageName="图片"+index;
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

                //编辑按钮（暂未实现）
                editBtn.setOnAction(actionEvent -> {

                });

                //删除按钮
                delBtn.setOnAction(actionEvent -> {
                    //删除容器中图片，关闭tab，
                    String ImgName=ChoseImgPane.getSelectionModel().getSelectedItem().getText();
                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("提示！");
                    alert.setHeaderText("");
                    alert.setContentText("已删除"+ImgName+"，此删除不会删除本地文件。");
                    alert.getButtonTypes().remove(ButtonType.CANCEL);
                    ChoseImgPane.getTabs().remove(ChoseImgPane.getSelectionModel().getSelectedItem());
                    alert.showAndWait();

                    imgmap.imgcontainer.put(ImgName,null);
                });

                //清空按钮
                clearBtn.setOnAction(actionEvent -> {
                    if(imgmap.imgcontainer.size()>0){
                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("警告！");
                        alert.setHeaderText("");
                        alert.setContentText("确定要清空吗？清空会删除所有图片！");
                        Optional<ButtonType> result=alert.showAndWait();
                        if(result.get()==ButtonType.OK){
                            imgmap.imgcontainer.clear();
                            index=0;
                            ChoseImgPane.getTabs().clear();
                        }
                    }else{
                        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("提示！");
                        alert.setHeaderText("");
                        alert.setContentText("没有图片可删除！");
                        alert.getButtonTypes().remove(ButtonType.CANCEL);
                        alert.showAndWait();
                    }

                });

                //保存按钮(待完善)
                saveBtn.setOnAction(actionEvent -> {
                    if(imgmap.imgcontainer.size()>0){
                        //获得当前选中的tab,找到tab名字，从img容器中查到对应图片保存
                        String ImgName=ChoseImgPane.getSelectionModel().getSelectedItem().getText();
                        System.out.println("保存图片："+ImgName);
                        BufferedImage selectTabimg=imgmap.imgcontainer.get(ImgName);
                        FileChooser filechoose=new FileChooser();
                        filechoose.setTitle("保存图片");
                        FileChooser.ExtensionFilter extfilter=new FileChooser.ExtensionFilter("图片（*.jpg)","*.png","*.gif");
                        filechoose.getExtensionFilters().add(extfilter);
                        File imgfile=filechoose.showSaveDialog(primary);
                        String filepath=imgfile.getAbsolutePath();
                        //判断文件保存类型，文件后缀
                        // if(filepath)
                        try {
                            ImageIO.write(selectTabimg,"png",imgfile);
                            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("保存提示！");
                            alert.setHeaderText("");
                            alert.getButtonTypes().remove(ButtonType.CANCEL);
                            alert.setContentText("保存成功，文件保存在："+filepath);
                            alert.showAndWait();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                //打开所有图片（暂未实现）
                openallbtn.setOnAction(actionEvent -> {

                });
                break;
            case 2:
                System.out.println("记事本");
                btn2.setDisable(true);
                btn1.setDisable(false);
                btn3.setDisable(false);
                btn4.setDisable(false);
                btn5.setDisable(false);
                btn6.setDisable(false);
                content.getChildren().clear();
                imgmap.imgcontainer.clear();
                index=0;
                //隐藏主窗口
                primary.setIconified(true);
                //新建新场景,加载记事本窗口
                Stage notepadStage=new Stage();
                Parent root= FXMLLoader.load(getClass().getResource("/src/resource/FXML/NoteBook.fxml"));
                Scene notebookScene=new Scene(root,600,400);
                notepadStage.setScene(notebookScene);
                notepadStage.show();
                notebookScene.setOnKeyPressed(event -> {
                    if(event.getCode()== KeyCode.ESCAPE){
                        notepadStage.close();
                        primary.setIconified(false);
                    }
                });
                break;
            case 3:
                Label label3 = new Label("3");
                label3.setStyle("-fx-font-size: 24px;");
                content.getChildren().add(label3);
                break;
            case 4:
                Label label4 = new Label("4");
                label4.setStyle("-fx-font-size: 24px;");
                content.getChildren().add(label4);
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

    //截图后添加到展示区，添加tabpane
    private void addTab() {

    }

    private void show() {
        //点击按钮，主窗口缩小
        primary.setIconified(true);
        //创建新窗口
        stage=new Stage();
        //创建蒙版，盖住全屏
        AnchorPane newpane=new AnchorPane();
        newpane.setStyle("-fx-background-color: #B5B5B522");//蒙版透明
        Scene scene=new Scene(newpane);
        scene.setFill(Paint.valueOf("#ffffff00"));//场景透明
        stage.setFullScreenExitHint("");//清空蒙版内容
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        drag(newpane);

        //监听场景，按esc关闭新窗口，显示旧窗口
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
            //左上角标
            Label label=new Label();
            label.setAlignment(Pos.CENTER);
            label.setPrefWidth(100);
            label.setPrefHeight(30);
            label.setText("宽:"+width+","+"高:"+height);
            AnchorPane.setLeftAnchor(label,sceneX_start);
            AnchorPane.setTopAnchor(label,sceneY_start-label.getPrefHeight());
            label.setTextFill(Paint.valueOf("#ffffff"));
            label.setStyle("-fx-background-color: #000000");
            an.getChildren().add(label);
        });
        an.setOnMouseDragExited(event -> {
            Button truebtn=new Button("确认");
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

    //选区确定后处理图片
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
        //BufferedImage buffimg=robot.createScreenCapture(rec);
        //生成图片名字，加入图片容器
        index++;
        String tmpimgname="图片"+index;
        System.out.println(tmpimgname);
        imgmap.imgIn(tmpimgname,buffimg);
        imgmap.showmapnum();
        WritableImage wi= SwingFXUtils.toFXImage(buffimg,null);

        File imgfile=new File("d://1111.png");
        ImageIO.write(buffimg,"png",imgfile);

        //新建tab显示图片
        Tab tmptab=new Tab(imgmap.getmapkey(index));
        System.out.println(tmptab.getText());
        ImageView iv=new ImageView();
        iv.setImage(wi);
        //限定imageview宽高，等比缩放
        iv.setFitWidth(680);
        iv.setFitHeight(580);
        iv.setPreserveRatio(true);
        tmptab.setContent(iv);
        ChoseImgPane.getTabs().add(tmptab);
        //获取tabpane模型，将新添加的tab设为选中状态
        SingleSelectionModel<Tab> selectionModel = ChoseImgPane.getSelectionModel();
        selectionModel.select(tmptab);
        primary.setIconified(false);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
