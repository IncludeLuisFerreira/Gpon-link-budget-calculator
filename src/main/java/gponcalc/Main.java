package gponcalc;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Label inputLabel = new Label("Painel de Entrada");
        inputLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(inputLabel, Pos.CENTER);
        root.setLeft(inputLabel);

        Label resultLabel = new Label("Resultados");
        resultLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(resultLabel, Pos.CENTER);
        root.setCenter(resultLabel);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Calculadora de Link Budget GPON");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
