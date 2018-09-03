package io.chirp.connectdemoapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Random;

import io.chirp.connect.ChirpConnect;
import io.chirp.connect.interfaces.ConnectEventListener;
import io.chirp.connect.interfaces.ConnectSetConfigListener;
import io.chirp.connect.models.ChirpError;
import io.chirp.connect.models.ConnectState;


public class MainActivity extends AppCompatActivity {

    private ChirpConnect chirpConnect;

    private static final int RESULT_REQUEST_RECORD_AUDIO = 1;

    TextView status;
    TextView lastChirp;
    TextView versionView;

    Button startStopSdkBtn;
    Button startStopSendingBtn;

    Boolean startStopSdkBtnPressed = false;

    String APP_KEY = "dB9bC3d98F434bCbd82DDfDbf";
    String APP_SECRET = "2Ba3ED5bbE5C0Ec0e3d2238b5a5DDafa293eEEEd30CebBacd9";
    String APP_CONFIG = "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";

    String TAG = "ConnectDemoApp";

    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentLayout = findViewById(android.R.id.content);

        status = findViewById(R.id.stateValue);
        lastChirp = findViewById(R.id.lastChirp);
        versionView = findViewById(R.id.versionView);
        startStopSdkBtn = findViewById(R.id.startStopSdkBtn);
        startStopSendingBtn = findViewById(R.id.startStopSengingBtn);

        startStopSendingBtn.setAlpha(.4f);
        startStopSendingBtn.setClickable(false);
        startStopSdkBtn.setAlpha(.4f);
        startStopSdkBtn.setClickable(false);

        if (APP_KEY.equals("") || APP_SECRET.equals("")) {
            Log.e(TAG, "APP_KEY or APP_SECRET is not set. " +
                    "Please update with your APP_KEY/APP_SECRET from admin.chirp.io");
            return;
        }

        Log.v("Connect Version: ", ChirpConnect.getVersion());
        versionView.setText(ChirpConnect.getVersion());

