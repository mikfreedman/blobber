package io.pivotal

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.HandlerInterceptor
import javax.security.auth.login.CredentialException
import javax.servlet.http.HttpServletRequest


@Component
class TestInterceptor : HandlerInterceptor {

    @Value("\${blobber.api_key}")
    lateinit var apiKey: String

    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {
    }

    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: java.lang.Exception?) {
    }

    @Throws(Exception::class)
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val supppliedApiKey = request.getHeader("X-API-KEY")

        val isGet = request.method == HttpMethod.GET.toString()

        if (isGet || (supppliedApiKey == apiKey)) {
            return true
        }

        throw HttpUnauthorizedException()
    }
}