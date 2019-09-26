package com.example.app_vol3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;

    TextView statusBluetoothTv, pairedTv;
    Button onBtn, offBtn, discoverableBtn, pairedBtn, logoutBtn;

    BluetoothAdapter mBlueAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusBluetoothTv = findViewById(R.id.statusBluetoothTv);
        pairedTv = findViewById(R.id.pairedTv);
        onBtn = findViewById(R.id.onBtn);
        offBtn = findViewById(R.id.offBtn);
        discoverableBtn =  findViewById(R.id.discoverableBtn);
        pairedBtn =  findViewById(R.id.pairedBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        mBlueAdapter = BluetoothAdapter.getDefaultAdapter();

        if(mBlueAdapter == null) {
            statusBluetoothTv.setText("Bluetooth is not available");
        }
        else {
            statusBluetoothTv.setText("Bluetooth is available");
        }

        onBtn.setOnClickListener(this);
        offBtn.setOnClickListener(this);
        discoverableBtn.setOnClickListener(this);
        pairedBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.onBtn:
                if(!mBlueAdapter.isEnabled()) {
                    showToast("Turning On Bluetooth...");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, REQUEST_ENABLE_BT);
                } else {
                    showToast("Bluetooth is already on");
                }
                break;
            case R.id.offBtn:
                if(mBlueAdapter.isEnabled()) {
                    mBlueAdapter.disable();
                    showToast("Turning Bluetooth Off");
                } else {
                    showToast("Bluetooth is already off");
                }
                break;
            case R.id.discoverableBtn:
                if(!mBlueAdapter.isDiscovering()) {
                    showToast("Making Your Device Discoverable");
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(intent, REQUEST_DISCOVER_BT);
                }
                break;
            case R.id.pairedBtn:
                if(mBlueAdapter.isEnabled()) {
                    pairedTv.setText("Paired Devices");
                    Set<BluetoothDevice> devices = mBlueAdapter.getBondedDevices();
                    for (BluetoothDevice device: devices){
                        pairedTv.append("\nDevice: " + device.getName() + ", " +device);
                    }
                } else {
                    showToast("Turn on bluetooth to get paired devices");
                }
                break;
            case R.id.logoutBtn:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch(requestCode) {
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK) {
                    showToast("Bluetooth is on");
                } else {
                    showToast("Could't on bluetooth");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
