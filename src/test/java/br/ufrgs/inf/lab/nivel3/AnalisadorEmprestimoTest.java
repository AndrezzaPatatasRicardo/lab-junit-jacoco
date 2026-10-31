package br.ufrgs.inf.lab.nivel3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AnalisadorEmprestimoTest {

    private final AnalisadorEmprestimo analisador = new AnalisadorEmprestimo();

    @Test
    void scoreNegativoDeveRetornarDadosInvalidos() {
        // Arrange
        int score = -1;
        double renda = 3000;
        double valor = 10000;
        int parcelas = 12;

        // Act
        DecisaoEmprestimo resultado = analisador.analisar(
            score, renda, valor, parcelas
        );

        // Assert
        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, resultado);
    }
    @Test
    void scoreAcimaDe1000DeveRetornarDadosInvalidos() {
    // Arrange
         int score = 1001;

    // Act
         DecisaoEmprestimo resultado = analisador.analisar(
         score, 3000, 10000, 12
    );

    // Assert
        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, resultado);
}

   @Test
    void rendaIgualAZeroDeveRetornarDadosInvalidos() {
    // Arrange
         double renda = 0;

    // Act
         DecisaoEmprestimo resultado = analisador.analisar(
         800, renda, 10000, 12
    );

    // Assert
        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, resultado);
}
  @Test
  void valorIgualAZeroDeveRetornarDadosInvalidos() {
    // Arrange
       double valor = 0;

    // Act
       DecisaoEmprestimo resultado = analisador.analisar(
       800, 3000, valor, 12
    );

    // Assert
      assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, resultado);
}
  @Test
  void parcelasIgualAZeroDeveRetornarDadosInvalidos() {
    // Arrange
       int parcelas = 0;

    // Act
      DecisaoEmprestimo resultado = analisador.analisar(
      800, 3000, 10000, parcelas
    );

    // Assert
      assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, resultado);
}
  @Test
   void parcelasAcimaDe360DeveRetornarDadosInvalidos() {
    // Arrange
        int parcelas = 361;

    // Act
        DecisaoEmprestimo resultado = analisador.analisar(
        800, 3000, 10000, parcelas
    );

    // Assert
        assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS, resultado);
}
   @Test
   void scoreAbaixoDe300DeveRetornarNegado() {
    // Arrange
       int score = 299;

    // Act
       DecisaoEmprestimo resultado = analisador.analisar(
       score, 3000, 10000, 12
    );

    // Assert
       assertEquals(DecisaoEmprestimo.NEGADO, resultado);
}
   @Test
   void scoreIgualA800DeveTerTaxaAnualDe12PorCento() {
    // Arrange
       int score = 800;

    // Act
       double taxa = analisador.taxaAnual(score);

    // Assert
       assertEquals(0.12, taxa);
}
   @Test
   void scoreIgualA600DeveTerTaxaAnualDe24PorCento() {
    // Arrange
        int score = 600;

    // Act
        double taxa = analisador.taxaAnual(score);

    // Assert
        assertEquals(0.24, taxa);
}
   @Test
    void scoreAbaixoDe600DeveTerTaxaAnualDe36PorCento() {
    // Arrange
        int score = 599;

    // Act
        double taxa = analisador.taxaAnual(score);

    // Assert
       assertEquals(0.36, taxa);
}
   @Test
   void calcularParcelaDeveUsarTabelaPrice() {
    // Arrange
        double valor = 1200;
        double taxaMensal = 0.01;
        int parcelas = 12;

    // Act
        double resultado = analisador.calcularParcela(
        valor, taxaMensal, parcelas
    );

    // Assert
        assertEquals(106.62, resultado, 0.01);
}
 @Test
 void comprometimentoAcimaDe50PorCentoDeveNegarEmprestimo() {
    // Arrange
       int score = 800;
       double renda = 1000;
       double valor = 10000;
       int parcelas = 12;

    // Act
      DecisaoEmprestimo resultado = analisador.analisar(
      score, renda, valor, parcelas
    );

    // Assert
       assertEquals(DecisaoEmprestimo.NEGADO, resultado);
}
  @Test
   void comprometimentoEntre30E50PorCentoDeveEnviarParaAnaliseManual() {
    // Arrange
       int score = 800;
       double renda = 2000;
       double valor = 10000;
       int parcelas = 12;

    // Act
       DecisaoEmprestimo resultado = analisador.analisar(
       score, renda, valor, parcelas
    );

    // Assert
       assertEquals(DecisaoEmprestimo.ANALISE_MANUAL, resultado);
}

