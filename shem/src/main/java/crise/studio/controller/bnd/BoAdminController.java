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
}
