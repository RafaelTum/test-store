package com.arena.frontline.teststore.web.api.models;

import java.util.Objects;

public class AnswerModel {

    private String displayText;
    private boolean correct;

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnswerModel that = (AnswerModel) o;
        return Objects.equals(displayText, that.displayText) &&
                Objects.equals(correct, that.correct);
    }

    @Override
    public int hashCode() {

        return Objects.hash(displayText, correct);
    }
}
