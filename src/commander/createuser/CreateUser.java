package commander.createuser;

import commander.ICommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateUser implements ICommand {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private int noParticipation;
    private int noAuctionsWon;

    public CreateUser() {

    }

    @Override
    public Map<String, Object> extractParameters(List<String> listParameters) {
        Map<String, Object> mapParameters = new HashMap<>();
        mapParameters.put("id", Integer.parseInt(listParameters.get(0)));
        mapParameters.put("first_name", listParameters.get(1));
        mapParameters.put("last_name", listParameters.get(2));
        mapParameters.put("address", listParameters.get(3));
        mapParameters.put("noParticipation", Integer.parseInt(listParameters.get(4)));
        mapParameters.put("noAuctionWon", Integer.parseInt(listParameters.get(5)));
        return mapParameters;
    }

    @Override
    public void execute() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getNoAuctionsWon() {
        return noAuctionsWon;
    }

    public void setNoAuctionsWon(int noAuctionsWon) {
        this.noAuctionsWon = noAuctionsWon;
    }
}
