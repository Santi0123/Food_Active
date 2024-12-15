## 🏋️ NOMBRE DE LA APLICACIÓN: GymActive

### Descripción de la Aplicación:

Mi propuesta consiste en una aplicación para Android que ofrecerá diversas funcionalidades. Se asemejará a una red social y permitirá a los usuarios mantener una motivación adicional, así como explorar nuevos aspectos relacionados con la nutrición y distintos entrenamientos. Además, facilitará la conexión con personas que comparten el mismo entusiasmo por el deporte.

---

### Características de la Aplicación:

1. **Pantalla de Registro**:

   - Registro sencillo con pocos campos: correo electrónico y contraseña.
2. **Pantalla de Login**:

   - Inicio de sesión con usuario y contraseña.
   - Opción de recuperación de contraseña mediante el correo electrónico.
3. **Pantalla de Inicio**:

   - Pantalla de bienvenida.
   - Barra de navegación inferior (Navigation Bar) para acceder a diferentes secciones de la aplicación.
   - Posibilidad de escuchar música desde el dispositivo.
   - Menú lateral (Navigation Drawer) que muestra:
     - Imagen de perfil, nombre y correo electrónico.
     - GYM actual.
     - Botón para acceder a las rutinas.
     - Botón de salida de la aplicación.
4. **Funcionalidad de Notas**:

   - Opción para registrar entrenamientos semanales y ver el progreso.
   - Interface intuitiva para seleccionar días de la semana y registrar ejercicios y repeticiones.
5. **Funcionalidad GYM**:

   - Visualizar en qué gimnasio se ha realizado el entrenamiento.
   - Posibilidad de calificar el gimnasio para compartir opiniones con otros usuarios.
6. **Compartir Entrenamientos**:

   - Publicar entrenamientos de forma pública.
   - Opción de agregar una imagen y comentar en las publicaciones de otros usuarios.
7. **Compartir Recetas**:

   - Funcionalidad para compartir recetas alimenticias.
   - Captura de fotos y descripción de la receta.

---

## 🛠️ Versión 1.1

### Implementación del RecyclerView:

- Se crearon los archivos de diseño XML en la carpeta `res`.

#### Archivos de Diseño:

- `activity_comida_view.xml`: Contiene el RecyclerView.
- `item_comida.xml`: CardView con campos para completar el objeto.

### Implementación de la Carpeta `models`:

- **Data Class `Comida`**: Contiene atributos como `nombre`, `descripcion`, y `image`.

### Carpeta `repository`:

- **`RepositoryComida`**: Contiene una lista mutable con todas las comidas.

### Carpeta `interfaces`:

- **`InterfacesComidaDao`**: Define las funciones necesarias.

### Carpeta `dao`:

- **Clase `DaoComida`**: Implementa `InterfacesComidaDao`.

### Carpeta `ViewHolderComida`:

- **Clase `ViewHolderComida`**: Adaptador para mostrar elementos individuales en el RecyclerView.

### Clase `AdapterComida`:

- Adaptador para el RecyclerView que maneja la lista de objetos `Comida` y la funcionalidad de eliminación de elementos.

### Carpeta `controller`:

- **Clase `Controller`**: Implementa la lógica para manejar `AdapterComida`.

### `ComidaView`:

- Clase principal que ejecuta la aplicación.

---

## 🛠️ Versión 1.2

### Carptea `dialog`:

Encontramos en la carpeta dialog tres tipos de dialog. Los cuales son DialogAgregarComida, DialogBorrarComida y DialogEditarComida.
Estos lo hacemos para interactuar y realizar las funciones de agregar, borrar y editar, de una manera más efectiva y visual.

* **Clase `DialogAgregarComida`** : Esta diseñado para permitir al usuario agregar una comida
* **Clase `DialogBorraComida`** : Esta diseñado para permitir al usuario borra una comida
* **Clase `DialogEditarComida`** : Esta diseñado para permitir al usuario editar una comida

### Carpeta `controller` :

En esta carpeta vamos a encontrar el controller el cual va a controlar de la logica de la aplicacion, podriamos decir que actua como el intermediario de entre la vista y los datos.
Este es el que se encarga de decir como se manipula.

#### Función `agregarComida`

Esta función se encarga de mostrar un diálogo para agregar una nueva comida. Al confirmar la acción en el diálogo,

se ejecuta la lógica necesaria para añadir la comida a la lista yactualizar el `RecyclerView`.

##### Código

```kotlin
fun agregarComida() {
    val dialog = DialogAgregarComida { comida -> okNuevaComida(comida) }

    val comidaActivity = context as ComidaAct
    dialog.show(comidaActivity.supportFragmentManager, "Agragamos comida")
}
```

#### Función `okNuevaComida`

La función `okNuevaComida` se encarga de gestionar la lógica necesaria para agregar un nuevo elemento (`comida`) a la lista de comidas,
actualizar el adaptador del `RecyclerView` y asegurar que el nuevo elemento sea visible en la interfaz.

