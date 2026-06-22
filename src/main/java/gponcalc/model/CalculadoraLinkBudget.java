package gponcalc.model;

import gponcalc.exception.ParametrosInsuficientesException;
import java.util.Map;

public class CalculadoraLinkBudget {

    private static final Map<String, Double> SPLITTER_LOSS = Map.of(
            "1:2", 3.0,
            "1:4", 6.0,
            "1:8", 9.0,
            "1:16", 12.0,
            "1:64", 18.0
    );

    public void calcularVariavelAusente(ParametrosRede parametros) {
        String campoAusente = encontrarCampoAusente(parametros);
        if (campoAusente == null) {
            throw new ParametrosInsuficientesException(
                    "Todos os campos estão preenchidos. Nenhuma variável para calcular.");
        }

        Double splitterLoss = obterSplitterLoss(parametros.getDivisaoSplitters());

        switch (campoAusente) {
            case "distanciaEnlace":
                double d = (parametros.getPotenciaTransmissao() - parametros.getSensibilidadeReceptor()
                        - parametros.getMargemSeguranca() - splitterLoss
                        - parametros.getPerdaConectores() - parametros.getPerdaFusoes())
                        / parametros.getAtenuacaoEspecifica();
                parametros.setDistanciaEnlace(d);
                break;
            case "potenciaTransmissao":
                double ptx = parametros.getSensibilidadeReceptor() + parametros.getMargemSeguranca()
                        + parametros.getAtenuacaoEspecifica() * parametros.getDistanciaEnlace()
                        + splitterLoss + parametros.getPerdaConectores()
                        + parametros.getPerdaFusoes();
                parametros.setPotenciaTransmissao(ptx);
                break;
            case "sensibilidadeReceptor":
                double s = parametros.getPotenciaTransmissao()
                        - parametros.getAtenuacaoEspecifica() * parametros.getDistanciaEnlace()
                        - splitterLoss - parametros.getPerdaConectores()
                        - parametros.getPerdaFusoes() - parametros.getMargemSeguranca();
                parametros.setSensibilidadeReceptor(s);
                break;
            case "atenuacaoEspecifica":
                double alpha = (parametros.getPotenciaTransmissao() - parametros.getSensibilidadeReceptor()
                        - parametros.getMargemSeguranca() - splitterLoss
                        - parametros.getPerdaConectores() - parametros.getPerdaFusoes())
                        / parametros.getDistanciaEnlace();
                parametros.setAtenuacaoEspecifica(alpha);
                break;
            case "perdaConectores":
                double lcon = parametros.getPotenciaTransmissao() - parametros.getSensibilidadeReceptor()
                        - parametros.getMargemSeguranca()
                        - parametros.getAtenuacaoEspecifica() * parametros.getDistanciaEnlace()
                        - splitterLoss - parametros.getPerdaFusoes();
                parametros.setPerdaConectores(lcon);
                break;
            case "perdaFusoes":
                double lfus = parametros.getPotenciaTransmissao() - parametros.getSensibilidadeReceptor()
                        - parametros.getMargemSeguranca()
                        - parametros.getAtenuacaoEspecifica() * parametros.getDistanciaEnlace()
                        - splitterLoss - parametros.getPerdaConectores();
                parametros.setPerdaFusoes(lfus);
                break;
            case "margemSeguranca":
                double m = parametros.getPotenciaTransmissao()
                        - parametros.getAtenuacaoEspecifica() * parametros.getDistanciaEnlace()
                        - splitterLoss - parametros.getPerdaConectores()
                        - parametros.getPerdaFusoes() - parametros.getSensibilidadeReceptor();
                parametros.setMargemSeguranca(m);
                break;
            case "divisaoSplitters":
                throw new ParametrosInsuficientesException(
                        "Não é possível calcular a divisão do splitter automaticamente");
        }
    }

    private String encontrarCampoAusente(ParametrosRede parametros) {
        String ausente = null;
        int count = 0;
        for (String campo : parametros.getCampos()) {
            if (parametros.isCampoVazio(campo)) {
                count++;
                ausente = campo;
            }
        }
        if (count != 1) {
            return null;
        }
        return ausente;
    }

    private Double obterSplitterLoss(String divisaoSplitters) {
        if (divisaoSplitters == null) return 0.0;
        return SPLITTER_LOSS.getOrDefault(divisaoSplitters, 0.0);
    }
}
