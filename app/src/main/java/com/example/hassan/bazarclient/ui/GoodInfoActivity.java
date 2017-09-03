package com.example.hassan.bazarclient.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.hassan.bazarclient.R;
import com.example.hassan.bazarclient.models.GoodModel;
import com.example.hassan.bazarclient.network.FakeGoodProvider;
import com.example.hassan.bazarclient.network.FakeGoodService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class GoodInfoActivity extends AppCompatActivity {

    private int mActionToDo = GoodConstants.GOOD_INFO;
    private String mGoodIdInInfoMode;
    private TextView mTxGoodName;
    private TextView mTxGoodPrice;
    private TextView description;
    private FakeGoodService gMService;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_info);


        //get argument and check is GoodInfo
        Bundle args = getIntent().getExtras();
        if (args != null) {
            mActionToDo = args.getInt(GoodConstants.ACTION_TO_DO_KEY, GoodConstants.GOOD_INFO);
            if (mActionToDo == GoodConstants.GOOD_INFO) {
                mGoodIdInInfoMode = args.getString(GoodConstants.GOOD_ID_KEY, "");
            }

        }


        FakeGoodProvider GoodProvider = new FakeGoodProvider();
        gMService = GoodProvider.getGService();

        if (mActionToDo == GoodConstants.GOOD_INFO && !mGoodIdInInfoMode.equals("")) {
            Log.d(TAG, "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD" + mGoodIdInInfoMode);

            Call<GoodModel> call = gMService.getGoodById(mGoodIdInInfoMode);
            call.enqueue(new Callback<GoodModel>() {
                @Override
                public void onResponse(Call<GoodModel> call, Response<GoodModel> response) {
                    if (response.isSuccessful()) {
                        Log.d(TAG, "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk" + mGoodIdInInfoMode);


                    } else {
                        Log.d(TAG, "else___else___else___else___else" + mGoodIdInInfoMode);

                    }
                }

                @Override
                public void onFailure(Call<GoodModel> call, Throwable t) {
                    Log.d(TAG, "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN" + mGoodIdInInfoMode);

                }
            });

        }

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle("Collapsing Toolbar");

/*

		mTxGoodName = (TextView) findViewById(R.id.goodNameInfo);
		this.mTxGoodName.setText(R.id.goodName);
		//this.mTxGoodPrice.setText(R.id.goodPrice);
		//this.description.setText(R.id.goodDes);



		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Module 5");
		toolbar.inflateMenu(R.menu.menu_main);
*/

    }
}
