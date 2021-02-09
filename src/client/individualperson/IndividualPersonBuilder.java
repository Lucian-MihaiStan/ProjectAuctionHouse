package client.individualperson;

import client.UserBuilder;

import java.util.Date;

/**
 * Builder for the individual person
 */
public class IndividualPersonBuilder implements UserBuilder<IndividualPerson, IndividualPersonBuilder> {
    private final IndividualPerson individualPerson = new IndividualPerson();

    /**
     * build an individual person with username
     * @param username username of the individual person
     * @return individual person builder instance
     */
    @Override
    public IndividualPersonBuilder withUsername(String username) {
        individualPerson.setUsername(username);
        return this;
    }

    /**
     * build an individual person with first name
     * @param firstName first name of the individual person
     * @return individual person builder instance
     */
    @Override
    public IndividualPersonBuilder withFirstName(String firstName) {
        individualPerson.setFirstName(firstName);
        return this;
    }

    /**
     * build an individual person with last name
     * @param lastName last name of the individual person
     * @return individual person builder instance
     */
    @Override
    public IndividualPersonBuilder withLastName(String lastName) {
        individualPerson.setLastName(lastName);
        return this;
    }

    /**
     * build an individual person with email
     * @param email email of the individual person
     * @return individual person builder instance
     */
    @Override
    public IndividualPersonBuilder withEmail(String email) {
        individualPerson.setEmail(email);
        return this;
    }

    /**
     * build an individual person with address
     * @param address address of the individual person
     * @return individual person builder instance
     */
    @Override
    public IndividualPersonBuilder withAddress(String address) {
        individualPerson.setAddress(address);
        return this;
    }

    /**
     * build an individual person with number of participation
     * @param noParticipation number of participation of the individual person
     * @return individual person builder instance
     */
    @Override
    public IndividualPersonBuilder withNoParticipation(int noParticipation) {
        individualPerson.setNoParticipation(noParticipation);
        return this;
    }

    /**
     * build an individual person with won auction
     * @param wonAuction won auction of the individual person
     * @return individual person builder instance
     */
    @Override
    public IndividualPersonBuilder withWonAuction(int wonAuction) {
        individualPerson.setWonAuctions(wonAuction);
        return this;
    }

    /**
     * build an individual person with username
     * @param username username of the individual person
     * @return individual person builder instance
     */
    public IndividualPersonBuilder withBirthDate(Date birthDate) {
        individualPerson.setBirthDate(birthDate);
        return this;
    }

    /**
     * assembly the components of a individual person
     * @return an individual person instance
     */
    @Override
    public IndividualPerson build() {
        return individualPerson;
    }
}
