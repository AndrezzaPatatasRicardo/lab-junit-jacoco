package br.ufrgs.inf.lab.nivel2;

import static org.junit.jupiter.api.Assertions.*;

import br.ufrgs.inf.lab.nivel1.Conta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * NÍVEL 2 (semi-guiado): ServicoTransferencia.
 *
 * Aqui NÃO há os asserts: cada teste abaixo diz o CENÁRIO e o que deve
 * ser verificado. Você decide como escrever (assertEquals, assertTrue...).
 * Leia as regras R1..R5 em ServicoTransferencia.java.
 *
 * 
 * Sinta-se livre para ACRESCENTAR testes (e usar @ParameterizedTest).
 */
class ServicoTransferenciaTest {

    private static final double DELTA = 0.001;

    private ServicoTransferencia servico;
    private Conta origem;
    private Conta destino;

    // Roda ANTES de cada teste: garante contas "limpas" (testes independentes)
    @BeforeEach
    void preparar() {
        servico = new ServicoTransferencia();
        origem = new Conta("Origem", 10_000.0);
        destino = new Conta("Destino", 0.0);
    }

    // ---------------------------------------------------------------
    // PRONTO (exemplo)
    // ---------------------------------------------------------------

    @Test
    void transferenciaPequenaDeveSerAceitaSemTarifa() {
        StatusTransferencia status = servico.transferir(origem, destino, 200.0);

        assertEquals(StatusTransferencia.OK, status);
        assertEquals(9_800.0, origem.getSaldo(), DELTA);
        assertEquals(200.0, destino.getSaldo(), DELTA);
    }

    // ---------------------------------------------------------------
    // PARA VOCÊ ESCREVER
    // ---------------------------------------------------------------

   @Test
    void transferenciaAcimaDe1000PagaTarifaDe1Porcento() {
         StatusTransferencia status = servico.transferir(origem, destino, 2000.0);

        assertEquals(StatusTransferencia.OK, status);
        assertEquals(20.0, servico.calcularTarifa(2000.0), DELTA);
        assertEquals(7980.0, origem.getSaldo(), DELTA);
        assertEquals(2000.0, destino.getSaldo(), DELTA);
}

   @Test
    void transferenciaDeExatamente1000NaoPagaTarifa() {
         StatusTransferencia status = servico.transferir(origem, destino, 1000.0);

        assertEquals(StatusTransferencia.OK, status);
        assertEquals(0.0, servico.calcularTarifa(1000.0), DELTA);
        assertEquals(9000.0, origem.getSaldo(), DELTA);
        assertEquals(1000.0, destino.getSaldo(), DELTA);
}

   @Test
   void transferenciaAcimaDoLimiteDeveSerRecusada() {
    // Exatamente o limite deve ser permitido
        StatusTransferencia statusLimite = servico.transferir(origem, destino, 5000.0);

        assertEquals(StatusTransferencia.OK, statusLimite);
        assertEquals(4950.0, origem.getSaldo(), DELTA);
        assertEquals(5000.0, destino.getSaldo(), DELTA);

    // Um centavo acima do limite deve ser recusado
        Conta origemTeste = new Conta("Origem teste", 10000.0);
        Conta destinoTeste = new Conta("Destino teste", 0.0);

        StatusTransferencia statusAcima = servico.transferir(origemTeste, destinoTeste, 5000.01);

        assertEquals(StatusTransferencia.ACIMA_DO_LIMITE, statusAcima);
        assertEquals(10000.0, origemTeste.getSaldo(), DELTA);
        assertEquals(0.0, destinoTeste.getSaldo(), DELTA);
}
    @Test
    void saldoInsuficienteParaValorMaisTarifaNaoAlteraNenhumaConta() {
         origem = new Conta("Origem", 2000.0);

        StatusTransferencia status = servico.transferir(origem, destino, 2000.0);

        assertEquals(StatusTransferencia.SALDO_INSUFICIENTE, status);
        assertEquals(2000.0, origem.getSaldo(), DELTA);
        assertEquals(0.0, destino.getSaldo(), DELTA);
}

  @Test
  void mesmaContaEValorInvalidoDevemSerRecusados() {
    // R1: mesma conta
       StatusTransferencia statusMesmaConta =
       servico.transferir(origem, origem, 100.0);

       assertEquals(StatusTransferencia.MESMA_CONTA, statusMesmaConta);

    // R2: valor zero
       StatusTransferencia statusZero =
       servico.transferir(origem, destino, 0.0);

       assertEquals(StatusTransferencia.VALOR_INVALIDO, statusZero);

    // R2: valor negativo
       StatusTransferencia statusNegativo =
       servico.transferir(origem, destino, -100.0);

       assertEquals(StatusTransferencia.VALOR_INVALIDO, statusNegativo);

    // Conferir que os saldos não mudaram
       assertEquals(10000.0, origem.getSaldo(), DELTA);
       assertEquals(0.0, destino.getSaldo(), DELTA);
}
}
