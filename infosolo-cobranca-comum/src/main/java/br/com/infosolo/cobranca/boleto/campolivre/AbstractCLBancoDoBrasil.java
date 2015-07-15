package br.com.infosolo.cobranca.boleto.campolivre;

import br.com.infosolo.cobranca.boleto.TipoSeguimento;
import br.com.infosolo.cobranca.boleto.guia.Arrecadacao;
import br.com.infosolo.cobranca.dominio.boleto.Boleto;

public abstract class AbstractCLBancoDoBrasil extends AbstractCampoLivre {
	private static final long serialVersionUID = -7324315662526104153L;

	protected AbstractCLBancoDoBrasil(Integer fieldsLength, TipoSeguimento tipoSeguimento) {
		super(fieldsLength, tipoSeguimento);
	}

	protected AbstractCLBancoDoBrasil(Integer fieldsLength, Integer stringLength) {
		super(fieldsLength, stringLength);
	}

	public static CampoLivre create(Boleto titulo) throws NaoSuportadoCampoLivreExcecao{
		CampoLivre campoLivre = null;
		
		if (titulo.getNossoNumero().length() == 10) {
			campoLivre = new CLBancoDoBrasilNN10(titulo);
		}
		else if (titulo.getNossoNumero().length() == 11) {
			campoLivre = new CLBancoDoBrasilNN11(titulo);
		}
		else if (titulo.getNossoNumero().length() == 17) {
			campoLivre = new CLBancoDoBrasilNN17(titulo);	
		}
		else {
			throw new NaoSuportadoCampoLivreExcecao(
				"Campo livre diponível somente para títulos com nosso número " +
				"composto por 10 posições(convênio com 7), 11 posições ou " +
				"17 posições(convênio com 6)."
			);
		}

		return campoLivre;
	}

	public static CampoLivre create(Arrecadacao arrecadacao) throws NaoSuportadoCampoLivreExcecao {			
		CampoLivre campoLivre = null;
		TipoSeguimento tipoSeguimento = null;
		
		tipoSeguimento = arrecadacao.getOrgaoRecebedor().getTipoSeguimento();
		
		if (tipoSeguimento == null) {
			if (arrecadacao.getNossoNumero().length() == 18) {
				campoLivre = new CLBancoDoBrasilPadraoSNR(arrecadacao);
			}
			else {
				campoLivre = new CLBancoDoBrasilNovo(arrecadacao);
			}
		}
		else if (tipoSeguimento == TipoSeguimento.USO_EXCLUSIVO_BANCO) {
			campoLivre = new CLBancoDoBrasilSegmento9(arrecadacao);	
		}
		else {
			campoLivre = new CLBancoDoBrasilPadrao(arrecadacao); 
		}

		return campoLivre;
	}
	
}
