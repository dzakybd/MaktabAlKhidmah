package id.alkhidmah.maktabal_khidmah.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import id.alkhidmah.maktabal_khidmah.model.Akun;
import id.alkhidmah.maktabal_khidmah.R;
/**
 * Created by zaki on 20/11/17.
 */

public class AkunRecyclerView extends AbstractItem<AkunRecyclerView, AkunRecyclerView.ViewHolder> {

    public Akun AkunModel;

    public AkunRecyclerView create(Akun AkunModel){
       this.AkunModel = AkunModel;
       return this;
    }

    @Override
    public int getType() {
        return AkunModel.akunid;
    }


    @Override
    public int getLayoutRes() {
//        return R.layout.Akun_item;
        return R.layout.activity_main;

    }

    @Override
    public void bindView(AkunRecyclerView.ViewHolder viewHolder, List<Object> payloads) {
        super.bindView(viewHolder, payloads);

        //define our data for the view
        viewHolder.tvjudul.setText(AkunModel.nama);

    }

    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.tvjudul.setText(null);
    }

    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    /**
     * our ViewHolder
     */
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvjudul;

        public ViewHolder(View view) {
            super(view);
//            tvjudul=view.findViewById(R.id.tvjudul);
        }
    }
}