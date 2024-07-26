package execution;

import java.util.Random;
import java.util.Scanner;

import types.Animals;
import types.Balls;
import types.BettingFillingGame;
import types.Emojis;
import types.Filling;
import types.FinalScoringFillingGame;
import types.Squares;
import types.AbstractFillingGame;

public class Main {

	// Instância de Random que permitirá gerar as seeds para os construtores dos
	// jogos.
	static Random rand = new Random();

	static Scanner sc = new Scanner(System.in);

	/* Símbolos que o utilizador pode escolher para preencher as garrafas. */
	static String emojis = "😃 😒 😡 😇 😉 😈 😎 😍";
	static String animais = "🐲 🐳 🐴 🐒 🐷 🐸 🐝 🐢 🐜 🐿 🐬 🐠";
	static String bolas = "① ② ③ ④ ⑤ ⑥ ⑦ ⑧ ⑨";
	static String quadrados = "🟨 🟫 🟥 ⬛ 🟧 🟩 🟪";

	static String divisor = "\n+------------------------------------------------------------------------------------+\n";
	// String com opções possíveis de input para escolher o modo do jogo. Para
	// verificação do input.
	static String modos = "cCbB";
	// String com opções possíveis de input para escolher os símbolos a utilizar.
	// Para verificação do input.
	static String simbolos = "1234";

	// Mensagens de interação com o utilizador.
	static final String MSG_MODO_JOGO = "Choose a gameplay mode:\n\nBetting game (b)\nClassic game (c)";
	static final String MSG_ESCOLHER_SIMBOLOS = "What type of symbols do you want to use in the game?\n";
	static final String MSG_TAMANHO_GARRAFA = "What size do you want the bottles to be? (Remember, the bigger the bottle is, the\nharder it will be to fill...";
	static final String MSG_NUM_SIMBOLOS = "How many symbols do you want to use to fill the bottles?";
	static final String MSG_APOSTA = "Place your bet:";
	static final String MSG_MAX_JOGADAS = "In how many plays do you propose to finish the round?";
	static final String MSG_NUM_INVALIDO = "Please insert a valid number";
	static final String MSG_INDICES_JOGADA_1 = "Insert the indexes of the bottles you want to use:";
	static final String MSG_INDICES_JOGADA_2 = "Pour from:";
	static final String MSG_INDICES_JOGADA_3 = "Pour to:";
	static final String MSG_COMECAR_JOGO = "Let's begin! Good luck :=)\n";

	// Modo escolhido pelo utilizador
	static String modoJogo;

	// Símbolos escolhidos pelo utilizador.
	static Filling[] simbolosEscolhidosParaOConstrutor;
	// Tamanho escolhido pelo utilizador para as garrafas.
	static int tamanhoGarrafa;
	// Número de símbolos escolhido pelo utilizador.
	static int numSimbolosAUsar;

	// Número de garrafas na mesa.
	static int noGarrafas;
	// Pontuação do jogador
	static int pontuacao = 0;
	// Boolean que guarda se já foi dada ajuda ao utilizador na presente ronda.
	static boolean jahFoiDadaAjuda;
	// Objeto de tipo AbstractFillingGame, instanciado como BettingFillingGame ou
	// FinalScoringFillingGame (consoante o escolhido pelo utilizador)
	static AbstractFillingGame jogo;

