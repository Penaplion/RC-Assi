package multipleroomtables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import multipleroomtables.entities.*
import multipleroomtables.entities.relations.*

@Dao
interface Dao {
    /*
        inserts
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: Group)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEndBalance(endBalance: EndBalance)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receipt: Receipt)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonGroupCrossRef(crossRef: PersonGroupCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonArticleCrossRef(crossRef: PersonArticleCrossRef)

    /*
        getRelation
     */
    @Transaction
    @Query("SELECT * FROM 'group' WHERE group_id = :group_id")
    suspend fun getGroupAndEndBalanceWithGroupID(group_id: Int): List<GroupAndEndBalance>

    @Transaction
    @Query("SELECT * FROM person WHERE person_id = :person_id")
    suspend fun getPersonWithReceipts(person_id: Int): List<PersonWithReceipts>

    @Transaction
    @Query("SELECT * FROM receipt WHERE receipt_id = :receipt_id")
    suspend fun getReceiptWithArticles(receipt_id: Int): List<ReceiptWithArticles>

    /*
        getCross-Ref
     */
    @Transaction
    @Query("SELECT * FROM article WHERE article_id = :article_id")
    suspend fun getPersonsOfArticle(article_id: Int): List<ArticleWithPersons>

    @Transaction
    @Query("SELECT * FROM person WHERE person_id = :person_id")
    suspend fun getArticlesOfPerson(person_id: Int): List<PersonWithArticles>

    @Transaction
    @Query("SELECT * FROM 'group' WHERE group_id = :group_id")
    suspend fun getPersonsOfGroup(group_id: Int): List<GroupWithPersons>

    @Transaction
    @Query("SELECT * FROM person WHERE person_id = :person_id")
    suspend fun getGroupsOfPerson(person_id: Int): List<PersonWithGroups>

    /*
        getTable
     */
    @Transaction
    @Query("SELECT * FROM 'group'")
    suspend fun getGroups(): List<Group>

    @Transaction
    @Query("SELECT * FROM person")
    suspend fun getPersons(): List<Person>

    /*
        isTableEmpty & getSingleEntries
     */
    @Transaction
    @Query("SELECT count(1) FROM person WHERE person_name = :person_name")
    suspend fun isInTablePersons(person_name: String): Int

    @Transaction
    @Query("SELECT * FROM person WHERE person_name = :person_name")
    suspend fun getPerson(person_name: String): Person

}