package com.example.sonogram;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import io.chirp.connect.ChirpConnect;
import io.chirp.connect.interfaces.ConnectEventListener;
import io.chirp.connect.interfaces.ConnectSetConfigListener;
import io.chirp.connect.models.ChirpError;
import io.chirp.connect.models.ConnectState;

import static com.example.sonogram.CRCToolkit.*;

public class MainActivity extends AppCompatActivity {
    String KEY = "A28cBeC94C2f7e8D2eFEc6244";
    String SECRET = "f13f77B53100a95FCA3f61Aa13Cd6ACdfCC8DDccB025659502";
//    String CONFIG = "aQSTJbapNLH2P8IUOxcW9u/ZtJql8wHl3sJapI4OaVxc2ozxN2+bN9dZCU0LHoTsKTCobEVLjyzIRj" +
////            "nUVE7JXqpW6yZdQOYTS5NrzrR60JPIHfSfahYeUVTcvH/uaCH1jWK22pmE2G5PYDpkMIR5lGXv6qUG3OhnQ2oL" +
////            "MEry4k+wiS26yvjba3zXEtKgHvggZugNWWt6d+rhfw9XDgv0youCG1aH8s/LZ+/St1eDLiDSOZfiPha74tyZaQ9" +
////            "H0YCJ3/8ZqQuTa5lc2mJ9CsDwLwlxAiU9Zp/56hyYfWLt2/qTD15sbLZh2Rl8w/FRjKBrV8nbQqWVojFc0hgCVn" +
////            "6TBF/je7XtPQWSdsub6+c0Opr6EnBuKBWj6WF3n361fLf2gtjZ5TkFwHIQgPrA9wSpuzWOysrXQUMjwsCx8RFFn" +
////            "zKwHdHY/7BCsCVJKOCOtfaAbm/BIBlJ0CIoy2p7zzjC2XeU4c35ix1Iql1OOi4qV9M7Sj+6TMLwyPGgY15CUIvQ" +
////            "7Dhsg0A/F8Q1WSy50C9vA8ziQBt7ot6d/DV2kQ2dzMs2bK56bkChMj+M1q0OZlMNFLYRSGSLOreaHmocAFTjTAB" +
////            "Z2snjPbfnyLfAlz+Dopvnu2aadbdVlCgJloEAKazTNdVt9yVSTaT/RDtgH9Dr/jrl3lA3aku6TK0T5l0dezEItv" +
////            "qL4d8gDc2mlar2Qd/B+e1hiGl1LCidpqfrsfBdUZvkvHfuluBJAOovXqq8gHwMw39gpLUxz2EIRAsVMae3vcpx1" +
////            "id5gY9KU3gwCDY9kQmDKsqOtldYpniXr65qliUz3KeqvgKRd6qgbh4KJCFGOW/S9flIlqCRfBe91nVds4kmiBHn" +
////            "eV0g6p2Js2qJZNQnA+WaQIuguAfUTE+cEyCAJWyTzKMBdK7FAl9JZUZXBQV0RV+eYglKWluEGYQ2JQ/lVIuABbV" +
////            "cFKjZgCoRTcywAOQEb2ubAjUpaT3FkDgQKvGpGG5FA1GX4cKXH+rMnp5pEIhHKsPd1OibIBgpQfJtkc7d9al+Cw" +
////            "wbDvP8JoPrbtDH3+UdDxt1jeUTpCqXqgg=";

    String CONFIG = "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";
    private static final int RESULT_REQUEST_RECORD_AUDIO = 0;
    boolean listening = false;
    boolean messageSent = false;
    boolean listeningack= false;
    final int BIT_STRING = 1;
    final int ERROR_STRING = 2;
    String correctText;
    ChirpConnect chirpConnect = new ChirpConnect(this, KEY, SECRET);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request audio permission if not given by default.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECORD_AUDIO},
                    RESULT_REQUEST_RECORD_AUDIO);
        }

        //Top text view
        final TextView top = findViewById(R.id.hello);
