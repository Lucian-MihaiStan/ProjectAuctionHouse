package client.legalperson;

import client.User;


/**
 * legal person implementation
 */
public class LegalPerson extends User {
    private TypeCompany typeCompany;
    private double socialCapital;

    /**
     * type of companies
     */
    public enum TypeCompany {
        /**
         * SRL type company
         */
        SRL,
        /**
         * SA type company
         */
        SA
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
                this.getUsername() + " " +
                this.getEmail() + " " +
                this.getFirstName() + " " +
                this.getLastName() + " " +
                this.getAddress() + " " +
                "| auction participated " + this.getNoParticipation() + " " +
                "| auction won " + this.getWonAuctions() + " " +
                "typeCompany=" + typeCompany +
                ", socialCapital=" + socialCapital +
                '}';
    }
}

