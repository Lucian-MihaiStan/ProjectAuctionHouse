package client.individualperson;

import client.User;

import java.util.Date;

/**
 *
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
}
