package model.bean;

public class Admin extends Moderator{

    public Admin(boolean isSuperAdmin) {

    }

    // constructor by instance of User
    public Admin(User u, String contractTime, boolean isSuperAdmin) {
        super(u, contractTime);
        this.isSuperAdmin = isSuperAdmin;
    }

    // constructor by parameters
    public Admin(String username, String password, String name, String surname, String address, String city,
                 String country, String birthDate, String mail, char sex, String telephone, String
                         contractTime, boolean isSuperAdmin) {
        super(username, password, name, surname, address, city, country, birthDate, mail, sex, telephone, contractTime);
        this.isSuperAdmin = isSuperAdmin;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "isSuperAdmin=" + isSuperAdmin +
                '}';
    }
    private boolean isSuperAdmin;
}
