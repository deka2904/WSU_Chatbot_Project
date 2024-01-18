import re

import pandas as pd
from konlpy.tag import Komoran


def read_text_data(filename):
    with open(filename, 'r', encoding='utf-8') as f:
        data = [line for line in f.read().splitlines()]
        # data = [line for line in f.readlines()]
        # data = data[1:] #헤더 제거 헤더가 없다
    return data

corpus_data = read_text_data('1H_intent.txt') #본인 데이터셋
file = open("C:\\inetpub\\wwwroot\\project\\models\\intent\\3H_corpus.txt", "w")

for cc in corpus_data:
    a = "{},3\n".format(cc)
    file.write(a)

    print(a)

file.close()