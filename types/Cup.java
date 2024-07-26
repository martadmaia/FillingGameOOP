package types;

public class Cup extends Bottle {

	private int noVezesJáUsado; // quando noVezesJáUsado excede 3, Cup deixa de poder receber novos conteúdos.

	public static final int CUP_SIZE = 1;

	public static final int TIMES_OF_USAGES = 3;


	/**
	 * Método construtor. Cria uma instância de Cup, com capacidade 1 e sem
	 * conteúdo. Cup vazia.
	 */
	public Cup() {
		super(CUP_SIZE);
		// Chamamos o construtor de Bottle ( o construtor que recebe um tamanho (a
		// capacidade da garrafa, neste caso 1).

		this.noVezesJáUsado = 0;
	}

	/**
	 * Método construtor. Cria uma instância de Cup, com capacidade == size, e sem
	 * conteúdo. Cup vazia.
	 * 
	 * @param size, inteiro que representa a capacidade da cup a criar.
	 * @requires size > 0
	 */
	public Cup(int size) {
		super(size);

		this.noVezesJáUsado = 0;
	}

	/**
	 * Método construtor. Cria uma instância de Cup, com capacidade 1 (CUP_SIZE),
	 * cujo conteúdo é o primeiro elemento de bottle.
	 * 
	 * @param bottle
	 * @requires bottle.length == 1 (tamanho tamanho de cup), elementos de Bottle !=
	 *           null
	 */
	public Cup(Filling[] bottle) {
		super(bottle);

		this.noVezesJáUsado = 0;
	}

	/**
	 * "Recebe" um dado conteúdo, isto é, adiciona esse conteúdo ao fim/topo do Cup.
	 * Quando se atinge o número máximo de vezes que o Cup pode ser usada, operação
	 * deixa de ser possível.
	 * 
	 * @param s - conteúdo que se vai receber
	 * @return true, se operação foi bem sucedida, caso contrário, false.
	 */
	@Override
	public boolean receive(Filling s) {

		// Apesar de esta verificação ser feita antes da invocação do método, é só para
		// prevenir algum erro que possa existir.
		if (this.noVezesJáUsado == TIMES_OF_USAGES) {
			System.out.println("Já excedeu o número de ajudas.");
			return false;
		}

		boolean sucesso = super.receive(s);

		if (sucesso) {
			this.setNoVezesJáUsado(getNoVezesJáUsado() + 1);;
			return true;
		}

		return false;
	}

	/**
	 * Retorna o número de vezes que a Cup já foi usada, para que, ao jogar, não
	 * tenhamos de invocar o método receive em vão. Assim, verificamos à partida se
	 * é possível efetuar a operação ou não.
	 * 
	 * @return inteiro, que representa o número de vezes que a Cup já foi usada.
	 */
	public int getNoVezesJáUsado() {
		return this.noVezesJáUsado;
	}

	/**
	 * Setter do noVezesJáUsado, permite alterar o seu valor (por exemplo, quando se
	 * adiciona (receive) um conteúdo.
	 * 
	 * @param noVezesJáUsado
	 * @requires noVezesJáUsado > this.noVezesJáUsado (o número aumenta à medida que
	 *           se joga, não diminui)
	 */
	private void setNoVezesJáUsado(int noVezesJáUsado) {
		this.noVezesJáUsado = noVezesJáUsado;
	}

	/**
	 * Verifica se Cup ainda pode ser usado em mais jogadas, isto é, se o número de
	 * vezes que já foi usado é menor do que o número de vezes que pode ser usado.
	 * 
	 * @return true, se ainda puder ser usado, false, caso contrário.
	 */
	public boolean podeSerUsado() {
		return this.getNoVezesJáUsado() < Cup.TIMES_OF_USAGES && !this.isFull();
	}

}