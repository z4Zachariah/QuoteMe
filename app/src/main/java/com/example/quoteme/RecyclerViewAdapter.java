package com.example.quoteme;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    // recyclerview adapter to handle the management of the recyclerview

    private List<Quotes> quotesList;

    private OnItemListener onItemListener;

//
    RecyclerViewAdapter(List<Quotes> quoteList) {
        this.quotesList = quoteList;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_layout, parent, false);

        view.setOnLongClickListener(new RV_ItemListener());

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {
        final Quotes quote = quotesList.get(position);
        holder.quote.setText(quote.getQuote());
        holder.source.setText(quote.getSource());
    }

    public int getID(final int position){
        final Quotes quote = quotesList.get(position);
        return quote.getId();
    }

    @Override
    public int getItemCount() {
        return quotesList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView quote;
        private TextView source;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            quote = itemView.findViewById(R.id.quote);
            source = itemView.findViewById(R.id.source);
            cardView = itemView.findViewById(R.id.carView);
        }


    }

    public  interface OnItemListener{
        void OnItemClickListener(View view, int position);
        void OnItemLongClickListener(View view, int position);
    }



    class RV_ItemListener implements View.OnClickListener, View.OnLongClickListener{

        @Override
        public void onClick(View view) {
            if (onItemListener != null){
                onItemListener.OnItemClickListener(view, view.getId());
            }
        }
        @Override
        public boolean onLongClick(View view) {
            if (onItemListener != null){
                onItemListener.OnItemLongClickListener(view,view.getId());

            }
            return true;
        }
    }

    public void setOnItemListenerListener(OnItemListener listener){
        this.onItemListener = listener;
    }





}