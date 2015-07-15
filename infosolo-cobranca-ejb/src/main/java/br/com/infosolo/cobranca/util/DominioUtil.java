package br.com.infosolo.cobranca.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import br.com.infosolo.cobranca.boleto.guia.Guia;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;
import br.com.infosolo.cobranca.dominio.boleto.Cedente;
import br.com.infosolo.cobranca.dominio.boleto.ContaBancaria;
import br.com.infosolo.cobranca.dominio.boleto.Endereco;
import br.com.infosolo.cobranca.dominio.boleto.Sacado;
import br.com.infosolo.cobranca.dominio.entidades.BancoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.BoletoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.CedenteEntidade;
import br.com.infosolo.cobranca.dominio.entidades.ContaBancariaEntidade;
import br.com.infosolo.cobranca.dominio.entidades.EnderecoEntidade;
import br.com.infosolo.cobranca.dominio.entidades.SacadoEntidade;
import br.com.infosolo.cobranca.enumeracao.Banco;
import br.com.infosolo.cobranca.enumeracao.EspecieTitulo;
import br.com.infosolo.cobranca.enumeracao.TipoCobranca;
import br.com.infosolo.cobranca.enumeracao.TipoInscricao;
import br.com.infosolo.cobranca.excecao.CobrancaExcecao;
import br.com.infosolo.cobranca.negocio.BancoNegocio;

/**
 * Utilitário para conversão de Domínios de Entidade para Domínios do Sistema.
 * 
 * @author Leandro
 *
 */
public class DominioUtil {

	/**
	 * Converte Domínio de Banco
	 * @param bancoEntidade
	 * @return
	 */
	public static Banco retornarDominioBanco(BancoEntidade bancoEntidade) {
		Integer codigo = bancoEntidade.getCodigoBanco().intValue();
		
		return Banco.findByCodigo(codigo);
	}
	
	public static Cedente retornarDominioCedente(CedenteEntidade cedenteEntidade) {
		Cedente cedente = new Cedente();
		EnderecoEntidade enderecoEntidade = new EnderecoEntidade();
		
		if (cedenteEntidade.getEnderecos() != null && cedenteEntidade.getEnderecos().size() > 0)
			enderecoEntidade = cedenteEntidade.getEnderecos().get(0);
		
		cedente.setCodigo(Long.parseLong(cedenteEntidade.getCodigoGeral()));
		cedente.setConvenio(cedenteEntidade.getId().getCodigoConvenio());
		cedente.setCpfCnpj(cedenteEntidade.getId().getNumeroCpfCnpjCedente());
		cedente.setEndereco(retornarDominioEndereco(enderecoEntidade));
		cedente.setContaBancaria(retornarDominioContaBancaria(cedenteEntidade.getContaBancaria()));
		cedente.setNome(cedenteEntidade.getNome());
		cedente.setTipoCobranca(TipoCobranca.findByOrdinal(cedenteEntidade.getTipoCobranca().intValue()));
		cedente.setTipoInscricao(cedenteEntidade.getId().getNumeroCpfCnpjCedente().length() > 11 ? TipoInscricao.CNPJ : TipoInscricao.CPF);
		cedente.setLocalPagamento(cedenteEntidade.getLocalPagamento());
		cedente.setInstrucaoSacado(cedenteEntidade.getInstrucaoSacado());
		cedente.setCarteira(cedenteEntidade.getCarteira());
		
		return cedente;
	}
	
	public static Endereco retornarDominioEndereco(EnderecoEntidade enderecoEntidade) {
		Endereco endereco = new Endereco();

		BeanUtils.copyProperties(enderecoEntidade, endereco);
		endereco.setEndereco(enderecoEntidade.getDescricao());
		
		return endereco;
	}
	
	public static ContaBancaria retornarDominioContaBancaria(ContaBancariaEntidade contaBancariaEntidade) {
		ContaBancaria contaBancaria = new ContaBancaria();
		
		contaBancaria.setAgencia(contaBancariaEntidade.getAgencia());
		contaBancaria.setBanco(retornarDominioBanco(contaBancariaEntidade.getBanco()));
		contaBancaria.setConta(contaBancariaEntidade.getConta());
		contaBancaria.setDigitoAgencia(contaBancariaEntidade.getDigitoAgencia());
		contaBancaria.setDigitoConta(contaBancariaEntidade.getDigitoConta());
		contaBancaria.setOperacaoConta(contaBancariaEntidade.getOperacaoConta());
		
		return contaBancaria;
	}
	
