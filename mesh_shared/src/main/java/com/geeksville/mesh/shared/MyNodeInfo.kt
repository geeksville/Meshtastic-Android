package com.geeksville.mesh.shared

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

// MyNodeInfo sent via special protobuf from radio
@Serializable
data class MyNodeInfo(
    val myNodeNum: Int,
    val hasGPS: Boolean,
    val region: String?,
    val model: String?,
    val firmwareVersion: String?,
    val couldUpdate: Boolean, // this application contains a software load we _could_ install if you want
    val shouldUpdate: Boolean, // this device has old firmware
    val currentPacketId: Long,
    val nodeNumBits: Int,
    val packetIdBits: Int,
    val messageTimeoutMsec: Int,
    val minAppVersion: Int
) : Parcelable {
    /** A human readable description of the software/hardware version */
    val firmwareString: String get() = "$model $region/$firmwareVersion"

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(myNodeNum)
        parcel.writeByte(if (hasGPS) 1 else 0)
        parcel.writeString(region)
        parcel.writeString(model)
        parcel.writeString(firmwareVersion)
        parcel.writeByte(if (couldUpdate) 1 else 0)
        parcel.writeByte(if (shouldUpdate) 1 else 0)
        parcel.writeLong(currentPacketId)
        parcel.writeInt(nodeNumBits)
        parcel.writeInt(packetIdBits)
        parcel.writeInt(messageTimeoutMsec)
        parcel.writeInt(minAppVersion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MyNodeInfo> {
        override fun createFromParcel(parcel: Parcel): MyNodeInfo {
            return MyNodeInfo(parcel)
        }

        override fun newArray(size: Int): Array<MyNodeInfo?> {
            return arrayOfNulls(size)
        }
    }


}