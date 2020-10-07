package com.sathish.carmap.data.model
import com.google.gson.annotations.SerializedName


class CarResponseApi : ArrayList<CarResponseApiItem>()

data class CarResponseApiItem(
    @SerializedName("batteryEstimatedDistance")
    val batteryEstimatedDistance: Int = 0, // 89
    @SerializedName("batteryPercentage")
    val batteryPercentage: Int = 0, // 73
    @SerializedName("id")
    val id: Int = 0, // 50
    @SerializedName("isCharging")
    val isCharging: Boolean = false, // false
    @SerializedName("location")
    val location: Location = Location(),
    @SerializedName("model")
    val model: Model = Model(),
    @SerializedName("plateNumber")
    val plateNumber: String = "", // TEST50
    @SerializedName("servicePlusEGoPoints")
    val servicePlusEGoPoints: Int = 0 // 0
)

data class Location(
    @SerializedName("address")
    val address: String = "", // Sporto gatvė 11
    @SerializedName("id")
    val id: Int = 0, // 0
    @SerializedName("latitude")
    val latitude: Double = 0.0, // 54.6948
    @SerializedName("longitude")
    val longitude: Double = 0.0 // 25.29991
)

data class Model(
    @SerializedName("id")
    val id: Int = 0, // 3
    @SerializedName("loyaltyPrize")
    val loyaltyPrize: Int = 0, // 0
    @SerializedName("photoUrl")
    val photoUrl: String = "", // https://s3-eu-west-1.amazonaws.com/rideshareuploads/uploads/16a68b02-7b19-4ad0-8b13-7a16fac4bbc6.jpeg
    @SerializedName("rate")
    val rate: Rate = Rate(),
    @SerializedName("title")
    val title: String = "" // VW e-Up!
)

data class Rate(
    @SerializedName("currency")
    val currency: String = "", // Euro
    @SerializedName("currencySymbol")
    val currencySymbol: String = "", // €
    @SerializedName("isWeekend")
    val isWeekend: Boolean = false, // true
    @SerializedName("lease")
    val lease: Lease = Lease(),
    @SerializedName("reservation")
    val reservation: Reservation = Reservation()
)

data class Lease(
    @SerializedName("freeKilometersPerDay")
    val freeKilometersPerDay: Int = 0, // 200
    @SerializedName("kilometerPrice")
    val kilometerPrice: Double = 0.0, // 0.1
    @SerializedName("weekends")
    val weekends: Weekends = Weekends(),
    @SerializedName("workdays")
    val workdays: Workdays = Workdays()
)

data class Reservation(
    @SerializedName("extensionMinutes")
    val extensionMinutes: Int = 0, // 1
    @SerializedName("extensionPrice")
    val extensionPrice: Int = 0, // 4
    @SerializedName("initialMinutes")
    val initialMinutes: Int = 0, // 3
    @SerializedName("initialPrice")
    val initialPrice: Int = 0, // 2
    @SerializedName("longerExtensionMinutes")
    val longerExtensionMinutes: Int = 0, // 2
    @SerializedName("longerExtensionPrice")
    val longerExtensionPrice: Int = 0 // 2
)

data class Weekends(
    @SerializedName("amount")
    val amount: Double = 0.0, // 0.15
    @SerializedName("dailyAmount")
    val dailyAmount: Int = 0, // 20
    @SerializedName("minimumMinutes")
    val minimumMinutes: Int = 0, // 13
    @SerializedName("minimumPrice")
    val minimumPrice: Double = 0.0, // 1.95
    @SerializedName("minutes")
    val minutes: Int = 0 // 1
)

data class Workdays(
    @SerializedName("amount")
    val amount: Double = 0.0, // 0.15
    @SerializedName("dailyAmount")
    val dailyAmount: Int = 0, // 28
    @SerializedName("minimumMinutes")
    val minimumMinutes: Int = 0, // 13
    @SerializedName("minimumPrice")
    val minimumPrice: Double = 0.0, // 1.95
    @SerializedName("minutes")
    val minutes: Int = 0 // 1
)