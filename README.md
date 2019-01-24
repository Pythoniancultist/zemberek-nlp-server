
# Zemberek Türkçe Doğal Dil İşleme Docker Mikroservis Sunucusu 

## Zemberek
[`Zemberek`](https://github.com/ahmetaa/zemberek-nlp) [`Zemberek Örnekler`](https://github.com/ahmetaa/turkish-nlp-examples). Projede mevcut olarak Zemberek'in 0.15.0 sürümü kullanılmaktadır.

## Mikroservis REST sunucusu - Spark
[`Spark`](http://sparkjava.com/) varsayılan olarak 4567 portları üzerinde çalışıyor.

## Kurulum
Dockerhub üzerindeki son sürümü kullanmak için;
``` 
docker pull cbilgili/zemberek-nlp-server:latest
docker run -p 4567:4567 cbilgili/zemberek-nlp-server:latest
``` 
Yerel geliştirme ortamında çalıştırmak için;
``` 
git clone https://github.com/cbilgili/zemberek-nlp-server.git
cd zemberek-nlp-server
mvn clean install
docker build -t zemberek-nlp-server .
docker run -p 4567:4567 zemberek-nlp-server
```
Bunun ardından http://localhost:4567 üzerinden endpointlere erişebilirsiniz.

## API Endpointleri
* [Cümle Sınırı Denetleme (Sentence Boundary Detection)](API.md) : `POST /sentence_boundary_detection`
* [Cümlenin Öğeleri Etiketleme (Part of Speech Tagging)](API.md) : `POST /find_pos`
* [Temel Cümle Öğelerine Ayırma (Simple Tokenization)](API.md) : `POST /simple_tokenization`
* [Cümle Öğelerine Ayırma (Token Iterator)](API.md) : `POST /token_iterator`
* [Yazım Kontrolü (Spelling Check)](API.md) : `POST /spelling_check`
* [Yazım Önerimi (Spelling Suggestions)](API.md) : `POST /spelling_suggestions`
* [Kelime Kökleri (Stemming)](API.md) : `POST /stems`
* [Kelime Kökleri (Lemmas)](API.md) : `POST /lemmas`
* [Kelime Analizi (Analyze Word)](API.md) : `POST /analyze_word`
* [Cümle Analizi (Analyze Sentence)](API.md) : `POST /analyze_sentence`
* [Kelime Oluşturma (Generate Word)](API.md) : `POST /generate_word`

