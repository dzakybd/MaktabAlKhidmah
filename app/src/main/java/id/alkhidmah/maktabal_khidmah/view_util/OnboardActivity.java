package id.alkhidmah.maktabal_khidmah.view_util;

import android.os.Bundle;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import id.alkhidmah.maktabal_khidmah.R;
import id.alkhidmah.maktabal_khidmah.util.PrefKeys;

public class OnboardActivity extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard(getString(R.string.app_name), getString(R.string.app_desc), R.drawable.ic_logo_white);
        AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Rombongan", getString(R.string.fitur_rombongan_desc), R.drawable.ic_fitur_rombongan);
        AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Maktab ", getString(R.string.fitur_maktab_desc), R.drawable.ic_fitur_maktab);
        AhoyOnboarderCard ahoyOnboarderCard4 = new AhoyOnboarderCard("Dapur Umum", getString(R.string.fitur_dapur_desc), R.drawable.ic_fitur_konsumsi);
        AhoyOnboarderCard ahoyOnboarderCard5 = new AhoyOnboarderCard("Parkir", getString(R.string.fitur_parkir_desc), R.drawable.ic_fitur_parkir);
        AhoyOnboarderCard ahoyOnboarderCard6 = new AhoyOnboarderCard("Segera hadir..", getString(R.string.fitur_lain_desc), R.drawable.ic_fitur_jadwal);

        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(ahoyOnboarderCard1);
        pages.add(ahoyOnboarderCard2);
        pages.add(ahoyOnboarderCard3);
        pages.add(ahoyOnboarderCard4);
        pages.add(ahoyOnboarderCard5);
        pages.add(ahoyOnboarderCard6);
        for (AhoyOnboarderCard page : pages) {
            page.setTitleColor(R.color.white);
            page.setDescriptionColor(R.color.grey_200);
            page.setBackgroundColor(R.color.black_transparent);
        }
        showNavigationControls(true);
        setImageBackground(R.color.my_app_secondary_color);
        setFinishButtonTitle("Mengerti");
        setOnboardPages(pages);
    }

    @Override
    public void onFinishButtonPressed() {
        if(!Prefs.getBoolean(PrefKeys.tutorial,false)) {
            Prefs.putBoolean(PrefKeys.tutorial,true);
        }
        finish();
    }
}
