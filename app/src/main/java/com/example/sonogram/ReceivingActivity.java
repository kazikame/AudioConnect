package com.example.sonogram;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import io.chirp.connect.ChirpConnect;
import io.chirp.connect.interfaces.ConnectEventListener;
import io.chirp.connect.interfaces.ConnectSetConfigListener;
import io.chirp.connect.models.ChirpError;


public class ReceivingActivity extends AppCompatActivity {
    String KEY = "A28cBeC94C2f7e8D2eFEc6244";
    String SECRET = "f13f77B53100a95FCA3f61Aa13Cd6ACdfCC8DDccB025659502";
    String CONFIG = "dDp/pK127YGHxWaOydb07S22h2s0Pg7gbqlWWztib/1xM0zX4FOkaHpdzHF+u89XrQ52HecEyXdoj8w96gbJNCTSX0Qyg0LHoM/s0SuLQmr+TF4YXueih3YSV0gZgblP/aQCbtb1vMSkm1ljvU6issYfwrmm8JAg5/W6ZM6wbqdvYMzaRykpEKeoP5c0PEuAhRC0Zd8R0f3TEFIOj6qkF2HCHx9fVSNBrfP+ws/pvDAOIhdr9LK6NS5nv3iwsP/V5csAdIATY5Hx19uJjdmjx/jYGeThv4/+l37kJnl4MJbg9nibBsGeOE4C3mba9L0ytE/jjg2vxy8Hy4KYeUB+6HcbFpZn1tKWyiX+8r3xJuRV0KYP4eZXZvTZDu+oXrGrOaUnMsuxdo1DN1qwtge+SlO/BeDOLo2Nc5mCecDYCt4KYR528c+7fcUQGMDCKZseWqYN9xrzRvAr+N2QLku/Tc8wiBRdcDfZYbLW1fpwvdKeQ4Haac+BiU8HmYRkaOSwi+uE1gtrf3r3AjSprkQ6XEGubbh4Bn8uxRg1bKSKmIC1KgnbNqH+10KlQ/iuR2twzpTLHS/oO6fnQsNQukg9V2lKy/mXzMFtdhU+VQq/Fl3EevebIXxOy9ZO6ln/y+zb7HKZgwvFdyxcj17VDqdmkCTKgQNv+gwfO7jPh/Bi8choPrKdPuQdxrsttR+482OqjeL3UWcWccgUv++me2ZmV7pX6eb+X1aSFoJxBuQdlZoO0J4e0zeEStggUfA5Vq6ru4Vng+LABsp61sNEaprcmuU/vWpNWpSHq+P1qcnducvZPFfmvXyrT2hInbo+hqTOZvlfVFhRXOxm7dm8UyCKKvchuuzUEGQPodbyydZa5+UCR+XSe2OslvcPALxKwNJMorgcwnvL+/DmrYbhNSsc0M6G9QhIloUAtvkHq6pbddv61UEMEtRP+FemKsqATQvrZy6f+wVGG4sfB426j5wTB2R4/m0UGxNkC3UjwOVNMClhUl1UeOpr9d8EV41PmgX7XdFyf0DqpktBK0AZeNttChJEkQdtu/Tik/Ocu92PKgMOYeJBRfhswlkbahx6uKWp8WPJgaQavyA/3rrI48A4yhfvCJHMe8lUWbh3i4NcoSsCPKDNaU718heOYr08OSP/LDUC3KcdIknqGSV0OTPuFML/5xZVfHus3wAktFWZdKUzrnpqUyMl0bbav9fpXxJZemfRSTr0G9pEu/ofmIWwaJPfR09QyK+ZBFo/ffaApGbCbbNgQ4pjOf2NrglAJbqGpGSz5f3ACsn7vKXYvYAOM+1eodJb2mY2a+tk7qDjTN/gDAGgZ4O9oXE3o4ELf5qlqbos56WtU1xsJEzWkyiX8FYdBkvWX/00JL0aY+X89jTkDKP/IhGw+5BobkIO2VshXi9md3MUC4Hym3SB4TWSNAbqBgMY7tMBOfKEaVZTBf3gIrYP4dzOFFW5yXQ0q+ENCeiLn9mloCLOp+xfIpqZbe4vqGdZ3nmSO6jvQ/Iv8o7d5F1YGPNURcI2XPhouHa/32qr9mKpqLSnZk89Xq5HKmpzLDDR5X9YkSe1hesN3CtOochPEzZuEfW2wDP4ujcpm4u0eW3UFkOBPpekKZowJSjXgvw+qG9KvPe/K8iMcrwHNXPKB5SvcAyx8iIkOLw4aDhEvCcBf3ekjdLvefEa7XuvGCfWRiSZEXQZuR7HRfEsffjqInrfn5Yi9DpLbzsN6IyPV1Kj1qu537bK44+VoC56cgDGgIMf8ysL5/gOeM2pqWQ3uQ6iWjUMH4S659BCa9cBmryWhep8gtmUn6/B9wIWiqByPVV4jKPpzwSqAbY0NYYJ/AbtDlEWtlsSOeCZgoRO8kM3eNfW4u83rz+BKqY76cJ9SBfV6oc0x15vyd/6Pc1/pU0tK0z4KGpv8cqnF4mr6E8jLXkLDFakex5yGAJlJnihDR9r02Ygl8vAJN8hJjZwtTk/a+Q/Zeu4KW/ZXhlHb4DVDNww0WFdvtFmHdQQZ+ziTen7l66HckK+uFx2cx6dqilwkIKGeS0cvvCeRryHCOoz2A4qEd3H2j61oOHdb+iEtzxVVZ3F0IWG+3aHCdBPvvOhRwd9WqUK7VxxK0AVRxVUylHt4wyLElnmuB6k1h0NByCzI1n6funSXXLwqJcKvl/2bINOFWA/WoNPOuB+aKEK/1tMfgEpgAIfhPCf9DavNmf6CIT2YAI8futqWM/AXdE7wbAnhEqeyflfuQoxxsgWMTw1e06Y31Q/5ozVx8dTWFlG/eeb0/A+73mPJu97VLO6diexlqMgKZIQ/0YARRccVvHq5r1BhGeM5++aUhGjUdRqHvPXxl1lgeQ0kqTFbxBLRU6O6hPTPR9rNFj4BQmTJG3t4qkO7ARWRXXzqVAQFu6L6DI2hkdqC63vOi6XMYUXpGqp7y0TKE9z6HpFQchhYVUe36hEAiwr+umsN63TJyThqcR+elbFSJgry+MTl0K2NErYADjFdyJUMobMQkqbDLYb+vOyxOY4U/wqELCwRr833WmqedB6OEYMIcP/9hfj0HBPSjMNzJ8DpQzfV34nQjekbzwx87ugEaX/826qHAxkYFhhIXSPQKo=";
    ChirpConnect chirpConnect = new ChirpConnect(this, KEY, SECRET);
    String bitString;
    int messageNo = 0;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        messageNo = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving);
        final TextView top = findViewById(R.id.top);
        final TextView[] messageBoxes = new TextView[2];
        messageBoxes[0] = findViewById(R.id.message1);
        messageBoxes[1] = findViewById(R.id.message2);
        final ProgressBar pbar = findViewById(R.id.progressBar);
        messageBoxes[0].setText("Message 1:", TextView.BufferType.SPANNABLE);
        messageBoxes[1].setText("Message 2:", TextView.BufferType.SPANNABLE);

        ConnectEventListener connectEventListener = new ConnectEventListener() {
            @Override
            public void onSent(byte[] bytes, byte b) {
                Log.v ("hello Wordl:", "number of channels:" + (int)chirpConnect.getChannelCount());
            }

            @Override
            public void onSending(byte[] bytes, byte b) {

            }

            @Override
            public void onReceived(byte[] payload, byte channel) {
                bitString = new String(payload);
                if (CRCToolkit.error_d(bitString.length(), bitString)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SpannableStringBuilder builder = new SpannableStringBuilder();
                            SpannableString str1 = (SpannableString) messageBoxes[messageNo].getText();
                            SpannableString str2 = new SpannableString("\n" + CRCToolkit.strip_crc(bitString));
                            str2.setSpan(new ForegroundColorSpan(Color.WHITE), 0, str2.length(), 0);

                            builder.append(str1);
                            builder.append(str2);
                            messageBoxes[messageNo].setText(builder, TextView.BufferType.SPANNABLE);

                            if (messageNo == 1)
                            {
                                setBackgroundGreen();
                                pbar.setIndeterminate(false);
                                pbar.setProgress(pbar.getMax());
                                top.setText("Received Successfully!");

                            }

                            else
                            {
                                Toast.makeText(getApplicationContext(), "Message 1 Received!", Toast.LENGTH_SHORT).show();
                            }
                            messageNo = (messageNo + 1) % 2;
                        }
                    });
                    chirpConnect.send("1".getBytes());

                }

                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SpannableStringBuilder builder = new SpannableStringBuilder();
                            SpannableString str2 = new SpannableString("\n" + CRCToolkit.strip_crc(bitString));
                            str2.setSpan(new ForegroundColorSpan(Color.RED), 0, str2.length(), 0);
                            builder.append(("Message " + (messageNo + 1) + ":") );
                            builder.append(str2);
                            messageBoxes[messageNo].setText(builder, TextView.BufferType.SPANNABLE);
                            Toast.makeText(getApplicationContext(), "Message received has errors!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    chirpConnect.send("0".getBytes());
                }

            }

            @Override
            public void onReceiving(byte b) {

            }

            @Override
            public void onStateChanged(byte b, byte b1) {

            }

            @Override
            public void onSystemVolumeChanged(int i, int i1) {

            }
        };

        chirpConnect.setConfig(CONFIG, new ConnectSetConfigListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(ChirpError chirpError) {

            }
        });

        chirpConnect.setListener(connectEventListener);

        chirpConnect.start();



    }

    @Override
    protected void onPause() {
        super.onPause();
        chirpConnect.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        chirpConnect.setTransmissionChannel((byte) 2);

        chirpConnect.start();
    }

    public void setBackgroundGreen() {
        ll = findViewById(R.id.ll);
        int colorFrom = ((ColorDrawable)ll.getBackground()).getColor();
        int colorTo = Color.GREEN;
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                ll.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }
}
