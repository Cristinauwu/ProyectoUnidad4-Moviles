package com.rockbass2560.mycamera

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_image_page.*
import java.io.File


class ImagePageFragment(private val photo: File) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_page, container, false)

        val photoImageView = view.findViewById<ImageView>(R.id.photo_imageview)
        val bitmap = BitmapFactory.decodeFile(photo.absolutePath)
        val btnEnviar: FloatingActionButton = view.findViewById(R.id.btn_enviar_whatsapp)
        
        btnEnviar.setOnClickListener {
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.`package` = "com.whatsapp"
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Enviado desde MyCamera")
            whatsappIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context!!,
                "com.rockbass2560.mycamera.fileprovider", photo))
            whatsappIntent.type = "image/jpeg"
            whatsappIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)


            try {
                activity!!.startActivity(whatsappIntent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this.context, "Whatsapp no se encuentra instalado", Toast.LENGTH_SHORT).show()
            }
        }
        
        photoImageView.setImageBitmap(bitmap)

        return view
    }

}