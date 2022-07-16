package com.my.mapper;/**
 * @ClassName UsersMapper.interface
 * @author hutf
 * @Description TODO
 * @createTime 2021年07月27日 22:52:00
 */

import com.my.entity.Role;
import com.my.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hutf
 * @createTime 2021年07月27日 22:52:00
 */

@Mapper
@Component
public interface UsersMapper {

    @Select("SELECT u.`password`,  u.id,  u.username,  u.`enable` \n" +
            "FROM users AS u\n" +
            "WHERE u.username = #{username}")
    Users selectByUsername(@Param("username") String username);

    @Select("SELECT\n" +
            "	r.id,\n" +
            "	r.roleCode,\n" +
            "	r.roleName \n" +
            "FROM\n" +
            "	users AS u\n" +
            "	INNER JOIN user_role AS ur ON u.id = ur.userId\n" +
            "	INNER JOIN role AS r ON ur.roleId = r.id where u.username = #{username}")
    List<Role> findRoleList(@Param("username") String username);
}
