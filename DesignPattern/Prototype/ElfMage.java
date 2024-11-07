package DesignPattern.Prototype;

public class ElfMage extends Mage{
    private final String helpType;

    public ElfMage(String helpType){
        super(null);
        this.helpType = helpType;
    }

    public ElfMage(ElfMage elfMage){
        super(elfMage);
        this.helpType = elfMage.helpType;
    }

    @Override
    public String toString() {
        return "Elven mage helps in " + helpType;
    }

}
