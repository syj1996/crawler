package Bean;

public class commonweixin {
    //文章创建时间
    private String create_time;
    //文章修改时间
    private String update_time;
    //文章摘要
    private String digest;
    //原创
    private String Original;
    //封面
    private String cover;
    //标题
    private String title;
    //链接
    private String articleLink;
    //文章ID
    private String aid;

    public commonweixin( String aid,String articleLink,String create_time, String update_time, String digest, String original, String cover, String title) {
        this.create_time = create_time;
        this.update_time = update_time;
        this.digest = digest;
        Original = original;
        this.cover = cover;
        this.title = title;
        this.articleLink = articleLink;
        this.aid = aid;
    }

    @Override
    public String toString() {
        return "commonweixin{" +
                "title='" + title + '\'' +
                ", articleLink='" + articleLink + '\'' +
                ", aid='" + aid + '\'' +
                '}';
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getOriginal() {
        return Original;
    }

    public void setOriginal(String original) {
        Original = original;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }
}