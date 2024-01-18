from time import sleep

from flask import Flask, request, jsonify, abort
import socket
import json

# 챗봇 엔진 서버 접속 정보
host = "127.0.0.1" # 챗봇 엔진 서버 IP 주소
port = 5050 # 챗봇 엔진 서버 통신 포트

#Flask 애플리케이션
app = Flask(__name__)

#챗봇 로그인 엔진 서버와 통신(로그인)
def get_login_from_engine(bottype, id, pw, logincheck):
    # 챗봇 엔진 서버 연결
    mySocket = socket.socket()
    mySocket.connect((host, port))

    #챗봇 엔진 질의 요청
    json_data = {
        'Id': id, #->1111
        'Pw': pw, #->2222,
        'LoginCheck': logincheck,
        'BotType': bottype
    }
    message = json.dumps(json_data)
    mySocket.send(message.encode())

    #챗봇 엔진 답변 출력
    data = mySocket.recv(8192).decode()
    ret_data = json.loads(data)

    #챗봇 엔진 서버 연결 소켓 닫기
    mySocket.close()

    return ret_data

# 챗봇 엔진 서버와 통신(아이디 중복체크)
def get_check_id(bottype, id):
    # 챗봇 엔진 서버 연결
    mySocket = socket.socket()
    mySocket.connect((host, port))
    # 챗봇 엔진 질의 요청
    json_data = {
        'Id': id,
        'BotType': bottype
    }
    message = json.dumps(json_data)
    mySocket.send(message.encode())

    # 챗봇 엔진 답변 출력
    data = mySocket.recv(8192).decode()
    ret_data = json.loads(data)
    # 챗봇 엔진 서버 연결 소켓 닫기
    mySocket.close()

    return ret_data

# 챗봇 엔진 서버와 통신(회원가입)
def Make_join_membership(bottype, id, pw):
    # 챗봇 엔진 서버 연결
    mySocket = socket.socket()
    mySocket.connect((host, port))
    # 챗봇 엔진 질의 요청
    json_data = {
        'Id': id,
        'Pw': pw,
        'BotType': bottype
    }
    message = json.dumps(json_data)
    mySocket.send(message.encode())

    # 챗봇 엔진 답변 출력
    data = mySocket.recv(8192).decode()
    ret_data = json.loads(data)
    # 챗봇 엔진 서버 연결 소켓 닫기
    mySocket.close()

    return ret_data

#챗봇 엔진 서버와 통신(챗봇 질문)
def get_answer_from_engine(bottype, query):
    # 챗봇 엔진 서버 연결
    mySocket = socket.socket()
    mySocket.connect((host, port))

    #챗봇 엔진 질의 요청
    json_data = {
        'Query': query, #->일반 가
        'BotType': bottype
    }
    message = json.dumps(json_data)
    mySocket.send(message.encode())

    #챗봇 엔진 답변 출력
    data = mySocket.recv(8192).decode()
    ret_data = json.loads(data)

    #챗봇 엔진 서버 연결 소켓 닫기
    mySocket.close()

    return ret_data

#챗봇 엔진 서버와 통신(문의사항)
def get_question_from_engine(bottype, member_num, question):
    # 챗봇 엔진 서버 연결
    mySocket = socket.socket()
    mySocket.connect((host, port))

    #챗봇 엔진 질의 요청
    json_data = {
        'Member_num': member_num,
        'Question': question,
        'BotType': bottype
    }
    message = json.dumps(json_data)
    mySocket.send(message.encode())

    #챗봇 엔진 답변 출력
    data = mySocket.recv(8192).decode()
    ret_data = json.loads(data)

    #챗봇 엔진 서버 연결 소켓 닫기
    mySocket.close()

    return ret_data

#챗봇 엔진 서버와 통신(북마크)
def get_bookmark_from_engin(bottype, id, query, bookmark):
    # 챗봇 엔진 서버 연결
    mySocket = socket.socket()
    mySocket.connect((host, port))

    # 챗봇 엔진 질의 요청
    json_data = {
        'Id': id,
        'Query': query,
        'Bookmark': bookmark,
        'BotType': bottype
    }
    message = json.dumps(json_data)
    mySocket.send(message.encode())

    # 챗봇 엔진 답변 출력
    data = mySocket.recv(8192).decode()
    ret_data = json.loads(data)

    # 챗봇 엔진 서버 연결 소켓 닫기
    mySocket.close()

    return ret_data

