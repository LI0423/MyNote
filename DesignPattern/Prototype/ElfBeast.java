package DesignPattern.Prototype;

public class ElfBeast extends Beast{
    private final String helpType;

    public ElfBeast(String helpType) {
        super(null);
        this.helpType = helpType;
    }

    public ElfBeast(ElfBeast elfBeast){
        super(elfBeast);
        this.helpType = elfBeast.helpType;
    }

    @Override
    public String toString() {
        return "Elven eagle helps in " + helpType;
    }

}
