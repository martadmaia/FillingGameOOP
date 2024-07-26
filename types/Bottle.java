package types;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//Notem que podem faltar métodos na classe que permitam lidar melhor com os objectos.
public class Bottle implements Iterable<Filling> {

	private int tamanho;
	private List<Filling> golos;
	private int espaçosVazios;

	public static final int DEFAULT_SIZE = 5;

	public static final String empty = "⬜";

	public static final String EOL = System.lineSeparator();

	/**
	 * Método construtor. Cria uma instância de Bottle, com capacidade para 5 golos
	 * (inicializada com todos os golos a null).
	 */
	public Bottle() {
		this.golos = new ArrayList<Filling>(DEFAULT_SIZE);
		this.tamanho = DEFAULT_SIZE;
		this.setEspaçosVazios(DEFAULT_SIZE);

		for (int i = 0; i <= DEFAULT_SIZE; i++) {
			this.golos.add(null);
		}
	}

	/**
	 * Método construtor. Cria uma instância de Bottle, com a capacidade dada
	 * (inicializada com todos os golos a null).
	 * 
	 * @param size, tamanho da garrafa.
	 * @requires size > 0
	 */
	public Bottle(int size) {
		this.golos = new ArrayList<Filling>(size);
		this.tamanho = size;
		this.setEspaçosVazios(size);

		for (int i = 0; i <= size; i++) {
			this.golos.add(null);
		}
	}

	/**
	 * Método construtor. Cria uma instância de Bottle, com a capacidade de
	 * content.length e com o conteúdo dado.
	 * 
	 * @param content, vector de conteúdos que será usado para preencher a garrafa.
	 * @requires content.length > 0, um elemento nulo não pode ser seguido de um
	 *           elemento não nulo. 
	 */
	public Bottle(Filling[] content) {
		int ocupados = 0;
		this.golos = new ArrayList<Filling>(content.length);
		for (Filling f : content) {
			this.golos.add(f);
			if (f != null) {
				ocupados++;
			}
		}

		this.tamanho = content.length;
		this.setEspaçosVazios(content.length - ocupados);
	}

	/**
	 * Getter do atributo espaçosVazios, que representa o número de espaços vazios
	 * na garrafa, isto é, quantos conteúdos ainda pode receber.
	 * 
	 * @return inteiro que representa o número de espaços vazios na garrafa.
	 */
	public int getEspaçosVazios() {
		return espaçosVazios;
	}
	
	/**
	 * Devolve o número de espaços vazios na garrafa.
	 * @return inteiro, que representa os espaços vazios na garrafa, isto é, quandos golos ainda pode receber.
	 */
	public int spaceAvailable() {
		return this.getEspaçosVazios();
	}

	/**
	 * Setter do atributo espaçosVazios, permite alterar o seu valor (por exemplo,
	 * quando se adiciona (receive) ou verte (pourOut) um conteúdo.
	 * 
	 * @param espaçosVazios, que 
	 */
	private void setEspaçosVazios(int espaçosVazios) {
		this.espaçosVazios = espaçosVazios;
	}

	/**
	 * Verifica se a garrafa está cheia, isto é, se já atingiu a sua capacidade de
	 * golos.
	 * 
	 * @return true se a garrafa está cheia, caso contrário, false.
	 */
	public boolean isFull() {
		return this.getEspaçosVazios() == 0;
	}

	/**
	 * Verifica se a garrafa está vazia, isto é, se não tem nenhum golo.
	 * 
	 * @return true se a garrafa está vazia, caso contrário, false.
	 */
	public boolean isEmpty() {
		return this.getEspaçosVazios() == this.tamanho;
	}

	/**
	 * Retorna o topo da garrafa, se esta não estiver vazia. O topo da garrafa é o
	 * último elemento.
	 * 
	 * @return último elemento de garrafa, elemento que está no topo.
	 */
	public Filling top() {
		if (!this.isEmpty()) {
			return this.golos.get(this.tamanho - this.getEspaçosVazios() - 1);
		} else {
			throw new ArrayIndexOutOfBoundsException("A garrafa encontra-se vazia.");
		}

	}

