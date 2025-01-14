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

## 🛠️ Versión 1.4

En esta versión se tiene que implementar a nuestro el Navigation Drawer, un toolbar y un Navigation Botton. Para ello lo pirmero de todo ha sido pasar todos los Activitys a Fragment. Una vez ya cambiado ya esta preparado para funcionar.

### Primero creamos el nav_graph:

#### Explicación del archivo de navegación en XML para Android

Este archivo define las rutas de navegación entre diferentes fragmentos (pantallas) de la aplicación, permitiendo gestionar las transiciones y acciones dentro del `Navigation Component`.

##### Estructura del XML

```xml
   <?xml version="1.0" encoding="utf-8"?>
   <navigation xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:app="http://schemas.android.com/apk/res-auto"
       xmlns:tools="http://schemas.android.com/tools"
       android:id="@+id/nav_graph"
       app:startDestination="@id/home2">

       <fragment
           android:id="@+id/home2"
           android:name="com.example.gymactive.fragment.Home"
           android:label="Home"
           tools:layout="@layout/fragment_home" />
       <fragment
           android:id="@+id/comidaMenu"
           android:name="com.example.gymactive.fragment.ComidaActFragment"
           android:label="Comida"
           tools:layout="@layout/fragment_comida" >
           <action
               android:id="@+id/action_comidaMenu_to_home2"
               app:destination="@id/home2" />
       </fragment>
       <fragment
           android:id="@+id/settingMenu"
           android:name="com.example.gymactive.fragment.SettingFragment"
           android:label="Setting"
           tools:layout="@layout/fragment_setting" >
           <action
               android:id="@+id/action_settingMenu_to_home2"
               app:destination="@id/home2" />
       </fragment>
       <fragment
           android:id="@+id/vistaGeneral"
           android:name="com.example.gymactive.fragment.VistaGeneral"
           android:label="Listado de comida"
           tools:layout="@layout/fragment_vista_general" >
           <action
               android:id="@+id/action_vistaGeneral_to_home2"
               app:destination="@id/home2" />
       </fragment>
   </navigation>
```

### Carpeta menu:

Esta va a contener lo siguientes ficheros:

#### nav_menu_extend

Este archivo XML define un menú estructurado que se puede usar en un `Navigation Drawer`, un `Toolbar`, o cualquier otro componente que soporte menús. Su diseño organiza las opciones de navegación y acciones en categorías, permitiendo mejorar la experiencia del usuario.

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    tools:showIn="navigation_view">

    <!-- Grupo principal con comportamiento de selección única -->
    <group android:checkableBehavior="single">
        <item
            android:id="@+id/comidaMenu"
            android:title="Comida"
            android:icon="@drawable/comida" />

        <item
            android:id="@+id/settingMenu"
            android:title="Configuración"
            android:icon="@drawable/setting" />

        <item
            android:id="@+id/vistaGeneral"
            android:title="Lista de comida"
            android:icon="@drawable/ojo" />
    </group>

    <!-- Separador visual opcional -->
    <item android:title="Otras opciones">
        <menu>
            <item
                android:id="@+id/logoutMenu"
                android:title="Cerrar sesión"
                android:icon="@drawable/logout" />
        </menu>
    </item>

</menu>
```



#### menu.xml

Este archivo XML describe un menú básico que podría utilizarse en componentes como un `PopupMenu`, un `Toolbar`, o un `Navigation Drawer`. Contiene tres opciones principales, cada una con un identificador único, título y un ícono asociado.

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/comidaMenu"
        android:title="Comida"
        android:icon="@drawable/comida"
        />

    <item
        android:id="@+id/logoutMenu"
        android:title="Logout"
        android:icon="@drawable/logout"
        />
    <item
        android:id="@+id/settingMenu"
        android:title="Setting"
        android:icon="@drawable/setting"/>

</menu>
```


#### bottom_menu.xml

Este archivo XML define un menú básico con varias opciones que podrían utilizarse en un `Navigation Drawer`, `Toolbar`, o cualquier otro componente compatible. Cada opción está habilitada, incluye un ícono y tiene un identificador único.

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/settingMenu"
        android:title="Setting"
        android:enabled="true"

        android:icon="@drawable/setting"/>

    <item
        android:id="@+id/home2"
        android:title="Home"
        android:enabled="true"
        android:icon="@drawable/house"/>

    <item
        android:id="@+id/comidaMenu"
        android:title="Comida"
        android:enabled="true"
        android:icon="@drawable/comida"
        />
    <item
        android:id="@+id/vistaGeneral"
        android:title="Mi lista"
        android:enabled="true"
        android:icon="@drawable/ojo"
        />

