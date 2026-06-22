# GPON Link Budget Calculator Implementation Plan

> **For agentic workers:** USE subagent-driven-development. Steps use checkbox (`- [ ]`) syntax.

**Goal:** Implement a JavaFX GPON Link Budget Calculator with MVC architecture, validation (ITU-T G.984), and JUnit tests.

**Architecture:** Java + JavaFX + Gradle. MVC pattern: Model (ParametrosRede, CalculadoraLinkBudget, ValidadorLimites), View (JavaFX panels), Controller (event handling). JUnit 5 for unit tests.

**Tech Stack:** Java 17+, JavaFX, Gradle (javafxplugin), JUnit 5, IntelliJ IDEA

---

## File Structure

```
src/main/java/gponcalc/
  Main.java                    -- App entry point
  model/
    ParametrosRede.java        -- Entity with all link parameters
    CalculadoraLinkBudget.java -- Mathematical solver (find missing var)
    ValidadorLimites.java      -- ITU-T G.984 validation rules
  view/
    InputPanel.java            -- Form with all input fields
    ResultPanel.java           -- Result display + alerts
  controller/
    MainController.java        -- Binds view events to model
  exception/
    ParametrosInsuficientesException.java

src/test/java/gponcalc/
  model/
    CalculadoraLinkBudgetTest.java
    ValidadorLimitesTest.java
```

## Dependency Order

```
#1 Scaffolding → #2 ParametrosRede + #3 Exceções + #8 Main
  → #4 Calculadora + #5 Validador + #9 InputPanel + #10 ResultPanel
    → #6 Tests Calc + #7 Tests Val + #11 Controller + #12 Alertas
      → #13 Integração
```

## Execution Phases

### Phase A: Sprint 1 Foundation (sequential)
- Task A1: #1 Scaffolding
- Task A2: #2 ParametrosRede + #3 Exceções (parallel)

### Phase B: Sprint 1 Logic (parallel after Phase A)
- Task B1: #4 CalculadoraLinkBudget
- Task B2: #5 ValidadorLimites

### Phase C: Sprint 1 Tests (parallel after Phase B)
- Task C1: #6 Tests CalculadoraLinkBudget
- Task C2: #7 Tests ValidadorLimites

### Phase D: Sprint 2 View (parallel after Phase A)
- Task D1: #8 Main JavaFX
- Task D2: #9 InputPanel (after #8)
- Task D3: #10 ResultPanel (after #8)

### Phase E: Sprint 2 Controller (after Phase B + Phase D)
- Task E1: #11 Controller
- Task E2: #12 Alertas Visuais

### Phase F: Integration (after everything)
- Task F1: #13 Integração Final

## Workflow Per Task

Each task follows Git Flow:
1. Branch: `git checkout -b issue/N-descricao`
2. Implement (professional commits with conventional commits)
3. PR: `gh pr create --title "feat: ..." --body "Closes #N"`
4. Review: `gh pr review N --approve --body "LGTM"`
5. Merge: `gh pr merge N --squash`

Build & test verification: `./gradlew build test`
