package client.legalperson;

import client.UserBuilder;

/**
 * legal person builder (design pattern)
 */
public class LegalPersonBuilder implements UserBuilder<LegalPerson, LegalPersonBuilder> {
    private final LegalPerson legalPerson = new LegalPerson();

    /**
     * build an legal person with email
     * @param email email of the legal person
     * @return legal person builder instance
     */
    public LegalPersonBuilder withEmail(String email) {
        legalPerson.setEmail(email);
        return this;
    }

    /**
     * build an legal person with username
     * @param username username of the legal person
     * @return legal person builder instance
     */
    public LegalPersonBuilder withUsername(String username) {
        legalPerson.setUsername(username);
        return this;
    }

    /**
     * build an legal person with first name
     * @param firstName first name of the legal person
     * @return legal person builder instance
     */
    public LegalPersonBuilder withFirstName(String firstName) {
        legalPerson.setFirstName(firstName);
        return this;
    }

    /**
     * build an legal person with last name
     * @param lastName last name of the legal person
     * @return legal person builder instance
     */
    public LegalPersonBuilder withLastName(String lastName) {
        legalPerson.setLastName(lastName);
        return this;
    }

    /**
     * build an legal person with address
     * @param address address of the legal person
     * @return legal person builder instance
     */
    public LegalPersonBuilder withAddress(String address) {
        legalPerson.setAddress(address);
        return this;
    }

    /**
     * build an legal person with number of participation
     * @param noParticipation number of participation of the legal person
     * @return legal person builder instance
     */
    public LegalPersonBuilder withNoParticipation(int noParticipation) {
        legalPerson.setNoParticipation(noParticipation);
        return this;
    }

    /**
     * build an legal person with number of won auctions
     * @param wonAuction won auctions of the legal person
     * @return legal person builder instance
     */
    public LegalPersonBuilder withWonAuction(int wonAuction) {
        legalPerson.setWonAuctions(wonAuction);
        return this;
    }

    /**
     * build an legal person with social capital
     * @param socialCapital social captial of the legal person
     * @return legal person builder instance
     */
    public LegalPersonBuilder withSocialCapital(double socialCapital) {
        legalPerson.setSocialCapital(socialCapital);
        return this;
    }

    /**
     * build an legal person with type Company
     * @param typeCompany type company of the legal person
     * @return legal person builder instance
     */
    public LegalPersonBuilder withTypeCompany(LegalPerson.TypeCompany typeCompany) {
        legalPerson.setTypeCompany(typeCompany);
        return this;
    }

    /**
     *  assembly the components of a legal person
     *  @return an legal person instance
     */
    public LegalPerson build() {
        return legalPerson;
    }
}
