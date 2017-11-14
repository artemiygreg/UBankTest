package artemiygreg.ubanktest.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import artemiygreg.ubanktest.R;
import artemiygreg.ubanktest.model.Data;

/**
 * Created by artem_mobile_dev on 13.11.2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private List<Data> listData;
    private LayoutInflater mLayoutInflater;

    public DataAdapter(@NonNull LayoutInflater layoutInflater) {
        this.mLayoutInflater = layoutInflater;
    }

    public void setData(List<Data> listData) {
        this.listData = listData;
    }

    @Override
    public int getItemCount() {
        return listData != null ? listData.size() : 0;
    }

    @Override
    public DataAdapter.DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.data_item, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.DataViewHolder holder, int position) {
        holder.bind(listData.get(position));
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewTime;

        DataViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewTime = itemView.findViewById(R.id.text_view_time);
        }

        void bind(@NonNull Data data) {
            textViewTitle.setText(data.getTitle());
            textViewTime.setText(data.getTime());
        }
    }
}