        /**
         * Key and secret initialisation
         */
        chirpConnect = new ChirpConnect(this, APP_KEY, APP_SECRET);
        chirpConnect.setConfig(APP_CONFIG, new ConnectSetConfigListener() {

            @Override
            public void onSuccess() {

                //Set-up the connect callbacks
                chirpConnect.setListener(connectEventListener);
                //Enable Start/Stop button
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startStopSdkBtn.setAlpha(1f);
                        startStopSdkBtn.setClickable(true);
                    }
                });
            }

            @Override
            public void onError(ChirpError setConfigError) {
                Log.e("setConfigError", setConfigError.getMessage());
            }
        });

    }


    ConnectEventListener connectEventListener = new ConnectEventListener() {

        @Override
        public void onSending(byte[] data, byte channel) {
            /**
             * onSending is called when a send event begins.
             * The data argument contains the payload being sent.
             */
            String hexData = "null";
            if (data != null) {
                hexData = chirpConnect.payloadToHexString(data);
            }
            Log.v("connectdemoapp", "ConnectCallback: onSending: " + hexData + " on channel: " + channel);
            updateLastPayload(hexData);
        }

        @Override
        public void onSent(byte[] data, byte channel) {
            /**
             * onSent is called when a send event has completed.
             * The data argument contains the payload that was sent.
             */
            String hexData = "null";
            if (data != null) {
                hexData = chirpConnect.payloadToHexString(data);
            }
            updateLastPayload(hexData);
            Log.v("connectdemoapp", "ConnectCallback: onSent: " + hexData + " on channel: " + channel);
        }

        @Override
        public void onReceiving(byte channel) {
            /**
             * onReceiving is called when a receive event begins.
             * No data has yet been received.
             */
            Log.v("connectdemoapp", "ConnectCallback: onReceiving on channel: " + channel);
        }

        @Override
        public void onReceived(byte[] data, byte channel) {
            /**
             * onReceived is called when a receive event has completed.
             * If the payload was decoded successfully, it is passed in data.
             * Otherwise, data is null.
             */
            String hexData = "null";
            if (data != null) {
                hexData = chirpConnect.payloadToHexString(data);
            }
            Log.v("connectdemoapp", "ConnectCallback: onReceived: " + hexData + " on channel: " + channel);
            updateLastPayload(hexData);
        }

        @Override
        public void onStateChanged(byte oldState, byte newState) {
            /**
             * onStateChanged is called when the SDK changes state.
             */
            ConnectState state = ConnectState.createConnectState(newState);
            Log.v("connectdemoapp", "ConnectCallback: onStateChanged " + oldState + " -> " + newState);
            if (state == ConnectState.ConnectNotCreated) {
                updateStatus("NotCreated");
            } else if (state == ConnectState.AudioStateStopped) {
                updateStatus("Stopped");
            } else if (state == ConnectState.AudioStatePaused) {
                updateStatus("Paused");
            } else if (state == ConnectState.AudioStateRunning) {
                updateStatus("Running");
            } else if (state == ConnectState.AudioStateSending) {
                updateStatus("Sending");
            } else if (state == ConnectState.AudioStateReceiving) {
                updateStatus("Receiving");
            } else {
                updateStatus(newState + "");
            }

        }

        @Override
        public void onSystemVolumeChanged(int oldVolume, int newVolume) {
            /**
             * onSystemVolumeChanged is called when the system volume is changed.
             */
            Snackbar snackbar = Snackbar.make(parentLayout, "System volume has been changed to: " + newVolume, Snackbar.LENGTH_LONG);
            snackbar.setAction("CLOSE", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                    .show();
            Log.v("connectdemoapp", "System volume has been changed, notify user to increase the volume when sending data");
        }

    };

    @Override
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RESULT_REQUEST_RECORD_AUDIO);
        } else {
            if (startStopSdkBtnPressed) startSdk();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RESULT_REQUEST_RECORD_AUDIO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (startStopSdkBtnPressed) stopSdk();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            chirpConnect.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        stopSdk();
    }

    public void updateStatus(final String newStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                status.setText(newStatus);
            }
        });
    }

    public void updateLastPayload(final String newPayload) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                lastChirp.setText(newPayload);
            }
        });
    }

    public void stopSdk() {
        ChirpError error = chirpConnect.stop();
        if (error.getCode() > 0) {
            Log.e("ConnectError: ", error.getMessage());
            return;
        }
        startStopSendingBtn.setAlpha(.4f);
        startStopSendingBtn.setClickable(false);
        startStopSdkBtn.setText("Start Sdk");
    }

    public void startSdk() {
        ChirpError error = chirpConnect.start();
        if (error.getCode() > 0) {
            Log.e("ConnectError: ", error.getMessage());
            return;
        }
        startStopSendingBtn.setAlpha(1f);
        startStopSendingBtn.setClickable(true);
        startStopSdkBtn.setText("Stop Sdk");
    }

    public void startStopSdk(View view) {
        /**
         * Start or stop the SDK.
         * Audio is only processed when the SDK is running.
         */
        startStopSdkBtnPressed = true;
        if (chirpConnect.getConnectState() == ConnectState.AudioStateStopped) {
            startSdk();
        } else {
            stopSdk();
        }
    }

    public void sendPayload(View view) {
        /**
         * A payload is a byte array dynamic size with a maximum size defined by the config string.
         *
         * Generate a random payload, and send it.
         */
        long maxPayloadLength = chirpConnect.getMaxPayloadLength();
        long size = (long) new Random().nextInt((int) maxPayloadLength) + 1;
        byte[] payload = chirpConnect.randomPayload(size);
        long maxSize = chirpConnect.getMaxPayloadLength();
        if (maxSize < payload.length) {
            Log.e("ConnectError: ", "Invalid Payload");
            return;
        }
        ChirpError error = chirpConnect.send(payload);
        if (error.getCode() > 0) {
            Log.e("ConnectError: ", error.getMessage());
        }
    }

}
