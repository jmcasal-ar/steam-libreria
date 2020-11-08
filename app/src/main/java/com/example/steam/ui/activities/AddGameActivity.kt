package com.example.steam.ui.activities

import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.steam.data.Game
import com.example.steam.R
import com.example.steam.db.GamesDao
import com.example.steam.notifications.GameNotifChannelManager
import com.example.steam.presenters.AddGamePresenter
import com.example.steam.presenters.AddGamePresenterImpl
import com.example.steam.repositories.GameRepositoryImpl
import com.example.steam.ui.activities.interfaces.AddGameView
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.disposables.CompositeDisposable

class AddGameActivity : AppCompatActivity(), AddGameView {
    private lateinit var etName: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var etPrice: TextInputEditText
    private lateinit var etDiscountPrice: TextInputEditText
    private lateinit var btnSave: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var loadingBackground: View
    private lateinit var container: LinearLayout
    private val compositeDisposable = CompositeDisposable()
    private val presenter: AddGamePresenter by lazy {
        AddGamePresenterImpl(
            this,
            GameRepositoryImpl (
                GamesDao(this@AddGameActivity.applicationContext),
                compositeDisposable
            )
        )
    }


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
            progressBar = findViewById(R.id.progressBar)
            loadingBackground = findViewById(R.id.loadingBackground)
            container = findViewById(R.id.containter)


        }

        private fun saveGame() {
            validateData()
            if (isDataValid()) {
                val game = createGameFromInput()
                presenter.doAddGame(game)
                //GamesDao(this@AddGameActivity.applicationContext)
                    //.addGame(createGameFromInput())
                showNotificacionts() //TODO this should be called from presenter
                //finish()
            } else {
                showMessage("Complete all the fields")
            }
        }

        private fun showNotificacionts() {
            GameNotifChannelManager.createNotificationChannelForNewGames(this)

            val intent = Intent(this, GameActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) //Borra lo q tenemos abierto de la app
            }
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            NotificationCompat.Builder(this, GameNotifChannelManager.NEW_GAMES_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("New game added")
                .setContentText("Enter and see the new game add with discounts")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
                .also {
                        notification ->
                    NotificationManagerCompat
                        .from(this)
                        .notify(1, notification)
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

        override fun showLoading() {
            progressBar.visibility = View.VISIBLE
            loadingBackground.visibility = View.VISIBLE
            container.visibility = View.GONE

        }

        override fun hideLoading() {
            progressBar.visibility = View.GONE
            loadingBackground.visibility = View.GONE
            container.visibility = View.VISIBLE
        }

        override fun goBack() {
            finish()
        }

        override fun showErrorMessage() {
            showMessage(getString(R.string.error_addingGame))
        }
    }
