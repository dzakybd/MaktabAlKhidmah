package id.alkhidmah.maktabal_khidmah.view_util;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import id.alkhidmah.maktabal_khidmah.R;
import id.alkhidmah.maktabal_khidmah.util.PrefKeys;
import io.realm.Realm;

public class AddDaerahFragment extends BottomSheetDialogFragment {

    Context mContext;
    private MaterialButton mButtonTambah;
    private TextInputLayout mTextNama;
    Realm mRealm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_daerah, parent, false);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
        setfungsi(view);
        return view;
    }

    private void setfungsi(View view) {
        String[] headers = PrefKeys.headers;
        mButtonTambah = view.findViewById(R.id.button_tambah);
        mTextNama = view.findViewById(R.id.text_nama);

        mButtonTambah.setOnClickListener(v -> {
            if (!Objects.requireNonNull(mTextNama.getEditText()).getText().toString().isEmpty()) {
                dismiss();

//                mRealm = Realm.getDefaultInstance();
//                mRealm.where(ItemDaerah.class).findAllAsync().addChangeListener(new RealmChangeListener<RealmResults<ItemDaerah>>() {
//                    @Override
//                    public void onChange(RealmResults<ItemDaerah> userItems) {
//                        //Remove the change listener
//                        userItems.removeChangeListener(this);
//                        //Store the primary key to get access from a other thread
//                        String[] headers = PrefKeys.headers;
//                        final long newPrimaryKey = userItems.last().getIdentifier() + 1;
//                        mRealm.executeTransactionAsync(new Realm.Transaction() {
//                            @Override
//                            public void execute(Realm realm) {
//                                ItemDaerah newDaerah = realm.createObject(ItemDaerah.class, newPrimaryKey);
//                                newDaerah.create(-1, mTextNama.getEditText().getText().toString(), "").withHeader(headers[1]);
//                            }
//                        });
//                    }
//
//                });

            }
        });

    }

}
