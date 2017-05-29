package proto.com.kotlinapp.delegates

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import proto.com.kotlinapp.R
import proto.com.kotlinapp.commons.inflate
import proto.com.kotlinapp.interfaces.ViewType
import proto.com.kotlinapp.interfaces.ViewTypeDelegateAdapter

/**
 * Created by rsbulanon on 5/22/17.
 */
class LoadingDelegateAdapter : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class TurnViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_loading))
}