//        final TextView wrong1 = findViewById(R.id.wrong1);
//        final TextView correct1 = findViewById(R.id.correct1);
//        final TextView wrong2 = findViewById(R.id.wrong2);
//        final TextView correct2 = findViewById(R.id.correct2);

        //Binary string buttons
        final Button one = findViewById(R.id.one);
        final Button zero = findViewById(R.id.zero);

        //Work Buttons
        final Button send = findViewById(R.id.send);
        final Button listen = findViewById(R.id.listen);

        //Text fields
//        final EditText bitString1 = findViewById(R.id.bitstring1);
//        final EditText errorbit11 = findViewById(R.id.errorbit11);
//        final EditText errorbit12 = findViewById(R.id.errorbit12);
//        final EditText bitString2 = findViewById(R.id.bitstring2);
//        final EditText errorbit21 = findViewById(R.id.errorbit21);
//        final EditText errorbit22 = findViewById(R.id.errorbit22);

        final EditText bitString = findViewById(R.id.bitstring);
        final EditText errorBitString = findViewById(R.id.errorbits);


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (errorBitString.hasFocus()) {
                    errorBitString.append("1");
                }

                else {
                    bitString.append("1");
                }
//                if (bitString.isEnabled()) {
//                    String temp = bitString.getText() + "1";
//                    bitString.setText(temp);
//
//                    if (temp.length() == messageLength) {
//                        chirpConnect.send(new BigInteger(temp, 2).toByteArray());
//                        bitString.setEnabled(false);
//                    }
//                }
            }
        });

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (errorBitString.hasFocus()) {
                    errorBitString.append("0");
                }

                else {
                    bitString.append("0");
                }
//                if (bitString.isEnabled()) {
//                    String temp = bitString.getText() + "0";
//                    bitString.setText(temp);
//
//                    if (temp.length() == messageLength) {
//                        chirpConnect.send(new BigInteger(temp, 2).toByteArray());
//                        bitString.setEnabled(false);
//                    }
//                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int bitStringLength = bitString.length();
                int errorStringLength = errorBitString.length();
                if (bitStringLength == 0)
                {
                    bitString.requestFocus();
                    Toast.makeText(getApplicationContext(),
                            "Enter a bit string!",
                            Toast.LENGTH_SHORT).show();
                }

                else if (errorStringLength == 0) {
                    correctText = bitString.getText().toString();
                    chirpConnect.send((correctText + crc_g(bitStringLength, correctText)).getBytes());
                }

                else if (bitStringLength != errorStringLength) {
                    Toast.makeText(getApplicationContext(),
                            "String length's don't match!",
                            Toast.LENGTH_SHORT).show();
                    if (bitStringLength > errorStringLength) {
                        errorBitString.requestFocus();
                    }
                    else
                        bitString.requestFocus();
                }

                else {
                    // SENDING THE ERROR BITS!!!
                    String toBeSent = errorBitString.getText().toString();
                    correctText = bitString.getText().toString();
                    chirpConnect.send((toBeSent + crc_g(bitStringLength, correctText)).getBytes());
                    Toast.makeText(getApplicationContext(), "Sending...", Toast.LENGTH_SHORT).show();
                }

//                if (bitString2Length == 0)
//                {
//                    bitString2.requestFocus();
//                    Toast.makeText(getApplicationContext(),
//                            "Enter a bit string!",
//                            Toast.LENGTH_SHORT).show();
//                }

//                else
//                {
//                    String bitstring = bitString.getText().toString();
//                    StringBuilder errorstring = new StringBuilder();
//                    for(int i = 0; i < bitStringLength; i++) {
//                        if(i == Integer.parseInt(errorbit11.getText().toString())
//                                || i == Integer.parseInt(errorbit12.getText().toString())) {
//                            if(bitstring.charAt(i)=='0'){
//                                errorstring.append("1");
//                            }
//                            else {
//                                errorstring.append("0");
//                            }
//                        }
//                        else {
//                            errorstring.append(bitstring.charAt(i));
//                        }
//                    }
//                    StringBuilder stringBuilder = new StringBuilder(bitString1Length + 5);
//                    stringBuilder.append(errorstring+crc_g(bitString1Length,bitstring,6,"101100"));
//                    chirpConnect.send(stringBuilder.toString().getBytes());
//                    listeningack=true;
//                }
            }
        });

        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chirpConnect.stop();
               startActivity(new Intent(getApplicationContext(), ReceivingActivity.class));
