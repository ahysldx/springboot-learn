package com.ldx.redis;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class User implements Serializable {

    private static final long serialVersionUID = 7669813543204580984L;
    private String name;
    private String id;
    public User(String name) {
        this.name = name;
    }

    public User() {
        this.name = new Date().toString();
        this.id = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

