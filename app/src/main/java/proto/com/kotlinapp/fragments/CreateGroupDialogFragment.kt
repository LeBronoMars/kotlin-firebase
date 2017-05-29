package proto.com.kotlinapp.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import proto.com.kotlinapp.R
import proto.com.kotlinapp.base.BaseActivity
import proto.com.kotlinapp.commons.AppConstants

/**
 * Created by rsbulanon on 5/24/17.
 */
class CreateGroupDialogFragment : DialogFragment() {

    var listener: OnCreateGroupListener? = null
    var groupName: String? = null
    var groupDescription: String? = null

    companion object {
        fun newInstance(groupName: String?, groupDescription: String?) : CreateGroupDialogFragment {
            val fragment: CreateGroupDialogFragment = CreateGroupDialogFragment()
            fragment.groupName = groupName
            fragment.groupDescription = groupDescription
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view: View = activity.layoutInflater.inflate(R.layout.dialog_fragment_create_group, null)
        val dialog: Dialog = Dialog(activity)
        val tv_header = view.findViewById(R.id.tv_header) as TextView
        val et_group_name = view.findViewById(R.id.et_group_name) as EditText
        val et_group_description = view.findViewById(R.id.et_group_description) as EditText
        val tv_create_group = view.findViewById(R.id.tv_create_group) as TextView
        val baseActivity: BaseActivity = activity as BaseActivity

        groupName?.let {
            tv_header.text = resources.getString(R.string.update_group_header)
            tv_create_group.text = resources.getString(R.string.update_group)
            et_group_name.setText(groupName)
            et_group_name.setSelection(et_group_name.text.toString().length)
            et_group_description.setText(groupDescription)
        }

        tv_create_group.let {
            tv_create_group.setOnClickListener {
                val groupName: String = et_group_name.text.toString().trim()
                val groupNameDesc: String = et_group_description?.text.toString().trim()

                if (TextUtils.isEmpty(groupName)) {
                    baseActivity.setError(et_group_name, AppConstants.WARN_FIELD_REQUIRED)
                } else {
                    if (groupName == null) {
                        listener?.apply {
                            onCreateGroup(groupName, groupNameDesc)
                        }
                    } else {
                        if (groupName.equals(this.groupName) && groupNameDesc.equals(this.groupDescription)) {
                            baseActivity.showToast("There are no changes detected.")
                        } else {
                            listener?.apply {
                                onCreateGroup(groupName, groupNameDesc)
                            }
                        }
                    }
                }
            }
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return dialog
    }

    interface OnCreateGroupListener {
        fun onCreateGroup(groupName: String, description: String)
    }

    fun setOnCreateGroupListener(onCreateGroupListener: OnCreateGroupListener) {
        this.listener = onCreateGroupListener
    }
}