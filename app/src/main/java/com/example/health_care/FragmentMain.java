package com.example.health_care;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.health_care.models.MedicalFolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends Fragment {
    int press;
    DatabaseReference ref;
    FirebaseUser user;
    MedicalFolder md;
    FirebaseAuth mAuth;
    LinearLayout bloodSect;
    LinearLayout pulseSect;
    LinearLayout oxymetySect;
    LinearLayout heightSect;
    LinearLayout weightsec;
    LinearLayout tempSect;
    LinearLayout orderSect;
    LinearLayout situationSect;
    TextView blood;
    TextView pulse;
    TextView oxymetry;
    TextView height;
    TextView weight;
    TextView temp;
    TextView order;
    TextView situation;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentMain() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMain.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_main, container, false);
        bloodSect = v.findViewById(R.id.bloodSect);
        oxymetySect = v.findViewById(R.id.oxymetrySect);
        pulseSect = v.findViewById(R.id.pulseSect);
        heightSect = v.findViewById(R.id.heightSect);
        weightsec = v.findViewById(R.id.weightsec);
        tempSect = v.findViewById(R.id.tempSect);
        orderSect = v.findViewById(R.id.orderSect);
        situationSect = v.findViewById(R.id.situationSect);
        blood = v.findViewById(R.id.blood);
        pulse = v.findViewById(R.id.pulse);
        oxymetry = v.findViewById(R.id.oxymetry);
        height = v.findViewById(R.id.height);
        weight = v.findViewById(R.id.weight);
        temp = v.findViewById(R.id.temp);
        md = null;
        press = 0;
        order = v.findViewById(R.id.order);
        situation = v.findViewById(R.id.situation);
        DossierMedicalMain activity = (DossierMedicalMain) getActivity();
        final String data= activity.getIntent().getExtras().getString("message");
        ref = FirebaseDatabase.getInstance().getReference("Dossier Medical").child(data);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                md = dataSnapshot.getValue(MedicalFolder.class);
                blood.setText(String.valueOf(md.getBloodPressure()));
                pulse.setText(String.valueOf(md.getPULSE()));
                oxymetry.setText(String.valueOf(md.getOxymety()));
                height.setText(String.valueOf(md.getHeight()));
                weight.setText(String.valueOf(md.getWeight()));
                temp.setText(String.valueOf(md.getTemperature()));
                order.setText(md.getOrder());
                situation.setText(md.getSituation());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        bloodSect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView titre,ancien;
                EditText nom,prenom;



                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);

                final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);

                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Edit Blood Pressure");
                ancien.setText("Blood Pressure : "+md.getBloodPressure());

                diagnom.setVisibility(v.VISIBLE);
                diagnom.setHint("Blood Pressure...");
                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.child("bloodPressure").setValue(diagnom.getText().toString());


                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        pulseSect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView titre,ancien;
                EditText nom,prenom;



                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);

                final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);

                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Edit Pulse");
                ancien.setText("Pulse : "+md.getPULSE());

                diagnom.setVisibility(v.VISIBLE);
                diagnom.setHint("Pulse...");
                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.child("pulse").setValue(diagnom.getText().toString());


                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }

        });
        oxymetySect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView titre,ancien;
                EditText nom,prenom;



                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);

                final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);

                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Edit Oxymetry");
                ancien.setText("Oxymetry : "+md.getOxymety());

                diagnom.setVisibility(v.VISIBLE);
                diagnom.setHint("Oxymetry...");
                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.child("oxymety").setValue(diagnom.getText().toString());


                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        heightSect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView titre,ancien;
                EditText nom,prenom;



                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);

                final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);

                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Edit Height");
                ancien.setText("Height : "+md.getHeight());

                diagnom.setVisibility(v.VISIBLE);
                diagnom.setHint("Height...");
                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.child("height").setValue(diagnom.getText().toString());


                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        weightsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView titre,ancien;
                EditText nom,prenom;



                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);

                final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);

                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Edit Weight");
                ancien.setText("Weight : "+md.getWeight());

                diagnom.setVisibility(v.VISIBLE);
                diagnom.setHint("Weight...");
                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.child("weight").setValue(diagnom.getText().toString());


                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        tempSect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView titre,ancien;
                EditText nom,prenom;



                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);

                final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);

                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Edit Temperature");
                ancien.setText("Temperature : "+md.getTemperature());

                diagnom.setVisibility(v.VISIBLE);
                diagnom.setHint("Temperature...");
                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.child("temperature").setValue(diagnom.getText().toString());


                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        orderSect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView titre,ancien;
                EditText nom,prenom;



                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);

                final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);

                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Edit order Medicaments");
                ancien.setText("Order : "+md.getOrder());

                diagnom.setVisibility(v.VISIBLE);
                diagnom.setHint("Order...");
                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.child("order").setValue(diagnom.getText().toString());


                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        situationSect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView titre,ancien;
                EditText nom,prenom;



                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                View mView = getLayoutInflater().inflate(R.layout.request_dialog_profile,null);

                final EditText diagnom = (EditText)mView.findViewById(R.id.diag_nom);

                titre=(TextView) mView.findViewById(R.id.titre);
                ancien=(TextView) mView.findViewById(R.id.ancien);

                titre.setText("Edit Situation");
                ancien.setText("Situation : "+md.getSituation());

                diagnom.setVisibility(v.VISIBLE);
                diagnom.setHint("Situation...");
                Button btn_cancel = (Button)mView.findViewById(R.id.cancel);
                Button btn_okay = (Button)mView.findViewById(R.id.send);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(false);

                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ref.child("situation").setValue(diagnom.getText().toString());


                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        return v;

    }
}
