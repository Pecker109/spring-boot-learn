package security.mapper;


import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import security.model.Menu;

import java.util.List;

@Repository
public interface MenuMapper {

    @Select("select m.*,r.`id` as rid,r.`name` as rname,r.`nameZh` as rnameZh from menu m,menu_role mr,role r where m.`id`=mr.`mid` and mr.`rid`=r.`id` order by m.`id`")
    List<Menu> getAllMenusWithRole();

    @Select("select * from menu")
    List<Menu> getAllMenus();

}