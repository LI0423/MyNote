package DesignPattern.ChainOfResponsibility;

public class Test {
    public static void main(String[] args) {
        MemberService memberService = new MemberService();
        memberService.login("admin", "123456");
    }
}
