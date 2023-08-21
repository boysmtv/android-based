package com.kotlin.learn.core.network.source

import com.kotlin.learn.core.model.BaseResponse
import com.kotlin.learn.core.model.LoginRespModel
import com.kotlin.learn.core.model.RegisterRespModel
import com.kotlin.learn.core.model.UserModel

interface UserDataSource {

    // TODO : start region to spring backend
    // ===============================================================

    suspend fun getUser(model: UserModel): BaseResponse<UserModel>

    suspend fun postUser(model: UserModel): BaseResponse<RegisterRespModel>

    suspend fun putUser(model: UserModel): BaseResponse<RegisterRespModel>

    suspend fun postAuth(model: UserModel): BaseResponse<LoginRespModel>


    // TODO : start region to firebase
    // ===============================================================

    suspend fun storeUserToFirebase(
        model: UserModel,
        onSuccess: (String) -> Unit,
        onError: () -> Unit
    )

    suspend fun <Z : Any> fetchUserFromFirebase(
        id: String,
        resources: Z,
        onSuccess: Z.() -> Unit,
        onError: (String) -> Unit
    )


    // TODO : start region to firestore
    // ===============================================================

    suspend fun storeUserToFirestore(
        id: String,
        model: UserModel,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    )

    suspend fun <Z : Any> fetchUserFromFirestore(
        id: String,
        resources: Z,
        onSuccess: (Z) -> Unit,
        onError: (String) -> Unit
    )

}