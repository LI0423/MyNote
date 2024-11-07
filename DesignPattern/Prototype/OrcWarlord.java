package DesignPattern.Prototype;

public class OrcWarlord extends Warlord{
    private final String weapon;

    public OrcWarlord(String weapon) {
        super(null);
        this.weapon = weapon;
    }

    public OrcWarlord(OrcWarlord orcWarlord){
        super(orcWarlord);
        this.weapon = orcWarlord.weapon;
    }

    @Override
    public String toString() {
        return "OrcWarlord [weapon=" + weapon + "]";
    }

}
