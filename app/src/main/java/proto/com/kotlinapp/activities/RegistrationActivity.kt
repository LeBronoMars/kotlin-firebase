package proto.com.kotlinapp.activities

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registration.*
import proto.com.kotlinapp.R
import proto.com.kotlinapp.base.BaseActivity
import proto.com.kotlinapp.commons.AppConstants
import proto.com.kotlinapp.commons.FirebaseConstants
import proto.com.kotlinapp.models.Member

class RegistrationActivity : BaseActivity() {

    private var fireBaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var dataReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setToolbarTitle("Registration", true)

        btn_create.setOnClickListener {
            val email: String = et_email.text.toString().trim()
            val password: String = et_password.text.toString().trim()
            val displayName: String = et_display_name.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                setError(et_email, AppConstants.WARN_FIELD_REQUIRED)
            } else if (TextUtils.isEmpty(password)) {
                setError(et_password, AppConstants.WARN_FIELD_REQUIRED)
            } else if (TextUtils.isEmpty(displayName)) {
                setError(et_display_name, AppConstants.WARN_FIELD_REQUIRED)
            } else {
                val member: Member = Member(null, displayName, "http://www.gravatar.com/avatar/$email?d=identicon")
                if (isNetworkAvailable()) {
                    showProgressDialog("Registration", "Creating your account, Please wait...", false)
                    fireBaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener({ task ->
                                when (task.isSuccessful) {
                                    true -> {
                                        val user = task.result.user
                                        user?.let {
                                            val userProfileChangeRequest = UserProfileChangeRequest.Builder()
                                                    .setDisplayName(displayName)
                                                    .setPhotoUri(Uri.parse(member.picUrl))
                                                    .build()
                                            user!!.updateProfile(userProfileChangeRequest)
                                                    .addOnCompleteListener({ updateTask ->
                                                        when (updateTask.isSuccessful) {
                                                            true -> {
                                                                member.memberId = user!!.uid
                                                                dismissProgressDialog()
                                                                dataReference.child(FirebaseConstants.MEMBERS).child(member.memberId).setValue(member)
                                                                showToast("Congratulations, Your may now use your account.")
                                                                finish()
                                                                animateToRight()
                                                            }
                                                            false -> {
                                                                dismissProgressDialog()
                                                                showConfirmDialog(null, "Registration Failed", updateTask.exception!!.message!!, "Close", null, null, false)
                                                            }
                                                        }
                                                    })
                                        }
                                    }
                                    false -> {
                                        dismissProgressDialog()
                                        showConfirmDialog(null, "Registration Failed", task.exception!!.message!!, "Close", null, null, false)
                                    }
                                }
                            })
                } else {
                    showToast(AppConstants.WARN_CONNECTION)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            animateToRight()
        }
        return super.onOptionsItemSelected(item)
    }
}
