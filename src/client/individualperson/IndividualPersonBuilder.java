package client.individualperson;

import client.UserBuilder;

import java.util.Date;

public class IndividualPersonBuilder implements UserBuilder<IndividualPerson, IndividualPersonBuilder> {
    private final IndividualPerson individualPerson = new IndividualPerson();

    @Override
    public IndividualPersonBuilder withUsername(String username) {
        individualPerson.setUsername(username);
        return this;
    }

    @Override
    public IndividualPersonBuilder withFirstName(String firstName) {
        individualPerson.setFirstName(firstName);
        return this;
    }

    @Override
    public IndividualPersonBuilder withLastName(String lastName) {
        individualPerson.setLastName(lastName);
        return this;
    }

    @Override
    public IndividualPersonBuilder withEmail(String email) {
        individualPerson.setEmail(email);
        return this;
    }

    @Override
    public IndividualPersonBuilder withAddress(String address) {
        individualPerson.setAddress(address);
        return this;
    }

    @Override
    public IndividualPersonBuilder withNoParticipation(int noParticipation) {
        individualPerson.setNoParticipation(noParticipation);
        return this;
    }

    @Override
    public IndividualPersonBuilder withWonAuction(int wonAuction) {
        individualPerson.setWonAuctions(wonAuction);
        return this;
    }

    public IndividualPersonBuilder withBirthDate(Date birthDate) {
        individualPerson.setBirthDate(birthDate);
        return this;
    }

    @Override
    public IndividualPerson build() {
        return individualPerson;
    }
}
