package gwhogg.models;

import java.util.ArrayList;
import java.util.List;

public class Condition {
    char[] digits;
    List<Character> digitsList = new ArrayList<>();
    int correctDigits;
    int correctPositions;

    public Condition(char[] digits, int correctDigits, int correctPositions) {
        this.digits = digits;
        this.correctPositions = correctPositions;
        this.correctDigits = correctDigits;
        for (char digit : digits) {
            digitsList.add(digit);
        }
    }

    public char[] getDigits() {
        return digits;
    }

    public void setDigits(char[] digits) {
        this.digits = digits;
    }

    public int getCorrectDigits() {
        return correctDigits;
    }

    public void setCorrectDigits(int correctDigits) {
        this.correctDigits = correctDigits;
    }

    public int getCorrectPositions() {
        return correctPositions;
    }

    public void setCorrectPositions(int correctPositions) {
        this.correctPositions = correctPositions;
    }

    public List<Character> getDigitsList() {
        return digitsList;
    }

    public void setDigitsList(List<Character> digitsList) {
        this.digitsList = digitsList;
    }
}
