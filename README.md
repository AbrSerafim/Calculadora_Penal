# Calculadora Penal Jurídica

Aplicativo Android desenvolvido em Kotlin para auxiliar no cálculo de datas relacionadas à progressão de regime penal, com base em dados fornecidos pelo usuário.

---

## 1. Componentes do Grupo

* **Desenvolvedor:** Erich Abreu Serafim
* **Projeto:** Calculadora Penal Jurídica
* **Instituição:** Instituto Mauá de Tecnologia
* **Empresa/Cliente:** Cespedes Lourenço Advogados
* **Contato:** https://cespedeslourencoadvogados.com.br/contato/

---

## 2. Descrição Geral do Projeto

A **Calculadora Penal Jurídica** é uma aplicação desenvolvida para auxiliar profissionais da área jurídica, assim como leigos do assunto, no cálculo de datas relacionadas à execução da pena.

O aplicativo recebe informações referentes ao apenado, como:

* Data de início do cumprimento da pena;
* Duração da pena;
* Tipo de crime;
* Condições relacionadas à execução da pena.

A partir dessas informações, o sistema realiza os cálculos necessários para determinar as datas previstas para progressão de regime, apresentando os resultados de forma clara e organizada.

O objetivo principal do projeto é **automatizar e padronizar os cálculos**, reduzindo a necessidade de realização manual dos cálculos de datas. Utilizando como base o Pacote Anticrime (Lei 13.964/2019)

> **Observação:** os resultados apresentados pelo aplicativo têm finalidade de apoio ao cálculo e devem ser conferidos por um profissional responsável antes de serem utilizados em procedimentos jurídicos.

---

## 3. Arquitetura do Sistema

O sistema é estruturado de forma modular, separando a interface do aplicativo da lógica responsável pelos cálculos.

### Aplicativo Android

O frontend é responsável pela interação com o usuário e pela apresentação dos resultados.

Principais componentes:

* Interface desenvolvida com **Jetpack Compose**;
* Componentes de entrada de dados;
* Seleção de datas;
* Seleção das informações referentes ao apenado;
* Exibição dos resultados dos cálculos.

### Visão geral

```text
┌──────────────────────────┐
│        Usuário           │
└────────────┬─────────────┘
             │
             ▼
┌──────────────────────────┐
│   CalculationScreen      │
│                          │
│ • Pena                   │
│ • Data de início         │
│ • Detração               │
│ • Tipo de crime          │
│ • Status do apenado      │
└────────────┬─────────────┘
             │
             │ CalculationData
             ▼
┌──────────────────────────┐
│     PenaltyCalculator    │
│                          │
│  Cálculos de progressão  │
└────────────┬─────────────┘
             │
             │ CalculationResult
             ▼
┌──────────────────────────┐
│      ResultsScreen       │
│                          │
│ • Semiaberto             │
│ • Aberto                 │
│ • Livramento condicional │
└──────────────────────────┘
```

---

## 4. Fluxo Operacional

O funcionamento da aplicação segue as seguintes etapas:

1. O usuário abre o aplicativo.
2. O usuário informa os dados necessários do apenado.
3. O aplicativo valida os dados fornecidos.
4. Os dados são enviados para o sistema responsável pelo cálculo.
5. São calculadas as datas correspondentes às progressões de regime.
6. O resultado é retornado ao aplicativo.
7. O aplicativo apresenta as datas calculadas ao usuário.

De forma simplificada:

```text
Entrada dos dados
       │
       ▼
Validação
       │
       ▼
Cálculo das datas
       │
       ▼
Exibição no aplicativo
```

---

## 5. Funcionalidades Principais

O aplicativo possui as seguintes funcionalidades:

* Entrada da duração da pena;
* Seleção da data de início do cumprimento da pena;
* Seleção das informações relacionadas ao tipo de crime;
* Cálculo das datas de progressão;
* Exibição das datas calculadas;
* Interface adaptada para dispositivos Android;
* Validação dos dados inseridos pelo usuário.

Entre os resultados apresentados estão as datas relacionadas às mudanças de regime, como:

* **Regime fechado → regime semiaberto;**
* **Regime semiaberto → regime aberto;**
* **Livramento condicional**, quando aplicável ao cálculo.