	/**
	 * Imprime na consola as mensagens de boas-vindas e as intruções. Obtém, através
	 * da interação com o utilizador, as configurações do jogo.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		boasVindas();
		escolherConfiguracoes();
	}

	/**
	 * Imprime na consola as mensagens de boas-vindas e as intruções do jogo.
	 */
	public static void boasVindas() {
		StringBuilder sb = new StringBuilder();

		String titulo = "//////////////////////////////////////////////////////////////////////////////////////\n"
				+ "//                                                                                  //\n"
				+ "//  #     #    #    ####### ####### ######         #####  ####### ######  #######   //\n"
				+ "//  #  #  #   # #      #    #       #     #       #     # #     # #     #    #      //\n"
				+ "//  #  #  #  #   #     #    #       #     #       #       #     # #     #    #      //\n"
				+ "//  #  #  # #     #    #    #####   ######         #####  #     # ######     #      //\n"
				+ "//  #  #  # #######    #    #       #   #               # #     # #   #      #      //\n"
				+ "//  #  #  # #     #    #    #       #    #        #     # #     # #    #     #      //\n"
				+ "//   ## ##  #     #    #    ####### #     #        #####  ####### #     #    #      //\n"
				+ "//                                                                                  //\n"
				+ "//////////////////////////////////////////////////////////////////////////////////////\n";

		String boasVindas = "Welcome to Water Sort, where the goal is to solve a puzzle!";
		String objetivos = "\nPour contents from one bottle to another, until all the bottles are filled with the \nsame content. \nSounds hard? We'll demonstrate!";

		String mesaExemplo = "\n\nImagine the bottles look like this..." + "\n\n⬜    😒    😡    ⬜    ⬜    ⬜"
				+ "\n😒    😡    😡    ⬜    ⬜    ⬜" + "\n😒    😒    😃    ⬜    ⬜    ⬜"
				+ "\n😃    😡    😃    😃    ⬜    ⬜"
				+ "\n\nWhat moves do you have now? You can move the two angry emojis to an empty bottle..."
				+ "\n\n⬜    😒    ⬜    ⬜    ⬜    ⬜" + "\n😒    😡    ⬜    ⬜    ⬜    ⬜"
				+ "\n😒    😒    😃    ⬜    😡    ⬜" + "\n😃    😡    😃    😃    😡    ⬜"
				+ "\n\nAnd then the smile emoji to the third bottle..." + "\n\n⬜    😒    ⬜    ⬜    ⬜    ⬜"
				+ "\n😒    😡    😃    ⬜    ⬜    ⬜" + "\n😒    😒    😃    ⬜    😡    ⬜"
				+ "\n😃    😡    😃    ⬜    😡    ⬜"
				+ "\n\nAnd you get the point! Keep moving contents until you win the round!\n";

		String regras = "\nHere are some rules you need to keep in mind while playing:\n1.We're programmers, so we start counting at 0. This means the first bottle is at\nindex 0, the second at index 1, and so on...\n2.You can only pour from one bottle to another if their tops are the same (or if the \nbottle that's receiving the content is empty)\n3. If you get stuck, we'll help you, but at a cost ;)\n";
		String comandos = "\nCommands:\n\nIf at any moment you want to quit the game, press q\nIf you get stuck and want some help, press h\nIf you want to play a new round and regenerate the contents of the table, press r\n";

		String jogosTipos = "\nThere are two gameplay modes:\n\nIf you're feeling daring... play a round in Betting mode.\nPlace a bet on how many moves you'll need to finish the round.\nIf you finish the round before reaching that number of moves, we'll add to your \nscore your bet * 2.\nIf you fail to finish the round in that number of moves, we'll take from your score\nyour bet * 2.\nWe'll help you by providing a small bottle (with a capacity for a single content),\nbut we'll take away one of the remaining moves...\n\nIf you're feeling more traditional, choose the Classic mode. You have unlimited moves,\nand if you get stuck and need help, we'll give you another bottle. For each bottle we\ngive you, we'll take away 100 points from your score...\n";
		String comecar = "\nAnd that's everything! We hope you have fun playing!\nLet's get started!\n";

		sb.append(titulo);
		sb.append(divisor);
		sb.append(boasVindas);
		sb.append(objetivos);
		sb.append(mesaExemplo);
		sb.append(divisor);
		sb.append(regras);
		sb.append(divisor);
		sb.append(comandos);
		sb.append(divisor);
		sb.append(jogosTipos);
		sb.append(divisor);
		sb.append(comecar);
		sb.append(divisor);

		System.out.println(sb);
	}

