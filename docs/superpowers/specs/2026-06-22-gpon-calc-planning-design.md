# Design de Planejamento — Calculadora Link Budget GPON

## Contexto

Projeto interdisciplinar de Engenharia de Software e Propagação de Ondas. Uma calculadora de Link Budget para redes GPON com interface gráfica JavaFX, capaz de isolar a variável faltante automaticamente seguindo o padrão MVC.

## Tecnologias

- **Linguagem:** Java
- **Build:** Gradle (com plugin JavaFX)
- **GUI:** JavaFX
- **Testes:** JUnit 5
- **IDE:** IntelliJ IDEA

## Organização do Projeto

### Milestones (Sprints)

| Milestone | Nome | Prazo |
|-----------|------|-------|
| Sprint 1 | Model + Testes | Dia 1 |
| Sprint 2 | GUI + Controller + Integração | Dia 2 |

### Labels

| Label | Cor | Descrição |
|-------|-----|-----------|
| `model` | #0366d6 | Issues da camada de domínio/modelo |
| `view` | #5319e7 | Issues da interface gráfica |
| `controller` | #e99695 | Issues da camada de controle |
| `test` | #0e8a16 | Issues de testes |
| `build` | #fbca04 | Issues de configuração/build |
| `bug` | #b60205 | Correção de bugs |
| `priority-high` | #d93f0b | Alta prioridade |
| `priority-medium` | #fbca04 | Prioridade média |

### Issues

#### Sprint 1 — Model + Testes

**Issue 1: Scaffolding do projeto Gradle**
- Configurar build.gradle com plugin JavaFX, JUnit 5
- Estrutura de pacotes: `model`, `view`, `controller`, `exception`
- Atualizar .gitignore
- ~50 linhas

**Issue 2: Entidade ParametrosRede**
- Classe com atributos: potenciaTransmissao, sensibilidadeReceptor, distanciaEnlace, atenuacaoEspecifica, quantidadeSplitters, perdaConectores, perdaFusoes, margemSeguranca
- Método `isCampoVazio()` para detecção de variável faltante
- ~100 linhas

**Issue 3: Exceções customizadas**
- `ParametrosInsuficientesException` — lançada quando 2+ campos vazios
- ~40 linhas

**Issue 4: CalculadoraLinkBudget**
- Método `calcularVariavelAusente(ParametrosRede)` — identifica campo vazio, isola e resolve a variável
- Suporte para resolução de: Ptx, S, d, alpha, perda splitters, conectores, fusões, margem
- ~200 linhas

**Issue 5: ValidadorLimites**
- Validação contra ITU-T G.984: potência, atenuação, perda de splitters, etc.
- Método `validarParametros(ParametrosRede)` retorna lista de alertas
- ~120 linhas

**Issue 6: Testes CalculadoraLinkBudgetTest**
- 4+ cenários: calcular distância, calcular potência Tx, exceção por dados insuficientes, cálculo de splitter
- ~150 linhas

**Issue 7: Testes ValidadorLimitesTest**
- Alertas para coeficientes de atenuação fora do padrão G.652
- Alertas para potência de transmissão incompatível
- ~100 linhas

#### Sprint 2 — GUI + Controller + Integração

**Issue 8: App principal JavaFX**
- Classe Main que estende Application
- Configuração de Stage, Scene, carregamento dos painéis
- ~80 linhas

**Issue 9: Painel de entrada (InputPanel)**
- Formulário com TextField para cada parâmetro
- Validação de digitação (somente números)
- ~300 linhas

**Issue 10: Painel de resultados (ResultPanel)**
- Exibição do resultado calculado
- Indicadores visuais de alerta (cores)
- ~150 linhas

**Issue 11: Controller**
- Bind dos eventos de clique do botão "Calcular"
- Chamada ao modelo (CalculadoraLinkBudget)
- Exibição de resultados e tratamento de erros
- ~250 linhas

**Issue 12: Sistema de alertas visuais**
- Destaque em vermelho/amarelo para campos inválidos
- Tooltips com descrição do alerta
- ~150 linhas

**Issue 13: Integração final**
- Smoke test manual, verificação de fluxo completo
- Ajustes finos
- ~50 linhas

### Board Kanban (GitHub Projects)

Colunas: **Backlog → Sprint Backlog → Em Progresso → Review → Done**

### Fluxo de trabalho

1. Issues começam em **Backlog**
2. Ao iniciar uma sprint, issues são movidas para **Sprint Backlog**
3. Issue em desenvolvimento vai para **Em Progresso**
4. Após implementação, vai para **Review** (revisão + testes)
5. Testes passando → **Done**
