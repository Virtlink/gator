term(name,properties) ::= <<

package com.virtlink.paplj.terms.paplj

import com.virtlink.paplj.terms.*

/**
 * The $name$ term.
 */
class $name$Term($properties:{p | val $p.name$: $p.type$}; separator=",\n"$)
    : Term() {

    companion object {

        /**
         * Gets the constructor of this term.
         */
        val constructor = TermConstructorOfT<$name$Term>("$name$", $length(properties)$)

        /**
         * Creates a new term from the specified list of child terms.
         *
         * @param children The list of child terms.
         * @return The created term.
         */
        @JvmStatic fun create(children: List<ITerm>): $name$Term {
            if (children.size != constructor.arity) {
                throw IllegalArgumentException("children must be \${constructor.arity} in length")
            }
            $properties:{p | val $p.name$ = children[$p.index$] as $p.type$}; separator="\n"$
            return TypeTerm($properties:{p | $p.name$}; separator=", "$)
        }
    }

    override val constructor: ITermConstructor
        get() = Companion.constructor

    override val children: List<ITerm> by lazy { ChildrenList() }

    private inner class ChildrenList: AbstractList<ITerm>() {

        override val size: Int
            get() = TypeTerm.constructor.arity

        override fun get(index: Int): ITerm
                = when (index) {
                    $properties:{p | $p.index$ -> this@$name$Term.$p.name$}; separator="\n"$
                    else -> throw IndexOutOfBoundsException()
                }

    }

}
>>
