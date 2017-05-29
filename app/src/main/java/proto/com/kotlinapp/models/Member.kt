package proto.com.kotlinapp.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by rsbulanon on 5/23/17.
 */
class Member : Parcelable {
    var memberId: String? = null

    var displayName: String? = null

    var picUrl: String? = null

    constructor()

    constructor(memberId: String?, displayName: String?, picUrl: String?) {
        this.memberId = memberId
        this.displayName = displayName
        this.picUrl = picUrl
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Member> = object : Parcelable.Creator<Member> {
            override fun createFromParcel(source: Parcel): Member = Member(source)
            override fun newArray(size: Int): Array<Member?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {}
}