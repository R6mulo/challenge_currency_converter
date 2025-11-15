## 🌍💱 Currency Converter – Java

Aplicação de conversão de moedas com histórico, JSON, TXT e consumo de API em tempo real.
<div align="center">








</div>
✨ Sobre o Projeto

Este projeto é um conversor de moedas moderno, desenvolvido em Java como parte do desafio do programa Oracle Next Education (ONE).
O objetivo é proporcionar uma aplicação robusta que:

✔ Consome uma API real de câmbio

✔ Converte moedas com valores atualizados

✔ Mantém um histórico persistente (JSON + TXT)

✔ Formata valores monetários automaticamente

✔ Permite limpar histórico

✔ Exibe o histórico sempre antes da próxima conversão

---

## 🚀 Funcionalidades

🔄 Conversão em Tempo Real

- Escolha a moeda de origem e destino

- Digite o valor

- Receba o resultado formatado e preciso

- Baseado em API externa via HttpClient

---

## 📊 Histórico Inteligente

- Registra cada conversão automaticamente

- Persistência em:

    - history.json

    - history.txt

- Mostrado automaticamente ao usuário antes da próxima conversão

- Mantém somente os últimos 20 registros (FIFO)

---

## 🧹 Limpeza de Histórico

Menu com opção para limpar:

🗑️ Histórico interno

🗑️ history.json

🗑️ history.txt

---

## 📜 HISTÓRICO DE CONVERSÕES
1) 100 USD → 564.32 BRL (02/11/2025)
2) 50 EUR → 286.10 BRL

===== MENU =====
1) Converter moeda
2) Limpar histórico
3) Sair



---

## 🛠️ Tecnologias Utilizadas

☕ **Java 17+** <br>
🌐 **HttpClient** (para consumo da API de câmbio) <br>
📦 **Gson** (para manipulação de JSON) <br>
💻 **Terminal** Console (CLI) <br>
⚙️ **ExchangeRate-API** (para taxas de câmbio)

---

## 🧠 Conceitos Aplicados

- Programação orientada a objetos (POO)
- Consumo de APIs externas
- Manipulação de arquivos JSON
- Tratamento de exceções
- Modularização e separação de responsabilidades

---

## 📂 Estrutura do Projeto

```bash
challenge_currency_converter/

br.com.r6mulo.currencyConverter/
├── api/
│   └── ApiClient.java
├── model/
│   ├── ConversionHistory.java
│   └── ConversionResult.java
├── service/
│   └── CurrencyService.java
└── Main.java
```
---

▶ Execução

=== Conversor de Moedas ===
1) BRL -> USD
2) USD -> BRL
3) EUR -> USD
4) USD -> EUR
5) BRL -> EUR
6) EUR -> BRL
7) Conversão personalizada
8) Listar moedas suportadas (código - descrição)
9) Limpar histórico
0) Sair
Escolha: 

---

## 👨‍💻 Autor
<div align="center">
Romulo Chaves

Estudante de Back-end – Oracle Next Education (ONE)

<a href="https://github.com/R6mulo" target="_blank"> <img src="https://img.shields.io/badge/GitHub-000?logo=github&style=for-the-badge" /> </a> <a href="https://www.linkedin.com/in/romulo-chaves" target="_blank"> <img src="https://img.shields.io/badge/LinkedIn-0A66C2?logo=linkedin&style=for-the-badge" /> </a> </div>
☕ Apoie meu trabalho

<a href="https://ko-fi.com/r6rorschach" target="_blank"> <img src="https://img.shields.io/badge/Ko--fi-Support%20me-29abe0?logo=kofi&style=for-the-badge" /> </a>

---

## 📄 Licença

Este projeto é de uso educacional e foi desenvolvido como parte do programa de formação Backend na Oracle Next Education (ONE).
Sinta-se à vontade para estudar, aprimorar e reutilizar o código para fins de aprendizado.
