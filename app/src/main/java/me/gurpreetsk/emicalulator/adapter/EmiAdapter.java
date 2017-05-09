package me.gurpreetsk.emicalulator.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.emicalulator.R;
import me.gurpreetsk.emicalulator.activity.EmiListActivity;
import me.gurpreetsk.emicalulator.activity.MainActivity;
import me.gurpreetsk.emicalulator.model.Emi;

/**
 * Created by gurpreet on 05/05/17.
 */

public class EmiAdapter extends RecyclerView.Adapter<EmiAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Emi> emis;
    private DatabaseReference loanRequestRef;

    private static final String TAG = EmiAdapter.class.getSimpleName();


    public EmiAdapter(Context context, ArrayList<Emi> emis) {
        this.context = context;
        this.emis = emis;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        loanRequestRef = database.getReference("Loan Requests");
        emis.add(0, new Emi("Principal", "Duration", "EMI", "Amount"));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_emi_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (position == 0) {
            holder.textviewSno.setText("S.no");
            holder.textviewEmi.setText("EMI");
            holder.textviewTenure.setText("Tenure");
            holder.textviewTotal.setText("Amount");
            holder.textviewPrincipal.setText("Principal");
        } else {
            holder.textviewSno.setText("" + position);
            holder.textviewEmi.setText(
                    String.format("%.2f", Double.parseDouble(emis.get(holder.getAdapterPosition()).getEmi())));
            holder.textviewTenure.setText(
                    String.format(emis.get(holder.getAdapterPosition()).getDuration()));
            holder.textviewTotal.setText(
                    String.format("%.2f", Double.parseDouble(emis.get(holder.getAdapterPosition()).getAmount())));
            holder.textviewPrincipal.setText(emis.get(holder.getAdapterPosition()).getPrincipal());
            holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new MaterialDialog.Builder(context)
                            .title("Submit Request?")
                            .positiveText(R.string.submit)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    Emi emi = new Emi();
                                    emi.setDuration(emis.get(holder.getAdapterPosition()).getDuration());
                                    emi.setPrincipal(emis.get(holder.getAdapterPosition()).getPrincipal());
                                    emi.setEmi(emis.get(holder.getAdapterPosition()).getEmi());
                                    emi.setAmount(emis.get(holder.getAdapterPosition()).getAmount());
                                    emi.setContact(PreferenceManager.getDefaultSharedPreferences(context)
                                            .getString(context.getString(R.string.contact), "+919971897447"));
                                    Log.i(TAG, "onClick: " + emis.get(holder.getAdapterPosition()).getContact());
                                    loanRequestRef.push().setValue(emi,
                                            new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(DatabaseError databaseError,
                                                                       DatabaseReference databaseReference) {
                                                    Toast.makeText(context, "Request submitted!",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            })
                            .negativeText(android.R.string.no)
                            .show();
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return emis.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textview_sno)
        TextView textviewSno;
        @BindView(R.id.textview_emi)
        TextView textviewEmi;
        @BindView(R.id.textview_tenure)
        TextView textviewTenure;
        @BindView(R.id.textview_total)
        TextView textviewTotal;
        @BindView(R.id.textview_principal)
        TextView textviewPrincipal;
        @BindView(R.id.linearlayout_emi)
        LinearLayout linearLayout;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
