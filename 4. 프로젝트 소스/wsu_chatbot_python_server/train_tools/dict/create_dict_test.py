import os
from utils.Preprocess import Preprocess
from keras import utils
import pickle


# , encoding='UTF8'
def read_corpus_data(filename):
    with open(filename, 'r', encoding='utf-8') as f:
        data = [line.split('\t') for line in f.read().splitlines()]
        # data = [line for line in f.readlines()]
        # data = data[1:] #헤더 제거 헤더가 없다
    return data


corpus_data = open('C:\\inetpub\\wwwroot\\project\\test\\exam\\ALL_INTENT.txt', 'r', encoding='utf-8')

# 키워드 추출
p = Preprocess(word2index_dic='chatbot_dict.bin',
               userdic="C:\\inetpub\\wwwroot\\project\\utils\\user_dic.tsv")

f = open("./chatbot_dict.bin", "rb")
word_index = pickle.load(f)
f.close()
for line in corpus_data.read().splitlines():
    line = line.upper()
    pos = p.pos(line)
    print(pos)
    keywords = p.get_keywords(pos, without_tag=True)
    c_lower = []
    for i in keywords:
        c_lower.append(i.lower())
    for word in c_lower:
        try:
            if word_index[word] != 'NNG':
                print(word, word_index[word])

        except KeyError:
            # 해당 단어가 사전에 없는 경우 OOV 처리
            print(word, word_index['OOV'])
