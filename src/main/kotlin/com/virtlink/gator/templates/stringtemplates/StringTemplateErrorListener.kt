package com.virtlink.gator.templates.stringtemplates

import com.virtlink.gator.templates.Position
import com.virtlink.logger
import org.antlr.runtime.RecognitionException
import org.antlr.runtime.Token
import org.stringtemplate.v4.STErrorListener
import org.stringtemplate.v4.compiler.GroupParser
import org.stringtemplate.v4.misc.*

class StringTemplateErrorListener : STErrorListener {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    override fun compileTimeError(msg: STMessage) {
        LOG.error(errorToString(msg))
    }

    override fun IOError(msg: STMessage) {
        LOG.error(errorToString(msg))
    }

    override fun internalError(msg: STMessage) {
        LOG.error(errorToString(msg))
    }

    override fun runTimeError(msg: STMessage) {
        when (msg.error) {
            ErrorType.NO_SUCH_ATTRIBUTE -> LOG.warn(errorToString(msg))
            else -> LOG.error(errorToString(msg))
        }
    }

    private fun errorToString(msg: STMessage): String {
        val sourceLocation = getSourceLocation(msg)
        val message = String.format(msg.error.message, msg.arg, msg.arg2, msg.arg3)
        return if (sourceLocation != null) {
            "$sourceLocation $message"
        } else {
            message
        }
    }

    private fun getSourceLocation(msg: STMessage): Position?
            = when (msg) {
                is STRuntimeMessage -> msg.sourceLocation2
                is STLexerMessage -> msg.sourceLocation
                is STGroupCompiletimeMessage -> msg.sourceLocation
                is STCompiletimeMessage -> msg.sourceLocation
                else -> null
            }

    private fun getSourceLocation(cause: Throwable?, token: Token?, templateToken: Token?): Position? {
        val re = cause as? RecognitionException
        var line = 0
        var character = -1
        if (token != null) {
            line = token.line
            character = token.charPositionInLine
        } else if (re != null) {
            line = re.line
            character = re.charPositionInLine
        }

        if (templateToken != null && (token == null || templateToken.inputStream != token.inputStream)) {
            var templateDelimiterSize = 1
            if (templateToken.type == GroupParser.BIGSTRING || templateToken.type == GroupParser.BIGSTRING_NO_NL) {
                templateDelimiterSize = 2
            }
            line += templateToken.line - 1
            character += templateToken.charPositionInLine + templateDelimiterSize
        }

        return if (line >= 0 && character >= 0)
            Position(line, character)
        else
            null
    }

    private val STRuntimeMessage.sourceLocation2: Position? get() {

        if (ip < 0 || this.self == null) return null
        val I = self.impl.sourceMap[ip] ?: return null
        // get left edge and get line/col
        val i = I.a
        val loc = Misc.getLineCharPosition(self.impl.template, i)
        return Position(loc.line - 1, loc.charPosition)
    }

    private val STLexerMessage.sourceLocation: Position?
        get() = getSourceLocation(this.cause, null, this.templateToken)

    private val STGroupCompiletimeMessage.sourceLocation: Position?
        get() = getSourceLocation(this.cause, this.token, null)

    private val STCompiletimeMessage.sourceLocation: Position?
        get() = getSourceLocation(this.cause, this.token, this.templateToken)

}