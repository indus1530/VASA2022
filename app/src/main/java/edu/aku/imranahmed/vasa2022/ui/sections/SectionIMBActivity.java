package edu.aku.imranahmed.vasa2022.ui.sections;

import static edu.aku.imranahmed.vasa2022.core.MainApp.familyList;
import static edu.aku.imranahmed.vasa2022.core.MainApp.selectedAdol;
import static edu.aku.imranahmed.vasa2022.core.MainApp.selectedChild;
import static edu.aku.imranahmed.vasa2022.core.MainApp.selectedMWRA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;

import edu.aku.imranahmed.vasa2022.R;
import edu.aku.imranahmed.vasa2022.contracts.TableContracts;
import edu.aku.imranahmed.vasa2022.core.MainApp;
import edu.aku.imranahmed.vasa2022.database.DatabaseHelper;
import edu.aku.imranahmed.vasa2022.databinding.ActivitySectionImbBinding;
import edu.aku.imranahmed.vasa2022.ui.EndingActivity;

public class SectionIMBActivity extends AppCompatActivity {

    private static final String TAG = "SectionIMBActivity";
    ActivitySectionImbBinding bi;
    private DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(MainApp.langRTL ? R.style.AppThemeSindhi : R.style.AppThemeEnglish1);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_imb);
        bi.sno.setText(familyList.get(Integer.parseInt(selectedChild) - 1).getD101());
        bi.name.setText(familyList.get(Integer.parseInt(selectedChild) - 1).getD102());
        bi.index.setText(familyList.get(Integer.parseInt(selectedChild) - 1).getIndexed());
        bi.setChild(MainApp.child);
        setSupportActionBar(bi.toolbar);
        db = MainApp.appInfo.dbHelper;
    }

    private boolean updateDB() {
        if (MainApp.superuser) return true;

        int updcount = 0;
        try {
            updcount = db.updatesChildColumn(TableContracts.ChildTable.COLUMN_SIM, MainApp.child.sIMtoString());
        } catch (JSONException e) {
            Toast.makeText(this, R.string.upd_db + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, R.string.upd_db_error, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    public void btnContinue(View view) {
        if (!formValidation()) return;
        if (updateDB()) {
            finish();
            startActivity(new Intent(this,
                    !selectedAdol.equals("") || MainApp.familyList.get(Integer.parseInt(selectedMWRA) - 1).getIndexed().equals("3") ? NeonateBActivity.class
                            : SectionMActivity.class));
        } else {
            Toast.makeText(this, R.string.fail_db_upd, Toast.LENGTH_SHORT).show();
        }
    }

    public void btnEnd(View view) {
        finish();
        startActivity(new Intent(this, EndingActivity.class).putExtra("complete", false));
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }


    @Override
    public void onBackPressed() {
        // Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();
        setResult(RESULT_CANCELED);
    }
}