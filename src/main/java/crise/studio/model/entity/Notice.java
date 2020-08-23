/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crise.studio.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 *
 * @author Devel
 */
@Entity
public class Notice {

    @Id
    @GeneratedValue(generator = "notice_seq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "notice_seq", table = "table_sequence", initialValue = 0, allocationSize = 1)
    private Integer id;

    private String title;

    @Column(length = 3000)
    private String content;

    @Column(updatable = false)
    private LocalDateTime regDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Transient
    private String simpleRegDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSimpleRegDate() {
        return simpleRegDate;
    }

    public void setSimpleRegDate(String simpleRegDate) {
        this.simpleRegDate = simpleRegDate;
    }
    
    

}
