import re

import pandas as pd
from konlpy.tag import Komoran


def read_text_data(filename):
    with open(filename, 'r') as f:
        data = [line for line in f.read().splitlines()]
        # data = [line for line in f.readlines()]
        # data = data[1:] #헤더 제거 헤더가 없다
    return data
def read_tag_data(filename):
    with open(filename, 'r', encoding='UTF8') as f:
        dic ={}
        for data in f.read().splitlines():
            temp = data.split('\t')
            dic[temp[0]] = temp[1]
    return dic

taging = read_tag_data("Ner_train/수시공통.txt")
corpus_data = read_text_data('C:\\inetpub\\wwwroot\\project\\models\\intent\\1S_corpus.txt') #본인 데이터셋
komoran = Komoran(userdic='../../utils/user_dic.tsv') #사용자정의 딕셔너리 폴더
f = open("../../models/ner/ner_train/Ner_train_susi.txt", "w", encoding='utf-8')

for c in corpus_data:
    pos = komoran.pos(c)
    firstline = "; {}\n".format(c)
    f.write(firstline)
    for word in pos:
        for k, v in taging.items():
            if k == word[0]:
                parse = re.sub(k, "", c)
                secondline = "$<{}:{}> {}\n".format(k, v, parse)
                f.write(secondline)
                idx = 1
                for tagdata in pos:
                    if tagdata[0] == k:
                        idxline = "{}\t{}\t{}\tB_{}\n".format(idx, tagdata[0], tagdata[1], v)
                        idx += 1
                        f.write(idxline)
                    else:
                        idxline = "{}\t{}\t{}\tO\n".format(idx, tagdata[0], tagdata[1])
                        idx += 1
                        f.write(idxline)
                f.write("\n")
                idx = 1
f.close()









'''text = "우리 w1 엔엘피를 좋아해."
pos = komoran.pos(text)
print(pos)'''