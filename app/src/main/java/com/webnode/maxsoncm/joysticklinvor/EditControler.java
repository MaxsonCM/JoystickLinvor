package com.webnode.maxsoncm.joysticklinvor;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.Log;
import android.view.DragEvent;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class EditControler extends ActionBarActivity {

    private static final String DEBUG_TAG_DRAG = "DRAG_DROP";

    private Obj_Componente componente = new Obj_Componente();

    boolean oAllow = false;

    Velocimetro ponteiro;//para o velocimetro
    //AttributeSet attrs;//para o velocimetro
    Thread thread;//para o velocimetro
    Handler handler = new Handler();//para o velocimetro
    float angulo = 0;//para o velocimetro
    boolean crescendo = true;//para o velocimetro


    private static final String DEBUG_TAG = "Velocity";//rastrear movimento
    private VelocityTracker mVelocityTracker = null;//rastrear movimento



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_controler);
        //criar variaveis de array para todas as funções dos botões
        final RelativeLayout TelaRelativa = (RelativeLayout) findViewById(R.id.TelaRelativa);

        TelaRelativa.setBackgroundColor(Color.BLACK);

        //ImageView manometro = new Velocimetro(getApplicationContext(),attrs);
        //ImageView manometro = new Velocimetro(getApplicationContext(),R.drawable.velocimetro);
        ImageView manometro = new Velocimetro(getApplicationContext(),R.drawable.velocimetro,300);
        manometro.setId(99);
        TelaRelativa.addView(manometro);

        //manometro.setBackgroundResource(R.drawable.velocimetro);
        manometro.setX(100 - (manometro.getWidth() / 2));
        manometro.setY(100 - (manometro.getHeight() / 2));
        manometro.getLayoutParams().height = 300;
        manometro.getLayoutParams().width = 300;

        ponteiro = (Velocimetro) findViewById(manometro.getId());



        Button direcional =  new Button(this);
        direcional.setId(100);
        TelaRelativa.addView(direcional);
        direcional.setText("Direcianal");
        direcional.setHeight(100);
        direcional.setWidth(100);
        direcional.setBackgroundResource(R.drawable.botao_centro_selector);
        direcional.setX(300 - (direcional.getWidth() / 2));
        direcional.setY(300 - (direcional.getHeight() / 2));
        direcional.setRotation(90);
        //adiciona componente a tela


        Button seta =  new Button(this);
        seta.setId(101);
        TelaRelativa.addView(seta);
        seta.setText("");
        seta.setTypeface(Typeface.SANS_SERIF);
        seta.setHeight(100);
        seta.setWidth(100);
        seta.setBackgroundResource(R.drawable.botao_cima_selector);
        seta.setX(seta.getX() - (direcional.getWidth() / 2));
        seta.setY(seta.getY() - (direcional.getHeight() / 2));
        seta.setRotation(0);


        findViewById(direcional.getId()).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
            }
        });
        findViewById(direcional.getId()).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(seta.getId()).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(manometro.getId()).setOnLongClickListener(new MyOnLongClickListener());
        findViewById(R.id.TelaRelativa).setOnDragListener(new MyOnDragListener());

        findViewById(direcional.getId()).setOnClickListener(new MyOnClickListener());
        findViewById(seta.getId()).setOnClickListener(new MyOnClickListener());

    }

    public void openDialogFragment(View view){


        componente.setCodigo(view.getId());
        componente.setLayout(0);
        componente.setOrdem(0);
        componente.setRotulo("Nome");
        componente.setStylo("1");
        componente.setTipo("");

        componente.setAltura(view.getHeight());
        componente.setLargura(view.getWidth());
        componente.setPosicaoX(view.getX());
        componente.setPosicaoY(view.getY());
        componente.setRotacao(0);


        componente.setDown("Pega da base");
        componente.setUP("Função down");
        componente.setAnalogico_max(0);
        componente.setAnalogico_min(0);

        //FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //Config_item_fragment cif = new Config_item_fragment(componente);
        //cif.show(ft, "dialog");
    }

    public void turnOffDialogFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Config_item_fragment cif = (Config_item_fragment) getSupportFragmentManager().findFragmentByTag("dialog");
        if(cif != null){
            cif.dismiss();
            ft.remove(cif);
        }
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    class MyOnLongClickListener implements OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("simple_text", "text");
            DragShadowBuilder sb = new View.DragShadowBuilder(findViewById(v.getId()));
            v.startDrag(data, sb, v, 0);
            //v.setVisibility(View.INVISIBLE);
            return(true);
        }
    }

    class MyOnClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            openDialogFragment(v);
        }
    }

    class MyOnDragListener implements OnDragListener {

        TextView texto = (TextView) findViewById(R.id.texto);
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            View view = (View) event.getLocalState();

            switch(action){
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.i(DEBUG_TAG_DRAG, "ACTION_DRAG_STARTED");
                    Toast.makeText(getApplicationContext(),"Mude de posição",Toast.LENGTH_SHORT).show();
                    return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.i(DEBUG_TAG_DRAG, "ACTION_DRAG_ENTERED");
                    //v.setBackgroundColor(Color.YELLOW);
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.i(DEBUG_TAG_DRAG, "ACTION_DRAG_LOCATION");

                    texto.setText("Posição: X " + event.getX() + " Y" + event.getY() + " tela " +v.getHeight());
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i(DEBUG_TAG_DRAG, "ACTION_DRAG_EXITED");

                    //não deixar o componente sair da tela - fazendo
                    if (v.getHeight() - view.getHeight() > view.getY()  ){
                        view.setY(v.getHeight() - view.getHeight());
                    }
                    //v.setBackgroundColor(Color.BLUE);

                    break;
                case DragEvent.ACTION_DROP:
                    Log.i(DEBUG_TAG_DRAG, "ACTION_DROP");

                    view.setX(event.getX() - (view.getWidth() / 2));
                    view.setY(event.getY() - (view.getHeight() / 2));
                    //view.setVisibility(View.VISIBLE);


                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i(DEBUG_TAG_DRAG, "ACTION_DRAG_ENDED");
                    //v.setBackgroundColor(Color.BLUE);
                    break;
            }

            return true;
        }
    }

    @Override//rastreio de movimento
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int action = event.getActionMasked();
        int pointerId = event.getPointerId(index);

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                Log.d("MOVIMENTO","Action was DOWN");
                if(mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the velocity of a motion.
                    mVelocityTracker = VelocityTracker.obtain();
                }
                else {
                    // Reset the velocity tracker back to its initial state.
                    mVelocityTracker.clear();
                }
                // Add a user's movement to the tracker.
                mVelocityTracker.addMovement(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.d("MOVIMENTO","Action was MOVE");
                mVelocityTracker.addMovement(event);
                // When you want to determine the velocity, call
                // computeCurrentVelocity(). Then call getXVelocity()
                // and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1000);
                // Log velocity of pixels per second
                // Best practice to use VelocityTrackerCompat where possible.
                Log.d("MOVIMENTO", "X velocity: " +
                        VelocityTrackerCompat.getXVelocity(mVelocityTracker,
                                pointerId));
                Log.d("MOVIMENTO", "Y velocity: " +
                        VelocityTrackerCompat.getYVelocity(mVelocityTracker,
                                pointerId));
                return true;

            case MotionEvent.ACTION_UP:
                Log.d("MOVIMENTO","Action was UP");
                return true;
            case MotionEvent.ACTION_CANCEL:
                Log.d("MOVIMENTO","Action was CANCEL");
                return true;
                // Return a VelocityTracker object back to be re-used by others.
                //mVelocityTracker.recycle();
            default :
                return super.onTouchEvent(event);
        }

    }

    @Override//para o velocimetro
    protected void onStart()    {
        super.onStart();

        thread = new Thread(rodando);
        thread.start();
    }

    Runnable rodando = new Runnable()
    {
        @Override
        public void run()
        {
            while(true)
            {
                if(crescendo)
                {
                    angulo++;

                    if(angulo == 360)
                    {
                        crescendo = false;
                    }
                }
                else
                {
                    angulo--;

                    if(angulo == 0)
                    {
                        crescendo = true;
                    }
                }

                handler.post(runHandler);

                try
                {
                    Thread.sleep(25);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    };

    Runnable runHandler = new Runnable()
    {
        @Override
        public void run()
        {
           ponteiro.desenhar(angulo);
        }
    };

    @Override//não funcionou?
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }



}
