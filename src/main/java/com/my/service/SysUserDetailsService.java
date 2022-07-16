package com.my.service;

import com.my.entity.Role;
import com.my.entity.Users;
import com.my.mapper.UsersMapper;
import com.my.utils.CustomAuthorityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hutf
 * @createTime 2021年07月27日 22:58:00
 */
@Service("sysUserDetailsService")
public class SysUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersMapper.selectByUsername(username);
        if (users == null) throw new UsernameNotFoundException("用户不存在");

        //2、表关联查询获取用户对应的角色信息，这里忽略，可以写mapper在查用户的时候一次性获取
        List<Role> roles = usersMapper.findRoleList(username);

        // 2. 将用户拥有的权限加到 grantedAuthorities(此处),注意我们在所有权限前面加了'ROLE_'字符串，这是因为下面 MySecurityConfig 类中对资源加权限 hasRole("ADMIN") 方法中会为加入的字符串前面统一加上"ROLE_",可以看源码
        Collection<GrantedAuthority> grantedAuthorities = roles.stream().map(r -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + r.getRoleCode());
            return grantedAuthority;
        }).collect(Collectors.toList());
        users.setAuthorities((List<GrantedAuthority>) grantedAuthorities);

        return users;
    }
}
