package com.example.gmrbus.models

class Users {
    private var uid: String = ""
    private var username: String = ""
    private var email: String = ""
    private var phone: String = ""
    private var password: String = ""
    private var busRoute: String = ""
    private var routeNumber:String = ""
    private var busNumber:String = ""

    constructor()
    constructor(
        uid: String,
        username: String,
        email: String,
        phone: String,
        password: String,
        busRoute: String,
        routeNumber: String,
        busNumber:String
    ) {
        this.uid = uid
        this.username = username
        this.email = email
        this.phone = phone
        this.password = password
        this.busRoute = busRoute
        this.routeNumber = routeNumber
        this.busNumber = busNumber
    }

    fun getUID(): String? {
        return uid
    }

    fun setUID(uid: String) {
        this.uid = uid
    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }

    fun getBusRoute(): String? {
        return busRoute
    }

    fun setBusRoute(busRoute: String) {
        this.busRoute = busRoute
    }

    fun getRouteNumber():String?{
        return routeNumber
    }

    fun setRouteNumber(routeNumber: String){
        this.routeNumber = routeNumber
    }

    fun getBusNumber():String?{
        return busNumber
    }

    fun setBusNumber(busNumber: String){
        this.busNumber = busNumber
    }

}