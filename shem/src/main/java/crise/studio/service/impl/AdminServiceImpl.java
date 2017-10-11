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

    /**
     *     아이디로 운영자 TO 를 가져온다.
     *
     * @param account 아이디
     * @return 운영자 TO
     */
    @Override
    public AdminTO getOneAdminByAccount(String account) {
        return adminMapper.admin2AdminTO(adminDao.searchUnique(new Search(Admin.class).addFilterEqual("account", account)));
    }

    /**
     *     아이디로 운영자 TO 및 내부 연관 TO 를 가져온다.
     *
     * @param account 아이디
     * @return 운영자 TO 및 내부 연관 TO
     */
    @Override
    public AdminTO getOneAdminWithAllByAccount(String account) {
        Admin admin = adminDao.searchUnique(new Search(Admin.class).addFilterEqual("account", account));

        if (admin == null) { return null; }

        AdminTO adminTO = adminMapper.admin2AdminTO(admin);

            Set<GrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
         adminTO.setRoles(
                 authorities
        );
        return adminTO;
    }

}
