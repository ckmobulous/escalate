package com.escalate;

import android.databinding.DataBinderMapper;
import android.databinding.DataBindingComponent;
import android.databinding.ViewDataBinding;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import com.escalate.databinding.ActivityChangePassBindingImpl;
import com.escalate.databinding.ActivityChatBindingImpl;
import com.escalate.databinding.ActivityCommentBindingImpl;
import com.escalate.databinding.ActivityForgotPasswordBindingImpl;
import com.escalate.databinding.ActivityHomeBindingImpl;
import com.escalate.databinding.ActivityLoginBindingImpl;
import com.escalate.databinding.ActivityNotificationBindingImpl;
import com.escalate.databinding.ActivityOtherProfileBindingImpl;
import com.escalate.databinding.ActivityPostQueriesBindingImpl;
import com.escalate.databinding.ActivityProceedBindingImpl;
import com.escalate.databinding.ActivityReplyBindingImpl;
import com.escalate.databinding.ActivityResetPassBindingImpl;
import com.escalate.databinding.ActivitySettingBindingImpl;
import com.escalate.databinding.ActivityTagDetailBindingImpl;
import com.escalate.databinding.ActivityTermsConditionBindingImpl;
import com.escalate.databinding.ActivityTopicDetailBindingImpl;
import com.escalate.databinding.ActivityUpdateUserBindingImpl;
import com.escalate.databinding.ChatListBindingImpl;
import com.escalate.databinding.CommentListHomeBindingImpl;
import com.escalate.databinding.ContentDialogBottomSheetBindingImpl;
import com.escalate.databinding.FragmentChatBindingImpl;
import com.escalate.databinding.FragmentHomeBindingImpl;
import com.escalate.databinding.FragmentLogInBindingImpl;
import com.escalate.databinding.FragmentPeopleBindingImpl;
import com.escalate.databinding.FragmentPostBindingImpl;
import com.escalate.databinding.FragmentRecentBindingImpl;
import com.escalate.databinding.FragmentSearchBindingImpl;
import com.escalate.databinding.FragmentSignUpBindingImpl;
import com.escalate.databinding.FragmentTagsBindingImpl;
import com.escalate.databinding.FragmentTopicBindingImpl;
import com.escalate.databinding.FragmentUserFrofileBindingImpl;
import com.escalate.databinding.InlcudeFilterLayoutBindingImpl;
import com.escalate.databinding.RecentSearchListBindingImpl;
import com.escalate.databinding.RecyclerChatListAdapterBindingImpl;
import com.escalate.databinding.RecyclerFollowerAdapterBindingImpl;
import com.escalate.databinding.RecyclerGenerAdapterBindingImpl;
import com.escalate.databinding.RecyclerHomeAdapterBindingImpl;
import com.escalate.databinding.RecyclerNotificationAdapterBindingImpl;
import com.escalate.databinding.RecyclerPeopleAdapterBindingImpl;
import com.escalate.databinding.RecyclerRepliesAdapterBindingImpl;
import com.escalate.databinding.SearchCategoryBindingImpl;
import com.escalate.databinding.SingleRowHomePostBindingImpl;
import com.escalate.databinding.SingleRowPostsBindingImpl;
import com.escalate.databinding.SingleRowRepliesPostBindingImpl;
import com.escalate.databinding.TagSearchListBindingImpl;
import com.escalate.databinding.TopicSearchAdapterBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYCHANGEPASS = 1;

  private static final int LAYOUT_ACTIVITYCHAT = 2;

  private static final int LAYOUT_ACTIVITYCOMMENT = 3;

  private static final int LAYOUT_ACTIVITYFORGOTPASSWORD = 4;

  private static final int LAYOUT_ACTIVITYHOME = 5;

  private static final int LAYOUT_ACTIVITYLOGIN = 6;

  private static final int LAYOUT_ACTIVITYNOTIFICATION = 7;

  private static final int LAYOUT_ACTIVITYOTHERPROFILE = 8;

  private static final int LAYOUT_ACTIVITYPOSTQUERIES = 9;

  private static final int LAYOUT_ACTIVITYPROCEED = 10;

  private static final int LAYOUT_ACTIVITYREPLY = 11;

  private static final int LAYOUT_ACTIVITYRESETPASS = 12;

  private static final int LAYOUT_ACTIVITYSETTING = 13;

  private static final int LAYOUT_ACTIVITYTAGDETAIL = 14;

  private static final int LAYOUT_ACTIVITYTERMSCONDITION = 15;

  private static final int LAYOUT_ACTIVITYTOPICDETAIL = 16;

  private static final int LAYOUT_ACTIVITYUPDATEUSER = 17;

  private static final int LAYOUT_CHATLIST = 18;

  private static final int LAYOUT_COMMENTLISTHOME = 19;

  private static final int LAYOUT_CONTENTDIALOGBOTTOMSHEET = 20;

  private static final int LAYOUT_FRAGMENTCHAT = 21;

  private static final int LAYOUT_FRAGMENTHOME = 22;

  private static final int LAYOUT_FRAGMENTLOGIN = 23;

  private static final int LAYOUT_FRAGMENTPEOPLE = 24;

  private static final int LAYOUT_FRAGMENTPOST = 25;

  private static final int LAYOUT_FRAGMENTRECENT = 26;

  private static final int LAYOUT_FRAGMENTSEARCH = 27;

  private static final int LAYOUT_FRAGMENTSIGNUP = 28;

  private static final int LAYOUT_FRAGMENTTAGS = 29;

  private static final int LAYOUT_FRAGMENTTOPIC = 30;

  private static final int LAYOUT_FRAGMENTUSERFROFILE = 31;

  private static final int LAYOUT_INLCUDEFILTERLAYOUT = 32;

  private static final int LAYOUT_RECENTSEARCHLIST = 33;

  private static final int LAYOUT_RECYCLERCHATLISTADAPTER = 34;

  private static final int LAYOUT_RECYCLERFOLLOWERADAPTER = 35;

  private static final int LAYOUT_RECYCLERGENERADAPTER = 36;

  private static final int LAYOUT_RECYCLERHOMEADAPTER = 37;

  private static final int LAYOUT_RECYCLERNOTIFICATIONADAPTER = 38;

  private static final int LAYOUT_RECYCLERPEOPLEADAPTER = 39;

  private static final int LAYOUT_RECYCLERREPLIESADAPTER = 40;

  private static final int LAYOUT_SEARCHCATEGORY = 41;

  private static final int LAYOUT_SINGLEROWHOMEPOST = 42;

  private static final int LAYOUT_SINGLEROWPOSTS = 43;

  private static final int LAYOUT_SINGLEROWREPLIESPOST = 44;

  private static final int LAYOUT_TAGSEARCHLIST = 45;

  private static final int LAYOUT_TOPICSEARCHADAPTER = 46;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(46);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_change_pass, LAYOUT_ACTIVITYCHANGEPASS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_chat, LAYOUT_ACTIVITYCHAT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_comment, LAYOUT_ACTIVITYCOMMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_forgot_password, LAYOUT_ACTIVITYFORGOTPASSWORD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_home, LAYOUT_ACTIVITYHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_notification, LAYOUT_ACTIVITYNOTIFICATION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_other_profile, LAYOUT_ACTIVITYOTHERPROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_post_queries, LAYOUT_ACTIVITYPOSTQUERIES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_proceed, LAYOUT_ACTIVITYPROCEED);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_reply, LAYOUT_ACTIVITYREPLY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_reset_pass, LAYOUT_ACTIVITYRESETPASS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_setting, LAYOUT_ACTIVITYSETTING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_tag_detail, LAYOUT_ACTIVITYTAGDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_terms_condition, LAYOUT_ACTIVITYTERMSCONDITION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_topic_detail, LAYOUT_ACTIVITYTOPICDETAIL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.activity_update_user, LAYOUT_ACTIVITYUPDATEUSER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.chat_list, LAYOUT_CHATLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.comment_list_home, LAYOUT_COMMENTLISTHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.content_dialog_bottom_sheet, LAYOUT_CONTENTDIALOGBOTTOMSHEET);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.fragment_chat, LAYOUT_FRAGMENTCHAT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.fragment_home, LAYOUT_FRAGMENTHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.fragment_log_in, LAYOUT_FRAGMENTLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.fragment_people, LAYOUT_FRAGMENTPEOPLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.fragment_post, LAYOUT_FRAGMENTPOST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.fragment_recent, LAYOUT_FRAGMENTRECENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.fragment_search, LAYOUT_FRAGMENTSEARCH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.fragment_sign_up, LAYOUT_FRAGMENTSIGNUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.fragment_tags, LAYOUT_FRAGMENTTAGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.fragment_topic, LAYOUT_FRAGMENTTOPIC);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.fragment_user_frofile, LAYOUT_FRAGMENTUSERFROFILE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.inlcude_filter_layout, LAYOUT_INLCUDEFILTERLAYOUT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.recent_search_list, LAYOUT_RECENTSEARCHLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.recycler_chat_list_adapter, LAYOUT_RECYCLERCHATLISTADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.recycler_follower_adapter, LAYOUT_RECYCLERFOLLOWERADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.recycler_gener_adapter, LAYOUT_RECYCLERGENERADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.recycler_home_adapter, LAYOUT_RECYCLERHOMEADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.recycler_notification_adapter, LAYOUT_RECYCLERNOTIFICATIONADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.recycler_people_adapter, LAYOUT_RECYCLERPEOPLEADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.recycler_replies_adapter, LAYOUT_RECYCLERREPLIESADAPTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.search_category, LAYOUT_SEARCHCATEGORY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.single_row_home_post, LAYOUT_SINGLEROWHOMEPOST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.single_row_posts, LAYOUT_SINGLEROWPOSTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.single_row_replies_post, LAYOUT_SINGLEROWREPLIESPOST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.tag_search_list, LAYOUT_TAGSEARCHLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.escalate.R.layout.topic_search_adapter, LAYOUT_TOPICSEARCHADAPTER);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYCHANGEPASS: {
          if ("layout/activity_change_pass_0".equals(tag)) {
            return new ActivityChangePassBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_change_pass is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCHAT: {
          if ("layout/activity_chat_0".equals(tag)) {
            return new ActivityChatBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_chat is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCOMMENT: {
          if ("layout/activity_comment_0".equals(tag)) {
            return new ActivityCommentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_comment is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYFORGOTPASSWORD: {
          if ("layout/activity_forgot_password_0".equals(tag)) {
            return new ActivityForgotPasswordBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_forgot_password is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYHOME: {
          if ("layout/activity_home_0".equals(tag)) {
            return new ActivityHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_home is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYNOTIFICATION: {
          if ("layout/activity_notification_0".equals(tag)) {
            return new ActivityNotificationBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_notification is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYOTHERPROFILE: {
          if ("layout/activity_other_profile_0".equals(tag)) {
            return new ActivityOtherProfileBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_other_profile is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPOSTQUERIES: {
          if ("layout/activity_post_queries_0".equals(tag)) {
            return new ActivityPostQueriesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_post_queries is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYPROCEED: {
          if ("layout/activity_proceed_0".equals(tag)) {
            return new ActivityProceedBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_proceed is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYREPLY: {
          if ("layout/activity_reply_0".equals(tag)) {
            return new ActivityReplyBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_reply is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYRESETPASS: {
          if ("layout/activity_reset_pass_0".equals(tag)) {
            return new ActivityResetPassBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_reset_pass is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYSETTING: {
          if ("layout/activity_setting_0".equals(tag)) {
            return new ActivitySettingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_setting is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYTAGDETAIL: {
          if ("layout/activity_tag_detail_0".equals(tag)) {
            return new ActivityTagDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_tag_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYTERMSCONDITION: {
          if ("layout/activity_terms_condition_0".equals(tag)) {
            return new ActivityTermsConditionBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_terms_condition is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYTOPICDETAIL: {
          if ("layout/activity_topic_detail_0".equals(tag)) {
            return new ActivityTopicDetailBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_topic_detail is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYUPDATEUSER: {
          if ("layout/activity_update_user_0".equals(tag)) {
            return new ActivityUpdateUserBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_update_user is invalid. Received: " + tag);
        }
        case  LAYOUT_CHATLIST: {
          if ("layout/chat_list_0".equals(tag)) {
            return new ChatListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for chat_list is invalid. Received: " + tag);
        }
        case  LAYOUT_COMMENTLISTHOME: {
          if ("layout/comment_list_home_0".equals(tag)) {
            return new CommentListHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for comment_list_home is invalid. Received: " + tag);
        }
        case  LAYOUT_CONTENTDIALOGBOTTOMSHEET: {
          if ("layout/content_dialog_bottom_sheet_0".equals(tag)) {
            return new ContentDialogBottomSheetBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for content_dialog_bottom_sheet is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTCHAT: {
          if ("layout/fragment_chat_0".equals(tag)) {
            return new FragmentChatBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_chat is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTHOME: {
          if ("layout/fragment_home_0".equals(tag)) {
            return new FragmentHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_home is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTLOGIN: {
          if ("layout/fragment_log_in_0".equals(tag)) {
            return new FragmentLogInBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_log_in is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTPEOPLE: {
          if ("layout/fragment_people_0".equals(tag)) {
            return new FragmentPeopleBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_people is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTPOST: {
          if ("layout/fragment_post_0".equals(tag)) {
            return new FragmentPostBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_post is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTRECENT: {
          if ("layout/fragment_recent_0".equals(tag)) {
            return new FragmentRecentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_recent is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSEARCH: {
          if ("layout/fragment_search_0".equals(tag)) {
            return new FragmentSearchBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_search is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTSIGNUP: {
          if ("layout/fragment_sign_up_0".equals(tag)) {
            return new FragmentSignUpBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_sign_up is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTTAGS: {
          if ("layout/fragment_tags_0".equals(tag)) {
            return new FragmentTagsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_tags is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTTOPIC: {
          if ("layout/fragment_topic_0".equals(tag)) {
            return new FragmentTopicBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_topic is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTUSERFROFILE: {
          if ("layout/fragment_user_frofile_0".equals(tag)) {
            return new FragmentUserFrofileBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_user_frofile is invalid. Received: " + tag);
        }
        case  LAYOUT_INLCUDEFILTERLAYOUT: {
          if ("layout/inlcude_filter_layout_0".equals(tag)) {
            return new InlcudeFilterLayoutBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for inlcude_filter_layout is invalid. Received: " + tag);
        }
        case  LAYOUT_RECENTSEARCHLIST: {
          if ("layout/recent_search_list_0".equals(tag)) {
            return new RecentSearchListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for recent_search_list is invalid. Received: " + tag);
        }
        case  LAYOUT_RECYCLERCHATLISTADAPTER: {
          if ("layout/recycler_chat_list_adapter_0".equals(tag)) {
            return new RecyclerChatListAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for recycler_chat_list_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_RECYCLERFOLLOWERADAPTER: {
          if ("layout/recycler_follower_adapter_0".equals(tag)) {
            return new RecyclerFollowerAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for recycler_follower_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_RECYCLERGENERADAPTER: {
          if ("layout/recycler_gener_adapter_0".equals(tag)) {
            return new RecyclerGenerAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for recycler_gener_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_RECYCLERHOMEADAPTER: {
          if ("layout/recycler_home_adapter_0".equals(tag)) {
            return new RecyclerHomeAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for recycler_home_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_RECYCLERNOTIFICATIONADAPTER: {
          if ("layout/recycler_notification_adapter_0".equals(tag)) {
            return new RecyclerNotificationAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for recycler_notification_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_RECYCLERPEOPLEADAPTER: {
          if ("layout/recycler_people_adapter_0".equals(tag)) {
            return new RecyclerPeopleAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for recycler_people_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_RECYCLERREPLIESADAPTER: {
          if ("layout/recycler_replies_adapter_0".equals(tag)) {
            return new RecyclerRepliesAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for recycler_replies_adapter is invalid. Received: " + tag);
        }
        case  LAYOUT_SEARCHCATEGORY: {
          if ("layout/search_category_0".equals(tag)) {
            return new SearchCategoryBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for search_category is invalid. Received: " + tag);
        }
        case  LAYOUT_SINGLEROWHOMEPOST: {
          if ("layout/single_row_home_post_0".equals(tag)) {
            return new SingleRowHomePostBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for single_row_home_post is invalid. Received: " + tag);
        }
        case  LAYOUT_SINGLEROWPOSTS: {
          if ("layout/single_row_posts_0".equals(tag)) {
            return new SingleRowPostsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for single_row_posts is invalid. Received: " + tag);
        }
        case  LAYOUT_SINGLEROWREPLIESPOST: {
          if ("layout/single_row_replies_post_0".equals(tag)) {
            return new SingleRowRepliesPostBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for single_row_replies_post is invalid. Received: " + tag);
        }
        case  LAYOUT_TAGSEARCHLIST: {
          if ("layout/tag_search_list_0".equals(tag)) {
            return new TagSearchListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for tag_search_list is invalid. Received: " + tag);
        }
        case  LAYOUT_TOPICSEARCHADAPTER: {
          if ("layout/topic_search_adapter_0".equals(tag)) {
            return new TopicSearchAdapterBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for topic_search_adapter is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new com.android.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(2);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(46);

    static {
      sKeys.put("layout/activity_change_pass_0", com.escalate.R.layout.activity_change_pass);
      sKeys.put("layout/activity_chat_0", com.escalate.R.layout.activity_chat);
      sKeys.put("layout/activity_comment_0", com.escalate.R.layout.activity_comment);
      sKeys.put("layout/activity_forgot_password_0", com.escalate.R.layout.activity_forgot_password);
      sKeys.put("layout/activity_home_0", com.escalate.R.layout.activity_home);
      sKeys.put("layout/activity_login_0", com.escalate.R.layout.activity_login);
      sKeys.put("layout/activity_notification_0", com.escalate.R.layout.activity_notification);
      sKeys.put("layout/activity_other_profile_0", com.escalate.R.layout.activity_other_profile);
      sKeys.put("layout/activity_post_queries_0", com.escalate.R.layout.activity_post_queries);
      sKeys.put("layout/activity_proceed_0", com.escalate.R.layout.activity_proceed);
      sKeys.put("layout/activity_reply_0", com.escalate.R.layout.activity_reply);
      sKeys.put("layout/activity_reset_pass_0", com.escalate.R.layout.activity_reset_pass);
      sKeys.put("layout/activity_setting_0", com.escalate.R.layout.activity_setting);
      sKeys.put("layout/activity_tag_detail_0", com.escalate.R.layout.activity_tag_detail);
      sKeys.put("layout/activity_terms_condition_0", com.escalate.R.layout.activity_terms_condition);
      sKeys.put("layout/activity_topic_detail_0", com.escalate.R.layout.activity_topic_detail);
      sKeys.put("layout/activity_update_user_0", com.escalate.R.layout.activity_update_user);
      sKeys.put("layout/chat_list_0", com.escalate.R.layout.chat_list);
      sKeys.put("layout/comment_list_home_0", com.escalate.R.layout.comment_list_home);
      sKeys.put("layout/content_dialog_bottom_sheet_0", com.escalate.R.layout.content_dialog_bottom_sheet);
      sKeys.put("layout/fragment_chat_0", com.escalate.R.layout.fragment_chat);
      sKeys.put("layout/fragment_home_0", com.escalate.R.layout.fragment_home);
      sKeys.put("layout/fragment_log_in_0", com.escalate.R.layout.fragment_log_in);
      sKeys.put("layout/fragment_people_0", com.escalate.R.layout.fragment_people);
      sKeys.put("layout/fragment_post_0", com.escalate.R.layout.fragment_post);
      sKeys.put("layout/fragment_recent_0", com.escalate.R.layout.fragment_recent);
      sKeys.put("layout/fragment_search_0", com.escalate.R.layout.fragment_search);
      sKeys.put("layout/fragment_sign_up_0", com.escalate.R.layout.fragment_sign_up);
      sKeys.put("layout/fragment_tags_0", com.escalate.R.layout.fragment_tags);
      sKeys.put("layout/fragment_topic_0", com.escalate.R.layout.fragment_topic);
      sKeys.put("layout/fragment_user_frofile_0", com.escalate.R.layout.fragment_user_frofile);
      sKeys.put("layout/inlcude_filter_layout_0", com.escalate.R.layout.inlcude_filter_layout);
      sKeys.put("layout/recent_search_list_0", com.escalate.R.layout.recent_search_list);
      sKeys.put("layout/recycler_chat_list_adapter_0", com.escalate.R.layout.recycler_chat_list_adapter);
      sKeys.put("layout/recycler_follower_adapter_0", com.escalate.R.layout.recycler_follower_adapter);
      sKeys.put("layout/recycler_gener_adapter_0", com.escalate.R.layout.recycler_gener_adapter);
      sKeys.put("layout/recycler_home_adapter_0", com.escalate.R.layout.recycler_home_adapter);
      sKeys.put("layout/recycler_notification_adapter_0", com.escalate.R.layout.recycler_notification_adapter);
      sKeys.put("layout/recycler_people_adapter_0", com.escalate.R.layout.recycler_people_adapter);
      sKeys.put("layout/recycler_replies_adapter_0", com.escalate.R.layout.recycler_replies_adapter);
      sKeys.put("layout/search_category_0", com.escalate.R.layout.search_category);
      sKeys.put("layout/single_row_home_post_0", com.escalate.R.layout.single_row_home_post);
      sKeys.put("layout/single_row_posts_0", com.escalate.R.layout.single_row_posts);
      sKeys.put("layout/single_row_replies_post_0", com.escalate.R.layout.single_row_replies_post);
      sKeys.put("layout/tag_search_list_0", com.escalate.R.layout.tag_search_list);
      sKeys.put("layout/topic_search_adapter_0", com.escalate.R.layout.topic_search_adapter);
    }
  }
}
