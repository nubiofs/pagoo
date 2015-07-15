package br.com.infosolo.cobranca.enumeracao;

public enum EspecieTitulo {
	CH(1, "CHEQUE"),
	DM(2, "DUPLICATA MERCANTIL"),
	DMI(3, "DUPLICATA MERCANTIL P/ INDICAÇÃO"),
	DS(4, "DUPLICATA DE SERVIÇO"),
	DSI(5, "DUPLICATA DE SERVIÇO P/ INDICAÇÃO"),
	DR(6, "DUPLICATA RURAL"),
	LC(7, "LETRA DE CAMBIO"),
	NCC(8, "NOTA DE CRÉDITO COMERCIAL"),
	NCE(9, "NOTA DE CRÉDITO A EXPORTAÇÃO"),
	NCI(10, "NOTA DE CRÉDITO RURAL"),
	NCR(11, "NOTA DE CRÉDITO RURAL"),
	NP(12, "NOTA PROMISSÓRIA"),
	NPR(13, "NOTA PROMISSÓRIA RURAL"),
	TM(14, "TRIPLICATA MERCANTIL"),
	TS(15, "TRIPLICATA DE SERVIÇO"),
	NS(16, "NOTA DE SEGURO"),
	RC(17, "RECIBO"),
	FAT(18, "FATURA"),
	ND(19, "NOTA DÉBITO"),
	AP(20, "APÓLICE DE SEGURO"),
	ME(21, "MENSALIDADE ESCOLAR"),
	PC(22, "PARCELA DE CONSÓRCIO"),
	PD(90, "* HSBC OBRIGATORIO *"),
	OU(99, "OUTROS");

	private int valor;
	private String descricao;

	private EspecieTitulo(int valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static EspecieTitulo findByValor(int numero) {
		EspecieTitulo retorno = null;
		
		EspecieTitulo[] tipoArray = EspecieTitulo.values();
		for (int i = 0; i < tipoArray.length; i++) {
			if (tipoArray[i].valor == numero) {
				retorno = tipoArray[i];
				break;
			}
				
		}
		
		return retorno;
	}	
}
