package com.zjcoding.entity;

import org.apache.ibatis.domain.blog.Section;

import java.io.Serializable;

/**
 * @author ZhangJun
 * @date 2023/1/4 09:37
 */
public class AuthorEntity implements Serializable {

    private static final long serialVersionUID = -6709911622897236797L;

    private int id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private Section favouriteSection;

    public AuthorEntity() {
        this(-1, null, null, null, null, null);
    }

    public AuthorEntity(Integer id, String username, String password, String email, String bio, Section section) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.favouriteSection = section;
    }

    public AuthorEntity(int id) {
        this(id, null, null, null, null, null);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setFavouriteSection(Section favouriteSection) {
        this.favouriteSection = favouriteSection;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public Section getFavouriteSection() {
        return favouriteSection;
    }

    @Override
    public String toString() {
        return "AuthorEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
