package gponcalc.model;

import java.util.ArrayList;
import java.util.List;

public class ValidadorLimites {
    public List<String> validar(ParametrosRede parametros) {
        List<String> alertas = new ArrayList<>();

        if (parametros.getPotenciaTransmissao() != null) {
            if (parametros.getPotenciaTransmissao() < 0 || parametros.getPotenciaTransmissao() > 10) {
                alertas.add("Potência de transmissão deve estar entre 0 e 10 dBm (ITU-T G.984)");
            }
        }

        if (parametros.getSensibilidadeReceptor() != null) {
            if (parametros.getSensibilidadeReceptor() < -30 || parametros.getSensibilidadeReceptor() > -8) {
                alertas.add("Sensibilidade do receptor deve estar entre -30 e -8 dBm (Classe B+)");
            }
        }

        if (parametros.getDistanciaEnlace() != null) {
            if (parametros.getDistanciaEnlace() <= 0 || parametros.getDistanciaEnlace() > 20) {
                alertas.add("Distância do enlace deve estar entre 0 e 20 km");
            }
        }

        if (parametros.getAtenuacaoEspecifica() != null) {
            if (parametros.getAtenuacaoEspecifica() < 0.25 || parametros.getAtenuacaoEspecifica() > 0.45) {
                alertas.add("Coeficiente de atenuação deve estar entre 0.25 e 0.45 dB/km (G.652)");
            }
        }

        if (parametros.getDivisaoSplitters() != null) {
            String validSplitters = "1:2,1:4,1:8,1:16,1:64";
            if (!validSplitters.contains(parametros.getDivisaoSplitters())) {
                alertas.add("Divisão de splitter inválida. Valores válidos: " + validSplitters);
            }
        }

        if (parametros.getMargemSeguranca() != null) {
            if (parametros.getMargemSeguranca() < 1 || parametros.getMargemSeguranca() > 6) {
                alertas.add("Margem de segurança deve estar entre 1 e 6 dB");
            }
        }

        if (parametros.getPerdaConectores() != null && parametros.getPerdaConectores() < 0) {
            alertas.add("Perda de conectores não pode ser negativa");
        }

        if (parametros.getPerdaFusoes() != null && parametros.getPerdaFusoes() < 0) {
            alertas.add("Perda de fusões não pode ser negativa");
        }

        return alertas;
    }
}
