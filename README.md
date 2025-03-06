## 🏋️ NOMBRE DE LA APLICACIÓN: GymActive

 Tabla de Contenidos
- [Descripción de la Aplicación](#descripción-de-la-aplicación)
- [Características de la Aplicación](#características-de-la-aplicación)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Uso](#uso)
- [Contribución](#contribución)
- [Licencia](#licencia)
- [Versión 1.1](#versión-11)
- [Versión 1.2](#versión-12)
- [Versión 1.3](#versión-13)
- [Versión 1.4](#versión-14)
- [Versión 2.1](#versión-21)
- [Versión 3.1](#versión-31)
- [Versión 4.1](#versión-41)

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

---
## ⚒️ Versión 2.1

### Pasar la aplicación a Model View View Model (MVVM)

Esta versión reestructura la aplicación siguiendo el patrón MVVM, organizando el código en capas para mejorar la mantenibilidad y escalabilidad.

---

### 📚 Estructura de Carpetas:

#### **Carpeta `comida`**:
- **`objects`**: Contiene `ComidasData`, que almacena los datos.
- **`repository`**: Contiene `ComidaRepository`, encargado de acceder y gestionar los datos.
- **`vistaGeneral`**: Contiene elementos compartidos a nivel general de la aplicación.

#### **Carpeta `domain` (Capa de dominio - Lógica de negocio)**:
- **`Comidas`**:
  - **`interfaces`**: Define `InterfacesDAO`, que contiene las reglas de acceso a los datos.
  - **`models`**: Contiene `Comida` y `ListComida`, representaciones de los datos.
  - **`usecase`**: Implementa los casos de uso de la aplicación:
    - **`DeleteComidasUseCase`**: Permite eliminar una comida de la lista según su posición.
    - **`GetComidaByPosUseCase`**: Obtiene una comida por su posición en la lista.
    - **`GetComidaMLUseCase`**: Recupera la lista completa de comidas disponibles.
    - **`GetComidaNaviteUseCase`**: Obtiene una lista de comidas filtradas por un criterio nativo.
    - **`GetComidasByIdUseCase`**: Devuelve una comida según su identificador.
    - **`NewComidaUseCase`**: Agrega una nueva comida si no existe previamente en la lista.
    - **`UpdateComidasCaseUse`**: Permite actualizar una comida existente según su posición.
- **`VistaGeneral`**: Contiene elementos generales relacionados con la vista.

#### **Carpeta `frameworks`**:
Contiene componentes auxiliares y de infraestructura.

#### **Carpeta `ui` (Capa de presentación)**:
- **`viewmodel`**: Contiene `ComidasViewModel`, responsable de gestionar la lógica de presentación.
- **`views`**:
  - **`activities`**: Contiene las pantallas principales de la aplicación.
  - **`fragment`**: Contiene las vistas fragmentadas para modularizar la UI.
    - `Comida`
      - **`adapter`**: Contiene adaptadores para manejar la vista de los datos.
      - **`dialog`**: Contiene elementos de diálogo.
      - `ComidaFragment`: Fragmento principal para mostrar las comidas.
    - `Home`: Fragmento principal de la aplicación.
    - `Setting`: Configuraciones de la aplicación.
  
#### **Archivo `MyApplication`**:
- Clase de aplicación que inicializa componentes globales.

---

### 📜 **Código Fuente**

#### **Repositorio: ComidaRepository**
```kotlin
package com.example.gymactive.data.comida.repository

import com.example.gymactive.data.comida.objects.ComidasData
import com.example.gymactive.domain.Comidas.interfaces.InterfacesDAO
import com.example.gymactive.domain.Comidas.models.Comida
import com.example.gymactive.domain.Comidas.models.ListComida
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComidaRepository @Inject constructor():InterfacesDAO {

    override suspend fun getNativeComida(): List<Comida> {
        return ComidasData.listaComidas
    }

    override suspend fun getComidaList(): List<Comida> {
        return ListComida.comidaObject.comidasMutableList
    }

    override suspend fun deleteComida(pos: Int): Boolean {
        return if (pos < ListComida.comidaObject.comidasMutableList.size){
            ListComida.comidaObject.comidasMutableList.removeAt(pos)
            true
        } else false
    }

    override suspend fun addComida(newComida: Comida): Comida? {
        ListComida.comidaObject.comidasMutableList.add(newComida)
        return newComida
    }

    override suspend fun updateComida(pos: Int, comida: Comida): Boolean {
        return if(pos < ListComida.comidaObject.comidasMutableList.size){
            ListComida.comidaObject.comidasMutableList[pos] = comida.copy(
                nombre_plato = comida.nombre_plato,
                descricion = comida.descricion,
                image = comida.image
            )
            true
        } else false
    }

    override suspend fun exisComida(comida: Comida): Boolean {
        return ListComida.comidaObject.comidasMutableList.contains(comida)
    }

    override suspend fun getComidaById(id: Int): Comida? {
        return ListComida.comidaObject.comidasMutableList.getOrNull(id)
    }

    override fun getComidaByPos(pos: Int): Comida? {
        return ListComida.comidaObject.comidasMutableList.getOrNull(pos)
    }
}
```

### 📦 Dependencias utilizadas
#### **`build.gradle (Project)`**
```gradle
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
}
```

#### **`build.gradle (App)`**
```gradle
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

// Hilt
implementation("com.google.dagger:hilt-android:2.51.1")
kapt("com.google.dagger:hilt-android-compiler:2.51.1")

// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")

// LiveData
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

// Facilita mvvm en el Fragment
implementation("androidx.fragment:fragment-ktx:1.3.2")

// Facilita mvvm en el Activity
implementation("androidx.activity:activity-ktx:1.2.2")
```

## 📸 Versión 3.1: Captura de Imágenes y Multimedia

### Implementación de la Captura de Imágenes:

#### Clase `DialogAgregarComida`:
- **Funcionalidad**: Permite al usuario agregar una nueva comida, incluyendo la opción de capturar o seleccionar una imagen para asociarla con la comida.
- **Lógica de Captura y Selección de Imagen**:
  - Se ha implementado un `DialogFragment` que muestra un diálogo donde el usuario puede capturar una foto con la cámara o seleccionar una imagen de la galería.
  - Utiliza el contrato `ActivityResultContracts.TakePicture` para tomar una foto y `ActivityResultContracts.GetContent` para seleccionar una imagen de la galería.
  - La imagen seleccionada o tomada se muestra en una vista previa en un `ImageView`.

#### Archivos de Implementación:

- **`DialogAgregarComida`**:
  - Se encarga de manejar la captura y selección de imágenes, convirtiéndolas en una cadena Base64 para almacenamiento o uso posterior.
  - Además, guarda la imagen en la galería del dispositivo y permite que se guarde la imagen seleccionada o tomada junto con otros datos de la comida.

### Implementación de la Conversión de Imagen:

- **Conversión a Base64**:  
  La imagen capturada o seleccionada se convierte a formato Base64 utilizando el método `convertImageToBase64`, lo cual es útil para almacenar o transmitir la imagen en formato de texto.
  
  **Flujo**:
  - Se redimensiona la imagen a un tamaño adecuado (800x800 píxeles) para optimizar el uso de memoria.
  - Se convierte a Base64 utilizando el formato JPEG con una calidad del 80%.

#### Funcionalidad de Edición y Eliminación de Imágenes:

- **Edición de Imágenes**:
  - Similar a la captura de una nueva imagen, se proporciona la opción de tomar una foto o seleccionar una imagen desde la galería para reemplazar una imagen existente de la comida.
  - Después de editar la imagen, el flujo de conversión a Base64 y actualización de la imagen en la vista previa sigue el mismo proceso.

- **Eliminación de Imagen**:
  - Para eliminar una imagen, se agrega una opción en el diálogo de edición que permite al usuario borrar la imagen asociada a la comida, estableciendo el campo `imagenBase64` como `null` y actualizando la vista previa para reflejar la eliminación.

#### Funcionalidades Adicionales:

- **Guardar la Imagen en la Galería**:
  - La imagen capturada se guarda en la galería del dispositivo utilizando `MediaStore`, lo que asegura que la imagen se almacene correctamente para su acceso posterior.
  - El proceso incluye la creación de un archivo con un nombre único basado en la fecha y hora, y la actualización de la galería con la imagen recién tomada o seleccionada.

#### Permisos Requeridos:
- **Permiso de Cámara**: Se requiere el permiso `Manifest.permission.CAMERA` para poder acceder a la cámara del dispositivo.
- **Permiso de Almacenamiento**: Para dispositivos con Android por debajo de la versión 10, se requiere el permiso `Manifest.permission.WRITE_EXTERNAL_STORAGE` para guardar la imagen en la galería.

### Proceso de Implementación:

1. **Mostrar Opciones de Imagen**:
   - Se muestra un diálogo con las opciones "Tomar foto" o "Seleccionar de galería".
   
2. **Tomar Foto**:
   - Se verifica si se tiene el permiso para acceder a la cámara y, si es así, se inicia la captura de la imagen.
   
3. **Seleccionar de Galería**:
   - Se permite al usuario seleccionar una imagen desde la galería utilizando el contrato `ActivityResultContracts.GetContent`.

4. **Conversión a Base64**:
   - La imagen seleccionada o tomada se convierte a Base64 y se actualiza la vista previa en el `ImageView`.

5. **Guardar en la Galería**:
   - Después de tomar la foto, la imagen se guarda en la galería utilizando `MediaStore`.

---

### Código de la Funcionalidad:

- **Métodos Clave**:
  - `createImageUri`: Crea un URI único para la imagen capturada.
  - `mostrarOpcionesImagen`: Muestra el diálogo con las opciones para tomar una foto o seleccionar una imagen.
  - `convertImageToBase64`: Convierte la imagen a formato Base64 para su almacenamiento o uso posterior.
  - `saveImageToGallery`: Guarda la imagen capturada en la galería del dispositivo.

Claro, aquí te dejo la documentación de la **Versión 4.1** del proyecto con ejemplos de código integrados, y redactada de manera que suene como si fuera tuya:

---

## 🛠️ Versión 4.1: Adaptación a API REST y Autenticación con Token

### Descripción General:

En esta versión, he realizado una serie de cambios clave para mejorar la seguridad y el rendimiento de la herramienta. El cambio principal es la adaptación a una arquitectura basada en **API REST**, lo cual facilita la interacción entre la aplicación y el backend. Además, he implementado un nuevo sistema de autenticación con **tokens JWT**, reemplazando el sistema anterior basado en Firebase. Esto no solo mejora la seguridad, sino que también hace que el manejo de sesiones sea más eficiente.

### Cambios Principales:

1. **Transición a API REST:**
   La estructura del proyecto ha sido modificada para cumplir con los principios RESTful. Ahora la comunicación entre la aplicación y el servidor es a través de solicitudes HTTP (GET, POST, PUT, DELETE).

2. **Autenticación con Token JWT:**
   El sistema de autenticación ya no depende de Firebase. Ahora, tras el inicio de sesión, el backend genera un **token JWT** que se utiliza para autenticar al usuario en futuras solicitudes.

3. **Actualización en el formato de logs:**
   El formato de los logs ahora sigue una estructura JSON, lo que facilita el análisis y la integración con otras herramientas de monitoreo.

---

### Implementación de la Autenticación con Token JWT:

El backend ahora utiliza un sistema basado en **JSON Web Tokens (JWT)** para manejar la autenticación. A continuación, te muestro el proceso de cómo se genera y valida el token en el servidor.

#### Generación del Token:

Cuando un usuario se autentica, el servidor valida las credenciales y, si son correctas, genera un token JWT. Este token se utiliza para autenticar al usuario en todas las solicitudes subsiguientes. Aquí está el código básico que implementé para generar el token:

```java
// Controlador de autenticación
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Autenticación de las credenciales
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
            )
        );

        // Establecer el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generar el token JWT
        String jwt = jwtTokenProvider.generateToken(authentication);
        
        // Devolver el token al cliente
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}
```

#### Explicación del Código:

- **Autenticación:** Se verifica que las credenciales enviadas por el cliente (nombre de usuario y contraseña) sean correctas.
- **Generación del Token JWT:** Si las credenciales son válidas, se genera un token JWT utilizando el `JwtTokenProvider`.
- **Respuesta:** El token generado se devuelve al cliente en formato JSON.

#### `JwtTokenProvider` - Generación del Token:

La clase `JwtTokenProvider` es la responsable de generar el token y de validarlo en solicitudes posteriores. Aquí está el código para la generación del token:

```java
public class JwtTokenProvider {

    private final String JWT_SECRET = "secret_key";  // Clave secreta para firmar el JWT
    private final long JWT_EXPIRATION = 604800000L; // 7 días en milisegundos

    public String generateToken(Authentication authentication) {
        // Extraer el nombre de usuario del contexto de seguridad
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        // Generar el token JWT
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Validación del token en futuras solicitudes
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
```

#### Explicación del Código:

- **Generación del token:** El método `generateToken` toma un objeto `Authentication` que contiene la información del usuario autenticado. Luego, utiliza esta información para crear el token JWT, firmado con una clave secreta y con una fecha de expiración configurada.
- **Validación del token:** El método `validateToken` verifica que el token recibido sea válido y no haya expirado.

---

### Integración en el Frontend (Android):

Desde el lado del cliente, cuando el usuario se loguea, el token se recibe y se almacena para usarlo en solicitudes posteriores. Aquí te muestro cómo gestioné la autenticación en el lado de la aplicación Android.

#### Envío del Token en las Solicitudes HTTP:

Cada vez que se realiza una solicitud al servidor que requiere autenticación, el token JWT debe enviarse en los encabezados HTTP. Este es un ejemplo de cómo lo implementé utilizando Retrofit:

```kotlin
// Interceptor para añadir el token al encabezado de la solicitud
class AuthenticationInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}

// Configuración de Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("https://miapi.com")
    .client(
        OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor("mi_token_jwt"))
            .build()
    )
    .addConverterFactory(GsonConverterFactory.create())
    .build()
```

#### Explicación del Código:

- **Interceptor:** El interceptor `AuthenticationInterceptor` es responsable de agregar el token JWT en el encabezado `Authorization` de cada solicitud HTTP. Esto asegura que todas las solicitudes autenticadas sean procesadas correctamente por el servidor.
- **Retrofit:** La configuración de Retrofit incluye el interceptor, asegurando que el token sea añadido automáticamente a cada solicitud.



