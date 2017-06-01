package proto.com.kotlinapp.base

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import proto.com.kotlinapp.R
import proto.com.kotlinapp.interfaces.OnConfirmDialogListener

/**
 * Created by rsbulanon on 5/23/17.
 */
abstract class BaseActivity : AppCompatActivity() {

    companion object {
        var progressDialog: ProgressDialog? = null
    }

    fun setError(textView: TextView, message: String) {
        textView.setError(message, null)
        textView.requestFocus()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showProgressDialog(title: String?, message: String?, cancelable: Boolean) {
        progressDialog?.let {
            progressDialog = null
        }

        progressDialog = ProgressDialog(this)

        title?.let {
            progressDialog!!.setTitle(title)
        }

        message?.let {
            progressDialog!!.setMessage(message)
        }

        progressDialog?.apply {
            setCancelable(cancelable)
            show()
        }
    }

    fun dismissProgressDialog() {
        progressDialog?.apply {
            dismiss()
        }
    }

    fun animateToRight() {
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right)
    }

    fun animateToBottom() {
        overridePendingTransition(R.anim.in_from_top, R.anim.out_to_bottom)
    }

    fun animateToLeft() {
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left)
    }

    fun animateToUp() {
        overridePendingTransition(R.anim.in_from_bottom, R.anim.out_to_top)
    }

    fun moveToOtherActivity(clz: Class<*>) {
        val intent = Intent(this, clz)
        startActivity(intent)
        animateToLeft()
    }

    fun setToolbarTitle(title: String?, showHomeEnabled: Boolean) {
        title?.let {
            supportActionBar!!.title = title
        }
        supportActionBar!!.setDisplayHomeAsUpEnabled(showHomeEnabled)
        supportActionBar!!.setDisplayShowHomeEnabled(showHomeEnabled)
    }

    fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        activeNetwork?.let {
            return (activeNetwork.type == ConnectivityManager.TYPE_WIFI
                    || activeNetwork.type == ConnectivityManager.TYPE_MOBILE)
        }
        return false
    }

    fun showConfirmDialog(action: String?, title: String, message: String,
                          positiveText: String?, negativeText: String?,
                          onConfirmDialogListener: OnConfirmDialogListener?,
                          cancelable: Boolean) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(title)
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(cancelable)

        if (positiveText != null && !positiveText.isEmpty()) {
            alertDialogBuilder.setPositiveButton(positiveText) { dialog, id ->
                onConfirmDialogListener?.apply {
                    onConfirmed(action!!)
                }
                dialog.cancel()
            }
        }

        if (negativeText != null && !negativeText.isEmpty()) {
            alertDialogBuilder.setNegativeButton(negativeText) { dialog, id ->
                onConfirmDialogListener?.apply {
                    onCancelled(action!!)
                }
                dialog.cancel()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

}