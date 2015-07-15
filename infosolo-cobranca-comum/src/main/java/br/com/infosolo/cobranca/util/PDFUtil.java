package br.com.infosolo.cobranca.util;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.infosolo.comum.util.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.SimpleBookmark;

/**
 * 
 * <p>
 * Serviços e atividades relacionadas a manipulaçãoo de Objetos relacionados a PDF.
 * </p>
 * 
 */
public class PDFUtil implements Serializable {
	private static final long serialVersionUID = 317122634334581419L;

	private static Logger logger = new Logger(PDFUtil.class.getName());

	/**
	 * <p>
	 * Muda um input field para uma imgem com as dimensões e possição do field.
	 * </p>
	 * 
	 * @param stamper
	 * @param positions
	 * @param image
	 * @return rectanglePDF
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 */

	public static RetanguloPDF changeField2Image(PdfStamper stamper,
			float[] positions, Image image) throws DocumentException {

		RetanguloPDF rect = new RetanguloPDF(positions);

		return changeField2Image(stamper, rect, image);
	}

	/**
	 * <p>
	 * Muda um input field para uma imgem com as dimensões e possição do field.
	 * </p>
	 * 
	 * @param stamper
	 * @param rect
	 * @param image
	 * @return rectanglePDF
	 * @throws DocumentException
	 * 
	 * @since 0.2
	 */

	public static RetanguloPDF changeField2Image(PdfStamper stamper,
			RetanguloPDF rect, Image image) throws DocumentException {

		// Ajustando o tamanho da imagem de acordo com o tamanho do campo.
		// image.scaleToFit(rect.getWidth(), rect.getHeight());
		image.scaleAbsolute(rect.getWidth(), rect.getHeight());

		// A rotina abaixo tem por objetivo deixar a imagem posicionada no
		// centro
		// do field, tanto na perspectiva horizontal como na vertical.
		// Caso não se queira mais posicionar a imagem no centro do field, basta
		// efetuar a chamada a seguir:
		// "image.setAbsolutePosition
		// (rect.getLowerLeftX(),rect.getLowerLeftY());"
		image.setAbsolutePosition(rect.getLowerLeftX()
				+ (rect.getWidth() - image.getScaledWidth()) / 2, rect.getLowerLeftY()
				+ (rect.getHeight() - image.getScaledHeight()) / 2);

		// cb = stamper.getUnderContent(rect.getPage());
		stamper.getOverContent(rect.getPage()).addImage(image);

		return rect;
	}

	/**
	 * <p>
	 * Junta varios arquivos pdf em um soh.
	 * </p>
	 * 
	 * @param pdfFiles
	 *            Lista de array de bytes
	 * 
	 * @return Arquivo PDF em forma de byte
	 * @since 0.2
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static byte[] mergeFiles(List<byte[]> pdfFiles) {

		// retorno
		byte[] bytes = null;

		if (pdfFiles != null && !pdfFiles.isEmpty()) {

			int pageOffset = 0;
			boolean first = true;

			ArrayList master = null;
			Document document = null;
			PdfCopy writer = null;
			ByteArrayOutputStream byteOS = null;

			try {
				byteOS = new ByteArrayOutputStream();
				master = new ArrayList();

				for (byte[] doc : pdfFiles) {
					if (doc != null) {

						// cria-se um reader para cada documento
						PdfReader reader = new PdfReader(doc);

						if (reader.isEncrypted()) {
							reader = new PdfReader(doc, "".getBytes());
						}

						reader.consolidateNamedDestinations();

						// pega-se o numero total de paginas
						int n = reader.getNumberOfPages();
						List bookmarks = SimpleBookmark.getBookmark(reader);

						if (bookmarks != null) {
							if (pageOffset != 0) {
								SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
							}
							master.addAll(bookmarks);
						}

						pageOffset += n;

						if (first) {
							// passo 1: criar um document-object
							document = new Document(reader.getPageSizeWithRotation(1));

							// passo 2: criar um writer que observa o documento
							writer = new PdfCopy(document, byteOS);
							document.addAuthor("Leandro Lima (leulima@gmail.com)");
							document.addSubject("Documento de Cobrança");
							document.addCreator("Infosolo Inc.");

							// passo 3: abre-se o documento
							document.open();
							first = false;
						}

						// passo 4: adciona-se o conteudo
						PdfImportedPage page;
						for (int i = 0; i < n;) {
							++i;
							page = writer.getImportedPage(reader, i);
							writer.addPage(page);
						}
					}
				}

				if (master.size() > 0) {
					writer.setOutlines(master);
				}

				// passo 5: fecha-se o documento
				if (document != null) {
					document.close();
				}

				bytes = byteOS.toByteArray();

			} catch (Exception e) {
				logger.error("", e);
			}
		}

		return bytes;
	}
}
