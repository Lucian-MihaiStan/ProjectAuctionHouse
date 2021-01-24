package commander.createuser;

import auction_house.AuctionHouse;
import client.User;
import client.individualperson.IndividualPersonBuilder;
import client.legalperson.LegalPerson;
import client.legalperson.LegalPersonBuilder;
import commander.ICommand;
import loginsql.clientconnection.usersql.AddUserSQL;


import java.util.List;

public class CreateUser implements ICommand {
    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private List<String> restParameters;

    @Override
    public void execute() {
        AuctionHouse auctionHouse = AuctionHouse.getInstance();
        auctionHouse.addNewClient(
                restParameters.size() == 1 ?
                        new IndividualPersonBuilder()
                                .withUsername(username)
                                .withFirstName(firstName)
                                .withLastName(lastName)
                                .withAddress(address)
                                .withNoParticipation(0)
                                .withWonAction(0)
                                .withBirthDate(HelperCU.convertParamIP(restParameters))
                                .build()
                        :
                        new LegalPersonBuilder()
                                .withUsername(username)
                                .withFirstName(firstName)
                                .withLastName(lastName)
                                .withAddress(address)
                                .withNoParticipation(0)
                                .withWonAction(0)
                                .withSocialCapital(HelperCU.convertParamLP(restParameters).getLeft())
                                .withTypeCompany(LegalPerson.TypeCompany
                                        .valueOf(HelperCU.convertParamLP(restParameters).getRight()))
                                .build()
        );
        User lastUser = auctionHouse.getLastClient();
        new AddUserSQL().addClientSQL(lastUser);
    }

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

    public List<String> getRestParameters() {
        return restParameters;
    }

    public void setRestParameters(List<String> restParameters) {
        this.restParameters = restParameters;
    }
}
