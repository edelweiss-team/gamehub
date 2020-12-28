package model.bean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Operator extends User {

    public Operator() {
        this.contractTime = "";
        this.cv = "";
    }

    public Operator(User u, @NotNull String contractTime, @NotNull String cv) {
        super(u.getUsername(), u.getName(), u.getSurname(), u.getAddress(),
            u.getCity(), u.getCountry(), u.getBirthDate(), u.getMail(), u.getSex(),
            u.getTelephone());
        this.contractTime = contractTime;
        this.cv = cv;
        this.setPasswordHash(u.getPasswordHash());
    }

    public Operator(@NotNull String username, @NotNull String password, @NotNull String name,
                    @NotNull String surname, @NotNull String address, @NotNull String city,
                    @NotNull String country, @NotNull String birthDate, @NotNull String mail,
                    char sex, @NotNull String telephone, @NotNull String contractTime,
                    @NotNull String cv) {

        super(username, password, name, surname, address, city, country, birthDate, mail, sex,
                telephone);
        this.contractTime = contractTime;
        this.cv = cv;

    }

    @NotNull
    public String getContractTime() {
        return this.contractTime;
    }

    @NotNull
    public String getCv() {
        return this.cv;
    }


    public void setContractTime(@NotNull String contractTime) {
        this.contractTime = contractTime;
    }

    public void setCv(@NotNull String cv) {
        this.cv = cv;
    }

    @Override
    @NotNull
    public String toString() {
        return super.toString() + "Operator{"
                + "contractTime='" + contractTime + '\''
                + ", cv='" + cv + '\'' + '}';
    }

    @NotNull
    private String contractTime;
    @NotNull
    private String cv;
}
