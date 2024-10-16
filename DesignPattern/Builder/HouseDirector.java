public class HouseDirector {
    public House builder(HouseBuilder houseBuilder){
        houseBuilder.doJiadian();
        houseBuilder.doDiban();
        houseBuilder.doYouqi();
        return houseBuilder.getHouse();
    }
}
