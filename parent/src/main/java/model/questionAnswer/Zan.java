package model.questionAnswer;
/*
定义赞的实体类
 */
public class Zan {
    private int zanId;
    private int zanUserId;
    private int zanAnswerId;

    public int getZanId() {
        return zanId;
    }

    public void setZanId(int zanId) {
        this.zanId = zanId;
    }

    public int getZanUserId() {
        return zanUserId;
    }

    public void setZanUserId(int zanUserId) {
        this.zanUserId = zanUserId;
    }

    public int getZanAnswerId() {
        return zanAnswerId;
    }

    public void setZanAnswerId(int zanAnswerId) {
        this.zanAnswerId = zanAnswerId;
    }
}
