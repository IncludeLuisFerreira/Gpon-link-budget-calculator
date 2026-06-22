package gponcalc;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import gponcalc.controller.WizardController;
import gponcalc.model.CalculadoraLinkBudget;
import gponcalc.model.GamificationEngine;
import gponcalc.view.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        showMenu(primaryStage);
    }

    private void showMenu(Stage stage) {
        VBox menuView = new VBox(20);
        menuView.setAlignment(Pos.CENTER);
        menuView.setStyle("-fx-background-color: #1a237e; -fx-padding: 40px;");

        Label title = new Label("\uD83D\uDCE1 GPON Link Budget");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 28px; -fx-font-weight: bold;");

        Label subtitle = new Label("Calculadora de Link Budget para redes GPON");
        subtitle.setStyle("-fx-text-fill: rgba(255,255,255,0.8); -fx-font-size: 14px;");

        Button btnIniciar = new Button("Iniciar C\u00E1lculo");
        btnIniciar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 12px 32px; -fx-background-radius: 8px; -fx-cursor: hand;");
        btnIniciar.setOnAction(e -> showCalculator(stage));

        menuView.getChildren().addAll(title, subtitle, btnIniciar);

        Scene scene = new Scene(menuView, 600, 400);
        scene.getStylesheets().add(getClass().getResource("/gponcalc/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("GPON Link Budget");
        stage.show();
    }

    private void showCalculator(Stage stage) {
        BorderPane root = new BorderPane();

        // Top bar
        HBox topBar = new HBox(8);
        topBar.setStyle("-fx-background-color: #1a237e; -fx-padding: 8px 16px; -fx-alignment: center-left;");
        Label appTitle = new Label("\uD83D\uDCE1 GPON Link Budget");
        appTitle.setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold;");
        Label appVersion = new Label("v2.0");
        appVersion.setStyle("-fx-text-fill: rgba(255,255,255,0.7); -fx-font-size: 11px;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Button btnMenu = new Button("\u2190 Menu");
        btnMenu.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-border-radius: 4px; -fx-padding: 4px 12px; -fx-cursor: hand;");
        btnMenu.setOnAction(e -> showMenu(stage));
        topBar.getChildren().addAll(appTitle, appVersion, spacer, btnMenu);

        // Wizard sidebar
        WizardSidebar sidebar = new WizardSidebar();

        // Step panels
        StepSinalPanel stepSinal = new StepSinalPanel();
        StepFibraPanel stepFibra = new StepFibraPanel();
        StepPerdasPanel stepPerdas = new StepPerdasPanel();
        StepResultadoPanel stepResultado = new StepResultadoPanel();

        // Content stack
        StackPane contentStack = new StackPane(
            stepSinal.getView(), stepFibra.getView(), 
            stepPerdas.getView(), stepResultado.getView()
        );
        for (int i = 1; i < 4; i++) {
            contentStack.getChildren().get(i).setVisible(false);
        }

        // Score sidebar
        ScoreSidebar scoreSidebar = new ScoreSidebar();

        // Controller
        CalculadoraLinkBudget calculadora = new CalculadoraLinkBudget();
        GamificationEngine engine = new GamificationEngine();
        WizardController controller = new WizardController(
            stepSinal, stepFibra, stepPerdas, stepResultado,
            sidebar, scoreSidebar, calculadora, engine
        );

        Label stepIndicator = new Label("Passo 1 de 4");
        stepIndicator.setStyle("-fx-font-size: 11px; -fx-text-fill: #999;");

        controller.setOnStepChanged(() -> {
            int step = controller.getCurrentStep();
            atualizarContent(contentStack, step);
            stepIndicator.setText("Passo " + (step + 1) + " de 4");
        });

        // Bottom nav
        HBox bottomNav = new HBox(16);
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setStyle("-fx-background-color: #fafafa; -fx-padding: 8px 16px; -fx-border-color: #e0e0e0; -fx-border-width: 1px 0 0 0;");
        Button btnVoltar = new Button("\u2190 Anterior");
        btnVoltar.setStyle("-fx-background-color: white; -fx-text-fill: #1565C0; -fx-border-color: #1565C0; -fx-border-width: 1.5px; -fx-font-size: 13px; -fx-padding: 8px 20px; -fx-background-radius: 6px; -fx-border-radius: 6px; -fx-cursor: hand;");
        btnVoltar.setOnAction(e -> controller.voltar());
        Button btnAvancar = new Button("Pr\u00F3ximo \u2192");
        btnAvancar.setStyle("-fx-background-color: #1565C0; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8px 20px; -fx-background-radius: 6px; -fx-cursor: hand;");
        btnAvancar.setOnAction(e -> controller.avancar());
        bottomNav.getChildren().addAll(btnVoltar, stepIndicator, btnAvancar);

        root.setTop(topBar);
        root.setLeft(sidebar);
        root.setCenter(contentStack);
        root.setRight(scoreSidebar);
        root.setBottom(bottomNav);

        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/gponcalc/style.css").toExternalForm());
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }
        stage.setTitle("GPON Link Budget - Calculadora");
    }

    private void atualizarContent(StackPane stack, int step) {
        for (int i = 0; i < stack.getChildren().size(); i++) {
            stack.getChildren().get(i).setVisible(i == step);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
