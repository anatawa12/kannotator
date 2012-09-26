package org.jetbrains.kannotator.classHierarchy

import org.jetbrains.kannotator.declarations.*

private val Method.isInheritable: Boolean
    get() =
    !isFinal() &&
    !isStatic() &&
    visibility != Visibility.PRIVATE &&
    id.methodName != "<init>" &&
    id.methodName != "<clinit>"

fun ClassNode.find(method: Method): Method? = methods.find { it.id == method.id }

fun samePackage(c1: ClassNode, c2: ClassNode): Boolean {
    fun ClassNode._package(): String = name.internal.substring(0, name.internal.lastIndexOf('/'))
    return c1._package() == c2._package()
}

fun ClassNode.getOverriddenMethods(method: Method): Set<Method> {
    val my = find(method)
    if (my == null) return hashSet()

    val result = hashSet<Method>(my)

    if (!my.isInheritable) return result

    for (subClassEdge in subClasses) {
        val subClass = subClassEdge.derived
        if (my.visibility == Visibility.PACKAGE && !samePackage(this, subClass)) {
            continue
        }
        result.addAll(subClass.getOverriddenMethods(method))
    }

    return result
}
