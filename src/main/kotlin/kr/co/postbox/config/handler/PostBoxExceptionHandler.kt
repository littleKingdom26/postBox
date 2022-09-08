package kr.co.postbox.config.handler


import kr.co.postbox.common.ApiResponse
import kr.co.postbox.config.exception.PostBoxException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@ControllerAdvice
class PostBoxExceptionHandler {

    @Autowired lateinit var messageSource: MessageSource

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PostBoxException::class)
    fun campingATSException(e: PostBoxException): ApiResponse {
        return ApiResponse.error(messageSource.getMessage(e.message?:"ERROR", null, Locale.getDefault()), e.message ?: "ERROR")
    }
}