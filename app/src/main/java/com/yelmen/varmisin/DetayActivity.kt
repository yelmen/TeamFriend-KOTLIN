package com.yelmen.varmisin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.yelmen.varmisin.databinding.ActivityDetayBinding
import kotlinx.android.synthetic.main.activity_detay.*

class DetayActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding=ActivityDetayBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val intent=intent
        var adisoyadi=intent.getStringExtra("adisoyadi")
        var baslik=intent.getStringExtra("baslik")
        var detay=intent.getStringExtra("detay")
        var il=intent.getStringExtra("il")
        var telefonno=intent.getStringExtra("telefonno")
        binding.detayil.text=il.toString()
        binding.detayadsoyad.text=adisoyadi.toString()
        binding.detaybaslik.text=baslik.toString()
        binding.detaydetay.text=detay.toString()
        binding.detaytelefonno.text=telefonno.toString()
    }
    fun haritagit(view: View){
        val intent=Intent(this,HaritalarActivity::class.java)
        startActivity(intent)
    }
}