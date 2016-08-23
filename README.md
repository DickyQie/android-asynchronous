# AsyncTask用法和异步加载图片

<div id="article_content" class="article_content">

<p><span style="font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px; background-color:rgb(255,255,255)"><span style="font-size:24px; color:#333399">AsyncTask</span><span style="color:#333399; font-size:13px">：</span></span><span style="font-size:14px"><span style="color:rgb(75,75,75); font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px">是android提供的轻量级的异步类,可以直接继承AsyncTask,在类中实现异步操作,</span><span style="color:rgb(75,75,75); font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px">并</span><span style="color:rgb(75,75,75); font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px">提供接口反馈当前</span><span style="color:rgb(75,75,75); font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px">异步执行的程度</span><span style="color:rgb(75,75,75); font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px">(可以通过接口实现UI进度更新),最后反馈执行的结果给UI主线程。</span></span></p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px; color:rgb(75,75,75)"><span style="color:rgb(51,51,153); font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px"><span style="font-size:14px; color:rgb(75,75,75); font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px">AsyncTask
</span><span style="color:rgb(75,75,75); font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px"><span style="font-size:14px">(</span></span></span><span style="font-size:14px">异步任务)，从字面上来说，就是在我们的UI主线程运行的时候，异步的完成一些操作。AsyncTask允许我们的执行一个异步的任务在后台。我们可以将耗时的操作放在异步任务当中来执行，并随时将任务执行的结果返回给我们的UI线程来更新我们的UI控件。通过AsyncTask我们可以轻松的解决多线程之间的通信问题。</span></span></p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"></span></p>
<p style="color:rgb(75,75,75); margin:10px auto; padding-top:0px; padding-bottom:0px; font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px">
<span style="font-size:14px">4个步骤：当我们执行一个异步任务的时候，其需要按照下面的4个步骤分别执行</span></p>
<ul style="color:rgb(75,75,75); margin:0px 0px 0px 30px; padding:0px; word-break:break-all; font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px">
<li style="margin:0px 0px 1em; padding:0px; list-style:disc"><span style="font-size:14px"><span style="margin:0px; padding:0px"><span style="margin:0px; padding:0px; color:rgb(0,0,255)">onPreExecute():</span></span>&nbsp;这个方法是在执行异步任务之前的时候执行，并且是在UI Thread当中执行的，通常我们在这个方法里做一些UI控件的初始化的操作，例如弹出要给ProgressDialog</span></li><li style="margin:0px 0px 1em; padding:0px; list-style:disc"><span style="font-size:14px"><span style="margin:0px; padding:0px"><span style="margin:0px; padding:0px; color:rgb(0,0,255)">doInBackground(Params... params):</span></span>&nbsp;在onPreExecute()方法执行完之后，会马上执行这个方法，这个方法就是来处理异步任务的方法，Android操作系统会在后台的线程池当中开启一个worker
 thread来执行我们的这个方法，所以这个方法是在worker thread当中执行的，这个方法执行完之后就可以将我们的执行结果发送给我们的最后一个 onPostExecute 方法，在这个方法里，我们可以从网络当中获取数据等一些耗时的操作</span></li><li style="margin:0px 0px 1em; padding:0px; list-style:disc"><span style="font-size:14px"><span style="margin:0px; padding:0px; color:rgb(0,0,255)"><span style="margin:0px; padding:0px">onProgressUpdate(Progess... values):</span></span>&nbsp;这个方法也是在UI Thread当中执行的，我们在异步任务执行的时候，有时候需要将执行的进度返回给我们的UI界面，例如下载一张网络图片，我们需要时刻显示其下载的进度，就可以使用这个方法来更新我们的进度。这个方法在调用之前，我们需要在
 doInBackground 方法中调用一个 publishProgress(Progress) 的方法来将我们的进度时时刻刻传递给 onProgressUpdate 方法来更新</span></li><li style="margin:0px 0px 1em; padding:0px; list-style:disc"><span style="font-size:14px"><span style="margin:0px; padding:0px; color:rgb(0,0,255)"><span style="margin:0px; padding:0px">onPostExecute(Result... result):</span></span>&nbsp;当我们的异步任务执行完之后，就会将结果返回给这个方法，这个方法也是在UI
 Thread当中调用的，我们可以将返回的结果显示在UI控件上</span></li></ul>