	/**
	 * Retorna o índice do elemento que está no topo da garrafa. Se a garrafa
	 * estiver vazia retorna o primeiro elemento (índice 0) da garrafa - primeiro
	 * null, que poderá receber conteúdo.
	 * 
	 * @return inteiro que representa o índice do elemento que está no topo da
	 *         garrafa.
	 */
	public int indexTop() {
		int índice = 0;
		int contagem = this.golos.size() - 1;

		if (this.isEmpty()) {
			return índice;
		} else {
			while (contagem >= 0 && índice == 0) {
				if (this.golos.get(contagem) != null && this.golos.get(contagem) == this.top()) {
					índice = contagem;
				}

				contagem--;
			}
		}

		return índice;
	}

	/**
	 * "Verte" o último elemento/topo da garrafa, na prática, remove-o.
	 */
	public void pourOut() {
		if (!this.isEmpty()) {
			int indice = this.indexTop();
			this.golos.set(indice, null);
			this.setEspaçosVazios(this.getEspaçosVazios() + 1);
			
		} else {
			System.out.println("Garrafa já se encontra vazia.");
		}

	}

	/**
	 * "Recebe" um dado conteúdo, isto é, adiciona esse conteúdo ao fim/topo da
	 * garrafa.
	 * 
	 * @param s - conteúdo que se vai receber
	 * @return true, se operação foi bem sucedida, caso contrário, false.
	 */
	public boolean receive(Filling s) {

		if (!this.isFull()) {

			if (this.isEmpty()) {
				this.golos.set(0, s);
				this.setEspaçosVazios(this.getEspaçosVazios() - 1);

				return true;
				
			} else if (this.top() == s) {

				this.golos.set(this.indexTop() + 1, s);
				this.setEspaçosVazios(this.getEspaçosVazios() - 1);

				return true;

			}

		}

		System.out.println("Não foi possível efetuar a jogada.");
		return false;

	}

	/**
	 * Retorna qual o tamanho da garrafa.
	 * 
	 * @return inteiro que representa o tamanho/capacidade da garrafa.
	 */
	public int size() {
		return this.tamanho;
	}

	/**
	 * Verifica se a garrafa está preenchida por um só conteúdo.(Não tem de estar
	 * cheia para fazermos esta verificação) ??
	 * 
	 * @return true, se a garrafa estiver preenchida por um só conteúdo, caso
	 *         contrário false.
	 */
	public boolean isSingleFilling() {

		if (this.isEmpty()) {
			return false;
		}

		Filling primeiro = this.golos.get(0);

		for (Filling f : this.golos) {
			if (f != primeiro && f != null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Retorna uma cópia do conteúdo da garrafa.
	 * 
	 * @return array preenchido com o conteúdo da instância corrente.
	 */
	public Filling[] getContent() {
		int índice = 0;

		Filling[] cópia = new Filling[this.tamanho];
		
		for (Filling f : this.golos) {
			cópia[índice] = f;
			índice++;
		}

		return cópia;
	}

	/**
	 * Retorna o conteúdo da garrafa na dada posição.
	 * @param i, índice do conteúdo que se quer obter.
	 */
	public Filling getIndex(int i) {
		return this.golos.get(i);
	}
	
	/**
	 * Retorna uma representação em string do conteúdo da garrafa (a começar pelo
	 * elemento no topo, até ao elemento em baixo - garrafa na vertical)
	 * 
	 * @return string que é a representação do conteúdo da garrafa.
	 */
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (int i = this.tamanho - 1; i >= 0; i--) {
			sb.append(this.golos.get(i) != null ? this.golos.get(i) : Bottle.empty);
			sb.append(Bottle.EOL);
		}

		return sb.toString();

	}

	/**
	 * Retorna iterador da classe Bottle (por delegação).
	 */
	public Iterator<Filling> iterator() {
		return this.golos.iterator();
	}

}
