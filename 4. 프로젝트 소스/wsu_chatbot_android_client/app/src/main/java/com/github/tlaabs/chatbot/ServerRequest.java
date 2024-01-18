package com.github.tlaabs.chatbot;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

// 서버에 연결하여 답변을 받는 클래스이다.
public class ServerRequest {

    // 서버에 연결하기 위한 URL 주소를 저장한다.
    private static String URL = "http://" + "54.180.104.198" + ":" + 5000 + "/query/";

    // 함수 내부적으로 사용하는 함수.
    // 통신하는데에 필요한 RequestBody를 만드는데에 필요한 함수이다.
    private static RequestBody buildRequestBody(HashMap msg) {

        JSONObject jsonInput= new JSONObject(msg);

        // 위에서 만든 JSON 객체 데이터를 바탕으로 RequestBody 객체를 만든다.
        RequestBody reqBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonInput.toString()
        );

        return reqBody;
    }

    // 서버에 연결하는함수. 첫번째 함수는 어떤 데이터를 원하냐에 따라서 조금씩 달라진다.
    public static void postRequest(String urlType, String msg4, Callback callback) {

        // RequestBody 객체 생성
        HashMap<String, String> data = new HashMap<>();
        data.put("query", msg4);
        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequest_signup(String urlType, String msg2, String msg3, Callback callback) {

        HashMap<String, String> data = new HashMap<>();
        data.put("id", msg2);
        data.put("pw", msg3);
        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequest_login(String urlType, String msg2, String msg3, String msg4, Callback callback) {
        // RequestBody 객체 생성
        HashMap<String, String> data = new HashMap<>();
        data.put("id", msg2);
        data.put("pw", msg3);
        data.put("logincheck", msg4);
        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequest_idcheck(String urlType, String message1, Callback callback) {
        // RequestBody 객체 생성

        HashMap<String, String> data = new HashMap<>();
        data.put("id", message1);

        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequest_logout(String urlType, String message1, String message2, String message3, Callback callback) {
        // RequestBody 객체 생성

        HashMap<String, String> data = new HashMap<>();
        data.put("id", message1);
        data.put("pw", message2);
        data.put("logincheck", message3);

        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequest_bookmark(String urlType, String message1, String message2, String message3, Callback callback) {
        // RequestBody 객체 생성
        HashMap<String, String> data = new HashMap<>();
        data.put("id", message1);
        data.put("query", message2);
        data.put("bookmark", message3);

        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequest_BookmarkSearch(String urlType, String message1, Callback callback) {
        // RequestBody 객체 생성

        HashMap<String, String> data = new HashMap<>();
        data.put("id", message1);

        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }
    //문의사항
    public static void postRequest_question(String urlType, String msg1, String msg2, Callback callback) {

        HashMap<String, String> data = new HashMap<>();
        data.put("member_num", msg1);
        data.put("question", msg2);
        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void postRequest_BookmarkSelect(String urlType, String message1, String message2, String message3, Callback callback) {
        // RequestBody 객체 생성

        HashMap<String, String> data = new HashMap<>();
        data.put("id", message1);
        data.put("query", message2);
        data.put("bookmark", message3);

        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequest_BookmarkDelete(String urlType, String message1, String message2, String message3, Callback callback) {
        // RequestBody 객체 생성

        HashMap<String, String> data = new HashMap<>();
        data.put("id", message1);
        data.put("query", message2);
        data.put("bookmark", message3);

        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void postRequest_RecommendSearch(String urlType, String message1, Callback callback) {
        // RequestBody 객체 생성

        HashMap<String, String> data = new HashMap<>();
        data.put("id", message1);

        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }
    public static void postRequest_RecommendSelect(String urlType, String message1, Callback callback) {
        // RequestBody 객체 생성

        HashMap<String, String> data = new HashMap<>();
        data.put("query", message1);

        RequestBody requestBody = buildRequestBody(data);

        // 요청을 전송할 OkHttpClient 객체 생성
        OkHttpClient okHttpClient = new OkHttpClient();

        // Timeout 시간을 셋팅하고 빌드한 객체를 저장한다.
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS)
                .build();

        // Request 객체를 생성하여 저장한다.
        Request request = new Request
                .Builder()
                .post(requestBody) // 위에서 만든 RequestBody 객체를 인자로 보낸다. 이때, post() 메서드를 통하여 post 요청임을 명시한다.
                .url(URL + urlType) // 어떤 데이터를 원하냐에 따라서 URL 이 조금씩 달라진다.
                .build();

        // 비동기식으로 실행하여 서버의 연결을 기다린다. 콜백 함수는 이 함수의 세번째 인자를 사용한다.
        okHttpClient.newCall(request).enqueue(callback);
    }

}