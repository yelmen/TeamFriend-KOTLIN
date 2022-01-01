package com.yelmen.varmisin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.yelmen.varmisin.databinding.ActivityMainBinding

class profilActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        databaseReference=database?.reference!!.child("profile")
        //hangi kullanıcı girdiğini öğren
        var currentuser=auth.currentUser
        binding.profilmail.text=" "+currentuser?.email
        //realtime databeseye bağlan id eşleştir hangi kullanıcı girdiyse onun bilgilerini göster
        var userReference=databaseReference?.child(currentuser?.uid!!)
        userReference?.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.profilisim.text=" "+snapshot.child("adisoyadi").value.toString()
                binding.profilbio.text=""+snapshot.child("biyografi").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })


    }
    fun hesapayarlari(view: View){
        val intent=Intent(this,hesapAyarlariActivity::class.java)
        startActivity(intent)
    }
    fun git(view: View){
        val intent=Intent(this,ilanverActivity::class.java)
        startActivity(intent)
    }
    fun ilanlaragit(view: View){
        val intent=Intent(this,ilanlarActivity::class.java)
        startActivity(intent)
    }

}