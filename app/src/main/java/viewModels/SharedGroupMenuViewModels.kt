package viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedGroupMenuViewModels: ViewModel() {
    private val _groupId = MutableLiveData<Int>()
    val groupId: LiveData<Int> = _groupId

    private val _imageUrl = MutableLiveData<String>()
    val imageUrl: LiveData<String> = _imageUrl

    private val _receiptId = MutableLiveData<Int>()
    val receiptId: LiveData<Int> = _receiptId

    private val _creditor = MutableLiveData<String>()
    val creditor: LiveData<String> = _creditor

    fun setGroupId(groupId: Int) {
        _groupId.value = groupId
    }

    fun setImageUrl(imageUrl: String) {
        _imageUrl.value = imageUrl
    }

    fun setReceiptId(receiptId: Int) {
        _receiptId.value = receiptId
    }

    fun setCreditor(creditor: String) {
        _creditor.value = creditor
    }
}