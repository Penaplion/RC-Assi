package multipleroomtables

import androidx.room.*
import androidx.room.Dao
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
    suspend fun insertArticle(article: Article): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonGroupCrossRef(crossRef: PersonGroupCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonArticleCrossRef(crossRef: PersonArticleCrossRef)

    /*
        delete
     */
    @Transaction
    @Query("DELETE FROM 'group' WHERE group_id = :group_id")
    suspend fun deleteGroup(group_id: Int)

    @Transaction
    @Query("DELETE FROM persongroupcrossref WHERE group_id = :group_id")
    suspend fun deletePersonGroupCrossRef(group_id: Int)

    @Transaction
    @Query("DELETE FROM persongroupcrossref WHERE group_id = :group_id AND person_id = :person_id")
    suspend fun deletePersonInGroup(person_id: Int, group_id: Int)

    /*
        update
     */
    @Transaction
    @Query("UPDATE `group` SET groupname = :group_name, memberCount = :memberCount WHERE group_id = :group_id")
    suspend fun updateGroup(group_id: Int, group_name: String, memberCount: Int)

    /*
        getRelation
     */
    @Transaction
    @Query("SELECT * FROM `group` WHERE group_id = :group_id")
    suspend fun getGroupAndEndBalanceWithGroupID(group_id: Int): List<GroupAndEndBalance>

    @Transaction
    @Query("SELECT * FROM person WHERE person_id = :person_id")
    suspend fun getPersonWithReceipts(person_id: Int): List<PersonWithReceipts>

    @Transaction
    @Query("SELECT * FROM receipt WHERE receipt_id = :receipt_id")
    suspend fun getReceiptWithArticles(receipt_id: Int): List<ReceiptWithArticles>

    @Transaction
    @Query("SELECT * FROM `group` WHERE group_id = :group_id")
    suspend fun getGroupWithReceipts(group_id: Int): List<GroupWithReceipts>

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
    @Query("SELECT * FROM receipt")
    suspend fun getReceipts(): List<Receipt>

    @Transaction
    @Query("SELECT * FROM article")
    suspend fun getArticles(): List<Article>

    @Transaction
    @Query("SELECT * FROM person")
    suspend fun getPersons(): List<Person>

    @Transaction
    @Query("SELECT * FROM 'group' WHERE group_id=:group_id")
    suspend fun getGroupByID(group_id: Int): Group

    /*
        isTableEmpty & getSingleEntries & tableContains
     */
    @Transaction
    @Query("SELECT count(1) FROM person WHERE person_name = :person_name")
    suspend fun isInTablePersons(person_name: String): Int

    @Transaction
    @Query("SELECT * FROM person WHERE person_name = :person_name")
    suspend fun getPersonByName(person_name: String): Person

    @Transaction
    @Query("SELECT * FROM person WHERE person_id = :person_id")
    suspend fun getPersonById(person_id: Int): Person

    @Transaction
    @Query("SELECT EXISTS(SELECT * FROM person WHERE person_name = :person_name)")
    suspend fun personsContain(person_name: String): Boolean

    @Transaction
    @Query("SELECT EXISTS(SELECT * FROM persongroupcrossref WHERE person_id = (SELECT person_id FROM person WHERE person_name = :person_name)  AND group_id = :group_id)")
    suspend fun groupContainsPerson(person_name: String, group_id: Int): Boolean

    @Transaction
    @Query ("SELECT person_id FROM person WHERE person_name =:person_name")
    suspend fun  getPersonIDByName(person_name : String): Int
}