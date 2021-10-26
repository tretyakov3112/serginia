package com.sergey.demo;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RestController
public class ApiController {

    private LinkedList<Theme> themes;

    {
        themes = new LinkedList<>();
        themes.addLast(new Theme("Sergey"));
        themes.addLast(new Theme("Alexander"));
        themes.addLast(new Theme("Serginia"));
        themes.addLast(new Theme("La_Serj"));
    }

    /**
     * <b> На троечку </b>
     */

    //curl --get http://localhost:8080/themes?sort=update
    @GetMapping("themes")
    public List<Theme> getThemes(@RequestParam String sort) {
        if (sort.equals("update"))
            return themes.stream().sorted().collect(Collectors.toList());
        return themes;
    }

    //curl -X DELETE http://localhost:8080/themes/0
    @DeleteMapping("themes/{index}")
    public void removeAt(@PathVariable("index") Integer index) {
        themes.remove((int) index);
    }

    //curl -X POST http://localhost:8080/themes/create -H "Content-Type: application/json" -d "{\"name\":\"santader\"}"
    @PostMapping("themes/create")
    public void createTheme(@Validated @RequestBody Theme theme) {
        themes.addLast(theme);
    }


    //curl -X PUT http://localhost:8080/themes/update/0 -H "Content-Type: application/json" -d "{\"name\":\"santader\"}"
    @PutMapping("themes/update/{index}")
    public void updateTheme(@PathVariable("index") Integer index, @RequestBody Theme theme) {
        themes.get(index).update(theme);
    }

    //curl --get http://localhost:8080/themes/size
    @GetMapping("themes/size")
    public Integer getSize() {
        return themes.size();
    }

    //curl -X DELETE http://localhost:8080/themes/
    @DeleteMapping("themes")
    public void clear() {
        themes.clear();
    }

    //curl --get http://localhost:8080/themes/0
    @GetMapping("themes/{index}")
    public Theme get(@PathVariable("index") Integer index) {
        return themes.get(index);
    }

    /**
     * <b>На четвероочку</b>
     */

    //curl -X POST http://localhost:8080/themes/0/comments/create -H "Content-Type: application/json" -d "{\"text\":\"nice sergey\", \"author\":\"ivan\"}"
    @PostMapping("themes/{index}/comments/create")
    public void addComment(@PathVariable("index") Integer index, @Validated @RequestBody Comment comment) {
        themes.get(index).addComment(comment);
    }

    //curl -X DELETE http://localhost:8080/themes/0/comments/0
    @DeleteMapping("themes/{index1}/comments/{index2}")
    public void deleteComment(@PathVariable("index1") Integer themeIndex, @PathVariable("index2") Integer commentIndex) {
        themes.get(themeIndex).removeComment(commentIndex);
    }

    //curl -X PUT http://localhost:8080/themes/0/comments/0 -H "Content-Type: application/json" -d "{\"text\":\"serj\", \"author\":\"sasha\"}"
    @PutMapping("themes/{index1}/comments/{index2}")
    public void updateComment(@PathVariable("index1") Integer themeIndex, @PathVariable("index2") Integer commentIndex, @Validated @RequestBody Comment comment) {
        themes.get(themeIndex).updateComment(commentIndex, comment);
    }

    //curl --get http://localhost:8080/themes/0/comments?sort=update
    @GetMapping("themes/{index}/comments")
    public List<Comment> getComments(@PathVariable("index") Integer index, @RequestParam String sort) {
        if (sort.equals("update"))
            return themes.get(index).getComments().stream().sorted().collect(Collectors.toList());
        return themes.get(index).getComments();
    }


    /** <b>На пятерочку</b>*/

    // curl --get http://localhost:8080/ivan?sort=update
    @GetMapping("{username}")
    public List<Comment> getCommentsOfUser(@PathVariable("username") String username, @RequestParam String sort) {
        LinkedList<Comment> comments = new LinkedList<>();
        for (var theme : themes) {
            for (var comment : theme.getComments()) {
                if (comment.getAuthor().equals(username))
                    comments.addLast(comment);
            }
        }
        if (sort.equals("update"))
            return comments.stream().sorted().collect(Collectors.toList());
        return comments;
    }
    //curl -X PUT http://localhost:8080/ivan/0/0 -H "Content-Type: application/json" -d "{\"text\":\"nice serjant\"}"
    @PutMapping("{username}/{theme}/{comment}")
    public void updateCommentOfUser(@PathVariable("username") String username,
                                    @PathVariable("theme") Integer themeIndex,
                                    @PathVariable("comment") Integer commentIndex, @Validated@RequestBody Comment comment)
    {
        comment.setAuthor(username);
        themes.get(themeIndex).updateComment(commentIndex, comment);
    }

    // curl -X DELETE http://localhost:8080/ivan
    @DeleteMapping("{username}")
    public void deleteCommentsOfUser(@PathVariable("username") String username) {
        for (var theme : themes) {
            theme.getComments().removeIf(comment -> comment.getAuthor().equals(username));
        }
    }


}

