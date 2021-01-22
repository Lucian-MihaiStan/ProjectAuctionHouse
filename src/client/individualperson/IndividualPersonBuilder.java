package client.individualperson;

import java.util.Date;

public class IndividualPersonBuilder {
    private final IndividualPerson individualPerson = new IndividualPerson();

    public IndividualPersonBuilder withFirstName(String firstName) {
        individualPerson.setFirstName(firstName);
        return this;
    }

    public IndividualPersonBuilder withLastName(String lastName) {
        individualPerson.setLastName(lastName);
        return this;
    }

    public IndividualPersonBuilder withAddress(String address) {
        individualPerson.setAddress(address);
        return this;
    }

    public IndividualPersonBuilder withNoParticipation(int noParticipation) {
        individualPerson.setNoParticipation(noParticipation);
        return this;
    }

    public IndividualPersonBuilder withWonAction(int wonAction) {
        individualPerson.setWonAuctions(wonAction);
        return this;
    }

    public IndividualPersonBuilder withBirthDate(Date birthDate) {
        individualPerson.setBirthDate(birthDate);
        return this;
    }

    public IndividualPerson build() {
        return individualPerson;
    }
}
