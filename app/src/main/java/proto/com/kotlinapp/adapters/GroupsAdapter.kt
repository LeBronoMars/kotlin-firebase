package proto.com.kotlinapp.adapters

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import proto.com.kotlinapp.commons.AdapterConstants
import proto.com.kotlinapp.delegates.GroupDelegateAdapter
import proto.com.kotlinapp.interfaces.ViewType
import proto.com.kotlinapp.interfaces.ViewTypeDelegateAdapter
import proto.com.kotlinapp.models.Group

/**
 * Created by rsbulanon on 5/24/17.
 */
class GroupsAdapter(val listener: GroupDelegateAdapter.OnSelectGroupListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()

    init {
        delegateAdapters.put(AdapterConstants.GROUPS, GroupDelegateAdapter(listener))
        items = ArrayList()
        Log.d("rv", "initialized")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, this.items[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].getViewType()
    }

    fun addGroups(groups: List<Group>) {
        // first remove loading and notify
        val initPosition = items.size - 1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        // insert news and the loading at the end of the list
        items.addAll(groups)
        notifyItemRangeChanged(initPosition, items.size + 1 /* plus loading item */)
    }

    fun addGroup(group: Group) {
        items.add(group)
        notifyDataSetChanged()
    }

    fun removeGroup(group: Group) {
        items.forEachIndexed { index, viewType ->
            if ((viewType as Group).id.equals(group.id)) {
                items.removeAt(index)
                notifyDataSetChanged()
                return
            }
        }
    }

    fun updateGroup(group: Group) {
        items.forEachIndexed { index, viewType ->
            if ((viewType as Group).id.equals(group.id)) {
                items.set(index, group)
                notifyDataSetChanged()
                return
            }
        }
    }
}