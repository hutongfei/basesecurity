package com.my.config;

import com.my.entity.RolePermission;
import com.my.mapper.RolePermissionMapper;
import com.my.vo.RolePermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

// 存储角色&权限之间的信息
@Component
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    //定义map存放访问资源与需要的角色Collection<ConfigAttribute>决策器 的关系
    private static HashMap<String, Collection<ConfigAttribute>> map = null;

    @Override
    //当接收到http请求时, filterSecurityInterceptor会调用的方法。这个方法返回请求该url所需要的所有权限集合。
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //初始化 访问资源(url)与角色的对应关系（即将角色与资源关联表role_permission的所有数据取出，加到权限设置）
        if (map == null) {
            map = new HashMap<>();

            List<RolePermissionVO> rps = rolePermissionMapper.getRolePermissionAll();
//            List<RolePermission> rps = xxx;  //获取关系表的全部内容
            //遍历全部关联信息，取出对应的 url 和 角色信息（某个资源可以被哪个角色访问）
            rps.stream().forEach(rp -> {
                String url = rp.getUrl();
                ConfigAttribute configAttribute = new SecurityConfig(rp.getRoleCode());
                //url是否加过别的角色
                if (map.containsKey(url)) {
                    map.get(url).add(configAttribute);
                } else {
                    List<ConfigAttribute> configAttributes = new ArrayList<>();
                    configAttributes.add(configAttribute);
                    map.put(url, configAttributes);
                }
            });
        }
        //object 中包含用户请求的request 信息（包含访问的当前url）
        // map 为  key:value --->>>>  /xxx/xxx : xxxx角色
        HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
        for (Iterator<String> it = map.keySet().iterator(); it.hasNext(); ) {
            String url = it.next();
            if (new AntPathRequestMatcher(url).matches(request)) {
                return map.get(url);
            }
        }
        return null;
    }

    @Override
    //Spring容器启动时自动调用, 一般把所有请求与权限的对应关系也要在这个方法里初始化, 保存在一个属性变量里。
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    //该类是否能够为某资源提供ConfigAttributes。
    public boolean supports(Class<?> aClass) {
        return true;
    }
}