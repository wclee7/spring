/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 *
 * @author Devel
 */
@Entity
public class Authority {

    @Id
    @GeneratedValue(generator = "authority_seq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "authority_seq", table = "table_sequence", allocationSize = 1, initialValue = 0)
    private Integer id;

    @Column
    private String name;

    @Column(name = "role_name", length = 127, nullable = false, unique = true)
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

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

}
