package com.smart.resources.schools_app.core.appDatabase

import androidx.room.*

@Dao
abstract class BaseDao<T> {
    /**
     * Insert an object in the database.
     *
     * @param dataModel the object to be inserted.
     * @return The SQLite row id
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(dataModel: T): Long

    /**
     * Insert an array of objects in the database.
     *
     * @param dataModel the objects to be inserted.
     * @return The SQLite row ids
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(dataModel: List<T>): List<Long>

    /**
     * Update an object from the database.
     *
     * @param dataModel the object to be updated
     */
    @Update
    abstract suspend fun update(dataModel: T)

    /**
     * Update an array of objects from the database.
     *
     * @param dataModel the object to be updated
     */
    @Update
    abstract suspend fun update(dataModel: List<T>)

    /**
     * Delete an object from the database
     *
     * @param dataModel the object to be deleted
     */
    @Delete
    abstract suspend fun delete(dataModel: T)

    /**
     * Delete an array of objects from the database
     *
     * @param dataModel the object to be deleted
     */
    @Delete
    abstract suspend fun delete(dataModel: List<T>)

    @Transaction
    open suspend fun upsert(dataModel: T) {
        val id = insert(dataModel)
        if (id == -1L) {
            update(dataModel)
        }
    }

    @Transaction
    open suspend fun upsert(dataList: List<T>) {
        val insertResult = insert(dataList)
        val updateList: MutableList<T> = mutableListOf()
        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) {
                updateList.add(dataList[i])
            }
        }
        if (updateList.isNotEmpty()) {
            update(updateList)
        }
    }
}