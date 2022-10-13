package com.mycompany.footballist.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mycompany.footballist.R
import com.mycompany.footballist.model.news.Article
import com.mycompany.footballist.utils.downloadFromUrl
import com.mycompany.footballist.utils.placeHolderProgressBar
import kotlinx.android.synthetic.main.activity_news_details.*

class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var articleDetails : Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        if(intent.hasExtra("articleDetails")){
            articleDetails = intent.getParcelableExtra<Article>("articleDetails")!!
        }

        webViewSetup(articleDetails.url!!)

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup(webUrl: String){
        wb_webView.webViewClient = WebViewClient()
        wb_webView.apply {
            loadUrl(webUrl)
            settings.javaScriptEnabled = true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(wb_webView.canGoBack()){
            wb_webView.goBack()
        }
        else{
            super.onBackPressed()
        }
    }

    inner class WebViewClient : android.webkit.WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            wb_webView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }


}