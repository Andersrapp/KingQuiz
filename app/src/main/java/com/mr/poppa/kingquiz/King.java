package com.mr.poppa.kingquiz;

/**
 * Created by Benjamin and Anders on 4/16/2015.
 */
public class King {
    private Integer id;
    private String name;
    private String reign;
    private String imageId;
    private boolean answered;
    private Answer answer = new Answer();

    public King(Integer id, String name, String reign, String imageId) {
        this.id = id;
        this.name = name;
        this.reign = reign;
        this.imageId = imageId;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReign() {
        return reign;
    }

    public void setReign(String reign) {
        this.reign = reign;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    @Override
    public String toString() {
        return "King{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof King)) return false;

        King king = (King) o;

        if (answered != king.answered) return false;
        if (answer != null ? !answer.equals(king.answer) : king.answer != null) return false;
        if (!id.equals(king.id)) return false;
        if (imageId != null ? !imageId.equals(king.imageId) : king.imageId != null) return false;
        if (name != null ? !name.equals(king.name) : king.name != null) return false;
        if (reign != null ? !reign.equals(king.reign) : king.reign != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (reign != null ? reign.hashCode() : 0);
        result = 31 * result + (imageId != null ? imageId.hashCode() : 0);
        result = 31 * result + (answered ? 1 : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }
}