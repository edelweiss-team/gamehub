package model.bean;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * Moderator is a specialization of {@link User} which represents a moderator
 * of the GameHub platform. A Moderator it's capable of much more operations than the normal User.
 */

public class Moderator extends User {

    /**
     * Constructs a new Moderator with empty fields.
     */
    public Moderator() {
        this.contractTime = "";
    }

    /**
     * Constructs a new {@link Moderator} starting from a {@link User}, and the contractTime.
     * Neither of the params should be null.
     *
     * @param u the {@link User} corresponding to the moderator
     * @param contractTime the contract expiration date
     */
    public Moderator(@NotNull User u, @NotNull String contractTime) {
        super(u.getUsername(), u.getName(), u.getSurname(), u.getAddress(),
                u.getCity(), u.getCountry(), u.getBirthDate(), u.getMail(), u.getSex(),
                u.getTelephone());
        this.contractTime = contractTime;
        this.setPasswordHash(Objects.requireNonNull(u.getPasswordHash()));
    }

    /**
     * Constructs a new {@link Moderator} starting from the user standard attributes,
     * plus the contractTime.
     * Neither of the params should be null.
     *
     * @param username the moderator's username
     * @param password the moderator's password
     * @param name the moderator's name
     * @param surname the moderator's surname
     * @param address the moderator's address
     * @param city the city of residence of the moderator
     * @param country the country of the moderator
     * @param birthDate the moderator's birth date
     * @param mail the moderator's mail
     * @param sex specifies the sex of the Moderator with one letter (M,F)
     * @param telephone password is the password that the Moderator uses to Log in the site
     * @param contractTime the contract expiration date
     */
    public Moderator(@NotNull String username, @NotNull String password, @NotNull String name,
                     @NotNull String surname, @NotNull String address, @NotNull String city,
                     @NotNull String country, @NotNull String birthDate,
                     @NotNull String mail, char sex, @NotNull String telephone,
                     @NotNull String contractTime) {

        super(username, password, name, surname, address, city, country, birthDate, mail, sex,
                telephone);
        this.contractTime = contractTime;
    }


    // constructor by a 'Moderator' instance.
    public Moderator(@NotNull Moderator m) {
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
    @NotNull
    public String getContractTime() {
        return this.contractTime;
    }


    /**
     * Set a new contract time for the moderator.
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

    @NotNull
    private String contractTime;
}
