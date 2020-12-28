package model.bean;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Moderator extends User {

    public Moderator() {
        this.contractTime = "";
    }

    // constructor by a User instance
    public Moderator(User u, @NotNull String contractTime) {
        super(Objects.requireNonNull(u.getUsername()), Objects.requireNonNull(u.getName()),
                Objects.requireNonNull(u.getSurname()), Objects.requireNonNull(u.getAddress()),
                Objects.requireNonNull(u.getCity()), Objects.requireNonNull(u.getCountry()),
                Objects.requireNonNull(u.getBirthDate()), Objects.requireNonNull(u.getMail()), u.getSex(),
                Objects.requireNonNull(u.getTelephone()));
        this.contractTime = contractTime;
        this.setPasswordHash(Objects.requireNonNull(u.getPasswordHash()));
    }

    // constructor by parameters
    public Moderator(@NotNull String username, @NotNull String password, @NotNull String name,
                     @NotNull String surname, @NotNull String address, @NotNull String city,
                     @NotNull String country, @NotNull String birthDate, @NotNull String mail,
                     char sex, @NotNull String telephone, @NotNull String contractTime) {

        super(username, password, name, surname, address, city, country, birthDate, mail, sex,
                telephone);
        this.contractTime = contractTime;
    }

    public @NotNull String getContractTime() {
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
    private String contractTime;
}
