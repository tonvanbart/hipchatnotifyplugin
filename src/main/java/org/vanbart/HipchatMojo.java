package org.vanbart;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Simple Maven mojo to send a notification from a build to a Hipchat room.
 * @since 30/06/2017.
 */
@Mojo(name = "notify")
public class HipchatMojo extends AbstractMojo {

    /**
     * The notification to send; default is "(no message)".
     */
    @Parameter(defaultValue = "(no message)", property = "notify.message")
    private String message;

    /**
     * A label added to the sender's name; defaults to "(Maven build)".
     */
    @Parameter(defaultValue = "(Maven build)", property = "notify.from")
    private String from;

    /**
     * The ID of the room where the notification is sent. This is a mandatory field.
     */
    @Parameter(property = "notify.room")
    private String room;

    /**
     * The authentication token for sending the message. This is a mandatory field.
     */
    @Parameter(property = "notify.authToken")
    private String authToken;

    /**
     * Execute the mojo. Fails if any mandatory parameter is missing.
     * @throws MojoExecutionException if any required parameter is missing.
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (isEmpty(room) || isEmpty(authToken)) {
            throw new MojoExecutionException("The 'room' and 'authToken' parameters are mandatory");
        }

        RestTemplate restTemplate = new RestTemplate();

        MessageDto messageDto = new MessageDto();
        messageDto.setMessage(message);
        messageDto.setFrom(from);

        String url = "https://backbase.hipchat.com/v2/room/"
                + room
                + "/notification?auth_token="
                + authToken;

        ResponseEntity<String> response = restTemplate.postForEntity(url, messageDto, String.class);
        HttpStatus statusCode = response.getStatusCode();
        if ( ! HttpStatus.NO_CONTENT.equals(statusCode)) {
            getLog().warn("Hipchat notification failed, HTTP status = " + statusCode);
        } else {
            getLog().info("Nofified Hipchat.");
        }
    }

    private boolean isEmpty(String s) {
        return (s == null || s.length() == 0);
    }
}
