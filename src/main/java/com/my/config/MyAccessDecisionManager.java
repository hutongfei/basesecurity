package com.my.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

/**
 * 创建实现  AccessDecisionManager 的类来
 * 负责鉴定用户是否有访问对应资源（方法或URL）的权限，其由AbstractSecurityInterceptor调用的。(决策器)
 */
@Component
 class MyAccessDecisionManager implements AccessDecisionManager {

    public static final Logger log = LoggerFactory.getLogger(MyAccessDecisionManager.class);

    /**
     * 通过参数来决定用户是否有访问对应资源的权限
     * @param authentication 含当前用户信息，以及拥有的权限。权限来源于前面登录时UserDetailsService中设置的authorities。
     * @param o  FilterInvocation对象，可以得到request
     * @param collection configAttributes是本次访问需要的权限
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException {
        log.info("********MyAccessDecisionManager.decide*******");
        org.springframework.security.web.FilterInvocation fi = (FilterInvocation) o;

        if ( collection.isEmpty()) {
            return;
        } else {
            collection.stream().forEach(c -> {
                String shouldRole = c.getAttribute();
                authentication.getAuthorities().stream().forEach(a -> {
                    //有访问权限，直接return
                    if (shouldRole .trim().equals(((GrantedAuthority) a).getAuthority().trim())){
                        return;
                    }
                });
            });
            throw new AccessDeniedException("当前访问没有权限");
        }
    }
 
    @Override
    //表示此AccessDecisionManager是否能够处理传递的ConfigAttribute呈现的授权请求
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }
 
    @Override
    //本类是否能够为指定的资源提供访问控制决策
    public boolean supports(Class<?> aClass) {
        return true;
    }
}