package br.com.infosolo.cobranca.boleto;

import java.io.Serializable;

import br.com.infosolo.cobranca.dominio.boleto.Endereco;
import br.com.infosolo.cobranca.dominio.boleto.Pessoa;
import br.com.infosolo.cobranca.enumeracao.TipoInscricao;
import br.com.infosolo.comum.util.TextoUtil;

/**
 * @author misael
 *
 */
public abstract class EntidadeDeCobranca implements Serializable {
	private static final long serialVersionUID = 8794286412913271840L;
	
	/**
	 * Utilizado como composição
	 */	
	protected Pessoa pessoa;
	
	protected EntidadeDeCobranca() {
		pessoa = new Pessoa();
	}
	
	protected EntidadeDeCobranca(String nome) {
		pessoa = new Pessoa(nome);
	}
	
	protected EntidadeDeCobranca(String nome, String cpfCnpj) {
		pessoa = new Pessoa(TextoUtil.retiraSimbolos(cpfCnpj), nome);
	}

	//	protected EntidadeDeCobranca(String nome, AbstractCPRF cadastroDePessoa) {
//		pessoa = new Pessoa(nome, cadastroDePessoa);
//	}

	/**
	 * @return
	 * @see br.com.nordestefomento.jrimum.domkee.financeiro.banco.Pessoa#getNome()
	 */
	public String getNome() {
		return pessoa.getNome();
	}

	/**
	 * @param nome
	 * @see br.com.nordestefomento.jrimum.domkee.financeiro.banco.Pessoa#setNome(java.lang.String)
	 */
	public void setNome(String nome) {
		pessoa.setNome(nome);
	}
	
	public String getCNPJ() {
		return pessoa.getCpfCnpj();
	}

	public void setCNPJ(String cnpj) {
		try {
			pessoa.setCpfCnpj(cnpj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCPF() {
		return pessoa.getCpfCnpj();
	}

	public void setCPF(String cpf) {
		try {
			pessoa.setCpfCnpj(cpf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Endereco getEndereco() {
		return pessoa.getEndereco();
	}
	
	public void setEndereco(Endereco endereco) {
		pessoa.setEndereco(endereco);
	}
	
	public TipoInscricao getTipoInscricao() {
		return pessoa.getTipoInscricao();
	}
	
}
