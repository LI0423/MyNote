package DesignPattern.Mediator;

public interface PartyMember {

    void joinedParty(Party party);

    void partyAction(Action action);

    void act(Action action);
    
}
