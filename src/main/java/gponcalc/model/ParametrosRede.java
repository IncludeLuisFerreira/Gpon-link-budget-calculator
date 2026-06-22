package gponcalc.model;

import java.util.ArrayList;
import java.util.List;

public class ParametrosRede {

    private Double potenciaTransmissao;
    private Double sensibilidadeReceptor;
    private Double distanciaEnlace;
    private Double atenuacaoEspecifica;
    private String divisaoSplitters;
    private Double perdaConectores;
    private Double perdaFusoes;
    private Double margemSeguranca;

    public ParametrosRede() {
    }

    public ParametrosRede(Double potenciaTransmissao, Double sensibilidadeReceptor,
                          Double distanciaEnlace, Double atenuacaoEspecifica,
                          String divisaoSplitters, Double perdaConectores,
                          Double perdaFusoes, Double margemSeguranca) {
        this.potenciaTransmissao = potenciaTransmissao;
        this.sensibilidadeReceptor = sensibilidadeReceptor;
        this.distanciaEnlace = distanciaEnlace;
        this.atenuacaoEspecifica = atenuacaoEspecifica;
        this.divisaoSplitters = divisaoSplitters;
        this.perdaConectores = perdaConectores;
        this.perdaFusoes = perdaFusoes;
        this.margemSeguranca = margemSeguranca;
    }

    public Double getPotenciaTransmissao() {
        return potenciaTransmissao;
    }

    public void setPotenciaTransmissao(Double potenciaTransmissao) {
        this.potenciaTransmissao = potenciaTransmissao;
    }

    public Double getSensibilidadeReceptor() {
        return sensibilidadeReceptor;
    }

    public void setSensibilidadeReceptor(Double sensibilidadeReceptor) {
        this.sensibilidadeReceptor = sensibilidadeReceptor;
    }

    public Double getDistanciaEnlace() {
        return distanciaEnlace;
    }

    public void setDistanciaEnlace(Double distanciaEnlace) {
        this.distanciaEnlace = distanciaEnlace;
    }

    public Double getAtenuacaoEspecifica() {
        return atenuacaoEspecifica;
    }

    public void setAtenuacaoEspecifica(Double atenuacaoEspecifica) {
        this.atenuacaoEspecifica = atenuacaoEspecifica;
    }

    public String getDivisaoSplitters() {
        return divisaoSplitters;
    }

    public void setDivisaoSplitters(String divisaoSplitters) {
        this.divisaoSplitters = divisaoSplitters;
    }

    public Double getPerdaConectores() {
        return perdaConectores;
    }

    public void setPerdaConectores(Double perdaConectores) {
        this.perdaConectores = perdaConectores;
    }

    public Double getPerdaFusoes() {
        return perdaFusoes;
    }

    public void setPerdaFusoes(Double perdaFusoes) {
        this.perdaFusoes = perdaFusoes;
    }

    public Double getMargemSeguranca() {
        return margemSeguranca;
    }

    public void setMargemSeguranca(Double margemSeguranca) {
        this.margemSeguranca = margemSeguranca;
    }

    public boolean isCampoVazio(String nomeCampo) {
        switch (nomeCampo) {
            case "potenciaTransmissao":
                return potenciaTransmissao == null;
            case "sensibilidadeReceptor":
                return sensibilidadeReceptor == null;
            case "distanciaEnlace":
                return distanciaEnlace == null;
            case "atenuacaoEspecifica":
                return atenuacaoEspecifica == null;
            case "divisaoSplitters":
                return divisaoSplitters == null;
            case "perdaConectores":
                return perdaConectores == null;
            case "perdaFusoes":
                return perdaFusoes == null;
            case "margemSeguranca":
                return margemSeguranca == null;
            default:
                throw new IllegalArgumentException("Campo desconhecido: " + nomeCampo);
        }
    }

    public List<String> getCampos() {
        List<String> campos = new ArrayList<>();
        campos.add("potenciaTransmissao");
        campos.add("sensibilidadeReceptor");
        campos.add("distanciaEnlace");
        campos.add("atenuacaoEspecifica");
        campos.add("divisaoSplitters");
        campos.add("perdaConectores");
        campos.add("perdaFusoes");
        campos.add("margemSeguranca");
        return campos;
    }
}
