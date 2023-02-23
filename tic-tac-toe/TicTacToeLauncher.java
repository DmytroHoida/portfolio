import java.util.Arrays;
import java.util.Scanner;

public class TicTacToeLauncher {
    public static void main(String[] args) {
        char[] game = new char[Helper.LENGTH];
        Arrays.fill(game, ' ');
        boolean gameOver = false;
        String winner;
        char whoseTurn = 'X';
        Scanner scanner = new Scanner(System.in);

        while (!gameOver) {
            Helper.printGameBoard(game);

            boolean userInputValid = false;
            int indexOfCell = 0;

            do {
                String input = scanner.nextLine();

                if (!Helper.isLengthOfInputCorrect(input)) {
                    continue;
                } else {
                    char firstNumber = input.charAt(0), secondNumber = input.charAt(2);
                    if (!Helper.hasInputGot2ValidNumbers(firstNumber, secondNumber)) {
                        continue;
                    } else {
                        int firstCoordinate = Character.getNumericValue(firstNumber),
                                secondCoordinate = Character.getNumericValue(secondNumber);
                        switch (firstCoordinate) {
                            case 1 -> indexOfCell = secondCoordinate - 1;
                            case 2 -> indexOfCell = firstCoordinate + secondCoordinate;
                            case 3 -> indexOfCell = firstCoordinate + secondCoordinate + 2;
                        }
                        if(!Helper.isCellAvailable(game, indexOfCell)) {
                            continue;
                        }
                    }
                }
                userInputValid = true;
            } while (!userInputValid);

            if (whoseTurn == 'X') {
                game[indexOfCell] = 'X';
                whoseTurn = 'O';
            } else {
                game[indexOfCell] = 'O';
                whoseTurn = 'X';
            }

            int spaceNum = Helper.countSpaces(game);

            if (spaceNum <= Helper.MAX_NUM_OF_SPACES_FOR_VICTORY) {
                winner = Helper.checkWinner(game);

                if (winner != null) {
                    Helper.printGameBoard(game);
                    System.out.print(winner + " wins");
                    gameOver = true;
                } else if (spaceNum == 0) {
                    Helper.printGameBoard(game);
                    System.out.print("Draw");
                    gameOver = true;
                }
            }
        }
    }

    static class Helper {
        final static int LENGTH = 9, MAX_NUM_OF_SPACES_FOR_VICTORY = 4;
        static void printGameBoard(char[] game) {
            System.out.println("---------");
            for (int i = 0; i < LENGTH; i+= 3) {
                System.out.printf("| %c %c %c |%n", game[i], game[i+1], game[i+2]);
            }
            System.out.println("---------");
        }

        static boolean isLengthOfInputCorrect(String input) {
            if (input.length() != 3) {
                System.out.println("Incorrect format! " +
                        "The coordinates of the cell should be two digits separated with a single space, e.g. (1 1)");
                return false;
            } else {
                return true;
            }
        }

        static boolean hasInputGot2ValidNumbers (char firstNumber, char secondNumber) {
            if (!Character.isDigit(firstNumber) || !Character.isDigit(secondNumber)) {
                System.out.println("You should enter numbers!");
                return false;
            } else if (Character.getNumericValue(firstNumber) <= 0 || Character.getNumericValue(firstNumber) >= 4 ||
                    (Character.getNumericValue(secondNumber) <= 0 || Character.getNumericValue(secondNumber) >= 4)) {
                System.out.println("Coordinates should be from 1 to 3!");
                return false;
            } else {
                return true;
            }
        }

        static boolean isCellAvailable (char[] game, int indexOfCell) {
            if (game[indexOfCell] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
                return false;
            } else {
                return true;
            }
        }

        static String checkWinner(char[] game) {
            String winner = null; // The winner will be put in this string.

            // Check winner horizontally and vertically.
            for (int horizontalChar = 0, verticalChar = 0; horizontalChar < 9; horizontalChar += 3, ++verticalChar) {
                if (game[horizontalChar] == game[horizontalChar + 1] &&
                        game[horizontalChar + 1] == game[horizontalChar + 2]) {
                    if (game[horizontalChar] != ' ') {
                        winner = String.valueOf(game[horizontalChar]);
                    }
                } else if (game[verticalChar] == game[verticalChar + 3] &&
                        game[verticalChar + 3] == game[verticalChar + 6]) {
                    if (game[verticalChar] != ' ') {
                        winner = String.valueOf(game[verticalChar]);
                    }
                }
            }

            if (game[0] == game[4] && game[4] == game[8]) { // Check winner diagonally.
                if (game[0] != ' ') {
                    winner = String.valueOf(game[0]);
                }
            } else if (game[2] == game[4] && game[4] == game[6]) {
                if (game[2] != ' ') {
                    winner = String.valueOf(game[2]);
                }
            }

            return winner;
        }

        static int countSpaces(char[] game) {
            int spaceNum = 0;
            for (int i = 0; i < LENGTH; ++i) {
                if (game[i] == ' ') {
                    spaceNum++;
                }
            }
            return spaceNum;
        }
    }
}

