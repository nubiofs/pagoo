package br.com.infosolo.cobranca.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.StringUtils;

import br.com.infosolo.comum.util.Logger;

/**
 * 
 * <p>
 * Uma lista sequencial de campos string.
 * </p>
 * 
 * @see TextoStream
 * @see java.util.List
 * 
 */
public abstract class AbstractLinhaDeCampos implements TextoStream, List<Campo<?>> {
	private static final long serialVersionUID = 9071816265288953266L;

	private static Logger logger = new Logger(AbstractLinhaDeCampos.class);
	
	

	/**
	 * <p>
	 * Numero de campos da lista.
	 * </p>
	 */
	private Integer fieldsLength;

	/**
	 * <p>
	 * Tamanho da string escrita pelos campos.
	 * </p>
	 */
	private Integer stringLength;

	/**
	 * <p>
	 * Campos armazenados na lista.
	 * </p>
	 */
	private List<Campo<?>> fields;

	/**
	 * <p>
	 * Cria um line of fields com um numero de campos e tamanho da string a ser
	 * escrita especificos.
	 * </p>
	 * 
	 * @param fieldsLength
	 * @param stringLength
	 * @since 0.2
	 */
	protected AbstractLinhaDeCampos() {
		fields = new ArrayList<Campo<?>>();
	}
	
	
	public AbstractLinhaDeCampos(Integer fieldsLength, Integer stringLength) {
		if (fieldsLength != null && stringLength != null) {
			if (fieldsLength > 0) {
				if (stringLength > 0) {
					fields = new ArrayList<Campo<?>>(fieldsLength);

					this.fieldsLength = fieldsLength;
					this.stringLength = stringLength;

				} else {
					IllegalArgumentException e = new IllegalArgumentException(
							"O tamanho da String [ " + stringLength + 
							" ] deve ser maior que 0!");

					logger.error(StringUtils.EMPTY, e);
					throw e;
				}
			} else {
				IllegalArgumentException e = new IllegalArgumentException(
						"O tamanho dos campos [ " + fieldsLength + 
						" ] deve ser maior que 0!");

				logger.error(StringUtils.EMPTY, e);
				throw e;
			}

		}
	}

	/**
	 * <p>
	 * Ler a string de a cordo com a configuração de campos
	 * </p>
	 * 
	 * @see TextoStream#read(String)
	 * @param lineOfFields
	 * @since 0.2
	 */
	public void read(String lineOfFields) {
		if (lineOfFields != null) {
			isConsistent(lineOfFields);

			StringBuilder builder = new StringBuilder(lineOfFields);

			for (Campo<?> field : fields) {
				try {
					field.read(builder.substring(0, field.getLength()));
					
				}catch (Exception e) {
					logger.error("ERRO DE LEITURA");
					throw new IllegalStateException("Erro na leitura do campo de posicao [ " + 
							fields.indexOf(field) + " ]",e);
				}
				
				builder.delete(0, field.getLength());
			}

			builder = null;
		}
	}

	/**
	 * <p>
	 * Escreve a string em funcao da configuracao de campos da instancia
	 * </p>
	 * 
	 * @see TextoStream#write()
	 * @since 0.2
	 */
	public String write() {
		StringBuilder lineOfFields = new StringBuilder(StringUtils.EMPTY);

		if (fields != null) {
			for (Campo<?> field : fields){
				try {
					lineOfFields.append(field.write());
					
				}
				catch (Exception e) {
					logger.error("ERRO DE LEITURA");
					throw new IllegalStateException("Erro na leitura do campo de posicao [ " + 
							fields.indexOf(field) + " ]: " + e.toString());
				}
			}

			isConsistent(lineOfFields);
		}

		String result = lineOfFields.toString();
		
		return result;
	}

