package org.example.model.entity;

import java.util.Date;

public class Client {
    private int idClient;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Date birthDate;
    private boolean isDeteled;

    public Client(int idClient, String name, String surname, String email, String password, Date birthDate, boolean isDeteled) {
        this.idClient = idClient;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
    }
    public Client() {
    }
    public int getIdClient() {
        return idClient;
    }
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Date getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    public boolean isDeteled() {
        return isDeteled;
    }
    public void setDeteled(boolean isDeteled) {
        this.isDeteled = isDeteled;
    }
    @Override
    public String toString() {
        return "Client{" + "idClient=" + idClient + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password=" + password + ", birthDate=" + birthDate + ", isDeteled=" + isDeteled + '}';
    }


}
