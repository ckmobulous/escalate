package com.escalate.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.escalate.R;
import com.escalate.adapter.GenreAdapter;
import com.escalate.databinding.ActivityProceedBinding;
import com.escalate.fragments.HomeFragment;
import com.escalate.model.FarmerList;
import com.escalate.model.GenerResponce;
import com.escalate.model.UpdateGenreResponce;
import com.escalate.retrofit.ApiClientConnection;
import com.escalate.retrofit.ApiInterface;
import com.escalate.retrofit.MyDialog;
import com.escalate.sharedpreference.SPreferenceKey;
import com.escalate.sharedpreference.SPreferenceWriter;
import com.escalate.utils.Util;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;

public class ProceedActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityProceedBinding binding;
    private static final String TAG = ProceedActivity.class.getSimpleName();
    private List<FarmerList> mFarmerList = new ArrayList<>();
    public static String[] arrStr = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private Fragment fragment;
    private GenreAdapter adapter;
    private String strSeletedGener = "", strSeletedGenreId = "";
    ArrayList<GenerResponce.DataBean> checklist = null;
    private String fromWhere = "";
    private List<GenerResponce.DataBean> genreList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_proceed);

        binding.btnProceed.setOnClickListener(this);
        genreList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        SPreferenceWriter.getInstance(getApplicationContext()).writeBooleanValue(SPreferenceKey.facebook_key, true);

        binding.btnProceed.setOnClickListener(this);
        binding.imgArrow.setOnClickListener(this);

        if (getIntent() != null) {
            fromWhere = getIntent().getStringExtra("from");
            if (fromWhere != null && !fromWhere.isEmpty()) {
                if (fromWhere.equalsIgnoreCase("my_profile")) {
                    genre_list_by_idService();

                } else {
                    genre_listService();
                }
            } else {
                genre_listService();
            }
        } else {
            genre_listService();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProceed:

                if (adapter != null) {
                    if (adapter.getList() != null && adapter.getList().size() > 0) {

                        if (adapter.isAnyOneSelected()) {
                            String ids = adapter.buildSelectedItemTopicIdsString();
                            String names = adapter.buildSelectedItemNamesString();
                            strSeletedGenreId = ids;
                            strSeletedGener = names;


                            Log.w(TAG, "ids: " + ids);
                            Log.w(TAG, "names: " + names);

                            if (Util.showInternetAlert(this)) {
                                updateTopic();
                            }

                        } else {
                            Log.w(TAG, "isAnyOneSelected: " + false);
                            Toast.makeText(this, "Please select at least one genre", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.imgArrow:
                onBackPressed();
                break;
        }
    }

    private void recyclerView(List<GenerResponce.DataBean> data) {

        for (int i = 0; i <= 3; i++) {

            mFarmerList.add(new FarmerList(HomeFragment.arrStr[i]));
        }

        if (adapter == null) {

            adapter = new GenreAdapter(this, data);
            adapter.setListener(new GenreAdapter.GenreAdapterListener() {
                //    @Override
                //    public void onItemClick(View v, ArrayList<GenerResponce.DataBean> checklist, GenerResponce.DataBean row_pos) {
                //
                //        if (checklist.isEmpty()) {
                //            Log.e("List Empty", ": " + checklist);
                //        } else {
                //            this.checklist = checklist;
                //        }
                //    }

                @Override
                public void onItemTap(View v, int pos) {

                }
            });
        }

        binding.recyclerViewProc.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewProc.setAdapter(adapter);
    }


    /*SERVICES*/
    private void updateTopic() {

        try {
            MyDialog.getInstance(ProceedActivity.this).showDialog(ProceedActivity.this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();

            Call<UpdateGenreResponce> call = apiInterface
                    .update_topic(strSeletedGenreId,
                            SPreferenceWriter.getInstance(ProceedActivity.this).getString(SPreferenceKey.TOKEN),
                            SPreferenceWriter.getInstance(ProceedActivity.this).getString(SPreferenceKey.USERID));
            Log.e(".....", "update_topic: " + call.request().toString());

            Log.w(".....", "update_topic params: \n\n" + strSeletedGenreId + ", " +
                    SPreferenceWriter.getInstance(ProceedActivity.this).getString(SPreferenceKey.TOKEN) + ", " +
                    SPreferenceWriter.getInstance(ProceedActivity.this).getString(SPreferenceKey.USERID));

            call.enqueue(new retrofit2.Callback<UpdateGenreResponce>() {
                @Override
                public void onResponse(Call<UpdateGenreResponce> call, Response<UpdateGenreResponce> response) {

                    MyDialog.getInstance(ProceedActivity.this).hideDialog();
                    if (response.isSuccessful()) {
                        UpdateGenreResponce loginResponse = response.body();
                        Log.e("onResponse", "" + loginResponse.toString());
                        if (loginResponse.getStatus().equalsIgnoreCase("SUCCESS")) {
//                            Toast.makeText(ProceedActivity.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            Toast.makeText(ProceedActivity.this, "Genre updated successfully.", Toast.LENGTH_SHORT).show();
                            SPreferenceWriter.getInstance(getApplicationContext()).writeBooleanValue(SPreferenceKey.LOGINKEY, true);

                            if (UpdateUserActivity.editGenre.equals("edit")) {
                                //AFTER EDIT GENRE VIA PROFILE
                                Intent intent = new Intent();
                                intent.putExtra("genre", strSeletedGener);
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                //AFTER SIGN UP TO HOME
                                startActivity(new Intent(ProceedActivity.this, HomeActivity.class));
                                finish();
                            }

                        } else {

                            Toast.makeText(ProceedActivity.this, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UpdateGenreResponce> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(ProceedActivity.this).hideDialog();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void genre_listService() {
        try {
            MyDialog.getInstance(this).showDialog(this);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<GenerResponce> call = apiInterface.genre_list();
            Log.e(".....", "signUpService: " + call.request().toString());

            call.enqueue(new retrofit2.Callback<GenerResponce>() {
                @Override
                public void onResponse(@NonNull Call<GenerResponce> call, @NonNull Response<GenerResponce> response) {
                    if (response.isSuccessful()) {
                        GenerResponce GenerResponce = response.body();
                        Log.e("response", "" + GenerResponce.toString());

                        if (GenerResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                            genreList.clear();
                            genreList = GenerResponce.getData();

                            if (genreList != null && genreList.size() > 0) {
                                for (int i = 0; i < genreList.size(); i++) {
                                    if (genreList.get(i).getStatus() != null && !genreList.get(i).getStatus().isEmpty()) {
                                        if (genreList.get(i).getStatus().equalsIgnoreCase("1"))
                                            genreList.get(i).setSelected(true);
                                        else
                                            genreList.get(i).setSelected(false);
                                    } else {
                                        genreList.get(i).setSelected(false);
                                    }
                                }
                            }

                            setUpRecycler(genreList);  //in genre_listService

                        } else {

                        }

                    } else {

                    }
                    MyDialog.getInstance(ProceedActivity.this).hideDialog();

                }

                @Override
                public void onFailure(@NonNull Call<GenerResponce> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    MyDialog.getInstance(ProceedActivity.this).hideDialog();
                    String s = "";
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void genre_list_by_idService() {
        try {

            String userid = SPreferenceWriter.getInstance(this).getString(SPreferenceKey.USERID);
            ApiInterface apiInterface = ApiClientConnection.getInstance().createApiInterface();
            Call<GenerResponce> call = apiInterface.genre_list_by_id(userid);

            call.enqueue(new retrofit2.Callback<GenerResponce>() {
                @Override
                public void onResponse(@NonNull Call<GenerResponce> call, @NonNull Response<GenerResponce> response) {

                    if (response.isSuccessful()) {
                        GenerResponce GenerResponce = response.body();

                        Log.e("response", "" + GenerResponce.toString());


                        if (GenerResponce.getStatus().equalsIgnoreCase("SUCCESS")) {

                            genreList.clear();
                            genreList = GenerResponce.getData();

                            if (genreList != null && genreList.size() > 0) {
                                for (int i = 0; i < genreList.size(); i++) {
                                    if (genreList.get(i).getStatus() != null && !genreList.get(i).getStatus().isEmpty()) {
                                        if (genreList.get(i).getStatus().equalsIgnoreCase("1"))
                                            genreList.get(i).setSelected(true);
                                        else
                                            genreList.get(i).setSelected(false);
                                    } else {
                                        genreList.get(i).setSelected(false);
                                    }
                                }
                            }

                            setUpRecycler(genreList);  //in genre_list_by_idService

                        } else {

                        }

                    } else {
                        //  Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GenerResponce> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpRecycler(List<GenerResponce.DataBean> genreList) {
        if (genreList != null && genreList.size() > 0) {
            binding.btnProceed.setEnabled(true);


            adapter = new GenreAdapter(this, genreList);
            binding.recyclerViewProc.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerViewProc.setAdapter(adapter);

            adapter.setListener(new GenreAdapter.GenreAdapterListener() {

                @Override
                public void onItemTap(View v, int pos) {

                    if (adapter.getList() != null && adapter.getList().size() > 0) {
                        GenerResponce.DataBean bean = adapter.getList().get(pos);

                        if (bean.isSelected()) {
                            bean.setSelected(false);
                        } else {
                            bean.setSelected(true);
                        }

                        adapter.notifyDataSetChanged();

                    }

                }
            });


        } else {
            binding.btnProceed.setEnabled(false);
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
        }
    }


    /*
    * if(adapter!=null)
                {
                    if(adapter.getList()!=null && adapter.getList().size()>0)
                    {

                        if(adapter.isAnyOneSelected())
                        {
                            String ids = adapter.buildSelectedItemTopicIdsString();
                            Log.w(TAG,"ids: "+ids);
                        }else {
                            Log.w(TAG,"isAnyOneSelected: "+false);
                        }

                    }
                }
    * */

}
