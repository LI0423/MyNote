package DesignPattern.Prototype;

public class OrcMage extends Mage{

    private final String weapon;

    public OrcMage(String weapon) {
        super(null);
        this.weapon = weapon;
    }

    public OrcMage(OrcMage orcMage){
        super(orcMage);
        this.weapon = orcMage.weapon;
    }

    @Override
    public String toString() {
        return "Orcish mage attacks with " + weapon;
    }
}
