package services.websocket;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.ws.Message;
import akka.http.javadsl.model.ws.TextMessage;
import akka.http.javadsl.model.ws.WebSocket;
import akka.japi.Function;
import akka.japi.JavaPartialFunction;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"Convert2MethodRef", "ConstantConditions"})
public class WebsocketServerForEcho {

    // -----------------------------------------------------------------------------------------
    // Websocket Handlers
    // -----------------------------------------------------------------------------------------
    /**
     * A handler that treats all incoming messages and responds with an echo.
     * However, if the incoming message is "ping" then the response will be "pong".
     */
    public static Flow<Message, Message, NotUsed> echoStreamer() {
        return
                Flow.<Message>create()
                        .collect(new JavaPartialFunction<Message, Message>() {
                            @Override
                            public Message apply(Message msg, boolean isCheck) throws Exception
                            {
                                if (isCheck)
                                {
                                    if (msg.isText())
                                    {
                                        return null;
                                    }
                                    else
                                    {
                                        throw noMatch();
                                    }
                                }
                                else
                                {
                                    return handleTextMessage(msg.asTextMessage());
                                }
                            }
                        });
    }
    public static TextMessage handleTextMessage(TextMessage msg)
    {
        if (msg.isStrict()) // optimization that directly creates a simple response...
        {
            String incomingMsg = msg.getStrictText();

            // On incoming msg is "ping" then respond "pong"
            if(incomingMsg.equals("ping"))
            {
                return TextMessage.create("pong");
            }
            else
            {
                return TextMessage.create(incomingMsg);
            }
        }
        else // default to handle all text messages in a streaming fashion
        {
            return TextMessage.create(Source.single("Hello ").concat(msg.getStreamedText()));
        }
    }

    // -----------------------------------------------------------------------------------------
    // Service Route: Websocket Request Handling
    // -----------------------------------------------------------------------------------------
    public static HttpResponse handleRequest(HttpRequest request)
    {
        System.out.println("Handling request to " + request.getUri());

        if (request.getUri().path().equals("/echo"))
        {
            final Flow<Message, Message, NotUsed> echoFlow = echoStreamer();

            return WebSocket.handleWebSocketRequestWith(request, echoFlow);
        }
        else
        {
            return HttpResponse.create().withStatus(404);
        }
    }

    // -----------------------------------------------------------------------------------------
    // Websocket Service - Main Loop
    // -----------------------------------------------------------------------------------------
    public static void main(String[] args) throws Exception
    {
        // -------------------------------------------------------------------------------------------------------------
        // Cloud-Deployment: Heroku-App Requirements
        // -------------------------------------------------------------------------------------------------------------
        // BIND_ADDRESS to 0.0.0.0
        final String BIND_ADDRESS =
                System.getenv("BIND_ADDRESS") != null ? System.getenv("BIND_ADDRESS") : "0.0.0.0";
        // Obtain PORT, which is allocated for this endpoint by Heroku-App
        final int BIND_PORT =
                System.getenv("PORT") != null ? Integer.parseInt(System.getenv("PORT")) : 8080;
        // -------------------------------------------------------------------------------------------------------------

        ActorSystem system = ActorSystem.create();

        try
        {
            final Materializer materializer = ActorMaterializer.create(system);

            final Function<HttpRequest, HttpResponse> handler = request -> handleRequest(request);
            CompletionStage<ServerBinding> serverBindingFuture =
                    Http.get(system).bindAndHandleSync(handler,
                                                       ConnectHttp.toHost(BIND_ADDRESS, BIND_PORT),
                                                       materializer);

            // will throw if binding fails
            serverBindingFuture.toCompletableFuture().get(1, TimeUnit.SECONDS);

            /*
            System.out.println("Press ENTER to stop.");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
            */

            System.out.println("Websocket Server is running...");

            // Let this main-loop run forever
            // Reference: https://stackoverflow.com/questions/12199011/how-to-run-a-program-forever-in-java-is-system-in-read-the-only-way)
            Thread.currentThread().join();
        }
        finally
        {
            system.terminate();
        }
    }
}