package client;

import java.util.Date;

/**
 *
 */
public class IndividualPerson extends Client {
    private final Date birthDate;

    public IndividualPerson(int id, String firstName, String lastName, String address, Date date) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.setNoParticipation(0);
        this.setWonAuctions(0);
        this.birthDate = date;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        return "IndividualPerson{" +
                this.getId() + " " +
                this.getFirstName() + " " +
                this.getLastName() + " " +
                this.getAddress() + " " +
                this.getNoParticipation() + " " +
                this.getWonAuctions() + " " +
                "birthDate=" + birthDate +
                '}';
    }
}
