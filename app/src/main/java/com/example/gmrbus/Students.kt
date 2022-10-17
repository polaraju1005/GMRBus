package com.example.gmrbus

class Students {
    private var uid: String = ""
    private var username: String = ""
    private var email: String = ""
    private var parentPhone: String = ""
    private var phone: String = ""
    private var yos: String = ""
    private var department: String = ""
    private var coordinator: String = ""
    private var password: String = ""

    constructor()
    constructor(
        uid: String,
        username: String,
        email: String,
        parentPhone: String,
        phone: String,
        yos: String,
        department: String,
        coordinator: String,
        password: String
    ) {
        this.uid = uid
        this.username = username
        this.email = email
        this.parentPhone = parentPhone
        this.phone = phone
        this.yos = yos
        this.department = department
        this.coordinator = coordinator
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

    fun getParentPhone(): String? {
        return parentPhone
    }

    fun setParentPhone(parentPhone: String) {
        this.parentPhone = parentPhone
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }

    fun getYos(): String? {
        return yos
    }

    fun setYos(yos: String) {
        this.yos = yos
    }

    fun getDepartment(): String? {
        return department
    }

    fun setDepartment(department: String) {
        this.department = department
    }

    fun getCoordinator(): String? {
        return coordinator
    }

    fun setCoordinator(coordinator: String) {
        this.coordinator = coordinator
    }
}