package com.my.mapper;

/**
 * @author hutf
 * @createTime 2022年07月16日 16:52:00
 */

import com.my.vo.RolePermissionVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RolePermissionMapper {


    @Select("SELECT	r.roleCode,r.roleName,p.permissionCode,p.permissionTitle,p.url " +
            " FROM role AS r	INNER JOIN role_permission AS rp ON r.id = rp.roleId	" +
            "INNER JOIN permission AS p ON rp.permissionId = p.id")
    List<RolePermissionVO> getRolePermissionAll();

}


