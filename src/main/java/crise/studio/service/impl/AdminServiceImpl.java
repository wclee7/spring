package crise.studio.service.impl;

import com.googlecode.genericdao.search.Search;
import crise.studio.model.TO.AdminTO;
import crise.studio.model.entity.Admin;
import crise.studio.model.structmapper.AdminMapper;
import crise.studio.orm.AdminDao;
import crise.studio.service.AdminService;
import java.util.HashSet;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Service
public class AdminServiceImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private AdminMapper adminMapper;

//    @Autowired
//    private AdminGroupDao adminGroupDao;

//    @Autowired
//    private CommonServiceLogic commonServiceLogic;
//
////    @Autowired
////    private AdminMenuDao adminMenuDao;
//
    /**
     * <pre>
     *     아이디로 운영자 TO 를 가져온다.
     * </pre>
     *
     * @param account 아이디
     * @return 운영자 TO
     */
    @Override
    public AdminTO getOneAdminByAccount(String account) {
        return adminMapper.admin2AdminTO(adminDao.searchUnique(new Search(Admin.class).addFilterEqual("account", account)));
    }
//
    /**
     * <pre>
     *     아이디로 운영자 TO 및 내부 연관 TO 를 가져온다.
     * </pre>
     *
     * @param account 아이디
     * @return 운영자 TO 및 내부 연관 TO
     */
    @Override
    public AdminTO getOneAdminWithAllByAccount(String account) {
        Admin admin = adminDao.searchUnique(new Search(Admin.class).addFilterEqual("account", account));

        if (admin == null) { return null; }

        AdminTO adminTO = adminMapper.admin2AdminTO(admin);
//        AdminGroup adminGroup = admin.getAdminGroup();

//        if (adminGroup == null) { return adminTO; }

//        adminTO.setAdminGroup(adminMapper.adminGroup2AdminGroupTO(adminGroup));

//        Map<String, List<AdminMenu>> usableMenus = adminGroup.getMenus().stream().collect(groupingBy(AdminMenu::getType));
//
//        List<AdminMenuTO> primeMenuWithChild = usableMenus.get(Enums.BackOfficeMenuLevel.PRIME.name()).stream()
//            .map(p -> {
//                AdminMenuTO primeMenuTO = adminMapper.adminMenuTO2AdminMenu(p);
//                List<AdminMenu> sub = p.getChildMenus().stream()
//                    .filter(child -> usableMenus.get(Enums.BackOfficeMenuLevel.SUB.name()).contains(child))
//                    .collect(toList());
//
//                primeMenuTO.setChildren(adminMapper.adminMenuList2AdminMenuTOList(sub));
//                return primeMenuTO;
//            }).collect(toList());
//
//        adminTO.setMenus(primeMenuWithChild);
//        adminTO.setRoles(
//            adminGroup.getMenus().stream()
//                .map(adminMenu -> new SimpleGrantedAuthority(adminMenu.getRoleName()))
//                .collect(toSet())
//        );
           // GrantedAuthority authority= new SimpleGrantedAuthority("ADMIN");             
            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
         adminTO.setRoles(
//            adminGroup.getMenus().stream()
//                .map(adminMenu -> new SimpleGrantedAuthority(adminMenu.getRoleName()))
//                .collect(toSet())
                 authorities
        );
        return adminTO;

    }

    /**
     * 조건에 따른 운영자 검색
     *
     * @param conditionMap : 검색 조건
     * @name : 운영자 이름
     * @account : 운영자 아이디
     * @adminGroupName : 권한 그룹 명
     * @isActive : 사용 여부
     * @return 운영자 리스트 반환
     */
    @Override
    public List<AdminTO> getListAdminBySearchCondition(Map<String, Object> conditionMap) {

        List<AdminTO> adminTOList = null;
        final String name = (String) conditionMap.get("name");
        final String account = (String) conditionMap.get("account");
        final String adminGroupName = (String) conditionMap.get("adminGroupName");

        Search s = new Search(Admin.class);
        s.addFetch("adminGroup");
        if (StringUtils.isNotBlank(name)) {
            s.addFilterLike("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(account)) {
            s.addFilterLike("account", "%" + account + "%");
        }
        if (StringUtils.isNotBlank(adminGroupName)) {
            s.addFilterLike("adminGroup.name", "%" + adminGroupName + "%");
        }
        //isActive is boolean Type
        if (conditionMap.get("isActive") != null) {
            s.addFilterEqual("isActive", conditionMap.get("isActive"));
        }
        List<Admin> adminList = adminDao.search(s);

        if (CollectionUtils.isNotEmpty(adminList)) {
            adminTOList = adminMapper.adminList2AdminTOList(adminList);

//            for (AdminTO adminTO : adminTOList) {
//                FormationUpdateHistoryTO formationUpdateHistoryTO = commonServiceLogic.getOneFormationUpdateHistory(Enums.ReferenceTable.ADMIN, adminTO.getId());
//                adminTO.setLastUpdate(formationUpdateHistoryTO);
//            }
        }

        return adminTOList;
    }

    /**
     * 운영자 아이디를 이용한 검색
     *
     * @param id
     * @return 운영자 반환
     */
    @Override
    public AdminTO getOnePureById(Integer id) {
        AdminTO adminTO = adminMapper.admin2AdminTO(adminDao.searchUnique(new Search(Admin.class).addFilterEqual("id", id)));
//       FormationUpdateHistoryTO formationUpdateHistoryTO = commonServiceLogic.getOneFormationUpdateHistory(Enums.ReferenceTable.ADMIN, adminTO.getId());
//        adminTO.setLastUpdate(formationUpdateHistoryTO);
        return adminTO;
    }

//    /**
//     * 권한 그룹 리스트 검색
//     *
//     * @param conditionMap
//     * @name
//     * @return 권한 그룹 리스트 반환
//     */
//    @Override
//    public List<AdminGroupTO> getListAdmimGroupBySearchCondition(Map conditionMap) {
//        List<AdminGroupTO> adminGroupTOList = adminGroupDao.getListAdminGroupBySearchCondition(conditionMap);
//
//        if (CollectionUtils.isNotEmpty(adminGroupTOList)) {
//            for (AdminGroupTO adminGroupTO : adminGroupTOList) {
//                //권한그룹을 최종 수정한 운영자의 정보를 대입한다.
//                FormationUpdateHistoryTO formationUpdateHistoryTO = commonServiceLogic.getOneFormationUpdateHistory(Enums.ReferenceTable.ADMIN_GROUP, adminGroupTO.getId());
//                adminGroupTO.setLastUpdate(formationUpdateHistoryTO);
//            }
//        }
//
//        return adminGroupTOList;
//    }

    /**
     * 운영자 저장 또는 업데이트
     *
     * @param adminTO : 운영자 정보
     * @param updater : 업데이터
     * @return 저장 성공 유무 반환
     */
    @Override
    public boolean saveOfAdmin(AdminTO adminTO, AdminTO updater) {
        boolean result = false;

        Admin admin = adminMapper.adminTO2Admin(adminTO);
//        Enums.FormationUpdateHistoryType historyType = Enums.FormationUpdateHistoryType.INSERT;

        //운영자 등록 또는 수정 할 때, 권한그룹을 선택하지 않았을 경우 (권한그룹을 선택하지 않으면, adminGroup은 null이 아니고 나머지 멤버 변수는 null값이 들어온다.)
//        if (admin.getAdminGroup() != null && admin.getAdminGroup().getId() == null) {
//            admin.setAdminGroup(null);
//        }

        //update시
        if (admin.getId() != null) {
            if (StringUtils.isBlank(admin.getPassword())) {
                Search s = new Search(Admin.class).addField("password").addFilterEqual("id", admin.getId());
                String password = adminDao.searchUnique(s);
                admin.setPassword(password);
            }
//            historyType = Enums.FormationUpdateHistoryType.UPDATE;
        }

        result = adminDao.save(admin);
//        commonServiceLogic.saveOfFormationUpdateHistory(historyType, Enums.ReferenceTable.ADMIN, admin.getId(), updater);
        return result;
    }

    /**
     * 운영자 삭제
     *
     * @param selectedId : 운영자 정보
     * @return 삭제 성공 유무 반환
     */
    @Override
    public boolean removeOfAdmin(Integer selectedId) {
        return adminDao.removeById(selectedId);
    }

//    /**
//     * 권한 그룹 삭제
//     *
//     * @param selectedId : 운영자 정보
//     * @return 삭제 성공 유무 반환
//     */
//    @Override
//    public boolean removeOfAdminGroup(Integer selectedId) {
//        return adminGroupDao.removeById(selectedId);
//    }
//
//    /**
//     * 권한 그룹의 메뉴 리스트 검색
//     *
//     * @param conditionMap : 검색 조건
//     * @adminGroupId : 권한 그룹 아이디
//     * @return 메뉴 리스트 반환 상세 : 모든 메뉴가 출력 되지만 선택한 권한 그룹이 접근 가능한 메뉴는 HasRole 변수에
//     * 1값을 넣어서 출력시 체크박스가 체크되어 있는 상태로 출력된다.
//     *
//     */
//    @Override
//    public List<AdminMenuTO> getListAdminMenuBySearchCondition(Map conditionMap) {
//        List<AdminMenuTO> adminMenuTOList = getListAdminMenu();
//        //권한 그룹의 아이디를 매개변수로 전달받아 해당 권한그룹이 접근 가능한 메뉴의 아이디 리스트를 반환받는다.
//        List<Integer> menuIdList = getListMenuIdOfAdminGroup((Integer) conditionMap.get("adminGroupId"));
//
//        if (CollectionUtils.isNotEmpty(menuIdList)) {
//            try {
//                int index = 0;
//                for (AdminMenuTO adminMenuTO : adminMenuTOList) {
//                    if (adminMenuTO.getId().equals(menuIdList.get(index))) {
//                        adminMenuTO.setHasRole(1);
//                        index++;
//                    }
//                }
//            } catch (IndexOutOfBoundsException e) {
//            }
//        }
//        return adminMenuTOList;
//
//    }
//
//    /**
//     * 모든 권한 메뉴 리스트 검색
//     *
//     * @return 운영자 메뉴 리스트 반환
//     */
//    @Override
//    public List<AdminMenuTO> getListAdminMenu() {
//
//        Search s = new Search(AdminMenu.class);
//        s.addFetch("parentMenu");
//        s.addFilterEqual("type", "SUB");
//        s.addSortAsc("id");
//
//        List<AdminMenu> adminMenuList = adminMenuDao.search(s);
//        return adminMapper.adminMenuList2AdminMenuTOList(adminMenuList);
//    }
//
//    /**
//     * 권한 그룹이 접근 가능한 메뉴 아이디 검색
//     *
//     * @param adminGroupId : 권한 그룹 아이디
//     * @return 메뉴 아이디 리스트 반환
//     */
//    @Override
//    public List<Integer> getListMenuIdOfAdminGroup(Integer adminGroupId) {
//        return adminGroupDao.getListMenuIdOfAdminGroup(adminGroupId);
//    }
//
//    /**
//     * 권한 그룹 검색
//     *
//     * @param adminGroupId : 권한 그룹 아이디
//     * @return 권한 그룹 정보 반환
//     */
//    @Override
//    public AdminGroupTO getOneAdminGroupById(Integer adminGroupId) {
//
//        Search s = new Search(AdminGroup.class);
//        s.addFilterEqual("id", adminGroupId);
//        AdminGroupTO adminGroupTO = adminMapper.adminGroup2AdminGroupTO(adminGroupDao.searchUnique(s));
//
//        //최근 업데이트 날짜 저장
//        FormationUpdateHistoryTO formationUpdateHistoryTO = commonServiceLogic.getOneFormationUpdateHistory(Enums.ReferenceTable.ADMIN_GROUP, adminGroupTO.getId());
//        adminGroupTO.setLastUpdate(formationUpdateHistoryTO);
//        return adminGroupTO;
//    }
//
//    /**
//     * 권한 그룹 저장 또는 수정
//     *
//     * @param adminGroupTO : 권한 그룹 정보
//     * @param menuIds : 메뉴아이디들
//     * @param updater : 업데이터
//     * @return 저장 또는 수정 성공 유무 반환
//     */
//    @Override
//    public boolean saveOfAdminGroupAndAdminMenu(AdminGroupTO adminGroupTO, String[] menuIds, AdminTO updater) {
//
//        AdminGroup adminGroup = adminMapper.adminGroupTO2AdminGroup(adminGroupTO);
//        Map<String, AdminMenu> adminMenuMap = new HashMap<>();
//        //선택한 메뉴가 존재 할 경우
//        if (menuIds != null && menuIds[0].length() > 0) {
//            ArrayList<AdminMenu> menus = new ArrayList<>();
//            for (String menuId : menuIds) {
//                //menuId로 메뉴를 검색한다.
//                AdminMenu adminMenu = adminMenuDao.find(Integer.valueOf(menuId));
//
//                if (adminMenu != null) {
//                    adminMenuMap.put(menuId, adminMenu);
//                    //Menu의 Parent가 Hashmap에 저장 되지 않았을 경우 (중복 저장 방지) 
//                    if (adminMenuMap.get(adminMenu.getParentMenu().getId()) == null) {
//                        //메뉴의 Parent를 저장한다. (Parent는 메뉴 타입 값이 "Prime" 일 경우를 말한다.)
//                        adminMenuMap.put(String.valueOf(adminMenu.getParentMenu().getId()), adminMenu.getParentMenu());
//                    }
//                }
//            }
//            for (Map.Entry<String, AdminMenu> entry : adminMenuMap.entrySet()) {
//                menus.add(entry.getValue());
//            }
//            adminGroup.setMenus(menus);
//        }
//
//        //권한그룹 정보 저장
//        boolean result = adminGroupDao.save(adminGroup);
//        //업데이터 히스토리 저장
//        if (adminGroupTO.getId() != null) {
//            commonServiceLogic.saveOfFormationUpdateHistory(Enums.FormationUpdateHistoryType.UPDATE, Enums.ReferenceTable.ADMIN_GROUP, adminGroupTO.getId(), updater);
//
//        } else {
//            commonServiceLogic.saveOfFormationUpdateHistory(Enums.FormationUpdateHistoryType.INSERT, Enums.ReferenceTable.ADMIN_GROUP, adminGroup.getId(), updater);
//        }
//        return result;
//    }
//
//    @Override
//    public AdminTO getOneAdminByAdminGroupId(Integer adminGroupId) {
//        return adminMapper.admin2AdminTO(adminDao.searchUnique(new Search(Admin.class).addFilterEqual("adminGroup.id", adminGroupId)));
//    }

}
