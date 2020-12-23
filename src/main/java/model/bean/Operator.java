package model.bean;

public class Operator extends User {

    public Operator() {

    }

    public Operator(User u, String contractTime, String cv) {
        super(u.getUsername(), u.getName(), u.getSurname(), u.getAddress(),
            u.getCity(), u.getCountry(), u.getBirthDate(), u.getMail(), u.getSex(),
            u.getTelephone());
        this.contractTime = contractTime;
        this.cv = cv;
        this.setPasswordHash(u.getPasswordHash());
    }

    public Operator(String username, String password, String name, String surname, String address,
                    String city, String country, String birthDate, String mail, char sex,
                    String telephone, String contractTime, String cv) {

        super(username, password, name, surname, address, city, country, birthDate, mail, sex,
                telephone);
        this.contractTime = contractTime;
        this.cv = cv;

    }

    public String getContractTime() {
        return this.contractTime;
    }

    public String getCv() {
        return this.cv;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    @Override
    public String toString() {
        return super.toString() + "Operator{"
                + "contractTime='" + contractTime + '\''
                + ", cv='" + cv + '\'' + '}';
    }

    private String contractTime;
    private String cv;
}
