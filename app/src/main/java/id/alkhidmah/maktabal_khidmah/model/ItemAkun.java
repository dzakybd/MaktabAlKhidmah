package id.alkhidmah.maktabal_khidmah.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import id.alkhidmah.maktabal_khidmah.R;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class ItemAkun extends RealmObject implements IItem<ItemAkun, ItemAkun.ViewHolder> {

    @PrimaryKey
    public long daerahid;

    public String namadaerah;
    public String keterangan;
    public String header;

    public int akunid;
    public String nama;
    public String nohp;
    public String password;
    public int peran;
    public int daerahpdkr;
    public int rombongankrjamaah;
    public boolean aktif;
    public double lat;
    public double lng;
    public String keteranganpengurus;

    public int daerahpdkr_nama;
    public String rombongankrjamaah_nama;


    public ItemAkun withHeader(String header) {
        this.header = header;
        return this;
    }

    public ItemAkun create(String namadaerah, String keterangan) {
        this.namadaerah = namadaerah;
        this.keterangan = keterangan;
        return this;
    }

     public ItemAkun withIdentifier(long daerahid) {
        this.daerahid = daerahid;
        return this;
    }

    @Override
    public long getIdentifier() {
        return daerahid;
    }

    // the tag for this item
    @Ignore
    protected Object mTag;

    public ItemAkun withTag(Object object) {
        this.mTag = object;
        return this;
    }

   @Override
    public Object getTag() {
        return mTag;
    }

    // defines if this item is enabled
    @Ignore
    protected boolean mEnabled = true;

     public ItemAkun withEnabled(boolean enabled) {
        this.mEnabled = enabled;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return mEnabled;
    }

    // defines if the item is selected
    @Ignore
    protected boolean mSelected = false;

    @Override
    public ItemAkun withSetSelected(boolean selected) {
        this.mSelected = selected;
        return this;
    }

    @Override
    public boolean isSelected() {
        return mSelected;
    }

    // defines if this item is selectable
    @Ignore
    protected boolean mSelectable = true;


    @Override
    public ItemAkun withSelectable(boolean selectable) {
        this.mSelectable = selectable;
        return this;
    }

    @Override
    public boolean isSelectable() {
        return mSelectable;
    }

    @Override
    public int getType() {
        return R.id.item_daerah_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_daerah;
    }


    @Override
    public View generateView(Context ctx) {
        ViewHolder viewHolder = getViewHolder(LayoutInflater.from(ctx).inflate(getLayoutRes(), null, false));

        //as we already know the type of our ViewHolder cast it to our type
        bindView(viewHolder, Collections.EMPTY_LIST);

        //return the bound view
        return viewHolder.itemView;
    }

    @Override
    public View generateView(Context ctx, ViewGroup parent) {
        ViewHolder viewHolder = getViewHolder(LayoutInflater.from(ctx).inflate(getLayoutRes(), parent, false));

        //as we already know the type of our ViewHolder cast it to our type
        bindView(viewHolder, Collections.EMPTY_LIST);
        //return the bound and generatedView
        return viewHolder.itemView;
    }


    @Override
    public ViewHolder getViewHolder(ViewGroup parent) {
        return getViewHolder(LayoutInflater.from(parent.getContext()).inflate(getLayoutRes(), parent, false));
    }

    private ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public void bindView(ViewHolder holder, List<Object> payloads) {
        //set the selected state of this item. force this otherwise it may is missed when implementing an item
        holder.itemView.setSelected(isSelected());

        holder.mTextNama.setText(namadaerah);
        if(keterangan.isEmpty()){
            holder.mTextKeterangan.setVisibility(View.GONE);
        }else{
            holder.mTextKeterangan.setText(keterangan);
        }
    }

    @Override
    public void unbindView(ViewHolder holder) {
        holder.mTextNama.setText(null);
        holder.mTextKeterangan.setText(null);
    }

    @Override
    public void attachToWindow(ViewHolder holder) {}

    @Override
    public void detachFromWindow(ViewHolder holder) {}

    @Override
    public boolean failedToRecycle(ViewHolder holder) {
        return false;
    }

    @Override
    public boolean equals(int id) {
        return id == daerahid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractItem<?, ?> that = (AbstractItem<?, ?>) o;
        return daerahid == that.getIdentifier();
    }


    @Override
    public int hashCode() {
        return Long.valueOf(daerahid).hashCode();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextKeterangan;
        private TextView mTextNama;

        ViewHolder(View view) {
            super(view);
            mTextKeterangan = view.findViewById(R.id.text_keterangan);
            mTextNama = view.findViewById(R.id.text_nama);
        }
    }
}