	public static Sacado retornarDominoSacado(SacadoEntidade sacadoEntidade) {
		Sacado sacado = new Sacado();
		
		EnderecoEntidade enderecoEntidade = new EnderecoEntidade();
		
		if (sacadoEntidade.getEnderecos() != null && sacadoEntidade.getEnderecos().size() > 0)
			enderecoEntidade = sacadoEntidade.getEnderecos().get(0);

		sacado.setCpfCnpj(sacadoEntidade.getNumeroCpfCnpjSacado());
		sacado.setEndereco(retornarDominioEndereco(enderecoEntidade));
		sacado.setNome(sacadoEntidade.getNome());
		sacado.setTipoInscricao(sacadoEntidade.getNumeroCpfCnpjSacado().length() > 11 ? TipoInscricao.CNPJ : TipoInscricao.CPF);
		
		return sacado;
	}
	
	public static Boleto retornarDominioBoleto(BoletoEntidade boletoEntidade) {
		Boleto boleto = new Boleto();
		Cedente cedente = retornarDominioCedente(boletoEntidade.getCedente());
		Banco banco = cedente.getContaBancaria().getBanco();
		BancoNegocio bancoNegocio = BancoNegocio.getInstance(banco);
		Sacado sacado = retornarDominoSacado(boletoEntidade.getSacado());
		
		// Carrega valores padrões do boleto
		String nossoNumero = boletoEntidade.getNossoNumero();	//bancoNegocio.formatarNossoNumero(cedente.getCodigo(), boletoEntidade.getIdBoleto());
		boleto = bancoNegocio.obterBoleto(cedente, boletoEntidade.getDataVencimento(), 
				boletoEntidade.getValorBoleto().doubleValue(), 
				new Long(boletoEntidade.getNumeroDocumento()), 
				sacado, nossoNumero);
		
		if (boleto != null) {
			boleto.setDataEmissao(boletoEntidade.getDataEmissao());
			boleto.setDataProcessamento(boletoEntidade.getDataProcessamento());
			boleto.setDataEmissao(boletoEntidade.getDataEmissao());
			if (boletoEntidade.getEspecieTitulo() != null)
				boleto.setEspecieTitulo(EspecieTitulo.findByValor(boletoEntidade.getEspecieTitulo().getCodigoEspecieTitulo().intValue()));
			boleto.setValorAbatimento(boletoEntidade.getValorAbatimento().doubleValue());
			boleto.setValorDesconto(boletoEntidade.getValorDesconto().doubleValue());
			boleto.setValorIOF(boletoEntidade.getValorIof().doubleValue());
			boleto.setValorJurosMora(boletoEntidade.getValorJurosMora().doubleValue());
			boleto.setValorMulta(boletoEntidade.getValorMulta().doubleValue());
		}
		else {
			throw new CobrancaExcecao("Não pode obter o boleto para este banco.");
		}
		return boleto;
	}

	public static Guia retornarDominioGuia(BoletoEntidade boletoEntidade, BoletoEntidade boletoEntidade2) {
		Guia guia = new Guia();
		
		Cedente cedente = retornarDominioCedente(boletoEntidade.getCedente());
		Cedente cedente2 = null;
		
		Double valorBoleto = boletoEntidade.getValorBoleto().doubleValue();
		Double valorBoleto2 = null;
		
		String nossoNumero = boletoEntidade.getNossoNumero();
		String nossoNumero2 = null; 
		
		if (boletoEntidade2 != null) {
			cedente2 = retornarDominioCedente(boletoEntidade2.getCedente());
			valorBoleto2 = boletoEntidade2.getValorBoleto().doubleValue();
			nossoNumero2 = boletoEntidade2.getNossoNumero();
		}
		
		Banco banco = cedente.getContaBancaria().getBanco();
		BancoNegocio bancoNegocio = BancoNegocio.getInstance(banco);
		Sacado sacado = retornarDominoSacado(boletoEntidade.getSacado());
		
		guia = bancoNegocio.obterGuia(cedente, cedente2, boletoEntidade.getDataVencimento(), 
				valorBoleto, valorBoleto2, new Long(boletoEntidade.getNumeroDocumento()), 
				sacado, nossoNumero, nossoNumero2);
		
		if (guia == null) {
			throw new CobrancaExcecao("Não pode obter o guia de arrecadação para este banco.");
		}
		return guia;
	}

	public static List<Boleto> retornarDominioListaBoleto(List<BoletoEntidade> boletosEntidade) {
		ArrayList<Boleto> listaBoleto = new ArrayList<Boleto>();
		
		for (BoletoEntidade boletoEntidade : boletosEntidade) {
			listaBoleto.add(retornarDominioBoleto(boletoEntidade));
		}
		
		return listaBoleto;
	}
	
//	public static <E> E retornarEnumeracao(int codigo) {
//		Enumeration<E> obj = <E>;
//		return obj;
//	}
//	
//	public static void main(String args[]){
//		TipoArquivo enumeracao = DominioUtil.<TipoArquivo>retornarEnumeracao(1);
//	}
}
