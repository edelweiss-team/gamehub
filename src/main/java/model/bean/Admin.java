package model.bean;

import org.jetbrains.annotations.NotNull;

/**
 * Admin is a specialization of {@link Moderator} which represents an admin of the GameHub platform.
 * Admin it's capable of much more operations than the normal Moderator,
 * such the administration of the e-commerce.
 * An admin can also be a super-admin,
 * capable of doing even more operations(represented by a boolean).
 */
public class Admin extends Moderator {

    /**
     * Constructs a new {@link Admin} with empty fields.
     */
    public Admin() {

    }

    /**
     * Constructs a new {@link Admin} starting from a user, and the contractTime.
     * This admin is by default treated not as a super-admin.
     * Neither of the params should be null.
     *
     * @param u the {@link User} corresponding to the admin
     * @param contractTime the contract expiration date
     */
    public Admin(@NotNull User u, @NotNull String contractTime) {
        super(u, contractTime);
        this.isSuperAdmin = false;
    }

    /**
     * Constructs a new {@link Admin} starting from a user, and the contractTime.
     * Neither of the params should be null.
     *
     * @param u the {@link User} corresponding to the admin
     * @param contractTime the contract expiration date
     * @param isSuperAdmin a boolean indicating if an admin is a super-admin
     */
    public Admin(@NotNull User u, @NotNull String contractTime, boolean isSuperAdmin) {
        super(u, contractTime);
        this.isSuperAdmin = isSuperAdmin;
    }

    /**
     * Constructs a new {@link Admin} starting from the user standard attributes,
     * plus the contractTime and isSuperAdmin attributes.
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
     * @param isSuperAdmin a boolean indicating if an admin is a super-admin
     */
    public Admin(@NotNull String username, @NotNull String password, @NotNull String name,
                 @NotNull String surname, @NotNull String address, @NotNull String city,
                 @NotNull String country, @NotNull String birthDate, @NotNull String mail,
                 char sex, @NotNull String telephone, @NotNull String contractTime,
                 boolean isSuperAdmin) {
        super(username, password, name, surname, address, city, country, birthDate, mail,
                sex, telephone, contractTime);
        this.isSuperAdmin = isSuperAdmin;
    }

    public Admin(@NotNull Moderator m, boolean isSuperAdmin) {
        super(m);
        this.isSuperAdmin = isSuperAdmin;
    }

    /**
     * Constructs a new {@link Admin} starting from the user standard attributes,
     * plus the contractTime attribute.
     * This admin is by default treated not as a super-admin.
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
    public Admin(@NotNull String username, @NotNull String password, @NotNull String name,
                 @NotNull String surname, @NotNull String address, @NotNull String city,
                 @NotNull String country, @NotNull String birthDate,
                 @NotNull String mail, char sex, @NotNull String telephone,
                 @NotNull String contractTime) {
        super(username, password, name, surname, address, city, country, birthDate,
                mail, sex, telephone, contractTime);
        this.isSuperAdmin = false;
    }


    /**
     * Determines whatever or not the Admin is a super-admin.
     *
     * @return true if the admin is a super-admin, false otherwise.
     */
    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    /**
     * Sets the isSuperAdmin value.
     *
     * @param superAdmin a boolean indicating if an admin is a super-admin.
     */
    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    @Override
    public String toString() {
        return super.toString() + ", Admin{"
                + "isSuperAdmin=" + isSuperAdmin
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Admin)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Admin admin = (Admin) o;
        return isSuperAdmin() == admin.isSuperAdmin();
    }

    private boolean isSuperAdmin;
}
