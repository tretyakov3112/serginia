package com.sergey.demo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.LinkedList;


public class Theme implements Comparable<Theme>{

    @Getter @Setter
    private String name;
    @Getter @JsonIgnore
    private final LinkedList<Comment> comments;
    @Getter @Setter @JsonIgnore
    private Calendar creatingDate;
    @Getter @Setter @JsonIgnore
    private Calendar updatingDate;

    {
        comments = new LinkedList<>();
        creatingDate = Calendar.getInstance();
        updatingDate = Calendar.getInstance();
    }

    public Theme(@JsonProperty("name") String name)
    {
        this.name = name;
    }

    public void addComment(Comment comment)
    {
        comments.addLast(comment);
    }

    public void removeComment(Integer commentIndex)
    {
        comments.remove((int) commentIndex);
    }

    public void updateComment(Integer commentIndex, Comment comment)
    {
        var com = comments.get(commentIndex);
        com.update(comment);
    }

    public void update(Theme theme)
    {
        updatingDate = Calendar.getInstance();
        name = theme.getName();
    }

    @Override
    public int compareTo(Theme o) {
        return  (updatingDate.getTimeInMillis() - o.getUpdatingDate().getTimeInMillis()) > 0 ? 1 : -1;
    }
}
