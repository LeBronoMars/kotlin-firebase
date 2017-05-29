package proto.com.kotlinapp.models.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import proto.com.kotlinapp.commons.createParcel

/**
 * Created by rsbulanon on 5/22/17.
 */
data class LoginResponse(@SerializedName("access_token") val accessToken: String,
                         @SerializedName("token_type") val tokenType: String,
                         @SerializedName("refresh_token") val refreshToken: String,
                         @SerializedName("expires_in") val expiresIn: Int,
                         @SerializedName("date_created") val dateCreated: Long,
                         @SerializedName("refresh_token_expires_in") val refreshTokenExpiresIn: Int,
                         val scope: String
                         ) : Parcelable {

    companion object {
        @JvmField @Suppress("unused")
        val CREATOR = createParcel { LoginResponse(it) }
    }

    constructor(parcelIn: Parcel) : this (
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readInt(),
            parcelIn.readLong(),
            parcelIn.readInt(),
            parcelIn.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.accessToken)
        dest.writeString(this.tokenType)
        dest.writeString(this.refreshToken)
        dest.writeInt(this.expiresIn)
        dest.writeLong(this.dateCreated)
        dest.writeInt(this.refreshTokenExpiresIn)
    }

    override fun describeContents() = 0
}