/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.model.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.Type;

/**
 *
 * @author Devel
 */
@Entity
@Table(name = "user_group")
public class UserGroup {

    @Id
    @GeneratedValue(generator = "user_group_seq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "user_group_seq", table = "table_sequence", initialValue = 0, allocationSize = 1)
    private Integer id;

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "user_group_authority",
            joinColumns = {
                @JoinColumn(name = "user_group_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "authority_id")},
            uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_group_id", "authority_id"})}
    )
    @CollectionId(columns = {
        @Column(name = "id")}, generator = "user_group_authority_seq", type = @Type(type = "int"))
    @TableGenerator(name = "user_group_authority_seq", allocationSize = 1, initialValue = 1, table = "table_sequence")
    private List<Authority> authoritys;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Authority> getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(List<Authority> authoritys) {
        this.authoritys = authoritys;
    }
    
    

}