	/**
	 * Obtém, através da interação com o utilizador, as configurações do jogo. Pede
	 * input ao utilizador para definir o modo do jogo, os símbolos a utilizar, o
	 * número de símbolos a usar, e o tamanho das garrafas. Invoca o método
	 * iniciaOJogo().
	 */
	public static void escolherConfiguracoes() {
		System.out.println(MSG_MODO_JOGO);
		modoJogo = sc.next().trim().toLowerCase();

		while (!modos.contains(modoJogo)) {
			System.out.println("Insert a valid gameplay mode.");
			modoJogo = sc.next().trim().toLowerCase();
		}

		System.out.println(MSG_ESCOLHER_SIMBOLOS);
		System.out.println("(1)  " + emojis + "\n");
		System.out.println("(2)  " + animais + "\n ");
		System.out.println("(3)  " + bolas + "\n");
		System.out.println("(4)  " + quadrados + "\n");

		String simbolosJogo = sc.next();

		while (!simbolos.contains(simbolosJogo)) {
			System.out.println("Insert a valid symbol set.");
			simbolosJogo = sc.next();
		}

		switch (Integer.parseInt(simbolosJogo)) {
		case 1:
			simbolosEscolhidosParaOConstrutor = Emojis.values();
			break;

		case 2:
			simbolosEscolhidosParaOConstrutor = Animals.values();
			break;

		case 3:
			simbolosEscolhidosParaOConstrutor = Balls.values();
			break;

		case 4:
			simbolosEscolhidosParaOConstrutor = Squares.values();
			break;
		}

		System.out.println(MSG_TAMANHO_GARRAFA);

		tamanhoGarrafa = inputInteiros();

		System.out.println(MSG_NUM_SIMBOLOS);

		numSimbolosAUsar = inputInteiros();

		iniciaOJogo();

	}

	/**
	 * Método auxiliar que efetua o pedido de input ao utilizador. É pedido input
	 * até se obter um número válido. @return, inteiro, que representa o input
	 * (válido) dado pelo utilizador.
	 */
	public static int inputInteiros() {
		int input = 0;
		boolean erro = true;

		do {
			if (sc.hasNextInt()) {
				input = sc.nextInt();
				erro = false;
			} else {
				sc.next();
				System.out.println(MSG_NUM_INVALIDO);
			}
		} while (erro);

		return input;
	}

	/**
	 * Método auxiliar que efetua o pedido de input ao utilizador - objetivo é obter
	 * 2 inteiros que correspondam a índices válidos para as garrafas que estão na
	 * mesa.
	 * 
	 * @return int[], em que a primeira posição corresponde ao índice da garrafa da
	 *         qual se verte, e o índice na segunda posição corresponde ao índice da
	 *         garrafa que recebe o conteúdo.
	 */
	public static int[] inputIndicesParaJogada() {
		int[] indices = new int[2];
		boolean erro = true;

		do {

			System.out.println(MSG_INDICES_JOGADA_1);
			System.out.println(MSG_INDICES_JOGADA_2);

			for (int i = 0; i < 2; i++) {
				boolean erroInput = true;

				if (i == 1) {
					System.out.println(MSG_INDICES_JOGADA_3);
				}

				while (erroInput) {
					if (sc.hasNextInt()) {
						int input = sc.nextInt();

						if (input >= 0 && input < noGarrafas) {
							indices[i] = input;
							erroInput = false;
						} else {
							erroInput = true;
							System.out.println(MSG_NUM_INVALIDO);
						}
					} else {
						String comando = sc.next().toLowerCase();
						if ("qrh".contains(comando)) {
							recebeComandosEspeciais(comando);
						} else {
							System.out.println(MSG_NUM_INVALIDO);
							erroInput = true;
						}
					}
				}
			}

			erro = false;

		} while (erro);

		return indices;
	}

