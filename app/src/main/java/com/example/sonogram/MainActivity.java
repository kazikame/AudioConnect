package com.example.sonogram;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
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
    //Chirp Config
    String KEY = "A28cBeC94C2f7e8D2eFEc6244";
    String SECRET = "f13f77B53100a95FCA3f61Aa13Cd6ACdfCC8DDccB025659502";
    String CONFIG = "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";
    private static String ACK = "1";
    private static String NACK = "0";
    private static final int RESULT_REQUEST_RECORD_AUDIO = 0;


    boolean messageSent = false;
    private static int currSequenceNo = -1;
    String correctText;
    ChirpConnect chirpConnect = new ChirpConnect(this, KEY, SECRET);


    HandlerThread timeoutThread = null;
    Looper timeoutLooper;
    Handler timeoutHandler;
    final int timeout = 10000;
    final int maxTimeoutTries = 3;
    boolean isTimeoutRunning = false;

    TextView top;

    Button one, zero, send, listen;
    EditText bitString, errorBitString;

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
        top = findViewById(R.id.hello);

        //Binary string buttons
        one = findViewById(R.id.one);
        zero = findViewById(R.id.zero);

        //Work Buttons
        send = findViewById(R.id.send);
        listen = findViewById(R.id.listen);

        //Text Boxes
        bitString = findViewById(R.id.bitstring);
        errorBitString = findViewById(R.id.errorbits);


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (errorBitString.hasFocus()) {
                    errorBitString.append("1");
                }

                else {
                    bitString.append("1");
                }
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
                    currSequenceNo++;
                    if (currSequenceNo == 2)
                        currSequenceNo = -1;
                    correctText = bitString.getText().toString();
                    chirpConnect.send((correctText + crc_g(bitStringLength, correctText) + currSequenceNo).getBytes());
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

                    currSequenceNo++;
                    if (currSequenceNo == 2)
                        currSequenceNo = -1;

                    chirpConnect.send((toBeSent + crc_g(bitStringLength, correctText) + currSequenceNo).getBytes());
                    Toast.makeText(getApplicationContext(), "Sending...", Toast.LENGTH_SHORT).show();

                }

            }
        });

        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chirpConnect.stop();
               startActivity(new Intent(getApplicationContext(), ReceivingActivity.class));
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
                        send.setEnabled(false);
                        top.setText("Waiting for acknowledgement!");
                    }
                });

                if (!isTimeoutRunning)
                    startTimeout(payload);

            }

            @Override
            public void onReceiving(byte channel) {
                if (messageSent){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Receiving ack...", Toast.LENGTH_SHORT).show();
                        }
                    });}
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Receiving Garbage :(", Toast.LENGTH_SHORT).show();
                        }
                    });}
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


                if (messageSent){
                    String recdBitString = "";
                    try {
                        recdBitString = new String(payload);
                    } catch (Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Received NULL :(" ,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    if (recdBitString.equals(ACK + currSequenceNo))
                    {
                        isTimeoutRunning = false;
                        timeoutThread.quit();
                        messageSent = false;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                send.setEnabled(true);
                                Toast.makeText(getApplicationContext(), "Sent Successfully!", Toast.LENGTH_SHORT).show();
                                top.setText("Successful!");
                            }
                        });

                    }

                    else if (recdBitString.equals(NACK + currSequenceNo))
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Received NACK, Retrying!" + (new String(payload)) ,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        isTimeoutRunning = false;
                        timeoutThread.quit();
                        chirpConnect.send((correctText + crc_g(correctText.length(), correctText) + currSequenceNo).getBytes());
                    }

                    else
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Received: " + (new String(payload)) ,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
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

    private void startTimeout(final byte[] message) {
        if (timeoutThread != null)
            timeoutThread.quit();
        timeoutThread = new HandlerThread("Timeout Thread");
        timeoutThread.start();

        timeoutLooper = timeoutThread.getLooper();
        timeoutHandler = new Handler(timeoutLooper);
        timeoutHandler.post(new Runnable() {
            @Override
            public void run() {
                isTimeoutRunning = true;
                for (int i = 0; i<maxTimeoutTries; i++) {
                    try {
                        Thread.sleep(timeout);
                        if (!isTimeoutRunning)
                            return;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Timeout: Retrying...",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                        chirpConnect.send(message);
                    } catch (InterruptedException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Timeout thread caught error, retry sending!",
                                        Toast.LENGTH_SHORT).show();
                                send.setEnabled(true);
                            }
                        });
                        isTimeoutRunning = false;
                        break;
                    }
                }

                try {
                    Thread.sleep(timeout);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Timeout try limit exceeded, try again!",
                                    Toast.LENGTH_SHORT).show();
                            send.setEnabled(true);
                        }
                    });
                } catch (Exception e) {}

                isTimeoutRunning = false;
            }
        });
    }
}