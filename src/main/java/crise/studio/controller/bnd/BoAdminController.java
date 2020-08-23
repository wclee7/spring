package crise.studio.controller.bnd;

import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import crise.studio.model.TO.AdminTO;
import crise.studio.service.AdminService;

@Controller
@RequestMapping(value = "/bnd/")
public class BoAdminController {

    private final Logger logger = LoggerFactory.getLogger(BoAdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /*
    로그인
     */
    @PreAuthorize(value = "permitAll")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginView() {

        logger.info("## Login View");

        ModelAndView view = new ModelAndView("login");

        return view;

    }

    /*
    운영자 리스트 
    GET
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/master/admin", method = RequestMethod.GET)
    public ModelAndView adminListView() {

        logger.info("## Master - Admin List View");

        ModelAndView view = new ModelAndView("master/adminList");

        return view;
    }

    /*
    운영자 리스트
    AJAX를 통해 호출
    POST
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/master/admin/list",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<AdminTO> adminList(
            String name,
            String account,
            String adminGroupName,
            Boolean isActive
    ) {

        Map<String, Object> conditionMap = new HashMap<>();
        conditionMap.put("name", name);
        conditionMap.put("account", account);
        conditionMap.put("adminGroupName", adminGroupName);
        conditionMap.put("isActive", isActive);

        logger.info("Search Condition : {}", conditionMap.toString());

        List<AdminTO> adminTOList = adminService.getListAdminBySearchCondition(conditionMap);

        if (adminTOList != null) {
            return adminTOList;
        } else {
            return new ArrayList();
        }
    }

    /*
    운영자 추가 폼
    GET
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/master/admin/insert", method = RequestMethod.GET)
    public ModelAndView adminInsertView() {

        logger.info("## Master - Admin Insert View");

        ModelAndView view = new ModelAndView("master/adminEdit");
        view.addObject("editMode", "insert");

        return view;
    }

    /*
    운영자 ID 중복 확인
    AJAX를 통해 호출
     */
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            value = "/master/admin/isOverapAccount",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public Boolean isOverapAccount(
            String account
    ) {
        logger.info("## Master - Admin Account Overrap Check");

        AdminTO adminTO = adminService.getOneAdminByAccount(account);

        if (adminTO != null) {
            return true;
        } else {
            return false;
        }
    }

    /*
    운영자 추가
    POST
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/master/admin/insert",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public void adminInsert(@ModelAttribute(value = "admin") AdminTO adminTO) {
        logger.info("## Master - Admin Insert Ajax");

        adminTO.setPassword(passwordEncoder.encode(adminTO.getPassword()));
        AdminTO updater = (AdminTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        logger.info("##############################");
        logger.info("Data Updater : {}", updater);
        logger.info("Data adminTO : {}", adminTO);
        logger.info("##############################");

        adminService.saveOfAdmin(adminTO, updater);
    }

    /*
    운영자 수정 폼
    GET
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/master/admin/update/{adminId}", method = RequestMethod.GET)
    public ModelAndView adminUpdateView(
            @PathVariable(value = "adminId") Integer adminId
    ) {

        logger.info("## Master - Admin Update View");

        AdminTO adminTO = adminService.getOnePureById(adminId);
        ModelAndView view = new ModelAndView("master/adminEdit");
        view.addObject("editMode", "update");
        view.addObject("admin", adminTO);

        return view;
    }

    /*
    운영자 수정
    POST
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/master/admin/update",
            method = RequestMethod.POST,
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public void adminUpdate(
            @ModelAttribute(value = "admin") AdminTO adminTO
    ) {
        if (StringUtils.isNotEmpty(adminTO.getPassword())) {
            adminTO.setPassword(passwordEncoder.encode(adminTO.getPassword()));
        }
        AdminTO updater = (AdminTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        logger.info("##############################");
        logger.info("Data Updater : {}", updater);
        logger.info("Data admin : {}", adminTO);
        logger.info("##############################");
        adminService.saveOfAdmin(adminTO, updater);
    }

    /*
    운영자 삭제
    POST
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(
            value = "/master/admin/deleteSelected",
            method = RequestMethod.POST
    )
    public void selectedAdminDelete(@RequestParam(value = "selectedIds") Integer[] selectedIds) {

        for (int i = 0; i < selectedIds.length; i++) {
            adminService.removeOfAdmin(selectedIds[i]);
        }
    }

    /*
    권한 그룹 리스트
    GET
     */
    @PreAuthorize("hasAuthority('ADMIN_GROUP')")
    @RequestMapping(value = "/master/adminGroup", method = RequestMethod.GET)
    public ModelAndView adminGroupListView() {
        logger.info("## Master - AdminGroup List View");

        ModelAndView view = new ModelAndView("master/groupList");

        return view;
    }

    /*
    권한 그룹 추가 폼
    GET
     */
    @PreAuthorize("hasAuthority('ADMIN_GROUP')")
    @RequestMapping(value = "/master/adminGroup/insert", method = RequestMethod.GET)
    public ModelAndView adminGroupInsertView() {
        logger.info("## Master - AdminGroup Insert View");

        ModelAndView view = new ModelAndView("master/groupEdit");
        view.addObject("editMode", "insert");

        return view;
    }

//    /*
//   권한 그룹 추가 
//    POST
//     */
//    @ResponseBody
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize("hasAuthority('ADMIN_GROUP')")
//    @RequestMapping(
//            value = "/master/adminGroup/insert",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
//    )
//    public void adminGroupInsert(
//            @ModelAttribute(value = "adminGroup") AdminGroupTO adminGroup,
//            @RequestParam(value = "ownMenuIds") String ownMenuIds
//    ) {
//        logger.info("## Master - AdminGroup Insert Ajax");
//
//        logger.info("Admin Group : {}", adminGroup.toString());
//        logger.info("Own Menu Ids : {}", ownMenuIds);
//
//        AdminTO updater = (AdminTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        logger.info("##############################");
//        logger.info("Data Updater : {}", updater);
//        logger.info("##############################");
//
//        adminService.saveOfAdminGroupAndAdminMenu(adminGroup, ownMenuIds.split(","), updater);
//    }
//
//    /*
//    권한 그룹 수정 폼
//    GET
//     */
//    @PreAuthorize("hasAuthority('ADMIN_GROUP')")
//    @RequestMapping(value = "/master/adminGroup/update/{adminGroupId}", method = RequestMethod.GET)
//    public ModelAndView adminGroupUpdateView(
//            @PathVariable(value = "adminGroupId") Integer adminGroupId
//    ) {
//        logger.info("## Master - AdminGroup Update View");
//        ModelAndView view = new ModelAndView("master/groupEdit");
//        view.addObject("editMode", "update");
//        AdminGroupTO adminGroup = adminService.getOneAdminGroupById(adminGroupId);
//
//        view.addObject("adminGroup", adminGroup);
//
//        return view;
//    }
//
//    /*
//    권한 그룹 수정
//    POST
//     */
//    @ResponseBody
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @PreAuthorize("hasAuthority('ADMIN_GROUP')")
//    @RequestMapping(
//            value = "/master/adminGroup/update",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
//    )
//    public void adminGroupUpdate(
//            @ModelAttribute(value = "adminGroup") AdminGroupTO adminGroup,
//            @RequestParam(value = "ownMenuIds") String ownMenuIds
//    ) {
//        logger.info("## Master - AdminGroup Insert Ajax");
//
//        logger.info("Admin Group : {}", adminGroup.toString());
//        logger.info("Own Menu Ids : {}", ownMenuIds);
//
//        AdminTO updater = (AdminTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        logger.info("##############################");
//        logger.info("Data Updater : {}", updater);
//        logger.info("##############################");
//
//        adminService.saveOfAdminGroupAndAdminMenu(adminGroup, ownMenuIds.split(","), updater);
//    }
//
//    /*
//    권한 그룹 리스트
//    POST
//     */
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAnyAuthority('ADMIN_GROUP', 'ADMIN')")
//    @RequestMapping(
//            value = "/master/adminGroup/list",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public List<AdminGroupTO> adminGroupList(
//            String name
//    ) {
//
//        logger.info("## Master - AdminGroup List Grid Data");
//
//        if (name == null) {
//            name = "";
//        }
//        Map<String, Object> conditionMap = new HashMap<>();
//        conditionMap.put("name", name);
//
//        logger.info("Search Condition : {}", conditionMap.toString());
//        List<AdminGroupTO> adminGroupTOList = adminService.getListAdmimGroupBySearchCondition(conditionMap);
//        if (adminGroupTOList != null) {
//            return adminGroupTOList;
//        } else {
//            return new ArrayList<>();
//        }
//    }
//
//    /*
//    권한 메뉴 리스트
//    POST
//     */
//    @ResponseBody
//    @ResponseStatus(HttpStatus.OK)
//    @PreAuthorize("hasAuthority('ADMIN_GROUP')")
//    @RequestMapping(
//            value = "/master/adminMenu/list",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public List<AdminMenuTO> adminMenuList(
//            Integer adminGroupId
//    ) {
//
//        logger.info("## Master - AdminMenu List Grid Data");
//
//        //logger.info(sc.toString());
//        Map<String, Object> conditionMap = new HashMap<>();
//        conditionMap.put("type", "SUB"); // GRID 
//        conditionMap.put("adminGroupId", adminGroupId);
//
//        logger.info("Search Condition : {}", conditionMap.toString());
//        List<AdminMenuTO> adminMenuTOList = adminService.getListAdminMenuBySearchCondition(conditionMap);
//        return adminMenuTOList;
//    }
//
//    /*
//    권한 메뉴 삭제
//    POST
//     */
//    @ResponseBody
//    @PreAuthorize("hasAuthority('ADMIN_GROUP')")
//    @RequestMapping(
//            value = "/master/adminGroup/deleteSelected",
//            method = RequestMethod.POST,
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )
//    public boolean selectedAdminGroupDelete(@RequestParam(value = "selectedIds") Integer[] selectedIds) {
//
//        boolean dFlag = true;
//        for (int i = 0; i < selectedIds.length; i++) {
//            if (adminService.getOneAdminByAdminGroupId(selectedIds[i]) != null) {
//                dFlag = false;
//                break;
//            }
//        }
//
//        if (dFlag) {
//            for (int i = 0; i < selectedIds.length; i++) {
//                adminService.removeOfAdminGroup(selectedIds[i]);
//            }
//        }
//        return dFlag;
//        //return dFlag;
//    }
}
