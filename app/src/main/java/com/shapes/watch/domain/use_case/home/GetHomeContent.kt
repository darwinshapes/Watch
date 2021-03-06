package com.shapes.watch.domain.use_case.home

import com.shapes.watch.common.Resource
import com.shapes.watch.domain.model.HomeContent
import com.shapes.watch.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetHomeContent @Inject constructor(
    private val repository: VideoRepository
) {
    operator fun invoke(): Flow<Resource<HomeContent>> = flow {
        emit(Resource.Loading())

        try {
            val content = repository.getHomeContent()
            emit(Resource.Success(content))
        } catch (e: IOException) {
            emit(Resource.Error(e))
        }
    }
}
