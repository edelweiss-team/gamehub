package model.bean;

import java.math.BigInteger;
import java.nio.charset.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class User {

    public User() {
        this.address = "";
        this.name = "";
        this.surname = "";
        this.username = "";
        this.city = "";
        this.country = "";
        this.birthDate = "";
        this.mail = "";
        this.sex = ' ';
        this.telephone = "";
        this.passwordHash = "";
    }

    public User(@NotNull String username, @NotNull String name, @NotNull String surname,
                @NotNull String address, @NotNull String city, @NotNull String country,
                @NotNull String birthDate, @NotNull String mail, char sex,
                @NotNull String telephone) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.city = city;
        this.country = country;
        this.birthDate = birthDate;
        this.mail = mail;
        this.sex = sex;
        this.telephone = telephone;
        this.passwordHash = "";
    }

    public User(@NotNull String username, @NotNull String password, @NotNull String name,
                @NotNull String surname, @NotNull String address, @NotNull String city,
                @NotNull String country, @NotNull String birthDate, @NotNull String mail,
                char sex, @NotNull String telephone) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.city = city;
        this.country = country;
        this.birthDate = birthDate;
        this.mail = mail;
        this.sex = sex;
        this.telephone = telephone;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            this.passwordHash = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    public @NotNull String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(@NotNull String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPassword(@NotNull String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            this.passwordHash = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getSurname() {
        return surname;
    }

    public void setSurname(@NotNull String surname) {
        this.surname = surname;
    }

    public @NotNull String getAddress() {
        return address;
    }

    public void setAddress(@NotNull String address) {
        this.address = address;
    }

    public @NotNull String getCity() {
        return city;
    }

    public void setCity(@NotNull String city) {
        this.city = city;
    }

    public @NotNull String getCountry() {
        return country;
    }

    public void setCountry(@NotNull String country) {
        this.country = country;
    }

    public @NotNull String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@NotNull String birthDate) {
        this.birthDate = birthDate;
    }

    public @NotNull String getMail() {
        return mail;
    }

    public void setMail(@NotNull String mail) {
        this.mail = mail;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public @NotNull String getTelephone() {
        return telephone;
    }

    public void setTelephone(@NotNull String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "User{"
                + "username='" + username + '\'' + ", password='" + passwordHash + '\''
                + ", name='" + name + '\'' + ", surname='" + surname + '\'' + ", address='"
                + address + '\'' + ", city='" + city + '\'' + ", country='" + country + '\''
                + ", birthDate='" + birthDate + '\'' + ", mail='" + mail + '\'' + ", sex="
                + sex + ", telephone='" + telephone + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(username, user.username);
    }


    @NotNull
    private String username;
    @NotNull
    private String passwordHash;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String address;
    @NotNull
    private String city;
    @NotNull
    private String country;
    @NotNull
    private String birthDate;
    @NotNull
    private String mail;
    private char sex;
    @NotNull
    private String telephone;
}
