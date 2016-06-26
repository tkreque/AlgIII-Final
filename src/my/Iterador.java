package my;

/**
 * Extensao da interface Iterator do Java, que permite
 * alem da consulta aos dados, a inclusao e remocao do
 * elemento apontado pelo iterador, e navegar nos dados
 * em duas direcoes.
 * @param <T> O tipo de dado dos elementos que serao
 * armazenados na estrutura de dados.
 */
public interface Iterador<T>
	extends java.util.Iterator<T>
{
	/**
	 * Retorna verdadeiro se existe um elemento ante do
	 * elemento atual. Caso retorne verdadeiro, a chamada
	 * ao metodo "previous()" ira funcionar.
	 * @return True se existe um elemento antes do atual,
	 * false caso contrario.
	 */
	boolean hasPrevious();
	/**
	 * Volta uma posicao na estrutura e retorna o elemento
	 * anterior.
	 * @return O elemento anterior na estrutura de dados.
	 */
	T previous();
	/**
	 * Insere um elemento imediatamente antes do elemento
	 * atual.
	 * @param dado O dado a ser inserido na estrutura.
	 */
	void insert(T dado);
	/**
	 * Remove o dado atual apontado pelo iterador.
	 */
	void remove();
}
