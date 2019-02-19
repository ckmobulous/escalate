package com.escalate.utils;

/**
 * Created by Rajeev Kr. Sharma [rajeevrocker7@gmail.com] on 27/6/18.
 */
public enum ParamEnum {

    NONE,
    FROM_WHERE("from_where"),
    IS_FROM_CAMERA("isFromCamera"),
    IS_USER_REMEMBER("IS_USER_REMEMBER"),
    IS_DOC_REMEMBER("IS_DOC_REMEMBER"),

    FROM_WHICH_SIGN_UP("from_which_sign_up"),

    FROM_SDP("from_sdp"),
    FROM_NOTIFICATION_CERTIFY("from_notification_certify"),
    FROM_WHICH_CERTIFY("from_which_certify"),
    FROM_SOCIETY_NAME("from_society_name"),
    FROM_DEVELOPERS("from_developers"),
    FROM_PROP_TYPE("from_property_type"),

    SOCIETY_NAME("society_name"),
    DEV_NAME("dev_name"),
    PUSH_CHAT("push_chat"),
    FOLLOW_USER("FOLLOW_USER"),
    LIKES("LIKES"),
    COMMENT("COMMENT"),

    BUY("buy"),
    SELL("sell"),
    LEASE("lease"),
    RENT("rent"),

    SOCIETY_ID("society_id"),
    DEV_ID("dev_id"),
    RATE_REVIEWS("RATE_REVIEWS"),
    PROPERTY_TYPE_ID("property_type_id"),

    ORIGINAL_SIGN_UP("original_sign_up"),
    LOGIN("login"),
    SIGN_UP("SIGN_UP"),
    SUBSCRIPTION_PLAN_BEAN("SUBSCRIPTION_PLAN_BEAN"),
    USER_PROFILE("USER_PROFILE"),
    SIGN_UP_DOC("SIGN_UP_DOC"),
    FORGOT_PASS("forgot_pass"),
    OTHER_PROPERTY_SIGN_UP("sign_in_other_property_sign_up"),

    SIGN_UP_BUNDLE("signup_bundle"),
    OTP_CODE("otp_code"),
    REFERRAL_CODE("referral_code"),
    PROPERTY_ID_AT_WHICH_REQ_CAME("property_id_at_which_req_came"),


    DEVICE_TOKEN("device_token"),
    DEVICE_TYPE("device_type"),
    FULL_NAME("FULL_NAME"),
    F_NAME("f_name"),
    L_NAME("l_name"),
    COUNTRY_CODE("country_code"),
    CONTACT("contact"),
    FULL_CONTACT("full_contact"),
    MAIN_USER_CONTACT("main_user_contact"),
    EMAIL("email"),
    EMAIL_DOC("email_doc"),
    SMS("sms"),
    PASSWORD("password"),
    CONFIRM_PASSWORD("CONFIRM_PASSWORD"),
    PASSWORD_DOC("password_doc"),
    USER_IMAGE_FILE("user_image_file"),

    IS_RELATIVE("is_relative"),
    RELATIONSHIP("relationship"),

    NORMAL_USER("normal_user"),
    RELATIVE_OTHER_USER("relative_other_user"),

    ALL_VERSION("all_version"),
    FREE_RYP2_ONLY("free_ryp2_only"),

    ATTACHMENTS_VIEW("attachments_view"),
    ATTACH_FILE_NAME("attach_file_name"),
    ATTACH_FILE_URL("attach_file_url"),
    ATTACH_FILE_TYPE("attach_file_type"),
    FOR_WHAT_PROPERTY("for_what_property"),

    FROM_WHICH_WEB("from_which_web"),

    TC_POLICY_WEB_VIEW("tc_policy_web_view"),
    PRIVACY_POLICY_WEB_VIEW("privacy_policy_web_view"),
    ABOUT_US_WEB_VIEW("about_us_web_view"),
    CONTACT_US_WEB_VIEW("contact_us_web_view"),

    REFUND_POLICY_WEB_VIEW("refund_policy_web_view"),

    FAQ_WEB_VIEW("faq_web_view"),
    FB_WEB_VIEW("fb_web_view"),
    TWITTER_WEB_VIEW("twitter_web_view"),



    TC("terms_cond"),
    FAQ("faq"),
    FB("fb"),
    TWITTER("twitter"),
    P_POLICY("p_policy"),
    REFUND_POLICY("refund_policy"),
    JSON_DATA("json_data"),
    SHARE_USER_BEAN_LIST("share_user_bean_list"),

