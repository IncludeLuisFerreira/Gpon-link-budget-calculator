package gponcalc.view;

import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;
import java.util.List;
import java.util.Map;

public class AlertaVisual {

    private static final String ESTILO_ERRO = "-fx-border-color: #c62828; -fx-border-width: 2px; -fx-background-color: #ffebee;";
    private static final String ESTILO_AVISO = "-fx-border-color: #f9a825; -fx-border-width: 2px; -fx-background-color: #fff8e1;";
    private static final String ESTILO_NORMAL = "-fx-border-color: #bdbdbd; -fx-border-width: 1px; -fx-background-color: white;";

    public static void aplicarAlertas(List<String> alertas, Map<String, Control> campoMap) {
        for (Control control : campoMap.values()) {
            control.setStyle(ESTILO_NORMAL);
            control.setTooltip(null);
        }

        for (String alerta : alertas) {
            if (alerta.contains("Potência de transmissão")) {
                aplicarEstilo(campoMap.get("potenciaTransmissao"), ESTILO_ERRO, alerta);
            } else if (alerta.contains("Sensibilidade do receptor")) {
                aplicarEstilo(campoMap.get("sensibilidadeReceptor"), ESTILO_ERRO, alerta);
            } else if (alerta.contains("Distância do enlace")) {
                aplicarEstilo(campoMap.get("distanciaEnlace"), ESTILO_ERRO, alerta);
            } else if (alerta.contains("Coeficiente de atenuação")) {
                aplicarEstilo(campoMap.get("atenuacaoEspecifica"), ESTILO_ERRO, alerta);
            } else if (alerta.contains("Divisão de splitter")) {
                aplicarEstilo(campoMap.get("divisaoSplitters"), ESTILO_ERRO, alerta);
            } else if (alerta.contains("Margem de segurança")) {
                aplicarEstilo(campoMap.get("margemSeguranca"), ESTILO_ERRO, alerta);
            } else if (alerta.contains("Perda de conectores")) {
                aplicarEstilo(campoMap.get("perdaConectores"), ESTILO_ERRO, alerta);
            } else if (alerta.contains("Perda de fusões")) {
                aplicarEstilo(campoMap.get("perdaFusoes"), ESTILO_ERRO, alerta);
            }
        }
    }

    private static void aplicarEstilo(Control control, String estilo, String tooltipText) {
        if (control != null) {
            control.setStyle(estilo);
            control.setTooltip(new Tooltip(tooltipText));
        }
    }

    public static void limparAlertas(Map<String, Control> campoMap) {
        for (Control control : campoMap.values()) {
            control.setStyle(ESTILO_NORMAL);
            control.setTooltip(null);
        }
    }
}
