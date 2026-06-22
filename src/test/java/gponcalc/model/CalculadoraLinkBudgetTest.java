package gponcalc.model;

import gponcalc.exception.ParametrosInsuficientesException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculadoraLinkBudgetTest {

    private final CalculadoraLinkBudget calculadora = new CalculadoraLinkBudget();

    @Test
    void shouldCalculateDistanceWhenMissing() {
        ParametrosRede params = new ParametrosRede();
        params.setPotenciaTransmissao(5.0);       // dBm
        params.setSensibilidadeReceptor(-27.0);    // dBm
        params.setAtenuacaoEspecifica(0.35);       // dB/km
        params.setDivisaoSplitters("1:8");         // 9 dB loss
        params.setPerdaConectores(0.5);            // dB
        params.setPerdaFusoes(0.1);                // dB
        params.setMargemSeguranca(3.0);            // dB
        
        calculadora.calcularVariavelAusente(params);
        
        // Ptx - (α*d + splitter + con + fus) = S + M
        // 5 - (0.35*d + 9 + 0.5 + 0.1) = -27 + 3
        // 5 - 0.35d - 9.6 = -24
        // -0.35d - 4.6 = -24
        // -0.35d = -19.4
        // d = 55.428... km
        assertNotNull(params.getDistanciaEnlace());
        assertEquals(55.43, params.getDistanciaEnlace(), 0.01);
    }

    @Test
    void shouldCalculatePtxWhenMissing() {
        ParametrosRede params = new ParametrosRede();
        params.setSensibilidadeReceptor(-27.0);
        params.setDistanciaEnlace(10.0);
        params.setAtenuacaoEspecifica(0.35);
        params.setDivisaoSplitters("1:8");
        params.setPerdaConectores(0.5);
        params.setPerdaFusoes(0.1);
        params.setMargemSeguranca(3.0);
        
        calculadora.calcularVariavelAusente(params);
        
        // Ptx = S + M + α*d + splitter + con + fus
        // Ptx = -27 + 3 + 0.35*10 + 9 + 0.5 + 0.1
        // Ptx = -24 + 3.5 + 9.6 = -10.9
        assertNotNull(params.getPotenciaTransmissao());
        assertEquals(-10.9, params.getPotenciaTransmissao(), 0.01);
    }

    @Test
    void shouldThrowExceptionWhenMultipleFieldsEmpty() {
        ParametrosRede params = new ParametrosRede();
        params.setPotenciaTransmissao(5.0);
        // Both distanciaEnlace AND sensibilidadeReceptor are null
        
        assertThrows(ParametrosInsuficientesException.class, () -> {
            calculadora.calcularVariavelAusente(params);
        });
    }

    @Test
    void shouldThrowExceptionWhenNoFieldEmpty() {
        ParametrosRede params = new ParametrosRede();
        params.setPotenciaTransmissao(5.0);
        params.setSensibilidadeReceptor(-27.0);
        params.setDistanciaEnlace(10.0);
        params.setAtenuacaoEspecifica(0.35);
        params.setDivisaoSplitters("1:8");
        params.setPerdaConectores(0.5);
        params.setPerdaFusoes(0.1);
        params.setMargemSeguranca(3.0);
        
        assertThrows(ParametrosInsuficientesException.class, () -> {
            calculadora.calcularVariavelAusente(params);
        });
    }

    @Test
    void shouldCalculateReceiverSensitivityWhenMissing() {
        ParametrosRede params = new ParametrosRede();
        params.setPotenciaTransmissao(5.0);
        params.setDistanciaEnlace(10.0);
        params.setAtenuacaoEspecifica(0.35);
        params.setDivisaoSplitters("1:8");
        params.setPerdaConectores(0.5);
        params.setPerdaFusoes(0.1);
        params.setMargemSeguranca(3.0);
        
        calculadora.calcularVariavelAusente(params);
        
        // S = Ptx - α*d - splitter - con - fus - M
        // S = 5 - 3.5 - 9 - 0.5 - 0.1 - 3 = -11.1
        assertNotNull(params.getSensibilidadeReceptor());
        assertEquals(-11.1, params.getSensibilidadeReceptor(), 0.01);
    }

    @Test
    void shouldCalculateSplitterRatioPreservesValueWhenNotMissing() {
        // When splitter is provided and another field is missing, calculation should succeed
        ParametrosRede params = new ParametrosRede();
        params.setPotenciaTransmissao(5.0);
        params.setSensibilidadeReceptor(-27.0);
        params.setDistanciaEnlace(10.0);
        params.setAtenuacaoEspecifica(0.35);
        params.setDivisaoSplitters("1:16");  // 12 dB
        params.setPerdaConectores(0.5);
        params.setPerdaFusoes(0.1);
        // margemSeguranca is null → should be calculated
        
        calculadora.calcularVariavelAusente(params);
        
        // M = Ptx - α*d - splitter - con - fus - S
        // M = 5 - 3.5 - 12 - 0.5 - 0.1 - (-27)
        // M = 5 - 3.5 - 12 - 0.5 - 0.1 + 27 = 15.9
        assertNotNull(params.getMargemSeguranca());
        assertEquals(15.9, params.getMargemSeguranca(), 0.01);
    }
}
