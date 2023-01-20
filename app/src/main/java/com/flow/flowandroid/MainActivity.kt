package com.flow.flowandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.flow.flowandroid.databinding.ActivityMainBinding
import com.flow.flowandroid.network.Status
import com.flow.flowandroid.viewmodel.CommentsViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CommentsViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(CommentsViewModel::class.java)


        viewModel.getNewComment("gg","gg")
        // start listening on background thread
        lifecycleScope.launch {

            viewModel.commentState.collect {

                // When state to check the
                // state of received data
                when (it.status) {

                    // If its loading state then
                    // show the progress bar
                    Status.LOADING -> {
                        binding.progressBar.isVisible = true
                        Log.d("MainActivity","data   LOADING")
                    }
                    // If api call was a success , Update the Ui with
                    // data and make progress bar invisible
                    Status.SUCCESS -> {
                        Log.d("MainActivity","data   SUCCESS")
                        binding.progressBar.isVisible = false

                        // Received data can be null, put a check to prevent
                        // null pointer exception
                        it.data?.let { comment ->
                            Log.d("MainActivity","data"+comment)
                            binding.name.text = comment.city?.name.toString()

                        }
                    }
                    // In case of error, show some data to user
                    else -> {
                        Log.d("MainActivity","data   false")
                        binding.progressBar.isVisible = false
                        Toast.makeText(this@MainActivity, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}