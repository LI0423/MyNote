package DesignPattern.Command;

public enum Size {
    SMALL("small"),
    NORMAL("normal");

    private final String title;

    Size(String title){
        this.title = title;
    }
    
    public String toString(){
        return title;
    }
    
}
