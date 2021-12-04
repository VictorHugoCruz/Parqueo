// Generated by view binder compiler. Do not edit!
package com.victorcruz.parking.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.victorcruz.parking.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnLogin;

  @NonNull
  public final Button btnRegistro;

  @NonNull
  public final SignInButton btnSigningoogle;

  @NonNull
  public final TextInputEditText etMail;

  @NonNull
  public final TextInputEditText etPass;

  @NonNull
  public final TextInputLayout password;

  @NonNull
  public final TextView txtInicio;

  @NonNull
  public final TextInputLayout user;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnLogin,
      @NonNull Button btnRegistro, @NonNull SignInButton btnSigningoogle,
      @NonNull TextInputEditText etMail, @NonNull TextInputEditText etPass,
      @NonNull TextInputLayout password, @NonNull TextView txtInicio,
      @NonNull TextInputLayout user) {
    this.rootView = rootView;
    this.btnLogin = btnLogin;
    this.btnRegistro = btnRegistro;
    this.btnSigningoogle = btnSigningoogle;
    this.etMail = etMail;
    this.etPass = etPass;
    this.password = password;
    this.txtInicio = txtInicio;
    this.user = user;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnLogin;
      Button btnLogin = rootView.findViewById(id);
      if (btnLogin == null) {
        break missingId;
      }

      id = R.id.btnRegistro;
      Button btnRegistro = rootView.findViewById(id);
      if (btnRegistro == null) {
        break missingId;
      }

      id = R.id.btn_signingoogle;
      SignInButton btnSigningoogle = rootView.findViewById(id);
      if (btnSigningoogle == null) {
        break missingId;
      }

      id = R.id.et_mail;
      TextInputEditText etMail = rootView.findViewById(id);
      if (etMail == null) {
        break missingId;
      }

      id = R.id.et_pass;
      TextInputEditText etPass = rootView.findViewById(id);
      if (etPass == null) {
        break missingId;
      }

      id = R.id.password;
      TextInputLayout password = rootView.findViewById(id);
      if (password == null) {
        break missingId;
      }

      id = R.id.txtInicio;
      TextView txtInicio = rootView.findViewById(id);
      if (txtInicio == null) {
        break missingId;
      }

      id = R.id.user;
      TextInputLayout user = rootView.findViewById(id);
      if (user == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, btnLogin, btnRegistro,
          btnSigningoogle, etMail, etPass, password, txtInicio, user);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
