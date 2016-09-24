package me.ewriter.bangumitv.ui.login;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.ewriter.bangumitv.R;
import me.ewriter.bangumitv.base.BaseActivity;

/**
 * Created by Zubin on 2016/9/18.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {

    Toolbar mToolbar;
    TextInputLayout mNameEdit;
    TextInputLayout mPasswordEdit;
    Button mLoginButton;
    ProgressDialog mProgressDialog;
    TextView mLoginHint;

    LoginContract.Presenter mPresenter;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNameEdit = (TextInputLayout) findViewById(R.id.emaiInput);
        mPasswordEdit = (TextInputLayout) findViewById(R.id.passwordInput);
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginHint = (TextView) findViewById(R.id.login_hint);

        mLoginHint.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);

        setupToolbar();

        mPresenter = new LoginPresenter(this);
        mPresenter.subscribe();
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.login));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
    }

    @Override
    protected void initBefore() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_hint:
                mPresenter.openLoginHint(LoginActivity.this);
                break;

            case R.id.login_button:
                String email = mNameEdit.getEditText().getText().toString();
                String password = mPasswordEdit.getEditText().getText().toString();
                mPresenter.login(email, password);
                break;

            default:

                break;
        }
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoginProDialog() {
        mProgressDialog = ProgressDialog.show(this, getString(R.string.login), getString(R.string.login_doing));
    }

    @Override
    public void hideLoginProDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showEmailError() {
        mNameEdit.setError(getString(R.string.email_input_hint));
    }

    @Override
    public void showPassError() {
        mPasswordEdit.setError(getString(R.string.password_input_hint));
    }

    @Override
    public void closeActivity() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
