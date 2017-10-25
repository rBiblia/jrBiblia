package net.avensome.dev.jrbiblia.ext

import javafx.beans.binding.DoubleExpression
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.shape.Rectangle
import tornadofx.*

fun Rectangle(width: DoubleExpression, height: DoubleExpression): ObjectProperty<Rectangle> {
    val initialRectangle = Rectangle(width.get(), height.get())
    val property = SimpleObjectProperty(initialRectangle)
    width.onChange { property.set(Rectangle(width.get(), height.get())) }
    height.onChange { property.set(Rectangle(width.get(), height.get())) }
    return property
}
