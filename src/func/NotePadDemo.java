package src.func;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class NotePadDemo extends Application {

    private TextArea textArea;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // 创建顶部工具栏
        HBox toolbar = new HBox();
        toolbar.setPadding(new Insets(10, 10, 10, 10));
        toolbar.setSpacing(10);
        toolbar.setStyle("-fx-background-color: #333333;");
        Label title = new Label("NotePad");
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Button saveBtn = new Button("Save");
        saveBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        saveBtn.setOnAction(event -> saveNote());
        Button clearBtn = new Button("Clear");
        clearBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ffffff;");
        clearBtn.setOnAction(event -> clearNote());
        toolbar.getChildren().addAll(title, saveBtn, clearBtn);
        root.setTop(toolbar);

        // 创建中间内容区域
        StackPane content = new StackPane();
        content.setStyle("-fx-background-color: #f4f4f4;");
        textArea = new TextArea();
        textArea.setStyle("-fx-font-size: 18px;");
        content.getChildren().add(textArea);
        root.setCenter(content);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("NotePad Demo");
        primaryStage.show();
    }

    // 保存便签内容
    private void saveNote() {
        System.out.println("Save button is clicked!");
        // TODO: 实现保存便签内容的代码
    }

    // 清空便签内容
    private void clearNote() {
        textArea.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

