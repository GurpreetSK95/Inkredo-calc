package me.gurpreetsk.emicalulator.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.gurpreetsk.emicalulator.R;
import me.gurpreetsk.emicalulator.model.Emi;

/**
 * Created by gurpreet on 05/05/17.
 */

public class EmiAdapter extends RecyclerView.Adapter<EmiAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Emi> emis;


    public EmiAdapter(Context context, ArrayList<Emi> emis) {
        this.context = context;
        this.emis = emis;
        emis.add(0, new Emi("Principal", "Duration", "EMI", "Amount"));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_emi_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
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

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