---

## 6. Tecnologias Utilizadas

### Aplicativo Android

* **Kotlin**
* **Android Studio**
* **Jetpack Compose**
* **Material 3**
* **Gradle**
* **Android SDK**

---

## 7. Infraestrutura de Execução do Projeto

Durante o desenvolvimento, o aplicativo pode ser executado localmente utilizando o Android Studio.

A aplicação pode ser executada em:

* Dispositivo Android físico;
* Emulador Android;
* Ambiente de desenvolvimento local.

### Ambiente de desenvolvimento

```text
Computador
    │
    ├── Android Studio
    │
    ├── JDK
    │
    ├── Gradle
    │
    └── Android SDK
             │
             ▼
      Aplicativo Android
```

---

## 8. Configuração do Ambiente para Instalação

### Requisitos

- Android Studio;
- JDK compatível com Java 11;
- Android SDK 37;
- Dispositivo Android ou emulador com Android 7.0 (API 24) ou superior.

### Configuração

O projeto utiliza:

- **Compile SDK:** API 37;
- **Target SDK:** API 37;
- **Minimum SDK:** API 24;
- **Java:** versão 11;
- **Jetpack Compose:** habilitado;
- **Navigation Compose:** versão 2.9.5.

### Clonando o projeto

Clone o repositório:

```bash
git clone https://github.com/AbrSerafim/Calculadora_Penal
```

### Abrindo no Android Studio

1. Abra o **Android Studio**.
2. Selecione **Open**.
3. Selecione a pasta do projeto.
4. Aguarde a sincronização do Gradle.
5. Conecte um dispositivo Android ou inicialize um emulador.
6. Execute o projeto através do botão **Run**.

### Execução pelo Android Studio

Após a sincronização do projeto:

```text
Android Studio
      │
      ▼
Selecionar dispositivo
      │
      ▼
Run ▶
      │
      ▼
Aplicativo instalado
```

---

## 9. Futuras Melhorias (Roadmap)

Possíveis melhorias para versões futuras do projeto incluem:

* [ ] Implementação de autenticação de usuários;
* [ ] Armazenamento do histórico de cálculos;
* [ ] Edição e exclusão de cálculos salvos;
* [ ] Geração de relatórios em PDF;
* [ ] Integração com sistemas jurídicos;
* [ ] Melhorias de acessibilidade;
* [ ] Suporte a diferentes configurações de cálculo;
* [ ] Disponibilização da API em ambiente de produção.

---

## 10. Referências

ANDROID DEVELOPERS. Android Developers: documentação para desenvolvedores Android. [S. l.], 2026. Disponível em: https://developer.android.com/. Acesso em: 1 out. 2026.

ANDROID DEVELOPERS. Get started with Jetpack Compose. [S. l.], 2026. Disponível em: https://developer.android.com/develop/ui/compose/documentation. Acesso em: 1 out. 2026.

BRASIL. Lei nº 7.210, de 11 de julho de 1984. Institui a Lei de Execução Penal. Diário Oficial da União, Brasília, DF, 13 jul. 1984.

BRASIL. Lei nº 13.964, de 24 de dezembro de 2019. Aperfeiçoa a legislação penal e processual penal. Diário Oficial da União, Brasília, DF, 24 dez. 2019. Disponível em: https://www.planalto.gov.br/ccivil_03/_ato2019-2022/2019/lei/l13964.htm. Acesso em: 1 out. 2026.

BUSCA CDP. Calculadora. [S. l.], [s. d.]. Disponível em: https://buscacdp.com.br/calculadora. Acesso em: 1 out. 2026.

DOCUMENTAÇÃO E MATERIAIS FORNECIDOS PARA O PROJETO. [S. l.: s. n.], 2026.

JETBRAINS. Kotlin documentation. [S. l.], 2026. Disponível em: https://kotlinlang.org/docs/home.html. Acesso em: 1 out. 2026.

MATERIAL DE APOIO JURÍDICO UTILIZADO NA DEFINIÇÃO DAS REGRAS DE CÁLCULO. [S. l.: s. n.], 2026.

---