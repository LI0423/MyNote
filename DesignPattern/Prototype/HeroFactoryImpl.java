package DesignPattern.Prototype;

public class HeroFactoryImpl implements HeroFactory{
    private final Mage mage;
    private final Warlord warlord;
    private final Beast beast;

    public HeroFactoryImpl(Mage mage, Warlord warlord, Beast beast){
        this.mage = mage;
        this.warlord = warlord;
        this.beast = beast;
    }

    @Override
    public Beast createBeast() {
        try {
            return beast.copy();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Mage createMage(){
        try {
            return mage.copy();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public Warlord createWarlord() {
        try {
            return warlord.copy();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    
}
