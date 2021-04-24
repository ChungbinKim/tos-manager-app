package com.example.tosmanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.tosmanager.R;
import com.example.tosmanager.model.DataHolder;
import com.example.tosmanager.util.CreateDialog;

import java.io.InputStream;
import java.io.OutputStream;

public class ConfigurationFragment extends PreferenceFragmentCompat {
    private static final int IMPORT_DATA = 1;
    private static final int EXPORT_DATA = 2;

    private boolean promptResult;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        DataHolder dataHolder = DataHolder.getInstance();

        // 가져오기
        Preference importPreference = findPreference("import");
        importPreference.setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/json");
            startActivityForResult(intent, IMPORT_DATA);

            return true;
        });

        // 내보내기
        Preference exportPreference = findPreference("export");
        exportPreference.setOnPreferenceClickListener(preference -> {
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("application/json");
            intent.putExtra(Intent.EXTRA_TITLE, "tos.json");
            startActivityForResult(intent, EXPORT_DATA);

            return true;
        });

        // 로그아웃
        Preference loginOutPreference = findPreference("loginOut");
        loginOutPreference.setOnPreferenceClickListener(preference -> {
            promptResult = false;
            CreateDialog.createPrompt(getActivity(), R.string.log_out_dialog_message, (dialog, which) -> {
                dataHolder.setLoginSession(null);

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

                promptResult = true;
            }).show();

            return promptResult;
        });

        // 버전 설정
        Preference versionPreference = findPreference("version");
        String version;
        try {
            version = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = "Not Found";
        }
        versionPreference.setSummary(String.format("Version: %s", version));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case IMPORT_DATA:
                        InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());

                        // TODO: 입력 -> DB에 저장
                        Toast.makeText(getActivity(), "import", Toast.LENGTH_SHORT).show();

                        inputStream.close();
                        break;
                    case EXPORT_DATA:
                        OutputStream outputStream = getActivity().getContentResolver().openOutputStream(data.getData());

                        // TODO: DB 가져오기 -> 출력
                        Toast.makeText(getActivity(), "export", Toast.LENGTH_SHORT).show();

                        outputStream.close();
                        break;
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}