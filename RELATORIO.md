# Relatório do Laboratório: JUnit 5

**Dupla:** Trabalho individual — Andrezza Aguiar Patatas Ricardo
**Link do fork:** https://github.com/AndrezzaPatatasRicardo/lab-junit-jacoco
## 1. Resultado final

Cole aqui o resumo que aparece no final de `java scripts/Rodar.java`:

```
Test run finished after 237 ms
[         6 containers found      ]
[         0 containers skipped    ]
[         6 containers started    ]
[         0 containers aborted    ]
[         6 containers successful ]
[         0 containers failed     ]
[        39 tests found           ]
[         0 tests skipped         ]
[        39 tests started         ]
[         0 tests aborted         ]
[        39 tests successful      ]
[         0 tests failed          ]
```

## 2. Nível 1: `if`s de `Conta`

| if (condição) | Teste que torna verdadeiro | Teste que torna falso |
|---|---|---|
| Saldo inicial negativo (construtor) | contaComSaldoInicialNegativoDeveSerRecusada | contaNovaDeveGuardarTitularESaldoInicial |
| Valor do depósito <= 0 | depositoComValorNegativoDeveSerRecusado | depositoValidoDeveAumentarOSaldo |
| Valor do saque <= 0 | saqueComValorNegativoDeveSerRecusado | saqueValidoDeveDiminuirOSaldo |
| Saque maior que o saldo | saqueMaiorQueSaldoDeveSerRecusado | saqueDoSaldoExatoDeveSerPermitidoEZerarAConta |

## 3. Nível 2: regras de `ServicoTransferencia`

| Regra | Teste(s) que a exercita(m) |
|---|---|
| R1 mesma conta | mesmaContaEValorInvalidoDevemSerRecusados |
| R2 valor menor ou igual a zero | mesmaContaEValorInvalidoDevemSerRecusados |
| R3 acima do limite de 5.000 (e 5.000 exatos permitido) | transferenciaAcimaDoLimiteDeveSerRecusada |
| R4 saldo insuficiente e tarifas | saldoInsuficienteParaValorMaisTarifaNaoAlteraNenhumaConta, transferenciaAcimaDe1000PagaTarifaDe1Porcento e transferenciaDeExatamente1000NaoPagaTarifa |
| R5 transferência OK: saldos da origem e do destino | transferenciaPequenaDeveSerAceitaSemTarifa, transferenciaAcimaDe1000PagaTarifaDe1Porcento, transferenciaDeExatamente1000NaoPagaTarifa e transferenciaAcimaDoLimiteDeveSerRecusada |

## 4. Nível 3: regras de `AnalisadorEmprestimo`

| Regra | Cenário escolhido (entradas → decisão esperada) | Método de teste |
|---|---|---|
| 1. Entradas inválidas | score = -1 e score = 1001; renda = 0; valor = 0; parcelas = 0 e parcelas = 361 → DADOS_INVALIDOS | scoreNegativoDeveRetornarDadosInvalidos, scoreAcimaDe1000DeveRetornarDadosInvalidos, rendaIgualAZeroDeveRetornarDadosInvalidos, valorIgualAZeroDeveRetornarDadosInvalidos, parcelasIgualAZeroDeveRetornarDadosInvalidos, parcelasAcimaDe360DeveRetornarDadosInvalidos |
| 2. Score menor que 300 | score = 299, renda = 3000, valor = 10000, parcelas = 12 → NEGADO | scoreAbaixoDe300DeveRetornarNegado |
| 3. Taxa por faixa de score | score = 800 → 12%; score = 600 → 24%; score = 599 → 36% | scoreIgualA800DeveTerTaxaAnualDe12PorCento, scoreIgualA600DeveTerTaxaAnualDe24PorCento, scoreAbaixoDe600DeveTerTaxaAnualDe36PorCento |
| 4. Parcela (Price) | valor = 1200, taxa mensal = 0,01, parcelas = 12 → aproximadamente R$ 106,62 | calcularParcelaDeveUsarTabelaPrice |
| 5. Comprometimento da renda | renda = 1000 → comprometimento acima de 50% → NEGADO; renda = 2000 → entre 30% e 50% → ANALISE_MANUAL | comprometimentoAcimaDe50PorCentoDeveNegarEmprestimo, comprometimentoEntre30E50PorCentoDeveEnviarParaAnaliseManual |
| 6. Comprometimento até 30% | score = 800 e renda = 10000 → APROVADO; score = 599 e renda = 10000 → ANALISE_MANUAL | comprometimentoAbaixoDe30PorCentoComScoreAltoDeveAprovar, comprometimentoAbaixoDe30PorCentoComScoreBaixoDeveEnviarParaAnaliseManual |

## 5. Reflexão (2 a 4 linhas cada)

**1. Os dois lados do `if`:**
No começo, alguns testes verificavam apenas um dos resultados possíveis das condições. Por exemplo, no teste de saque, era importante verificar tanto um saque válido quanto um saque inválido. Sem testar os dois lados, um erro que permitisse sacar um valor maior que o saldo poderia passar despercebido.

**2. Valor-limite:**
Considero o score 600 um dos limites mais importantes, pois a partir dele a taxa anual muda de 36% para 24%. Se o código utilizasse `>` em vez de `>=`, um cliente com score exatamente 600 receberia a taxa incorreta de 36%.

**3. Nível 3:**
Decidi os cenários a partir da leitura das regras do código. Separei entradas válidas e inválidas, testei valores nos limites e considerei diferentes faixas de score e de comprometimento da renda. Assim, consegui verificar os principais comportamentos esperados do sistema.

**4. Teste que passa mas não protege:**
Um teste sem `assert` pode executar um método e terminar sem erros, mas não verifica se o resultado está correto. Por exemplo, chamar o método de saque sem conferir se o saldo foi alterado corretamente.

**5. Divisão do trabalho:**
O laboratório foi realizado individualmente. Fiquei responsável pela escrita dos testes, análise das regras, execução, verificação dos resultados e elaboração do relatório.