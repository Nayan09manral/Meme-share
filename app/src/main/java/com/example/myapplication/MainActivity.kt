package com.example.myapplication

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.substring
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currUrl:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()

    }

    fun loadmeme(){

        ProgressBar.visibility = View.VISIBLE


        currUrl = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currUrl, null ,
            { response ->
                // Display the first 500 characters of the response string.
                 currUrl = response.getString("url")
                Glide.with(this).load(currUrl).listener(object: RequestListener<Drawable>{

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                       ProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        ProgressBar.visibility = View.GONE
                        return false

                    }

                }).into(memeimageView)

            },
            {
                Toast.makeText(this,"something went wrong !",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun NextButton(view: View) {
        loadmeme()
    }
    fun ShareButton(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type ="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "A funny Meme check this out : ${currUrl}")
        val chooser = Intent.createChooser(intent,"Share THis MEME with MEME APP")
        startActivity(chooser)
    }

}