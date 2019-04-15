/**
 *
 * @param answerId 回答的id
 * @param userId 回答者的id
 * @param entityId 被回答的实体id
 * @param entityType 被回答的实体类型（0是问题，1是答案）
 * @param  answerContent 回答的内容
 *
 */

package model.questionAnswer;
public class Answer {
    private int answerId;
    private int userId;
    private int entityId;
    private int entityType;
    private String answerContent;

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
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

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
}
