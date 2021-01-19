package client;


public class LegalPerson extends Client {
    private final TypeCompany typeCompany;
    private final double socialCapital;

    public enum TypeCompany {
        SRL,
        SA
    }

    public LegalPerson(int id, String firstName, String lastName, String address, double socialCapital, TypeCompany typeCompany) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setAddress(address);
        this.setNoParticipation(0);
        this.setWonAuctions(0);
        this.socialCapital = socialCapital;
        this.typeCompany = typeCompany;
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
                this.getId() + " " +
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

