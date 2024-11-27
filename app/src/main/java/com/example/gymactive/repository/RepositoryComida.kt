package com.example.gymactive.repository

import com.example.gymactive.models.Comida

object RepositoryComida {
    //hay que hacer una lista mutable
    val listaComidas: MutableList<Comida> = mutableListOf(
        Comida(
            nombre_plato = "Paella Valenciana",
            descricion = "Delicioso arroz con pollo, conejo y judías verdes.",
            image = "https://example.com/paella.jpg"
        ),
        Comida(
            nombre_plato = "Tortilla Española",
            descricion = "Tortilla de patatas hecha con huevos, patatas y cebolla.",
            image = "https://www.google.com/imgres?q=tortilla%20espa%C3%B1ola&imgurl=https%3A%2F%2Fwww.goya.com%2Fmedia%2F3816%2Ftortilla-espan-ola-potato-omelet.jpg%3Fquality%3D80&imgrefurl=https%3A%2F%2Fwww.goya.com%2Fes%2Frecipes%2Ftortilla-a-la-espanola-potato-omelet&docid=t1Az1YgymYSUMM&tbnid=g2mXgCbKJw4ULM&vet=12ahUKEwjQjIn_6_SJAxX3UaQEHT-iHeIQM3oECBcQAA..i&w=1463&h=1094&hcb=2&ved=2ahUKEwjQjIn_6_SJAxX3UaQEHT-iHeIQM3oECBcQAA"
        ),
        Comida(
            nombre_plato = "Gazpacho Andaluz",
            descricion = "Sopa fría de tomate con pepino, pimiento y ajo.",
            image = "https://example.com/gazpacho.jpg"
        ),
        Comida(
            nombre_plato = "Pulpo a la Gallega",
            descricion = "Pulpo cocido servido con aceite de oliva, pimentón y sal.",
            image = "https://example.com/pulpo.jpg"
        ),
        Comida(
            nombre_plato = "Churros con Chocolate",
            descricion = "Deliciosos churros fritos acompañados de chocolate caliente.",
            image = "https://example.com/churros.jpg"
        ),
        Comida(
            nombre_plato = "Cocido Madrileño",
            descricion = "Guiso de garbanzos con carne, tocino y verduras.",
            image = "https://example.com/cocido.jpg"
        ),
        Comida(
            nombre_plato = "Patatas Bravas",
            descricion = "Patatas fritas con salsa picante de tomate.",
            image = "https://example.com/patatas_bravas.jpg"
        ),
        Comida(
            nombre_plato = "Fabada Asturiana",
            descricion = "Guiso de alubias con chorizo, morcilla y panceta.",
            image = "https://example.com/fabada.jpg"
        ),
        Comida(
            nombre_plato = "Calamares a la Romana",
            descricion = "Calamares fritos en harina y servidos con limón.",
            image = "https://example.com/calamares.jpg"
        ),
        Comida(
            nombre_plato = "Ensaimada Mallorquina",
            descricion = "Dulce espiral hecho con masa y azúcar glas.",
            image = "https://example.com/ensaimada.jpg"
        )
    )

}
