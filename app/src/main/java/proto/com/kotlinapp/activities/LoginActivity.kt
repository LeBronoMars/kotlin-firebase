package proto.com.kotlinapp.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import proto.com.kotlinapp.R
import proto.com.kotlinapp.base.BaseActivity
import proto.com.kotlinapp.commons.AppConstants

class LoginActivity : BaseActivity() {

    private var fireBaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fireBaseAuth.currentUser?.let {
            moveToHomeScreen()
        }

        btn_login.setOnClickListener {
            val email: String = et_email.text.toString().trim()
            val password: String = et_password.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                setError(et_email, AppConstants.WARN_FIELD_REQUIRED)
            } else if (TextUtils.isEmpty(password)) {
                setError(et_password, AppConstants.WARN_FIELD_REQUIRED)
            } else {
                fireBaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    when (task.exception) {
                        null -> when(task.isComplete) {
                            true -> moveToHomeScreen()
                            false -> Toast.makeText(this, "Unable to login, Please try again.", Toast.LENGTH_SHORT).show()
                        }
                        else -> Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun moveToHomeScreen() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}