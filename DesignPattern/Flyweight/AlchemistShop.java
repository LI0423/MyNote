package DesignPattern.Flyweight;

import java.util.List;

public class AlchemistShop {
    private final List<Potion> topShelf;
    private final List<Potion> bottomShelf;

    public AlchemistShop(){
        PotionFactory factory = new PotionFactory();
        topShelf = List.of(
            factory.createPotion(PotionType.INVISIBILITY),
            factory.createPotion(PotionType.INVISIBILITY),
            factory.createPotion(PotionType.STRENGTH),
            factory.createPotion(PotionType.HEALING),
            factory.createPotion(PotionType.INVISIBILITY),
            factory.createPotion(PotionType.STRENGTH),
            factory.createPotion(PotionType.HEALING),
            factory.createPotion(PotionType.HEALING)
        );
        bottomShelf = List.of(
            factory.createPotion(PotionType.POISON),
            factory.createPotion(PotionType.POISON),
            factory.createPotion(PotionType.POISON),
            factory.createPotion(PotionType.HOLY_WATER),
            factory.createPotion(PotionType.HOLY_WATER)
        );
    }

    public final List<Potion> getTopShelf(){
        return List.copyOf(this.topShelf);
    }

    public final List<Potion> getBottomShelf(){
        return List.copyOf(this.bottomShelf);
    }

    public void drinkPotions(){
        System.out.println("Drinking top shelf potions");
        topShelf.forEach(Potion::drink);
        System.out.println("Drinking bottom shelf potions");
        bottomShelf.forEach(Potion::drink);
    }
}
