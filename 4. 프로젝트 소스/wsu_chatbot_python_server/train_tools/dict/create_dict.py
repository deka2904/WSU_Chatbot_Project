import os
from utils.Preprocess import Preprocess
from keras import preprocessing
import pickle


# , encoding='UTF8'
def read_corpus_data(filename):
    with open(filename, 'r', encoding='UTF8') as f:
        data = [line.split('\t') for line in f.read().splitlines()]
        # data = [line for line in f.readlines()]
        # data = data[1:] #헤더 제거 헤더가 없다
    return data


# 말뭉치 가져오기 네이버 리뷰 또는 교재 예제로
corpus_data = read_corpus_data('corpus.txt')

# 키워드 추출
# p = Preprocess(word2index_dic='chatbot_dict.bin',userdic = '../../utils/user_dic.tsv')
p = Preprocess(userdic ='../../utils/user_dic.tsv')

dict = []
for c in corpus_data:
    # pos = p.pos(c[1].upper())  # 형태소 분석기에 c[1] 아이디를 제거한 문장만 넣기
    print(c[1])
    dict.append(c[1])  # 형태소 분석기에서 나온 리스트를 하나씩 빼와서 품사기호를 제거한 단어들만 사전에 추가

# 사전에 사용할 word2index 생성 임베딩
# 사전에 첫 번째 인덱스에는 OOV 사용
tokenizer = preprocessing.text.Tokenizer(split='/', oov_token='OOV', lower=True)  # 트레인 데이터에 없는 단어들은 그냥 넘어가게되는데
tokenizer.fit_on_texts(dict)
word_index = tokenizer.word_index

# 사전 파일 생성
f = open("chatbot_dict.bin", "wb")
try:
    pickle.dump(word_index, f)
except Exception as e:
    print(e)
finally:
    f.close()