@Test
void comprometimentoAbaixoDe30PorCentoComScoreAltoDeveAprovar() {
    // Arrange
    int score = 800;
    double renda = 10000;
    double valor = 10000;
    int parcelas = 12;

    // Act
    DecisaoEmprestimo resultado = analisador.analisar(
        score, renda, valor, parcelas
    );

    // Assert
    assertEquals(DecisaoEmprestimo.APROVADO, resultado);
}

@Test
void comprometimentoAbaixoDe30PorCentoComScoreBaixoDeveEnviarParaAnaliseManual() {
    // Arrange
    int score = 599;
    double renda = 10000;
    double valor = 10000;
    int parcelas = 12;

    // Act
    DecisaoEmprestimo resultado = analisador.analisar(
        score, renda, valor, parcelas
    );

    // Assert
    assertEquals(DecisaoEmprestimo.ANALISE_MANUAL, resultado);
}
    @Test
    void score300NaoDeveSerNegado() {
         assertEquals(DecisaoEmprestimo.ANALISE_MANUAL,
                analisador.analisar(300, 1_000_000, 10000, 12));
    }

    @Test
    void score600ComComprometimentoBaixoDeveAprovar() {
         assertEquals(DecisaoEmprestimo.APROVADO,
                analisador.analisar(600, 1_000_000, 10000, 12));
    }

    @Test
    void limitesDeScoreSaoValidos() {
         assertEquals(DecisaoEmprestimo.NEGADO,
                analisador.analisar(0, 1_000_000, 10000, 12));
         assertEquals(DecisaoEmprestimo.APROVADO,
                analisador.analisar(1000, 1_000_000, 10000, 12));
    }

    @Test
    void limitesDeParcelasSaoValidos() {
         assertEquals(DecisaoEmprestimo.APROVADO,
                analisador.analisar(800, 1_000_000, 10000, 1));
         assertEquals(DecisaoEmprestimo.APROVADO,
                analisador.analisar(800, 1_000_000, 10000, 360));
    }

    @Test
    void rendaEValorNegativosSaoInvalidos() {
         assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS,
                analisador.analisar(800, -3000, 10000, 12));
         assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS,
                analisador.analisar(800, 3000, -10000, 12));
    }

    @Test
    void dadosInvalidosTemPrioridadeSobreScoreBaixo() {
         assertEquals(DecisaoEmprestimo.DADOS_INVALIDOS,
                analisador.analisar(100, 0, 10000, 12));
    }

    @Test
    void taxaAnualNasOutrasFronteiras() {
         assertEquals(0.12, analisador.taxaAnual(1000));
         assertEquals(0.24, analisador.taxaAnual(799));
         assertEquals(0.36, analisador.taxaAnual(300));
    }

    // Parcela de 10000 em 12x a 12% ao ano ~ 888,49
    @Test
    void comprometimentoPerto_de50PorCento() {
         assertEquals(DecisaoEmprestimo.ANALISE_MANUAL,
                analisador.analisar(800, 1815, 10000, 12)); // ~0,49
         assertEquals(DecisaoEmprestimo.NEGADO,
                analisador.analisar(800, 1740, 10000, 12)); // ~0,51
    }

    @Test
    void comprometimentoPerto_de30PorCento() {
         assertEquals(DecisaoEmprestimo.APROVADO,
                analisador.analisar(800, 3070, 10000, 12)); // ~0,29
         assertEquals(DecisaoEmprestimo.ANALISE_MANUAL,
                analisador.analisar(800, 2860, 10000, 12)); // ~0,31
    }

    @Test
    void score799PagaTaxaMaiorQueScore800() {
}
}