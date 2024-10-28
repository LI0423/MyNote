package DesignPattern.ChainOfResponsibility;

public class MemberService {
    public void login(String loginName, String loginPass){
        ValidDataHandler validDataHandler = new ValidDataHandler();

        LoginHandler loginHandler = new LoginHandler();
        validDataHandler.next(loginHandler);

        AuthHandler authHandler = new AuthHandler();
        loginHandler.next(authHandler);

        validDataHandler.doHandler(new Member(loginName, loginPass));

    }
}
