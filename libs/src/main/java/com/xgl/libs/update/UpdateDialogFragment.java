package com.xgl.libs.update;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.xgl.libs.R;
import com.xgl.libs.base.BaseDialog;

/**
 * Description :
 * <p/>
 * Created by Chris Kyle on 2017/2/13.
 */
public final class UpdateDialogFragment extends BaseDialog {
    TextView versionName;
    TextView description;
    OnUpdateDialogListener onUpdateDialogListener;

    private static final String K_VERSION_NAME = "version.name";
    private static final String K_FILE_SIZE = "file.size";
    private static final String K_DESCRIPTION = "description";

    public static class UpdateDialogBuilder {

        private Bundle data;

        private OnUpdateDialogListener mOnUpdateDialogListener;

        public UpdateDialogBuilder() {
            data = new Bundle();
        }

        public UpdateDialogBuilder versionName(String versionName) {
            data.putString(K_VERSION_NAME, versionName);
            return this;
        }

        public UpdateDialogBuilder fileSize(String fileSize) {
            data.putString(K_FILE_SIZE, fileSize);
            return this;
        }

        public UpdateDialogBuilder description(String description) {
            data.putString(K_DESCRIPTION, description);
            return this;
        }

        public UpdateDialogBuilder updateDialogListener(OnUpdateDialogListener onUpdateDialogListener) {
            mOnUpdateDialogListener = onUpdateDialogListener;
            return this;
        }

        public UpdateDialogFragment create() {
            UpdateDialogFragment fragment = new UpdateDialogFragment();
            fragment.setArguments(data);
            fragment.setUpdateDialogListener(mOnUpdateDialogListener);

            return fragment;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return inflater.inflate(R.layout.fragment_dialog_update, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_FRAME, 0);

        String versionNameStr = getArguments().getString(K_VERSION_NAME);
        if (!TextUtils.isEmpty(versionNameStr)) {
            versionName.setText(versionNameStr);
        }

        String descriptionStr = getArguments().getString(K_DESCRIPTION);
        if (!TextUtils.isEmpty(descriptionStr)) {
            description.setText(Html.fromHtml(descriptionStr));
        }
    }

    @Override
    protected View getChildView() {
        return null;
    }

  /*  @OnClick(R.id.download)
    void downloadApk() {
        dismiss();

        if (null != onUpdateDialogListener) {
            onUpdateDialogListener.downloadApk();
        }
    }*/

  /*  @OnClick(R.id.cancel)
    void cancelUpdate() {
        dismiss();

        if (null != onUpdateDialogListener) {
            onUpdateDialogListener.cancelUpdate();
        }
    }*/

    public void setUpdateDialogListener(OnUpdateDialogListener onUpdateDialogListener) {
        this.onUpdateDialogListener = onUpdateDialogListener;
    }

    public interface OnUpdateDialogListener {

        void downloadApk();

        void cancelUpdate();
    }
}
