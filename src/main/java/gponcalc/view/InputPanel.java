package gponcalc.view;

import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;

public class InputPanel extends GridPane {

    private TextField txtPotenciaTransmissao;
    private TextField txtSensibilidadeReceptor;
    private TextField txtDistanciaEnlace;
    private TextField txtAtenuacaoEspecifica;
    private ComboBox<String> cmbDivisaoSplitters;
    private TextField txtPerdaConectores;
    private TextField txtPerdaFusoes;
    private TextField txtMargemSeguranca;
    private Button btnCalcular;

    public InputPanel() {
        setHgap(10);
        setVgap(8);
        setPadding(new Insets(15));

        txtPotenciaTransmissao = createNumericField();
        txtSensibilidadeReceptor = createNumericField();
        txtDistanciaEnlace = createNumericField();
        txtAtenuacaoEspecifica = createNumericField();
        cmbDivisaoSplitters = new ComboBox<>(
                FXCollections.observableArrayList("1:2", "1:4", "1:8", "1:16", "1:64")
        );
        cmbDivisaoSplitters.getSelectionModel().selectFirst();
        txtPerdaConectores = createNumericField();
        txtPerdaFusoes = createNumericField();
        txtMargemSeguranca = createNumericField();

        addRow(0, new Label("Potência do Transmissor (dBm):"), txtPotenciaTransmissao);
        addRow(1, new Label("Sensibilidade do Receptor (dBm):"), txtSensibilidadeReceptor);
        addRow(2, new Label("Distância do Enlace (km):"), txtDistanciaEnlace);
        addRow(3, new Label("Atenuação Específica (dB/km):"), txtAtenuacaoEspecifica);
        addRow(4, new Label("Divisão dos Splitters:"), cmbDivisaoSplitters);
        addRow(5, new Label("Perda nos Conectores (dB):"), txtPerdaConectores);
        addRow(6, new Label("Perda nas Fusões (dB):"), txtPerdaFusoes);
        addRow(7, new Label("Margem de Segurança (dB):"), txtMargemSeguranca);

        btnCalcular = new Button("Calcular");
        add(btnCalcular, 1, 8);
    }

    private static TextField createNumericField() {
        TextField field = new TextField();
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) return change;
            if (newText.equals("-")) return change;
            if (newText.matches("-?\\d*\\.?\\d*")) return change;
            return null;
        };
        field.setTextFormatter(new TextFormatter<>(filter));
        return field;
    }

    public TextField getTxtPotenciaTransmissao() {
        return txtPotenciaTransmissao;
    }

    public TextField getTxtSensibilidadeReceptor() {
        return txtSensibilidadeReceptor;
    }

    public TextField getTxtDistanciaEnlace() {
        return txtDistanciaEnlace;
    }

    public TextField getTxtAtenuacaoEspecifica() {
        return txtAtenuacaoEspecifica;
    }

    public ComboBox<String> getCmbDivisaoSplitters() {
        return cmbDivisaoSplitters;
    }

    public TextField getTxtPerdaConectores() {
        return txtPerdaConectores;
    }

    public TextField getTxtPerdaFusoes() {
        return txtPerdaFusoes;
    }

    public TextField getTxtMargemSeguranca() {
        return txtMargemSeguranca;
    }

    public Button getBtnCalcular() {
        return btnCalcular;
    }

    public Double getPotenciaTransmissao() {
        return parseDouble(txtPotenciaTransmissao.getText());
    }

    public Double getSensibilidadeReceptor() {
        return parseDouble(txtSensibilidadeReceptor.getText());
    }

    public Double getDistanciaEnlace() {
        return parseDouble(txtDistanciaEnlace.getText());
    }

    public Double getAtenuacaoEspecifica() {
        return parseDouble(txtAtenuacaoEspecifica.getText());
    }

    public String getDivisaoSplitters() {
        return cmbDivisaoSplitters.getValue();
    }

    public Double getPerdaConectores() {
        return parseDouble(txtPerdaConectores.getText());
    }

    public Double getPerdaFusoes() {
        return parseDouble(txtPerdaFusoes.getText());
    }

    public Double getMargemSeguranca() {
        return parseDouble(txtMargemSeguranca.getText());
    }

    private static Double parseDouble(String text) {
        if (text == null || text.trim().isEmpty()) return null;
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
