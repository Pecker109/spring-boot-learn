package security.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import security.mapper.MenuMapper;
import security.mapper.MenuRoleMapper;
import security.model.Menu;

import java.util.List;


@Service
public class MenuService {
    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    /**
     * 获取所有菜单
     *
     * @return 菜单信息
     */
    public List<Menu> getAllMenusWithRole() {

        List<Menu> menuList = menuMapper.getAllMenusWithRole();
        menuList.forEach(menu -> {
            menu.setRoles(menuRoleMapper.selectRolesByMenuId(menu.getId()));
        });
        return menuList;
    }


}
