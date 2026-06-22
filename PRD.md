# Product Requirement Document (PRD) — Calculadora de Link Budget GPON

Este documento especifica os requisitos de produto, especificações técnicas e arquitetura para o desenvolvimento da Calculadora de Link Budget GPON, em conformidade com as diretrizes do documento **Interdisciplinar GPON 2026.pdf**.

---

## 1. Visão Geral do Produto

### 1.1. Contexto do Desafio

O projeto constitui um desafio interdisciplinar que integra os conceitos de Propagação de Ondas Eletromagnéticas e Engenharia de Software. O escopo exige a tradução das leis físicas de atenuação e propagação de sinal óptico em uma lógica de software flexível e desacoplada, acompanhada de uma interface gráfica amigável para o usuário.

### 1.2. Objetivo do Sistema

O objetivo principal é construir uma ferramenta capaz de calcular o equilíbrio de potência de um enlace óptico (Link Budget) em redes GPON (Gigabit Passive Optical Network). O software deve simular o balanço de potências tanto no fluxo de **downstream** quanto no de **upstream**, garantindo que o sinal emitido pela OLT chegue à ONU (e vice-versa) com intensidade elegível pelo receptor, respeitando as perdas impostas pelos componentes passivos.

---

## 2. Requisitos Funcionais (RF)

* **RF01 — Resolução de Variável Faltante (Arquitetura Não Linear):** O sistema não deve processar os dados de forma estritamente linear. A lógica computacional deve tratar a equação do Link Budget como um sistema coordenado: o usuário poderá deixar exatamente um campo em branco na interface, e o software terá o papel de identificar o campo vazio, isolar matematicamente a variável ausente e computar o seu valor real.
* **RF02 — Entrada Flexível de Parâmetros Técnicos:** O sistema deve aceitar a inserção manual dos seguintes dados de projeto por meio da interface gráfica:
* Potência de Transmissão ($P_{tx}$) em dBm.
* Sensibilidade do Receptor ($S$) em dBm.
* Distância do enlace ($d$) em quilômetros (km).
* Coeficiente de atenuação específica da fibra óptica ($\alpha$) em dB/km.
* Quantidade e razão de divisão dos Splitters (ex: 1:2, 1:4, 1:8, 1:16, 1:64).
* Quantidade e perda unitária de Conectores ($N_{\text{con}}$) e Fusões ($N_{\text{fus}}$).
* Margem de segurança do projeto ($M$) em dB.


* **RF03 — Sistema Automatizado de Alertas:** O software deve validar os dados inseridos em tempo real. Caso algum parâmetro ou resultado calculado extrapole as características práticas de mercado ou os limites estipulados pelas normas técnicas (como a ITU-T G.984), a aplicação deve disparar alertas visuais contextuais na interface gráfica.
* **RF04 — Tratamento de Exceções Robustas:** Se o usuário não fornecer dados suficientes para solucionar a equação (por exemplo, deixando dois ou mais campos vazios), o software deve capturar a inconsistência e retornar uma mensagem de erro amigável na interface gráfica, prevenindo travamentos (*crashes*) no sistema.

---

## 3. Requisitos Não Funcionais (RNF)

* **RNF01 — Linguagem e Paradigma:** O projeto deve ser desenvolvido utilizando uma linguagem orientada a objetos, com preferência explícita para a linguagem **Java**.
* **RNF02 — Arquitetura de Software Modular (Padrão MVC):** A estrutura interna deve separar de forma rígida a camada de apresentação gráfica (interface com o usuário) da camada de lógica matemática de propagação e regras de validação.
* **RNF03 — Interface Gráfica do Usuário (GUI):** A aplicação deve contar com campos numéricos estruturados que executem validações imediatas de digitação, impedindo caracteres de texto em entradas puramente matemáticas.
* **RNF04 — Cobertura de Testes Automatizados:** As regras de isolamento matemático e validação técnica devem obrigatoriamente contar com cobertura de testes de unidade automatizados utilizando o framework **JUnit**.
* **RNF05 — Prazo Restrito de Entrega:** O ciclo completo de desenvolvimento, testes e documentação dos diagramas deve ser concluído no prazo de **dois dias**.

---

## 4. Engenharia do Domínio (Modelo Matemático de Propagação)

O cálculo do equilíbrio do enlace baseia-se em somar todas as perdas passivas da rede e subtraí-las da potência emitida, garantindo conformidade com a sensibilidade do equipamento receptor.

### 4.1. Equação Base do Sistema

A relação de equilíbrio de potência segue a fórmula matemática:

$$P_{tx} - A_{\text{total}} \ge S + M$$

Onde a Atenuação Total ($A_{\text{total}}$) é a soma das perdas distribuídas e concentradas ao longo do canal físico:

