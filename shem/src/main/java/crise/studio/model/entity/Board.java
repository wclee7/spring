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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 *
 * @author wclee
 */
@Table(name="board", uniqueConstraints={})
@javax.persistence.Entity
public class Board {

    @Id
    @GeneratedValue(generator = "board_seq", strategy = GenerationType.TABLE)
    @TableGenerator(name = "board_seq", table = "table_sequence", initialValue = 0, allocationSize = 1)
    private Integer id;

    @Column(name="title", length=255 , nullable=false )
    private String title;

    @Column(name="content", length = 3000, nullable=false)
    private String content;

    @Column(name="regDate", updatable = false)
    private LocalDateTime regDate;
    
    @Column(name="updateDate", updatable = true)
    private LocalDateTime updateDate;
    
    @Column(name="hit", length=11)
    private Integer hit;

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
     
    public String getSimpleRegDate() {
        return simpleRegDate;
    }

    public void setSimpleRegDate(String simpleRegDate) {
        this.simpleRegDate = simpleRegDate;
    }
        
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Board{");
        sb.append("id=").append(id);
        sb.append(", content='").append(content).append('\'');
        sb.append(", hit='").append(hit).append('\'');
        sb.append(", regDate='").append(regDate).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", regDate=").append(regDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
