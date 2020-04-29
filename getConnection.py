import gensim
import requests
from gensim.models import KeyedVectors

EMBEDDING_FILE = 'GoogleNews-vectors-negative300.bin.gz'
#!wget -P /root/input/ -c "https://s3.amazonaws.com/dl4j-distribution/GoogleNews-vectors-negative300.bin.gz"

word2vec = KeyedVectors.load_word2vec_format(EMBEDDING_FILE, binary=True)


f = open("inputConnection.txt", "r")
f1 = f.readlines()
englishWord = f1[0].strip()
word = f1[1].strip()
data = word2vec.similarity(w1=str(englishWord),w2=str(word))
print(str(data))