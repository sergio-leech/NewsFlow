package com.example.newsflow.common.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsflow.common.CONSTANT
import com.example.newsflow.common.models.Article
import com.example.newsflow.common.service.NewsService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_INDEX = 1

class NewsPagingSource @Inject constructor(private val newsService: NewsService) :
    PagingSource<Int, Article>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: STARTING_INDEX

        return try {
            val data = newsService.getAllNews(
                "us",
                "business",
                //FIXME  save in Gradle or NDK
                CONSTANT.API_NEWS_KEY,
                position,
                params.loadSize
            )
            LoadResult.Page(
                data = data.articles,
                prevKey = if (params.key == STARTING_INDEX) null else position - 1,
                nextKey = if (data.articles.isEmpty()) null else position + 1

            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}