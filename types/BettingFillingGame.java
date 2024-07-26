package types;

//Notem que podem faltar métodos na classe que permitam lidar melhor com os objectos.
public class BettingFillingGame extends AbstractFillingGame {

	private int maxJogadasOriginal;
	private int maxJogadas;
	private int pontuacao;
	private int aposta;
	private boolean jahTerminou = false;

	/**
	 * Constrói um jogo. Inicializa a mesa de jogo, e define a estrutura do jogo,
	 * consoante com parâmetros de score, bet e maxPlays
	 * 
	 * @param symbols,            vector de símbolos, a partir do qual se escolhe o
	 *                            conteúdo a usar para preencher as garrafas da
	 *                            mesa.
	 * @param numberOfUsedSymbols número de símbolos que se usa para preencher as
	 *                            garrafas.
	 * @param seed                seed para a instância de Random, que permite
	 *                            controlar a geração de símbolos.
	 * @param bottleSize          tamanho das garrafas que compõem a mesa.
	 * @param score               pontuação inicial
	 * @param bet                 aposta com a qual o jogador vai jogar
	 * @param maxPlays            número máximo de jogadas em que o jogador se
	 *                            propõe a resolver cada ronda do jogo
	 */
	public BettingFillingGame(Filling[] symbols, int numberOfUsedSymbols, int seed, int bottleSize, int score, int bet,
			int maxPlays) {

		// É inicializada uma mesa de jogo e numJogadas é inicializado com valor 0.
		super(symbols, numberOfUsedSymbols, seed, bottleSize); // Invoca construtor da super classe AbstractFillingGame.

		this.maxJogadas = maxPlays;
		this.maxJogadasOriginal = maxPlays;
		this.pontuacao = score;
		this.aposta = bet;
	}

	/**
	 * Providencia ajuda ao utilizador ao criar uma instância de Cup e adicioná-la à
	 * mesa de jogo. No entanto, é decrementado o número máximo de jogadas em que o
	 * utilizador tem de concluir a ronda.
	 */
	@Override
	public void provideHelp() {
		this.maxJogadas--;
		super.mesaJogo.addBottle(this.getNewBottle());
	}

	/**
	 * Devolve o valor da pontuação do jogador.
	 */
	@Override
	public int score() {
		return this.pontuacao;
	}

	/**
	 * Indica se a ronda já terminou, isto é, se as garrafas já estão todas
	 * preenchidas com um conteúdo ou se o número de jogadas máximo já foi atingido.
	 */
	@Override
	public boolean isRoundFinished() {
		
		if (this.jahTerminou) {
			return true;
		}
		
		if (this.jogadas() >= this.maxJogadas) {
			
			this.updateScore();
			this.jahTerminou = true;
			
			return true;
		
		} else if (this.jogadas() < this.maxJogadas && this.areAllFilled()){
			
			this.updateScore();
			this.jahTerminou = true;

			return true;
		}
		
		return false; 
	}

	/**
	 * Indica o número de jogadas que o jogador ainda tem para jogar.
	 * @return inteiro, que representa o número de jogadas restante.
	 */
	public int numberOfPlaysLeft() {
		return this.maxJogadas - this.jogadas();
	}

	/**
	 * Devolve uma garrafa que será adicionada à mesa para ajudar o utilizador.
	 * @return Bottle, instanciada como Cup (capacidade para um golo).
	 */
	@Override
	public Bottle getNewBottle() {
		Cup ajuda = new Cup();
		return ajuda;
	}
	
	/**
	 * Começa uma nova ronda, isto é, gera uma nova mesa com novas garrafas.
	 */
	@Override
	public void startNewRound() {
		
		this.numJogadas = 0;
		this.mesaJogo.regenerateTable();
		this.maxJogadas = this.maxJogadasOriginal;
		this.jahTerminou = false;
		
	}

	/**
	 * Atualiza a pontuação do utilizador, consoante o seu desempenho na ronda. Se a tiver resolvido antes de atingir o número máximo de jogadas, duplica o valor da sua aposta. Caso contrário, fica com menos 2 * o valor da aposta.
	 */
	@Override
	public void updateScore() {

		if (this.jogadas() < this.maxJogadas && this.areAllFilled()) {
			
			this.pontuacao += this.aposta * 2;
			
		} else if (this.jogadas() >= this.maxJogadas && !this.areAllFilled()) {
			
			this.pontuacao -= this.aposta * 2;
			
		}
	}
	
	/**
	 * Retorna uma representação em string do conteúdo do jogo, isto é, do estado da mesa, da pontuação e das jogadas remanecescentes e que já foram efetuadas
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String estado = this.isRoundFinished() ? "Status: This round is finihed." + EOL + this.jogadas() + " moves were used." + EOL: "Status: " + this.jogadas() + " moves have been used until now. You still have " + this.numberOfPlaysLeft() + " moves left." + EOL; 
		sb.append("Score: " + this.score() + EOL);
		sb.append(super.mesaJogo.toString());
		sb.append(estado);

		return sb.toString();
	}

}
