package br.com.infosolo.cobranca.negocio.ejb;
import java.util.List;

import javax.ejb.Local;

import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.entidades.ArquivoRemessaEntidade;
import br.com.infosolo.cobranca.dominio.entidades.ArquivoRetornoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidadeLite;
import br.com.infosolo.cobranca.dominio.entidades.BoletoRetornoErroEntidade;
import br.com.infosolo.cobranca.dominio.entidades.CedenteEntidade;
import br.com.infosolo.cobranca.dominio.entidades.CobrancaEntidade;

@Local
public interface ArquivoNegocioLocal {
	
	/**
	 * Método de negócio responsável por gerar o arquivo físico de remessa.
	 * @param cedenteEntidade
	 * @param boletosEntidade
	 */
	public List<Boleto> gerarArquivoRemessa(CobrancaEntidade cobrancaEntidade, CedenteEntidade cedenteEntidade, 
			List<BoletoEntidade> boletosEntidade, ArquivoRemessaEntidade arquivoEntidade);
	
	/**
	 * Processa os arquivos de retornos se existir retornando uma lista de entidade de arquivos
	 * e atualiza a lista de entidades de boletos.
	 * @param boletosEntidade
	 * @return
	 */
	public List<ArquivoRetornoEntidade> processarArquivosRetorno(List<BoletoEntidadeLite> listaBoletosEntidadeLite, 
			List<BoletoRetornoErroEntidade> listaBoletosRetornoErro);

	/**
	 * Marca os arquivos informados como processados.
	 * Nesta rotina sera escolhido o que fazer com estes arquivos, como apaga-los, move-los para outro
	 * local ou renomea-los.
	 * @param listaArquivosRetorno
	 */
	public void marcarArquivosProcessados(List<ArquivoRetornoEntidade> listaArquivosRetorno);

}
