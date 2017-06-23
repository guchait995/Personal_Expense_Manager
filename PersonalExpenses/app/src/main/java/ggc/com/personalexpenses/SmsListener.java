package ggc.com.personalexpenses;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsListener extends BroadcastReceiver {

    private SharedPreferences preferences;

    final SmsManager sms=SmsManager.getDefault();
    DataBaseHandler dataBaseHandler;
    String msg_from;
    String msgBody;
    Double mainprice;
    String item;
    @Override
    public void onReceive(Context context, Intent intent) {
        dataBaseHandler=new DataBaseHandler(context,1);
        // TODO Auto-generated method stub
        Log.i("Broadcast", "onReceive: sms recieved ");
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;

            Log.i("Broadcast", "onReceive: sms recieved ");
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                    }
                }catch(Exception e){
                    Log.i("Broadcast", "onReceive: sms recieved "+e.getMessage());
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        }

        savetodatabase();
        createnotification(context,mainprice,item);


    }
    //

    public void savetodatabase()
    {
        Log.i("VALUES", "savetodatabase: called" +msg_from);
           String Words[]=null;
            int i=0;
            int flag=0;
            int flag2=0;
            int flag3=0;
            for(i=0;i<=msgBody.length();i++)
            {
                Words=msgBody.split(" ");

            }
            for(i=0;i<Words.length;i++)
            {
                Log.i("VALUES", "savetodatabase: "+Words[i]);
                if(Words[i].equalsIgnoreCase("worth"))
                {
                    Log.i("VALUES_found worth", "savetodatabase: "+Words[i]);
                    flag=i;
                }
                if(Words[i].equalsIgnoreCase("SBI")||Words[i].equalsIgnoreCase("Debit")
                        ||Words[i].equalsIgnoreCase("card")||Words[i].equalsIgnoreCase("for")
                        ||Words[i].equalsIgnoreCase("a")||Words[i].equalsIgnoreCase("purchase"))
                {
                    flag3=flag3+1;
                }
                if(Words[i].equalsIgnoreCase("at"))
                {
                    flag2=i;
                }
            }

            item=Words[flag2+1];
            String price=Words[flag+1];
            mainprice=Double.parseDouble(price.substring(2));
        Log.i("VALUES", "savetodatabase: " + item + "," + mainprice+","+flag3);
        if(flag3==7) {
            dataBaseHandler.readfromsms(item, mainprice);
            Log.i("VALUES", "savetodatabase: " + item + "," + mainprice+","+flag3);
        }

    }

    public void createnotification(Context context, Double mainprice, String item)
    {
        Resources res=context.getResources();
        Bitmap bmp= BitmapFactory.decodeResource(res,R.mipmap.ic_launcher);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Purchase of Rs."+mainprice)
                .setLargeIcon(bmp)
                .setContentText("Item : "+item)
                .setPriority(Notification.PRIORITY_MAX);

        Intent intent =new Intent(context,MainActivity.class);
        PendingIntent resultpendingintent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultpendingintent);
        int mNotificationid=156790898;
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationid,mBuilder.build());


    }





}
