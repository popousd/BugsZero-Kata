package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[8];
    int[] purses  = new int[8];
    boolean[] inPenaltyBox  = new boolean[8];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();
    
    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

	private static final int QUESTION_COUNT = 50;
    
    public  Game(){
    	for (int i = 0; i < QUESTION_COUNT; i++) {
			addQuestions(popQuestions, "Pop Question ", i);
			addQuestions(scienceQuestions, "Science Question ", i);
			addQuestions(sportsQuestions, "Sports Question ", i);
			addQuestions(rockQuestions, "Rock Question ", i);
    	}
    }

	private void addQuestions(LinkedList<String> questionList, String type, int index) {
		questionList.addLast(type + index);
	}

	public boolean isPlayable() {
		return (howManyPlayers() < 8);
	}

	public void add(String playerName) {

		if(isPlayable()){
			players.add(playerName);
			places[howManyPlayers()] = 0;
			purses[howManyPlayers()] = 0;
			inPenaltyBox[howManyPlayers()] = false;

			System.out.println(playerName + " was added");
			System.out.println("They are player number " + players.size());
		}

		return System.out.println("This game is limited to 8 players");
	}
	
	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				movePlayerAndAskQuestion(roll);
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {

			movePlayerAndAskQuestion(roll);
		}
		
	}

	private void movePlayerAndAskQuestion(int roll) {
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

		System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
		askQuestion();
	}

	private void askQuestion() {
		switch (currentCategory()){
			case "Pop":
				System.out.println(popQuestions.poll());
			case "Science":
				System.out.println(scienceQuestions.poll());
			case "Sports":
				System.out.println(sportsQuestions.poll());
			case "Rock":
				System.out.println(rockQuestions.poll());
		}
	}
	
	
	private String currentCategory() {
		switch (places[currentPlayer]) {
			case 0:
			case 4:
			case 8:
				return "Pop";
			case 1:
			case 5:
			case 9:
				return "Science";
			case 2:
			case 6:
			case 10:
				return "Sports";
			default:
				return "Rock";
		}
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]) {
			if (isGettingOutOfPenaltyBox) {
				handleCorrectAnswer();
			}
		} else {
			handleCorrectAnswer();
		}

		currentPlayer = (currentPlayer + 1) % players.size();
		return didPlayerWin();
	}

	private void handleCorrectAnswer() {
		System.out.println("Answer was correct!!!!");
		purses[currentPlayer]++;
		System.out.println(players.get(currentPlayer) +
				" now has " +
				purses[currentPlayer] +
				" Gold Coins.");
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer = (currentPlayer + 1) % players.size();
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}
}
