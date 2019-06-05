package com.example.ankodemo

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.View
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class LoginAvtivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainUI().setContentView(this@LoginAvtivity)
    }
}

class MainUI:AnkoComponent<LoginAvtivity>{
    override fun createView(ui: AnkoContext<LoginAvtivity>) = with(ui) {
        verticalLayout {
            //这个grativity对应的就是gravity，而在lparams闭包中，gravity对应就是layout_gravity

            gravity = Gravity.CENTER


            lparams(matchParent, matchParent)

           val username =  editText {
                hint = "userName"
                gravity = Gravity.CENTER

            }.lparams(width = dip(250),height = 200)
            val password = editText {
                hint = "password"
                top = 20
                gravity = Gravity.CENTER
            }.lparams(width = dip(250),height = 200)
            button("list"){
                backgroundColor = Color.parseColor("#FF9999")
                alpha = 0.5f
                //点击
                onClick {
                    toast(username.text.toString())
                }
                //长按
                onLongClick {
                    Log.e("uname",username.text.toString())
                    toast("Long Click")
                }
            }
        }
    }
}