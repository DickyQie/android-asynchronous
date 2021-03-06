### AsyncTask用法和异步加载图片

 <p>AsyncTask：是Android提供的轻量级的异步类,可以直接继承AsyncTask,在类中实现异步操作,并提供接口反馈当前异步执行的程度(可以通过接口实现UI进度更新),最后反馈执行的结果给UI主线程。</p> 
<p>AsyncTask&nbsp;(异步任务)，从字面上来说，就是在我们的UI主线程运行的时候，异步的完成一些操作。AsyncTask允许我们的执行一个异步的任务在后台。我们可以将耗时的操作放在异步任务当中来执行，并随时将任务执行的结果返回给我们的UI线程来更新我们的UI控件。通过AsyncTask我们可以轻松的解决多线程之间的通信问题。</p> 
<p>&nbsp;</p> 
<p>4个步骤：当我们执行一个异步任务的时候，其需要按照下面的4个步骤分别执行</p> 
<ul> 
 <li>onPreExecute():&nbsp;这个方法是在执行异步任务之前的时候执行，并且是在UI Thread当中执行的，通常我们在这个方法里做一些UI控件的初始化的操作，例如弹出要给ProgressDialog</li> 
 <li>doInBackground(Params... params):&nbsp;在onPreExecute()方法执行完之后，会马上执行这个方法，这个方法就是来处理异步任务的方法，Android操作系统会在后台的线程池当中开启一个worker thread来执行我们的这个方法，所以这个方法是在worker thread当中执行的，这个方法执行完之后就可以将我们的执行结果发送给我们的最后一个 onPostExecute 方法，在这个方法里，我们可以从网络当中获取数据等一些耗时的操作</li> 
 <li>onProgressUpdate(Progess... values):&nbsp;这个方法也是在UI Thread当中执行的，我们在异步任务执行的时候，有时候需要将执行的进度返回给我们的UI界面，例如下载一张网络图片，我们需要时刻显示其下载的进度，就可以使用这个方法来更新我们的进度。这个方法在调用之前，我们需要在 doInBackground 方法中调用一个 publishProgress(Progress) 的方法来将我们的进度时时刻刻传递给 onProgressUpdate 方法来更新</li> 
 <li>onPostExecute(Result... result):&nbsp;当我们的异步任务执行完之后，就会将结果返回给这个方法，这个方法也是在UI Thread当中调用的，我们可以将返回的结果显示在UI控件上</li> 
</ul> 
<p>一个超简单的理解 AsyncTask 的例子：<strong>AsyncTask来从网络上加载一张图片</strong></p> 
<p>Activity代码：</p> 
<pre><code class="language-java">
 public class MAsyncTask extends AsyncTask&lt;String, Integer, byte[]&gt;
    {
        @Override
        protected voidonPreExecute()
        {
            super.onPreExecute();
            progressDialog.show();
        }
        @Override
        protected byte[]doInBackground(String... params)
        {
            HttpClient httpClient = newDefaultHttpClient();
            HttpGet httpGet = newHttpGet(params[0]);
            byte[] image =new byte[]{};
            try
            {
                HttpResponse httpResponse =httpClient.execute(httpGet);
                HttpEntity httpEntity =httpResponse.getEntity();
                if(httpEntity!= null &amp;&amp;httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    image = EntityUtils.toByteArray(httpEntity);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
               httpClient.getConnectionManager().shutdown();
            }
            return image;
        }
        @Override
        protected voidonProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected voidonPostExecute(byte[] result)
        {
            super.onPostExecute(result);
            //    将doInBackground方法返回的byte[]解码成要给Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(result,0, result.length);
            //    更新我们的ImageView控件
            imageView.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }
}</code></pre> 
<p><span style="color:#B22222"><strong>一个ImageView控件和一个Button控件，当点击Button控件时，弹出一个ProgressDialog，然后开启一个异步任务，从网络中下载一张图片，并更新到我们的ImageView上。这里还要注意一点，如果我们要访问网络，必须还要给其授权才行</strong></span></p> 
<p>AndroidManifest.xml文件：</p> 
<pre><code class="language-html">&lt;uses-permission android:name="android.permission.INTERNET"/></pre>

<p>效果图：</p> 
<p><img alt="" height="353" src="http://images2015.cnblogs.com/blog/1041439/201611/1041439-20161116092011779-996504030.png" width="423">&nbsp;<img alt="" height="372" src="http://images2015.cnblogs.com/blog/1041439/201611/1041439-20161116092027467-1655478763.png" width="327"></p> 

