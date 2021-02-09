package client.individualperson;

import client.User;

import java.util.Date;
import java.util.Objects;

/**
 * Individual Person class
 */
public class IndividualPerson extends User {
    private Date birthDate;

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        return "IndividualPerson{" +
                this.getUsername() + " " +
                this.getEmail() + " " +
                this.getFirstName() + " " +
                this.getLastName() + " " +
                this.getAddress() + " " +
                "| auction participated " + this.getNoParticipation() + " " +
                "| auction won " + this.getWonAuctions() + " " +
                birthDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IndividualPerson that = (IndividualPerson) o;
        return Objects.equals(birthDate, that.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), birthDate);
    }
}
