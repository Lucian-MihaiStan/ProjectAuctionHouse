package client.legalperson;

public class LegalPersonBuilder {
    private final LegalPerson legalPerson = new LegalPerson();

    public LegalPersonBuilder withFirstName(String firstName) {
        legalPerson.setFirstName(firstName);
        return this;
    }

    public LegalPersonBuilder withLastName(String lastName) {
        legalPerson.setLastName(lastName);
        return this;
    }

    public LegalPersonBuilder withAddress(String address) {
        legalPerson.setAddress(address);
        return this;
    }

    public LegalPersonBuilder withNoParticipation(int noParticipation) {
        legalPerson.setNoParticipation(noParticipation);
        return this;
    }

    public LegalPersonBuilder withWonAction(int wonAction) {
        legalPerson.setWonAuctions(wonAction);
        return this;
    }

    public LegalPersonBuilder withSocialCapital(double socialCapital) {
        legalPerson.setSocialCapital(socialCapital);
        return this;
    }

    public LegalPersonBuilder withTypeCompany(LegalPerson.TypeCompany typeCompany) {
        legalPerson.setTypeCompany(typeCompany);
        return this;
    }

    public LegalPerson build() {
        return legalPerson;
    }
}
