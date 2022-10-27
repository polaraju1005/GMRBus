package com.example.gmrbus

class Parents {
    private var uid: String = ""
    private var username: String = ""
    private var email: String =""
    private var phone: String = ""
    private var password: String = ""

    constructor()
    constructor(
        uid: String,
        username: String,
        email: String,
        phone: String,
        password: String,
    ){
        this.uid = uid
        this.username = username
        this.email = email
        this.phone = phone
        this.password = password
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

}