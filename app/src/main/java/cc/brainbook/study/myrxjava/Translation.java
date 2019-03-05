package cc.brainbook.study.myrxjava;

public class Translation {
    private int status;

    private String content;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Translation{" +
                "status=" + status +
                ", content='" + content + '\'' +
                '}';
    }
}
