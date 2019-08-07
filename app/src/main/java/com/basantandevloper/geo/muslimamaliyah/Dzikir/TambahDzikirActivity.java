package com.basantandevloper.geo.muslimamaliyah.Dzikir;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.basantandevloper.geo.muslimamaliyah.Database.DatabaseHelper;
import com.basantandevloper.geo.muslimamaliyah.R;
import com.basantandevloper.geo.muslimamaliyah.helper.InputValidation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TambahDzikirActivity extends AppCompatActivity {

    private final AppCompatActivity activity = TambahDzikirActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutNama;
    private TextInputLayout textInputLayoutArti;
    private TextInputLayout textInputLayoutBacaan;
    private TextInputLayout textInputLayoutHitungan;

    private TextInputEditText textInputEditTextNama;
    private TextInputEditText textInputEditTextArti;
    private TextInputEditText textInputEditTextBacaan;
    private TextInputEditText textInputEditTextHitungan;

    private AppCompatButton appCompatButtonTambah;
    private AppCompatTextView appCompatTextViewBenefList;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private DzikirModel dzikirModel;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = TambahDzikirActivity.this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(TambahDzikirActivity.this,R.color.deeppurple));
        setContentView(R.layout.activity_tambah_dzikir);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        initObjects();

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutNama = (TextInputLayout) findViewById(R.id.textInputLayoutNama);
        textInputLayoutArti = (TextInputLayout) findViewById(R.id.textInputLayoutArti);
        textInputLayoutBacaan = (TextInputLayout) findViewById(R.id.textInputLayoutBacaan);
        textInputLayoutHitungan = (TextInputLayout) findViewById(R.id.textInputLayoutHitungan);

        textInputEditTextNama = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextArti = (TextInputEditText) findViewById(R.id.textInputEditTextArti);
        textInputEditTextBacaan = (TextInputEditText) findViewById(R.id.textInputEditTextBacaan);
        textInputEditTextHitungan = (TextInputEditText) findViewById(R.id.textInputEditTextHitungan);

        appCompatButtonTambah = (AppCompatButton) findViewById(R.id.appCompatButtonTambah);

    }
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        dzikirModel = new DzikirModel();
    }


    private void emptyInputEditText() {
        textInputEditTextNama.setText(null);
        textInputEditTextBacaan.setText(null);
        textInputEditTextHitungan.setText(null);
    }

    public void postDzikirBaru(View view) {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextNama,
                textInputLayoutNama, getString(R.string.error_message_name))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(textInputEditTextHitungan, textInputLayoutHitungan, getString(R.string.error_message_hitungan))) {
            return;
        }

        dzikirModel.setNama(textInputEditTextNama.getText().toString().trim());
        dzikirModel.setArti(textInputEditTextArti.getText().toString().trim());
        dzikirModel.setBacaan(textInputEditTextBacaan.getText().toString().trim());
        dzikirModel.setHitungan(textInputEditTextHitungan.getText().toString().trim());


        databaseHelper.addDzikir(dzikirModel);

        // Snack Bar to show success message that record saved successfully
        Intent accountsIntent = new Intent(activity, DzikirActivity.class);
        Toast.makeText(this, "Dzikir Anda Sudah di Tambahkan!", Toast.LENGTH_SHORT)
                .show();
        accountsIntent.putExtra("NAMA", textInputEditTextNama.getText().toString().trim());
        accountsIntent.putExtra("ARTI", textInputEditTextArti.getText().toString().trim());
        accountsIntent.putExtra("BACAAN", textInputEditTextBacaan.getText().toString().trim());
        accountsIntent.putExtra("HITUNGAN", textInputEditTextHitungan.getText().toString().trim());
        emptyInputEditText();
        startActivity(accountsIntent);

    }
}
