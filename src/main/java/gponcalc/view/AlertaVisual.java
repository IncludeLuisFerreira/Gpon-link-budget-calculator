package gponcalc.view;

import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import java.util.List;
import java.util.Map;

public class AlertaVisual {

    public static void aplicarAlertas(List<String> alertas, Map<String, Control> campoMap) {
        limparAlertas(campoMap);

        for (String alerta : alertas) {
            Control control = null;
            if (alerta.contains("Potência de transmissão")) {
                control = campoMap.get("potenciaTransmissao");
            } else if (alerta.contains("Sensibilidade do receptor")) {
                control = campoMap.get("sensibilidadeReceptor");
            } else if (alerta.contains("Distância do enlace")) {
                control = campoMap.get("distanciaEnlace");
            } else if (alerta.contains("Coeficiente de atenuação")) {
                control = campoMap.get("atenuacaoEspecifica");
            } else if (alerta.contains("Divisão de splitter")) {
                control = campoMap.get("divisaoSplitters");
            } else if (alerta.contains("Margem de segurança")) {
                control = campoMap.get("margemSeguranca");
            } else if (alerta.contains("Perda de conectores")) {
                control = campoMap.get("perdaConectores");
            } else if (alerta.contains("Perda de fusões")) {
                control = campoMap.get("perdaFusoes");
            }
            if (control != null) {
                control.setStyle("-fx-border-color: #c62828; -fx-border-width: 2px; -fx-background-color: #ffebee;");
                control.setTooltip(new Tooltip(alerta));
            }
        }
    }

    public static void limparAlertas(Map<String, Control> campoMap) {
        for (Control control : campoMap.values()) {
            control.setStyle(null);
            control.setTooltip(null);
        }
    }
}