<span style="font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px"><span style="font-size:18px"><span style="color:#4b4b4b">一个超简单的理解 AsyncTask 的例子：</span><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="color:#3366ff"><strong>AsyncTask来从网络上下载一张图片</strong></span></span></span></span><br>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px"><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:14px">xml:</span></span></span></span></p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px"><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"></span></span></span></p>
<p></p>
<p style="font-size:14px"><span style="color:teal">&lt;</span><span style="color:#3F7F7F">RelativeLayout</span>
<span style="color:#7F007F">xmlns:android</span>=<em><span style="color:#2A00FF">&quot;http://schemas.android.com/apk/res/android&quot;</span></em></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:layout_width</span>=<em><span style="color:#2A00FF">&quot;match_parent&quot;</span></em></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:layout_height</span>=<em><span style="color:#2A00FF">&quot;match_parent&quot;</span></em><span style="color:teal">&gt;</span></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">ImageView</span></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:id</span>=<em><span style="color:#2A00FF">&quot;@&#43;id/imageViewss&quot;</span></em></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:layout_width</span>=<em><span style="color:#2A00FF">&quot;wrap_content&quot;</span></em></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:layout_height</span>=<em><span style="color:#2A00FF">&quot;200dp&quot;</span></em></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:src</span>=<em><span style="color:#2A00FF">&quot;@drawable/ic_launcher&quot;</span></em></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:scaleType</span>=<em><span style="color:#2A00FF">&quot;fitCenter&quot;</span></em><span style="color:teal">/&gt;</span></p>
<p style="font-size:14px">&nbsp;</p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">Button</span></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:id</span>=<em><span style="color:#2A00FF">&quot;@&#43;id/buttonOnClicksAsyncTask&quot;</span></em></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:layout_width</span>=<em><span style="color:#2A00FF">&quot;wrap_content&quot;</span></em></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:layout_height</span>=<em><span style="color:#2A00FF">&quot;wrap_content&quot;</span></em></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:layout_below</span>=<em><span style="color:#2A00FF">&quot;@&#43;id/imageView&quot;</span></em></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:layout_centerHorizontal</span>=<em><span style="color:#2A00FF">&quot;true&quot;</span></em></p>
<p style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:layout_marginTop</span>=<em><span style="color:#2A00FF">&quot;200dp&quot;</span></em></p>
<p style="font-size:14px"><span style="color:#7F007F">android:text</span>=<em><span style="color:#2A00FF">&quot;</span><span style="color:#2A00FF">从网络上下载一张图片</span><span style="color:#2A00FF">&quot;</span></em>
<span style="color:teal">/&gt;</span></p>
<p style="font-size:14px"><span style="color:teal">&lt;/</span><span style="color:#3F7F7F">RelativeLayout</span><span style="color:teal">&gt;</span></p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:18px; color:#3366ff">Activity代码：</span></span><br>
</p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:18px; color:#3366ff"></span></span></p>
<p><strong><span style="color:#7F0055">private</span></strong> Button <span style="color:#0000C0">
btn</span>;</p>
<p><strong><span style="color:#7F0055">private</span></strong> ImageView<span style="color:#0000C0">imageView</span>;</p>
<p><strong><span style="color:#7F0055">private</span></strong>ProgressDialog <span style="color:#0000C0">
progressDialog</span>;</p>
<p><strong><span style="color:#7F0055">private</span> <span style="color:#7F0055">
final</span></strong> String <span style="color:#0000C0">IMGURL</span>= <span style="color:#2A00FF">
&quot;http://img0.pconline.com.cn/pconline/1206/18/2829090_3867bd63fd673471aa184c02_500.jpg&quot;</span>;</p>
<p>&nbsp; &nbsp;&nbsp;<span style="color:#646464">@Override</span></p>
<p>&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">protected</span> <span style="color:#7F0055">
void&nbsp;</span></strong>onCreate(Bundle savedInstanceState)</p>
<p>&nbsp;&nbsp;&nbsp; {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">super</span></strong>.onCreate(savedInstanceState);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; setContentView(R.layout.<em><span style="color:#0000C0">asynctask_img</span></em>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">btn</span>=(Button)findViewById(R.id.<em><span style="color:#0000C0">buttonOnClicksAsyncTask</span></em>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">imageView</span> =(ImageView)findViewById(R.id.<em><span style="color:#0000C0">imageViewss</span></em>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">progressDialog</span> = <strong><span style="color:#7F0055">new</span></strong>ProgressDialog(<strong><span style="color:#7F0055">this</span></strong>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">progressDialog</span>.setTitle(<span style="color:#2A00FF">&quot;</span><span style="color:#2A00FF">提示信息</span><span style="color:#2A00FF">&quot;</span>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">progressDialog</span>.setMessage(<span style="color:#2A00FF">&quot;</span><span style="color:#2A00FF">正在下载中，请稍后</span><span style="color:#2A00FF">......&quot;</span>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">progressDialog</span>.setCancelable(<strong><span style="color:#7F0055">false</span></strong>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">progressDialog</span>.setProgressStyle(ProgressDialog.<em><span style="color:#0000C0">STYLE_SPINNER</span></em>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">btn</span>.setOnClickListener(<strong><span style="color:#7F0055">new</span></strong>View.OnClickListener()</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#646464">@Override</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
void</span></strong>onClick(View v)</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">// </span><span style="color:#3F7F5F">在</span><span style="color:#3F7F5F">UI Thread</span><span style="color:#3F7F5F">当中实例化</span><span style="color:#3F7F5F">AsyncTask</span><span style="color:#3F7F5F">对象，并调用</span><span style="color:#3F7F5F">execute</span><span style="color:#3F7F5F">方法</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">new</span></strong>MAsyncTask().execute(<span style="color:#0000C0">IMGURL</span>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; });</p>
<p>&nbsp;&nbsp;&nbsp; }</p>
<p><br>
</p>
<p>&nbsp;<strong><span style="color:#7F0055">public</span> <span style="color:#7F0055">
class&nbsp;</span></strong>MAsyncTask <strong><span style="color:#7F0055">extends</span></strong> AsyncTask&lt;String, Integer,
<strong><span style="color:#7F0055">byte</span></strong>[]&gt;</p>
<p>&nbsp;&nbsp;&nbsp; {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#646464">@Override</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">protected</span> <span style="color:#7F0055">
void</span></strong>onPreExecute()</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">super</span></strong>.onPreExecute();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">progressDialog</span>.show();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#646464">@Override</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">protected</span> <span style="color:#7F0055">
byte</span></strong>[]doInBackground(String... params)</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HttpClient httpClient = <strong><span style="color:#7F0055">new</span></strong>DefaultHttpClient();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HttpGet httpGet = <strong><span style="color:#7F0055">new</span></strong>HttpGet(params[0]);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">byte</span></strong>[] image =<strong><span style="color:#7F0055">new</span>
<span style="color:#7F0055">byte</span></strong>[]{};</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">try</span></strong></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HttpResponse httpResponse =httpClient.execute(httpGet);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; HttpEntity httpEntity =httpResponse.getEntity();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">if</span></strong>(httpEntity!=
<strong><span style="color:#7F0055">null</span></strong> &amp;&amp;httpResponse.getStatusLine().getStatusCode() == HttpStatus.<em><span style="color:#0000C0">SC_OK</span></em>)</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; image = EntityUtils.<em>toByteArray</em>(httpEntity);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">catch</span></strong>(Exception e)</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; e.printStackTrace();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">finally</span></strong></p>
<p>&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;httpClient.getConnectionManager().shutdown();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">return</span></strong> image;</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#646464">@Override</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">protected</span> <span style="color:#7F0055">
void</span></strong>onProgressUpdate(Integer... values)</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">super</span></strong>.onProgressUpdate(values);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#646464">@Override</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">protected</span> <span style="color:#7F0055">
void</span></strong>onPostExecute(<strong><span style="color:#7F0055">byte</span></strong>[] result)</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong><span style="color:#7F0055">super</span></strong>.onPostExecute(result);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">//&nbsp;&nbsp;&nbsp; </span><span style="color:#3F7F5F">将</span><span style="color:#3F7F5F">doInBackground</span><span style="color:#3F7F5F">方法返回的</span><span style="color:#3F7F5F">byte[]</span><span style="color:#3F7F5F">解码成要给</span><span style="color:#3F7F5F">Bitmap</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Bitmap bitmap = BitmapFactory.<em>decodeByteArray</em>(result,0, result.<span style="color:#0000C0">length</span>);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#3F7F5F">//&nbsp;&nbsp;&nbsp; </span><span style="color:#3F7F5F">更新我们的</span><span style="color:#3F7F5F">ImageView</span><span style="color:#3F7F5F">控件</span></p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">imageView</span>.setImageBitmap(bitmap);</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#0000C0">progressDialog</span>.dismiss();</p>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }</p>
<p>&nbsp;&nbsp;&nbsp; }</p>
}
<p style="font-size:14px"><span style="color:teal"><br>
</span></p>
<span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px; background-color:rgb(255,0,0)"></span>
<p style="font-size:14px"><span style="color:#3366ff">一个ImageView控件和一个Button控件，当点击Button控件时，弹出一个ProgressDialog，然后开启一个异步任务，从网络中下载一张图片，并更新到我们的ImageView上。这里还要注意一点，如果我们要访问网络，必须还要给其授权才行</span></p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:18px">AndroidManifest.xml文件：</span></span><br>
</p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"></span></p>
<p style="font-size:18px"><span style="color:teal">&lt;</span><span style="color:#3F7F7F">uses-permission</span>
<span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;android.permission.INTERNET&quot;</span></em><span style="color:teal">/&gt;</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">application</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:allowBackup</span>=<em><span style="color:#2A00FF">&quot;true&quot;</span></em></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:icon</span>=<em><span style="color:#2A00FF">&quot;@drawable/ic_launcher&quot;</span></em></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:label</span>=<em><span style="color:#2A00FF">&quot;@string/app_name&quot;</span></em></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&gt;</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">activity</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;.MainActivity&quot;</span></em></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:#7F007F">android:label</span>=<em><span style="color:#2A00FF">&quot;@string/app_name&quot;</span></em><span style="color:teal">&gt;</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">intent-filter</span><span style="color:teal">&gt;</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">action</span>
<span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;android.intent.action.MAIN&quot;</span></em>
<span style="color:teal">/&gt;</span></p>
<p style="font-size:18px">&nbsp;</p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;</span><span style="color:#3F7F7F">category</span>
<span style="color:#7F007F">android:name</span>=<em><span style="color:#2A00FF">&quot;android.intent.category.LAUNCHER&quot;</span></em>
<span style="color:teal">/&gt;</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;/</span><span style="color:#3F7F7F">intent-filter</span><span style="color:teal">&gt;</span></p>
<p style="font-size:18px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span style="color:teal">&lt;/</span><span style="color:#3F7F7F">activity</span><span style="color:teal">&gt;</span></p>
<p style="font-size:18px">&nbsp;<span style="color:teal">&lt;/</span><span style="color:#3F7F7F">application</span><span style="color:teal">&gt;</span></p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:14px">效果图：</span></span><br>
</p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:14px"><img src="http://img.blog.csdn.net/20160808163845472?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" alt="">&nbsp;
 &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img src="http://img.blog.csdn.net/20160808163904267?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" alt=""><br>
