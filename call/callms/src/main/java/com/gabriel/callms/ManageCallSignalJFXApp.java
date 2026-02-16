package com.gabriel.callms;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ManageCallSignalJFXApp extends Application {
@Override
	public void start(Stage stage) throws IOException {
		System.out.println("SplashApp:start ");
		FXMLLoader fxmlLoader = new FXMLLoader(ManageCallSignalJFXApp.class.getResource("splash-view.fxml"));
		Parent root = (Parent)fxmlLoader.load();
		SplashViewController splashViewController= fxmlLoader.getController();
		splashViewController.setStage(stage);
		Scene scene = new Scene(root, 420, 420);
		splashViewController.setSplashScene(scene);
		stage.setTitle("CallSignal Management!");
		stage.setScene(scene);
		stage.show();
	}
}
