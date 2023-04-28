package src.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class test extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SplitPane splitPane = new SplitPane();
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();

        // Create TextArea for first ScrollPane
        TextArea textArea1 = new TextArea();
        textArea1.setWrapText(true);
        textArea1.setText("This is a sample text area for the first scroll pane. It will adapt to the size of the pane and always fill it.");

        // Create ScrollPane for first VBox
        ScrollPane scrollPane1 = new ScrollPane();
        scrollPane1.setContent(textArea1);
        scrollPane1.setFitToWidth(true);
        scrollPane1.setFitToHeight(true);

        // Create TextArea for second ScrollPane
        TextArea textArea2 = new TextArea();
        textArea2.setWrapText(true);
        textArea2.setText("This is a sample text area for the second scroll pane. It will adapt to the size of the pane and always fill it.");

        // Create ScrollPane for second VBox
        ScrollPane scrollPane2 = new ScrollPane();
        scrollPane2.setContent(textArea2);
        scrollPane2.setFitToWidth(true);
        scrollPane2.setFitToHeight(true);

        // Wrap ScrollPane in AnchorPane to keep it in place
        AnchorPane anchorPane1 = new AnchorPane(scrollPane1);
        AnchorPane.setBottomAnchor(scrollPane1, 0.0);
        AnchorPane.setTopAnchor(scrollPane1, 0.0);
        AnchorPane.setLeftAnchor(scrollPane1, 0.0);
        AnchorPane.setRightAnchor(scrollPane1, 0.0);

        AnchorPane anchorPane2 = new AnchorPane(scrollPane2);
        AnchorPane.setBottomAnchor(scrollPane2, 0.0);
        AnchorPane.setTopAnchor(scrollPane2, 0.0);
        AnchorPane.setLeftAnchor(scrollPane2, 0.0);
        AnchorPane.setRightAnchor(scrollPane2, 0.0);

        // Add AnchorPanes to VBoxes
        vBox1.getChildren().add(anchorPane1);
        vBox2.getChildren().add(anchorPane2);

        // Add VBoxes to SplitPane
        splitPane.getItems().addAll(vBox1, vBox2);

        Scene scene = new Scene(splitPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
