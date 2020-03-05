package model;


public class Messages {
    private int id;
    private int user_id;
    private int admin_id;
    private int sender;
    private String message;
    private String dateTime;

   
   

    public Messages() {
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
     public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


    @Override
    public String toString() {
        return "Messages{" + "id=" + id + ", student_id=" + user_id + ", teacher_id=" + admin_id + ", sender=" + sender + ", message=" + message + ", dateTime=" + dateTime + '}';
    }

 
    
    
    
}
