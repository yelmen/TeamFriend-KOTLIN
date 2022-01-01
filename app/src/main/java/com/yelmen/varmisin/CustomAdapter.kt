package com.yelmen.varmisin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val userList: ArrayList<listedegosterdata>,val context:Context) :
        RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
      val tvadsoyad:TextView=view.findViewById(R.id.tvadsoyad)
        val tvbaslik:TextView=view.findViewById(R.id.tvbaslik)
        val tvdetay:TextView=view.findViewById(R.id.tvdetay)
        val tvil:TextView=view.findViewById(R.id.tvil)
        val tvtelefonno: TextView=view.findViewById(R.id.tvtelefonno)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.ilanlarlistesi, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.tvadsoyad.text=userList[position].adisoyadi
        viewHolder.tvbaslik.text=userList[position].ilanbaslik
        viewHolder.tvdetay.text=userList[position].ilandetay
        viewHolder.tvil.text=userList[position].ilanil
        viewHolder.tvtelefonno.text=userList[position].telefonno
        viewHolder.itemView.setOnClickListener {
            //tıklananı al
            var user= userList[position]
            var adisoyadi:String?=user.adisoyadi
            var baslik:String?=user.ilanbaslik
            var detay:String?=user.ilandetay
            var il:String?=user.ilanil
            var tarih:String?=user.tarih
            var telefonno:String?=user.telefonno
            var intent=Intent(context,DetayActivity::class.java)
            intent.putExtra("adisoyadi",adisoyadi)
            intent.putExtra("baslik",baslik)
            intent.putExtra("detay",detay)
            intent.putExtra("il",il)
            intent.putExtra("tarih",tarih)
            intent.putExtra("telefonno",telefonno)
            context.startActivity(intent)
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = userList.size

}