//                if(!listeningack){
//                    top.setText("Listening...");
//                    listening = true;
//                }
            }
        });

        //Setup chirp
        ConnectEventListener connectEventListener = new ConnectEventListener() {

            @Override
            public void onSending(byte[] payload, byte channel) {

            }

            @Override
            public void onSent(byte[] payload, byte channel) {
                Log.v("chirpConnectDemoApp",
                        "This is called when a payload has been sent "
                                + payload
                                + " on channel: "
                                + channel);
                messageSent = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        top.setText("Waiting for acknowledgement!");
                    }
                });
            }

            @Override
            public void onReceiving(byte channel) {
                //if (listening)
                if (messageSent && (int)channel == 2)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Receiving ack...", Toast.LENGTH_SHORT).show();
                        }
                    });
                else if ((int) channel == 0)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Receiving on channel", Toast.LENGTH_SHORT).show();
                        }
                    });
                //}
                Log.v("chirpConnectDemoApp", "This is called when the SDK is expecting a payload to be received on channel: " + channel);
            }

            @Override
            public void onReceived(final byte[] payload, byte channel) {
                Log.v("chirpConnectDemoApp",
                        "This is called when a payload has been received "
                                + payload
                                + " on channel: "
                                + channel);

//                if (listening)
//                {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String rec_text = new String(payload);
//                            int rec_text_length=rec_text.length();
//                            String orig_text=rec_text.substring(0,rec_text_length-5);
//                            String orig_text_crc=rec_text;
//
//                            if (error_d(rec_text_length, orig_text_crc, 6, "101100") == 1) {
//                                correct1.setText("The correct message 1 is: " + orig_text);
//                                chirpConnect.send("11".getBytes());
//                            }
//                            else{
//                                wrong1.setText("The wrong message 1 is: "+orig_text);
//                                chirpConnect.send("10".getBytes());
//                            }
//
//
//                        }
//                    });
//                }

                if (messageSent){
                    if (channel == 2) {
                        if (payload.toString().equals("1"))
                        {
                            messageSent = false;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Sent Successfully!", Toast.LENGTH_SHORT).show();
                                    top.setText("Successful!");
                                }
                            });

                        }

                        else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Unsuccessful, Retrying!", Toast.LENGTH_SHORT).show();
                                }
                            });

                            chirpConnect.send((correctText + crc_g(correctText.length(), correctText)).getBytes());
                        }
                    }

                }
            }

            @Override
            public void onStateChanged(byte oldState, byte newState) {
                Log.v("chirpConnectDemoApp", "This is called when the SDK state has changed " + oldState + " -> " + newState);
            }

            @Override
            public void onSystemVolumeChanged(int old, int current) {
                Log.d("chirpConnectDemoApp",
                        "This is called when the Android system volume has changed "
                                + old
                                + " -> " + current);

            }

        };
        chirpConnect.setConfig(CONFIG, new ConnectSetConfigListener() {

            @Override
            public void onSuccess() {
                Log.i("setConfig", "Config successfully set.");
            }

            @Override
            public void onError(ChirpError setConfigError) {
                Log.e("setConfig", setConfigError.getMessage());
            }
        });
        chirpConnect.setListener(connectEventListener);
        chirpConnect.start();


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECORD_AUDIO},
                    RESULT_REQUEST_RECORD_AUDIO);
        }
        else {
            chirpConnect.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case RESULT_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chirpConnect.start();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chirpConnect.stop();
    }


}