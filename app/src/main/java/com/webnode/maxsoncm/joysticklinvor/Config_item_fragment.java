package com.webnode.maxsoncm.joysticklinvor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Maxson on 15/04/2016.
 */
public class Config_item_fragment extends DialogFragment {
    private Obj_Componente componente;
    private String[] arraySpinner;

    public Config_item_fragment(){

    }
    /*public Config_item_fragment(Obj_Componente componente){
        this.componente = componente;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Script", "onCreate()");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i("Script", "onCreateView()");

        View view = inflater.inflate(R.layout.activity_config_item_fragment ,container );

        this.arraySpinner = new String[] {
                "1", "2", "3", "4", "5"
        };

        Spinner s = (Spinner) view.findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);


        Button btnOk = (Button) view.findViewById(R.id.btOk);
        Button btnCancel = (Button) view.findViewById(R.id.btCancelar);
        TextView txtEditRotulo = (TextView) view.findViewById(R.id.txtEditRotulo);
        TextView txtEditAltura = (TextView) view.findViewById(R.id.txtEditAltura);
        TextView txtEditLargura = (TextView) view.findViewById(R.id.txtEditLargura);
        TextView txtEditRotacao = (TextView) view.findViewById(R.id.txtEditRotacao);
        TextView txtEditComandoD = (TextView) view.findViewById(R.id.txtEditComandoD);
        TextView txtEditComandoU = (TextView) view.findViewById(R.id.txtEditComandoU);
        TextView txtEditComandoF = (TextView) view.findViewById(R.id.txtEditComandoF);
        TextView txtEditAnalogMax = (TextView) view.findViewById(R.id.txtEditAnalogMax);
        TextView txtEditAnalogMin = (TextView) view.findViewById(R.id.txtEditAnalogMin);
        TextView txtEditPositionX = (TextView) view.findViewById(R.id.txtEditPositionX);
        TextView txtEditPositionY = (TextView) view.findViewById(R.id.txtEditPositionY);

        //componente.getCodigo();
        //componente.getLayout();
        //componente.getOrdem();
        txtEditRotulo.setText(componente.getRotulo());
        //componente.getStylo();
        //componente.getTipo();

        txtEditAltura.setText(String.valueOf(componente.getAltura()));
        txtEditLargura.setText(String.valueOf(componente.getLargura()));
        txtEditPositionX.setText(String.valueOf(componente.getPosicaoX()));
        txtEditPositionY.setText(String.valueOf(componente.getPosicaoY()));
        txtEditRotacao.setText(String.valueOf(componente.getRotacao()));


        txtEditComandoD.setText(componente.getDown());
        txtEditComandoU.setText(componente.getUP());
        txtEditComandoF.setText(componente.getFim());
        txtEditAnalogMax.setText(String.valueOf(componente.getAnalogico_max()));
        txtEditAnalogMin.setText(String.valueOf(componente.getAnalogico_min()));


        btnOk.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                //dismiss();
                ((EditControler) getActivity()).turnOffDialogFragment();
            }
        });
        btnCancel.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return (view);
    }

    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);
        Log.i("Script", "onCreateDialog()");

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                .setTitle("DialogFragment")
                .setIcon(R.drawable.china)
                .setPositiveButton("Aplicar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Aplicado", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return(alert.show());
    }
*/
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Log.i("Script", "onActivityCreated()");

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        Log.i("Script", "onAttach()");
    }

    @Override
    public void onCancel(DialogInterface dialog){
        super.onCancel(dialog);
        Log.i("Script", "onCancel()");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        Log.i("Script", "onDestroyView()");
    }

    @Override
    public void onDetach(){
        super.onDetach();
        Log.i("Script", "onDetach()");
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        super.onDismiss(dialog);
        Log.i("Script", "onDismiss()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i("Script", "onSaveInstanceState()");
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i("Script", "onStart()");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.i("Script", "onStop()");
    }
}
