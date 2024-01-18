import tensorflow as tf
from keras.models import Model, load_model
from keras import utils
from config.GlobalParams import MAX_SEQ_LEN
import numpy as np

# 의도 분류 모델 함수
class IntentModel:
    def __init__(self, model_name, preprocess):
        # 의도 클래스 교과서
        self.labels = {0: "인사", 1: "수시", 2: "정시", 3: "공통", 4: "기타"}

        # 의도 분류 모델 불러오기
        self.model = load_model(model_name)

        # 챗봇 Preprocess 객체
        self.p = preprocess

    def predict_class(self, query):
        # 형태소 분석 호출
        pos = self.p.pos(query.upper())

        # 문장내에 키워드 필터링
        keywords = self.p.get_keywords(pos, without_tag=True) # 태그없이
        sequences = [self.p.get_wordidx_sequence(keywords)] # 단어 사전 인덱스  시퀀스 얻어오기

        from config.GlobalParams import MAX_SEQ_LEN
        # 단어시퀀스 백터 크기 정하기 왜냐 문장별로 길이가 달라 백터길이가 달라져 최대길이이하는 0으로 패딩 하기위해
        padded_seqs = utils.pad_sequences(sequences, maxlen=MAX_SEQ_LEN, padding='post') #나머지 0

        predict = self.model.predict(padded_seqs)
        score = np.max(predict)
        predict_class = tf.math.argmax(predict, axis=1) # 의도 분류 결과값 0~3
        print(predict_class.numpy()[0])
        if predict_class.numpy()[0] == 0:
            return predict_class.numpy()[0]
        elif score < 0.8: #의도분류 값이 0.8미만일 경우 의도번호 4번인 기타로 분류를 한다.
            predict_class = 4
            return predict_class

        return predict_class.numpy()[0]

