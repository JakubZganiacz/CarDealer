package org.wit.placemark.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.wit.placemark.R
import org.wit.placemark.helpers.readImage
import org.wit.placemark.helpers.readImageFromPath
import org.wit.placemark.helpers.showImagePicker
import org.wit.placemark.main.MainApp
import org.wit.placemark.models.Location
import org.wit.placemark.models.PlacemarkModel

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {

  var placemark = PlacemarkModel()
  var edit = false
  lateinit var app: MainApp
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)


    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    info("Placemark Activity started..")

    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }

    app = application as MainApp

    if (intent.hasExtra("placemark_edit")) {
      edit = true
      btnAdd.setText(R.string.save_dealer)
      placemark = intent.extras?.getParcelable<PlacemarkModel>("placemark_edit")!!
      placemarkTitle.setText(placemark.title)
      address.setText(placemark.address)
      website.setText(placemark.website)
      phone.setText(placemark.phone)
      dealerImage.setImageBitmap(readImageFromPath(this, placemark.image))

      if (placemark.image != null) {
        chooseImage.setText(R.string.change_dealer_image)
      }
    }

    btnAdd.setOnClickListener() {
      placemark.title = placemarkTitle.text.toString()
      placemark.address = address.text.toString()
      placemark.website = website.text.toString()
      placemark.phone = phone.text.toString()
      if (placemark.title.isEmpty()) {
        toast(R.string.enter_placemark_title)
      } else {
        if (edit) {
          app.placemarks.update(placemark.copy())
        } else {
          app.placemarks.create(placemark.copy())
        }
      }
      info("add Button Pressed: $placemarkTitle")
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }


      dealerLocation.setOnClickListener {
          val location = Location(52.245696, -7.139102, 15f)
          if (placemark.zoom != 0f) {
              location.lat =  placemark.lat
              location.lng = placemark.lng
              location.zoom = placemark.zoom
          }
          startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
      }

  }
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.placemark_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }
  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> {
        finish()
      }
      R.id.item_delete -> {
          app.placemarks.delete(placemark)
          finish()
      }
    }
    return super.onOptionsItemSelected(item)
  }
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(requestCode, resultCode, data)
      when (requestCode) {
          IMAGE_REQUEST -> {
              if (data != null) {
                  placemark.image = data.getData().toString()
                  dealerImage.setImageBitmap(readImage(this, resultCode, data))
                  chooseImage.setText(R.string.change_dealer_image)
              }
          }
          LOCATION_REQUEST -> {
              if (data != null) {
                  val location = data.extras?.getParcelable<Location>("location")
                  if (location != null) {
                      placemark.lat = location.lat
                      placemark.lng = location.lng
                      placemark.zoom = location.zoom
                  }

              }
          }
      }
  }


}

