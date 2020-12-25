package model.bean;

import java.util.GregorianCalendar;

public class Admin extends Moderator {

    public Admin() {

    }

    // constructor by instance of User
    public Admin(User u, int contract_year, int contract_month, int contract_day, boolean isSuperAdmin) {
        // GregorianCalendar starts counting months from 0, this is why we
        // subtract 1 from 'contract_month' parameter.
        super(u, new GregorianCalendar(contract_year, contract_month-1, contract_day));
        this.isSuperAdmin = isSuperAdmin;
    }

    // constructor by parameters
    public Admin(String username, String password, String name, String surname,
                 String address, String city, String country, String birthDate,
                 String mail, char sex, String telephone, int contract_year, int contract_month, int contract_day,
                 boolean isSuperAdmin) {
        super(username, password, name, surname, address, city, country, birthDate,
                mail, sex, telephone, contract_year, contract_month, contract_day);
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
        return "Admin{"
                + "isSuperAdmin=" + isSuperAdmin
                + '}';
    }

    private boolean isSuperAdmin;
}
