package gponcalc.controller;

import gponcalc.exception.ParametrosInsuficientesException;
import gponcalc.model.CalculadoraLinkBudget;
import gponcalc.model.ParametrosRede;
import gponcalc.model.ValidadorLimites;
import gponcalc.view.InputPanel;
import gponcalc.view.ResultPanel;

public class MainController {
    private final InputPanel inputPanel;
    private final ResultPanel resultPanel;
    private final CalculadoraLinkBudget calculadora;
    private final ValidadorLimites validador;

    public MainController(InputPanel inputPanel, ResultPanel resultPanel,
                          CalculadoraLinkBudget calculadora, ValidadorLimites validador) {
        this.inputPanel = inputPanel;
        this.resultPanel = resultPanel;
        this.calculadora = calculadora;
        this.validador = validador;
        registerHandlers();
    }

    private void registerHandlers() {
        inputPanel.getBtnCalcular().setOnAction(e -> handleCalcular());
    }

    private void handleCalcular() {
        try {
            resultPanel.limpar();
            ParametrosRede params = buildParametros();

            String campoFaltante = null;
            int camposVazios = 0;
            for (String campo : params.getCampos()) {
                if (!campo.equals("divisaoSplitters") && params.isCampoVazio(campo)) {
                    campoFaltante = campo;
                    camposVazios++;
                }
            }

            java.util.List<String> alertas = validador.validar(params);
            if (!alertas.isEmpty()) {
                resultPanel.exibirAlertas(alertas);
            }

            calculadora.calcularVariavelAusente(params);

            if (campoFaltante != null && camposVazios == 1) {
                Double valor = getValor(params, campoFaltante);
                resultPanel.exibirResultado(formatarNomeExibicao(campoFaltante), valor);
            }

            alertas = validador.validar(params);
            if (!alertas.isEmpty()) {
                resultPanel.exibirAlertas(alertas);
            }

        } catch (ParametrosInsuficientesException e) {
            resultPanel.exibirErro("Erro: " + e.getMessage());
        } catch (NumberFormatException e) {
            resultPanel.exibirErro("Erro: Verifique se todos os campos numéricos estão preenchidos corretamente.");
        }
    }

    private ParametrosRede buildParametros() {
        ParametrosRede params = new ParametrosRede();
        params.setPotenciaTransmissao(inputPanel.getPotenciaTransmissao());
        params.setSensibilidadeReceptor(inputPanel.getSensibilidadeReceptor());
        params.setDistanciaEnlace(inputPanel.getDistanciaEnlace());
        params.setAtenuacaoEspecifica(inputPanel.getAtenuacaoEspecifica());
        params.setDivisaoSplitters(inputPanel.getDivisaoSplitters());
        params.setPerdaConectores(inputPanel.getPerdaConectores());
        params.setPerdaFusoes(inputPanel.getPerdaFusoes());
        params.setMargemSeguranca(inputPanel.getMargemSeguranca());
        return params;
    }

    private Double getValor(ParametrosRede params, String nomeCampo) {
        switch (nomeCampo) {
            case "potenciaTransmissao": return params.getPotenciaTransmissao();
            case "sensibilidadeReceptor": return params.getSensibilidadeReceptor();
            case "distanciaEnlace": return params.getDistanciaEnlace();
            case "atenuacaoEspecifica": return params.getAtenuacaoEspecifica();
            case "perdaConectores": return params.getPerdaConectores();
            case "perdaFusoes": return params.getPerdaFusoes();
            case "margemSeguranca": return params.getMargemSeguranca();
            default: return 0.0;
        }
    }

    private String formatarNomeExibicao(String nomeCampo) {
        java.util.Map<String, String> labels = new java.util.HashMap<>();
        labels.put("potenciaTransmissao", "Potência de Transmissão (Ptx)");
        labels.put("sensibilidadeReceptor", "Sensibilidade do Receptor (S)");
        labels.put("distanciaEnlace", "Distância do Enlace (d)");
        labels.put("atenuacaoEspecifica", "Atenuação Específica (α)");
        labels.put("perdaConectores", "Perda em Conectores");
        labels.put("perdaFusoes", "Perda em Fusões");
        labels.put("margemSeguranca", "Margem de Segurança (M)");
        return labels.getOrDefault(nomeCampo, nomeCampo);
    }
}
