package com.example.tosmanager.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tosmanager.BuildConfig;
import com.example.tosmanager.R;
import com.example.tosmanager.model.DataHolder;

public class ConfigurationFragment extends PreferenceFragmentCompat {
    private boolean logout;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        DataHolder dataHolder = DataHolder.getInstace();
        // 로그인/로그아웃
        Preference loginOutPreference = findPreference("loginOut");
        if (dataHolder.getLoginSession() != null) {
            loginOutPreference.setTitle(getString(R.string.log_out_title));

            loginOutPreference.setOnPreferenceClickListener(preference -> {
                logout = false;
                createLogoutDialog((dialog, which) -> {
                    dataHolder.setLoginSession(null);

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                    logout = true;
                }).show();

                return logout;
            });
        } else {
            loginOutPreference.setTitle(getString(R.string.log_in_title));

            loginOutPreference.setOnPreferenceClickListener(preference -> {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                return true;
            });
        }

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

    private Dialog createLogoutDialog(DialogInterface.OnClickListener onClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.log_out_dialog_message)
                .setPositiveButton(R.string.yes, onClick)
                .setNegativeButton(R.string.no, ((dialog, which) -> dialog.cancel()));

        return builder.create();
    }
}