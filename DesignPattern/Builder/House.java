public class House{

    private String jiadian;

    private String diban;

    private String youqi;

    public void setJiadian(String jiadian){
        this.jiadian = jiadian;
    }

    public String getJiadian(){
        return this.jiadian;
    }

    public void setDiban(String diban){
        this.diban = diban;
    }

    public String getDiban(){
        return this.diban;
    }

    public void setYouqi(String youqi){
        this.youqi = youqi;
    }

    public String getYouqi(){
        return this.youqi;
    }

    public String toString(){
        return "House: " + this.jiadian + this.diban + this.youqi; 
    }
}