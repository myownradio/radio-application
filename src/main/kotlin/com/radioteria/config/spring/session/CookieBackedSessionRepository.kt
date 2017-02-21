package com.radioteria.config.spring.session

import com.auth0.jwt.JWT
import org.springframework.session.Session
import org.springframework.session.SessionRepository
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class CookieBackedSessionRepository : SessionRepository<Session> {

    companion object {
        const val SESSION_COOKIE_NAME = "RT_SESSION"
    }

    override fun createSession(): Session {
        throw UnsupportedOperationException("not implemented")
    }

    override fun getSession(id: String): Session {
        throw UnsupportedOperationException("not implemented")
    }

    override fun save(session: Session) {
        throw UnsupportedOperationException("not implemented")
    }

    override fun delete(id: String) {
        throw UnsupportedOperationException("not implemented")
    }

    protected abstract fun getResponse(): HttpServletResponse

    protected abstract fun getRequest(): HttpServletRequest

    private fun getSessionFromRequest(): Session {
        val req = getRequest()
        val cookie = req.cookies.firstOrNull { it.name == SESSION_COOKIE_NAME }

        if (cookie != null) {
            return decodeCookie(cookie)
        }

        throw UnsupportedOperationException("not implemented")
    }

    private fun decodeCookie(cookie: Cookie): Session {
        throw UnsupportedOperationException("not implemented")
    }

    private fun encodeCookie(session: Session): Cookie {
        throw UnsupportedOperationException("not implemented")
    }

}