package model.bean;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * Moderator is a specialization of User which represents a moderator of the GameHub platform.
 * Moderator it's capable of much more operations than the normal User.
 */
public class Moderator extends User {

    /**
     * Constructs a new Moderator with empty fields.
     */
    public Moderator() {
        this.contractTime = "";
    }

    /**
     * Constructs a new Moderator starting from a user, and the contractTime.
     * Neither of the params should be null.
     *
     * @param u the user corresponding to the moderator
     * @param contractTime the contract expiration date
     */
    public Moderator(User u, String contractTime) {
        super(u.getUsername(), u.getName(), u.getSurname(), u.getAddress(),
                u.getCity(), u.getCountry(), u.getBirthDate(), u.getMail(), u.getSex(),
                u.getTelephone());
        this.contractTime = contractTime;
        this.setPasswordHash(Objects.requireNonNull(u.getPasswordHash()));
    }

    /**
     * Constructs a new Moderator starting from the user standard attributes,
     * plus the contractTime.
     * Neither of the params should be null.
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
     */
    public Moderator(String username, String password, String name, String surname, String address,
                     String city, String country, String birthDate, String mail, char sex,
                     String telephone, String contractTime) {

        super(username, password, name, surname, address, city, country, birthDate, mail, sex,
                telephone);
        this.contractTime = contractTime;
    }


    // constructor by a 'Moderator' instance.
    public Moderator(Moderator m) {
        super(m.getUsername(), m.getName(), m.getSurname(), m.getAddress(),
                m.getCity(), m.getCountry(), m.getBirthDate(), m.getMail(), m.getSex(),
                m.getTelephone());
        this.contractTime = m.getContractTime();
    }

    /**
     * Get the moderator's contract time.
     *
     * @return a String the contract expiration date.
     */
    public String getContractTime() {
        return this.contractTime;
    }


    /**
     * Set a new contract time for the moderator
     *
     * @param contractTime a String the contract expiration date, must be not null.
     */
    public void setContractTime(@NotNull String contractTime) {
        this.contractTime = contractTime;
    }

    @Override
    public String toString() {
        return super.toString() + "Moderator{"
                + "contractTime='" + contractTime + '\''
                + '}';
    }

    @NotNull
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Moderator)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Moderator moderator = (Moderator) o;
        return Objects.equals(this.getContractTime(), moderator.getContractTime());
    }


    private String contractTime;
}
