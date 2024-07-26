package types;

public interface FillingGame {

	/**
	 * Efetua uma jogada, ao verter um símbolo (conteúdo) de uma garrafa para outra, dados os índices das garrafas.
	 * @param x, índice da garrafa da qual se verte o conteúdo
	 * @param y, índice da garrafa que recebe o conteúdo
	 * @requires, x e y são índices válidos para mesa atual do jogo (isto é, x e y correspondem a índices de garrafas existentes na mesa)
	 */
	public void play(int x, int y);
	
	/**
	 * Verifica se uma ronda terminou, isto é, se todas as garrafas estão cheias com um único conteúdo ou se estão todas vazias.
	 * @return true, se todas as garrafas estiverem cheias com um único conteúdo ou vazias; false, caso contrário.
	 */
	public boolean isRoundFinished();
	
	/**
	 * Começa uma nova ronda, isto é, gera uma nova mesa com novas garrafas.
	 */
	public void startNewRound();
	
	/**
	 * Providencia ajuda ao utilizador, adicionando uma nova garrafa à mesa. (Depende do tipo de jogo que está a ser jogado)
	 */
	public void provideHelp();
	
	/**
	 * Retorna qual o conteúdo que está no topo da garrafa correspondente a um dado índice.
	 * @param x, índice da garrafa cujo topo se quer verificar.
	 * @requires x é índice válido no contexto da mesa de jogo atual. 
	 * @return símbolo, correspondente ao topo da garrafa.
	 */
	public Filling top (int x);
	
	/**
	 * Indica se a garrafa correspondente a um dado índice está preenchida por um conteúdo.
	 * @param x, índice da garrafa que se quer verificar.
	 * @return true, se a garrafa estiver preenchida por um conteúdo, caso contrário, false.
	 */
	public boolean singleFilling(int x);
	
	
	/**
	 * Indica qual a pontuação do jogo.
	 * @return int, que corresponde à pontuação do jogo.
	 */
	public int score();

}
