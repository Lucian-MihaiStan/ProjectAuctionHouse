package client.individualperson;

import client.User;

import java.util.Date;

/**
 *
 */
public class IndividualPerson extends User {
    private Date birthDate;

    public IndividualPerson() {

    }

    public IndividualPerson(String firstName, String lastName, String address, Date date) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.setNoParticipation(0);
        this.setWonAuctions(0);
        this.birthDate = date;
    }

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
                this.getFirstName() + " " +
                this.getLastName() + " " +
                this.getAddress() + " " +
                this.getNoParticipation() + " " +
                this.getWonAuctions() + " " +
                "birthDate=" + birthDate +
                '}';
    }
}
