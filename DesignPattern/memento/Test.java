import java.util.Stack;

public class Test{
    public static void main(String[] args) {
        Stack<StarMemento> stack = new Stack<StarMemento>();
        Star star = new Star(StarType.SUN, 10000000, 500000);
        System.out.println(star.toString());
        stack.add(star.getMemento());
        star.timePasses();
        System.out.println(star.toString());
        stack.add(star.getMemento());
        star.timePasses();
        System.out.println(star.toString());
        stack.add(star.getMemento());
        star.timePasses();
        System.out.println(star.toString());
        while (!stack.isEmpty()) {
            star.setMemento(stack.pop());
            System.out.println(star.toString());
        }
    }
}