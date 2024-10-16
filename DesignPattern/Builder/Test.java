public class Test {

    public static void build(){
        HouseDirector houseDirector = new HouseDirector();
        JiazhuangBuilder jiazhuangBuilder = new JiazhuangBuilder();
        
        System.out.println(houseDirector.builder(jiazhuangBuilder).toString());
    }
    public static void main(String[] args) {
        build();
    }
}
