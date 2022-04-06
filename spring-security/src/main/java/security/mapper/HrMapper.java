package security.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import security.model.Hr;
import security.model.Role;

import java.util.List;

@Repository
public interface HrMapper {
    @Select("select * from hr where username=#{username}")
    Hr loadUserByUsername(String username);

    @Select("select r.* from role r,hr_role hrr where hrr.`rid`=r.`id` and hrr.`hrid`=#{id}")
    List<Role> getHrRolesById(Integer id);

}