package com.flow.flowandroid.repository

import com.flow.flowandroid.models.WeatherMainModel
import com.flow.flowandroid.network.ApiService
import com.flow.flowandroid.network.CommentApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CommentsRepository(private val apiService: ApiService) {

    suspend fun getComment(lat: String,lng:String,appid:String): Flow<CommentApiState<WeatherMainModel>> {
        return flow {

            // get the comment Data from the api
            val comment=apiService.getWeatherDetails(lat,lng,appid)

            // Emit this data wrapped in
            // the helper class [CommentApiState]
            emit(CommentApiState.success(comment))
        }.flowOn(Dispatchers.IO)
    }
}