package id.alkhidmah.maktabal_khidmah.view_util;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.leinardi.android.speeddial.SpeedDialView;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.listeners.ItemFilterListener;
import com.mikepenz.fastadapter.listeners.OnClickListener;
import com.mikepenz.itemanimators.SlideDownAlphaAnimator;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.alkhidmah.maktabal_khidmah.R;
import id.alkhidmah.maktabal_khidmah.model.ItemDaerah;
import id.alkhidmah.maktabal_khidmah.adapter.StickyHeaderAdapter;
import id.alkhidmah.maktabal_khidmah.util.PrefKeys;
import id.alkhidmah.maktabal_khidmah.util.SharedMethods;
import io.realm.Realm;


public class ListDaerahActivity extends AppCompatActivity implements ItemFilterListener<ItemDaerah> {


    Toolbar mToolbar;
    RecyclerView mRecyclerDaerah;
    StickyHeaderAdapter stickyHeaderAdapter;
    ItemAdapter headerAdapter;
    ItemAdapter itemAdapter;
    FastAdapter fastAdapter;
    SpeedDialView mFabUsul;
    Realm mRealm;
    List<ItemDaerah> itemDaerahs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_daerah);

        mToolbar = findViewById(R.id.toolbar);
        mRecyclerDaerah = findViewById(R.id.recycler_daerah);
        mFabUsul = findViewById(R.id.fab_usul);


        settoolbar();
        setfab();
        setTitle("Daftar Daerah");
        setlist();
        setdata();


    }

    private void setfab() {

        mFabUsul.setOnChangeListener(new SpeedDialView.OnChangeListener() {
            @Override
            public boolean onMainActionSelected() {
//                Toast.makeText(getApplicationContext(), "masuk", Toast.LENGTH_SHORT).show();
                    AddDaerahFragment addDaerahFragment = new AddDaerahFragment();
                    addDaerahFragment.show(getSupportFragmentManager(), addDaerahFragment.getTag());
                return false;
            }

            @Override
            public void onToggleChanged(boolean isOpen) {
                Log.d(PrefKeys.ErrorTAG, "Speed dial toggle state changed. Open = " + isOpen);
            }
        });

        mFabUsul.setOnActionSelectedListener(speedDialActionItem -> {
            switch (speedDialActionItem.getId()) {
                case R.id.fab_usul:

                    return false; // true to keep the Speed Dial open
                default:
                    return false;
            }
        });
    }


    private void settoolbar() {
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setdata() {
        String[] headers = PrefKeys.headers;
        mRealm = Realm.getDefaultInstance();

        SharedMethods sharedMethods = new SharedMethods();
        if(sharedMethods.checkint(this, false)){
//            get online and save to realm, then use arraylist
            for (int i = 1; i <= 10; i++) {
                ItemDaerah itemDaerah = new ItemDaerah();
                itemDaerah.create("Demak " + i, "Kota").withHeader(headers[i / 5]).withIdentifier(i);
                itemDaerahs.add(itemDaerah);
            }

            itemAdapter.add(itemDaerahs);

        }else{
//            get from realm, then use arraylist

            for (int i = 1; i <= 10; i++) {
                ItemDaerah itemDaerah = new ItemDaerah();
                itemDaerah.create("Semarang " + i, "Kota").withHeader(headers[i / 5]).withIdentifier(i);
                itemDaerahs.add(itemDaerah);
            }

            itemAdapter.add(itemDaerahs);

        }

//        mRealm.executeTransactionAsync(realm -> {
//            //insert the created objects to realm
//            //a bulk insert has lower object allocations then a copy
//            realm.insertOrUpdate(itemDaerahs);
//        });

    }

    private void setlist() {

        //create our adapters
        stickyHeaderAdapter = new StickyHeaderAdapter();
        headerAdapter = new ItemAdapter();
        itemAdapter = new ItemAdapter();
        itemDaerahs = new ArrayList<>();

        //create our FastAdapter
        fastAdapter = FastAdapter.with(Arrays.asList(headerAdapter, itemAdapter));
        fastAdapter.withSelectable(true);

        //configure our fastAdapter
        //as we provide id's for the items we want the hasStableIds enabled to speed up things
        fastAdapter.setHasStableIds(true);

        //get our recyclerView and do basic setup
        mRecyclerDaerah.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerDaerah.setItemAnimator(new SlideDownAlphaAnimator());
        Objects.requireNonNull(mRecyclerDaerah.getItemAnimator()).setAddDuration(500);
        mRecyclerDaerah.getItemAnimator().setRemoveDuration(500);
        mRecyclerDaerah.setAdapter(stickyHeaderAdapter.wrap(fastAdapter));

        //this adds the Sticky Headers within our list
        final StickyRecyclerHeadersDecoration decoration = new StickyRecyclerHeadersDecoration(stickyHeaderAdapter);
        mRecyclerDaerah.addItemDecoration(decoration);

        //so the headers are aware of changes
        stickyHeaderAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                decoration.invalidateHeaders();
            }
        });

        //configure our fastAdapter
        fastAdapter.withOnClickListener((OnClickListener<ItemDaerah>) (v, adapter, item, position) -> {
            Toast.makeText(v.getContext(), item.namadaerah, Toast.LENGTH_SHORT).show();
            return false;
        });

        //configure the itemAdapter
        itemAdapter.getItemFilter().withFilterPredicate((IItemAdapter.Predicate<ItemDaerah>) (item, constraint) -> {
            //return true if we should filter it out
            //return false to keep it
            Log.d("filter", item.namadaerah);
            return item.namadaerah.toLowerCase().contains(constraint.toString().toLowerCase());
        });

        itemAdapter.getItemFilter().withItemFilterListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                itemAdapter.filter(s);
                return true;
            }


            @Override
            public boolean onQueryTextChange(String s) {
                itemAdapter.filter(s);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void itemsFiltered(@Nullable CharSequence constraint, @Nullable List<ItemDaerah> results) {
        Toast.makeText(ListDaerahActivity.this, "filtered items count: " + itemAdapter.getAdapterItemCount(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReset() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (fastAdapter != null) {
            fastAdapter.notifyDataSetChanged();
        }
    }

    //Prevent the realm instance from leaking
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeRealm();
    }

    private void closeRealm() {
        if (!mRealm.isClosed()) {
            mRealm.close();
        }
    }
}
