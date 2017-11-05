package com.virtlink.gator.templates

import java.util.*

class AttributeList(c: Collection<Any>): AbstractMutableList<Any>() {

    private val list: LinkedList<Any> = LinkedList(c)

    constructor(): this(emptyList())

    override val size: Int
        get() = this.list.size

    override fun add(index: Int, element: Any)
            = this.list.add(index, element)

    override fun removeAt(index: Int): Any
            = this.list.removeAt(index)

    override fun get(index: Int): Any
            = this.list.get(index)

    override fun set(index: Int, element: Any): Any
            = this.list.set(index, element)
}