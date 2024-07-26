package types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Notem que podem faltar métodos na classe que permitam lidar melhor com os objectos.
public class Table {

	public static String EOL = System.lineSeparator();

	public static final String empty = "⬜";

	public static final int DIFICULTY = 3;

	public static final int DEFAULT_BOTTLE_SIZE = 5;

	private List<Bottle> mesa;

	private int tamanhoGarrafa;

	private int noGarrafasNaMesa;

	public Random rand;
	
	public Filling[] symbols;
	
	private int noSimbolosAUsar;

	/**
	 * Método construtor. Constrói uma mesa, composta por garrafas, cujo conteúdo é
	 * gerado aleatoriamente consoante os parâmetros dados.
	 * 
	 * @param symbols,             vector de símbolos, a partir do qual se escolhe o
	 *                             conteúdo a usar para preencher as garrafas da
	 *                             mesa.
	 * @param numberOfUsedSymbols, número de símbolos que se usa para preencher as
	 *                             garrafas.
	 * @param seed,                seed para a instância de Random, que permite
	 *                             controlar a geração de símbolos.
	 * @param bottleSize,          tamanho das garrafas que compõem a mesa.
	 */

	public Table(Filling[] symbols, int numberOfUsedSymbols, int seed, int bottleSize) {
		
		rand = new Random(seed); //instanciamos Random com a seed dada.

		this.noSimbolosAUsar = Math.min(symbols.length, numberOfUsedSymbols); // Calculamos o número de símbolos a usar.
		int noGarrafas = this.noSimbolosAUsar + DIFICULTY; // Calculamos o número de garrafas na mesa.
		this.noGarrafasNaMesa = noGarrafas;
		this.tamanhoGarrafa = bottleSize;
		this.symbols = symbols;
		this.mesa = this.preencheGarrafas(); //Invocamos o método que preenche as garrafas
		
	}

	/**
	 * Devolve o número de garrafas que estão atualmente na mesa.
	 * @return inteiro, que representa o número de garrafas na mesa.
	 */
	public int getNoGarrafasNaMesa() {
		return noGarrafasNaMesa;
	}


	/**
	 * Cria um ArrayList (mesa), ao qual são adicionadas garrafas, preenchidas com conteúdo aleatório. 
	 * Este método é usado no construtor e também no regenerate table.
	 * @return mesa, ArrayList com objetos de tipo Bottle, que representa a mesa de jogo. 
	 */
	private ArrayList<Bottle> preencheGarrafas() {
		
		// Mesa, onde serão adicionadas garrafas preenchidas aleatoriamente.
		ArrayList<Bottle> mesaCriada = new ArrayList<Bottle>();
		
		//Para controlarmos se todos os símbolos necessários já foram gerados
		int totalSimbolos = 0;

		// Array em que cada posição corresponde a um dos símbolos a usar e às vezes que
		// já foi usado nas garrafas.
		// Quando número chega a bottleSize, já não podemos adicionar esse símbolo às
		// garrafas.

		int[] simbolosJaUsados = new int[this.noSimbolosAUsar];

		// Iteramos até esgotarmos os símbolos (noSímbolosAUsar * bottleSize)
		// noSimbolosAUsar * bottleSize, dá-nos o total de símbolos que temos de
		// gerar.
		// Ex: noSimbolosAUsar = 7, bottleSize = 7 => se uma garrafa precisa de 7
		// conteúdos para ficar preenchida, precisamos de gerar 7*7 conteúdos.

		while (totalSimbolos < this.noSimbolosAUsar * this.tamanhoGarrafa) {
			
			Bottle garrafa;

			// Geramos o vector que irá originar a garrafa.
			Filling[] conteudo = new Filling[this.tamanhoGarrafa];

			//Iteramos tantas vezes quanto o tamanho das garrafas que queremos preencher.
			//Até a garrafa ficar cheia.
			for (int i = 0; i < this.tamanhoGarrafa; i++) {

				boolean adicionou = true;

				// Criamos este loop que verifica se foi ou não adicionado um conteúdo ao vector
				// Por exemplo, se um certo símbolo já tiver "saído" o máximo de vezes
				// então temos de gerar outro símbolo e assim sucessivamente
				// implica várias chamadadas a nextInt()
				while (adicionou) {
					int numRandom = rand.nextInt(noSimbolosAUsar);
					
					// Se for menor do que bottleSize, significa que ainda podemos adicionar este
					// símbolo ao vector.
					if (simbolosJaUsados[numRandom] < this.tamanhoGarrafa) {
						Filling f = this.symbols[numRandom];
						conteudo[i] = f;
						simbolosJaUsados[numRandom]++; // Atualizamos o número de vezes que já saiu.
						totalSimbolos++;
						adicionou = false; // Saímos deste loop.
					}
				}
			}

			// Criamos instância de Bottle com o vector criado.
			// Adicionamos Bottle à mesa.
			garrafa = new Bottle(conteudo);
			mesaCriada.add(garrafa);
		}

		// Criamos garrafas vazias. 
		// Número de garrafas vazias corresponde a DIFICULTY
		for (int i = 0; i < DIFICULTY; i++) {
			Bottle b = new Bottle(this.tamanhoGarrafa); // cria garrafa com todos os elementos a null (vazia).
			mesaCriada.add(b);
		}
		
		return mesaCriada;
	}
	
