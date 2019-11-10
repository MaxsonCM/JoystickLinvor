package com.webnode.maxsoncm.joysticklinvor;
import java.io.ByteArrayInputStream;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * Created by Maxson on 08/02/2016.
 */
public class LayoutAdapter extends BaseAdapter{
    private Context context;
    private List<Obj_Layout> list;

    public LayoutAdapter(Context context, List<Obj_Layout> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0).getCodigo();
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {
        final int auxPosition = position;

        Bitmap foto=null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_layout, null);

        TextView tvn = (TextView) layout.findViewById(R.id.tv_nome);
        tvn.setText(list.get(position).getNome());

        TextView tvd = (TextView) layout.findViewById(R.id.tv_descricao);
        tvd.setText(list.get(position).getDescricao());


        if (list.get(position).getImagem() != null) {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(list.get(position).getImagem());
            foto = BitmapFactory.decodeStream(inputStream);
        } else if (list.get(position).getBanner() != null){
            ByteArrayInputStream inputStream = new ByteArrayInputStream(list.get(position).getBanner());
            foto = BitmapFactory.decodeStream(inputStream);
        }else {
            //Bitmap foto = BitmapFactory.decodeFile(R.drawable.noImagem);
            //Bitmap foto = R.drawable.noImagem;
        }


        ImageView ivi = (ImageView) layout.findViewById(R.id.iv_imagem);
        ivi.setImageBitmap(foto) ;

        return layout;
    }
}
