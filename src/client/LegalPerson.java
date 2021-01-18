package client;

public class LegalPerson extends Client {
    private Enum typeCompany;
    private double socialCapital;
}

enum typeCompany {
    SRL,
    SA
}