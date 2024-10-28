package DesignPattern.ChainOfResponsibility;

public class LoginHandler extends Handler{

    @Override
    public void doHandler(Member member) {
        System.out.println("登陆成功！");
        member.setRoleName("管理员");
        handler.doHandler(member);
    }
    
}
