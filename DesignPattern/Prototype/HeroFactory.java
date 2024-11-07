package DesignPattern.Prototype;

public interface HeroFactory {
    Mage createMage() throws CloneNotSupportedException;

    Warlord createWarlord() throws CloneNotSupportedException;

    Beast createBeast() throws CloneNotSupportedException;
}
