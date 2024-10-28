package DesignPattern.ChainOfResponsibility;

public class AuthHandler extends Handler{

    @Override
    public void doHandler(Member member) {
        if (!"管理员".equals(member.getRoleName())){
            System.out.println("无操作权限！");
            return;
        }
        System.out.println("操作成功！");
    }
    
}
