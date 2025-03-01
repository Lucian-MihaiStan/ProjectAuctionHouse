package client;

public abstract class User {
    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private int noParticipation;
    private int wonAuctions;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNoParticipation() {
        return noParticipation;
    }

    public void setNoParticipation(int noParticipation) {
        this.noParticipation = noParticipation;
    }

    public int getWonAuctions() {
        return wonAuctions;
    }

    public void setWonAuctions(int wonAuctions) {
        this.wonAuctions = wonAuctions;
    }

    @Override
    public String toString() {
        return "User{" +
                "Username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", noParticipation=" + noParticipation +
                ", wonAuctions=" + wonAuctions +
                '}';
    }
}
