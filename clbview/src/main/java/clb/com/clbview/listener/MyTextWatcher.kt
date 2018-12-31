package clb.com.clbview.listener

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by EMRE CELEBI on 3/9/18. cLB
 */
interface MyTextWatcher : TextWatcher {


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {

    }


}