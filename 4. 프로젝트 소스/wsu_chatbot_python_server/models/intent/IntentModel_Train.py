# 필요한 모듈 임포트
import pandas as pd
import tensorflow as tf
from keras import utils
from keras.models import Model
from keras.layers import Input, Embedding, Dense, Dropout, Conv1D, GlobalMaxPool1D, concatenate

import os
#os.environ["CUDA_VISIBLE_DEVICES"] = "2"

# 데이터 읽어오기
train_file = "intent_train_data.csv"
data = pd.read_csv(train_file, delimiter=',')
queries = data['query'].tolist()
intents = data['intent'].tolist()

from utils.Preprocess import Preprocess
p = Preprocess(word2index_dic='../../train_tools/dict/chatbot_dict.bin',
               userdic='../../utils/user_dic.tsv')


# 단어 시퀀스 생성
sequences = []
for sentence in queries:
    pos = p.pos(sentence.upper())
    keywords = p.get_keywords(pos, without_tag=True)
    c_lower = []
    for i in keywords:
        c_lower.append(i.lower())
    seq = p.get_wordidx_sequence(c_lower)
    sequences.append(seq)



# 단어 인덱스 시퀀스 벡터 ○2
# 단어 시퀀스 벡터 크기
from config.GlobalParams import MAX_SEQ_LEN # MAX_SEQ_LEN =15
padded_seqs = utils.pad_sequences(sequences, maxlen=MAX_SEQ_LEN, padding='post')
# 뒤에서부터 패딩처리하는 post-padding 와 앞에서부터 패딩처리하는 pre-padding 중 하나를 사용한다.
#padding은 post-padding보다는 pre-padding이 성능이 좋고 많이 쓰입니다.
#뒷단의 입력이 중요한 recurrent model 입장에서 뒤로 갈수록 피처가 희미해지는 long-dependency 현상을 심화시킬 수 있습니다.

# (105658, 15)
print(padded_seqs.shape)
print(len(intents)) #105658

# 학습용, 검증용, 테스트용 데이터셋 생성 ○3
# 학습셋:검증셋:테스트셋 = 7:2:2
ds = tf.data.Dataset.from_tensor_slices((padded_seqs, intents))
ds = ds.shuffle(len(queries))

train_size = int(len(padded_seqs) * 0.7)
val_size = int(len(padded_seqs) * 0.2)
test_size = int(len(padded_seqs) * 0.2)

train_ds = ds.take(train_size).batch(2) #
val_ds = ds.skip(train_size).take(val_size).batch(20)
test_ds = ds.skip(train_size + val_size).take(test_size).batch(20)

# 하이퍼 파라미터 설정
dropout_prob = 0.5 #뉴런이라 부르는 노드를 무작위로 껐다 켰다하는 행동을 dropout이라한다->overfitting과 접화를 방지
EMB_SIZE = 128 #임베딩 사이즈
EPOCH = 20 #훈련 횟수
VOCAB_SIZE = len(p.word_index) + 1 #전체 단어 개수


# CNN 모델 정의  ○4
input_layer = Input(shape=(MAX_SEQ_LEN,)) #MAX_SEQ_LEN 크기 만큼의 입력을 받는 입력층
embedding_layer = Embedding(VOCAB_SIZE, EMB_SIZE, input_length=MAX_SEQ_LEN)(input_layer)
dropout_emb = Dropout(rate=dropout_prob)(embedding_layer)

conv1 = Conv1D(
    filters=128,
    kernel_size=3,
    padding='valid',
    activation=tf.nn.relu)(dropout_emb)
pool1 = GlobalMaxPool1D()(conv1) #최대 풀링 연산

conv2 = Conv1D( #1차원의 합성곱
    filters=128,
    kernel_size=4,
    padding='valid',
    activation=tf.nn.relu)(dropout_emb)
pool2 = GlobalMaxPool1D()(conv2)

conv3 = Conv1D(
    filters=128,
    kernel_size=5,
    padding='valid',
    activation=tf.nn.relu)(dropout_emb)
pool3 = GlobalMaxPool1D()(conv3) #맥스 풀링은 각 합성곱 연산으로부터 얻은 결과 벡터에서 가장 큰 값을 가진 스칼라 값을 빼내는 연산입니다.

# 3,4,5gram 이후 합치기
concat = concatenate([pool1, pool2, pool3])

hidden = Dense(128, activation=tf.nn.relu)(concat) #은닉 층으로 학습
dropout_hidden = Dropout(rate=dropout_prob)(hidden)
logits = Dense(5, name='logits')(dropout_hidden)#logit함수는 logistic과 probit의 합성어이다. 그리고 sigmoid 함수와는 서로 역함수 관계이다.
predictions = Dense(19, activation=tf.nn.softmax)(logits)#softmax함수는 인공신경망에서 출력된 K개의 클래스 구분 결과를 확률처럼 해석하도록 만들어준다. 따라서 보통은 output 노두 바로 뒤에 부착된다.


# 모델 생성  ○5
model = Model(inputs=input_layer, outputs=predictions)
model.compile(optimizer='adam', #옵티마이저(Optimizer, 최적화) :손실 함수(Loss Function)의 결과값을 최소화하는 모델의 파라미터(가중치)를 찾는 것을 의미한다
              loss='sparse_categorical_crossentropy',# 다중 분류 손실함수 :라벨링 변수가 integer 일 때 쓰는 손실함수
              metrics=['accuracy'])


# 모델 학습 ○6
model.fit(train_ds, validation_data=val_ds, epochs=EPOCH, verbose=1)


# 모델 평가(테스트 데이터 셋 이용) ○7
loss, accuracy = model.evaluate(test_ds, verbose=1)
print('Accuracy: %f' % (accuracy * 100))
print('loss: %f' % (loss))


# 모델 저장  ○8
model.save('intent_model.h5')

