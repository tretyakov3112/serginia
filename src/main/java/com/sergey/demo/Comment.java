package com.sergey.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter @Setter
public class Comment implements Comparable<Comment>{

    private String text;
    private String author;
    @Getter @Setter @JsonIgnore
    private Calendar creatingDate;
    @Getter @Setter @JsonIgnore
    private Calendar updatingDate;

    {
        creatingDate = Calendar.getInstance();
        updatingDate = Calendar.getInstance();
    }

    public Comment(@JsonProperty("text") String text)
    {
        this(text, "");
    }

    public Comment(String text, String author)
    {
        this.text = text;
        this.author = author;
    }

    public void update(Comment comment)
    {
        updatingDate = Calendar.getInstance();
        text = comment.getText();
        author = comment.getAuthor();
    }

    @Override
    public int compareTo(Comment o) {
        return  (updatingDate.getTimeInMillis() - o.getUpdatingDate().getTimeInMillis()) > 0 ? 1 : -1;
    }
}
