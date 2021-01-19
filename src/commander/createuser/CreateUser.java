package commander.createuser;

import auctionHouse.AuctionHouse;
import client.Client;
import client.IndividualPerson;
import client.LegalPerson;
import commander.ICommand;
import loginsql.clientconnection.usersql.AddUserSQL;


import java.util.List;

public class CreateUser implements ICommand {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private List<String> restParameters;

    @Override
    public void execute() {
        AuctionHouse auctionHouse = AuctionHouse.getInstance();
        auctionHouse.addNewClient(
                restParameters.size() == 1 ?
                        new IndividualPerson(
                                id,
                                firstName,
                                lastName,
                                address,
                                HelperCU.convertParamIP(restParameters)
                        ) :
                        new LegalPerson(
                                id,
                                firstName,
                                lastName,
                                address,
                                HelperCU.convertParamLP(restParameters).getLeft(),
                                LegalPerson.TypeCompany.valueOf(HelperCU.convertParamLP(restParameters).getRight())
                        )
        );
        Client lastClient = auctionHouse.getLastClient();
        new AddUserSQL().addUserClient(lastClient);
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

    public List<String> getRestParameters() {
        return restParameters;
    }

    public void setRestParameters(List<String> restParameters) {
        this.restParameters = restParameters;
    }
}
