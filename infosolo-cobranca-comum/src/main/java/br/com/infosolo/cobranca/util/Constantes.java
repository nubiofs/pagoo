package br.com.infosolo.cobranca.util;

public class Constantes {

	/**
	 * Constante do arquivo de configuração do sistema
	 */
    public static String COBRANCA_PROPERTIES = "/recursos/cobranca.properties";

    public static final String HIFEN_SEPERADOR = "-";
    public static final String DIRETORIO_IMAGENS_BANCO = "/recursos/imagens/bancos/";

	// 
	// Códigos de Mora
	// 
	
	public static int CODIGO_MORA_VALOR_POR_DIA = 1;
	public static int CODIGO_MORA_TAXA_MENSAL = 2;
	public static int CODIGO_MORA_ISENTO = 3;
	
	//
	// Códigos de Desconto
	//
	
	public static int CODIGO_DESCONTO_VALOR_FIXO = 1;
	public static int CODIGO_DESCONTO_PERCENTUAL = 2;
	public static int CODIGO_DESCONTO_ANTECIPACAO = 3;
	
	//
	// Códigos de Protesto
	//
	
	public static int CODIGO_PROTESTO_DIAS_CORRIDOS = 1;			// Sem impressão da mensagem no bloqueto
	public static int CODIGO_PROTESTO_DIAS_UTEIS = 2;				// Sem impressão da mensagem no	bloqueto
	public static int CODIGO_PROTESTO_NAO_PROTESTAR = 3;
	public static int CODIGO_PROTESTO_DIAS_CORRIDOS_IMPRESSAO = 4;	// Com impressão da mensagem no bloqueto
	public static int CODIGO_PROTESTO_DIAS_UTEIS_IMPRESSAO = 5;		// Com impressão da mensagem no bloqueto
	
	public static String MENSAGEM_PROTESTO = "Protestar %d dias corridos ou úteis após o vencimento se não pago.";

	//
	// Códigos de Baixa
	//

	public static int CODIGO_BAIXA_BAIXAR_DEVOLVER = 1;
	public static int CODIGO_BAIXA_NAO_BAIXAR_DEVOLVER = 2;
	
	//
	// Emissão do Bloqueto
	//
	
	public static int EMISSAO_BLOQUETO_CLIENTE_EMITE = 2;
	public static int EMISSAO_BLOQUETO_BANCO_EMITE = 9;
	
	//
	// Distribuição do Bloqueto
	//
	
	public static int DISTRIBUICAO_BLOQUETO_BANCO_DISTRIBUI = 1;
	public static int DISTRIBUICAO_BLOQUETO_CLIENTE_DISTRIBUI = 2;

	//
	// Identificação de Titulo Aceito/Não Aceito
	//
	
	public static String TITULO_ACEITE = "A";
	public static String TITULO_NAO_ACEITE = "N";

	//
	// Códigos de Carteira
	//

	public static int CARTEIRA_COBRANCA_SIMPLES = 1;
	public static int CARTEIRA_GARANTIA_OPERACOES = 3;
	public static int CARTEIRA_DESCONTO = 9;
	
	
	// Constantes booleanas
	
	public static String IC_NAO = "N";
	public static String IC_SIM = "S";
}
