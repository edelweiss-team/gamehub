package model.bean;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Moderator extends User {


    public Moderator() {
        this.contractTime = "";
    }

    // constructor by a 'User' instance
    public Moderator(User u, String contractTime) {
        super(u.getUsername(), u.getName(), u.getSurname(), u.getAddress(),
                u.getCity(), u.getCountry(), u.getBirthDate(), u.getMail(), u.getSex(),
                u.getTelephone());
        this.contractTime = contractTime;
        this.setPasswordHash(Objects.requireNonNull(u.getPasswordHash()));
    }

    // constructor by parameters
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

    public String getContractTime() {
        return this.contractTime;
    }

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
        if (this == o) return true;
        if (!(o instanceof Moderator)) return false;
        if (!super.equals(o)) return false;
        Moderator moderator = (Moderator) o;
        return Objects.equals(this.getContractTime(), moderator.getContractTime());
    }


    private String contractTime;
}
