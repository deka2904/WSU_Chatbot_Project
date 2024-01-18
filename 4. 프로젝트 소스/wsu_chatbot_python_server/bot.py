import json,random
import threading
from time import sleep

from config.DatabaseConfig import *
from utils.Database import Database
from utils.BotServer import BotServer
from utils.Preprocess import Preprocess
from models.intent.IntentModel import IntentModel
from models.ner.NerModel import *
from utils.MySqlQuery import MySqlQuery
from gensim.models import Word2Vec

recommendmodel = Word2Vec.load('Re_Qu/nvmc.model')

# 전처리 객체 생성 : 형태소 분석과 그것을 이용하여 시퀀스 벡터를 만듦
p = Preprocess(word2index_dic='train_tools/dict/chatbot_dict.bin',# word to index 사전
               userdic='utils/user_dic.tsv') # 유저 단어 사전

# 의도 파악 모델
intent = IntentModel(model_name='models/intent/intent_model.h5', preprocess=p)

# 개체명 인식 모델
ner1_susi = NerModel_Susi(model_name='models/ner/ner_model_susi.h5', preprocess=p) # 수시 개체명 인식모델
ner2_jungsi = NerModel_jungsi(model_name='models/ner/ner_model_jungsi.h5', preprocess=p) # 정시 개체명 인식모델
ner3_h = NerModel_H(model_name='models/ner/ner_model_h.h5', preprocess=p) # 공통 개체명 인식모델

# 코사인유사도 챗봇
# pororo = None