</menu>

```

### Carpeta Layout

#### header_menu.xml

Este archivo XML define un diseño utilizando `ConstraintLayout`, que organiza y alinea elementos de manera eficiente en Android. El diseño incluye un `CardView` con una imagen, y dos `TextView` para mostrar el nombre y el correo del usuario.

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="180dp"
    android:background="@color/color_caja"
    android:padding="20dp"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <androidx.cardview.widget.CardView
        android:id="@+id/cardView3"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:layout_marginStart="4dp"
        android:layout_marginTop="16dp"
        android:background="@color/color_caja"
        app:cardCornerRadius="40dp"
        app:cardElevation="10dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <ImageView
            android:id="@+id/userImageView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:src="@drawable/foto_principal" />
    </androidx.cardview.widget.CardView>

    <TextView
        android:id="@+id/userNameTextView"
        android:layout_width="163dp"
        android:layout_height="45dp"
        android:layout_marginStart="104dp"
        android:layout_marginTop="36dp"
        android:text="Santi"
        android:textColor="@color/black"
        android:textSize="30dp"
        android:textStyle="bold"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/userEmailTextView"
        android:layout_width="378dp"
        android:layout_height="42dp"
        android:layout_marginStart="4dp"
        android:layout_marginTop="100dp"
        android:text="santiagofuentesespinosa71@gmail.com"
        android:textColor="@color/black"
        android:textSize="20dp"
        android:textStyle="bold"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>

```

#### container_fragment.xml

Este archivo XML define una estructura básica que utiliza un `LinearLayout` como contenedor raíz y un `FragmentContainerView` para gestionar la navegación dentro de la aplicación.

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/fragmentContainerView"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:defaultNavHost="true"
        app:navGraph="@navigation/nav_graph" />
</LinearLayout>
```

#### app_bottom_bar.xml

Este archivo XML define una interfaz basada en un `CoordinatorLayout` que incluye un `BottomAppBar` y un `BottomNavigationView`. Este diseño es común en aplicaciones modernas que utilizan Material Design para una navegación más fluida e intuitiva.

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <!-- Aquí puedes colocar tu contenido principal o fragmentos -->

    <!-- BottomAppBar -->
    <com.google.android.material.bottomappbar.BottomAppBar
        android:id="@+id/my_bottom_app_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom"
        app:backgroundTint="@color/color_caja"
        app:fabCradleMargin="10dp"
        app:fabCradleRoundedCornerRadius="50dp">
    </com.google.android.material.bottomappbar.BottomAppBar>

    <!-- BottomNavigationView -->
    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/my_bottom_navigation"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/color_caja"
        app:labelVisibilityMode="labeled"
        app:layout_anchor="@+id/my_bottom_app_bar"
        app:layout_anchorGravity="center"
        app:menu="@menu/bottom_menu" />

</androidx.coordinatorlayout.widget.CoordinatorLayout>

```

#### activity_main_content.xml

Este archivo XML utiliza un `CoordinatorLayout` como contenedor principal para organizar una barra de herramientas (`Toolbar`) dentro de un `AppBarLayout` y un contenedor de fragmentos (`FrameLayout`). Este diseño sigue las pautas de Material Design y es común para aplicaciones que necesitan una barra superior fija y un área para mostrar contenido dinámico.

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    >

    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/appbar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <androidx.appcompat.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:background="@color/color_caja"
            app:menu="@menu/menu"
            app:popupTheme="@style/ThemeOverlay.MaterialComponents.Light" />
    </com.google.android.material.appbar.AppBarLayout>

    <FrameLayout
        android:id="@+id/fragmentContainerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior" />

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```


#### activity_main.xml

Este archivo XML define un diseño que utiliza un `DrawerLayout` como contenedor principal, con un menú de navegación lateral (Drawer) y fragmentos dinámicos a través de un `NavHostFragment`. También incluye barras de herramientas y barra inferior.

```xml
<androidx.drawerlayout.widget.DrawerLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawer_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:openDrawer="start"
    tools:context=".MainActivity"
    android:fitsSystemWindows="true">

    <!-- Contenedor de fragmentos para el NavHostFragment -->
    <androidx.fragment.app.FragmentContainerView
        android:id="@+id/fragmentContainerView"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:defaultNavHost="true"
        app:navGraph="@navigation/nav_graph" />

    <!-- Layout principal de la app -->
    <include
        android:id="@+id/app_bar_layout_drawer"
        layout="@layout/activity_main_content"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <include
        android:id="@+id/app_bottom_bar"
        layout="@layout/app_bottom_bar"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />


    <!-- NavigationView para el Drawer -->
    <com.google.android.material.navigation.NavigationView
        android:id="@+id/nav_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        app:menu="@menu/nav_menu_extend"
        app:headerLayout="@layout/header_menu"
        android:layout_gravity="start"
        android:fitsSystemWindows="true"
        android:background="@color/fondo_de_la_aplicacion" />

