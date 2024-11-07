package DesignPattern.Prototype;

public class ElfWarlord extends Warlord{
    private final String helpType;

    public ElfWarlord(String helpType) {
        super(null);
        this.helpType = helpType;
    }

    public ElfWarlord(ElfWarlord elfWarlord){
        super(elfWarlord);
        this.helpType = elfWarlord.helpType;
    }

    @Override
    public String toString() {
        return "Elven warlord hepls in " + helpType;
    }
}
