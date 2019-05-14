package hng.tech.apoe_4.models;

public class AnswerState {
    String answerText;
    int chosen;

    public AnswerState(String answerText, int chosen) {
        this.answerText = answerText;
        this.chosen = chosen;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getChosen() {
        return chosen;
    }

    public void setChosen(int chosen) {
        this.chosen = chosen;
    }
}
