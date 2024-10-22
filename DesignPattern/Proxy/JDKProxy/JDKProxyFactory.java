package DesignPattern.Proxy.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxyFactory implements InvocationHandler{

    private Object object;

    public JDKProxyFactory(Object object){
        this.object = object;
    }

    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), object.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        if ("loadVideo".equals(method.getName())){
            result = method.invoke(object, args);
        }

        if ("playerVideo".equals(method.getName())){
            System.out.println("前置增强");
            result = method.invoke(object, args);
            System.out.println("后置增强");
        }
        return result;
    }
    
}
