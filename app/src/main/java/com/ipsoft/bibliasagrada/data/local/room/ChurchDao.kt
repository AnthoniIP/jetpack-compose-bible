package com.ipsoft.bibliasagrada.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.ipsoft.bibliasagrada.data.local.room.ChurchDatabase.Companion.ABBREVS_TABLE
import com.ipsoft.bibliasagrada.data.local.room.ChurchDatabase.Companion.BOOKS_TABLE
import com.ipsoft.bibliasagrada.data.local.room.ChurchDatabase.Companion.CHAPTERS_TABLE
import com.ipsoft.bibliasagrada.domain.model.AbbrevRoomModel
import com.ipsoft.bibliasagrada.domain.model.BookResponse
import com.ipsoft.bibliasagrada.domain.model.ChapterResponse

@Dao
interface ChurchDao {

    @Query(ALL_CHAPTERS_QUERY)
    fun getAllChapters(): List<ChapterResponse>

    @Query(ALL_BOOKS_QUERY)
    fun getAllBooks(): List<BookResponse>

    @Query(ALL_ABBREVS_QUERY)
    fun getAllAbbrevs(): List<AbbrevRoomModel>

    @Insert(onConflict = REPLACE)
    fun insertAllBooks(books: List<BookResponse>)

    @Insert(onConflict = REPLACE)
    fun insertAllChapters(vararg chapters: ChapterResponse)

    @Insert(onConflict = REPLACE)
    fun insertAllAbbrevs(vararg abbrevs: AbbrevRoomModel)

    companion object {
        const val ALL_CHAPTERS_QUERY = "SELECT * FROM $CHAPTERS_TABLE"
        const val ALL_BOOKS_QUERY = "SELECT * FROM $BOOKS_TABLE"
        const val ALL_ABBREVS_QUERY = "SELECT * FROM $ABBREVS_TABLE"
    }
}
