package com.example.ankodemo

import android.annotation.SuppressLint

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.design.widget.Snackbar
import android.util.Log

import okhttp3.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import java.io.IOException
import java.lang.reflect.Type


class MainActivity : AppCompatActivity() {

           @SuppressLint("ResoutceType")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)


        verticalLayout{
            button("OKHttp3"){
                onClick {
                    Snackbar.make(this@button,"OKHttp3",Snackbar.LENGTH_SHORT).show()
                    getNetWork()
                }
            }
            button("retrofit"){
                onClick {
                    useRetrofit()
                }
            }
            button("alertDialog") {
                onClick {
                    alert {
                        title = "Dialog"
                        message = "AlertDialog"
                        okButton {
                            toast("dialog posotive")
                        }
                        cancelButton {
                            toast("dialog negative")
                        }
                        show()
                    }
                }
            }
            button("login") {
                onClick {
                    startActivity<LoginAvtivity>()
                }
            }
        }
    }

    ///OKHttp3 进行网络请求
    private fun getNetWork()  {
        //创建okHttpClient，也就是okhttp的客户端
        val okHttpClient = OkHttpClient()
        //https://www.tianqiapi.com/api/?version=v1
        //val formBoay = FormBody.Builder().add("version","v1")
        //创建请求，Builder是辅助类，辅助进行网络请求的
        val request = Request.Builder().url("https://www.tianqiapi.com/api/?version=v1").get().build()
        //创建请求队列，将请求放进去
        val call = okHttpClient.newCall(request)
        //请求
        call.enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("TAG","失败")
                Looper.prepare()
                toast("失败 $e")
                Looper.loop() //这种情况下，Runnable对象是运行在子线程中的，可以进行联网操作，但是不能更新UI
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("TAG","成功")
                val result = response.body()?.string()
                Looper.prepare()
                toast("成功 $result")
                Looper.loop()
            }
        })
    }

    //retrofit2进行网络请求
    private  fun useRetrofit(){
        val retrofit = Retrofit.Builder().baseUrl("https://www.baidu.com")
            .addConverterFactory(object : Converter.Factory(){
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *>? {
                    return Converter<ResponseBody,String> { return@Converter it.string() }
                }
            }).build()
        val service = retrofit.create(RetrofitService::class.java)
        service.getBaidu().enqueue(object : retrofit2.Callback<String>{
            override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                Log.d("aulton","retrofit failed msg in ${t.message}")
            }

            override fun onResponse(call: retrofit2.Call<String>, response: retrofit2.Response<String>) {
                Log.d("aulton","retrofit success result is ${response.body()}")
            }
        })
    }
 }

interface RetrofitService{
    @GET(".")
    fun getBaidu():retrofit2.Call<String>
}
