package com.reradio.views;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.reradio.OnSearchListener;
import com.reradio.R;
import com.utils.DebugLogger;

public class InputFormScreenViewImpl extends RelativeLayout implements InputFormScreenView {

    private static final String TAG = InputFormScreenViewImpl.class.getSimpleName();

    private OnSearchListener mOnSearchListener;
    private TextInputLayout mTextInputLayout;

    public InputFormScreenViewImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
        DebugLogger.d(TAG, "InputFormScreenViewImpl - constructor");
        View view = View.inflate(context, R.layout.v_input_form, this);
        mTextInputLayout = (TextInputLayout) view.findViewById(R.id.input_layout);
        EditText editText = (EditText) view.findViewById(R.id.input_form);
        editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                startSearching(editText.getText());
                return true;
            } else {
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // empty
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // empty
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (SearchQueryValidator.isValidSearch(editable.toString()) || editable.length() == 0) {
                    mTextInputLayout.setErrorEnabled(false);
                } else {
                    mTextInputLayout.setError(getResources().getString(R.string.input_search_error));
                }
            }
        });
        Button button = (Button) view.findViewById(R.id.start_searching_button);
        button.setOnClickListener(view1 ->
                startSearching(editText.getText())
        );
    }

    private void startSearching(Editable editable) {
        if (SearchQueryValidator.isValidSearch(editable.toString())) {
            if (mOnSearchListener != null) {
                mOnSearchListener.onSearch(editable.toString());
                DebugLogger.d(TAG, "on search button clicked");
            }
            mTextInputLayout.setErrorEnabled(false);
        } else {
            mTextInputLayout.setError(getResources().getString(R.string.input_search_error));
        }
    }

    @Override
    public void setOnSearchListener(OnSearchListener onSearchListener) {
        DebugLogger.d(TAG, "setOnSearchListener: " + onSearchListener);
        mOnSearchListener = onSearchListener;
    }
}
