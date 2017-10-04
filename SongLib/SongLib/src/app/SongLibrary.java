package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.Controller;

public class SongLibrary extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/songLibGUI.fxml"));
            AnchorPane root =   (AnchorPane)loader.load();

            Controller listController =  loader.getController();
            listController.start(primaryStage);

            Scene scene = new Scene(root);

            primaryStage.setTitle("Song Library by EF338 and DRB197");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e){
            e.printStackTrace();
            return;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
