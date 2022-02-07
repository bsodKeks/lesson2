package com.als.l2.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.als.l2.R
import com.als.l2.databinding.MainActivityBinding
import com.als.l2.experiment.ContactFragment
import com.als.l2.experiment.TestForeground
import com.als.l2.presentation.history.HistoryFragment
import com.als.l2.presentation.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
//        binding.ok.setOnClickListener(clickListener)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            showMainFragment()
//            showFragment(ThreadsFragment.newInstance())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                showFragmentWithBackStack(HistoryFragment.newInstance())
                //startService(Intent(this, TestForeground::class.java))
                true
            }
            R.id.menu_contacts -> {
                showFragmentWithBackStack(ContactFragment.newInstance())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun showFragmentWithBackStack(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showMainFragment() {
        showFragment(MainFragment.newInstance())
    }

//    var clickListener: View.OnClickListener = object : View.OnClickListener {
//
//        @RequiresApi(Build.VERSION_CODES.N)
//        override fun onClick(v: View?) {
//            try {
//                val uri = URL(binding.url.text.toString())
//                val handler = Handler() //Запоминаем основной поток
//                Thread {
//                    var urlConnection: HttpsURLConnection? = null
//                    try {
//                        urlConnection = uri.openConnection() as HttpsURLConnection
//                        urlConnection.requestMethod =
//                            "GET" // установка метода получения данных — GET
//                        urlConnection.readTimeout = 10000 // установка таймаута — 10 000 миллисекунд
//                        val reader =
//                            BufferedReader(InputStreamReader(urlConnection.inputStream)) // читаем  данные в поток
//                        val result = getLines(reader)
//                        // Возвращаемся к основному потоку
//                        handler.post {
//                            binding.webview.loadDataWithBaseURL(null, result, "text/html; charset=utf-8", "utf-8", null)
//                        }
//
//                    } catch (e: Exception) {
//                        Log.e("", "Fail connection", e)
//                        e.printStackTrace()
//                    } finally {
//                        urlConnection?.disconnect()
//                    }
//                }.start()
//            } catch (e: MalformedURLException) {
//                Log.e("", "Fail URI", e)
//                e.printStackTrace()
//            }
//        }
//
//        @RequiresApi(Build.VERSION_CODES.N)
//        private fun getLines(reader: BufferedReader): String {
//            return reader.lines().collect(Collectors.joining("\n"))
//        }
//    }
}
