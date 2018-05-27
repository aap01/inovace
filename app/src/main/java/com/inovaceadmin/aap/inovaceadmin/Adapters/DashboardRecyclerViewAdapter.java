package com.inovaceadmin.aap.inovaceadmin.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inovaceadmin.aap.inovaceadmin.Activities.DepartmentDetailsActivity;
import com.inovaceadmin.aap.inovaceadmin.Models.DashboardCard;
import com.inovaceadmin.aap.inovaceadmin.R;

import java.util.List;

public class DashboardRecyclerViewAdapter extends RecyclerView.Adapter<DashboardRecyclerViewAdapter.CardViewHolder> {
    public Context context;
    private List<DashboardCard> dashboardCards;
    public DashboardRecyclerViewAdapter(Context context, List<DashboardCard> dashboardCards) {
        this.context = context;
        this.dashboardCards = dashboardCards;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.dashboard_gridcell,parent,false);
        CardViewHolder cardViewHolder = new CardViewHolder(view,context);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.textView_name.setText(dashboardCards.get(position).getName());
        holder.textView_absent.setText("Absent: "+dashboardCards.get(position).getAbsent());
        holder.textView_present.setText("Present: "+dashboardCards.get(position).getPresent());
        holder.getTextView_total.setText("Total: "+dashboardCards.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return dashboardCards.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView textView_name,textView_absent,
                textView_present,getTextView_total;
        Context context;


        public CardViewHolder(View itemView, final Context context) {
            super(itemView);
            this.context = context;
            textView_name = itemView.findViewById(R.id.department_name);
            textView_absent = itemView.findViewById(R.id.department_absent);
            textView_present = itemView.findViewById(R.id.department_present);
            getTextView_total = itemView.findViewById(R.id.department_total);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /**
                     * getting token from local cache
                     */
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    String token = sharedPreferences.getString("token","");
                    String refreshToken = sharedPreferences.getString("refreshToken","");
                    Intent intent = new Intent(context, DepartmentDetailsActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
