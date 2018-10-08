package c.thenewboston.interlink2;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by Asus on 9/28/2018.
 */

public class ExampleAdapter2 extends RecyclerView.Adapter<ExampleAdapter2.ExampleViewHolder> {

    // HERE CONTEXT IS CHAT ACTIVITY
    private Context mContext;
    private ArrayList<ExampleItem2> mExampleList;
    private OnItemClickListener mListener;
    private String mMyemail;




    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener {
        public boolean onItemLongClicked(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //HERE IS THE CONSTRUCTOR THAT REQUIRE A CONTEXT,ARRAYLIST,STRING THAT MY EMAIL COMING FROM CHAT ACTIVITY

    public ExampleAdapter2(Context context,ArrayList<ExampleItem2> exampleList,String myemail){
        mContext=context;
        mExampleList=exampleList;
        mMyemail=myemail;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.example_item2,parent,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem2 currentItem=mExampleList.get(position);

       String msgDesc=currentItem.getmDescription();
       String msgSender=currentItem.getmSender();
       String msgSenderEmail=currentItem.getmSenderEmail();
       String msgTime=currentItem.getmTimer();



  if(msgSenderEmail.equals(mMyemail)) {

      //SET THESE ATTRIBUTES FOR XML
      holder.mTextViewSender.setText(msgSender);
      holder.mTextViewLDescription.setText(msgDesc);
      holder.mTextViewTimer.setText(msgTime);
      holder.mTextViewSender.setTextColor(Color.WHITE);
      holder.mCardView.setCardBackgroundColor(0xFF00838F);
      //HERE CREATE A LINEAR LAYOUT PROGRAMATICALLY AND CHANGE SOME LAYOUT MARGIN ATTRIBUTE
      LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      holder.mCardView.setLayoutParams(cardViewParams);
      ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) holder.mCardView.getLayoutParams();
      cardViewMarginParams.setMargins(300, 10, 0, 0);
      holder.mCardView.requestLayout();
  }
  else {

      holder.mTextViewSender.setText(msgSender);
      holder.mTextViewLDescription.setText(msgDesc);
      holder.mTextViewTimer.setText(msgTime);
      holder.mCardView.setCardBackgroundColor(Color.WHITE);
      holder.mTextViewSender.setTextColor(0xFF00838F);
      LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      holder.mCardView.setLayoutParams(cardViewParams);

      ViewGroup.MarginLayoutParams cardViewMarginParams = (ViewGroup.MarginLayoutParams) holder.mCardView.getLayoutParams();
      cardViewMarginParams.setMargins(0, 10, 0, 0);
      holder.mCardView.requestLayout();
  }

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{


        public TextView mTextViewSender;
        public TextView mTextViewLDescription;
        public TextView mTextViewTimer;
        public CardView mCardView;

        public ExampleViewHolder(View itemView) {
            super(itemView);

            //ASSIGNING EXAMPLE2 ITEM XML ELEMENTS TO THE
           mTextViewLDescription=itemView.findViewById(R.id.text_view_message);
           mTextViewSender=itemView.findViewById(R.id.text_view_sender);
           mTextViewTimer=itemView.findViewById(R.id.text_view_timer);
           mCardView=itemView.findViewById(R.id.cardview1);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
