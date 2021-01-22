package client.legalperson;


import client.User;

public class LegalPerson extends User {
    private TypeCompany typeCompany;
    private double socialCapital;

    public enum TypeCompany {
        SRL,
        SA
    }

    public LegalPerson() {

    }

    public LegalPerson(String firstName, String lastName, String address, double socialCapital, TypeCompany typeCompany) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.setNoParticipation(0);
        this.setWonAuctions(0);
        this.socialCapital = socialCapital;
        this.typeCompany = typeCompany;
    }

    public void setTypeCompany(TypeCompany typeCompany) {
        this.typeCompany = typeCompany;
    }

    public void setSocialCapital(double socialCapital) {
        this.socialCapital = socialCapital;
    }

    public TypeCompany getTypeCompany() {
        return typeCompany;
    }

    public double getSocialCapital() {
        return socialCapital;
    }

    @Override
    public String toString() {
        return "LegalPerson{" +
                this.getFirstName() + " " +
                this.getLastName() + " " +
                this.getAddress() + " " +
                this.getNoParticipation() + " " +
                this.getWonAuctions() + " " +
                "typeCompany=" + typeCompany +
                ", socialCapital=" + socialCapital +
                '}';
    }
}

