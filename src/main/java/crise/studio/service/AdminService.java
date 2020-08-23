package crise.studio.service;

import java.util.List;
import java.util.Map;
import crise.studio.model.TO.AdminTO;

public interface AdminService {

    AdminTO getOneAdminByAccount(String account);

    AdminTO getOneAdminWithAllByAccount(String account);

    AdminTO getOnePureById(Integer id);

//    AdminGroupTO getOneAdminGroupById(Integer adminGroupId);
    
//    AdminTO getOneAdminByAdminGroupId(Integer adminGroupId);

    List<AdminTO> getListAdminBySearchCondition(Map<String, Object> conditionMap);

//    List<AdminGroupTO> getListAdmimGroupBySearchCondition(Map conditionMap);
//
//    List<AdminMenuTO> getListAdminMenuBySearchCondition(Map conditionMap);
//
//    List<AdminMenuTO> getListAdminMenu();

//    public List<Integer> getListMenuIdOfAdminGroup(Integer adminGroupId);

  //  boolean saveOfAdminGroupAndAdminMenu(AdminGroupTO adminGroup, String[] menuIds, AdminTO updater);

    boolean saveOfAdmin(AdminTO admin, AdminTO updater);

    boolean removeOfAdmin(Integer selectedId);

//    boolean removeOfAdminGroup(Integer selectedId);

}
