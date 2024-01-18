import socket
import json

# 챗봇 엔진 서버 접속 정보
host = "127.0.0.1"  # 챗봇 엔진 서버 IP 주소
port = 5050  # 챗봇 엔진 서버 통신 포트

# 클라이언트 프로그램 시작
while True:
    print("아이디 : ")
    id = input()  # 질문 입력
    print("비밀번호 : ")
    pw = input()  # 질문 입력
    print("닉네임 : ")
    name = input()  # 질문 입력
    if id == "exit":
        exit(0)
    print("=" * 40)

    # 챗봇 엔진 서버 연결
    mySocket = socket.socket()
    mySocket.connect((host, port))

    # 챗봇 엔진 질의 요청
    json_data = {
        'Id': id,
        'Pw': pw,
        'Name': name,
        'BotType': "MAKE_JOIN_MEMBERSHIP"
    }
    message = json.dumps(json_data)
    print(message.encode())
    mySocket.send(message.encode())


    # 챗봇 엔진 답변 출력
    data = mySocket.recv(8192).decode(encoding="UTF-8")
    ret_data = json.loads(data)  # json 형태 문자열을 json 객체로 변환
    print(ret_data)
    print("\n")
    # 챗봇 엔진 서버 연결 소켓 닫기
    mySocket.close()