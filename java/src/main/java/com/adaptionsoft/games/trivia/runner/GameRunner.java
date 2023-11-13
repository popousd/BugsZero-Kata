
package com.adaptionsoft.games.trivia.runner;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;


public class GameRunner {

	List<String> playerNames = Arrays.asList("Chet", "Pat", "Sue");

	private static boolean notAWinner;

	public static void main(String[] args) {
		playGame();
	}

	public static void playGame() {
		Random rand = new Random();
		Game aGame = new Game();

		//Pour ajouter des membres dans faire un add a chaque fois
		for (String playerName : playerNames) {
			aGame.add(playerName);
		}

		do {

			aGame.roll(rand.nextInt(5) + 1);

			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}



		} while (notAWinner);
	}
}
