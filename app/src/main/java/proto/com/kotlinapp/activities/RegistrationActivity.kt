package proto.com.kotlinapp.activities

import android.os.Bundle
import android.view.MenuItem
import proto.com.kotlinapp.R
import proto.com.kotlinapp.base.BaseActivity

class RegistrationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        setToolbarTitle("Registration", true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            animateToRight()
        }
        return super.onOptionsItemSelected(item)
    }
}
