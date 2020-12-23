package model.bean;

public class Moderator extends User {

    public Moderator() {

    }

    // constructor by a User instance
    public Moderator(User u, String contractTime) {
        super(u.getUsername(), u.getName(), u.getSurname(), u.getAddress(),
                u.getCity(), u.getCountry(), u.getBirthDate(), u.getMail(), u.getSex(),
                u.getTelephone());
        this.contractTime = contractTime;
        this.setPasswordHash(u.getPasswordHash());
    }

    // constructor by parameters
    public Moderator(String username, String password, String name, String surname, String address,
                     String city, String country, String birthDate, String mail, char sex,
                     String telephone, String contractTime) {

        super(username, password, name, surname, address, city, country, birthDate, mail, sex,
                telephone);
        this.contractTime = contractTime;
    }

    public String getContractTime() {
        return this.contractTime;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
    }

    @Override
    public String toString() {
        return super.toString() + "Moderator{" +
                "contractTime='" + contractTime + '\'' +
                '}';
    }

    private String contractTime;
}
