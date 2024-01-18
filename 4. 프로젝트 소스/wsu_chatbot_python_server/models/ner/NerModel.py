import tensorflow as tf
import numpy as np
from keras.models import Model, load_model
from keras import utils

#수시
class NerModel_Susi:
    def __init__(self, model_name, preprocess):

        # NER 태그 클래시별 레이블
        item =   {'O': 1, 'B_S_INTERVIEW': 2, 'B_S_SUBANN': 3, 'B_S_REC': 4, 'B_S_SCHOLARSHIP': 5, 'B_S_AREA': 6, 'B_S_INQUIRY': 7,
                  'B_S_GRADEPRODUCTION': 8, 'B_S_FOUNDATION': 9, 'B_S_GRADE': 10, 'B_S_SOFTWARE': 11, 'B_S_GYOWAY': 12, 'B_S_JONGWAY': 13,
                  'B_S_SYNS': 14, 'B_S_SYNS1': 15, 'B_S_SYNS2': 16, 'B_S_INQUIRYF': 17, 'B_S_SCHEDULES': 18, 'B_S_RECRUITMENT': 19,
                  'B_S_SUSI': 20, 'B_S_GENERAL': 21, 'B_S_GENERAL1': 22, 'B_S_GENERAL2': 23, 'B_S_AREASUB': 24, 'B_S_AREASYN': 25,
                  'B_S_SELFRECOM': 26, 'B_S_VILLAGE': 27, 'B_S_CHARA': 28, 'B_S_WAY': 29, 'B_S_ACCEPTANCE': 30, 'B_S_SCHEDULESANN': 31,
                  'B_S_SYNANN': 32, 'B_S_ACCEPTANCEANN': 33}
        self.index_to_ner = {}
        for k, v in item.items():
            self.index_to_ner[v] = k

        # 의도 분류 모델 불러오기
        self.model = load_model(model_name)

        # 챗봇 Preprocess 객체
        self.p = preprocess

    # 개체명 클래스 예측
    def predict(self, query):
        # 형태소 분석
        pos = self.p.pos(query)

        # 문장 내 키워드 추출(불용어 제거)
        keywords = self.p.get_keywords(pos, without_tag=True)
        sequences = [self.p.get_wordidx_sequence(keywords)]

        # 패딩 처리
        max_len = 40

        padded_seqs = utils.pad_sequences(sequences, padding="post", value=0, maxlen=max_len)

        # 키워드별 개체명 예측
        predict = self.model.predict(np.array([padded_seqs[0]])) #오류코드 시발새끼
        predict_class = tf.math.argmax(predict, axis=-1)

        tags = [self.index_to_ner[i] for i in predict_class.numpy()[0]]
        return list(zip(keywords, tags))

    def predict_tags(self, query):
        # 형태소 분석
        pos = self.p.pos(query)

        # 문장 내 키워드 추출(불용어 제거)
        keywords = self.p.get_keywords(pos, without_tag=True)
        sequences = [self.p.get_wordidx_sequence(keywords)]

        # 패딩 처리
        max_len = 40
        padded_seqs = utils.pad_sequences(sequences, padding="post", value=0, maxlen=max_len)
        predict = self.model.predict(np.array([padded_seqs[0]]))
        score = np.max(predict)
        print(score)
        if score < 0.8:
            return None
        predict_class = tf.math.argmax(predict, axis=-1)

        tags = []
        for tag_idx in predict_class.numpy()[0]:
            if tag_idx == 1: continue
            tags.append(self.index_to_ner[tag_idx])

        if len(tags) == 0:  return None
        return tags

#정시
class NerModel_jungsi:
    def __init__(self, model_name, preprocess):

        # BIO 태그 클래시별 레이블
        item = {'O': 1, 'B_J_METHOD': 2, 'B_J_GENERAL': 3, 'B_J_STUDENT': 4, 'B_J_GRADE': 5, 'B_J_SCHOLARSHIP': 6, 'B_J_FOUNDATION': 7, 'B_J_EDUCATION': 8,
                'B_J_SCHEDULE': 9, 'B_J_RECRUITMENT': 10, 'B_J_SYNS': 11, 'B_J_ACCEPTANCE': 12, 'B_J_INQUIRYF': 13, 'B_J_JUNGSI': 14, 'B_J_ADMISSIONFREE': 15,
                'B_J_VILLAGE': 16, 'B_J_CHARA': 17, 'B_J_SCHEDULESANN': 18, 'B_J_MODELANN': 19}


        self.index_to_ner = {}
        for k, v in item.items():
            self.index_to_ner[v] = k

        # 의도 분류 모델 불러오기
        self.model = load_model(model_name)

        # 챗봇 Preprocess 객체
        self.p = preprocess

    # 개체명 클래스 예측
    def predict(self, query):
        # 형태소 분석
        pos = self.p.pos(query.upper())

        # 문장 내 키워드 추출(불용어 제거)
        keywords = self.p.get_keywords(pos, without_tag=True)
        sequences = [self.p.get_wordidx_sequence(keywords)]

        # 패딩 처리
        max_len = 40

        padded_seqs = utils.pad_sequences(sequences, padding="post", value=0, maxlen=max_len)

        # 키워드별 개체명 예측
        predict = self.model.predict(np.array([padded_seqs[0]]))
        predict_class = tf.math.argmax(predict, axis=-1)

        tags = [self.index_to_ner[i] for i in predict_class.numpy()[0]]
        return list(zip(keywords, tags))

    def predict_tags(self, query):
        # 형태소 분석
        pos = self.p.pos(query)

        # 문장 내 키워드 추출(불용어 제거)
        keywords = self.p.get_keywords(pos, without_tag=True)
        sequences = [self.p.get_wordidx_sequence(keywords)]

        # 패딩 처리
        max_len = 40
        padded_seqs = utils.pad_sequences(sequences, padding="post", value=0, maxlen=max_len)

        predict = self.model.predict(np.array([padded_seqs[0]]))
        score = np.max(predict)
        print(score)
        if score < 0.8:
            return None
        predict_class = tf.math.argmax(predict, axis=-1)

        tags = []
        for tag_idx in predict_class.numpy()[0]:
            if tag_idx == 1: continue
            tags.append(self.index_to_ner[tag_idx])

        if len(tags) == 0:  return None
        return tags