$$A_{\text{total}} = (\alpha \cdot d) + A_{\text{splitters}} + (N_{\text{con}} \cdot L_{\text{con}}) + (N_{\text{fus}} \cdot L_{\text{fus}})$$

### 4.2. Tabela de Referência para Comportamento das Perdas

| Componente da Rede | Natureza da Perda | Regra de Comportamento Prático / Norma |
| --- | --- | --- |
| **Fibra Óptica (Norma G.652)** | Distribuída ($\alpha \cdot d$) | Varia conforme o comprimento de onda do sinal (tipicamente menor em 1550 nm do que em 1310 nm). |
| **Splitters Passivos** | Concentrada ($A_{\text{splitters}}$) | Sofre atenuação logarítmica intrínseca de pelo menos **3 dB** a cada passo de divisão (ex: 1:2 $\approx$ 3 dB, 1:4 $\approx$ 6 dB, 1:8 $\approx$ 9 dB, 1:16 $\approx$ 12 dB, 1:64 $\approx$ 18 dB). |
| **Conectores Ópticos** | Concentrada ($L_{\text{con}}$) | Pequena perda inserida a cada ponto de manobra mecânica (tipicamente de **0.2 dB** a **0.5 dB** por conector). |
| **Fusões de Fibra** | Concentrada ($L_{\text{fus}}$) | Perda residual gerada pela emenda de fusão da fibra (tipicamente de **0.05 dB** a **0.1 dB** por fusão). |

---

## 5. Arquitetura de Classes Proposta

Para cumprir a meta de modularidade (RNF02) e evitar que uma única classe acumule múltiplas funções, o núcleo do sistema backend será estruturado em três componentes bem definidos:

### 5.1. Classe de Entidade: `ParametrosRede`

Armazena puramente o estado do enlace e os parâmetros configurados pelo usuário.

* **Atributos:** `potenciaTransmissao`, `sensibilidadeReceptor`, `distanciaEnlace`, `atenuacaoEspecifica`, `quantidadeSplitters`, `perdaConectores`, `perdaFusoes`, `margemSeguranca`.

### 5.2. Classe de Serviço Matemático: `CalculadoraLinkBudget`

Contém as funções algébricas para inverter a fórmula de propagação e encontrar o elemento nulo do objeto.

* **Método Principal:** `calcularVariavelAusente(ParametrosRede parametros)`. Identifica qual atributo numérico não foi preenchido, isola a variável correspondente e atualiza o estado do objeto com o resultado encontrado.

### 5.3. Classe de Regras de Negócio: `ValidadorLimites`

Contém os limites técnicos com base em conhecimentos práticos de propagação de ondas e normas internacionais.

* **Método Principal:** `validarParametros(ParametrosRede parametros)`. Dispara flags de aviso se, por exemplo, a atenuação da fibra estiver fora das características práticas de fabricação ou se a potência de transmissão configurada for incompatível com os padrões GPON.

---

## 6. Mapeamento de Casos de Uso (Interações)

O fluxo operacional do Ator (Engenheiro ou Usuário do Sistema) com a interface gráfica seguirá o seguinte comportamento lógico:

1. **Inserir Parâmetros:** O usuário insere os valores conhecidos na interface e deixa o campo que deseja descobrir totalmente vazio.
2. **Validação Dinâmica:** Durante a inserção, o sistema executa validações em tempo real nos campos numéricos. Caso detecte valores incompatíveis com as normas (ex: atenuação excessiva da fibra), renderiza um alerta visual de aviso na tela.
3. **Solicitar Cálculo:** O usuário clica no botão de execução.
* *Cenário de Exceção:* Se mais de um campo estiver vazio, o sistema exibe um alerta controlado: *"Erro: Dados insuficientes para cálculo"*.
* *Cenário de Sucesso:* O motor matemático isola e calcula a variável faltante, exibindo o resultado formatado em tela.



---

## 7. Planejamento de Testes Unitários (JUnit)

Para garantir a confiabilidade matemática da aplicação em apenas 2 dias de desenvolvimento, a camada `Model` passará por testes automatizados estritos cobrindo os seguintes cenários mínimos:

```java
// Cenários de testes de unidade mapeados no JUnit:
public class CalculadoraLinkBudgetTest {
    // 1. Validar isolamento e cálculo correto da Distância (d) dadas as perdas e potências.
    // 2. Validar isolamento e cálculo correto da Potência de Transmissão (Tx).
    // 3. Validar se a classe ValidadorLimites aciona alertas para coeficientes de atenuação fora do padrão G.652.
    // 4. Garantir que uma exceção customizada seja lançada ao tentar calcular com parâmetros insuficientes.
}

```

---