package proto.com.kotlinapp.base

import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast

/**
 * Created by rsbulanon on 5/23/17.
 */
abstract class BaseActivity : AppCompatActivity() {

    fun setError(textView: TextView, message: String) {
        textView.setError(message, null)
        textView.requestFocus()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}