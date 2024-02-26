# Fuzzy Logic
## Konu
Pil şarj cihazının doluluğu 
anlaması; voltaja ve pil sıcaklığına 
bağlıdır. 
##### Değişken1 : 
Voltaj(V) 
##### Değişken2: 
Pil Sıcaklığı(C°) 
##### Sonuç: 
Doluluk Durumu (%) 
LG ICR18650HB2 1500mAh lityum iyon pilinin çalışma aralıkları baz alınmıştır1. 
##### Voltaj(V) : 
[2.5V-4.2V] 
##### Pil Sıcaklığı(C°) Şarj Durumunda: 
[0C°-45C°] 
##### Doluluk Durumu (%):  
[0%-100%] 
 
## Dilsel değişkenlerin tanımlanması: 
##### Voltaj(V): 
Çok Düşük Voltaj, Düşük Voltaj, Normal Voltaj, Normal Üstü Voltaj, Yüksek Voltaj, Çok Yüksek Voltaj 
##### Pil Sıcaklığı(C°) Şarj Durumunda:
Düşük Sıcaklık, Normal Sıcaklık, Yüksek Sıcaklık 
##### Doluluk Durumu (%): 
Çok Az Dolu, Az Dolu, Orta Dolu, Orta Üstü Dolu, Dolu, Çok Dolu    
 
## Üyelik Fonksiyonları: 
##### Voltaj: 
Voltaj arttıkça doluluk artar fakat düşük voltajdan belli bir voltaja kadar olan artışta 
doluluk durumu fazla değişmez lityum iyon bataryalar için voltaj ve doluluk oranının grafikleri 
![Ekran görüntüsü 2023-11-09 014031](https://github.com/enesbt/FuzzyLogic-And-Neural-Network/assets/95939881/bf14b455-93fd-4aaf-99c9-695fe85ce253)
Kullandığım pil için 2.5V doluluğun 0% olduğu 4.2V 100% olduğu durumdur. Voltajı 6 dilsel 
değişkenle ifade ettim. Çok düşük voltaj için 2.5V-3.6V volt arası kabul ettim çünkü bu aralıklarda çoğu 
grafikte de göründüğü gibi doluluk oranı çok az olacak. Bundan sonrası için 0.1Vluk değişim bile 
doluluk oranını önemli ölçüde değiştireceği için aralıkları kısa tuttum. 0.1V artışlarla üyelik 
fonksiyonlarının sınır değerlerini belirledim.
![Ekran görüntüsü 2023-11-09 020805](https://github.com/enesbt/FuzzyLogic-And-Neural-Network/assets/95939881/83921f02-3678-4361-8635-b93088b80e8b)
##### Pil Sıcaklığı:
Sıcaklık için kullanmış olduğum pilin şarj durumunda çalışma sıcaklığı 0C°-45C° arasıdır. 
Bu aralıklar için normal sıcaklık olarak belirdim. Optimum sıcaklık değerini aşağıdaki değerlere göre 
belirledim. 
![Ekran görüntüsü 2023-11-09 021423](https://github.com/enesbt/FuzzyLogic-And-Neural-Network/assets/95939881/f46617f5-a5d6-46c7-8a51-ce33d4688029)

![Ekran görüntüsü 2023-11-09 022004](https://github.com/enesbt/FuzzyLogic-And-Neural-Network/assets/95939881/3a190519-05e8-452b-a969-89d02553554b)

##### Doluluk:
Doluluk değeri 0%-100% arası değişmektedir. 50% için orta doluda tam üyedir. Diğer 
değişkenler içinde 20% aralıklarla değişim yaparak sınır değerleri belirledim.

![Ekran görüntüsü 2023-11-09 023903](https://github.com/enesbt/FuzzyLogic-And-Neural-Network/assets/95939881/75c602c0-1aeb-436d-938f-96e05dd5a983)

##### Bulanık Kurallar: 
- Bulanık kuralları oluştururken voltaj değerini baz aldım. Voltaj arttıkça bir sonraki doluluk seviyesine 
- üyeliği artar. 
- Pil sıcaklığı normal aralık içerisinde doluluğu etkilemez. Normal aralık dışında doluluk oranını negatif 
yönde etkiler. 
- Voltaj arttıkça ve sıcaklık normalse doluluk oranını arttırdım fakat sıcaklık normal aralık dışında ise bir 
alt seviye doluluk oranına eşitledim.

## Ornek Cıktı
Voltaj V[2.5-4.2]:3,7 
Sicaklik C�[0-45]:5 
5 
(0.6666666666666667) 
if (voltaj IS normal) AND (sicaklik IS dusuk) 
then doluluk IS azDolu [weight: 1.0] 
6 
(0.3333333333333333) 
if (voltaj IS normal) AND (sicaklik IS normal) 
then doluluk IS ortaDolu [weight: 1.0] 
Doluluk Orani: 35.0% 
![37-5](https://github.com/enesbt/FuzzyLogic-And-Neural-Network/assets/95939881/a4c3b00f-1521-41c5-a9f1-7bd176017475)