def to_client(conn, addr, params):
    db = params['db']
    try:
        db.connect()  # DB 연결
        f = MySqlQuery(db)  # 답변검색 클래스 생성

        # 데이터 수신
        read = conn.recv(8192)  # 수신 데이터가 있을 때까지 블로킹
        print("===========================")
        print("Connection from: %s" % str(addr))

        if read is None or not read:
            # 클라이언트 연결이 끊어지거나 오류가 있는 경우
            print("클라이언트 연결 끊어짐")
            exit(0)  # 스레드 강제 종료

        # json 데이터로 변환

        recv_json_data = json.loads(read.decode())
        print("데이터 수신 : ", recv_json_data)

        # 회원가입이 안되어 있을 경우 클라이언트 쪽에서 답변 입력을 허용하면
        # MAKE_JOIN_MEMBERSHIP타입으로 서버로 들어와서 답변을 만듦
        # 회원 가입
        if recv_json_data['BotType'] == "MAKE_JOIN_MEMBERSHIP":
            id = recv_json_data['Id']
            pw = recv_json_data['Pw']
            answer = f.checkId_query(id)
            if answer is True:
                insert = f.InsertMysql_joinmembership(id, pw)
                if insert is True:
                    answer = "회원가입 성공"
                send_json_data_str = {
                    "Id": id,
                    "Pw": pw,
                    "answer": answer  # 성공 시
                }
                message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                conn.send(message.encode())  # 응답 전송
                return

            elif answer is False:
                answer = "존재하는 회원입니다."
                send_json_data_str = {
                    "Id": id,
                    "Pw": pw,
                    "answer": answer  #실패 시
                }
                message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                conn.send(message.encode())  # 응답 전송
                return
        # 아이디 중복체크
        elif recv_json_data['BotType'] == "CHECKID":
            id = recv_json_data['Id']
            answer = f.checkId_query(id)
            if answer is True:
                answer = "회원가입 가능한 아이디입니다."
                send_json_data_str = {
                    "Id": id,
                    "answer": answer  # 성공 시
                }
                message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                conn.send(message.encode())  # 응답 전송
                return
            else:
                answer = "존재하는 아이디입니다."
                send_json_data_str = {
                    "Id": id,
                    "answer": answer  # 성공 시
                }
                message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                conn.send(message.encode())  # 응답 전송
                return
        # 로그인 아웃
        elif recv_json_data['BotType'] == "LOGINOUT":
            id = recv_json_data['Id']
            pw = recv_json_data['Pw']
            logincheck = recv_json_data['LoginCheck']
            answer = f.LoginCheck(id, pw)
            logincheck_id = f.Logincheckid(id)
            if logincheck == '1':
                if answer is True:
                    if logincheck_id is True:
                        answer = "로그인 상태입니다."
                        send_json_data_str = {
                            "Id": id,
                            "Pw": pw,
                            "LoginCheck": logincheck,
                            "answer": answer  # 성공 시
                        }
                        message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                        conn.send(message.encode())  # 응답 전송
                        return
                    answer = "로그인 성공"
                    send_json_data_str = {
                        "Id": id,
                        "Pw": pw,
                        "LoginCheck": logincheck,
                        "answer": answer  # 성공 시
                    }
                    f.Login(id, logincheck)
                    message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                    conn.send(message.encode())  # 응답 전송
                    return

                else:
                    answer = "로그인 실패"
                    send_json_data_str = {
                        "Id": id,
                        "Pw": pw,
                        "LoginCheck": logincheck,
                        "answer": answer  # 실패 시
                    }
                    message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                    conn.send(message.encode())  # 응답 전송
                    return

            elif logincheck == '0':
                if answer is True:
                    answer = "로그아웃 성공"
                    send_json_data_str = {
                        "Id": id,
                        "Pw": pw,
                        "LoginCheck": logincheck,
                        "answer": answer  # 성공 시
                    }
                    f.Login(id, logincheck)
                    message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                    conn.send(message.encode())  # 응답 전송
                    return
                else:
                    answer = "로그아웃 실패"
                    send_json_data_str = {
                        "Id": id,
                        "Pw": pw,
                        "LoginCheck": logincheck,
                        "answer": answer  # 실패 시
                    }
                    message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                    conn.send(message.encode())  # 응답 전송
                    return
        #문의사항
        elif recv_json_data['BotType'] == "QUESTIONS":
            member_num = recv_json_data['Member_num']
            question = recv_json_data['Question']
            answer = f.Questions(member_num, question)
            if answer is True:
                answer = "문의 성공"
                send_json_data_str = {
                    "Member_num": member_num,
                    "Question": question,
                    "answer": answer  # 성공 시
                }
                message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                conn.send(message.encode())  # 응답 전송
                return
            elif answer is False:
                answer = "문의 실패"
                send_json_data_str = {
                    "Member_num": member_num,
                    "Question": question,
                    "answer": answer  # 실패 시
                }
                message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                conn.send(message.encode())  # 응답 전송
                return
        #북마크
        elif recv_json_data['BotType'] == "BOOKMARK":
            id = recv_json_data['Id']
            bookmark = recv_json_data['Bookmark']
            if bookmark == "save" or bookmark == "select" or bookmark == "delete":
                query = recv_json_data['Query']
            if bookmark == "save":
                answer = f.checkId_query(id)
                if answer is False:
                    insert = f.bookmark_check(id, query)
                    print(insert)
                    if insert is True:
                        f.insert_bookmark(id, query)
                        answer = "북마크 설정 완료"
                        send_json_data_str = {
                            "query": query,
                            "answer": answer  # 성공 시
                        }
                        message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                        conn.send(message.encode())  # 응답 전송
                        return

                    elif insert is False:
                        answer = "북마크 설정 실패"
                        send_json_data_str = {
                            "query": query,
                            "answer": answer  # 성공 시
                        }
                        message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                        conn.send(message.encode())  # 응답 전송
                        return
            elif bookmark == "select":
                answer = f.checkId_query(id)
                if answer is False:
                    data = f.select_bookmark(id, query)
                    query = data['query']
                    intent_predict = intent.predict_class(query)
                    intent_name = intent.labels[intent_predict]  # 나온 의도를 의도명으로 바꿈 ex) 0 -> 인사
                    ner_predicts = None
                    ner_tags = None

                    # 나온 의도에 따라 각각의 개체명인식 모델로  보내서 개체명인식을 함
                    if intent_predict == 1:
                        ner_predicts = ner1_susi.predict(query)
                        ner_tags = ner1_susi.predict_tags(query)
                    elif intent_predict == 2:
                        ner_predicts = ner2_jungsi.predict(query)
                        ner_tags = ner2_jungsi.predict_tags(query)
                    elif intent_predict == 3:
                        ner_predicts = ner3_h.predict(query)
                        ner_tags = ner3_h.predict_tags(query)
                    print(intent_predict)
                    print(ner_tags)

                    # 답변 검색
                    try:
                        if ((intent_predict == 0 or intent_predict == 4) and ner_tags == None):
                            answer_image = None
                            answer = f.selectQNA(query)  # 의도명과 개체명이 일치하는 답변을 찾음
                        else:
                            answer_image = None
                            answer_text, answer_image = f.search(intent_name, ner_tags)  # 의도명과 개체명이 일치하는 답변을 찾음
                            answer = f.tag_to_word(ner_predicts,
                                                   answer_text)  # 답변의 태그명을 단어로 바꿈 -> {B_W17} 길찾기 -> 미디어융합관 길찾기
                            # DB에 답변이 없을 경우 예외 처리가 발생하여 except부분으로 넘어감
                    except Exception as e:
                        print(e)
                        answer = "학습이 필요로 합니다"
                        answer_image = None

                    send_json_data_str = {
                        "Query": query,
                        "Answer": answer,
                        "AnswerImageUrl": answer_image,
                        "Intent": str(intent_predict),
                        "NER": str(ner_predicts)
                    }

                    message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                    conn.send(message.encode())  # 응답 전송
                    return
                else:
                    answer = "북마크 불러오기 실패"
                    send_json_data_str = {
                        "query": query,
                        "answer": answer  # 성공 시
                    }
                    message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                    conn.send(message.encode())  # 응답 전송
                    return

            elif bookmark == "delete":
                answer = f.checkId_query(id)
                if answer is False:
                    data = f.delete_bookmark(id, query)
                    if data is True:
                        answer = "북마크 삭제 성공"
                        send_json_data_str = {
                            "query": query,
                            "answer": answer  # 성공 시
                        }
                        message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                        conn.send(message.encode())  # 응답 전송
                        return
                    else:
                        answer = "북마크 삭제 실패"
                        send_json_data_str = {
                            "query": query,
                            "answer": answer  # 성공 시
                        }
                        message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                        conn.send(message.encode())  # 응답 전송
                        return
        #북마크 불러오기
        elif recv_json_data['BotType'] == "BOOKMARK_SEARCH":
            id = recv_json_data['Id']
            answer = f.checkId_query(id)
            if answer is False:
                datas = f.search_bookmark_v1(id)
                if datas is not None:
                    answer = "북마크 불러오기 성공"
                    query = list(data['query'] for data in datas)
                    send_json_data_str = {
                        "Id": id,
                        "query": query,
                        "answer": answer  # 성공 시
                    }
                    message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                    conn.send(message.encode())  # 응답 전송
                    return
                    # 추천질문
        # 추천질문
        elif recv_json_data['BotType'] == "RECOMMEND_QUESTION":
            id = recv_json_data['Id']
            answer = f.checkId_query(id)
            if answer is False:
                select = f.search_bookmark(id)
                datas = f.search_bookmark_v1(id)
                if select == False:
                    count = f.Count_Bookmark(id)
                    print(count, "count")
                    query = ""
                    answer = "추천질문을 불러옵니다"
                    if count == 1:
                        for data in datas:
                            print(data)
                            a = recommendmodel.wv.most_similar(data['query'], topn=5)
                            for b, c in a:
                                query = list(b)

                    else:
                        allrecommend = []
                        for data in datas:
                            print(data)
                            a = recommendmodel.wv.most_similar(data['query'], topn=5)
                            for b, c in a:
                                allrecommend.append(b)
                        query = list(random.sample(allrecommend, 5))
                elif select == True:
                    answer = "북마크 먼저 등록해 주세요."
                    send_json_data_str = {
                        "answer": answer  # 성공 시
                    }
                    message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
                    conn.send(message.encode())  # 응답 전송
                    query = ""
            send_json_data_str = {
                "Id": id,
                "query": query,
                "answer": answer  # 성공 시
            }
            message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
            conn.send(message.encode())  # 응답 전송
            return
        # 추천질문 불러오기
        elif recv_json_data['BotType'] == "RECOMMEND_QUESTION_SEARCH":
            query = recv_json_data['Query']
            answer, answer_image = f.select_data(query)

            send_json_data_str = {
                "query": query,
                "answer": answer,
                "answer_image": answer_image  # 성공 시
            }
            message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
            conn.send(message.encode())  # 응답 전송
            return
        # 챗봇 질문
        elif recv_json_data['BotType'] == "TEST" or "KAKAO":
            query = recv_json_data['Query']
            # 의도 파악  들어온 질문의 의도를 파악함
            intent_predict = intent.predict_class(query)
            intent_name = intent.labels[intent_predict]  # 나온 의도를 의도명으로 바꿈 ex) 0 -> 인사
            ner_predicts = None
            ner_tags = None

            # 나온 의도에 따라 각각의 개체명인식 모델로  보내서 개체명인식을 함
            if intent_predict == 1:
                ner_predicts = ner1_susi.predict(query)
                ner_tags = ner1_susi.predict_tags(query)
            elif intent_predict == 2:
                ner_predicts = ner2_jungsi.predict(query)
                ner_tags = ner2_jungsi.predict_tags(query)
            elif intent_predict == 3:
                ner_predicts = ner3_h.predict(query)
                ner_tags = ner3_h.predict_tags(query)
            print(intent_name)

            print(ner_tags)
            print("=================================================================================")

            # 답변 검색
            try:
                if((intent_predict == 0 or intent_predict == 4) and ner_tags == None):
                    answer_image = None
                    answer = f.selectQNA(query)  # 의도명과 개체명이 일치하는 답변을 찾음
                    data = f.insert_query(intent_name, query)
                    print(answer)
                    print(data)
                    #answer = f.tag_to_word(ner_predicts, answer_text)  # 답변의 태그명을 단어로 바꿈 -> {B_W17} 길찾기 -> 미디어융합관 길찾기
                    # DB에 답변이 없을 경우 예외 처리가 발생하여 except부분으로 넘어감
                else:
                    answer_image = None
                    answer_text, answer_image = f.search(intent_name, ner_tags)  # 의도명과 개체명이 일치하는 답변을 찾음
                    answer = f.tag_to_word(ner_predicts, answer_text)  # 답변의 태그명을 단어로 바꿈 -> {B_W17} 길찾기 -> 미디어융합관 길찾기
                    data = f.insert_query(intent_name, query)
                    print(answer)
                    print(data)
                    # DB에 답변이 없을 경우 예외 처리가 발생하여 except부분으로 넘어감
            except:
                answer = "조금 더 구체적으로 얘기해주시겠어요?😥 질문이 간결하고 정확할수록 더 잘 답변할 수 있습니다!🙂"
                answer_image = None
                data = f.insert_query(intent_name, query)
                print(data)


            send_json_data_str = {
                "Query": query,
                "Answer": answer,
                "AnswerImageUrl": answer_image,
                "Intent": str(intent_predict),
                "NER": str(ner_predicts)
            }

            message = json.dumps(send_json_data_str)  # json 객체를 전송 가능한 문자열로 변환
            conn.send(message.encode())  # 응답 전송

    except Exception as ex:
        print(ex)

    finally:
        if db is not None:  # db 연결 끊기
            db.close()
        conn.close()


if __name__ == '__main__':
    # 질문/답변 학습 db 연결 객체 생성
    db = Database(
        host=DB_HOST, user=DB_USER, password=DB_PASSWORD, db_name=DB_NAME
    )
    print("DB 접속")
    # 봇 서버 동작
    port = 5050
    listen = 100
    bot = BotServer(port, listen)
    bot.create_sock()
    print("bot start")

    while True:

        conn, addr = bot.ready_for_client()
        params = {
            "db": db
        }

        client = threading.Thread(target=to_client, args=(
            conn,  # 클라이언트 연결 소켓
            addr,  # 클라이언트 연결 주소 정보
            params  # 스레드 함수 파라미터
        ))
        client.start()  # 스레드 시작