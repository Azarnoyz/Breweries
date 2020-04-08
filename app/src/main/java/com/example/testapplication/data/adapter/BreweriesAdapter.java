package com.example.testapplication.data.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testapplication.R;
import com.example.testapplication.data.db.Breweries;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BreweriesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Callback callback;
    private List<Breweries> breweriesList;

    public BreweriesAdapter(List<Breweries> breweriesListFromDb) {
        breweriesList = breweriesListFromDb;
    }

    public void setCallback(Callback callbackClick) {
        callback = callbackClick;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.breweeries_list_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return breweriesList.size();
    }

    public void addItems(List<Breweries> breweriesListFromDb) {
        clearList();
        breweriesList.addAll(breweriesListFromDb);
        notifyDataSetChanged();
    }

    private void clearList() {
        breweriesList.clear();
        notifyDataSetChanged();
    }


    public interface Callback {
        void mapButtonClick(String lat, String lon);
    }


    public class ViewHolder extends BaseViewHolder {
        @BindView(R.id.title_text)
        TextView titleText;
        @BindView(R.id.city_text)
        TextView cityText;
        @BindView(R.id.country_text)
        TextView countryText;
        @BindView(R.id.phone_text)
        TextView phoneText;
        @BindView(R.id.street_text)
        TextView streetText;
        @BindView(R.id.state_text)
        TextView stateText;
        @BindView(R.id.website_text)
        TextView websiteText;
        @BindView(R.id.button_map)
        Button buttonMap;
        @BindView(R.id.city_ll)
        LinearLayout cityLineral;
        @BindView(R.id.country_ll)
        LinearLayout countryLineral;
        @BindView(R.id.phone_ll)
        LinearLayout phoneLineral;
        @BindView(R.id.state_ll)
        LinearLayout stateLineral;
        @BindView(R.id.street_ll)
        LinearLayout streetLineral;
        @BindView(R.id.website_ll)
        LinearLayout websiteLineral;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setPosition(int position) {
            super.setPosition(position);
            final Breweries breweries = breweriesList.get(position);

            if (!breweries.getName().equals("")) {
                titleText.setVisibility(View.VISIBLE);
                titleText.setText(breweries.getName());
            }

            if (!breweries.getStreet().equals("")) {
                streetLineral.setVisibility(View.VISIBLE);
                streetText.setText(breweries.getStreet());
            }
            if (!breweries.getCity().equals("")) {
                cityLineral.setVisibility(View.VISIBLE);
                cityText.setText(breweries.getCity());
            }

            if (!breweries.getCountry().equals("")) {
                countryLineral.setVisibility(View.VISIBLE);
                countryText.setText(breweries.getCountry());
            }

            if (!breweries.getPhone().equals("")) {
                phoneLineral.setVisibility(View.VISIBLE);
                phoneText.setText(breweries.getPhone());
            }

            if (!breweries.getState().equals("")) {
                stateLineral.setVisibility(View.VISIBLE);
                stateText.setText(breweries.getState());
            }

            if (!breweries.getWebsiteUrl().equals("")) {
                websiteLineral.setVisibility(View.VISIBLE);
                websiteText.setText(breweries.getWebsiteUrl());
                websiteText.setPaintFlags(websiteText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            }

            try {
                if (!breweries.getLatitude().equals("") || !breweries.getLongitude().equals(""))
                    buttonMap.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                Log.d("exception", "lat or lon null");
            }

            buttonMap.setOnClickListener(v -> callback.mapButtonClick(breweries.getLatitude(), breweries.getLongitude()));

            websiteText.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(breweries.getWebsiteUrl()));
                    itemView.getContext().startActivity(intent);
                } catch (Exception e) {
                    Log.e("Website error", "onClick: Website url is not correct");
                }
            });
        }
    }

}
