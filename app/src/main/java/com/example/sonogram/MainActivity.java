package com.example.sonogram;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigInteger;

import io.chirp.connect.ChirpConnect;
import io.chirp.connect.interfaces.ConnectEventListener;
import io.chirp.connect.interfaces.ConnectSetConfigListener;
import io.chirp.connect.models.ChirpError;
import io.chirp.connect.models.ConnectState;

public class MainActivity extends AppCompatActivity {
    String KEY = "A28cBeC94C2f7e8D2eFEc6244";
    String SECRET = "f13f77B53100a95FCA3f61Aa13Cd6ACdfCC8DDccB025659502";
    String CONFIG = "aQSTJbapNLH2P8IUOxcW9u/ZtJql8wHl3sJapI4OaVxc2ozxN2+bN9dZCU0LHoTsKTCobEVLjyzIRjnUVE7JXqpW6yZdQOYTS5NrzrR60JPIHfSfahYeUVTcvH/uaCH1jWK22pmE2G5PYDpkMIR5lGXv6qUG3OhnQ2oLMEry4k+wiS26yvjba3zXEtKgHvggZugNWWt6d+rhfw9XDgv0youCG1aH8s/LZ+/St1eDLiDSOZfiPha74tyZaQ9H0YCJ3/8ZqQuTa5lc2mJ9CsDwLwlxAiU9Zp/56hyYfWLt2/qTD15sbLZh2Rl8w/FRjKBrV8nbQqWVojFc0hgCVn6TBF/je7XtPQWSdsub6+c0Opr6EnBuKBWj6WF3n361fLf2gtjZ5TkFwHIQgPrA9wSpuzWOysrXQUMjwsCx8RFFnzKwHdHY/7BCsCVJKOCOtfaAbm/BIBlJ0CIoy2p7zzjC2XeU4c35ix1Iql1OOi4qV9M7Sj+6TMLwyPGgY15CUIvQ7Dhsg0A/F8Q1WSy50C9vA8ziQBt7ot6d/DV2kQ2dzMs2bK56bkChMj+M1q0OZlMNFLYRSGSLOreaHmocAFTjTABZ2snjPbfnyLfAlz+Dopvnu2aadbdVlCgJloEAKazTNdVt9yVSTaT/RDtgH9Dr/jrl3lA3aku6TK0T5l0dezEItvqL4d8gDc2mlar2Qd/B+e1hiGl1LCidpqfrsfBdUZvkvHfuluBJAOovXqq8gHwMw39gpLUxz2EIRAsVMae3vcpx1id5gY9KU3gwCDY9kQmDKsqOtldYpniXr65qliUz3KeqvgKRd6qgbh4KJCFGOW/S9flIlqCRfBe91nVds4kmiBHneV0g6p2Js2qJZNQnA+WaQIuguAfUTE+cEyCAJWyTzKMBdK7FAl9JZUZXBQV0RV+eYglKWluEGYQ2JQ/lVIuABbVcFKjZgCoRTcywAOQEb2ubAjUpaT3FkDgQKvGpGG5FA1GX4cKXH+rMnp5pEIhHKsPd1OibIBgpQfJtkc7d9al+CwwbDvP8JoPrbtDH3+UdDxt1jeUTpCqXqgg=";
    private static final int RESULT_REQUEST_RECORD_AUDIO = 0;
    boolean listening = false;
    boolean messageSent = false;
    final int messageLength = 4;

    //Textbox
    ChirpConnect chirpConnect = new ChirpConnect(this, KEY, SECRET);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request audio permission if not given by default.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
        }

        //Set buttons
        Button one = findViewById(R.id.one);
        Button zero = findViewById(R.id.zero);
        final EditText bitString = findViewById(R.id.bitstring);

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

        ConnectEventListener connectEventListener = new ConnectEventListener() {

            @Override
            public void onSending(byte[] payload, byte channel) {
            }

            @Override
            public void onSent(byte[] payload, byte channel) {
                Log.v("chirpConnectDemoApp", "This is called when a payload has been sent " + payload  + " on channel: " + channel);
                messageSent = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView t = findViewById(R.id.hello);
                        t.setText("Waiting on acknowledgement");
                    }
                });
            }

            @Override
            public void onReceiving(byte channel) {
                Log.v("chirpConnectDemoApp", "This is called when the SDK is expecting a payload to be received on channel: " + channel);
            }

            @Override
            public void onReceived(byte[] payload, byte channel) {
                Log.v("chirpConnectDemoApp", "This is called when a payload has been received " + payload  + " on channel: " + channel);
                if (messageSent && channel == 2)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView t = findViewById(R.id.hello);
                            t.setText("Done!");
                        }
                    });
            }

            @Override
            public void onStateChanged(byte oldState, byte newState) {
                Log.v("chirpConnectDemoApp", "This is called when the SDK state has changed " + oldState + " -> " + newState);
            }

            @Override
            public void onSystemVolumeChanged(int old, int current) {
                Log.d("chirpConnectDemoApp", "This is called when the Android system volume has changed " + old + " -> " + current);
            }

        };
        chirpConnect.setListener(connectEventListener);
        chirpConnect.setListenToSelf(false);
        chirpConnect.start();
        long maxLength = chirpConnect.getMaxPayloadLength();
        byte[] payload = chirpConnect.randomPayload(2);
        //chirpConnect.send(payload);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitString.isEnabled()) {
                    String temp = bitString.getText() + "1";
                    bitString.setText(temp);

                    if (temp.length() == messageLength) {
                        chirpConnect.send(new BigInteger(temp, 2).toByteArray());
                        bitString.setEnabled(false);
                    }
                }
            }
        });

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitString.isEnabled()) {
                    String temp = bitString.getText() + "0";
                    bitString.setText(temp);

                    if (temp.length() == messageLength) {
                        chirpConnect.send(new BigInteger(temp, 2).toByteArray());
                        bitString.setEnabled(false);
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
        }
        else {
            chirpConnect.start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chirpConnect.start();
                }
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        chirpConnect.stop();
    }
}
