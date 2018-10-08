package c.thenewboston.interlink2;

//THIS CLASS KEEPS DATA WHICH IS WANTO TO DISPLAY A PARTICULAR MESSAGE IN RECYCLER VIEW

/**
 * Created by Asus on 9/28/2018.
 */

public class ExampleItem2 {
    private String mDescription;
    private String mSender;
    private String mTimer;
    private String mSenderEmail;

    public String getmSenderEmail() {
        return mSenderEmail;
    }

    public String getmTimer() {
        return mTimer;
    }

    public ExampleItem2(String Description, String Sender, String Timer,String SenderEmail){
        mDescription=Description;
        mSender=Sender;
        mTimer=Timer;
        mSenderEmail=SenderEmail;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmSender() {
        return mSender;
    }
}