	/**
	 * Realiza a lógica de sair do programa, começar uma nova ronda ou dar ajuda ao utilizador, caso seja dado como input um dos seguintes caracteres - q, r, h.
	 * @param comando, string que corresponde ao carácter especial.
	 */
	public static void recebeComandosEspeciais(String comando ) {
		switch (comando) {

		case "q":
			System.out.println("You've pressed q to exit the game. Bye bye!");
			System.exit(0);

		case "h":

			if ((jogo instanceof BettingFillingGame && !jahFoiDadaAjuda)
					|| (jogo instanceof FinalScoringFillingGame)) {
				System.out.println("You've asked for help and it shall be provided!");
				jogo.provideHelp();
				noGarrafas = jogo.noGarrafasNaMesaJogo();
				jahFoiDadaAjuda = true;
				System.out.println(jogo.toString());
			} else {
				System.out.println("Sorry, no other help available.");
				System.out.println(jogo.toString());
			}

			System.out.println(MSG_INDICES_JOGADA_1);
			System.out.println(MSG_INDICES_JOGADA_2);

			break;

		case "r":

			jogo.startNewRound();

			System.out.println(
					"You've pressed r to regenerate the contents of the table and start a new round!\nHere's the new table:");
			System.out.println(jogo.toString());
			System.out.println(MSG_INDICES_JOGADA_1);
			System.out.println(MSG_INDICES_JOGADA_2);
			break;
		}
	}

	/**
	 * Inicia o jogo, ao invocar os métodos específicos a cada jogo.
	 */
	public static void iniciaOJogo() {

		if (modoJogo.toLowerCase().equals("c")) {
			jogaFinalScoringGame();
		} else {
			jogaBettingGame();
		}
	}

	/**
	 * Cria um novo jogo (FinalScoringFillingGame) e invoca o método que inicia a
	 * interação com o utilizador para o jogar.
	 */
	public static void jogaFinalScoringGame() {
		System.out.println(divisor);
		System.out.println(MSG_COMECAR_JOGO);

		jogo = new FinalScoringFillingGame(simbolosEscolhidosParaOConstrutor, numSimbolosAUsar, rand.nextInt(),
				tamanhoGarrafa, pontuacao);
		noGarrafas = jogo.noGarrafasNaMesaJogo();
		System.out.println(jogo.toString());

		jogaJogo();
	}

	/**
	 * Cria um novo jogo (BettingFillingGame) e invoca o método que inicia a
	 * interação com o utilizador para o jogar. Pede ao utilizador o input adicional
	 * para definir a aposta e o número máximo de jogadas.
	 */
	public static void jogaBettingGame() {
		System.out.println(MSG_APOSTA);
		int aposta = inputInteiros();

		System.out.println(MSG_MAX_JOGADAS);
		int maxJogadas = inputInteiros();
		System.out.println(divisor);
		System.out.println(MSG_COMECAR_JOGO);

		jogo = new BettingFillingGame(simbolosEscolhidosParaOConstrutor, numSimbolosAUsar, rand.nextInt(),
				tamanhoGarrafa, pontuacao, aposta, maxJogadas);
		noGarrafas = jogo.noGarrafasNaMesaJogo();
		System.out.println(jogo.toString());

		jogaJogo();
	}

	/**
	 * Pede input (índices das garrafas) enquanto a ronda não está acabada. Vai
	 * efetuando as respetivas jogadas (vertendo conteúdo de uma garrafa para a
	 * outra).
	 */
	static void jogaJogo() {

		while (!jogo.isRoundFinished()) {

			int[] indices = inputIndicesParaJogada();

			jogo.play(indices[0], indices[1]);

			System.out.println(jogo.toString());
		}

		fimJogo();
	}

	/**
	 * Método invocado quando a ronda termina. É pedido ao utilizador input acerca
	 * da próxima ação a realizar: começar uma nova ronda, começar um novo tipo de
	 * jogo ou sair do programa.
	 */
	static void fimJogo() {
		String fimJogo;

		System.out.println("Play another round (1)\nStart new game (2)\nQuit (3)");
		fimJogo = sc.next();

		while (!"123".contains(fimJogo)) {
			System.out.println(MSG_NUM_INVALIDO);
			fimJogo = sc.next();
		}

		pontuacao = jogo.score();

		switch (fimJogo) {
		case "1":

			jogo.startNewRound();
			jahFoiDadaAjuda = false;
			System.out.println(jogo.toString());
			jogaJogo();
			break;

		case "2":
			escolherConfiguracoes();
			break;
		case "3":
			System.out.println("Bye!");
			System.exit(0);
		}
	}
}
