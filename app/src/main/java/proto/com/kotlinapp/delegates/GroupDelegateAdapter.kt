package proto.com.kotlinapp.delegates

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_group.view.*
import proto.com.kotlinapp.R
import proto.com.kotlinapp.commons.inflate
import proto.com.kotlinapp.interfaces.ViewType
import proto.com.kotlinapp.interfaces.ViewTypeDelegateAdapter
import proto.com.kotlinapp.models.Group

/**
 * Created by rsbulanon on 5/24/17.
 */
class GroupDelegateAdapter(val onSelectGroupListener: OnSelectGroupListener) : ViewTypeDelegateAdapter {

    interface OnSelectGroupListener {
        fun onSelectedGroup(group: Group, positio: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return GroupViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType, position: Int) {
        holder as GroupViewHolder
        holder.bind(item as Group, position)
    }

    inner class GroupViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.row_group)) {

        fun bind(group: Group, position: Int) = with(itemView) {
            tv_group_name.text = group.groupName
            tv_group_description.text = group.groupDescription
            super.itemView.setOnClickListener { onSelectGroupListener.onSelectedGroup(group, position)}
        }
    }
}