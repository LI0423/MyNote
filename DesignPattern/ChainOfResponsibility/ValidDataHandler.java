package DesignPattern.ChainOfResponsibility;

public class ValidDataHandler extends Handler{

    @Override
    public void doHandler(Member member) {
        if ((member.getLoginName() == null && "".equals(member.getLoginName())) || (member.getLoginPass() == null && "".equals(member.getLoginPass()))) {
            System.out.println("用户名或密码不能为空！");
            return;
        }
        System.out.println("验证通过！");
        handler.doHandler(member);
    }
    
}
