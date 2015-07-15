package br.com.infosolo.cobranca.dominio.arquivo;

import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Descricao;
import br.com.infosolo.cobranca.dominio.arquivo.anotacao.Posicao;

/**
 * Registro de detalhe de retorno
 * 
 * @author Leandro Lima (leandro.lima@infosolo.com.br)
 *
 */
public class RegistroDetalheSegmentoE extends RegistroDetalhe {

	//
	// Dados da Empresa
	//
	
	@Descricao("Tipo de Inscrição da Empresa")
	@Posicao("18,18")
	public int tipoInscricaoEmpresa;
	
	@Descricao("Número de Inscrição da Empresa")
	@Posicao("19,32")
	public long numeroInscricaoEmpresa;

	@Descricao("Código do Convênio no Banco")
	@Posicao("33,52")
	public long codigoConvenioBanco;
	
	//
	// Conta Corrente
	//
	@Descricao("Código Agência Mantenedora da Conta")
	@Posicao("53,57")
	public long agenciaMantenedoraConta;
	
	@Descricao("Dígito Verificador da Agencia")
	@Posicao("58,58")
	public char digitoVerificadorAgencia;
	
	@Descricao("Número da Conta Corrente")
	@Posicao("59,70")
	public long numeroContaCorrente;
	
	@Descricao("Dígito Verificador da Conta")
	@Posicao("71,71")
	public char digitoVerificadorConta;
	
	@Descricao("Dígito Verificador da Agencia/Conta")
	@Posicao("72,72")
	public char digitoVerificadorAgenciaConta;
	
	@Descricao("Nome da Empresa")
	@Posicao("73,102")
	public String nomeEmpresa;
	
	@Descricao("Reservado CNAB")
	@Posicao("103,108")
	public String reservadoCNA103;

	@Descricao("Natureza do Lançamento")
	@Posicao("109,111")
	public String naturezaLancamento;

	@Descricao("Tipo do Complemento Lançamento")
	@Posicao("112,113")
	public int tipoComplementoLancamento;

	@Descricao("Complemento do Lançamento")
	@Posicao("114,133")
	public String complementoLancamento;

	@Descricao("Identificação de Isenção do CPMF")
	@Posicao("134,134")
	public char identificacaoIsencaoCMPF;

	@Descricao("Data Contábil")
	@Posicao("135,142")
	public String dataContabil;

	//
	// Lançamento
	//
	
	@Descricao("Data do Lançamento")
	@Posicao("143,150")
	public String dataLancamento;

	@Descricao("Valor do Lançamento")
	@Posicao("151,168")
	public double valorLancamento;

	/*
	 Tipo do Lançamento: Valor a Débito / Crédito
			Código adotado pela FEBRABAN para caracterizar o item que está sendo representado no
			extrato bancário.
			
			Domínio:
			'D' = Débito
			'C' = Crédito
	 */
	
	@Descricao("Tipo Lançamento: Valor a Déb./Créd.")
	@Posicao("169,169")
	public char tipoLancamento;

	/*
	 Categoria do Lançamento
			Código adotado pela FEBRABAN, para identificar a categoria padrão do Lançamento, para
			conciliação entre Bancos.
			Domínio:
			
			Débitos:
			'101' = Cheques
			'102' = Encargos
			'103' = Estornos
			'104' = Lançamento Avisado
			'105' = Tarifas
			'106' = Aplicação
			'107' = Empréstimo / Financiamento
			'108' = Câmbio
			'109' = CPMF
			'110' = IOF
			'111' = Imposto de Renda
			'112' = Pagamento Fornecedores
			'113' = Pagamento Funcionários
			'114' = Saque Eletrônico
			'115' = Ações
			'117' = Transferência entre Contas
			'118' = Devolução da Compensação
			'119' = Devolução de Cheque Depositado
			'120' = Transferência Interbancária (DOC, TED)
			'121' = Antecipação a Fornecedores
	 */
	
	@Descricao("Categoria do Lançamento")
	@Posicao("170,172")
	public int categoriaLancamento;

	@Descricao("Código Histórico no Banco")
	@Posicao("173,176")
	public String codigoHistoricoBanco;

	@Descricao("Descrição Histórico Lcto. no Banco")
	@Posicao("177,201")
	public String descricaoHistorico;

	@Descricao("Número Documento/Complemento")
	@Posicao("202,240")
	public String numeroDocumento;

	public RegistroDetalheSegmentoE() {
		this.segmento = 'E';
	}
}
