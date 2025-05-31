package model;

import java.io.Serializable;

public class Professor implements Serializable {
    private String id;
    private String idType;
    private String fullName;
    private String email;

    public Professor(String id, String idType, String fullName, String email) {
        this.id = id;
        this.idType = idType;
        this.fullName = fullName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Professor[" + "id='" + id + "- fullName='" + fullName + "- email='" + email + ']';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}