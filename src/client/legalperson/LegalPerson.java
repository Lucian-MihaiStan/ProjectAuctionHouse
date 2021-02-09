package client.legalperson;

import client.User;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LegalPerson that = (LegalPerson) o;
        return Double.compare(that.socialCapital, socialCapital) == 0 &&
                typeCompany == that.typeCompany;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), typeCompany, socialCapital);
    }
}

