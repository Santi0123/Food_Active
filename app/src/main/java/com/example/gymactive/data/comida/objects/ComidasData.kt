package com.example.gymactive.data.comida.objects

import com.example.gymactive.domain.Comidas.models.Comida

object ComidasData {

    val listaComidas: List<Comida> = listOf(
        Comida(
            nombre_plato = "Paella Valenciana",
            descricion = "Delicioso arroz con pollo, conejo y judías verdes.",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQniMRDnDGgIzGjKz7vO6zun9cTqyL8zCKOXQ&s"
        ),
        Comida(
            nombre_plato = "Tortilla Española",
            descricion = "Tortilla de patatas hecha con huevos, patatas y cebolla.",
            image = "https://www.goya.com/media/3816/tortilla-espan-ola-potato-omelet.jpg?quality=80"
        ),
        Comida(
            nombre_plato = "Gazpacho Andaluz",
            descricion = "Sopa fría de tomate con pepino, pimiento y ajo.",
            image = "https://i.blogs.es/e64620/gazpacho/1366_2000.jpg"
        ),
        Comida(
            nombre_plato = "Pulpo a la Gallega",
            descricion = "Pulpo cocido servido con aceite de oliva, pimentón y sal.",
            image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR9heE_kwm9yGei-ZX_BB_oiGxhu6gC1GLA_w&s"
        ),
        Comida(
            nombre_plato = "Churros con Chocolate",
            descricion = "Deliciosos churros fritos acompañados de chocolate caliente.",
            image = "https://assets.tmecosys.com/image/upload/t_web767x639/img/recipe/ras/Assets/60eb25e0c5999993a224c87e8ff363ef/Derivates/e2616be4386c198bfd121c7f4b8f47931f10a777.jpg"
        ),
        Comida(
            nombre_plato = "Cocido Madrileño",
            descricion = "Guiso de garbanzos con carne, tocino y verduras.",
            image = "https://www.laespanolaaceites.com/wp-content/uploads/2019/06/cocido-madrileno-1080x671.jpg"
        ),
        Comida(
            nombre_plato = "Patatas Bravas",
            descricion = "Patatas fritas con salsa picante de tomate.",
            image = "https://imagenes.20minutos.es/files/image_1920_1080/uploads/imagenes/2021/07/20/patatas-bravas.jpeg"
        ),
        Comida(
            nombre_plato = "Fabada Asturiana",
            descricion = "Guiso de alubias con chorizo, morcilla y panceta.",
            image = "https://imag.bonviveur.com/fabada-asturiana.jpg"
        ),
        Comida(
            nombre_plato = "Calamares a la Romana",
            descricion = "Calamares fritos en harina y servidos con limón.",
            image = "https://cdn.elcocinerocasero.com/imagen/receta/1000/2022-05-25-21-02-06/calamares-a-la-romana.jpeg"
        ),
        Comida(
            nombre_plato = "Ensaimada Mallorquina",
            descricion = "Dulce espiral hecho con masa y azúcar glas.",
            image = "https://levanova.es/wp-content/uploads/2021/07/ensaimada1-1200x900.png"
        )
    )
}