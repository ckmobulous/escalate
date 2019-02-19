package com.escalate.retrofit;


import com.escalate.adapter.ChatHistoryModel;
import com.escalate.model.ChatResponseBean;
import com.escalate.model.EmailResponce;
import com.escalate.model.FollowReponce;
import com.escalate.model.GenerResponce;
import com.escalate.model.LikeResponce;
import com.escalate.model.LoginResponse;
import com.escalate.model.NotificationModel;
import com.escalate.model.OtpVerifyResponce;
import com.escalate.model.PostAudioResponce;
import com.escalate.model.PostListResponce;
import com.escalate.model.ReplyCommentResponse;
import com.escalate.model.ReplyPostList;
import com.escalate.model.ReportedUserModel;
import com.escalate.model.SampleResponce;
import com.escalate.model.SignUpResponce;
import com.escalate.model.SocialLoginResponce;
import com.escalate.model.TopSearchResponce;
import com.escalate.model.TopicDetailModel;
import com.escalate.model.UpdateGenreResponce;
import com.escalate.model.ViewProfileResponce;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by mobulous06 on 19/7/17.
 */
public interface ApiInterface {

    //SERVICE INFO: https://mobulous.app/escalate/api/info

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("username") String username,
                              @Field("password") String password,
                              @Field("deviceToken") String device_token,
                              @Field("deviceType") String device_type,
                              @Field("firebase_id") String firebase_id);

    @Multipart
    @POST("users")
    Call<SignUpResponce> normalSignUp(@PartMap Map<String, RequestBody> partMapData);

    @Multipart
    @POST("users")
    Call<SignUpResponce> fbSignUp(@PartMap Map<String, RequestBody> partMapData, @Part MultipartBody.Part file);


    @Multipart
    @POST("check_back")
    Call<SampleResponce> check_avalibility(@PartMap Map<String, RequestBody> partMapData);

    @FormUrlEncoded
    @POST("social_login")
    Call<SocialLoginResponce> login_social(@Field("socialid") String socialid,
                                           @Field("deviceToken") String deviceToken,
                                           @Field("deviceType") String deviceType);

    @GET("genre_list")
    Call<GenerResponce> genre_list();

    @GET("userdetail/{view_profile_user_id}/{login_user_id}")
    Call<ViewProfileResponce> view_profile(@Path("view_profile_user_id") String view_profile_user_id, @Path("login_user_id") String login_user_id);

    @Multipart
    @POST("users/{id}")
    Call<ViewProfileResponce> edit_attachment(@Path("id") String id,
                                              @PartMap Map<String, RequestBody> partMapData,
                                              @Part MultipartBody.Part file, @Part MultipartBody.Part audio);

    @FormUrlEncoded
    @POST("users_change_password/{id}")
    Call<LoginResponse> changePassword(@Path("id") String id,
                                       @Field("password_old") String password_old,
                                       @Field("password") String passwordCnf,
                                       @Field("password_confirmation") String password,
                                       @Field("token") String token);

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<EmailResponce> forgot_password(@Field("email") String email);

    @FormUrlEncoded
    @POST("verifyemailotp")
    Call<OtpVerifyResponce> verify_email_otp(@Field("email") String email, @Field("otp") String otp);


    @FormUrlEncoded
    @POST("setnewpass")
    Call<SampleResponce> reset_password(@Field("user_id") String user_id, @Field("password") String password,
                                        @Field("password_confirmation") String password_confirmation);

    @FormUrlEncoded
    @POST("getuseridbynumber")
    Call<OtpVerifyResponce> verify_email_otp(@Field("phone") String phone);

    @Multipart
    @POST("audio")
    Call<PostAudioResponce> post_audio(@PartMap Map<String, RequestBody> partMapData,
                                       @Part MultipartBody.Part duration);

    @Multipart
    @POST("commentOnAudio")
    Call<ReplyCommentResponse> commentOnAudioReq(@PartMap Map<String, RequestBody> partMapData,
                                                 @Part MultipartBody.Part duration);

    @GET("postlist/{user_id}")
    Call<PostListResponce> post_list(@Path("user_id") String id);

    @FormUrlEncoded
    @POST
    Call<PostListResponce> post_listHome(@Url String url,
                                         @Field("token") String token,
                                         @Field("user_id") String user_id);


    @GET("genre_listbyid/{id}")
    Call<GenerResponce> genre_list_by_id(@Path("id") String id);

    @GET("commentlist/{id}")
    Call<ReplyPostList> reply_list_by_audioid(@Path("id") String id);

    @FormUrlEncoded
    @POST("likepost")
    Call<LikeResponce> like_post(@Field("token") String socialid,
                                 @Field("user_id") String deviceToken,
                                 @Field("audio_id") String deviceType);

    @FormUrlEncoded
    @POST("update_topic")
    Call<UpdateGenreResponce> update_topic(@Field("topic_id") String topicIds,
                                           @Field("token") String token,
                                           @Field("user_id") String user_id);

    @GET("followersList/{id}/{id}")
    Call<FollowReponce> follower_list(@Path("id") String followerid, @Path("id") String userIdLogin);

    @GET("followingList/{id}/{id}")
    Call<FollowReponce> following_list(@Path("id") String followerid, @Path("id") String userIdLogin);

    @GET("audioListbypostnum/{profile_view_user_id}/{login_user_id}")
    Call<PostListResponce> post_list_by_id(@Path("profile_view_user_id") String followerid, @Path("login_user_id") String userid);

    @FormUrlEncoded
    @POST("follow")
    Call<SampleResponce> update_follower(@Field("follower_id") String follower_id,
                                         @Field("user_id") String user_id,
                                         @Field("token") String token);

    /*
              "requestKey": "api/DeletePost"
    *         'token'=>required
              'user_id'=>required
              'post_id'=>required
    * */
    @FormUrlEncoded
    @POST("DeletePost")
    Call<SampleResponce> deletePost(@Field("post_id") String post_id,
                                    @Field("user_id") String user_id,
                                    @Field("token") String token);

    /*
            "requestKey": "api/latlongupdater"
              lat : required
              log : required
              user_id : required
      * */
    @FormUrlEncoded
    @POST("latlongupdater")
    Call<SampleResponce> latLngUpdate(@Field("lat") String lat,
                                      @Field("log") String lng,
                                      @Field("user_id") String user_id);


    @GET("topprofile/{id}")
    Call<TopSearchResponce> top_profile_search(@Path("id") String id);

    @GET("topgenre")
    Call<TopSearchResponce> topicRequest();

    @GET("toptags")
    Call<TopSearchResponce> tagRequest();

    @FormUrlEncoded
    @POST("genrepostbyid")
    Call<TopicDetailModel> requestGenrePostbyId(@Field("token") String token,
                                                @Field("user_id") String user_id,
                                                @Field("topic_id") String topic_id);

    @FormUrlEncoded
    @POST("tagpostbyid")
    Call<TopicDetailModel> requestTagpostbyid(@Field("token") String token,
                                              @Field("user_id") String user_id,
                                              @Field("tag_id") String topic_id);

    @FormUrlEncoded
    @POST("reportuser")
    Call<ReportedUserModel> requestReportUser(@Field("token") String token,
                                              @Field("user_id") String user_id,
                                              @Field("reported_user_id") String reported_user_id);

    @GET("notifylist/{user_id}")
    Call<NotificationModel> requestnotifylist(@Path("user_id") String user_id);

    @GET("notificationtrigger/{user_id}")
    Call<ReportedUserModel> requestNotificationTrigger(@Path("user_id") String user_id);

    @GET("tandccontent")
    Call<ReportedUserModel> requesttandccontent();

    @GET("privacypolicycontent")
    Call<ReportedUserModel> requestPrivacypolicycontent();


    @Multipart
    @POST("sendmessage")
    Call<ChatResponseBean> requestSendmessage(@PartMap Map<String, RequestBody> partMapData,
                                              @Part MultipartBody.Part duration);

    @FormUrlEncoded
    @POST("getmsg")
    Call<ChatResponseBean> chatMsgHistory(@Field("sender_id") String sender_id,
                                          @Field("receiver_id") String receiver_id,
                                          @Field("token") String token);

    @FormUrlEncoded
    @POST("chatpage")
    Call<ChatHistoryModel> requestChatpage(@Field("user_id") String user_id, @Field("token") String token);

    @FormUrlEncoded
    @POST("hidepost")
    Call<LikeResponce> hide_post(@Field("user_id") String user_id,
                                 @Field("token") String deviceToken,
                                 @Field("postuploder_id") String postuploder_id);
    @FormUrlEncoded
    @POST("submitquery")
    Call<EmailResponce> submitqueryReq(@Field("msg") String msg,@Field("user_id") String user_id,@Field("token") String token);

}
