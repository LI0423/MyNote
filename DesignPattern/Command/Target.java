package DesignPattern.Command;

public abstract class Target {
    private Size size;

    private Visibility visibility;

    public Size getSize(){
        return this.size;
    }

    public void setSize(Size size){
        this.size = size;
    }

    public Visibility geVisibility(){
        return this.visibility;
    }

    public void setVisibility(Visibility visibility){
        this.visibility = visibility;
    }

    public void printStatus(){
        System.out.println(this + " size=" + getSize() + " visibility=" + geVisibility());
    }

    public void changeSize(){
        Size oldSize = getSize() == Size.NORMAL ? Size.SMALL : Size.NORMAL;
        setSize(oldSize);
    }

    public void changeVisibility(){
        Visibility visible = geVisibility() == Visibility.INVISIBLE ? Visibility.VISIBLE : Visibility.INVISIBLE;
        setVisibility(visible);
    }

}
