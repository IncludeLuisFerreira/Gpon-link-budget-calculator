package gponcalc.view;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ResultPanel extends VBox {
    private Label lblResultado;
    private ListView<String> listAlertas;

    public ResultPanel() {
        setSpacing(10);
        setPadding(new javafx.geometry.Insets(15));

        // Title
        Label title = new Label("Resultado do Cálculo");
        title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Result label (large, centered)
        lblResultado = new Label("Preencha os campos e clique em Calcular");
        lblResultado.setStyle("-fx-font-size: 14px;");
        lblResultado.setWrapText(true);

        // Alerts list
        Label alertTitle = new Label("Alertas:");
        alertTitle.setStyle("-fx-font-weight: bold;");
        listAlertas = new ListView<>();
        listAlertas.setPrefHeight(200);

        getChildren().addAll(title, lblResultado, alertTitle, listAlertas);
    }

    public void exibirResultado(String nomeVariavel, double valor) {
        lblResultado.setText(nomeVariavel + " = " + String.format("%.2f", valor));
        // Green style for success
        lblResultado.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
    }

    public void exibirErro(String mensagem) {
        lblResultado.setText(mensagem);
        lblResultado.setStyle("-fx-font-size: 14px; -fx-text-fill: #c62828;");
    }

    public void exibirAlertas(java.util.List<String> alertas) {
        listAlertas.getItems().clear();
        if (alertas != null) {
            listAlertas.getItems().addAll(alertas);
        }
    }

    public void limpar() {
        lblResultado.setText("Preencha os campos e clique em Calcular");
        lblResultado.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        listAlertas.getItems().clear();
    }
}
