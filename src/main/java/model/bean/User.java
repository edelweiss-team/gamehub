package model.bean;

import java.math.BigInteger;
import java.nio.charset.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class User {

    public User() { }

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

    public @Nullable String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    public @Nullable String getPasswordHash() {
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

    public @Nullable String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @Nullable String getSurname() {
        return surname;
    }

    public void setSurname(@NotNull String surname) {
        this.surname = surname;
    }

    public @Nullable String getAddress() {
        return address;
    }

    public void setAddress(@NotNull String address) {
        this.address = address;
    }

    public @Nullable String getCity() {
        return city;
    }

    public void setCity(@NotNull String city) {
        this.city = city;
    }

    public @Nullable String getCountry() {
        return country;
    }

    public void setCountry(@NotNull String country) {
        this.country = country;
    }

    public @Nullable String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(@NotNull String birthDate) {
        this.birthDate = birthDate;
    }

    public @Nullable String getMail() {
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

    public @Nullable String getTelephone() {
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
    public boolean equals(@Nullable Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Nullable
    private String username;
    @Nullable
    private String passwordHash;
    @Nullable
    private String name;
    @Nullable
    private String surname;
    @Nullable
    private String address;
    @Nullable
    private String city;
    @Nullable
    private String country;
    @Nullable
    private String birthDate;
    @Nullable
    private String mail;
    private char sex;
    @Nullable
    private String telephone;
}
