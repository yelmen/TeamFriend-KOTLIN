package com.yelmen.varmisin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yelmen.varmisin.databinding.ActivityIlanverBinding
import kotlinx.android.synthetic.main.activity_ilanver.*

class ilanverActivity : AppCompatActivity() {
    private lateinit var editText: EditText
   // private lateinit var addButton: Button
    //private lateinit var chipGroup: ChipGroup
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database:FirebaseDatabase?=null
    lateinit var binding: ActivityIlanverBinding
    /*private fun addChip(text:String){
        val chip=Chip(this)
        chip.text=text
        chip.isCloseIconVisible=true
        chip.setChipIconResource(R.drawable.ic_baseline_location_on_24)
        chip.setOnCloseIconClickListener {
            chipGroup.removeView(chip)
        }
        chipGroup.addView(chip)
    }*/
    override fun onCreate(savedInstanceState: Bundle?) {
        val intent = intent
        val adsoyad = intent.getStringExtra("adisoyadi")
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        super.onCreate(savedInstanceState)
        val binding = ActivityIlanverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.butonkaydet.setOnClickListener {
            var detay = binding.ilandetay.text.toString()
            var baslik = binding.ilanbaslik.text.toString()
            if (TextUtils.isEmpty(detay)) {
                binding.ilandetay.error = "Lütfen İlan Detayı Ekleyiniz"
            }
            if (TextUtils.isEmpty(baslik)) {
                binding.ilanbaslik.error = "Lütfen Başlık Ekleyiniz"
            } else {
                var currentuser = auth.currentUser
                //kullanici idsini al aynı id de veri kaydet
                var currentuserDb = currentuser?.let { it1 -> databaseReference?.child(it1.uid) }
                currentuserDb?.child("ilanil")?.setValue(binding.showtext.text.toString())
                currentuserDb?.child("ilanbaslik")?.setValue(binding.ilanbaslik.text.toString())
                currentuserDb?.child("ilandetay")?.setValue(binding.ilandetay.text.toString())
                currentuserDb?.child("telefonno")?.setValue(binding.ilanvertelefon.text.toString())
                Toast.makeText(this, "İlanınız Yayınlanmıştır.", Toast.LENGTH_LONG).show()
            }
            startActivity(Intent(this, ilanlarActivity::class.java))
            finish()
        }



        binding.ilanverharitagit.setOnClickListener {
            val intent=Intent(this,HaritalarActivity::class.java)
            startActivity(intent)
        }

    }

}