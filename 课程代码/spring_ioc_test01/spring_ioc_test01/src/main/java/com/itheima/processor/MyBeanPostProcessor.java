package com.itheima.processor;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Proxy;
import java.util.Date;

public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        /*if(bean instanceof UserDaoImpl){
            UserDaoImpl userDao = (UserDaoImpl) bean;
            userDao.setUsername("haohao");
        }*/
        System.out.println(beanName+":postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName+":postProcessAfterInitialization");
        Object o = Proxy.newProxyInstance(
                bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                ((proxy, method, args) -> {
//                    输出开始时间
                    System.out.println("方法" + method.getName() + "开始时间" + new Date().getTime());
//                    执行目标的方法
                    Object invoke = method.invoke(bean, args);
//                    输出结束的时间
                    System.out.println("方法" + method.getName() + "结束时间" + new Date().getTime());

                    return invoke;
                })
        );
        return o;
    }
}
