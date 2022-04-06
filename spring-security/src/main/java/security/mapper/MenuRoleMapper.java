package security.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import security.model.Role;

import java.util.List;

@Repository
public interface MenuRoleMapper {

    @Select("SELECT r.* FROM role r WHERE r.id IN (SELECT mr.rid FROM menu_role mr WHERE mr.mid = #{menuId})")
    List<Role> selectRolesByMenuId(Integer menuId);
}