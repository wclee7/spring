package crise.studio.model.TO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AdminTO implements UserDetails {

    private Integer id;

    private String account;

    @JsonIgnore
    private String password;

    private String name;

    private String email;

    private String contact;

    private boolean isActive;

//    private AdminGroupTO adminGroup;

//    private List<AdminMenuTO> menus;

    private Set<? extends GrantedAuthority> roles;

//    private FormationUpdateHistoryTO lastUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    

//    public AdminGroupTO getAdminGroup() {
//        return adminGroup;
//    }
//
//    public void setAdminGroup(AdminGroupTO adminGroup) {
//        this.adminGroup = adminGroup;
//    }
//
//    public List<AdminMenuTO> getMenus() {
//        return menus;
//    }
//
//    public void setMenus(List<AdminMenuTO> menus) {
//        this.menus = menus;
//    }

    public Set<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Set<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }

//    public FormationUpdateHistoryTO getLastUpdate() {
//        return lastUpdate;
//    }
//
//    public void setLastUpdate(FormationUpdateHistoryTO lastUpdate) {
//        this.lastUpdate = lastUpdate;
//    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
