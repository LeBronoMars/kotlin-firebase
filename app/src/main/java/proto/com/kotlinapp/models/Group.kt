package proto.com.kotlinapp.models

import android.os.Parcel
import android.os.Parcelable
import proto.com.kotlinapp.commons.AdapterConstants
import proto.com.kotlinapp.interfaces.ViewType

/**
 * Created by rsbulanon on 5/23/17.
 */
class Group : ViewType, Parcelable {
    var id: String = ""

    var groupName: String = ""

    var groupDescription: String = ""

    var membersCount: Int = 0

    var members: List<Member> = emptyList()

    constructor()

    constructor(groupName: String, groupDescription: String, membersCount: Int,
            members: List< Member >) {
        this.groupName = groupName
        this.groupDescription = groupDescription
        this.membersCount = membersCount
        this.members = members
    }

    override fun getViewType(): Int = AdapterConstants.GROUPS

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Group> = object : Parcelable.Creator<Group> {
            override fun createFromParcel(source: Parcel): Group = Group(source)
            override fun newArray(size: Int): Array<Group?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {}
}