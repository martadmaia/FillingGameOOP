package types;

public class FinalScoringFillingGame extends AbstractFillingGame {

	private int pontuacao;
	private boolean jahTerminou = false;

	/**
	 * Constrói um jogo. Inicializa a mesa de jogo.
	 * 
	 * @param symbols,            vector de símbolos, a partir do qual se escolhe o
	 *                            conteúdo a usar para preencher as garrafas da
	 *                            mesa.
	 * @param numberOfUsedSymbols número de símbolos que se usa para preencher as
	 *                            garrafas.
	 * @param seed                seed para a instância de Random, que permite
	 *                            controlar a geração de símbolos.
	 * @param bottleSize          tamanho das garrafas que compõem a mesa.
	 */
	public FinalScoringFillingGame(Filling[] symbols, int numberOfUsedSymbols, int seed, int bottleSize) {
		super(symbols, numberOfUsedSymbols, seed, bottleSize);
		this.pontuacao = 0;
	}

	/**
	 * Constrói um jogo. Inicializa a mesa de jogo e a pontuação (dado um valor).
	 * 
	 * @param symbols,            vector de símbolos, a partir do qual se escolhe o
	 *                            conteúdo a usar para preencher as garrafas da
	 *                            mesa.
	 * @param numberOfUsedSymbols número de símbolos que se usa para preencher as
	 *                            garrafas.
	 * @param seed                seed para a instância de Random, que permite
	 *                            controlar a geração de símbolos.
	 * @param bottleSize          tamanho das garrafas que compõem a mesa.
	 * @param score               pontuação do jogador
	 */
	public FinalScoringFillingGame(Filling[] symbols, int numberOfUsedSymbols, int seed, int bottleSize, int score) {
		super(symbols, numberOfUsedSymbols, seed, bottleSize);
		this.pontuacao = score;
	}

	/**
	 * Permite ao utilizador obter uma ajuda ao adicionar uma garrafa à mesa do
	 * jogo.
	 */
	@Override
	public void provideHelp() {
		this.mesaJogo.addBottle(getNewBottle());
		this.pontuacao -= 100;
	}

	/**
	 * Atualiza a pontuação do utilizador no jogo.
	 */
	public void updateScore() {
		
		if (this.jogadas() < 10) {
			this.pontuacao += 1000;
		} else if (this.jogadas() < 15) {
			this.pontuacao += 500;
		} else if (this.jogadas() < 25) {
			this.pontuacao += 200;
		} else {
			this.pontuacao += 0;
		}

	}

	/**
	 * Verifica se uma ronda terminou, isto é, se todas as garrafas estão cheias com
	 * um único conteúdo ou se estão vazias.
	 * 
	 * @return true, se todas as garrafas estiverem cheias com um único conteúdo ou
	 *         vazias; false, caso contrário.
	 */
	public boolean isRoundFinished() {
		
		if (this.jahTerminou) {
			return true;
		}
		
		if (this.areAllFilled()) {
			this.updateScore();
			this.jahTerminou = true;
			return true;
		}

		return false;
	}
	
	/**
	 * Começa uma nova ronda, isto é, gera uma nova mesa com novas garrafas e atualiza o número de jogadas para zero.
	 */
	@Override
	public void startNewRound() {
		this.numJogadas = 0;
		this.mesaJogo.regenerateTable();
		this.jahTerminou = false;
	}

	/**
	 * Indica qual a pontuação do jogo.
	 * 
	 * @return int, que corresponde à pontuação do jogo.
	 */
	@Override
	public int score() {
		return this.pontuacao;
	}

	/**
	 * Gera uma garrafa extra para ser usada na mesa;
	 * 
	 * @return garrafa, que é adicionada à mesa do jogo.
	 */
	@Override
	public Bottle getNewBottle() {
		Bottle garrafaAjuda = new Bottle(this.getBottlesSize());
		return garrafaAjuda;
	}

	/**
	 * Retorna uma representação em string do jogo, isto é, da mesa de jogo, da
	 * pontuação e do número de jogadas efetuadas.
	 * 
	 * @return String, que corresponde à representação do jogo.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String estado = this.isRoundFinished()? "Status: This round is finihed." + EOL + this.jogadas() + " moves were used." + EOL: "Status: The round is not finished." + EOL + this.jogadas() + " moves have been used until now." + EOL; 
		sb.append("Score: " + this.score() + EOL);
		sb.append(super.mesaJogo.toString());
		sb.append(estado);

		return sb.toString();

	}

}
