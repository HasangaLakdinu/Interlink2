package c.thenewboston.interlink2;


//ACTUALLY THIS CLASS HOLD THE INFORMATION THAT JSON BRINGS

/**
 * Created by Asus on 9/21/2018.
 */
public class ExampleItem {
    private String mImageUrl;
    private String mStudent;
    private String mEmail;

    public ExampleItem(String imageUrl,String student,String email){
        mImageUrl=imageUrl;
        mStudent=student;
        mEmail=email;
    }

    public String getImageUrl(){
        return  mImageUrl;
    }

    public String getStudent(){
        return mStudent;
    }

    public String getEmail(){
        return mEmail;
    }
}
