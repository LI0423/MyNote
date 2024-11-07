package DesignPattern.Prototype;

public class Test {
    public static void main(String[] args) {
        HeroFactoryImpl factory = new HeroFactoryImpl(new ElfMage("cooking"), new ElfWarlord("cleaning"), new ElfBeast("protecting"));
        Mage mage = factory.createMage();;
        Warlord warlord = factory.createWarlord();;
        Beast beast = factory.createBeast();;
        System.out.println(mage.toString());
        System.out.println(warlord.toString());
        System.out.println(beast.toString());

        factory = new HeroFactoryImpl(new OrcMage("axe"), new OrcWarlord("sword"), new OrcBeast("laser"));
        mage = factory.createMage();
        warlord = factory.createWarlord();
        beast = factory.createBeast();
        System.out.println(mage.toString());
        System.out.println(warlord.toString());
        System.out.println(beast.toString());
    }
}
