class MySqlQuery:
    def __init__(self, db):
        self.db = db

    # 검색 쿼리 생성
    def _make_query(self, intent_name, ner_tags):
        sql = "select * from chatbot_train_data"
        if intent_name is not None and ner_tags is None:
            sql = sql + " where intent='{}' and ner is Null".format(intent_name)

        elif intent_name is not None and ner_tags is not None:
            where = " where intent='%s' " % intent_name
            if len(ner_tags) > 0:
                where += "and "
                for ne in ner_tags:
                    where += " ner = '{}' ".format(ne)
            sql = sql + where

        # 동일한 답변이 2개 이상인 경우 랜덤으로 선택
        sql = sql + " order by rand() limit 1"
        return sql

    def _make_query1(self, query):
        sql = "select answer from answerqna"
        if query is not None:
            sql = sql + " where query='{}'".format(query)
        return sql

    # 답변 검색
    def search(self, intent_name, ner_tags):
        # 의도명과 개체명으로 답변 검색

        sql = self._make_query(intent_name, ner_tags)
        answer = self.db.select_one_answer(sql)

        # 검색되는 답변이 없으면 의도명만 검색
        if answer is None:
            sql = self._make_query(intent_name, None)
            answer = self.db.select_one_answer(sql)

        return answer['answer'], answer['answer_image']

    def selectQNA(self, query):
        # 의도명과 개체명으로 답변 검색

        sql = self._make_query1(query)
        answer = self.db.select_one_answer(sql)

        # 검색되는 답변이 없으면 의도명만 검색
        if answer is None :
            answer = "학습이 필요합니다."

        return answer['answer']

    # NER 태그를 실제 입력된 단어로 변환
    def tag_to_word(self, ner_predicts, answer):
        for word, tag in ner_predicts:

            # 변환해야 하는 태그가 있는 경우 추가
            if tag == 'B_FOOD':
                answer = answer.replace(tag, word)

        answer = answer.replace('{', '')
        answer = answer.replace('}', '')
        return answer

    # INSERT
    # into
    # bookmark(id, query)
    def insert_bookmark(self, id, query):
        sql = '''
                insert into chatbot.bookmark(id, query) values(
                '%s', '%s'
                    )
                ''' % (id, query)
        self.db.insertbookmark(sql)

    def bookmark_check(self, id, query):
        sql = '''
             select * from chatbot.bookmark where id = '{}' and query = '{}'
        '''.format(id, query)
        answer = self.db.select_id(sql)
        if answer is True:
            return True
        return False

    def select_bookmark(self, id, query):
        sql = "select query from chatbot.bookmark where id='{}' and query='{}'".format(id, query)

        data = self.db.select_one_answer(sql)
        if data is None:
            return "데이터를 찾지 못했습니다.    "
        return data

    def select_data(self, query):
        sql = "select answer, answer_image from chatbot.chatbot_train_data where query='{}'".format(query)
        data = self.db.select_one_answer(sql)
        if data is None:
            return "데이터를 찾지 못했습니다.    "
        return data['answer'], data['answer_image']

    def delete_bookmark(self, id, query):
        sql = "delete from chatbot.bookmark where id = '{}' and query = '{}'".format(id, query)
        answer = self.db.insert(sql)
        if answer is True:
            return True
        return False

    def search_bookmark(self, id):
        sql = "select * from chatbot.bookmark where id='{}'".format(id)
        answer = self.db.select_id(sql)

        return answer

    def search_bookmark_v1(self, id):
        sql = "select * from chatbot.bookmark where id='{}'".format(id)
        answer = self.db.select_all(sql)

        return answer

    def InsertMysql_joinmembership(self, id, pw):
        sql = '''
                INSERT into chatbot.member(id, pw) values(
                '%s', '%s'
                    )
                ''' % (id, pw)  # 없는것들을 널로
        answer = self.db.insert(sql)
        if answer is True:
            return True
        return False

    def LoginCheck(self, id, pw):
        sql = "select * from chatbot.member where id = '{}' and pw ='{}'".format(id, pw)
        answer = self.db.select_one(sql)
        if answer is not None:
            return True
        return False

    def Login(self, id, logincheck):
        sql = "Update chatbot.member Set logincheck = '{}' where id = '{}'".format(logincheck, id)
        answer = self.db.Login(sql)
        if answer is True:
            return True
        return False

    def Logincheckid(self, id):
        sql = "select * from chatbot.member where id = '{}' and logincheck = '1'".format(id)
        answer = self.db.select_one(sql)
        if answer is not None:
            return True
        return False

    def checkId_query(self, id):
        sql = "select id from chatbot.member where id = '{}'".format(id)
        answer = self.db.select_one(sql)
        if answer is None:
            return True
        return False

    # 문의사항 데이터베이스에 넣기
    def Questions(self, member_num, question):
        sql = '''
               INSERT into chatbot.questions(member_num, question) values(
                   '%s', '%s'
                    )
               ''' % (member_num, question)
        # 없는것들을 널로
        question = self.db.insert(sql)
        if question is True:
            return True
        return False

    def Count_Bookmark(self, id):
        sql = "SELECT COUNT(if(id = '{}',id,null)) FROM chatbot.bookmark".format(id)

        # 없는것들을 널로
        count = self.db.recommend_que(sql)
        return count

    def insert_query(self, intent, query):
        sql = '''
                insert into chatbot.insertquery(intent, query) values(
                '%s', '%s')
                on DUPLICATE KEY UPDATE count = count+1;
            ''' % (intent, query)
        self.db.insertquery(sql)
