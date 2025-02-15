import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.gymactive.databinding.DialogComidaBinding
import com.example.gymactive.domain.Comidas.models.Comida
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File

class DialogEditarComida(
    private val posicion: Int,
    private val comida: Comida,
    private val onActualizarComida: (Int, Comida) -> Unit
) : DialogFragment() {

    private lateinit var binding: DialogComidaBinding
    private var imagenBase64: String? = null
    private var photoUri: Uri? = null
    private var isImageConverted = false


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogComidaBinding.inflate(LayoutInflater.from(requireContext()))

        // Rellenamos los campos del formulario con los datos actuales de la comida
        binding.nombrePlato.setText(comida.nombre_plato ?: "Sin nombre")
        binding.descricion.setText(comida.descricion ?: "Sin descripción")


        loadImage(comida.image)

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


    private fun loadImage(image:String) {
        Glide.with(this)
            .let {
                when {
                    image.startsWith("http") -> it.load(image) // URL remota
                    image.startsWith("content://") -> it.load(image) // Uri local
                    image.length > 100 -> { // Base64 (suelen ser largas)
                        val decodedBytes = Base64.decode(image, Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                        it.load(bitmap)
                    }
                    else -> it.load(image.toIntOrNull() ?: 0) // Recurso local
                }
            }
            .centerCrop()
            .into(binding.imagenPreview)
    }

    private fun createImageUri(): Uri? {
        val imageFile = File(
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "comida${System.currentTimeMillis()}.jpg"
        )
        return try {
            FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                imageFile
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun mostrarOpcionesImagen() {
        val opciones = arrayOf("Tomar foto", "Seleccionar de galería")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Elige una opción")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> {
                        photoUri = createImageUri()
                        if (photoUri != null) {
                            checkPermissionsAndOpenCamera()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Error al crear la imagen",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    1 -> selectImage.launch("image/*")
                }
            }
            .show()
    }

    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && photoUri != null) {
                binding.imagenPreview.setImageURI(photoUri)
                convertImageToBase64(photoUri!!)
                saveImageToGallery(photoUri!!)  // Guardar en galería
            } else {
                Toast.makeText(requireContext(), "Error al tomar la foto", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri ->
        uri?.let {
            binding.imagenPreview.setImageURI(it)
            convertImageToBase64(it)
        }
    }

    private fun convertImageToBase64(uri: Uri) {
        CoroutineScope(Dispatchers.IO).launch {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val resizedBitmap = resizeBitmap(bitmap, 800, 800) // Ajusta el tamaño según tus necesidades
            imagenBase64 = convertBitmapToBase64(resizedBitmap)

            // Actualizamos el indicador de que la conversión ha terminado
            isImageConverted = true

            withContext(Dispatchers.Main) {
                binding.imagenPreview.setImageURI(uri)
            }
        }
    }

    private fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val ratioBitmap = width.toFloat() / height.toFloat()
        val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

        var finalWidth = maxWidth
        var finalHeight = maxHeight
        if (ratioMax > 1) {
            finalWidth = (maxHeight * ratioBitmap).toInt()
        } else {
            finalHeight = (maxWidth / ratioBitmap).toInt()
        }
        return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true)
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // Ajusta la calidad según tus necesidades
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }

    private fun saveImageToGallery(imageUri: Uri) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "comida${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        val resolver = requireContext().contentResolver
        val galleryUri =
            resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        galleryUri?.let { uri ->
            resolver.openOutputStream(uri)?.use { outputStream ->
                resolver.openInputStream(imageUri)?.use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            contentValues.clear()
            contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, contentValues, null, null)
        }
    }

    private fun checkPermissionsAndOpenCamera() {
        val cameraPermission = Manifest.permission.CAMERA
        val storagePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            requestPermissionLauncher.launch(arrayOf(cameraPermission))
        } else {
            requestPermissionLauncher.launch(arrayOf(cameraPermission, storagePermission))
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
            val storageGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: false

            if (cameraGranted) {
                takePhoto.launch(photoUri) // Llamar a la cámara si el permiso fue otorgado
            } else {
                Toast.makeText(context, "Permiso de cámara requerido", Toast.LENGTH_SHORT).show()
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && !storageGranted) {
                Toast.makeText(
                    context,
                    "Permiso de almacenamiento requerido para guardar la imagen",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}
