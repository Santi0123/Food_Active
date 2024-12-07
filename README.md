# 📘 Tarea de Desarrollo de Aplicación

## Asignatura: Programación multimedia y dispositivos móviles 
**Alumno**: Santiago Fuentes Espinosa  
**Curso**: 2º DAM A

---

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
