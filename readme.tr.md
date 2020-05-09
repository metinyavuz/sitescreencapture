
SITE CAPTURE:

Uygulama içerisinde ihtiyaçları ve kullanılan kütüphaneler aşağıda listelenmiştir.

* selenium-java: Websitesi test için kullanılan paket WebDriver ile ekran görüntüleri alabilmektedir.
	Bu uygulamada ChromeDriver kullanılmıştır.Nasıl kurulacağı farklı OS için ihtiyaçlar bölümünde anlatılımıştır.

* Active MQ: Message Queue olarak kullanılmıştır. Sadece notification implementasyon yapılıştır. Website object saklanması için planlanmıştır.

* Spring Async method yapısı kullanılmıştır. corePoolSize ve maxPoolSize istenilen ihitiyaçlara göre  app.properties de değişiklik yapılabilir

* File upload path istenilen koşulllara göre değiştirlebilir.

App.properties kullanımları.

	#Upload Path
	website.screen.upload.path=./upload/

	#Executor Poll Size
	websiteCaptureTaskExecutor.corePoolSize=4
	websiteCaptureTaskExecutor.maxPoolSize=4


İHTİYAÇLAR:

   * Java 8
   * ChromeDriver    
        
        Mac kullanıcıları Homebrew installed:  brew cask install chromedriver
        Debian tabanlı Linux distros: sudo apt-get install chromium-chromedriver
        Windows kullanılıcıları with Chocolatey installed: choco install chromedriver
        
        Ek Bilgi için:
            https://github.com/SeleniumHQ/selenium/wiki/ChromeDriver
            
        Mac OS Sorun:
            https://stackoverflow.com/questions/60362018/macos-catalinav-10-15-3-error-chromedriver-cannot-be-opened-because-the-de

KURULUM VE ÇALIŞTIRMA:

** ChromeDriver kurulmuş (Detaylar ihtiyaçlar bölümünde) ve java 8+ ile çalışıldığından emin olunması gerekiyor. (console üzerinden java -version)

sitecapture.zip dosyası istenilen bir folder a kopyalanır.            

unzip sitecapture.zip 

cd sitecapture

mvn clean package -Dmaven.test.skip=true

java -jar target/sitecapture-0.0.1-SNAPSHOT.jar


UYGULAMA TESTİ:

Test:
curl -X POST \
  http://localhost:8080/site/capture?urls='http://www.sanalmarket.com.tr,www.huriyet.com.tr,http://www.ntv.com.tr,http://www.hurriyet.com.tr,http://www.metinyavuz.net' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H 'Postman-Token: 4ec770bd-c15c-4ef5-862d-87647a9e8593' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  

Resim Gösterilmesi:
	Json içerisinde her url için cevap bilgileri içermektedir.
	State: 
		SUCCESS işlemin başarılı olduğunu
		FAIL işlermin başarısız olduğu note bölümünde de hata açıklaması.

	captureUrl:	Başarı işlemler için capture edilen ekran görünüsü erişim url.
		Test için:
			http://localhost:8080/<captureUrl>

Örnek Response JSON:

  [
    {
        "url": "http://www.sanalmarket.com.tr",
        "captureUrl": "/site/screenshot/1536586530652.png",
        "state": "SUCCESS",
        "note": null,
        "startTime": "2018-09-10T16:35:15.404",
        "duration": 15617
    },
    {
        "url": "www.huriyet.com.tr",
        "captureUrl": null,
        "state": "FAIL",
        "note": "org.openqa.selenium.chrome.ChromeDriverWeb drive exception contact with support",
        "startTime": "2018-09-10T16:35:15.404",
        "duration": 3756
    },
    {
        "url": "http://www.ntv.com.tr",
        "captureUrl": "/site/screenshot/1536586532734.png",
        "state": "SUCCESS",
        "note": null,
        "startTime": "2018-09-10T16:35:15.404",
        "duration": 17465
    },
    {
        "url": "http://www.hurriyet.com.tr",
        "captureUrl": "/site/screenshot/1536586536695.png",
        "state": "SUCCESS",
        "note": null,
        "startTime": "2018-09-10T16:35:15.404",
        "duration": 21444
    },
    {
        "url": "http://www.metinyavuz.net",
        "captureUrl": "/site/screenshot/1536586525888.png",
        "state": "SUCCESS",
        "note": null,
        "startTime": "2018-09-10T16:35:19.248",
        "duration": 6870
    }
]

            
 
