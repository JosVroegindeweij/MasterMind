package com.example.josvr.mastermind;

import java.util.Arrays;
import java.util.Random;

class MasterMindPuzzle {
    private int[] code;
    private final int totalRows = 10;
    private final int totalCols = 4;
    private int selectedRow;
    private int selectedCol;
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
        for (int[] cg : guessesColor)
            Arrays.fill(cg, playActivity.getResources().getColor(R.color.gray));
        for (int[] cf : feedbackColor)
            Arrays.fill(cf, playActivity.getResources().getColor(R.color.gray));
        selectedRow = 0;
        selectedCol = 0;
    }

    /**
     * generates a random color.
     *
     * @return random color
     */
    private int randomColor() {
        int black = playActivity.getResources().getColor(R.color.black);
        int white = playActivity.getResources().getColor(R.color.white);
        int green = playActivity.getResources().getColor(R.color.green);
        int yellow = playActivity.getResources().getColor(R.color.yellow);
        int red = playActivity.getResources().getColor(R.color.red);
        int blue = playActivity.getResources().getColor(R.color.blue);
        int[] colors = {black, white, green, yellow, red, blue};
        int pick = new Random().nextInt(6);
        return colors[pick];
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
                addFirstEmpty(feedbackColor[selectedRow], playActivity.getResources().getColor(R.color.black));
                codePlaces[i] = true;
                attemptPlaces[i] = true;
            }
        }

        // Checking for white feedback pins.
        for (int i = 0; i < 4; i++)
            if (!codePlaces[i])
                for (int j = 0; j < 4; j++)
                    if (!attemptPlaces[j] && code[i] == guess[j]) {
                        addFirstEmpty(feedbackColor[selectedRow], playActivity.getResources().getColor(R.color.white));
                        attemptPlaces[j] = true;
                        break;
                    }
    }

    /**
     * inserts element {@code e} at first empty index of {@code a}.
     * Returns {@code true} if element is added.
     *
     * @param array   array
     * @param element element
     */
    private void addFirstEmpty(int[] array, int element) {
        for (int i = 0; i < array.length; i++)
            if (array[i] == playActivity.getResources().getColor(R.color.gray)) {
                array[i] = element;
                return;
            }
    }

    void checkVictory() {
        if (feedbackColor[selectedRow][totalCols - 1] == playActivity.getResources().getColor(R.color.black)) {
            victory();
        }
    }

    void checkLoss() {
        if (selectedRow == 10) {
            loss();
        }
    }

    void resetRow() {
        Arrays.fill(guessesColor[selectedRow], playActivity.getResources().getColor(R.color.gray));
    }

    /**
     * Displays victory message.
     */
    private void victory() {
        System.out.println("Not Implemented Yet!");
    }

    /**
     * Displays loss message.
     */
    private void loss() {
        System.out.println("Not Implemented Yet!");
    }

    int getColor(String gof, int col) {
        switch (gof) {
            case "feedback":
                return feedbackColor[selectedRow][col];
            case "guess":
                return guessesColor[selectedRow][col];
        }
        return -1;
    }

    void setColor(int color) {
        guessesColor[selectedRow][selectedCol] = color;
        playActivity.updateColor(selectedRow, selectedCol);
    }

    boolean isComplete() {
        for (int c : guessesColor[selectedRow])
            if (c == playActivity.getResources().getColor(R.color.gray)) return false;
        return true;
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

    void setSelectedColNextFreeCol() {
        int col = findFirstFreeColAfterCurrentCol();
        if (col != -1) {
            setSelectedCol(col);
            return;
        } else {
            col = findFirstFreeCol();
        }
        if (col != -1) setSelectedCol(col);
    }

    private int findFirstFreeColAfterCurrentCol() {
        for (int i = selectedCol; i < guessesColor[selectedRow].length; i++) {
            if (guessesColor[selectedRow][i] == playActivity.getResources().getColor(R.color.gray))
                return i;
        }
        return -1;
    }

    private int findFirstFreeCol() {
        for (int i = 0; i < selectedCol; i++) {
            if (guessesColor[selectedRow][i] == playActivity.getResources().getColor(R.color.gray))
                return i;
        }
        return -1;
    }

    int incSelectedRow() {
        return ++selectedRow;
    }
}
