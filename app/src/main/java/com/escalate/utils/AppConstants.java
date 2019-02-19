package com.escalate.utils;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Rajeev Kr. Sharma [rajeevrocker7@gmail.com] on 27/6/18.
 */

public interface AppConstants extends Serializable {


    String kAppPreferences = "EscalatePreferences";
    String kDefaultAppName = "Escalate";

    String kCurrentUser = "currentUser";
    String kData = "data";
    String kStatus = "status";
    String kMessage = "message";
    String kMsg = "msg";
    String kSUCCESS = "SUCCESS";
    String kFAILURE = "FAILURE";

    String kSeparator = "__";
    String kEmptyString = "";
    String kWhitespace = " ";
    Number kEmptyNumber = 0;

    String kMessageInternalInconsistency = "Some internal inconsistency occurred. Please try again.";
    String kMessageNetworkError = "Device does not connect to internet.";
    String kSocketTimeOut = kDefaultAppName + " Server not responding! Time Out.";
    String kMessageServerNotRespondingError = kDefaultAppName + " Server not responding!";
    String kMessageConnecting = "Connecting...";
    String kMessageSomeWentWrong = "Something went wrong!";
    String kError = "Error";
    String kMakeSureInternet = "Make sure you are connected to Internet.";


    /*  SERVICE INFO: https://mobulous.app/healthapp/api/info */

    /*******
     * SERVICES (API) NAME CONSTANTS:
     *****/
    String kUserSignUp = "users";
    String kCheckUserOTP = "userotp";
    String kUserLogin = "login";
    String kDoctorLogin = "login";
    String kCitySelect = "city_update";
    String kCitySelectDoctor = "city_update_doctor";
    String kSpecialityList = "specialty";
    String kDoctorSignUp = "doctor";
    String kPostFeedNews = "postnews";
    String kPostFeedNewsList = "newsfeedlist";
    String kPostLikeUnlike = "likepost";
    String kPostSaveUnSave = "savepost";
    String kCommentOnPost = "commentOnPost";
    String kCommentList = "Commentlist";
    String kViewUserProfile = "viewUserProfile";
    String kEditUserProfile = "editUserProfile";
    String kSavedPostList = "savepostlist";
    String kYourPostList = "postlistbyusertypeid";
    String kDeletePost = "deletepost";
    String kChangePassword = "changepass";
    String kViewDocProfile = "viewdoctorprofile";
    String kEditDocProfile = "editdoctorprofile";
    String kAllCity = "all_city";
    String kSpecialityListByCity = "specialtiesbycity";
    String kDocClinicList = "dorcname";
    String kSearchDocList = "doctordetaillist";
    String kDocDetails = "fulldoctordetails";
    String kSubmitRatingReview = "submitReview";
    String kCancelReasonsList = "reasonlist";
    String kIssuesList = "issuelist";
    String kSubmitCancelReason = "reasonSubmit";
    String kNotificationList = "notifylist";
    String kBookingRequestByUser = "notifyfromuser";
    String kBookingConfirmByDoc = "notifyfromdoctor";
    String kBookingsList = "bookinglist";
    String kNotificationONOFF = "notificationtrigger";
    String kForgotPassword = "forgotpass";
    String kRatingReviewsDoc = "reviewandratting";




    /******
     * SERVICES (API) PARAMETERS CONSTANTS:
     *****/
    String kName = "name";
    String kToken = "token";
    String kRequestKey = "requestKey";
    String kIsFirstTime = "isFirstTime";
    String kRememberMe = "rememberMe";

    String kUserType = "usertype";
    String kUserData = "data";
    String kSpecialities = "specialties";

    //login, Sign up : users
    String kId = "id";
    String kUserId = "user_id";
    String kDoctorId = "doctor_id";
    String kCityId = "city_id";
    String kFullName = "fullname";
    String kPhone = "phone";
    String kOtpCode = "otp";
    String kCountryCode = "country_code";
    String kEmail = "email";
    String kPassword = "password";
    String kOldPassword = "oldpassword";
    String kConfirmPassword = "password_confirmation";
    String kDeviceType = "deviceType";
    String kDeviceToken = "deviceToken";
    String kCreatedAt = "created_at";
    String kUpdateAt = "updated_at";

    //city select
    String kCityName = "city_name";


    //sign up : doctor
    String kSpeciality_Id = "speciality_id";
    String kSpeciality_Other = "the_other_speciality";
    String kClinicName = "clinic";
    String kLicenceNum = "licence_number";
    String kExpertiseArea = "expertise_area";
    String kInsuranceAccepted = "insurance_accept";

