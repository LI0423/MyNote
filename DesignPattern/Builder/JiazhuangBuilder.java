public class JiazhuangBuilder implements HouseBuilder {
    private House house = new House();

    @Override
    public void doJiadian() {
        house.setJiadian("简单家电");
    }

    @Override
    public void doDiban() {
        house.setDiban("简单地板");
    }

    @Override
    public void doYouqi() {
        house.setYouqi("简单油漆");
    }

    @Override
    public House getHouse() {
        return house;
    }
    
}
