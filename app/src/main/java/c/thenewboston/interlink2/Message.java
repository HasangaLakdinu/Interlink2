package c.thenewboston.interlink2;

/**
 * Created by Asus on 9/28/2018.
 */

public class Message {
    private String msender;
    private String mDescription;
    private String mReceiver;
    private String msenderEmail;
    private String mReceiverEmail;
    private String mTime;

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getMsenderEmail() {
        return msenderEmail;
    }

    public void setMsenderEmail(String msenderEmail) {
        this.msenderEmail = msenderEmail;
    }

    public String getmReceiverEmail() {
        return mReceiverEmail;
    }

    public void setmReceiverEmail(String mReceiverEmail) {
        this.mReceiverEmail = mReceiverEmail;
    }

    public String getmReceiver() {
        return mReceiver;
    }

    public void setmReceiver(String mReceiver) {
        this.mReceiver = mReceiver;
    }

    public String getMsender() {
        return msender;
    }

    public void setMsender(String msender) {
        this.msender = msender;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public Message(){

    }

    public Message(String sender,String Description,String Receiver,String receiverEmail,String senderEmail,String time){
        if(sender.trim().equals("")){
            sender="noname";
        }
        if(Receiver.trim().equals("")){
            Receiver="noname";
        }
        if(Description.trim().equals("")){
            Description="noname";
        }

        msender=sender;
        mDescription=Description;
        mReceiver=Receiver;
        msenderEmail=senderEmail;
        mReceiverEmail=receiverEmail;
        mTime=time;
    }
}