#챗봇 엔진 서버와 통신(북마크)
def get_bookmark_search_from_engin(bottype, id):
    # 챗봇 엔진 서버 연결
    mySocket = socket.socket()
    mySocket.connect((host, port))

    # 챗봇 엔진 질의 요청
    json_data = {
        'Id': id,
        'BotType': bottype
    }
    message = json.dumps(json_data)
    mySocket.send(message.encode())

    # 챗봇 엔진 답변 출력
    data = mySocket.recv(8192).decode()
    ret_data = json.loads(data)

    # 챗봇 엔진 서버 연결 소켓 닫기
    mySocket.close()

    return ret_data

#챗봇 엔진 서버와 통신(추천질문)
def recommend_question_from_engin(bottype, id):
    # 챗봇 엔진 서버 연결
    mySocket = socket.socket()
    mySocket.connect((host, port))

    # 챗봇 엔진 질의 요청
    json_data = {
        'Id': id,
        'BotType': bottype
    }
    message = json.dumps(json_data)
    mySocket.send(message.encode())

    # 챗봇 엔진 답변 출력
    data = mySocket.recv(8192).decode()
    ret_data = json.loads(data)

    # 챗봇 엔진 서버 연결 소켓 닫기
    mySocket.close()

    return ret_data

#챗봇 엔진 서버와 통신(추천질문)
def recommend_question_search_from_engin(bottype, query):
    # 챗봇 엔진 서버 연결
    mySocket = socket.socket()
    mySocket.connect((host, port))

    # 챗봇 엔진 질의 요청
    json_data = {
        'Query': query,
        'BotType': bottype
    }
    message = json.dumps(json_data)
    mySocket.send(message.encode())

    # 챗봇 엔진 답변 출력
    data = mySocket.recv(8192).decode()
    ret_data = json.loads(data)

    # 챗봇 엔진 서버 연결 소켓 닫기
    mySocket.close()

    return ret_data

@app.route('/', methods=['GET', 'POST'])
def handle_request():
    return "Flask Server & Android are Working Successfully"

#챗봇 엔진 query 전송 API
@app.route('/query/<bot_type>', methods=['POST'])
def query(bot_type):
    body = request.get_json()

    try:
        if bot_type == "LOGINOUT":
            # 챗봇 API 테스트
            ret = get_login_from_engine(bottype=bot_type, id=body['id'], pw=body['pw'], logincheck=body['logincheck'])
            print(ret)

            return jsonify(ret)

        elif bot_type == "CHECKID":
            ret = get_check_id(bottype=bot_type, id=body['id'])
            print(ret)
            return jsonify(ret)

        elif bot_type == "MAKE_JOIN_MEMBERSHIP":
            ret = Make_join_membership(bottype=bot_type, id=body['id'], pw=body['pw'])
            print(ret)
            return jsonify(ret)

        elif bot_type == "TEST":
            # 챗봇 API 테스트
            ret = get_answer_from_engine(bottype=bot_type, query=body['query'])
            print(ret)
            return jsonify(ret)

        elif bot_type == "QUESTIONS":
            # 챗봇 API 테스트
            ret = get_question_from_engine(bottype=bot_type, member_num=body['member_num'], question=body['question'])
            print(ret)
            return jsonify(ret)

        elif bot_type == "BOOKMARK":
            # 챗봇 API 테스트
            ret = get_bookmark_from_engin(bottype=bot_type, id=body['id'], query=body['query'], bookmark=body['bookmark'])
            print(ret)
            return jsonify(ret)

        elif bot_type == "BOOKMARK_SEARCH":
            # 챗봇 API 테스트
            ret = get_bookmark_search_from_engin(bottype=bot_type, id=body['id'])
            print(ret)
            return jsonify(ret)

        elif bot_type == "RECOMMEND_QUESTION":
            # 챗봇 API 테스트
            ret = recommend_question_from_engin(bottype=bot_type, id=body['id'])
            print(ret)
            return jsonify(ret)

        elif bot_type == "RECOMMEND_QUESTION_SEARCH":
            # 챗봇 API 테스트
            ret = recommend_question_search_from_engin(bottype=bot_type, query=body['query'])
            print(ret)
            return jsonify(ret)

        elif bot_type == "KAKAO":
            body = request.get_json()
            utterance = body['userRequest']['utterance']
            query = utterance.strip('\n')
            ret = get_answer_from_engine(bottype=bot_type, query=query)


            from KakaoTemplate import KakaoTemplate
            skillTemplate = KakaoTemplate()
            return skillTemplate.send_response(ret)


        else:
            abort(404)

    except Exception as ex:
        abort(ex)


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)