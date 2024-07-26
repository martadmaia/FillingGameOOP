package types;

public abstract class AbstractFillingGame implements FillingGame {

	public static String EOL = System.lineSeparator();

	protected Table mesaJogo;
	protected int numJogadas;

	/**
	 * Constrói um jogo abstrato. Cria uma mesa sobre a qual serão invocados métodos
	 * para manipular os seus conteúdos.
	 * 
	 * @param symbols,             vector de símbolos a partir do qual serão
	 *                             selecionados os símbolos para preencher as
	 *                             garrafas da mesa.
	 * @param numberOfUsedSymbols, número de símbolos a utilizar para preencher as
	 *                             garrafas da mesa.
	 * @param seed,                seed para a instância de Random, que permite
	 *                             controlar a geração de símbolos.
	 * @param bottleSize,          tamanho das garrafas na mesa.
	 */
	public AbstractFillingGame(Filling[] symbols, int numberOfUsedSymbols, int seed, int bottleSize) {

		this.mesaJogo = new Table(symbols, numberOfUsedSymbols, seed, bottleSize);
		this.numJogadas = 0;
	}

	/**
	 * Retorna o número de jogadas já efetuadas no jogo.
	 * 
	 * @return int, que representa o número de jogadas.
	 */
	public int jogadas() {
		return this.numJogadas;
	}

	/**
	 * Devolve o tamanho das garrafas originais que compõem a mesa do jogo.
	 * 
	 * @return inteiro, que representa o tamanho das garrafas originais.
	 */
	public int getBottlesSize() {
		return this.mesaJogo.getSizeBottles();
	}

	/**
	 * Efetua uma jogada, ao verter um símbolo (conteúdo) de uma garrafa para outra,
	 * dados os índices das garrafas. Se a garrafa da qual se verte tiver vários
	 * símbolos iguais no topo, e se a garrafa que recebe tiver capacidade para
	 * receber mais do que um símbolo, então podem ser vertidos vários símbolos.
	 * 
	 * @param x, índice da garrafa da qual se verte o conteúdo
	 * @param y, índice da garrafa que recebe o conteúdo
	 * 
	 * @requires x e y são índices válidos para mesa atual do jogo (isto é, x e y
	 *           correspondem a índices de garrafas existentes na mesa)
	 */
	public void play(int x, int y) {

		boolean foiEfetuada = false;

		// Verificamos se a jogada é válida antes de a efetuar.
		// Iteramos até jogada deixar de ser válida => para verter vários conteúdos numa
		// só jogada.
		while (this.jogadaEhValida(x, y)) {
			this.mesaJogo.pourFromTo(x, y);
			foiEfetuada = true;
		}

		if (foiEfetuada) {
			this.numJogadas++;
		}
	}

	/**
	 * Verifica se é válida a operação de verter de uma garrafa para outra, dado os
	 * seus índices.
	 * 
	 * @param x, índice da garrafa da qual se quer verter o conteúdo
	 * @param y, índice da garrafa que irá receber o conteúdo @return, true, se a
	 *           jogada foi válida, false, caso contrário.
	 */
	public boolean jogadaEhValida(int x, int y) {

		// Fazemos uma primeira verificação para ver a joga envolve um objeto de tipo
		// Cup (garrafa ajuda).
		// Caso envolva, verificamos se este ainda pode ser usado, ou se já foi excedido
		// TIMES_OF_USAGES
		if (this.mesaJogo.getGarrafa(y) instanceof Cup) {

			Cup ajuda = (Cup) this.mesaJogo.getGarrafa(y); // Fazemos o cast para o subtipo, para podermos aceder ao seu
															// método específico.

			if (!ajuda.podeSerUsado()) {

				return false;
			}

		}

		// Se a garrafa que recebe estiver vazia, pode receber.
		// Verificamos se as garrafas permitem verter ou receber consoante os seus conteúdos.
		if (!this.mesaJogo.isEmpty(x) && this.mesaJogo.isEmpty(y)) {
			return true;
		} else if (!this.mesaJogo.isEmpty(x) && this.mesaJogo.top(x) == this.mesaJogo.top(y) && x != y) { 
			return true;
		}

		return false;
	}

	/**
	 * Permite ao utilizador obter uma ajuda ao adicionar uma garrafa (ou um seu
	 * subtipo - Cup) à mesa do jogo.
	 */
	public void provideHelp() {
		Bottle garrafaAjuda = new Cup();
		this.mesaJogo.addBottle(garrafaAjuda);
	}

	/**
	 * Gera uma garrafa extra para ser usada na mesa;
	 * 
	 * @return garrafa, que é adicionada à mesa do jogo.
	 */
	public abstract Bottle getNewBottle();

	/**
	 * Atualiza a pontuação do utilizador no jogo.
	 */
	public abstract void updateScore();

	/**
	 * Retorna o conteúdo que está no topo de uma garrafa, dado o seu índice.
	 * 
	 * @param x, índice da garrafa que se quer verificar.
	 * @return conteúdo, que corresponde ao topo da garrafa.
	 */
	public Filling top(int x) {
		return this.mesaJogo.top(x);
	}

	/**
	 * Verifica se uma garrafa está preenchida por um só conteúdo, dado o seu conteúdo.
	 * 
	 * @param x, índice da garrafa que se quer verificar.
	 * @return true, se a garrafa está preenchida por um só conteúdo, false, caso
	 *         contrário.
	 */
	public boolean singleFilling(int x) {
		return this.mesaJogo.singleFilling(x);
	}

	/**
	 * Verifica se uma ronda terminou, isto é, se todas as garrafas estão cheias com
	 * um único conteúdo ou se estão vazias.
	 * 
	 * @return true, se todas as garrafas estiverem cheias com um único conteúdo ou
	 *         vazias; false, caso contrário.
	 */
	public abstract boolean isRoundFinished();

	/**
	 * Indica qual a pontuação do jogo.
	 * 
	 * @return int, que corresponde à pontuação do jogo.
	 */
	public abstract int score();

	/**
	 * Começa uma nova ronda, isto é, gera uma nova mesa com novas garrafas e atualiza o número de jogadas para zero.
	 */
	public void startNewRound() {
		this.numJogadas = 0;
		this.mesaJogo.regenerateTable();
	}

	/**
	 * Verifica se todas as garrafas da mesa estão preenchidas com um único
	 * conteúdo.
	 * 
	 * @return true, caso todas as garrafas estejam preenchidas com um só conteúdo,
	 *         false, caso contrário.
	 */
	public boolean areAllFilled() {
		return this.mesaJogo.areAllFilled();
	}

	/**
	 * Retorna o número de garradas em jogo.
	 * 
	 * @return inteiro, que representa o número de garrafas que estão atualmente na
	 *         mesa de jogo.
	 */
	public int noGarrafasNaMesaJogo() {
		return this.mesaJogo.getNoGarrafasNaMesa();
	}

	/**
	 * Retorna uma representação em string do jogo, isto é, da mesa de jogo, da
	 * pontuação e do número de jogadas efetuadas.
	 * 
	 * @return String, que corresponde à representação do jogo.
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Score: " + this.score() + EOL);
		sb.append(this.mesaJogo.toString());
		sb.append("Número de jogadas efetuadas: " + this.numJogadas);

		return sb.toString();
	}

}
