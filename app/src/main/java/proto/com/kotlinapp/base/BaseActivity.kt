package proto.com.kotlinapp.base

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast

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
}