#공통
class NerModel_H:
    def __init__(self, model_name, preprocess):
        # BIO 태그 클래시별 레이블
        item =  {'O': 1, 'B_H_MAP': 2, 'B_H_HOBM': 3, 'B_H_EARCLE': 4, 'B_H_HEALTHBM': 5, 'B_H_SPORT': 6, 'B_H_CONINF': 7, 'B_H_GLOCOST': 8,
                 'B_H_KOCOOK': 9, 'B_H_GCOMA': 10, 'B_H_TRELSYS': 11, 'B_H_TRSYS': 12, 'B_H_GLOMIDI': 13, 'B_H_MIDIDESIG': 14, 'B_H_IT': 15, 'B_H_SOCIAL': 16,
                 'B_H_BEAUTY': 17, 'B_H_FIREMAN': 18, 'B_H_FOUNDATION': 19, 'B_H_SYNS': 20, 'B_H_INQUIRYF': 21, 'B_H_ATTENDREF': 22, 'B_H_ECAI': 23, 'B_H_GHOTELMA': 24,
                 'B_H_SCMSYS': 25, 'B_H_GAME': 26, 'B_H_PAULBO': 27, 'B_H_COOK': 28, 'B_H_AMENITIES': 29, 'B_H_GRADE': 30, 'B_H_CURRICULUM': 31, 'B_H_BM': 32, 'B_H_FTM': 33,
                 'B_H_ENDSTU': 34, 'B_H_GLOTRAIN': 35, 'B_H_TRAININD': 36, 'B_H_TRAINBM': 37, 'B_H_TRSOFT': 38, 'B_H_COM': 39, 'B_H_GLOCOOK': 40, 'B_H_COOKBM': 41,
                 'B_H_COOKNU': 42, 'B_H_CHILD': 43, 'B_H_EMERMEDI': 44, 'B_H_NURSE': 45, 'B_H_DOCPHY': 46, 'B_H_JWAI': 47, 'B_H_JWDATASC': 48, 'B_H_JWCOSC': 49,
                 'B_H_SCHOLARSHIP': 50, 'B_H_DORMITORY': 51, 'B_H_ADMISSION': 52, 'B_H_VILLAGE': 53, 'B_H_CHARA': 54, 'B_H_ACCEPTANCE': 55, 'B_H_GENERAL': 56,
                 'B_H_SCHOOLLIFE': 57, 'B_H_SOL': 58, 'B_H_BUILDENG': 59, 'B_H_THERAPI': 60}
        self.index_to_ner = {}
        for k, v in item.items():
            self.index_to_ner[v] = k

        # 의도 분류 모델 불러오기
        self.model = load_model(model_name)

        # 챗봇 Preprocess 객체
        self.p = preprocess

    # 개체명 클래스 예측
    def predict(self, query):
        # 형태소 분석
        pos = self.p.pos(query.upper())

        # 문장 내 키워드 추출(불용어 제거)
        keywords = self.p.get_keywords(pos, without_tag=True)
        sequences = [self.p.get_wordidx_sequence(keywords)]

        # 패딩 처리
        max_len = 40

        padded_seqs = utils.pad_sequences(sequences, padding="post", value=0, maxlen=max_len)

        # 키워드별 개체명 예측
        predict = self.model.predict(np.array([padded_seqs[0]]))
        predict_class = tf.math.argmax(predict, axis=-1)

        tags = [self.index_to_ner[i] for i in predict_class.numpy()[0]]
        return list(zip(keywords, tags))

    def predict_tags(self, query):
        # 형태소 분석
        pos = self.p.pos(query)

        # 문장 내 키워드 추출(불용어 제거)
        keywords = self.p.get_keywords(pos, without_tag=True)
        sequences = [self.p.get_wordidx_sequence(keywords)]

        # 패딩 처리
        max_len = 40
        padded_seqs = utils.pad_sequences(sequences, padding="post", value=0, maxlen=max_len)
        predict = self.model.predict(np.array([padded_seqs[0]]))
        score = np.max(predict)
        print(score)
        if score < 0.8:
            return None
        predict_class = tf.math.argmax(predict, axis=-1)

        tags = []
        for tag_idx in predict_class.numpy()[0]:
            if tag_idx == 1: continue
            tags.append(self.index_to_ner[tag_idx])

        if len(tags) == 0:  return None
        return tags