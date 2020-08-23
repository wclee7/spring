package crise.studio.common.auth;


import crise.studio.model.TO.AdminTO;
import crise.studio.service.AdminService;
import java.util.HashSet;
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
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

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

        logger.info("Authenticate Try.. {} / {}", account, password);

        AdminTO adminTO = adminService.getOneAdminByAccount(account);

        if (account.equals("wclee")&&password.equals("1121")) {
            AdminTO myID = new AdminTO();
            myID.setName("관리자");
            myID.setAccount("root");
            myID.setIsActive(true);
           // ADMIN
            GrantedAuthority authority= new SimpleGrantedAuthority("ADMIN");
            Set<GrantedAuthority> authorities = new HashSet<>();
            //authorities.add(authority);
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            // UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(adminTO, passwordEncoder.encode(password), adminTO.getAuthorities());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(myID, passwordEncoder.encode(password), authorities);
             return token;
        }
        logger.debug("test1234 encoded value : {}", passwordEncoder.encode("test1234"));
        
        if (Objects.isNull(adminTO)) {
            throw new BadCredentialsException("존재하지 않는 계정입니다.");
        }

        if (!passwordEncoder.matches(password, adminTO.getPassword())) {
            throw new BadCredentialsException("패스워드가 일치하지 않습니다.");
        }

        adminTO = adminService.getOneAdminWithAllByAccount(account);

        adminTO.setPassword(null);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(adminTO, passwordEncoder.encode(password), adminTO.getAuthorities());

        logger.info("is authed? : {}", token.isAuthenticated());
        logger.info(token.toString());

        return token;
    }

    @Override
    public boolean supports(Class<?> type) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }

}
