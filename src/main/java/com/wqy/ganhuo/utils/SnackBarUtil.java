package com.wqy.ganhuo.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by weiquanyun on 16/3/14.
 */
public class SnackBarUtil {

    /**
     * 不带Action的短SnackBar
     * @param view
     * @param sequence
     */
    public static Snackbar makeShort(View view, CharSequence sequence) {
        return Snackbar.make(view, sequence, Snackbar.LENGTH_SHORT);
    }

    /**
     * 不带Action的长SnackBar
     * @param view
     * @param sequence
     */
    public static Snackbar makeLong(View view, CharSequence sequence) {
        return Snackbar.make(view, sequence, Snackbar.LENGTH_LONG);
    }

    /**
     * 带Action的长SnackBar
     * @param view
     * @param sequence
     * @param action
     * @param actionListener
     */
    public static Snackbar makeLong(View view, CharSequence sequence, CharSequence action, View.OnClickListener actionListener) {
        return Snackbar.make(view, sequence, Snackbar.LENGTH_LONG).setAction(action, actionListener);
    }

    /**
     * 不带Action的永久SnackBar
     * @param view
     * @param sequence
     */
    public static Snackbar makeIndefinite(View view, CharSequence sequence) {
        return Snackbar.make(view, sequence, Snackbar.LENGTH_INDEFINITE);
    }

    /**
     * 带Action的永久SnackBar
     * @param view
     * @param sequence
     * @param action
     * @param actionListener
     */
    public static Snackbar makeIndefinite(View view, CharSequence sequence, CharSequence action, View.OnClickListener actionListener) {
        return Snackbar.make(view, sequence, Snackbar.LENGTH_INDEFINITE).setAction(action, actionListener);
    }
}
