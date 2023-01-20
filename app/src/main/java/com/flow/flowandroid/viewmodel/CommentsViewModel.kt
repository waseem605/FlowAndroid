package com.flow.flowandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flow.flowandroid.models.WeatherMainModel
import com.flow.flowandroid.network.CommentApiState
import com.flow.flowandroid.network.Status
import com.flow.flowandroid.repository.CommentsRepository
import com.flow.flowandroid.utils.AppConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CommentsViewModel : ViewModel() {

    val appId = "5f3e8f117e64c70e303e1af7d02256e5"
    val latT = "33.5222536"
    val lngT = "73.1540469"

    // Create a Repository and pass the api
    // service we created in AppConfig file
    private val repository = CommentsRepository(
        AppConfig().apiService()
    )

//    private val weatherLiveData = MutableStateFlow<WeatherMainModel>()
    val commentState = MutableStateFlow(
        CommentApiState(
            Status.LOADING,
            WeatherMainModel(null, null, null, null, null), ""
        )
    )


    // Function to get new Comments
    fun getNewComment(lat: String, lng: String) {

        // Since Network Calls takes time,Set the
        // initial value to loading state
        commentState.value = CommentApiState.loading()

        // ApiCalls takes some time, So it has to be
        // run and background thread. Using viewModelScope
        // to call the api
        viewModelScope.launch {
            // Collecting the data emitted
            // by the function in repository
            repository.getComment(latT, lngT, appId)
                // If any errors occurs like 404 not found
                // or invalid query, set the state to error
                // State to show some info
                // on screen
                .catch {
                    commentState.value =
                        CommentApiState.error(it.message.toString())
                }
                // If Api call is succeeded, set the State to Success
                // and set the response data to data received from api
                .collect {model->
                    commentState.value = CommentApiState.success(model.data)
                }
        }
    }
}