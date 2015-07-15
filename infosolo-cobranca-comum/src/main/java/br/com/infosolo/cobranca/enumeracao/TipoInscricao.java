package br.com.infosolo.cobranca.enumeracao;

/**
 * Tipo de Inscricao / Numero de Inscricao
 * 
 * 1 = CPF.
 * 2 = CNPJ.
 * 0 = Nao Informado.
 * 9 = Outros.
 * 
 * Observacao:
 * - Quando o Tipo de Inscricao for igual a 0 = Nao Informado, o campo
 *   Numero de Inscricao devera ser preenchido com zeros.
 * Para Operacaes de Desconto:
 * - É obrigatorio constar o tipo de inscricao 1 ou 2. Se constar 0 ou 9
 *   o registro sera rejeitado (exceto para o campo Sacador/ Avalista).
 * - Se o CNPJ do Sacado coincidir com o CNPJ do Cedente, o titulo
 *   sera recusado.
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public enum TipoInscricao {
	NAO_INFORMADO(0),
	CPF(1),
	CNPJ(2),
	OUTROS(9);
	
	private int value;
	
	private TipoInscricao(int value) {
		this.setValue(value);
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
