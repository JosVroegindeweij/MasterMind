package com.example.josvr.mastermind;

import android.support.v4.content.ContextCompat;

import java.util.Arrays;
import java.util.Random;

class MasterMindPuzzle {
    private int[] code;
    private final int totalRows = 10;
    private final int totalCols = 4;
    private int selectedRow = 0;
    private int selectedCol = 0;
    private int[][] guessesColor;
    private int[][] feedbackColor;
    private PlayActivity playActivity;

    /**
     * Generates new MasterMind puzzle
     */
    MasterMindPuzzle(PlayActivity pa) {
        this.playActivity = pa;
        code = new int[4];
        for (int i = 0; i < code.length; i++) {
            code[i] = randomColor();
        }

        guessesColor = new int[totalRows][totalCols];
        feedbackColor = new int[totalRows][totalCols];
        Arrays.stream(guessesColor).forEach(s -> Arrays.fill(s, ContextCompat.getColor(playActivity, R.color.gray)));
        Arrays.stream(feedbackColor).forEach(s -> Arrays.fill(s, ContextCompat.getColor(playActivity, R.color.gray)));
    }

    /**
     * generates a random color.
     *
     * @return random color
     */
    private int randomColor() {
        int black = ContextCompat.getColor(playActivity, R.color.black);
        int white = ContextCompat.getColor(playActivity, R.color.white);
        int green = ContextCompat.getColor(playActivity, R.color.green);
        int yellow = ContextCompat.getColor(playActivity, R.color.yellow);
        int red = ContextCompat.getColor(playActivity, R.color.red);
        int blue = ContextCompat.getColor(playActivity, R.color.blue);
        int[] colors = {black, white, green, yellow, red, blue};
        return colors[new Random().nextInt(6)];
    }

    @Override
    public String toString() {
        return String.format("%s", Arrays.toString(code));
    }

    /**
     * This function checks the correctness of the guessed code.
     * The feedback color array is updated accordingly.
     *
     * @param row row where to find guessed code
     */
    void checkAttempt(int row) {
        int nrCols = code.length;
        int[] guess = guessesColor[row];

        // The following boolean arrays are used for keeping track of colors that have already been matched successfully.
        // If for example a green guess pin is in the correct place, that specific column in codePlaces gets set to true, and cant be matched for a white feedback pin anymore.
        boolean[] attemptPlaces = new boolean[nrCols];
        boolean[] codePlaces = new boolean[nrCols];

        // Checking for black feedback pins.

        for (int i = 0; i < 4; i++) {
            if (code[i] == guess[i]) {
                addFirstEmpty(feedbackColor[selectedRow], ContextCompat.getColor(playActivity, R.color.black));
                codePlaces[i] = true;
                attemptPlaces[i] = true;
            }
        }

        // Checking for white feedback pins.
        for (int i = 0; i < 4; i++)
            if (!codePlaces[i])
                for (int j = 0; j < 4; j++)
                    if (!attemptPlaces[j] && code[i] == guess[j]) {
                        addFirstEmpty(feedbackColor[selectedRow], ContextCompat.getColor(playActivity, R.color.white));
                        attemptPlaces[j] = true;
                        break;
                    }
    }

    /**
     * inserts element {@code e} at first empty index of {@code a}.
     * Returns {@code true} if element is added.
     *
     * @param arr   array
     * @param element element
     */
    private void addFirstEmpty(int[] arr, int element) {
        for (int i = 0; i < arr.length; i++)
            if (arr[i] == ContextCompat.getColor(playActivity, R.color.gray)) {
                arr[i] = element;
                return;
            }
    }

    /**
     * Checks whether the player has succeeded in the game.
     *
     * @return {@code true} if game is won, {@code false} otherwise.
     */
    boolean checkVictory() {
        return feedbackColor[selectedRow][totalCols - 1] == ContextCompat.getColor(playActivity, R.color.black);
    }

    /**
     * Checks whether the player has failed in the game.
     *
     * @return {@code true} if game is lost, {@code false} otherwise.
     */
    boolean checkLoss() {
        return selectedRow == 10;
    }

    /**
     * Resets the colors in the selected row of guesses.
     */
    void resetRow() {
        Arrays.fill(guessesColor[selectedRow], ContextCompat.getColor(playActivity, R.color.gray));
    }

    /**
     * Returns color code of either guess or feedback color array (decided by {@code feedback} at position {@code col}.
     *
     * @param gof either "feedback" or "guess" to denote which color array to look in
     * @param col col
     * @return color code
     */
    int getColor(String gof, int col) {
        switch (gof) {
            case "feedback":
                return feedbackColor[selectedRow][col];
            case "guess":
                return guessesColor[selectedRow][col];
        }
        return -1;
    }

    /**
     * Changes the color in guess array at selected position.
     *
     * @param color color
     */
    void setColor(int color) {
        guessesColor[selectedRow][selectedCol] = color;
        playActivity.updateColor(selectedRow, selectedCol);
    }

    /**
     * Checks if all 4 colors of the code have been entered.
     *
     * @return {@code true} if 4 colors are provided, {@code false} otherwise
     */
    boolean isComplete() {
        return !Arrays.stream(guessesColor[selectedRow]).anyMatch(s -> s==ContextCompat.getColor(playActivity, R.color.gray));
    }

    int getSelectedCol() {
        return selectedCol;
    }

    int getSelectedRow() {
        return selectedRow;
    }

    int getTotalCols() {
        return totalCols;
    }

    int getTotalRows() {
        return totalRows;
    }

    void setSelectedCol(int selectedCol) {
        this.selectedCol = selectedCol;
    }

    /**
     * Sets selected column to the next free column.
     * If there is no free column after current selected column, selected column will be set to first free column.
     * If there is no free column, will not change selected column.
     */
    void setSelectedColNextFreeCol() {
        int col = findFirstFreeColAfterCurrentCol();
        if (col != -1) {
            setSelectedCol(col);
            return;
        } else {
            col = findFirstFreeCol();
            if (col != -1) setSelectedCol(col);
        }
    }

    /**
     * looks for first unfilled column after currently selected column.
     *
     * @return index of first free column, -1 if no such exists.
     */
    private int findFirstFreeColAfterCurrentCol() {
        for (int i = selectedCol; i < guessesColor[selectedRow].length; i++) {
            if (guessesColor[selectedRow][i] == ContextCompat.getColor(playActivity, R.color.gray))
                return i;
        }
        return -1;
    }

    /**
     * looks for first unfilled column.
     *
     * @return index of first free column, -1 if no such exists.
     */
    private int findFirstFreeCol() {
        for (int i = 0; i < selectedCol; i++) {
            if (guessesColor[selectedRow][i] == ContextCompat.getColor(playActivity, R.color.gray))
                return i;
        }
        return -1;
    }

    /**
     * increments selected row by 1.
     *
     * @return new selected row.
     */
    int incSelectedRow() {
        return ++selectedRow;
    }
}
