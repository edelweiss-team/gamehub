package model.bean;

import java.sql.Date;
import java.util.GregorianCalendar;

public class Moderator extends User {

    /*
    *                               READ ME
    *
    *   # GregorianCalendar starts counting months from 0, this is why we
    *     subtract 1 from 'contract_month' parameter.
    *
    *
    */

    public Moderator() {

    }

    // constructor by a User instance
    public Moderator(User u, int contract_year, int contract_month, int contract_day) {
        super(u.getUsername(), u.getName(), u.getSurname(), u.getAddress(),
                u.getCity(), u.getCountry(), u.getBirthDate(), u.getMail(), u.getSex(),
                u.getTelephone());

        this.contractTime = new GregorianCalendar(contract_year, contract_month-1, contract_day);
        this.setPasswordHash(u.getPasswordHash());
    }

    public Moderator(User u, GregorianCalendar g) {
        super(u.getUsername(), u.getName(), u.getSurname(), u.getAddress(),
                u.getCity(), u.getCountry(), u.getBirthDate(), u.getMail(), u.getSex(),
                u.getTelephone());

        this.contractTime = g;
        this.setPasswordHash(u.getPasswordHash());
    }

    // constructor by parameters
    public Moderator(String username, String password, String name, String surname, String address,
                     String city, String country, String birthDate, String mail, char sex,
                     String telephone, int contract_year, int contract_month, int contract_day) {

        super(username, password, name, surname, address, city, country, birthDate, mail, sex,
                telephone);
        this.contractTime = new GregorianCalendar(contract_year, contract_month -1, contract_day);
    }

    public GregorianCalendar getContractTime() {
        return this.contractTime;
    }

    public Date getContractTimeAsDate() {
        long contractTimeInMillisec = (this.contractTime).getTimeInMillis();
        return new Date(contractTimeInMillisec);
    }

    public void setContractTime(int contract_year, int contract_month, int contract_day) {
        this.contractTime = new GregorianCalendar(contract_year, contract_month -1, contract_day);
    }

    @Override
    public String toString() {
        return super.toString() + "Moderator{" +
                "contractTime='" + contractTime.toString() + '\'' +
                '}';
    }

    private GregorianCalendar contractTime;
}
