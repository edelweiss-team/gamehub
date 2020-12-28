package model.bean;


public class Admin extends Moderator {

    public Admin() {

    }

    /**
     *
     * @param u the user correspondig to the admin
     * @param contractTime the contract expiration date
     */
    public Admin(User u, String contractTime) {
        super(u, contractTime);
        this.isSuperAdmin = false;
    }

    /**
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
     * @param isSuperAdmin a boolean indicating if an admin is a superadmin
     */
    public Admin(String username, String password, String name, String surname,
                 String address, String city, String country, String birthDate,
                 String mail, char sex, String telephone, String contractTime,
                 boolean isSuperAdmin) {
        super(username, password, name, surname, address, city, country, birthDate,
                mail, sex, telephone, contractTime);
        this.isSuperAdmin = isSuperAdmin;
    }


    /**
     *
     * @return return true if the admin is a superadmin and false otherwise
     */
    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    /**
     *
     * @param superAdmin a boolean indicating if an admin is a superadmin
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

    private boolean isSuperAdmin;
}