##### Código

```kotlin
private fun okNuevaComida(comida: Comida) {
    listaComidas.add(comida)
    val comidaActivity = context as ComidaAct
    comidaActivity.binding.rvComida.adapter?.notifyItemInserted(listaComidas.size - 1)
    layoutManager.scrollToPositionWithOffset(listaComidas.lastIndex, listaComidas.size - 1)
}
```

#### Función `editarComida`

La función `editarComida` permite iniciar el proceso de edición de una comida dentro de una lista. Muestra un diálogo personalizado

donde se pueden modificar los datos de una comida específica y `actualizarComida` permite actualizar automáticamente la lista una vez confirmados los cambios.

##### Código

```kotlin
fun editarComida(positionLista: Int) {
    val comidaActivity = context as ComidaAct

    val comidaEditarDialog = DialogEditarComida(positionLista, listaComidas[positionLista]) { comidaActualizada, position ->
        actualizarComida(comidaActualizada, position)
    }

    comidaEditarDialog.show(comidaActivity.supportFragmentManager, "Editamos la comida")
}
```

##### Código

```kotlin
fun actualizarComida(comidaActualizada: Comida, position: Int) {
        listaComidas[position] = comidaActualizada
        val comidaActivity = context as ComidaAct
        comidaActivity.binding.rvComida.adapter?.notifyItemChanged(position)
    }
```

#### Función `mostrarDialogoBorrarComida`

La función `mostrarDialogoBorrarComida` muestra un diálogo personalizado que solicita al usuario confirmar la eliminación de un elemento específico de la lista de comidas.

Si el usuario confirma, llama a la función `confirmarBorrado` para eliminar el elemento y actualizar la interfaz.

##### Código

```kotlin
private fun mostrarDialogoBorrarComida(position: Int) {
    val comidaActivity = context as ComidaAct

    val comidaBorrarDialog = DialogBorrarComida(position, listaComidas[position]) {
        confirmarBorrado(it)
    }

    comidaBorrarDialog.show(comidaActivity.supportFragmentManager, "Borrar la comida")
}
```

##### Código

```kotlin
fun confirmarBorrado(position: Int) {
        listaComidas.removeAt(position)
        val comidaActivity = context as ComidaAct
        comidaActivity.binding.rvComida.adapter?.notifyItemRemoved(position)
        comidaActivity.binding.rvComida.adapter?.notifyItemRangeChanged(position, listaComidas.size)
    }
```

#### Función `openGallery`

La función `openGallery` permite abrir la galería de imágenes del dispositivo para que el usuario pueda seleccionar una imagen.
Utiliza un intent explícito para invocar el selector de imágenes del sistema.

##### Código

```kotlin
fun openGallery() {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    (context as Activity).startActivityForResult(intent, PICK_IMAGE_REQUEST)
}
```

#### Función `handleGalleryResult`

La función `handleGalleryResult` procesa el resultado de la acción de seleccionar una imagen en la galería. Comprueba las condiciones necesarias para

confirmar que el usuario ha seleccionado una imagen correctamente, y almacena su URI.

##### Código

```kotlin
fun handleGalleryResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
        imageUri = data.data
    }
}
```

---

## 🛠️ Versión 1.3

En esta versión, se integrará el sistema de autenticación de Firebase para gestionar el registro de usuarios, inicio de sesión (login) y la recuperación de contraseñas.

Firebase proporciona una solución sencilla y segura para manejar la autenticación a través de varios métodos, como correo electrónico y contraseña.

### Paso 1: Registro en firebase

Tenemos que registrarnos en la página de [firebase](https://firebase.google.com/docs/auth?hl=es) tras el registo nos aparecerá para crear un proyecto, esto se hace de una forma simple y solo se ha tenido que seguir los paso proporcioandos en firebase

### Paso 2: Implementacion en el proyecto

#### Creacion de fragmento para el registro

Se crea un fragmento para que se pueda registrar el usuario, se le pide los datos siguientes:

* **Correo electronico** -> Este tambien verifica si el correo electronico contiene un formato correcto o no
* **Contraseña y repetición de la misma** -> Tenemos que tener una contraseña y tenemos que tener otro campo para que la repita. Tiene que coincidir las contraseñas y tienen que tener un mínimo de seguridad
* **Aceptar terminos y condiciones** -> Tenemos que verificar la casilla para que pueda hacerse el registro bien

Una vez que todos los campos estan correctos se le da a registrarse y ya se desvía la página al login

En el login no se puede iniciar sesión hasta que la cuenta no se haya verificado a través de un correo electrónico.

Una vez verificado se insetan los datos en la pantalla del login ya si todo va podremos entrar en la la aplicación en caso que se nos olvide la contraseña se nos enviará un correo de verificación
