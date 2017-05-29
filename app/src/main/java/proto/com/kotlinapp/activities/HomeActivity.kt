package proto.com.kotlinapp.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import proto.com.kotlinapp.R
import proto.com.kotlinapp.adapters.GroupsAdapter
import proto.com.kotlinapp.base.BaseActivity
import proto.com.kotlinapp.commons.FirebaseConstants
import proto.com.kotlinapp.delegates.GroupDelegateAdapter
import proto.com.kotlinapp.fragments.CreateGroupDialogFragment
import proto.com.kotlinapp.models.Group
import proto.com.kotlinapp.models.Member
import java.util.*


class HomeActivity : BaseActivity(), CreateGroupDialogFragment.OnCreateGroupListener {

    private var fireBaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var dataReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //init groups recycler view
        rv_groups.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = GroupsAdapter(object : GroupDelegateAdapter.OnSelectGroupListener {
                override fun onSelectedGroup(group: Group, position: Int) {
                    val builderSingle = AlertDialog.Builder(context)
                    builderSingle.setTitle(group.groupName)

                    val arrayAdapter = ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice)
                    arrayAdapter.add("Update")
                    arrayAdapter.add("Manage Members")
                    arrayAdapter.add("Delete")

                    builderSingle.setAdapter(arrayAdapter) { dialog, position ->
                        if (position == 0) {
                            //update group details
                            val createDialogFragment: CreateGroupDialogFragment = CreateGroupDialogFragment
                                    .newInstance(group.groupName, group.groupDescription)
                            createDialogFragment.setOnCreateGroupListener(object : CreateGroupDialogFragment.OnCreateGroupListener {
                                override fun onCreateGroup(groupName: String, description: String) {
                                    createDialogFragment.dismiss()

                                }
                            })
                            createDialogFragment.show(supportFragmentManager, CreateGroupDialogFragment::class.qualifiedName)
                        } else if (position == 2) {
                            //delete
                            Log.d("delete", "must delete this group ${group.groupName}")
                            dataReference.child(FirebaseConstants.GROUP).child(group.groupName).removeValue()
                        }
                    }
                    builderSingle.setPositiveButton("cancel", { dialog, which -> dialog.dismiss() })
                    builderSingle.show()
                }
            })
        }

        //fetch groups from fire base
        dataReference.child(FirebaseConstants.GROUP).addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
                manageGroup(p0, "add")
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
                manageGroup(p0, "remove")
            }
        })

        //show create group dialog fragment
        fab_add_group.setOnClickListener {
            val createDialogFragment: CreateGroupDialogFragment = CreateGroupDialogFragment.newInstance(null, null)
            createDialogFragment.setOnCreateGroupListener(object : CreateGroupDialogFragment.OnCreateGroupListener {
                override fun onCreateGroup(groupName: String, description: String) {
                    createDialogFragment.dismiss()
                    fireBaseAuth.currentUser?.apply {
                        val members: ArrayList<Member> = ArrayList()
                        members.add(Member(uid, displayName, photoUrl.toString()))
                        //val group: Group = Group(groupName, description, 1, members)
                        val group: Group = Group()
                        group.groupName = groupName
                        group.groupDescription = description
                        group.membersCount = 1
                        group.members = members

                        dataReference.child(FirebaseConstants.GROUP).child(groupName)
                                .addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onCancelled(p0: DatabaseError?) {
                                    }

                                    override fun onDataChange(p0: DataSnapshot?) {
                                        when (p0?.exists()) {
                                            true -> showToast("Group already exist")
                                            false -> dataReference.child(FirebaseConstants.GROUP).child(groupName).setValue(group)
                                        }
                                    }
                                })
                    }
                }
            })
            createDialogFragment.show(this.supportFragmentManager, CreateGroupDialogFragment::class.qualifiedName)
        }
    }

    fun moveToHomeScreen() {
        val intent: Intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onCreateGroup(groupName: String, groupDescription: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun manageGroup(group: DataSnapshot?, action: String) {
        when (group?.exists()) {
            true -> {
                group?.let {
                    val group: Group = group.getValue(Group::class.java)
                    rv_groups?.let {
                        if (action.equals("add")) {
                            (rv_groups.adapter as GroupsAdapter).addGroup(group)
                        } else if (action.equals("remove")) {
                            Log.d("delete", "must remove group")
                            (rv_groups.adapter as GroupsAdapter).removeGroup(group)
                        }
                    }
                }
            }
            false -> Log.d("group", "groups not existing")
        }
    }
}
