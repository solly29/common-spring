package com.solly.commonspring.config

import com.solly.commonspring.properties.AjpConfigProperties
import org.apache.catalina.connector.Connector
import org.apache.coyote.ajp.AbstractAjpProtocol
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Configuration
import java.net.InetSocketAddress

@Configuration
@EnableConfigurationProperties(AjpConfigProperties::class)
@ConditionalOnProperty(value = ["tomcat.ajp.enable"], havingValue = "true", matchIfMissing = false)
class AjpConfig (
    private val properties: AjpConfigProperties,
) : WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    override fun customize(factory: TomcatServletWebServerFactory?) {
        val ajpConnector = Connector(properties.protocol).apply {
            port = properties.port.toInt()

            secure = false
            allowTrace = false
            scheme = "http"
            (protocolHandler as AbstractAjpProtocol<*>).secretRequired = false
            (protocolHandler as AbstractAjpProtocol<*>).address = InetSocketAddress(0).address
        }
        factory?.addAdditionalTomcatConnectors(ajpConnector)
    }

}
