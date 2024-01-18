from pandas import np
from keras.models import Model, load_model
import pandas as pd
import tensorflow as tf
from keras import utils

intent_labels = {0: "인사", 1: "수시", 2: "정시", 3: "공통", 4: "기타"}


# 의도 분류 모델 불러오기
model = load_model('intent_model.h5')

from utils.Preprocess import Preprocess
p = Preprocess(word2index_dic='../../train_tools/dict/chatbot_dict.bin',
               userdic='../../utils/user_dic.tsv')


train_file = "test_intent.csv"
data = pd.read_csv(train_file, delimiter=',')
queries = data['query'].tolist()
intents = data['intent'].tolist()
out_file = open("결과.txt", 'w')
i = 0
for query in queries:
    query = query.upper()
    pos = p.pos(query)
    keywords = p.get_keywords(pos, without_tag=True)
    lower = []
    for temp in keywords:
        lower.append(temp.lower())

    seq = p.get_wordidx_sequence(lower)
    sequences = [seq]

    # 단어 시퀀스 벡터 크기
    from config.GlobalParams import MAX_SEQ_LEN

    padded_seqs = utils.pad_sequences(sequences, maxlen=MAX_SEQ_LEN, padding='post')

    predict = model.predict(padded_seqs)
    score = np.max(predict)
    predict_class = tf.math.argmax(predict, axis=1)
    out_file.writelines("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n")
    out_file.writelines(query+","+str(intents[i]) + "\n")
    temp = str.format("의도 예측 점수 :{} \n", predict)
    out_file.writelines(temp)
    # temp = str.format("의도 예측 점수 : {}\n", score)
    # out_file.writelines(temp)
    # temp = str.format("의도 예측 클래스 :{} \n", predict_class.numpy())
    # out_file.writelines(temp)
    temp = str.format("의도  : {}\n", intent_labels[predict_class.numpy()[0]])
    out_file.writelines(temp)
    i = i + 1
    out_file.writelines("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n")
out_file.close()



