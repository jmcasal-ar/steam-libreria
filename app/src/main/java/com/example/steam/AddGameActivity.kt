package com.example.steam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.steam.db.GamesRpository
import com.google.android.material.textfield.TextInputEditText

class AddGameActivity : AppCompatActivity() {
    private lateinit var etName: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var etPrice: TextInputEditText
    private lateinit var etDiscountPrice: TextInputEditText
    private lateinit var btnSave: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)

        setupUI()
    }

    private fun setupUI() {
        etName = findViewById(R.id.etName)
        etDescription = findViewById(R.id.etDescription)
        etPrice = findViewById(R.id.etPrice)
        etDiscountPrice = findViewById(R.id.etDiscountPrice)
        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener{ saveGame() }
    }

    private fun saveGame() {
        validateData()
        if (isDataValid()) {
            GamesRpository(this@AddGameActivity.applicationContext)
                .addGame(createGameFromInput())
            showMessage("Game added")
            finish()
        } else {
            showMessage("Complete all the fields")
        }
    }

    private fun createGameFromInput(): Game {
        return Game(
            R.drawable.ic_launcher_foreground,
            getTextFrom(etName),
            getPercentDiscount(),
            getTextFrom(etDiscountPrice),
            getTextFrom(etPrice),
            getTextFrom(etDescription)
        )
    }

    private fun getPercentDiscount(): String {
        val price = getTextFrom(etPrice).toDouble()
        val discount = getTextFrom(etDiscountPrice).toDouble()
        return  (price * discount / 100).toString()
    }

    private fun showMessage(s: String) =
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()


    private fun isDataValid() =
        etName.error.isNullOrEmpty() && etPrice.error.isNullOrEmpty() && etDiscountPrice.error.isNullOrEmpty()

    private fun validateData() {
        validateName()
        validatePrice()
        validateDiscountPrice()
    }

    private fun validateDiscountPrice() {
        val discountPrice = getTextFrom(etDiscountPrice)
        val price = getTextFrom(etPrice)
        if (getTextFrom(etDiscountPrice).isEmpty()){
            etDiscountPrice.error = "Fill Price"
        }else if (price.isNotEmpty() &&
                price.toDouble() < discountPrice.toDouble()){
            etDiscountPrice.error ="Discount price must be lower than price"
        }
    }

    private fun validatePrice() {
        if (getTextFrom(etPrice).isEmpty()) {
            etName.error = "Fill price"
        }
    }

    private fun getTextFrom(editText: EditText) = editText.text.toString()


        private fun validateName() {
        if (getTextFrom(etName).isEmpty()){
            etName.error = "Fill name"
        }
    }
}