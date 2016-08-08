package com.erample.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.InputStream;

import java.net.URL;


/****
 *
 * AsyncTask 网络异步加载图片
 */
public class MainActivity extends Activity {

    private Button button;
    private ImageView imageView;
    private ProgressDialog progressDialog;
    private final String IMAGE_PATH2="http://img0.pconline.com.cn/pconline/1206/18/2829090_3867bd63fd673471aa184c02_500.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView()
    {


        button = (Button)findViewById(R.id.buttonOnClicksAsyncTask);
        imageView = (ImageView)findViewById(R.id.imageViewss);
        //    弹出要给ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示信息");
        progressDialog.setMessage("正在下载中，请稍后......");
        //    设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
        progressDialog.setCancelable(false);
        //    设置ProgressDialog样式为圆圈的形式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // 在UI Thread当中实例化AsyncTask对象，并调用execute方法
                new MyAsyncTask().execute(IMAGE_PATH2);
            }
        });
    }


    /**
     * 定义一个类，让其继承AsyncTask这个类
     * Params: String类型，表示传递给异步任务的参数类型是String，通常指定的是URL路径
     * Progress: Integer类型，进度条的单位通常都是Integer类型
     * Result：byte[]类型，表示我们下载好的图片以字节数组返回
     * @author xiaoluo
     *
     */
    public class MyAsyncTask extends AsyncTask<String, Integer, Bitmap>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            //    在onPreExecute()中我们让ProgressDialog显示出来
            progressDialog.show();
        }
        @Override
        protected Bitmap doInBackground(String... params)
        {

            Bitmap bitmap=null;
            try {
                URL u=new URL(IMAGE_PATH2);
                InputStream in=u.openStream();
                bitmap=BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(Bitmap result)
        {
            super.onPostExecute(result);

            imageView.setImageBitmap(result);
            //    使ProgressDialog框消失
            progressDialog.dismiss();
        }
    }


}