</androidx.drawerlayout.widget.DrawerLayout>

```

### MainActivity.kt

El método `onCreate` se ejecuta cuando la actividad es creada. En este método, se inicializan los componentes visuales, se configura la navegación y se realiza la vinculación con los elementos del layout. A continuación, se explica cada sección del código.

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización del Binding para acceder a las vistas definidas en el layout XML
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root) // Establece el layout de la actividad


        // Configuración del título de la barra superior
        supportActionBar?.title = "Gym Active"

        // Configuración del Toolbar
        val toolbar = mainBinding.appBarLayoutDrawer.toolbar
        setSupportActionBar(toolbar)

        // Configuración del DrawerLayout y el Navigation Component
        drawerLayout = mainBinding.drawerLayout
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController // Controlador de navegación

        // Configuración de los destinos principales y el DrawerLayout
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.home2), // Destinos principales
            drawerLayout // DrawerLayout asociado
        )

        // Vinculación del AppBar con el controlador de navegación
        setupActionBarWithNavController(navController, appBarConfiguration)
        // Configuración del NavigationView con el controlador de navegación
        mainBinding.navView.setupWithNavController(navController)

        // Inicialización del BottomAppBar y el BottomNavigationView
        bottomAppBar = findViewById(R.id.my_bottom_app_bar)
        bottomNavigationView = findViewById(R.id.my_bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        // Configuración del BottomAppBar
        bottomAppBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        mainBinding.navView.setNavigationItemSelectedListener { item ->

                when (item.itemId) {
                    R.id.vistaGeneral->{
                        navController.navigate(R.id.vistaGeneral)
                        drawerLayout.closeDrawer(GravityCompat.START)

                    }
                    R.id.settingMenu -> { // Navegar a la configuración
                        navController.navigate(R.id.settingMenu)
                        drawerLayout.closeDrawer(GravityCompat.START)

                    }
                    R.id.comidaMenu -> { // Navegar al menú de comida
                        navController.navigate(R.id.comidaMenu)
                        drawerLayout.closeDrawer(GravityCompat.START)

                    }
                    R.id.logoutMenu -> {
                        logout()
                        drawerLayout.closeDrawer(GravityCompat.START)

                    }
                    else ->  drawerLayout.closeDrawer(GravityCompat.START)
                }
            true
        }

        // Configuración del BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.settingMenu -> {
                    navController.navigate(R.id.settingMenu)
                    true
                }
                R.id.comidaMenu -> {
                    navController.navigate(R.id.comidaMenu)
                    true
                }
                R.id.home2 -> {
                    navController.navigate(R.id.home2)
                    true
                }
                R.id.vistaGeneral ->{
                    navController.navigate(R.id.vistaGeneral)
                    true
                }

                else -> false
            }
        }

        // Listener para actualizar el BottomNavigationView cuando cambie el destino
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home2 -> bottomNavigationView.menu.findItem(R.id.home2).isChecked = true
                R.id.settingMenu -> bottomNavigationView.menu.findItem(R.id.settingMenu).isChecked = true
                R.id.comidaMenu -> bottomNavigationView.menu.findItem(R.id.comidaMenu).isChecked = true
            }
        }

        // Carga de datos del usuario para mostrar en el header del Navigation Drawer
        loadUserData(mainBinding.navView)
    }
```