    //add feed post
    String kPostTitle = "title";
    String kBooking = "Booking";
    String kPostSource = "source";
    String kPostURL = "url";
    String kPostId = "post_id";
    String kCommentMsg = "message";
    String kImage = "image";
    String kLocationCityId = "city_id";
    String kQualification = "qualification";
    String kFees = "fees";
    String kStartTime = "start_time";
    String kEndTime = "end_time";
    String kAvailability = "avilability";
    String kDocClinicSearchName = "search_name";
    String kMin_Price_Range = "minrange";
    String kMax_Price_Range = "maxrange";
    String kInsuranceAcceptedCommaSep = "insurance";
    String kRatingCommaSep = "rating";
    String kRatings = "rating";
    String kReviews = "review";
    String kCancelReasonId= "reason_id";

    String kFirstName = "firstname";
    String kLastName = "lastname";
    String kAge = "age";
    String kGender = "gender";
    String kAppointmentDate= "apdate";
    String kAppointmentTime= "aptime";



    /**
     * Status Enumeration for Task Status
     */
    enum Status {
        success(0),
        fail(1),
        reachLimit(2),
        noChange(3),
        history(4),
        normal(5),
        discard(6);

        private int value;

        Status(int status) {
            this.value = status;
        }

        public static Status getStatus(int value) {
            for (Status status : Status.values()) {
                if (status.value == value) {
                    return status;
                }
            }
            return fail;
        }

        /**
         * To get Integer value of corresponding enum
         */
        public Integer getValue() {
            return this.value;
        }
    }

    /**
     * Http Status for Api Response
     */
    enum HTTPStatus {
        eSUCCESS("SUCCESS"),
        eFAILURE("FAILURE"),
        eERROR("ERROR");

        private String httpStatus;

        HTTPStatus(String httpStatus) {
            this.httpStatus = httpStatus;
        }

        public static HTTPStatus getStatus(String status) {
            for (HTTPStatus httpStatus : HTTPStatus.values()) {
                if (Objects.equals(httpStatus.httpStatus, status)) {
                    return httpStatus;
                }
            }
            return eERROR;
        }

        public String getValue() {
            return this.httpStatus;
        }


    }

    enum UserRegType {


        system("normal"),
        facebook("facebook"),
        google("gmail"),
        amazon("amazon"),
        twitter("twitter");

        private String value;

        UserRegType(String regType) {
            this.value = regType;
        }

        /**
         * Convert int to UserRegType Type
         */
        public static UserRegType getRegType(String value) {
            for (UserRegType regType : UserRegType.values()) {
                if (Objects.equals(regType.value, value)) {
                    return regType;
                }
            }
            return system;
        }

        /**
         * To get Integer value of corresponding enum
         */
        public String getValue() {
            return this.value;
        }
    }

    enum DeviceType {
        eiOS("ios"),
        eAndroid("android");

        private String value;

        DeviceType(String deviceType) {
            this.value = deviceType;
        }

        /**
         * Convert int to DeviceType Type
         */
        public static DeviceType getDeviceType(String value) {
            for (DeviceType deviceType : DeviceType.values()) {
                if (Objects.equals(deviceType.value, value)) {
                    return deviceType;
                }
            }
            return eAndroid;
        }

        /**
         * To get Integer value of corresponding enum
         */
        public String getValue() {
            return this.value;
        }
    }

    enum UserType {
        eNone("None"),
        ePatient("Patient"),
        eDoctor("Doctor");

        private String value;

        UserType(String userType) {
            this.value = userType;
        }

        /**
         * Convert int to DeviceType Type
         */
        public static UserType getUserType(String value) {
            for (UserType deviceType : UserType.values()) {
                if (Objects.equals(deviceType.value, value)) {
                    return deviceType;
                }
            }
            return ePatient;
        }

        /**
         * To get Integer value of corresponding enum
         */
        public String getValue() {
            return this.value;
        }
    }

    enum SelectedStatus {
        eNone("None"),
        eYes("Yes"),
        eNo("No");

        private String value;

        SelectedStatus(String userType) {
            this.value = userType;
        }

        /**
         * Convert int to DeviceType Type
         */
        public static SelectedStatus getUserType(String value) {
            for (SelectedStatus deviceType : SelectedStatus.values()) {
                if (Objects.equals(deviceType.value, value)) {
                    return deviceType;
                }
            }
            return eNo;
        }

        /**
         * To get Integer value of corresponding enum
         */
        public String getValue() {
            return this.value;
        }
    }


}
