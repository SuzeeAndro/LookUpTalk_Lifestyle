package com.lb.utils

import android.content.Context
import android.content.SharedPreferences


class UserSession(// Context


    private val _context: Context
) {
    // Shared Preferences reference
    internal var pref: SharedPreferences

    // Editor reference for Shared preferences
    private val editor: SharedPreferences.Editor

    // Shared preferences mode
    internal var PRIVATE_MODE = 0

    var acceptDataPrivacy: Boolean
        get() = pref.getBoolean(IS_DATA_PRIVACY, false)
        set(value) {
            editor.putBoolean(IS_DATA_PRIVACY, value)
            editor.apply()
        }

    var profileAccept: Boolean
        get() = pref.getBoolean(VIEW_PROFILE_ACCEPT, false)
        set(value) {
            editor.putBoolean(VIEW_PROFILE_ACCEPT, value)
            editor.apply()
        }

    //saving user data
    var userData: String?
        get() = pref.getString(USER_DATA, "")
        set(userData) {
            editor.putString(USER_DATA, userData)
            editor.apply()
        }

    // Check for login
    val isUserLoggedIn: Boolean
        get() = pref.getBoolean(IS_USER_LOGIN, false)

    var userLanguageCode: String?
        get() = pref.getString(USER_LANGUAGE_CODE, "")
        set(language) {
            editor.putString(USER_LANGUAGE_CODE, language)
            editor.apply()
        }

    var userLanguage: String?
        get() = pref.getString(USER_LANGUAGE, "")
        set(language) {
            editor.putString(USER_LANGUAGE, language)
            editor.apply()
        }

    val registrationNumber: String?
        get() = pref.getString(REGISTRATION_NUMBER, "")

    var languageID: String?
        get() = pref.getString(LANGUAGE_SELECTED_POSITION, "")
        set(number) {
            editor.putString(LANGUAGE_SELECTED_POSITION, number)
            editor.apply()
        }

    var signupData: String?
        get() = pref.getString(SIGNUP_DATA, "")
        set(signupData) {
            editor.putString(SIGNUP_DATA, signupData)
            editor.apply()
        }

    /*Language Code*/
    var languageCode: String?
        get() = pref.getString(LANGUAGE_CODE, "")
        set(code) {
            editor.putString(LANGUAGE_CODE, code)
            editor.apply()
        }

    /*Language Name*/
    var languageName: String?
        get() = pref.getString(LANGUAGE_NAME, "")
        set(code) {
            editor.putString(LANGUAGE_NAME, code)
            editor.apply()
        }

    /*Guest Login Saves*/

    var guestName: String?
        get() = pref.getString(GUEST_NAME, "")
        set(number) {
            editor.putString(GUEST_NAME, number)
            editor.apply()
        }

    /*Save Token*/

    /*Get Fcm Token*/
    fun setToken(value: String) {
        editor.putString(SET_TOKEN, value)
        editor.apply()
    }


    /*Get Fcm Token*/
    fun getToken(value: String) {
        editor.putString(SET_TOKEN, value)
        editor.apply()
    }

    /*Remove Fcm Token*/
    fun removeToken() {
        editor.remove(SET_TOKEN)
        editor.apply()
    }

    /*Save userID*/
    var userPhone: String?
        get() = pref.getString(USER_PHONE, "")
        set(user_phone) {
            editor.putString(USER_PHONE, user_phone)
            editor.apply()
        }

    /*Guest LOGIN*/
    var saveEvdata: String?
        get() = pref.getString(GUEST_LOGIN, "")
        set(user_id) {
            editor.putString(GUEST_LOGIN, user_id)
            editor.apply()
        }

    /*User Details*/

    /* Details*/
    var userDetails: String?
        get() = pref.getString(USER_DETAILS, "")
        set(value) {
            editor.putString(USER_DETAILS, value)
            editor.apply()
        }

    /*SignUp*/

    var signUp: String?
        get() = pref.getString(SIGN_UP, "")
        set(value) {
            editor.putString(SIGN_UP, value)
            editor.apply()
        }

    var otp: String?
        get() = pref.getString(OTP_VALUE, "")
        set(value) {
            editor.putString(OTP_VALUE, value)
            editor.apply()
        }

    /* Details*/
    var modelData: String?
        get() = pref.getString(MODEL_DATA, "")
        set(value) {
            editor.putString(MODEL_DATA, value)
            editor.apply()
        }


    init {
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    //Create login session
    fun createUserLoginSession() {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true)
        // commit changes
        editor.apply()
    }

    fun setLoginDetails(value: String) {
        editor.putString(LOGINDETAILS, value)
        editor.apply()
    }

    fun getLoginDetails(): String {
        return pref.getString(LOGINDETAILS, "")!!
    }

    fun setTestdriveSlot(value: String) {
        editor.putString(TESTDRIVE_SLOT, value)
        editor.apply()
    }

    fun getTestdriveSlot(): String {
        return pref.getString(TESTDRIVE_SLOT, "")!!
    }

    fun removegetTestdriveSlot() {
        editor.remove(TESTDRIVE_SLOT)
        editor.apply()
    }

    fun setEvDashboard(value: String) {
        editor.putString(EV_DASHBOARD, value)
        editor.apply()
    }

    fun getEvDashboard(): String {
        return pref.getString(EV_DASHBOARD, "")!!
    }

    fun setEvFormData(value: String) {
        editor.putString(EVFROMDATA, value)
        editor.apply()
    }

    fun getEvFormData(): String {
        return pref.getString(EVFROMDATA, "")!!
    }

    fun removeEvFormData() {
        editor.remove(EVFROMDATA)
        editor.apply()
    }

    /*SaveReqID*/
    fun setReqId(value: String) {
        editor.putString(REQ_ID, value)
        editor.apply()
    }

    fun getReqId(): String {
        return pref.getString(REQ_ID, "")!!
    }

    fun removeReqId() {
        editor.remove(REQ_ID)
        editor.apply()
    }


    fun setSelectedStockDetails(value: String) {
        editor.putString(SELECTEDSTOCK, value)
        editor.apply()
    }

    fun getSelectedStockDetails(): String {
        return pref.getString(SELECTEDSTOCK, "")!!
    }

    fun setSellerProfileData(value: String) {
        editor.putString(SELLERPROFILES, value)
        editor.apply()
    }

    fun getSellerProfileData(): String {
        return pref.getString(SELLERPROFILES, "")!!
    }

    /*Open Task DMS*/
    fun setDMS_OpenTask(value: String) {
        editor.putString(OPENTASK, value)
        editor.apply()
    }

    fun getDMS_OpenTask(): String {
        return pref.getString(OPENTASK, "")!!
    }


    fun removeDMS_OpenTask() {
        editor.remove(OPENTASK)
        editor.apply()
    }


    /*Save PhoneNumber*/
    fun setPhoneNum(value: String) {
        editor.putString(PHONE_ID, value)
        editor.apply()
    }

    fun getPhoneNum(): String {
        return pref.getString(PHONE_ID, "")!!
    }

/*Vehicle ID*/
    fun removePhoneNum() {
        editor.remove(PHONE_ID)
        editor.apply()
    }


    fun setGoogleLogin(value: String) {
        editor.putString(GOOGLE_LOGIN, value)
        editor.apply()
    }

    fun getGoogleLogin(): String {
        return pref.getString(GOOGLE_LOGIN, "")!!
    }


    fun removeGoogleLogin() {
        editor.remove(GOOGLE_LOGIN)
        editor.apply()
    }

    /*Save Google Login*/
    fun setGoogleData(value: String) {
        editor.putString(GOOGLE_DATA, value)
        editor.apply()
    }

    fun getGoogleData(): String {
        return pref.getString(GOOGLE_DATA, "")!!
    }


    fun removeGoogleData() {
        editor.remove(GOOGLE_DATA)
        editor.apply()
    }


    /*Save Facebook Login*/
    fun setFBData(value: String) {
        editor.putString(FB_DATA, value)
        editor.apply()
    }

    fun getFBData(): String {
        return pref.getString(FB_DATA, "")!!
    }


    fun removeFBData() {
        editor.remove(FB_DATA)
        editor.apply()
    }


    /*Open Task DMS*/
    fun setGetStarted(value: String) {
        editor.putString(GET_STARTED, value)
        editor.apply()
    }

    fun getGetStarted(): String {
        return pref.getString(GET_STARTED, "")!!
    }

    fun removeGetStarted() {
        editor.remove(GET_STARTED)
        editor.apply()
    }

    /*LOGIN Number*/
    fun setUserBasicInfo(value: String) {
        editor.putString(USER_INFO, value)
        editor.apply()
    }


    fun getUserBasicInfo(): String {
        return pref.getString(USER_INFO, "")!!
    }

    fun removeUserBasicInfo() {
        editor.remove(USER_INFO)
        editor.apply()
    }

    /*NID*/
    fun setNID(value: String) {
        editor.putString(NID, value)
        editor.apply()
    }

    fun getNID(): String {
        return pref.getString(NID, "")!!
    }

    /*NEW CUST NID*/
    fun setNewCustNID(value: String) {
        editor.putString(NEW_Cust_NID, value)
        editor.apply()
    }

    fun getNewCustNID(): String {
        return pref.getString(NEW_Cust_NID, "")!!
    }


    /*Save DMS PROFILE*/
    fun setDMSProfileData(value: String) {
        editor.putString(DMS_PROFILE, value)
        editor.apply()
    }

    fun getDMSProfileData(): String {
        return pref.getString(DMS_PROFILE, "")!!
    }

    fun removeDMSProfileData(): String {
        return pref.getString(DMS_PROFILE, "")!!
    }

    /*BasicForm_Offline*/
    fun setBasicform(value: String) {
        editor.putString(EV_BASIC_FORM, value)
        editor.apply()
    }

    fun getBasicform(): String {
        return pref.getString(EV_BASIC_FORM, "")!!
    }

    fun removeBasicform() {
        editor.remove(EV_BASIC_FORM)
        editor.apply()
    }

    /*Interior Offline*/
    fun setnidnalue(value: String) {
        editor.putString(NID_VALUES, value)
        editor.apply()
    }

    fun getnidnalue(): String {
        return pref.getString(NID_VALUES, "")!!
    }

    fun removenidnalue() {
        editor.remove(NID_VALUES)
        editor.apply()
    }


    /*Exterior Offline*/
    fun setExteriorform(value: String) {
        editor.putString(EV_EXTERNAL_FORM, value)
        editor.apply()
    }

    fun getExteriorform(): String {
        return pref.getString(EV_EXTERNAL_FORM, "")!!
    }

    fun removeExteriorform() {
        editor.remove(EV_EXTERNAL_FORM)
        editor.apply()
    }


    /*TestDrive Offline*/
    fun setTestDriveform(value: String) {
        editor.putString(EV_TESTDRIVE_FORM, value)
        editor.apply()
    }

    fun getTestDriveform(): String {
        return pref.getString(EV_TESTDRIVE_FORM, "")!!
    }

    fun removeTestDriveform() {
        editor.remove(EV_TESTDRIVE_FORM)
        editor.apply()
    }



    /*RateCar Offline*/
    fun setEditInsurance(value: String) {
        editor.putString(EDIT_INSURANCE, value)
        editor.apply()
    }

    fun getEditInsurance(): String {
        return pref.getString(EDIT_INSURANCE, "")!!
    }

    fun removeEditInsurance() {
        editor.remove(EDIT_INSURANCE)
        editor.apply()
    }

    /*RateCar Offline*/
    fun setInsurance(value: String) {
        editor.putString(INSURANCE_CLICK, value)
        editor.apply()
    }

    fun getInsurance(): String {
        return pref.getString(INSURANCE_CLICK, "")!!
    }

    fun removeInsurance() {
        editor.remove(INSURANCE_CLICK)
        editor.apply()
    }

    fun setBookingServivce(value: String) {
        editor.putString(BOOKING_SERVICE, value)
        editor.apply()
    }

    fun getBookingServivce(): String {
        return pref.getString(BOOKING_SERVICE, "")!!
    }

    fun removeBookingServivce() {
        editor.remove(BOOKING_SERVICE)
        editor.apply()
    }

    fun setFinanceSuccess(value: String) {
        editor.putString(FINANCE_SUCCESS, value)
        editor.apply()
    }

    fun getFinanceSuccess(): String {
        return pref.getString(FINANCE_SUCCESS, "")!!
    }

    fun removeFinanceSuccess() {
        editor.remove(FINANCE_SUCCESS)
        editor.apply()
    }

    fun setDashboard_details(value: String) {
        editor.putString(DASHBOARD_ITEM, value)
        editor.apply()
    }

    fun getDashboard_details(): String {
        return pref.getString(DASHBOARD_ITEM, "")!!
    }

    fun removeDashboard_details() {
        editor.remove(DASHBOARD_ITEM)
        editor.apply()
    }


    companion object {

        // Shared preferences file location_name
        private val PREFER_NAME = "VAS"


        private val USER_LANGUAGE_CODE = "language"
        private val USER_LANGUAGE = "language_user"
        private val IS_USER_LOGIN = "login"
        private val IS_DATA_PRIVACY = "data_privacy"
        private val VIEW_PROFILE_ACCEPT = "VIEW_PROFILE_ACCEPT"
        private val REGISTRATION_NUMBER = "registration"
        private val LANGUAGE_SELECTED_POSITION = "position"
        private val LANGUAGE_CODE = "language_code"
        private val LANGUAGE_NAME = "language_name"
        private val USER_DATA = "user_data"
        private val SIGNUP_DATA = "signup_data"
        private val GUEST_NAME = "guest_name"
        private val SET_TOKEN = "token"
        private val USER_PHONE = "USER_PHONE"
        private val USER_DETAILS = "user_details"
        private val GUEST_LOGIN = "guest_login"
        private val EV_DASHBOARD = "ev_dashboard"
        private val SIGN_UP = "sign_up"
        private val OTP_VALUE = "otp"
        private val MODEL_DATA = "model_data"
        private val TESTDRIVE_SLOT = "testdrive_slot"
        private val INSURANCE_CLICK = "insurance_click"
        private val EDIT_INSURANCE = "insurance_edit"
        private val POLICY_LIFE = "policy_life"


        private val LOGINDETAILS = "login_details"
        private val EVFROMDATA = "ev_form_data"
        private val REQ_ID = "req_id"
        private val SELECTEDSTOCK = "selected_stock_details"
        private val OPENTASK = "open_task"
        private val GET_STARTED = "GET_STARTED"
        private val NID = "nid"
        private val NEW_Cust_NID = "new_cust_nid "
        private val USER_INFO = "user_info "
        private val DMS_PROFILE = "dms_profile "
        private val SELLERPROFILES = "seller_details"
        private val PHONE_ID = "phone_ID"
        private val GOOGLE_LOGIN = "google_login"
        private val GOOGLE_DATA = "google_data"
        private val FB_DATA = "fb_data"


        private val EV_BASIC_FORM = "ev_basic_form"
        private val NID_VALUES = "nid_values"
        private val EV_EXTERNAL_FORM = "ev_external_form"
        private val EV_TESTDRIVE_FORM = "ev_testdrive_form"
        private val EV_FOLLOWUP_FORM = "ev_followup_form"
        private val EV_RATECAR_FORM = "ev_ratecar_form"
        private val EV_UPLOADPHOTO_FORM = "ev_upload_form"

        private val FINANCE_QUOTE = "finance_quote"
        private val FINANCE_SUCCESS = "finance_success"
        private val BOOKING_SERVICE = "booking_serice"
        private val DASHBOARD_ITEM = "dashboard_item"

//        internal var LOGINDETAILS: String? = null
//        internal var OPENTASK: String? = null
//        internal var NID_VALUES: String? = null
//        internal var LOGIN_NUMBER: String? = null
//        internal var NID: String? = null
//        internal var NEW_Cust_NID: String? = null
    }


}
