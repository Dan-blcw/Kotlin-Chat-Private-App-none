package com.dan.chatpapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dan.chatpapp.databinding.ActivityRegisterAccountBinding
import com.google.firebase.database.*

private lateinit var binding: ActivityRegisterAccountBinding
@Suppress("DEPRECATION")
class RegisterAccount : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val processDialog = ProgressDialog(this)
        processDialog.setCancelable(false)
        processDialog.setMessage("Loading.......")

        if(!MemoryData.getData(this).isEmpty()){
            val intent = Intent(this@RegisterAccount,MainActivity::class.java)
            intent.putExtra("email","")
            intent.putExtra("username",MemoryData.getData(this))
            intent.putExtra("numberofphone","")
            startActivity(intent)
            finish()
        }

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://chatpapp-10739-default-rtdb.asia-southeast1.firebasedatabase.app")
        binding.btnRegister.setOnClickListener {
            processDialog.show()
            val email = binding.edtGmail.text.toString()
            val userphone = binding.edtUserPhone.text.toString()
            val password = binding.edtPassword.text.toString()
            val numberofphone = binding.edtRepassword.text.toString()

            if(email.isEmpty() || userphone.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"All blanks must be filled in !!",Toast.LENGTH_LONG).show()
                processDialog.dismiss()
            }else{
                databaseReference.addListenerForSingleValueEvent(object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        processDialog.dismiss()
                        if(snapshot.child("users").hasChild(userphone)){
                            Toast.makeText(this@RegisterAccount,"User Phone already exists", Toast.LENGTH_LONG).show()
                        }else{
                            databaseReference.child("users").child(userphone).child("email").setValue(email)
                            databaseReference.child("users").child(userphone).child("numberofphone").setValue(numberofphone)
                            databaseReference.child("users").child(userphone).child("profile_pic").setValue("")

                            MemoryData.savaData(userphone,this@RegisterAccount)

                            Toast.makeText(this@RegisterAccount,"Sign Up Success !!!", Toast.LENGTH_LONG).show()
                            val intent = Intent(this@RegisterAccount,MainActivity::class.java)
                            intent.putExtra("email",email)
                            intent.putExtra("username",userphone)
                            intent.putExtra("numberofphone",numberofphone)
                            startActivity(intent)
                            finish()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        processDialog.dismiss()
                    }

                })
            }
        }
    }
}