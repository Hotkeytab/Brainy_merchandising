package com.example.brainymerchandising.Display.Adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.brainymerchandising.Activities.PrimeActivity
import com.example.brainymerchandising.Display.Display_Fragment
import com.example.brainymerchandising.Display.Model.DisplayCustomFields
import com.example.brainymerchandising.Display.Model.Post.CustomFieldValue
import com.example.brainymerchandising.databinding.DropdownItemBinding


class edittext_adapter(
    private val
    liste_display: ArrayList<DisplayCustomFields>,
    displayTypeId: Int,
    displayFragment: Display_Fragment,
    position: Int
) : RecyclerView.Adapter<edittext_ViewHolder>() {
    private val liste_display_Recycle_adapter = liste_display
    private val displayTypeId = displayTypeId
    private val displayFragment = displayFragment
    private val position = position


    interface edittext_adapterListener {
        fun onClickedEditText(position: Int)
    }

    private val items = ArrayList<DisplayCustomFields>()
    private val items_Edittext_values = ArrayList<CustomFieldValue>()


    fun setItems(items: ArrayList<DisplayCustomFields>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setItemsValues(items_value: ArrayList<CustomFieldValue>) {
        this.items_Edittext_values.clear()
        this.items_Edittext_values.addAll(items_value)
        notifyDataSetChanged()
    }

    fun clear() {
        val size: Int = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): edittext_ViewHolder {
        val binding: DropdownItemBinding =
            DropdownItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)




        return edittext_ViewHolder(
            binding,
            parent,
            liste_display_Recycle_adapter, displayTypeId, displayFragment, position

        )
    }

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: edittext_ViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class edittext_ViewHolder(
    private val itemBinding: DropdownItemBinding,
    private val activityIns: ViewGroup,
    private var parent: ArrayList<DisplayCustomFields>,
    displayTypeId: Int,
    displayFragment: Display_Fragment,
    position14: Int,

    ) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {
    private lateinit var customFieldValue: CustomFieldValue
    private var position14 = position14

    val displayTypeId = displayTypeId
    val displayFragment = displayFragment
    var compteur: Int = -1

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: DisplayCustomFields) {
        itemBinding.edittextIsplay.hint = item.name


        itemBinding.edittextIsplay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            }

            override fun afterTextChanged(editable: Editable) {
                Log.d(position14.toString(), "parent")
                customFieldValue = CustomFieldValue(
                    item.id,
                    editable.toString(),
                    "2022",
                    "2022",
                    item.id,
                    displayTypeId,
                    item.displaySectionId,
                    item.name,
                    item.type
                )

                if ((displayFragment.activity as PrimeActivity).tab_CustomFieldValues1!!.isEmpty()) {

                    (displayFragment.activity as PrimeActivity).tab_CustomFieldValues1!!
                        .put(item.id, customFieldValue)

                } else {
                    if ((displayFragment.activity as PrimeActivity).tab_CustomFieldValues1!!.containsKey(
                            item.id
                        )
                    ) {

                        (displayFragment.activity as PrimeActivity).tab_CustomFieldValues1!!
                            .replace(item.id, customFieldValue)


                    } else {

                        (displayFragment.activity as PrimeActivity).tab_CustomFieldValues1!!
                            .put(item.id, customFieldValue)
                    }


                }


            }


        })


    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }


}
