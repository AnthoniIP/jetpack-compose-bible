package com.ipsoft.bibliasagrada.domain.repository

import com.ipsoft.bibliasagrada.data.local.room.ChurchDatabase
import com.ipsoft.bibliasagrada.data.remote.bible.ChurchRoomService
import com.ipsoft.bibliasagrada.domain.common.constants.BIBLE_BASE_URL
import com.ipsoft.bibliasagrada.domain.core.exception.Failure
import com.ipsoft.bibliasagrada.domain.core.function.Either
import com.ipsoft.bibliasagrada.domain.core.plataform.NetworkHandler
import com.ipsoft.bibliasagrada.domain.model.Abbrev
import com.ipsoft.bibliasagrada.domain.model.AbbrevRoomModel
import com.ipsoft.bibliasagrada.domain.model.BookResponse
import com.ipsoft.bibliasagrada.domain.model.ChapterResponse
import javax.inject.Inject

interface BibleRepository : Repository {
    suspend fun getBooks(): Either<Failure, List<BookResponse>>
    suspend fun getChapter(
        bookName: String,
        bookAbbrev: String,
        selectedChapter: Int,
    ): Either<Failure, ChapterResponse>

    class Network
    @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val service: ChurchRoomService,
        private val churchDatabase: ChurchDatabase,
    ) : BibleRepository {

        private val verseUrl = BIBLE_BASE_URL + "verses/nvi/%s/%d"

        override suspend fun getBooks(): Either<Failure, List<BookResponse>> {

            val booksDao = churchDatabase.churchDao()

            val books = booksDao.getAllBooks()

            return if (books.isEmpty()) {
                when (networkHandler.isNetworkAvailable()) {
                    true ->
                        request(
                            service.getBooks()
                        ) { booksResponse ->
                            booksDao.insertAllBooks(booksResponse)
                            booksResponse.forEach {
                                booksDao.insertAllAbbrevs(
                                    AbbrevRoomModel(
                                        bookName = it.name,
                                        abbrev = it.abbrev.pt
                                    )
                                )
                            }
                            booksResponse
                        }
                    false -> Either.Left(Failure.NetworkConnection)
                }
            } else {

                val abbrevs = booksDao.getAllAbbrevs()

                books.forEach { bookResponse ->
                    abbrevs.firstOrNull { it.bookName == bookResponse.name }?.abbrev?.let {
                        bookResponse.abbrev = Abbrev(pt = it)
                    }
                }

                Either.Right(books.distinctBy { it.name })
            }
        }

        override suspend fun getChapter(
            bookName: String,
            bookAbbrev: String,
            selectedChapter: Int,
        ): Either<Failure, ChapterResponse> {

            val bibleDao = churchDatabase.churchDao()

            val chapterDbResponse = bibleDao.getAllChapters()
                .firstOrNull { it.book.name == bookName && it.chapter.number == selectedChapter }

            val abbrevs = bibleDao.getAllAbbrevs()
            val abbrev = abbrevs.firstOrNull { it.bookName == bookName }

            return if (chapterDbResponse == null) {
                when (networkHandler.isNetworkAvailable()) {
                    true ->
                        request(
                            service.getChapter(verseUrl.format(abbrev?.abbrev, selectedChapter))
                        ) { chapterResponse ->
                            bibleDao.insertAllChapters(chapterResponse)
                            chapterResponse
                        }
                    false -> Either.Left(Failure.NetworkConnection)
                }
            } else {
                Either.Right(chapterDbResponse)
            }
        }
    }
}
