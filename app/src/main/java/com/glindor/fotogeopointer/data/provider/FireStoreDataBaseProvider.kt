package com.glindor.fotogeopointer.data.provider

import androidx.lifecycle.MutableLiveData
import com.glindor.fotogeopointer.data.entity.Point
import com.glindor.fotogeopointer.data.entity.User
import com.glindor.fotogeopointer.data.error.NotAuthentication
import com.glindor.fotogeopointer.data.model.DataResult
import com.glindor.fotogeopointer.utils.Logger
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FireStoreDataBaseProvider : IDataProvider {

    companion object{
        const val POINT_COLLECTION_NAME = "points"
        const val USER_COLLECTION_NAME = "users"
    }
    private val storage by lazy { FirebaseFirestore.getInstance() }
    private val currentUser get() = FirebaseAuth.getInstance().currentUser

    private val pointsCollection: CollectionReference
    get() = currentUser?.let {
        Logger.d(this,"pointsCollection call get() ")
        storage.collection(USER_COLLECTION_NAME)
            .document(it.uid)
            .collection(POINT_COLLECTION_NAME)
    }?: throw NotAuthentication()


    override fun getCurrentUser() =  MutableLiveData<DataResult>().apply {
        try {
            currentUser?.let{Logger.d(null,"FireStoreDataBaseProvider: currentUser есть")}
                    ?: run { Logger.d(null,"FireStoreDataBaseProvider: currentUser нет") }
            value = currentUser?.let { fbUser ->
                Logger.d(null,"FireStoreDataBaseProvider: getCurrentUser fbUser = ${fbUser.email}")
                DataResult.Success(User(fbUser.displayName ?: "", fbUser.email ?: ""))
            } ?: throw NotAuthentication()
        } catch (e: Throwable) {
            Logger.d(null,"FireStoreDataBaseProvider:getCurrentUser Exception $e")
            value = DataResult.Error(e)
        }
    }

    override fun getPoints() = MutableLiveData<DataResult>().apply {
        try {
            pointsCollection.addSnapshotListener { querySnapshot, error ->
                error?.let {
                    throw it
                } ?: let {
                    value = DataResult.Success(
                        querySnapshot
                            ?.map { it.toObject(Point::class.java) }
                            ?.sortedBy { it.name })
                }
            }
        } catch (e: Throwable) {
            Logger.d(null,"FireStoreDataBaseProvider: getPoints Exception $e")
            value = DataResult.Error(e)
        }
    }

    override fun getPoint(id: String) = MutableLiveData<DataResult>().apply {
        try {
            pointsCollection.document(id).get()
                .addOnSuccessListener {
                    value = DataResult.Success(it.toObject(Point::class.java))
                }
                .addOnFailureListener {
                    throw it
                }
        } catch (e: Throwable) {
            Logger.d(null,"FireStoreDataBaseProvider: getPoint Exception $e")
            value = DataResult.Error(e)
        }
    }

    override fun addPoint(point: Point) = MutableLiveData<DataResult>().apply{
        try {
            pointsCollection.document(point.id).set(point).addOnSuccessListener {
                value = DataResult.Success(point)
            }.addOnFailureListener {
                throw it
            }
        } catch (e: Throwable) {
            value = DataResult.Error(e)
        }

    }


}