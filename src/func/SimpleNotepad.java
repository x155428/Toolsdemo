package src.func;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class SimpleNotepad extends Application {
    private Stage primaryStage;
    private TextArea textArea;
    private String currentFile;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Simple Notepad");
        textArea = new TextArea();
        BorderPane root = new BorderPane();
        root.setCenter(textArea);

        Menu fileMenu = new Menu("文件");
        MenuItem newItem = new MenuItem("新建");
        MenuItem openItem = new MenuItem("打开");
        MenuItem saveItem = new MenuItem("保存");
        MenuItem exitItem = new MenuItem("退出");
        fileMenu.getItems().addAll(newItem, openItem, saveItem, new SeparatorMenuItem(), exitItem);

        Menu editMenu = new Menu("编辑");
        MenuItem copyItem = new MenuItem("复制");
        MenuItem pasteItem = new MenuItem("粘贴");
        MenuItem cutItem = new MenuItem("剪切");
        editMenu.getItems().addAll(copyItem, pasteItem, cutItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu);
        root.setTop(menuBar);

        // 新建
        newItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.clear();
                currentFile = null;
            }
        });

        // 打开
        openItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if (selectedFile != null) {
                    currentFile = selectedFile.getAbsolutePath();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(currentFile));
                        String line;
                        while ((line = br.readLine()) != null) {
                            textArea.appendText(line + "\n");
                        }
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // 保存
        saveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (currentFile == null) {
                    FileChooser fileChooser = new FileChooser();
                    File selectedFile = fileChooser.showSaveDialog(primaryStage);
                    if (selectedFile != null) {
                        currentFile = selectedFile.getAbsolutePath();
                    }
                }
                if (currentFile != null) {
                    try {
                        FileWriter fw = new FileWriter(currentFile);
                        fw.write(textArea.getText());
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // 退出
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        // 复制
        copyItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                textArea.copy();
            }
        });
    }}