package com.kotlin.learn.catalog.core.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kotlin.learn.catalog.core.common.pagingSucceeded
import com.kotlin.learn.catalog.core.data.repository.MovieRepository
import com.kotlin.learn.catalog.core.model.MovieDataModel
import com.kotlin.learn.catalog.core.model.MovieModel
import com.kotlin.learn.catalog.core.model.MovieSearchModel
import com.kotlin.learn.catalog.core.utilities.Constant
import com.kotlin.learn.catalog.core.utilities.extension.replaceIfNull

class SearchPagingSource(
    private val repository: MovieRepository,
    private val searchModel: MovieSearchModel
) : PagingSource<Int, MovieDataModel>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDataModel>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.minus(Constant.ONE)
                ?: state.closestPageToPosition(it)?.nextKey?.plus(Constant.ONE)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDataModel> {
        val page = params.key.replaceIfNull(1)

        return repository.searchMovie(page, searchModel)
            .pagingSucceeded { response -> loadResult(response = response, page = page) }
    }

    private fun loadResult(response: MovieModel, page: Int) =
        LoadResult.Page(
            data = response.results,
            prevKey = if (page != Constant.ONE) page.minus(Constant.ONE) else null,
            nextKey = if (page != response.totalPages) page.plus(Constant.ONE) else null
        )
}