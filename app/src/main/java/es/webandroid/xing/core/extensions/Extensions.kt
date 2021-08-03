package es.webandroid.xing.core.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import es.webandroid.xing.R

fun Fragment.hideKeyboard(activity: Activity) {
    val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    val currentFocus = activity.currentFocus
    if (currentFocus != null && inputManager != null) {
        inputManager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

fun ImageView.loadFromUrl(url: String?) = Picasso.get().load(url).placeholder(R.drawable.ic_error_placeholder).into(this)

fun Fragment.toast(text: String) = Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()

fun Activity.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Fragment.showModalInfo(title: Int, titleButton: Int, onPositive: () -> Unit): AlertDialog =
    MaterialAlertDialogBuilder(this.requireContext())
        .setTitle(title)
        .setPositiveButton(titleButton) { _, _ -> onPositive() }
        .setNegativeButton(this.getText(R.string.button_cancel_modal), null)
        .show()
