package com.virtlink.gator

import com.virtlink.logger
import org.antlr.runtime.RecognitionException
import org.antlr.runtime.Token
import org.stringtemplate.v4.STErrorListener
import org.stringtemplate.v4.compiler.GroupParser
import org.stringtemplate.v4.misc.*

class TemplateErrorListener: STErrorListener {

    @Suppress("PrivatePropertyName")
    private val LOG by logger()

    override fun compileTimeError(msg: STMessage) {
        when (msg.error) {
            else -> LOG.error(errorToString(msg))
        }
    }

    override fun IOError(msg: STMessage) {
        when (msg.error) {
            else -> LOG.error(errorToString(msg))
        }
    }

    override fun internalError(msg: STMessage) {
        when (msg.error) {
            else -> LOG.error(errorToString(msg))
        }
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

    private fun getSourceLocation(msg: STMessage): String?
            = when (msg) {
                is STRuntimeMessage -> msg.sourceLocation
                is STLexerMessage -> msg.sourceLocation
                is STGroupCompiletimeMessage -> msg.sourceLocation
                is STCompiletimeMessage -> msg.sourceLocation
                else -> null
            }

    private fun getSourceLocation(cause: Throwable?, token: Token?, templateToken: Token?): String? {
        val re = cause as? RecognitionException
        var line = 0
        var charPos = -1
        if (token != null) {
            line = token.line
            charPos = token.charPositionInLine
        } else if (re != null) {
            line = re.line
            charPos = re.charPositionInLine
        }
        if (templateToken != null && (token == null || templateToken.inputStream != token.inputStream)) {
            var templateDelimiterSize = 1
            if (templateToken.type == GroupParser.BIGSTRING || templateToken.type == GroupParser.BIGSTRING_NO_NL) {
                templateDelimiterSize = 2
            }
            line += templateToken.line - 1
            charPos += templateToken.charPositionInLine + templateDelimiterSize
        }
        if (line >= 0 && charPos >= 0)
            return "$line:$charPos"
        else
            return null
    }

    private val STLexerMessage.sourceLocation: String?
        get() = getSourceLocation(this.cause, null, this.templateToken)

    private val STGroupCompiletimeMessage.sourceLocation: String?
        get() = getSourceLocation(this.cause, this.token, null)

    private val STCompiletimeMessage.sourceLocation: String?
        get() = getSourceLocation(this.cause, this.token, this.templateToken)

}