</span></span></p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:14px"><span style="font-family:Arial; font-size:14px; line-height:35px"><br>
</span></span></span></p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:14px"><span style="font-family:Arial; font-size:14px; line-height:35px"><span style="color:rgb(204,0,0); font-family:'Microsoft Yahei'; font-size:18px; line-height:28px; text-indent:32px">源码下载：</span><br>
</span></span></span></p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:14px"><span style="font-family:Arial; font-size:14px; line-height:35px"><br>
</span></span></span></p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:14px"><span style="font-family:Arial; font-size:14px; line-height:35px">CSDN下载地址：</span><span style="font-family:Arial; line-height:35px"><span style="color:#3333ff; text-decoration:none"><a target="_blank" href="http://download.csdn.net/detail/dickyqie/9598236" style="text-decoration:none">http://download.csdn.net/detail/dickyqie/9598236</a></span><a target="_blank" href="http://download.csdn.net/detail/dickyqie/9598236"></a></span><br>
</span></span></p>
<p><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:14px"><span style="font-family:Arial; line-height:35px">Github下载： &nbsp;
<a target="_blank" href="https://github.com/DickyQie/AsynTaskDemo.git">https://github.com/DickyQie/AsynTaskDemo.git</a></span></span></span></p>
<br>
<p style="font-size:14px"><span style="font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px"><span style="font-family:Verdana,Arial,Helvetica,sans-serif; line-height:21px"><span style="font-size:14px"><br>
</span></span></span></p>
<p style="font-size:14px"><br>
</p>
<p style="font-size:14px"><span style="font-size:14px"></span></p>
<p style="font-size:13px; margin:10px auto; line-height:19.5px; color:rgb(75,75,75); font-family:Verdana,Geneva,Arial,Helvetica,sans-serif">
</p>
<p style="font-size:14px"></p>
<br>
<p><br>
</p>
<p><span style="font-size:14px"><span style="color:rgb(75,75,75); font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; line-height:19.5px"></span></span></p>
<p style="line-height:19.5px; margin:10px auto; color:rgb(75,75,75); font-family:Verdana,Geneva,Arial,Helvetica,sans-serif; font-size:13px">
</p>
<br>
   
</div>
