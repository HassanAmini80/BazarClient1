package com.example.hassan.bazarclient.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassan.bazarclient.Network.FakeGoodProvider;
import com.example.hassan.bazarclient.Network.FakeGoodService;
import com.example.hassan.bazarclient.R;
import com.example.hassan.bazarclient.models.ErrorModel;
import com.example.hassan.bazarclient.models.GoodModel;
import com.example.hassan.bazarclient.models.OrderModel;
import com.example.hassan.bazarclient.utility.ClientConfigs;
import com.example.hassan.bazarclient.utility.ErrorUtils;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodInfoActivity extends AppCompatActivity {

    private int mActionToDo = GoodConstants.GOOD_INFO;
    private String mGoodIdInInfoMode;
    GoodModel currentGood = new GoodModel();
    OrderModel currentOrder = new OrderModel();
    private TextView mTxGoodName;
    private TextView mTxGoodPrice;
    private TextView description;
    private ImageView imGoodInfo;
    private Context mContext;
    public FakeGoodService gMService;
    private ProgressDialog mProgress;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_info);

        new GoodInfo().execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.send_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                order();
            }
        });
    }

    public void setData(GoodModel currentModel, Context context) {
        //point elements
        mTxGoodName = (TextView) findViewById(R.id.goodNameInfo);
        mTxGoodPrice = (TextView) findViewById(R.id.goodPriceInfo);
        description = (TextView) findViewById(R.id.goodDesInfo);
        imGoodInfo = (ImageView) findViewById(R.id.goodImgInfo);
        //setData
        this.mContext = context;
        this.mTxGoodName.setText(currentModel.goodName);
        this.mTxGoodPrice.setText(currentModel.goodPrice);
        this.description.setText(currentModel.goodDes);
        Picasso.with(mContext).load(ClientConfigs.REST_API_BASE_URL + "../images/" + currentModel.imageUrl).into(this.imGoodInfo);
        //this.position = position;
        this.currentGood = currentModel;
    }

    class GoodInfo extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(GoodInfoActivity.this);
            mProgress.setMessage("searching...");
            mProgress.show();
        }

        @Override
        protected String doInBackground(String... params) {

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

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgress.cancel();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (mActionToDo == GoodConstants.GOOD_INFO && !mGoodIdInInfoMode.equals("")) {

                        Call<GoodModel> call = gMService.getGoodById(mGoodIdInInfoMode);
                        call.enqueue(new Callback<GoodModel>() {
                            @Override
                            public void onResponse(Call<GoodModel> call, Response<GoodModel> response) {
                                 if (response.isSuccessful()) {
                                     currentGood = response.body();
                                     setData(currentGood, mContext);

                                 } else {

                                 }
                            }

                            @Override
                            public void onFailure(Call<GoodModel> call, Throwable t) {
                             }
                        });
                     }
                }
            }
            );
            CollapsingToolbarLayout collapsingToolbar =
                    (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
            collapsingToolbar.setTitle(currentGood.goodName);

        }

    }

    public void order(){
        FakeGoodProvider GoodProvider = new FakeGoodProvider();
        gMService = GoodProvider.getGService();
        Call<String> call = gMService.order(currentOrder);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()){
                    send();
                }else {
                    ErrorModel errorModel = ErrorUtils.parseError(response);
                    Toast.makeText(getBaseContext(), "Error type is " + errorModel.type + " , description " + errorModel.description, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Toast.makeText(getBaseContext(), "Fail it >>" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void send(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(GoodInfoActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.order, null);
        Button send = (Button) mView.findViewById(R.id.send_popup);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                EditText orderNumber = (EditText) mView.findViewById(R.id.orderNumber);
                currentOrder.setNumber(Integer.parseInt(String.valueOf(orderNumber.getText())));
                currentOrder.setGood_id(currentGood.goodId);
            }
        });
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

}