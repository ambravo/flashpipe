package io.github.engswee.flashpipe.cpi.api

import io.github.engswee.flashpipe.http.HTTPExecuter
import io.github.engswee.flashpipe.http.HTTPExecuterApacheImpl
import io.github.engswee.flashpipe.http.OAuthToken
import spock.lang.Shared
import spock.lang.Specification

class RuntimeArtifactOAuthIT extends Specification {

    @Shared
    HTTPExecuter httpExecuter
    @Shared
    RuntimeArtifact runtimeArtifact

    def setupSpec() {
        def host = System.getProperty('cpi.host.tmn')
        def clientid = System.getProperty('cpi.oauth.clientid')
        def clientsecret = System.getProperty('cpi.oauth.clientsecret')
        def oauthHost = System.getProperty('cpi.host.oauth')
        def token = OAuthToken.get('https', oauthHost, 443, clientid, clientsecret)

        httpExecuter = HTTPExecuterApacheImpl.newInstance('https', host, 443, token)
        runtimeArtifact = new RuntimeArtifact(httpExecuter)
    }

    def 'Undeploy'() {
        when:
        runtimeArtifact.undeploy('FlashPipe_Update', null)

        then:
        noExceptionThrown()
    }

    def 'Deploy'() {
        given:
        DesignTimeArtifact designTimeArtifact = new DesignTimeArtifact(httpExecuter)

        when:
        designTimeArtifact.deploy('FlashPipe_Update', null)

        then:
        noExceptionThrown()
    }

    def 'Query'() {
        when:
        runtimeArtifact.getStatus('FlashPipe_Update')

        then:
        noExceptionThrown()
    }
}