	/**
	 * <p>
	 * Verifica se a escrever possue o mesmo tamnho que o definido para a instancia.
	 * </p>
	 * 
	 * @param lineOfFields
	 * @return
	 * 
	 * @since 0.2
	 */
	protected final boolean isConsistent(StringBuilder lineOfFields) {
		boolean is = false;

		if (isConsistent(lineOfFields.toString())) {
			if (fieldsLength == size()) {
				is = true;
			} else {
				IllegalStateException e = new IllegalStateException(
						"O tamanho dos campos [ " + size()
								+ " ] é incompatível com o especificado ["
								+ fieldsLength + "]!");
				logger.error(StringUtils.EMPTY, e);
				throw e;
			}
		}
		return is;
	}

	/**
	 * <p>
	 * Verifica se a escrever possue o mesmo tamnho que o definido para a
	 * instancia.
	 * </p>
	 * 
	 * @param lineOfFields
	 * @return
	 * 
	 * @since 0.2
	 */
	protected final boolean isConsistent(String lineOfFields) {
		boolean is = false;

		if (lineOfFields.length() == stringLength) {
			is = true;
		} else {
			IllegalStateException e = new IllegalStateException(
					"O tamanho da String de campos [ " + lineOfFields.length()
							+ " ] é incompatível com o especificado ["
							+ stringLength + "]!");
			logger.error(StringUtils.EMPTY, e);
			throw e;
		}
		return is;
	}

	/**
	 * @return length of line as string.
	 */
	public int stringSize() {
		return write().length();
	}

	/**
	 * @return the fieldsLength
	 */
	public Integer getFieldsLength() {
		return fieldsLength;
	}

	/**
	 * @param fieldsLength
	 *            the fieldsLength to set
	 */
	public void setFieldsLength(Integer fieldsLength) {
		this.fieldsLength = fieldsLength;
	}

	/**
	 * @return the stringLength
	 */
	public Integer getStringLength() {
		return stringLength;
	}

	/**
	 * @param stringLength
	 *            the stringLength to set
	 */
	public void setStringLength(Integer stringLength) {
		this.stringLength = stringLength;
	}

	/**
	 * @see java.util.List#add(java.lang.Object)
	 */

	public boolean add(Campo<?> e) {
		return fields.add(e);
	}

	/**
	 * @see java.util.List#add(int, java.lang.Object)
	 */

	public void add(int index, Campo<?> element) {
		fields.add(index, element);
	}

	/**
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends Campo<?>> c) {
		return fields.addAll(c);
	}

	/**
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends Campo<?>> c) {
		return fields.addAll(index, c);
	}

	/**
	 * @see java.util.List#clear()
	 */
	public void clear() {
		fields.clear();
	}

	/**
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return fields.contains(o);
	}

	/**
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		return fields.containsAll(c);
	}

	/**
	 * @see java.util.List#get(int)
	 */
	public Campo<?> get(int index) {
		return fields.get(index);
	}

	/**
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		return fields.indexOf(o);
	}

	/**
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return fields.isEmpty();
	}

	/**
	 * @see java.util.List#iterator()
	 */
	public Iterator<Campo<?>> iterator() {
		return fields.iterator();
	}

	/**
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		return fields.indexOf(o);
	}

	/**
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<Campo<?>> listIterator() {
		return fields.listIterator();
	}

	/**
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<Campo<?>> listIterator(int index) {
		return fields.listIterator(index);
	}

	/**
	 * @see java.util.List#remove(int)
	 */
	public Campo<?> remove(int index) {
		return fields.remove(index);
	}

	/**
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return fields.remove(o);
	}

	/**
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		return fields.removeAll(c);
	}

	/**
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		return fields.retainAll(c);
	}

	/**
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public Campo<?> set(int index, Campo<?> element) {
		return fields.set(index, element);
	}

	/**
	 * @see java.util.List#size()
	 */
	public int size() {
		return fields.size();
	}

	/**
	 * @see java.util.List#subList(int, int)
	 */
	public List<Campo<?>> subList(int fromIndex, int toIndex) {
		return fields.subList(fromIndex, toIndex);
	}

	/**
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		return fields.toArray();
	}

	/**
	 * @see java.util.List#toArray(Object[])
	 */
	public <T> T[] toArray(T[] a) {
		return fields.toArray(a);
	}

}
