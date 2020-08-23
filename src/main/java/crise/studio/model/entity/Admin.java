package crise.studio.model.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Table(name = "tl_admin", uniqueConstraints = {})
@javax.persistence.Entity
public class Admin {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "admin_seq")
    @TableGenerator(name = "admin_seq", allocationSize = 1, initialValue = 1, table = "tl_template_sequence")
    private Integer id;

    @Column(name="account",length = 127, nullable = false, unique = true)
    private String account;

    @Column(name="password",length = 511, nullable = false)
    private String password;

    @Column(name="name",length = 127, nullable = false)
    private String name;

    @Column(name="email",length = 511)
    private String email;

    @Column(name="contact",length = 127)
    private String contact;

    @Column(columnDefinition = "CHAR(1)", name = "is_active", nullable = false)
    @Type(type = "yes_no")
    private boolean isActive = false;

//    @ManyToOne(fetch = FetchType.LAZY, optional = true)
//    @JoinColumn(name = "admin_group_id", nullable = true)
//    private AdminGroup adminGroup;

    public Admin() {
    }

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

//    public AdminGroup getAdminGroup() {
//        return adminGroup;
//    }
//
//    public void setAdminGroup(AdminGroup adminGroup) {
//        this.adminGroup = adminGroup;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        return new EqualsBuilder()
                .append(id, admin.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .toHashCode();
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Admin{");
        sb.append("id=").append(id);
        sb.append(", account='").append(account).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", contact='").append(contact).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", isActive=").append(isActive);
//        sb.append(", adminGroup=").append(adminGroup);
        sb.append('}');
        return sb.toString();
    }
}

