package com.webnode.maxsoncm.joysticklinvor;

/**
 * Created by Maxson on 27/04/2016.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Velocimetro extends ImageView
{
    Path ponteiro;
    Paint paint;

    boolean primeiraVez;
    int escala;
    float angulo, tamanho;

    public Velocimetro(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        paint = new Paint();
        ponteiro = new Path();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        primeiraVez = true;

        angulo = 0;
    }

    public Velocimetro(Context context,int drawable)
    {
        super(context);

        this.setBackgroundResource(drawable);
        paint = new Paint();
        ponteiro = new Path();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        primeiraVez = true;

        angulo = 0;
    }

    public Velocimetro(Context context,int drawable,float tamanho)
    {
        super(context);
        this.tamanho = tamanho;
        if( tamanho <= 100 ){
            escala = 2;
        }else if( tamanho > 100 && tamanho < 501){
            escala = 5;
        }else escala = 8;

        this.setBackgroundResource(drawable);
        paint = new Paint();
        ponteiro = new Path();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        primeiraVez = true;

        angulo = 0;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.save();

        super.onDraw(canvas);

        if(primeiraVez)
        {
            criaPath();

            primeiraVez = false;
        }

        canvas.rotate(angulo,this.getWidth()/2, this.getHeight()/2);
        canvas.drawCircle(tamanho/2, tamanho/2, tamanho/20, paint);
        //canvas.drawCircle(this.getWidth()/2, this.getHeight()/2, this.getHeight()/20, paint);
        canvas.drawPath(ponteiro, paint);

        canvas.restore();
    }

    public void desenhar(float a)
    {
        angulo = a;
        invalidate();
    }

    public void criaPath()
    {
        ponteiro = new Path();

        ponteiro.moveTo((tamanho/2)-escala, tamanho/2);
        ponteiro.lineTo((tamanho/2)+escala, tamanho/2);
        ponteiro.lineTo((tamanho/2)+escala, (tamanho/14)*12);
        ponteiro.lineTo((tamanho/2)-escala, (tamanho/14)*12);

        //ponteiro.moveTo((this.getLayoutParams().width /2)-8, this.getLayoutParams().height /2);
        //ponteiro.lineTo((this.getLayoutParams().width /2)+8, this.getLayoutParams().height /2);
        //ponteiro.lineTo((this.getLayoutParams().width /2)+8, (this.getLayoutParams().height /14)*12);
        //ponteiro.lineTo((this.getLayoutParams().width /2)-8, (this.getLayoutParams().height /14)*12);
        ponteiro.close();
    }
}
