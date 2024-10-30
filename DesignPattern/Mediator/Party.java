package DesignPattern.Mediator;

public interface Party {

    void addMember(PartyMember partyMember);

    void act(PartyMember actor, Action action);
    
}
