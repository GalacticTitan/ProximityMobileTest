package com.kbj.aqiindex.models
import android.graphics.Color
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class AQIBean (
	@PrimaryKey(autoGenerate = true) val id: Int?,
	@SerializedName("city") val city : String,
	@SerializedName("aqi") val aqi : Double,
	var color: Int?,
	var seconds: Long?,
	var lastUpdated: String?,
){
	override fun equals(other: Any?): Boolean {
		if(other == null){
			return false
		}
		if (other is AQIBean){
			return this.city == other.city
		}
		return super.equals(other)
	}

	override fun hashCode(): Int {
		var result = city.hashCode()
		result = 31 * result + aqi.hashCode()
		result = 31 * result + color.hashCode()
		result = 31 * result + lastUpdated.hashCode()
		return result
	}
}