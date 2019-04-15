package model.user;

public class Letter {
    //站内信的id
    private int letterId;
    //发送者id
    private int senderId;
    //接收者id
    private int receiverId;
    //站内信内容
    private String letterContent;
    //站内信状态(0未读，1已读)
    private int letterStatus;

    public int getLetterId() {
        return letterId;
    }

    public void setLetterId(int letterId) {
        this.letterId = letterId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getLetterContent() {
        return letterContent;
    }

    public void setLetterContent(String letterContent) {
        this.letterContent = letterContent;
    }

    public int getLetterStatus() {
        return letterStatus;
    }

    public void setLetterStatus(int letterStatus) {
        this.letterStatus = letterStatus;
    }
}
