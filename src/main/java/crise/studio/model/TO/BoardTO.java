package crise.studio.model.TO;

public class BoardTO  {

    private Integer id;
    private String title;
    private String content;
    private String  regDate;
    private String updateDate;
    private Integer hit;
    private String simpleRegDate;

    public BoardTO() {}
    
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

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
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
        final StringBuilder sb = new StringBuilder("boardTO{");
        sb.append("id=").append(id);
        sb.append(", content='").append(content).append('\'');
        sb.append(", hit='").append(hit).append('\'');
        sb.append(", regDate='").append(regDate).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", updateDate=").append(updateDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
