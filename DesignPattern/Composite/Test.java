package DesignPattern.Composite;

public class Test {
    public static void main(String[] args) {
        Composite root = new Composite();
        Component node1 = new Leaf("1");
        Component node2 = new Composite();
        Component node3 = new Leaf("3");
        root.add(node1);
        root.add(node2);
        root.add(node3);
        Component node21 = new Leaf("21");
        Component node22 = new Composite();
        node2.add(node21);
        node2.add(node22);
        Component node221 = new Leaf("221");
        node22.add(node221);

        root.operation();
    }
}
