package crise.studio.common.auth;

import crise.studio.model.TO.AdminTO;
import crise.studio.service.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Objects;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AdminService adminService;

    /**
     *
     * @param auth
     * @return
     */
    @Override
    public Authentication authenticate(Authentication auth) {
        String account = Objects.toString(auth.getPrincipal(), StringUtils.EMPTY);
        String password = Objects.toString(auth.getCredentials(), StringUtils.EMPTY);
        AdminTO adminTO = adminService.getOneAdminByAccount(account);
        
        if (Objects.isNull(adminTO)) {
            throw new BadCredentialsException("존재하지 않는 계정입니다.");
        }

        if (!passwordEncoder.matches(password, adminTO.getPassword())) {
            throw new BadCredentialsException("패스워드가 일치하지 않습니다.");
        }
        adminTO = adminService.getOneAdminWithAllByAccount(account);
        adminTO.setPassword(null);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(adminTO, passwordEncoder.encode(password), adminTO.getAuthorities());
  
        return token;
    }

    @Override
    public boolean supports(Class<?> type) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
}
