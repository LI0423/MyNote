package DesignPattern.State;

public class PeacefulState implements State{
    private Mammoth mammoth;

    public PeacefulState(Mammoth mammoth){
        this.mammoth = mammoth;
    }

    @Override
    public void onEnterState() {
        System.out.println(mammoth + " calms down.");
    }

    @Override
    public void observe() {
        System.out.println(mammoth + " is calm and peaceful.");
    }
    
}
