package com.visita.models;

public class Medecin
{
    private int id ;
    private String fullname;
    private String username;
    private String email;
    private int phone ;
    private String password;
    private boolean token ;
    private String photo;
    private int code ;
    private String role;
    private String specialite;
    private String address;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Medecin(int id) {
        this.id = id;
    }

    public Medecin(int id, String fullname) {
        this.id = id;
        this.fullname = fullname;
    }

    public Medecin(int id, String fullname, String username, String email, int phone, String password, boolean token, String photo, int code, String role, String specialite, String address) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.token = token;
        this.photo = photo;
        this.code = code;
        this.role = role;
        this.specialite = specialite;
        this.address = address;
    }
}