    URL_TO_LOAD("url_to_load"),
    REQ_FRAG_POP("req_frag_pop"),
    NOTIFICATION_FRAG_POP("notification_frag_pop"),


//    RYP1/RYP2/RYP3
    FREE_RYP1_VERSION("RYP1"),
    RYP2_VERSION("RYP2"),
    RYP3_VERSION("RYP3"),

    YES("yes"),
    NO("no"),


    EXPIRED_PLAN("expired_plan"),
    ACTIVE_PLAN("active_plan"),

    REG_MOBILE_NUMBER("reg_mobile_number"),
    TOKEN_SAVED("token_saved"),
    TOKEN("token"),

    HOME("Home"),
    HOME_DOC("HOME_DOC"),
    CANCELLATION("CANCELLATION"),
    HOME_DOC_POSTS("HOME_DOC_POSTS"),
    MAIN("Main"),
    POST_BEAN("post_bean"),
    POSITION("POSITION"),
    HOME_LIST("HOME_LIST"),
    NEWS_FEED_ADAPTER("NEWS_FEED_ADAPTER"),
    YOUR_POSTS("YOUR_POSTS"),
    NEWS_FEED("NEWS_FEED"),
    MY_BOOKING("MY_BOOKING"),
    DOC_BOOKINGS("DOC_BOOKINGS"),
    HOME_SEARCH("HOME_SEARCH"),
    HOME_CITY_SEARCH("HOME_CITY_SEARCH"),
    HOME_SPECIALITY_SEARCH("HOME_SPECIALITY_SEARCH"),
    HOME_CLINIC_AND_DOC_SEARCH("HOME_CLINIC_AND_DOC_SEARCH"),
    MY_SAVED_POST("MY_SAVED_POST"),
    NOTIFY_CERTIFICATION_COMPLETE("notify_certification_complete"),
    NOTIFY_LIST_BEAN("notify_list_bean"),
    NOTIFY_BOOKING_CONFIRM_TO_USER("NOTIFY_BOOKING_CONFIRM_TO_USER"),
    NOTIFY_BOOKING_REQ_TO_DOC("NOTIFY_BOOKING_REQ_TO_DOC"),
    NOTIFY_CHAT_TO_DOC("NOTIFY_CHAT_TO_DOC"),
    NOTIFY_CHAT_TO_USER("NOTIFY_CHAT_TO_USER"),
    MY_BUSINESS_DETAIL("my_business_detail"),
    B_DETAIL("b_detail"),
    MSGS_CHAT_ACT("msgs_chat_act"),


    B_TO_C("b_to_c"),
    C_TO_B("c_to_b"),
    B_TO_C_COUNT("b_to_c_count"),
    C_TO_B_COUNT("c_to_b_count"),
    TOTAL_MSG_COUNT("total_msg_count"),

    BUSINESS_ID("business_id"),
    BUSINESS_OWNER_ID("business_owner_id"),
    RECEIVER_ID("receiver_id"),
    MSG_TYPE("msg_type"),
    PUSH_TYPE("push_type"),
    SENDER_ID("sender_id"),
    SENDER_TYPE("sender_type"),
    SENDER_NAME("sender_name"),
    MESSAGE("message"),
    SENDER_IMAGE("sender_image"),
    TIME("time"),
    UNIQUE_ID_CHAT("unique_id"),
    FLAG_CHAT("flag"),
    DATE("DATE"),
    PUSH_BOOKING("PUSH_BOOKING"),
    PUSH_CERTIFICATION("push_certification"),
    PUSH_HOME_COUNT("push_home_count"),
    PUSH_B2C_COUNT("push_b2c_count"),
    PUSH_C2B_COUNT("push_c2b_count"),


