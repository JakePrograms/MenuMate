package com.kingdom.menumate.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.kingdom.menumate.R;

public class LicensesDialog extends DialogFragment {
    private TextView glideGithub, glideLicense;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Context context = getActivity();

        View view = null;
        AlertDialog.Builder builder = null;
        if (context != null) {
            view = getActivity().getLayoutInflater().inflate(R.layout.dialog_licenses, null);
            builder = new AlertDialog.Builder(getActivity())
                    .setView(view);
        }

        Button btnDismiss = null;
        if (view != null) {
            btnDismiss = view.findViewById(R.id.btnLicencesDismiss);

            initViews(view);
        }

        glideGithub.setText(Html.fromHtml("<a href=\"https://github.com/bumptech/glide\">GitHub</a>"));
        glideGithub.setMovementMethod(LinkMovementMethod.getInstance());
        glideLicense.setText(Html.fromHtml("<a href=\"https://github.com/bumptech/glide/blob/master/LICENSE\">License</a>"));
        glideLicense.setMovementMethod(LinkMovementMethod.getInstance());

        assert btnDismiss != null;
        btnDismiss.setOnClickListener(view1 -> dismiss());

        assert builder != null;
        return builder.create();
    }

    private void initViews(View view) {
        glideGithub = view.findViewById(R.id.glideGithub);
        glideLicense = view.findViewById(R.id.glideLicense);
    }
}
