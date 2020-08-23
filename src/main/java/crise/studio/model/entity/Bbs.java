package crise.studio.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 *
 * @author wclee
 */
@Table(name="bbs", uniqueConstraints={})
@javax.persistence.Entity
public class Bbs {

    @Id
    @GeneratedValue(generator = "bbs_seq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "bbs_seq", table = "table_sequence", initialValue = 1, allocationSize = 1)
    private Integer id;

    @Column(name="title", length=255 , nullable=false )
    private String title;

    @Column(name="content", length = 3000, nullable=false)
    private String content;
    
    @Column(name="writer", length=127, nullable=false)
    private String writer;
    
    @Column(name="bpass", length=511, nullable=false)
    private String bpass;

    @Column(name="regDate", updatable = false)
    private LocalDateTime regDate;
    
    @Column(name="updateDate", updatable = true)
    private LocalDateTime updateDate;
    
    @Column(name="hit", length=11)
    private Integer hit;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//    
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

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getHit() {
        return hit;
    }

    public void setHit(Integer hit) {
        this.hit = hit;
    }
     
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public String getSimpleRegDate() {
        return simpleRegDate;
    }

    public void setSimpleRegDate(String simpleRegDate) {
        this.simpleRegDate = simpleRegDate;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getBpass() {
        return bpass;
    }

    public void setBpass(String bpass) {
        this.bpass = bpass;
    }
        
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BOARD{");
        sb.append("id=").append(id);
        sb.append(", content='").append(content).append('\'');
        sb.append(", hit='").append(hit).append('\'');
        sb.append(", regDate='").append(regDate).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", writer=").append(writer).append('\'');
        sb.append(", regDate=").append(regDate).append('\'');
//        sb.append(", user_id=").append(user.getId()).append('\'');
//        sb.append(", user_name=").append(user.getName());
//        sb.append(", adminGroup=").append(adminGroup);
        sb.append('}');
        return sb.toString();
    }

}