    SCORE("score"),
    COLOR_CERTIFICATE("color_certificate"),
    USER_COUNTS_IN_SELL_REQ("user_counts_in_sell_req"),
    PROPERTY_FOR("property_for"),
//    SOCIETY_ID("society_id"),
    USER_ID("user_id"),
    PROPERTY_TYPE("property_type"),
    PROPERTY_ID("property_id"),
    PROPERTY_CERTIFY_ID("property_certify_id"),
    CERTIFICATION_ID_TO_SHARE_CERTIFICATE("certification_id_to_share_certificate"),
    DEVELOPER_ID("developer_id"),
    MAIN_PROPERTY_TYPE("main_property_type"),
    CERTIFICATION_STATUS("certification_status"),
    CERTIFICATION_ID("certification_id"),
    CITY_ID("CITY_ID"),
    SPECIALITY_ID("SPECIALITY_ID"),
    CITY_NAME("CITY_NAME"),
    SPECIALITY_NAME("SPECIALITY_NAME"),
    MSZ_DURATION("message_duration"),
    DOC_ID("DOC_ID"),
    DOC_DESC("DOC_DESC"),
    CLINIC_NAME("CLINIC_NAME"),
    IS_CERTIFY_CAPTURED("is_certify_captured"),
    IS_EMAIL_SENT("is_email_sent"),
    USER_VERSION("user_version"),
    SELECTED_VERSION_TYPE("selected_version_type"),
    MILESTONE_NUMBER("milestone_number"),

    //FROM WHERE TO CHAT
    CHAT_SR_USER_ID("CHAT_SR_USER_ID"),
    CHAT_SR_DOC_ID("CHAT_SR_DOC_ID"),

    DOC_DESC_ACT("DOC_DESC_ACT"),
    CHAT_FRAG_P("CHAT_FRAG_P"),
    CHAT_FRAG_DOC("CHAT_FRAG_DOC"),
    BOOKINGS_FRAG_P("BOOKINGS_FRAG_P"),
    BOOKINGS_FRAG_DOC("BOOKINGS_FRAG_DOC"),
    PUSH_NOTIFY("PUSH_NOTIFY"),

    NOT_TODAY("not_today"),
    YES_CREATE_BUSINESS_TODAY("yes_create_business_today"),
    FINAL_B_SERVICE_LIST("final_b_service_list"),
    FINAL_B_PRODUCT_LIST("final_b_product_list"),
    FINAL_B_BRANCHES_LIST("final_b_branches_list"),
    FINAL_B_DEPTS_LIST("final_b_depts_list"),
    FINAL_CATEGORIES_LIST("final_categories_list"),
    MESSAGES_LIST("messages_list"),
    ISSUES_LIST("ISSUES_LIST"),
    MESSAGE_POSITION("message_position"),

    BUSINESS_NAME("business_name"),
    BRANCHES_BEAN_LIST("branches_bean_list"),
    DEPTS_BEAN_LIST("depts_bean_list"),
    BRANCH_NAME("branch_name"),
    BRANCH_PHONE("branch_phone"),
    BRANCH_EMAIL("branch_email"),
    BRANCH_PHY_ADDR("branch_phy_addr"),
    BRANCH_POST_ADDR("branch_post_addr"),


    USER_IMAGE("user_image"),
    GENDER("gender"),
    GENDER_POSITION("gender_position"),
    IMAGE_BYTE_ARRAY("image_byte_array"),
    IMAGE_URI("image_uri"),

    SEARCH_TYPE("search_type"),
    SEARCH_TEXT("search_text"),
    CATEGORY("category"),

    PARENT_YES("parent_yes"),
    PARENT_NO("parent_no"),
    CHILD("child"),

    ABOUT_US("about_us"),
    CONTACT_US("contact_us"),

    LAT("lat"),
    LNG("lng"),
    LOCATION("location"),

    FROM_WHICH_SEARCH("from_search"),
    IS_BUSINESS_SEARCH("is_business_search"),
    IS_USER_SEARCH("is_user_search"),

    VALUE_FACTOR("value_factor"),
    SCORE_PERCENT("score_percent"),

    USE_DISCOUNT("use_discount"),
    YOUR_RYPRO_FRIENDS("your_rypro_friends"),

    VERIFY_OTP("verify_otp");


    //  Constructor
    ParamEnum(String v) {
        this.value = v;
    }

    //    No-arg constructor for 'NONE' Constant
    ParamEnum() {
        this.value = null;
    }

    private final String value;

    public String theValue() {
        return this.value;
    }


//    //TO GET PARAMETER
//    String name = ParamEnum.LOGIN.name();
//    ParamEnum parameters = ParamEnum.valueOf(name);
//
//    // TO GET PARAMETER's VALUE
//    String s = ParamEnum.LOGIN.theValue();
//
//    System.out.println("PARAMETER IS: "+parameters);  //LOGIN
//    System.out.println("THE VALUE OF PARAMETER IS: "+s);  //login

}