	/**
	 * Regenera o conteúdo das garrafas que compõem a mesa. (Conteúdo é gerado a partir do último símbolo gerado no construtor, a instância de Random guarda estado, e recomeça a geração de inteiros.) 
	 */
	public void regenerateTable() {
		this.mesa = this.preencheGarrafas();
	}

	/**
	 * Verifica se a garrafa na mesa com o dado índice está preenchida por um só
	 * conteúdo
	 * 
	 * @param x, índice da garrafa que se quer verificar
	 * @return true se a garrafa no dado índice está preenchida por um só conteúdo
	 *         (incluindo se a garrafa estiver vazia), caso contrário, false.
	 */
	public boolean singleFilling(int x) {
		if (this.mesa.get(x).isEmpty()) {
			return true;
		}

		return this.mesa.get(x).isSingleFilling();
	}

	/**
	 * Verifica se a garrafa na mesa com o dado índice está vazia
	 * 
	 * @param x, índice da garrafa que se quer verificar
	 * @return true se a garrafa no dado índice está vazia, caso contrário, false.
	 */
	public boolean isEmpty(int x) {
		return this.mesa.get(x).isEmpty();
	}

	/**
	 * Verifica se a garrafa na mesa com o dado índice está cheia
	 * 
	 * @param x, índice da garrafa que se quer verificar
	 * @return true se a garrafa no dado índice está cheia por um só conteúdo, caso
	 *         contrário, false.
	 */
	public boolean isFull(int x) {
		return this.mesa.get(x).isFull();
	}

	/**
	 * Verifica se todas as garrafas não vazias estão cheias com um só tipo de
	 * conteúdo.
	 * 
	 * @return, true, se todas as garrafas da mesa estão preenchidas com um só conteúdo ou vazias, caso contrário, false.
	 */
	public boolean areAllFilled() {
		for (Bottle b : this.mesa) {
			if (!b.isEmpty()) {
				if (!b.isSingleFilling()) {
					return false;
				}
				
				if (!b.isFull()) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Verte um conteúdo de uma garrafa para outra na mesa, de acordo com os índices dados. (Operação é feita mediante confirmação de que a garrafa indicado pode receber o conteúdo)
	 * @param i, índice da garrafa de onde se verte o conteúdo
	 * @param j, índice da garrafa que recebe o conteúdo
	 * @requires i e j são índices válidos para a mesa em questão.
	 */
	public void pourFromTo(int i, int j) {
		Filling f = this.isEmpty(i) ? null : this.top(i);
		
		boolean podeReceber = this.mesa.get(j).receive(f);
		if (podeReceber) {
			this.mesa.get(i).pourOut();
		}
	}

	/**
	 * Adiciona uma dada garrafa à mesa.
	 * 
	 * @param bottle, garrafa que se quer adicionar.
	 */
	public void addBottle(Bottle bottle) {
		this.mesa.add(bottle);
		this.noGarrafasNaMesa++;
	}
	
	/**
	 * Retorna uma garrafa na mesa, dado o seu índice.
	 * @return objeto de tipo Bottle (que pode também ser instância de Cup)
	 */
	public Bottle getGarrafa(int i) {
		return this.mesa.get(i);
	}

	/**
	 * Devolve o tamanho das garrafas originais que compõem a mesa.
	 * 
	 * @return inteiro, que representa o tamanho das garrafas originais.
	 */
	public int getSizeBottles() {

		return this.tamanhoGarrafa;
	}
	

	/**
	 * Verifica qual o conteúdo que se encontra no topo da garrafa com índice x.
	 * 
	 * @param x, índide da garrafa que se quer verificar.
	 * @return Filling (elemento que está no topo da garrafa), se a garrafa não
	 *         estiver vazia, caso contrário, lança uma exceção.
	 */
	public Filling top(int x) {

		return this.mesa.get(x).top();
	}

	/**
	 * Retorna uma representação em string do conteúdo da mesa, isto é, uma
	 * representação do conteúdo de cada garrafa que compõe a mesa.
	 * 
	 * @return string que é a representação do conteúdo da mesa.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = this.tamanhoGarrafa - 1; i >= 0; i--) {
			for (Bottle b : this.mesa) {
				if (b instanceof Cup) {
					if (i != 0) {
						sb.append("⚪" + "    ");
					} else {
						sb.append(b.getIndex(i) != null ? b.getIndex(i) + "    " : Table.empty + "    ");
					}

				} else {

					sb.append(b.getIndex(i) != null ? b.getIndex(i) + "    " : Table.empty + "    ");
				}

			}

			sb.append(Bottle.EOL);
		}

		return sb.toString();
	}

}
