package com.automacent.fwk.notifiers;


import com.automacent.fwk.reporting.Logger;
import com.automacent.fwk.rest.Client;

public class Slack {

    private static final Logger _logger = Logger.getLogger(Slack.class);

    public void postRequestToSlack(String webHookUrl, String body){
        Client client = new Client();
        _logger.info("Posting slack message to url :" + webHookUrl + ", content :" + body);
        client.validatePostResponseCode(client.postRequestByUrlAndHeadersAndBodyContent(webHookUrl, client.getDefaultHeader(), body));
    }
}
