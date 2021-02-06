package commander.createuser;

import auction_house.AuctionHouse;
import client.User;
import client.individualperson.IndividualPersonBuilder;
import client.legalperson.LegalPerson;
import client.legalperson.LegalPersonBuilder;
import commander.ICommand;
import loginsql.clientconnection.usersql.AddUserSQL;
import socketserver.ServerClientThread;


import java.util.List;

public class CreateUser implements ICommand {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private List<String> restParameters;

    private AuctionHouse auctionHouse;

    @Override
    public synchronized void execute(ServerClientThread sct) {
        auctionHouse = sct.getAuctionHouse();
        if (!checkDuplicate(this)) {
            ServerClientThread.Helper errorMessage = ServerClientThread.Helper.getInstance();
            errorMessage.setCommandResult(errorMessage
                    .getCommandResult()
                    .append("Error duplicate user"));
            return;
        }
        auctionHouse.addNewClient(
                restParameters.size() == 1 ?
                        new IndividualPersonBuilder()
                                .withUsername(username)
                                .withEmail(email)
                                .withFirstName(firstName)
                                .withLastName(lastName)
                                .withAddress(address)
                                .withNoParticipation(0)
                                .withWonAuction(0)
                                .withBirthDate(HelperCU.convertParamIP(restParameters))
                                .build()
                        :
                        new LegalPersonBuilder()
                                .withUsername(username)
                                .withEmail(email)
                                .withFirstName(firstName)
                                .withLastName(lastName)
                                .withAddress(address)
                                .withNoParticipation(0)
                                .withWonAuction(0)
                                .withSocialCapital(HelperCU.convertParamLP(restParameters).getLeft())
                                .withTypeCompany(LegalPerson.TypeCompany
                                        .valueOf(HelperCU.convertParamLP(restParameters).getRight()))
                                .build()
        );
        User lastUser = auctionHouse.getLastClient();
        new AddUserSQL(sct.getMySQLConnection()).addClientSQL(lastUser);
    }

    private boolean checkDuplicate(CreateUser createUser) {
        return auctionHouse.getUserList()
                .stream().noneMatch(user -> createUser.getUsername().equals(user.getUsername()));
    }

    public void setAuctionHouse(AuctionHouse auctionHouse) {
        this.auctionHouse = auctionHouse;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
