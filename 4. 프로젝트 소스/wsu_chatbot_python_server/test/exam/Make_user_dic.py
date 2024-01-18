import re
#-*-coding: uft-8 -*-

import pandas as pd
from konlpy.tag import Komoran


def read_text_data(filename):
    with open(filename, 'r') as f:
        data = [line for line in f.read().splitlines()]
        # data = [line for line in f.readlines()]
        # data = data[1:] #헤더 제거 헤더가 없다
        print(data)
    return data

corpus_data = read_text_data('C:\\inetpub\\wwwroot\\project\\train_tools\\dict\\chatbot_dict_txtTEST.txt') #본인 데이터셋
file = open("../../utils/user_dic.tsv", "w")

for cc in corpus_data:
    a = "{}\tNNG\n".format(cc)
   
    file.write(a)
    
print("완료")
file.close()