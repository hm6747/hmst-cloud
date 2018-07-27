/*
package com.syscloud.minitor.config;

import com.google.gson.Gson;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class DingTalkStatusChangeNotifier extends AbstractStatusChangeNotifier {
    private Expression text;
    static Gson gson = new Gson();
    private RestTemplate restTemplate = new RestTemplate();
    private String webhookToken;
    private final SpelExpressionParser parser = new SpelExpressionParser();

    public DingTalkStatusChangeNotifier(String webHookToken){
        this.webhookToken = webHookToken;
        this.text = parser.parseExpression("#{application.name} (#{application.id}) status changed from #{from.status} to #{to.status}  #{application.healthUrl}", ParserContext.TEMPLATE_EXPRESSION);
    }

    private HttpEntity<String> createMessage(String message) {
        Map<String, Object> messageJson = new HashMap<String, Object>();
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("content", message);
        messageJson.put("text", gson.toJson(context));
        messageJson.put("msgtype", "text");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<String>(gson.toJson(messageJson), headers);
    }

    @Override
    protected Mono<Void> doNotify(final InstanceEvent instanceEvent, Instance instance) {
        return new Mono<Void>() {
            @Override
            public void subscribe(CoreSubscriber<? super Void> coreSubscriber) {
                EvaluationContext context = new StandardEvaluationContext(instanceEvent);
                String message = text.getValue(context, String.class);
                restTemplate.postForEntity(webhookToken, createMessage(message), Void.class);
            }
        };
    }
}
*/
