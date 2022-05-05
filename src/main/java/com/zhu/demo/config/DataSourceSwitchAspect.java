package com.zhu.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author 河南张国荣
 * @create 2022-03-14 22:33
 */
@Component
@Order(value = -100)
@Slf4j
@Aspect
public class DataSourceSwitchAspect {

    //设置该路径下mapper使用first数据源
    @Pointcut("execution(* com.zhu.demo.controller..*.*(..))")
    private void firstAspect() {
    }

    @Before("firstAspect()")
    public void first() {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String wqxh = request.getHeader("wqxh");
        if (Objects.equals(wqxh, "26b")) {

            log.info("切换到first数据源...");
            DbContextHolder.setDbType(DBTypeEnum.first);
        } else {
            log.info("切换到second数据源...");
            DbContextHolder.setDbType(DBTypeEnum.second);
        }
    }

}
