package com.webnode.maxsoncm.joysticklinvor;

import android.os.Bundle;

import java.util.List;

import android.app.ListActivity;

public class ListaLayouts  extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list_users);

        ControleBD bd  = new ControleBD(this);



        List<Obj_Layout> list = bd.buscar();
        setListAdapter(new LayoutAdapter(this, list));
    }

}
