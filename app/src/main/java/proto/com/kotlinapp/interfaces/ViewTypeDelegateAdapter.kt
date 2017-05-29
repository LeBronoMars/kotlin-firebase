package proto.com.kotlinapp.interfaces

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * Created by rsbulanon on 5/22/17.
 */
interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, position: Int)
}