package model.bean;

import java.math.BigInteger;
import java.nio.charset.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a user registered in the Game Hub system.
 * It holds information about the username, password, name, surname, address, city,
 * country, mail, sex, telephone, birthDate.
 */
public class User {

    /**
     * Constructs a new user, with empty fields.
     * These fields should be initialized to be consistent.
     */
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

    /**
     * Constructs a new user starting by username, name, surname, address, city, country,
     * birthDate, mail, sex, telephone.
     *
     * @param username the username of the user.
     * @param name the name of the user.
     * @param surname the surname of the user.
     * @param address the address of the user.
     * @param city the city of the user.
     * @param country the country of the user.
     * @param birthDate the birth date of the user.
     * @param mail the mail of the user.
     * @param sex the sex of the user(either 'M' or 'F').
     * @param telephone the telephone of the user.
     */
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

    /**
     * Constructs a new user starting by username, password, name, surname, address, city, country,
     * birthDate, mail, sex, telephone.
     *
     * @param username the username of the user.
     * @param password the password of the user, will be hash-encrypted before being saved.
     * @param name the name of the user.
     * @param surname the surname of the user.
     * @param address the address of the user.
     * @param city the city of the user.
     * @param country the country of the user.
     * @param birthDate the birth date of the user.
     * @param mail the mail of the user.
     * @param sex the sex of the user(either 'M' or 'F').
     * @param telephone the telephone of the user.
     */
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


    /**
     * Gets the username of the user.
     *
     * @return the username of the user.
     */
    public @NotNull String getUsername() {
        return username;
    }

    /**
     * Sets the new username of the user.
     *
     * @param username the new username of the user.
     */
    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    /**
     * Gets the hash-encrypted version of the password.
     *
     * @return the hash-encrypted version of the password.
     */
    public @NotNull String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets the new hash-encrypted version of the password.
     *
     * @param passwordHash the new hash-encrypted version of the password.
     */
    public void setPasswordHash(@NotNull String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Sets the new password of the user, that will be hash-encrypted before being saved.
     *
     * @param password the password of the user, will be hash-encrypted before being saved.
     */
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

    /**
     * Gets the name of the user.
     *
     * @return the name of the user.
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Sets the new name of the user.
     *
     * @param name the new name.
     */
    public void setName(@NotNull String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the user.
     *
     * @return the surname of the user.
     */
    public @NotNull String getSurname() {
        return surname;
    }

    /**
     * Sets the new surname of the user.
     *
     * @param surname the new surname.
     */
    public void setSurname(@NotNull String surname) {
        this.surname = surname;
    }

    /**
     * Gets the address of the user.
     *
     * @return the address of the user.
     */
    public @NotNull String getAddress() {
        return address;
    }

    /**
     * Sets the new address of the user.
     *
     * @param address the new address of the user.
     */
    public void setAddress(@NotNull String address) {
        this.address = address;
    }

    /**
     * Gets the city of the user.
     *
     * @return the city of the user.
     */
    public @NotNull String getCity() {
        return city;
    }

    /**
     * Sets the new city of the user.
     *
     * @param city the new city of the user.
     */
    public void setCity(@NotNull String city) {
        this.city = city;
    }

    /**
     * Gets the country of the user.
     *
     * @return the country of the user.
     */
    public @NotNull String getCountry() {
        return country;
    }

    /**
     * Sets the new country of the user.
     *
     * @param country the new country of the user.
     */
    public void setCountry(@NotNull String country) {
        this.country = country;
    }

    /**
     * Gets the birth date of the user.
     *
     * @return the birth date of the user.
     */
    public @NotNull String getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the new birth date of the user.
     *
     * @param birthDate the new birth date of the user.
     */
    public void setBirthDate(@NotNull String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Gets the mail of the user.
     *
     * @return the mail of the user.
     */
    public @NotNull String getMail() {
        return mail;
    }

    /**
     * Sets the new mail of the user.
     *
     * @param mail the new mail of the user.
     */
    public void setMail(@NotNull String mail) {
        this.mail = mail;
    }

    /**
     * Gets the sex of the user.
     *
     * @return the sex of the user.
     */
    public char getSex() {
        return sex;
    }

    /**
     * Sets the new sex of the user.
     *
     * @param sex the new birth date of the user(either 'M' or 'F').
     */
    public void setSex(char sex) {
        this.sex = sex;
    }

    /**
     * Gets the telephone of the user.
     *
     * @return the telephone of the user.
     */
    public @NotNull String getTelephone() {
        return telephone;
    }

    /**
     * Sets the new telehone of the user.
     *
     * @param telephone the new telephone of the user.
     */
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
