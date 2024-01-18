import socket
import json

def read_text_data(filename):
    with open(filename, 'r', -1, encoding = "utf8") as f:
        data = []
        for line in f.read().splitlines():
            data.append(line)
        # data = [line for line in f.readlines()]
        # data = data[1:] #헤더 제거 헤더가 없다
    return data

corpus_data = read_text_data('TESTTEXT_H.txt')
f = open('h_result.txt', 'w', encoding="utf-8")
# 챗봇 엔진 서버 접속 정보
host = "127.0.0.1"  # 챗봇 엔진 서버 IP 주소
port = 5050  # 챗봇 엔진 서버 통신 포트

# 클라이언트 프로그램 시작
for data in corpus_data:
    query = data  # 질문 입력
    print("=" * 80)
    print(query)
    f.write("=" * 80)
    f.write(query + '\n')
    # 챗봇 엔진 서버 연결
    mySocket = socket.socket()
    mySocket.connect((host, port))

    # 챗봇 엔진 질의 요청
    json_data = {
        'Query': query,
        'BotType': "MyService"
    }
    message = json.dumps(json_data)
    print(message.encode())
    mySocket.send(message.encode())


    # 챗봇 엔진 답변 출력
    data = mySocket.recv(8192).decode(encoding = "utf-8")
    ret_data = json.loads(data)  # json 형태 문자열을 json 객체로 변환
    print("답변 : ")
    print(ret_data['Answer'])
    f.write(ret_data['Answer'] + '\n')
    f.write("태그명 : " + ret_data['NER'] + '\n')
    f.write("인텐트 : " + ret_data['Intent'] + '\n')
    print("\n")
    # 챗봇 엔진 서버 연결 소켓 닫기
    mySocket.close()
f.close()


