package rahulkumardas.otpreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rahul Kumar Das on 24-03-2017.
 */

public class OtpReader extends BroadcastReceiver {
    private static final String TAG = "RAHUL";
    private final String OTP_REG = "[0-9]{1,6}";

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    String senderAddress = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);
                    Toast.makeText(context, "OTP is : " + getOTP(message), Toast.LENGTH_SHORT).show();

                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private String getOTP(String message) {
        Pattern pattern = Pattern.compile(OTP_REG);
        Matcher matcher = pattern.matcher(message);
        String otp = "Not Found!";
        while (matcher.find()) {
            otp = matcher.group();
        }
        return otp;
    }
}
