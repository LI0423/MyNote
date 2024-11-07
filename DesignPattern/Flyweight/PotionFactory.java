package DesignPattern.Flyweight;

import java.util.EnumMap;
import java.util.Map;

public class PotionFactory {
    private final Map<PotionType, Potion> potions;

    public PotionFactory(){
        potions = new EnumMap<>(PotionType.class);
    }

    Potion createPotion(PotionType type){
        Potion potion = potions.get(type);
        if (potion == null) {
            switch (type) {
                case HEALING -> potion = new HealingPotion();
                case HOLY_WATER -> potion = new HolyWaterPotion();
                case INVISIBILITY -> potion = new InvisibilityPotion();
                case POISON -> potion = new PoisonPotion();
                case STRENGTH -> potion = new StrengthPotion();
                default -> {
                }
            }
            if (potion != null) {
                potions.put(type, potion);
            }
        }
        return potion;
    }
    
}
