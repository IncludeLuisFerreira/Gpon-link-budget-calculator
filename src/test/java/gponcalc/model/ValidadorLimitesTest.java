package gponcalc.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ValidadorLimitesTest {

    private final ValidadorLimites validador = new ValidadorLimites();

    @Test
    void nullFieldsShouldReturnNoAlerts() {
        ParametrosRede params = new ParametrosRede();
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.isEmpty());
    }

    @Test
    void validPtxShouldNotProduceAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setPotenciaTransmissao(5.0);
        List<String> alertas = validador.validar(params);
        assertFalse(alertas.stream().anyMatch(a -> a.contains("Potência de transmissão")));
    }

    @Test
    void ptxBelowZeroShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setPotenciaTransmissao(-1.0);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Potência de transmissão")));
    }

    @Test
    void ptxAboveTenShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setPotenciaTransmissao(11.0);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Potência de transmissão")));
    }

    @Test
    void validSensibilidadeShouldNotProduceAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setSensibilidadeReceptor(-20.0);
        List<String> alertas = validador.validar(params);
        assertFalse(alertas.stream().anyMatch(a -> a.contains("Sensibilidade do receptor")));
    }

    @Test
    void sensibilidadeBelowMinus30ShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setSensibilidadeReceptor(-31.0);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Sensibilidade do receptor")));
    }

    @Test
    void sensibilidadeAboveMinus8ShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setSensibilidadeReceptor(-7.0);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Sensibilidade do receptor")));
    }

    @Test
    void validDistanceShouldNotProduceAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setDistanciaEnlace(10.0);
        List<String> alertas = validador.validar(params);
        assertFalse(alertas.stream().anyMatch(a -> a.contains("Distância do enlace")));
    }

    @Test
    void distanceZeroShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setDistanciaEnlace(0.0);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Distância do enlace")));
    }

    @Test
    void distanceAboveTwentyShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setDistanciaEnlace(21.0);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Distância do enlace")));
    }

    @Test
    void validAttenuationShouldNotProduceAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setAtenuacaoEspecifica(0.35);
        List<String> alertas = validador.validar(params);
        assertFalse(alertas.stream().anyMatch(a -> a.contains("Coeficiente de atenuação")));
    }

    @Test
    void attenuationBelow025ShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setAtenuacaoEspecifica(0.24);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Coeficiente de atenuação")));
    }

    @Test
    void attenuationAbove045ShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setAtenuacaoEspecifica(0.46);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Coeficiente de atenuação")));
    }

    @Test
    void validSplitterShouldNotProduceAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setDivisaoSplitters("1:8");
        List<String> alertas = validador.validar(params);
        assertFalse(alertas.stream().anyMatch(a -> a.contains("Divisão de splitter")));
    }

    @Test
    void invalidSplitterShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setDivisaoSplitters("1:3");
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Divisão de splitter")));
    }

    @Test
    void validMarginShouldNotProduceAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setMargemSeguranca(3.0);
        List<String> alertas = validador.validar(params);
        assertFalse(alertas.stream().anyMatch(a -> a.contains("Margem de segurança")));
    }

    @Test
    void marginBelowOneShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setMargemSeguranca(0.5);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Margem de segurança")));
    }

    @Test
    void marginAboveSixShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setMargemSeguranca(7.0);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Margem de segurança")));
    }

    @Test
    void positiveConnectorLossShouldNotAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setPerdaConectores(0.5);
        List<String> alertas = validador.validar(params);
        assertFalse(alertas.stream().anyMatch(a -> a.contains("Perda de conectores")));
    }

    @Test
    void negativeConnectorLossShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setPerdaConectores(-0.1);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Perda de conectores")));
    }

    @Test
    void positiveSpliceLossShouldNotAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setPerdaFusoes(0.1);
        List<String> alertas = validador.validar(params);
        assertFalse(alertas.stream().anyMatch(a -> a.contains("Perda de fusões")));
    }

    @Test
    void negativeSpliceLossShouldAlert() {
        ParametrosRede params = new ParametrosRede();
        params.setPerdaFusoes(-0.05);
        List<String> alertas = validador.validar(params);
        assertTrue(alertas.stream().anyMatch(a -> a.contains("Perda de fusões")));
    }
}
