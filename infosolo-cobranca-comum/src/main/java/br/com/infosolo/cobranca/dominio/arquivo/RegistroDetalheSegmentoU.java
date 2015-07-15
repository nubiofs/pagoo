package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Registro de detalhe de retorno
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class RegistroDetalheSegmentoU extends RegistroDetalhe {

	//
	// Dados do titulo
	//
	@Descricao("Valor de Acrescimos de Juros/Multa/Encargos")
	@Posicao("18,32")
	public double valorAcrescimo;

	@Descricao("Valor do desconto")
	@Posicao("33,47")
	public double valorDesconto;
	
	@Descricao("Valor do Abatimento")
	@Posicao("48,62")
	public double valorAbatimento;
	
	@Descricao("Valor do IOF")
	@Posicao("63,77")
	public double valorIOF;
	
	@Descricao("Valor Pago")
	@Posicao("78,92")
	public double valorPago;
	
	@Descricao("Valor Líquido")
	@Posicao("93,107")
	public double valorLiquido;
	
	//
	
	@Descricao("Valor Outras Despesas")
	@Posicao("108,122")
	public double valorOutrasDespesas;
	
	@Descricao("Valor Outros Créditos")
	@Posicao("123,137")
	public double valorOutrosCreditos;
	
	@Descricao("Data da Ocorrência")
	@Posicao("138,145")
	public String dataOcorrencia;
	
	@Descricao("Data do Crédito")
	@Posicao("146,153")
	public String dataCredito;
	
	//
	// Ocorrencia do Sacado
	//
	@Descricao("Código da Ocorrência do Sacado")
	@Posicao("154,157")
	public int codigoOcorrenciaSacado;
	
	@Descricao("Data da Ocorrência do Sacado")
	@Posicao("158,165")
	public String dataOcorrenciaSacado;
	
	@Descricao("Valor da Ocorrência do Sacado")
	@Posicao("166,180")
	public double valorOcorrenciaSacado;
	
	@Descricao("Complemento da Ocorrência do Sacado")
	@Posicao("181,210")
	public String complementoOcorrenciaSacado;
	
	@Descricao("Banco Correspondente Compensação")
	@Posicao("211,213")
	public int bancoCorrespondente;

	@Descricao("Nosso Número Banco Correspondente")
	@Posicao("214,233")
	public String nossoNumeroBancoCorrespondente;

	@Descricao("Reservado CNAB")
	@Posicao("234,240")
	public char reservadoCNAB234;

	public RegistroDetalheSegmentoU() {
		this.segmento = 'U';
	}
	
}
