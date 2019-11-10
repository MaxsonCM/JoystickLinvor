package com.webnode.maxsoncm.joysticklinvor;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.UUID;


public class Joystick extends ActionBarActivity {
    private InputStream inStream = null;
    Handler handler = new Handler();
    byte delimiter = 10;
    int readBufferPosition = 0;
    byte[] readBuffer = new byte[1024];
    boolean stopWorker = false;

    public Button btnFrente, btnTras, btnDis,btnEsquerda,btnDireita, btnFrenquencia;
    SeekBar velocidade;
    TextView lblVelocidade, saida;
    String address = null;
    private ProgressDialog progress;

    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;

    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onDestroy()
    {

        super.onDestroy();
        Disconnect();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(ListaDispositivo.EXTRA_ADDRESS); //recebe o endereço do dipositivo bluetooth

        //view do joystick
        setContentView(R.layout.activity_joystick);
        //chama os widgtes
        btnFrente = (Button)findViewById(R.id.button2);
        btnTras = (Button)findViewById(R.id.button3);
        btnEsquerda = (Button)findViewById(R.id.button5);
        btnDireita = (Button)findViewById(R.id.button6);
        btnDis = (Button)findViewById(R.id.button4);
        velocidade = (SeekBar)findViewById(R.id.seekBar);
        lblVelocidade = (TextView)findViewById(R.id.lumn);
        saida = (TextView)findViewById(R.id.txtSaida);
        btnFrenquencia = (Button)findViewById(R.id.button7);
        new ConnectBT().execute(); //chama a classe para conectar




        btnFrenquencia.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) { // Quando o bt é pressionado
                    frequencia() ;
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {   // Quando o bt é "solto"
                    //stop();
                }
                receber();
                return false;                                     // Retorna false o onTouch
            }
        });

        btnFrente.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) { // Quando o bt é pressionado
                    frente();
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {   // Quando o bt é "solto"
                    stop();
                }
                receber();
                return false;                                     // Retorna false o onTouch
            }
        });


        btnTras.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) { // Quando o bt é pressionado
                    tras();
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {   // Quando o bt é "solto"
                    stop();
                }
                receber();
                return false;                                     // Retorna false o onTouch
            }
        });
        btnEsquerda.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) { // Quando o bt é pressionado
                    esquerda();
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {   // Quando o bt é "solto"
                    stop();
                }
                receber();
                return false;                                     // Retorna false o onTouch
            }
        });
        btnDireita.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) { // Quando o bt é pressionado
                    direita();
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {   // Quando o bt é "solto"
                    stop();
                }
                receber();
                return false;                                     // Retorna false o onTouch
            }
        });
        btnDis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Disconnect(); //close connection
            }
        });

        velocidade.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser == true) {
                    lblVelocidade.setText( String.valueOf(progress) );
                    velocidade();
                    receber();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void Disconnect()
    {
        stopWorker = true;
        if (btSocket!=null) //se btSocket estiver ocupado
        {
            try
            {
                btSocket.close(); //fecha conecxão
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //retorna para o primeiro layout

    }

    private void tras()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("Tras:".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void stop()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("Parar:".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void frente()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("Frente:".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void esquerda()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("Esquerda:".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void direita()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("Direita:".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void frequencia()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("Freq:".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    private void velocidade()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write( ("V" + lblVelocidade.getText() + ":").toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }
    //Descontinuado
    private void receber()
    {/*
        if (btSocket!=null) {
            try {

                String s ="";
                while (btSocket.getInputStream().available() != 0) {
                    byte[] readBuffer = new byte[20];
                    InputStream mmInStream = btSocket.getInputStream();
                    int read = mmInStream.read(readBuffer);

                    if(read != -1) { //while (read != -1) {
                        s += new String(readBuffer,0,read);
                    }
                }
                saida.setText(s);
            } catch (IOException e) {
                msg("Erro ao receber");
            }
        }
*/
    }

    // fast way to call Toast
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_joystick , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(Joystick.this, "Conectando...", "Por favor espere!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                    beginListenForData();
                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess)
            {
                msg("Falha ao tentar conectar !");
                finish();
            }
            else
            {
                msg("Conectado !");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    public void beginListenForData()   {

        try {
            inStream = btSocket.getInputStream();
        } catch (IOException e) {
            stopWorker = true;
        }

        Thread workerThread = new Thread(new Runnable()
        {
            public void run()
            {

                while(!Thread.currentThread().isInterrupted() && !stopWorker)
                {
                    try
                    {
                        int bytesAvailable = inStream.available();
                        if(bytesAvailable > 0)
                        {
                            byte[] packetBytes = new byte[bytesAvailable];
                            inStream.read(packetBytes);
                            for(int i=0;i<bytesAvailable;i++)
                            {
                                byte b = packetBytes[i];
                                if(b == delimiter)
                                {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    handler.post(new Runnable()
                                    {
                                        public void run()
                                        {
                                            saida.setText(data);
                                        }
                                    });
                                }
                                else
                                {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

}

