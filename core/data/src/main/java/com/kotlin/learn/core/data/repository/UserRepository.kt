package com.kotlin.learn.core.data.repository

import com.kotlin.learn.core.common.util.network.Result
import com.kotlin.learn.core.model.BaseResponse
import com.kotlin.learn.core.model.LoginRespModel
import com.kotlin.learn.core.model.RegisterRespModel
import com.kotlin.learn.core.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    // TODO : start region to spring backend
    // ===============================================================

    fun getUser(model: UserModel): Flow<Result<BaseResponse<UserModel>>>

    fun postUser(model: UserModel): Flow<Result<BaseResponse<RegisterRespModel>>>

    fun putUser(model: UserModel): Flow<Result<BaseResponse<RegisterRespModel>>>

    fun postAuth(model: UserModel): Flow<Result<BaseResponse<LoginRespModel>>>


    // TODO : start region to firebase
    // ===============================================================

    fun storeUserToFirebase(
        model: UserModel,
        onSuccess: (String) -> Unit,
        onError: () -> Unit
    ): Flow<Result<Unit>>

    fun <Z : Any> fetchUserFromFirebase(
        id: String,
        resources: Z,
        onSuccess: Z.() -> Unit,
        onError: (String) -> Unit
    ): Flow<Result<Any?>>


    // TODO : start region to firestore
    // ===============================================================

    fun storeUserToFirestore(
        id: String,
        model: UserModel,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ): Flow<Result<Any?>>

    fun <Z : Any> fetchUserFromFirestore(
        id: String,
        resources: Z,
        onSuccess: (Z) -> Unit,
        onError: (String) -> Unit
    ): Flow<Result<Any?>>

}