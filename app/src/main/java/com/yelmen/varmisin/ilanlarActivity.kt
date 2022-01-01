package com.yelmen.varmisin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.yelmen.varmisin.databinding.ActivityIlanlarBinding

class ilanlarActivity : AppCompatActivity() {
    lateinit var binding: ActivityIlanlarBinding
    private lateinit var dbref:DatabaseReference
    private lateinit var userRecyclerView:RecyclerView
    private lateinit var userArrayList:ArrayList<listedegosterdata>
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding=ActivityIlanlarBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        userRecyclerView=binding.ilanlist
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        userArrayList= arrayListOf<listedegosterdata>()
        getuserdata()
        binding.ilaneklebutonu.setOnClickListener {
            val intent=Intent(this,ilanverActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getuserdata() {
        dbref= FirebaseDatabase.getInstance().getReference("profile")
        dbref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (userSnapshot in snapshot.children){
                        val user=userSnapshot.getValue(listedegosterdata::class.java)
                        userArrayList.add(user!!)
                    }

                    userRecyclerView.adapter=CustomAdapter(userArrayList,this@ilanlarActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }
    fun profilegit(view:View){
        startActivity(Intent(this,profilActivity::class.java))
    }
}