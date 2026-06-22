package gponcalc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import gponcalc.controller.MainController;
import gponcalc.model.CalculadoraLinkBudget;
import gponcalc.model.ValidadorLimites;
import gponcalc.view.InputPanel;
import gponcalc.view.ResultPanel;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        InputPanel inputPanel = new InputPanel();
        ResultPanel resultPanel = new ResultPanel();

        CalculadoraLinkBudget calculadora = new CalculadoraLinkBudget();
        ValidadorLimites validador = new ValidadorLimites();
        new MainController(inputPanel, resultPanel, calculadora, validador);

        root.setLeft(inputPanel);
        root.setCenter(resultPanel);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Calculadora de Link Budget GPON");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
