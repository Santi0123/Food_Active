import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.gymactive.databinding.DialogComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.ByteArrayOutputStream

class DialogEditarComida(
    private val posicion: Int,
    private val comida: Comida,
    private val onActualizarComida: (Int, Comida) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogComidaBinding
    private var imagenBase64: String? = null

    private val seleccionarImagen = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.imagenPreview.setImageURI(it)
            convertirImagenABase64(it)
        }
    }

    private val tomarFoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            binding.imagenPreview.setImageBitmap(it)
            imagenBase64 = convertirBitmapABase64(it)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogComidaBinding.inflate(LayoutInflater.from(requireContext()))

        // Rellenamos los campos del formulario con los datos actuales de la comida
        binding.nombrePlato.setText(comida.nombre_plato ?: "Sin nombre")
        binding.descricion.setText(comida.descricion ?: "Sin descripción")

        // Aquí es donde cargamos la imagen correctamente en el ImageView
        comida.image?.let {
            imagenBase64 = it
            // Ahora tratamos la imagen dependiendo del tipo que recibimos
            when {
                it.startsWith("http") -> {
                    // Si es una URL remota, usamos Glide para cargar la imagen
                    Glide.with(requireContext()).load(it).into(binding.imagenPreview)
                }
                it.startsWith("content://") -> {
                    // Si es una URI local, la mostramos directamente
                    binding.imagenPreview.setImageURI(android.net.Uri.parse(it))
                }
                it.length > 100 -> {
                    // Si es Base64, la convertimos a Bitmap y la mostramos
                    loadImage(it, binding.imagenPreview)
                }
                else -> {
                    // Si es un recurso local (por ejemplo, "R.drawable.some_image")
                    val resourceId = it.toIntOrNull()
                    if (resourceId != null) {
                        binding.imagenPreview.setImageResource(resourceId)
                    } else {

                    }
                }
            }
        }

        binding.imagenPreview.setOnClickListener { mostrarOpcionesImagen() }

        val dialog = MaterialAlertDialogBuilder(requireActivity())
            .setView(binding.root)
            .setTitle("Actualizar Comida")
            .setPositiveButton("Aceptar") { _, _ ->
                val nuevoNombre = binding.nombrePlato.text.toString()
                val nuevaDescripcion = binding.descricion.text.toString()

                val comidaActualizada = Comida(
                    nuevoNombre,
                    nuevaDescripcion,
                    imagenBase64 ?: comida.image // Si no se ha actualizado la imagen, mantenemos la original
                )

                onActualizarComida(posicion, comidaActualizada)
                dismiss()
            }
            .setNegativeButton("Cancelar") { _, _ -> dismiss() }
            .create()

        return dialog
    }

    private fun mostrarOpcionesImagen() {
        val opciones = arrayOf("Tomar foto", "Seleccionar de galería")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Elige una opción")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> tomarFoto.launch(null)
                    1 -> seleccionarImagen.launch("image/*")
                }
            }
            .show()
    }

    private fun loadImage(imageData: String, imageView: ImageView) {
        try {
            val decodedBytes = Base64.decode(imageData, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
            imageView.setImageBitmap(bitmap)
        } catch (e: Exception) {
            imageView.setImageResource(android.R.drawable.ic_menu_report_image)
        }
    }

    private fun convertirImagenABase64(uri: android.net.Uri) {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        imagenBase64 = convertirBitmapABase64(bitmap)
    }

    private fun convertirBitmapABase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }
}
