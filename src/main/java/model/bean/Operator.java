package model.bean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Operator is a specialization of User which represents a operator of the GameHub platform.
 * Moderator it's capable of much more operations than the normal User.
 * such as confirming orders.
 */

public class Operator extends User {

    /**
     * Constructs a new Moderator with empty fields.
     */
    public Operator() {
        this.contractTime = "";
        this.cv = "";
    }

    /**
     * Constructs a new Moderator starting from a user,
     * the contractTime, and the curriculum .
     * None of the params should be null.
     *
     * @param u the user corresponding to the moderator
     * @param contractTime the contract expiration date
     * @param cv the curriculum vitae of the moderator
     */
    public Operator(User u, @NotNull String contractTime, @NotNull String cv) {
        super(u.getUsername(), u.getName(), u.getSurname(), u.getAddress(),
            u.getCity(), u.getCountry(), u.getBirthDate(), u.getMail(), u.getSex(),
            u.getTelephone());
        this.contractTime = contractTime;
        this.cv = cv;
        this.setPasswordHash(u.getPasswordHash());
    }

    /**
     * Constructs a new Operator starting from the user standard attributes,
     * plus the contractTime and the curriculum.
     * None of the params should be null.
     *
     * @param username the admin's username
     * @param password the admin's password
     * @param name the admin's name
     * @param surname the admin's surname
     * @param address the admin's address
     * @param city the city of residence of the admin
     * @param country the country of the admin
     * @param birthDate the admin's birth date
     * @param mail the admin's mail
     * @param sex specifies the sex of the Admin with one letter (M,F)
     * @param telephone password is the password that the Admin uses to Log in the site
     * @param contractTime the contract expiration date
     * @param cv the curriculum vitae of the moderator
     */
    public Operator(@NotNull String username, @NotNull String password, @NotNull String name,
                    @NotNull String surname, @NotNull String address, @NotNull String city,
                    @NotNull String country, @NotNull String birthDate, @NotNull String mail,
                    char sex, @NotNull String telephone, @NotNull String contractTime,
                    @NotNull String cv) {

        super(username, password, name, surname, address, city, country, birthDate, mail, sex,
                telephone);
        this.contractTime = contractTime;
        this.cv = cv;

    }

    /**
     * Get the contract expiration date of the Operator.
     *
     * @return a String indicating the expiration date of the Operator
     */
    @NotNull
    public String getContractTime() {
        return this.contractTime;
    }

    /**
     * Get the curriculum vitae of the Operator.
     *
     * @return a String indicating the Operator's curriculum.
     */
    @NotNull
    public String getCv() {
        return this.cv;
    }

    /**
     * Set new contract expiration date of the Operator.
     *
     * @param contractTime a String indicating the expiration date of the Operator, must be not null.
     */
    public void setContractTime(@NotNull String contractTime) {
        this.contractTime = contractTime;
    }

    /**
     * Set new Operator's curriculum.
     *
     * @param cv a String indicating the curriculum vitae of the Operator, must be not null.
     */
    public void setCv(@NotNull String cv) {
        this.cv = cv;
    }

    @Override
    @NotNull
    public String toString() {
        return super.toString() + "Operator{"
                + "contractTime='" + contractTime + '\''
                + ", cv='" + cv + '\'' + '}';
    }

    @NotNull
    private String contractTime;
    @NotNull
    private String cv;
}
