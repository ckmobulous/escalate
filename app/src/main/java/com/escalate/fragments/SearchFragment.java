package com.escalate.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.escalate.R;
import com.escalate.adapter.PeopleSearchAdapter;
import com.escalate.adapter.RecentAdapter;
import com.escalate.adapter.RecentSearchAdapter;
import com.escalate.adapter.TagSearchAdapter;
import com.escalate.adapter.TopicSearchAdapter;
import com.escalate.databinding.FragmentSearchBinding;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    private FragmentSearchBinding binding;
    //    private String[] data = {"People", "Topics", "Recent","Tags"};
    private MyViewPagerAdapter myViewPagerAdapter;
    private int tabPos = 0;
    private final String TAG = SearchFragment.class.getSimpleName();
    private Bundle saveInstance;
    private List<String> titleList = null;
    private PeopleSearchAdapter peopleAdapter = null;
    private TopicSearchAdapter topicSearchAdapter = null;
    RecentSearchAdapter recentSearchAdapter = null;
    private RecentAdapter recentAdapter = null;
    //    HomePostsAdapter homePostsAdapter;
    TagSearchAdapter tagSearchAdapter = null;
    private PeopleFrag peopleFrag;
    private TopicFrag topicFrag;
    private RecentFrag recentFrag;
    private TagsFrag tagsFrag;
    private FragmentManager childFragmentManager;


    public SearchFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        saveInstance = savedInstanceState;

        peopleFrag = new PeopleFrag();
        topicFrag = new TopicFrag();
        recentFrag = new RecentFrag();
        tagsFrag = new TagsFrag();
        configureViewPager();


        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {

                    filterAccToAdapter(tabPos, binding.etSearch.getText().toString()); //filter

                } else {
                    Log.w("ontext", "length==0");
                    selectPage(tabPos);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return binding.getRoot();
    }

    //    METHOD: TO  Configure ViewPager
    private void configureViewPager() {
        childFragmentManager = getChildFragmentManager();

        binding.viewPager.setOffscreenPageLimit(4);
        populateViewPager(binding.viewPager);

        binding.tabsSearch.setupWithViewPager(binding.viewPager);

        setUpCustomView();

//      SET UP ON TAB SELECTED LISTENER (for Selected Tab Position)
        binding.tabsSearch.addOnTabSelectedListener(this);


    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tabPos = tab.getPosition();    //  Update Tab Position in selectedTab
        Log.i(TAG, "onTabSelected: " + tabPos);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.w(TAG, "onPageSelected: " + position + " : " + titleList.get(position));

        switch (position) {
            case 0:     //People
                peopleFrag.peopleListService();
                binding.etSearch.setText("");
                binding.etSearch.setHint("Search");
                break;
            case 1:     //Topic
                topicFrag.topicListService();
                binding.etSearch.setText("");
                binding.etSearch.setHint("Search");
                break;
            case 2:      //Recent
//                recentFrag.recentService();
                recentFrag.setUpRecentList();   // SET UP RECENT_SEARCHES
                binding.etSearch.setText("");
                binding.etSearch.setHint("Search");
                break;
            case 3://Tag
                binding.etSearch.setText("");
                binding.etSearch.setHint("Search");
                tagsFrag.tagsService();
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private class MyViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public MyViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);


        }


        //        METHOD: TO ADD NEW FRAGMENT
        void addNewFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


        private List<String> getTitleList() {
            return mFragmentTitleList;
        }


        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            //do nothing here! no call to super.restoreState(arg0, arg1);
        }

    }


    //  METHOD: TO POPULATE VIEW PAGER WITH FRAGMENTS
    private void populateViewPager(ViewPager viewPager) {

        myViewPagerAdapter = new MyViewPagerAdapter(childFragmentManager);

        myViewPagerAdapter.addNewFragment(peopleFrag, "People");
        myViewPagerAdapter.addNewFragment(topicFrag, "Topics");
        myViewPagerAdapter.addNewFragment(recentFrag, "Recent");
        myViewPagerAdapter.addNewFragment(tagsFrag, "Tags");


        viewPager.setAdapter(myViewPagerAdapter);

    }


    //  METHOD: TO SELECT A PARTICULAR TAB BASED ON TAB_POSITION(pageIndex) AND HIT API/SERVICE ..
    public void selectPage(int pageIndex) {

        try {
//            tabs.setScrollPosition(pageIndex, 0f, true);

            binding.viewPager.setCurrentItem(pageIndex);
            TabLayout.Tab tab = binding.tabsSearch.getTabAt(pageIndex);
            if (tab != null) {

                tab.select();

                switch (pageIndex) {
                    case 0:
                        peopleFrag.peopleListService();
                        break;

                    case 1:
                        topicFrag.topicListService();
                        break;
                    case 2:
//                        recentFrag.recentService();
                        recentFrag.setUpRecentList();   // SET UP RECENT_SEARCHES
                        break;
                    case 3:
                        tagsFrag.tagsService();
                        break;
                }

                getTheLatestFragAdapter(pageIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        binding.viewPager.addOnPageChangeListener(this);

        if (saveInstance != null) {
            tabPos = saveInstance.getInt("tabPos", 0);

            selectPage(tabPos);
        } else {

            selectPage(tabPos);
        }
    }

    private void getTheLatestFragAdapter(int pos) {
        Fragment f = myViewPagerAdapter.getItem(pos);
        if (f != null) {
            switch (pos) {
                case 0:
                    PeopleFrag fragment = (PeopleFrag) f;
                    peopleAdapter = fragment.getAdapter();
                    break;
                case 1:
                    TopicFrag fragment1 = (TopicFrag) f;
                    topicSearchAdapter = fragment1.getTopicSearchAdapter();
                    break;
                case 2:
                    RecentFrag recentFragmnt = (RecentFrag) f;
//                    recentSearchAdapter = recentFragmnt.getRecentSearchAdapter();
                    recentAdapter = recentFragmnt.getRecentAdapter();
                    break;
                case 3:
                    TagsFrag tf = (TagsFrag) f;
                    tagSearchAdapter = tf.getTagSearchAdapter();
                    break;
            }

        }
    }

    //    METHOD: TO SET UP TAB CUSTOM VIEW
    private void setUpCustomView() {
        try {
//          GET TAB TITLE LIST AND INITIALIZE
            titleList = myViewPagerAdapter.getTitleList();

            for (int i = 0; i < titleList.size(); i++) {
                TabLayout.Tab tab = binding.tabsSearch.getTabAt(i);
                if (tab != null) {
                    tab.setText(titleList.get(i));

//                    tab.setCustomView(prepareTabView(i));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveInstance = outState;
        saveInstance.putInt("tabPos", tabPos);
    }


    private void filterAccToAdapter(int pos, String newText) {
        getTheLatestFragAdapter(pos);
        switch (pos) {
            case 0:
                if (peopleAdapter != null) {
                    peopleAdapter.getFilter().filter(newText);    //FILTERING
                }
                break;
            case 1:

                if (topicSearchAdapter != null) {
                    topicSearchAdapter.getFilter().filter(newText);
                }
                break;
            case 2:
                if (recentAdapter != null) {
                    recentAdapter.getFilter().filter(newText);
                }
                break;
            case 3:
                if (tagSearchAdapter != null) {
                    tagSearchAdapter.getFilter().filter(newText);
                }
                break;
        }
    }
}
