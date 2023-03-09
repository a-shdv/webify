package com.example.webify.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "posts")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tag")
    private String tag;
    @Column(name = "text")
    private String text;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public String getUsername() {
        return user != null ?  user.getUsername() : "<none>";
    }

    public Post(String tag, String text, User user) {
        this.tag = tag;
        this.text = text;
        this.user = user;
    }
}
