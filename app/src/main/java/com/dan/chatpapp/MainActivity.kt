package com.dan.chatpapp

import android.app.ProgressDialog
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.dan.chatpapp.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private lateinit var binding: ActivityMainBinding
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    lateinit var username:String
    lateinit var email:String
    lateinit var numberofphone:String
    val databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatpapp-10739-default-rtdb.asia-southeast1.firebasedatabase.app")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        util()

        binding.rvMain.setHasFixedSize(true)
        binding.rvMain.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        val processDialog = ProgressDialog(this)
        processDialog.setCancelable(false)
        processDialog.setMessage("Loading.......")
        processDialog.show()
        databaseReference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val profileUrl = snapshot.child("users").child(username).child("profile_pic").getValue(
                    String.Companion::class.java
                )
                if(profileUrl != null){
                    Glide.with(applicationContext).load(profileUrl).into(binding.imgProfileMain)
                }
                processDialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                processDialog.dismiss()
            }

        })
    }

    private fun util() {
        username = intent.getStringExtra("username")!!
        email = intent.getStringExtra("email")!!
        numberofphone = intent.getStringExtra("numberofphone")!!
    }
}