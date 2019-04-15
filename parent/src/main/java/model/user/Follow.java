package model.user;

/*
定义关注实体类
 */
public class Follow {
    //id
    private int followId;
    //关注者的id
    private int userId;
    //被关注实体的id
    private int entityId;
    //关注的类型
    private String type;

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
