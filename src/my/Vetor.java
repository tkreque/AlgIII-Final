package my;

import java.util.Iterator;

/**
 * Implementa uma estrutura de dados de Vetor Dinamico,
 * que permite a insercao e remocao de elementos e o
 * ajuste automatico do tamanho do vetor.
 */
public class Vetor<T> implements Iterable<T> {
	
	/* Armazena os elementos como se fossem Objetos.
	 * Devido a limitacoes do Java, o vetor nao pode
	 * ser um vetor de T.
	 */
	private Object[] data;
	private int itemCount;
	
	/**
	 * Inicializa o vetor com um minimo de espaco de armazenamento.
	 */
	public Vetor() {
		this.data = new Object[16];
		this.itemCount = 0;
	}
	
	/**
	 * Calcula o novo tamanho do vetor.
	 * @return O valor to tamanho do vetor.
	 */
	private int getIncrement() {
		final int MAX_CHUNK = 2048;
		if (data.length >= MAX_CHUNK) {
			return data.length + MAX_CHUNK;
		} else {
			return data.length*2;
		}
	}
	
	/**
	 * Garante que exista memoria suficiente para inserir o novo
	 * elemento no vetor. 
	 */
	private void ensureMemoryAvailable() {
		if (itemCount != data.length)
			return;
		Object[] newdata = new Object[getIncrement()];
		System.arraycopy(data, 0, newdata, 0, itemCount);
		data = newdata;
	}

	/**
	 * Insere um novo elemento no vetor.
	 * @param index O indice do novo elemento.
	 * @param value O novo elemento.
	 */
	public void insert(int index, T value) {
		ensureMemoryAvailable();
		System.arraycopy(data, index, data, index+1, itemCount-index);
		this.data[index] = value;
		this.itemCount++;
	}

	/**
	 * Remove um elemento do Vetor.
	 * @param index O indice do elemento a ser removido.
	 */
	public void remove(int index) {
		if (index < 0 || index >= itemCount)
			throw new ArrayIndexOutOfBoundsException("Indice invalido: "+index);
		System.arraycopy(data, index+1, data, index, itemCount-index);
		itemCount--;
	}
	
	/**
	 * Retorna um elemento do vetor.
	 * @param index O indice do elemento.
	 * @return O elemento encontrado.
	 */
	@SuppressWarnings("unchecked") // Necessario para evitar "warning".
	public T get(int index) {
		if (index < 0 || index >= itemCount)
			throw new ArrayIndexOutOfBoundsException("Indice invalido: "+index);
		return (T)data[index]; // Este "cast" e seguro, e sempre vai funcionar.
	}
	
	/**
	 * Consulta quantos objetos o vetor pode armazenar sem aumentar ate
	 * aumentar seu tamanho. 
	 * @return O numero de elementos que podem ser armazenados sem aumentar
	 * o tamanho do vetor.
	 */
	public int getCapacity() {
		return data.length;
	}
	
	/**
	 * Consulta o numero de elementos atualmente armazenados no vetor.
	 * @return O numero de elementos armazenados no vetor.
	 */
	public int getSize() {
		return itemCount;
	}

	/**
	 * Adiciona um novo elemento no final do vetor.
	 * @param value O novo elemento a ser adicionado.
	 */
	public void append(T value) {
		insert(itemCount,value);
	}
	
	/**
	 * Obtem um iterador para o vetor.
	 * @return Um iterador para o vetor.
	 */
	@Override
	public Iterador<T> iterator() {
		return new IteradorVetor<T>(this);
	}
}
