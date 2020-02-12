package com.bignerdranch.android.criminalintent.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bignerdranch.android.criminalintent.Crime
import com.bignerdranch.android.criminalintent.R
import com.bignerdranch.android.criminalintent.getScaledBitmap
import kotlinx.android.synthetic.main.dialog_image.*

private const val FILE_PATH = "file_path"
class ImageDialog: DialogFragment() {
    private lateinit var imageView: ImageView

    fun newInstance(filePath: String): ImageDialog{
        val args = Bundle()
        args.putString(FILE_PATH, filePath)

        val imageDialog = ImageDialog()
        imageDialog.arguments = args

        return imageDialog
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val view = inflater.inflate(R.layout.dialog_image, null)
            val imageView = view.findViewById<ImageView>(R.id.image_zoom_dialog)


            val filePath = arguments?.getString(FILE_PATH)
            if(filePath != null) {
                val bm: Bitmap = getScaledBitmap(filePath, activity!!)
                imageView.setImageBitmap(bm)
            }



            builder.setView(view)


            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")


    }

}