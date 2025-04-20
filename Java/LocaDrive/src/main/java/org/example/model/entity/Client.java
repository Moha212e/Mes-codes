package org.example.model.entity;

import com.google.gson.annotations.Expose;
import java.time.LocalDate;

/**
 * Classe représentant un client dans le système
 */
public class Client extends AbstractEntity implements Identifiable {
    
    private int idClient;
    
    private String name;
    
    private String surname;
    
    private String email;
    
    private String phoneNumber; // Numéro de téléphone
    
    private LocalDate birthDate;
    
    private String licenseNumber; // Numéro de permis
    
    private String address; // Adresse

    /**
     * Constructeur par défaut
     */
    public Client() {
    }

    /**
     * Constructeur avec les attributs de base
     */
    public Client(int idClient, String name, String surname, String email, LocalDate birthDate) {
        this.idClient = idClient;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
    }

    /**
     * Constructeur complet avec tous les attributs
     */
    public Client(int idClient, String name, String surname, String email, String phoneNumber, 
                 LocalDate birthDate, String licenseNumber, String address) {
        this.idClient = idClient;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.licenseNumber = licenseNumber;
        this.address = address;
    }

    // Getters et setters
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public Object getId() {
        return idClient;
    }

    @Override
    public String toString() {
        return "Client{" +
               "idClient=" + idClient +
               ", name='" + name + '\'' +
               ", surname='" + surname + '\'' +
               ", email='" + email + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", birthDate=" + birthDate +
               ", licenseNumber='" + licenseNumber + '\'' +
               ", address='" + address + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return idClient == client.idClient;
    }

    @Override
    public int hashCode() {
        return idClient;
    }
}
