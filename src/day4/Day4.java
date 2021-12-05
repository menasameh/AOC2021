package day4;

import utils.FilesUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 {

    public void smallSol() {
        Game game = getInput();

        do {
            game.runTurn();
            Board winner = game.getWinner();
            if(winner != null) {
                System.out.println(winner.calcScore());
                return;
            }

        } while(game.hasTurns());
    }

    public void largeSol() {
        Game game = getInput();
        int lastScore = 0;

        do {
            game.runTurn();
            List<Board> winners = game.getWinners();
            for(Board b: winners) {
                lastScore = b.calcScore();
                game.removeBoard(b);
            }
        } while(game.hasTurns());
        System.out.println(lastScore);
    }

    Game getInput() {
        String input = FilesUtil.getContentOf("src/day4/input");
        List<String> lines = Arrays.stream(input.split("\n")).toList();

        List<Integer> numbers = Arrays.stream(lines.get(0).split(",")).map(Integer::valueOf).toList();
        // empty line before
        int numOfBoards = (lines.size() - 1)/6;
        List<Board> boards = new ArrayList<>();

        for(int i=0;i<numOfBoards;i++) {
            List<String> boardInput = new ArrayList<>();
            for(int j=0;j<5;j++) {
                boardInput.add(lines.get(j+2+i*6));
            }
            boards.add(new Board(boardInput));
        }

        return new Game(numbers, boards);
    }
}

class Game {
    int index;
    List<Integer> numbers;
    List<Board> boards;

    Game(List<Integer> numbers,  List<Board> boards) {
        index = 0;
        this.numbers = numbers;
        this.boards = boards;
    }

    boolean hasTurns() {
        return index < numbers.size();
    }

    void runTurn() {
        for(Board b: boards) {
            b.markNumber(numbers.get(index));
        }
        index++;
    }

    Board getWinner() {
        for(Board b: boards) {
            if(b.didWin) {
                return b;
            }
        }
        return null;
    }

    List<Board> getWinners() {
        return boards.stream().filter(item -> item.didWin).toList();
    }

    void removeBoard(Board board) {
        boards.remove(board);
    }

}

class Board {
    Integer winningNumber;
    boolean didWin;
    Integer[][] board;

    Board(List<String> boardInput) {
        didWin = false;
        board = new Integer[5][5];
        for(int i=0;i<5;i++) {
            board[i] = Arrays.stream(boardInput.get(i).trim().split(" +")).map(Integer::valueOf).toArray(Integer[]::new);
        }
    }

    void markNumber(Integer number) {
        for(int i=0;i<5;i++) {
            for(int j=0;j<5;j++) {
                if(number.equals(board[i][j])) {
                    board[i][j] = null;
                    boolean rowWin = true;
                    for(int k=0;k<5;k++) {
                        if (board[i][k] != null) {
                            rowWin = false;
                            break;
                        }
                    }

                    boolean colWin = true;
                    for(int k=0;k<5;k++) {
                        if (board[k][j] != null) {
                            colWin = false;
                            break;
                        }
                    }

                    didWin = rowWin || colWin;
                    if(didWin) {
                        winningNumber = number;
                    }
                }
            }
        }
    }

    int calcScore() {
        int sum =0;
        for(int i=0;i<5;i++) {
            for (int j = 0; j < 5; j++) {
                if(board[i][j] != null) {
                    sum += board[i][j];
                }
            }
        }
        return sum*winningNumber;
    }
}