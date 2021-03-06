# RxErrorHandler
[ ![Jcenter](https://img.shields.io/badge/Jcenter-v2.1.1-brightgreen.svg?style=flat-square) ](https://bintray.com/jessyancoding/maven/rxerrorhandler/2.1.1/link)
[ ![Build Status](https://travis-ci.org/JessYanCoding/RxErrorHandler.svg?branch=2.x) ](https://travis-ci.org/JessYanCoding/RxErrorHandler)
[ ![API](https://img.shields.io/badge/API-9%2B-blue.svg?style=flat-square) ](https://developer.android.com/about/versions/android-2.3.html)
[ ![License](http://img.shields.io/badge/License-Apache%202.0-blue.svg?style=flat-square) ](http://www.apache.org/licenses/LICENSE-2.0)
[ ![Author](https://img.shields.io/badge/Author-JessYan-orange.svg?style=flat-square) ](https://www.jianshu.com/u/1d0c0bc634db)
[ ![QQ-Group](https://img.shields.io/badge/QQ%E7%BE%A4-455850365%20%7C%20301733278-orange.svg?style=flat-square) ](https://shang.qq.com/wpa/qunwpa?idkey=7e59e59145e6c7c68932ace10f52790636451f01d1ecadb6a652b1df234df753)

## Error Handle Of Rxjava

## Download

``` gradle
implementation 'me.jessyan:rxerrorhandler:2.1.1' //rxjava2

implementation 'me.jessyan:rxerrorhandler:1.0.1' //rxjava1
```

## Initialization

``` java
  RxErrorHandler rxErrorHandler = RxErrorHandler 
                .builder()
                .with(this)
                .responseErrorListener(new ResponseErrorListener() {
                    @Override
                    public void handleResponseError(Context context, Throwable t) {
                        if (t instanceof UnknownHostException) {
                            //do something ...
                        } else if (t instanceof SocketTimeoutException) {
                            //do something ...
                        } else {
                            //handle other Exception ...
                        }
                        Log.w(TAG, "Error handle");
                    }
                }).build();
```

## Usage

``` java
  Observable
            .error(new Exception("Error"))
            .retryWhen(new RetryWithDelay(3, 2))//retry(http connect timeout) 
            .subscribe(new ErrorHandleSubscriber<Object>(rxErrorHandler) {
                    @Override
                    public void onNext(Object o) {

                    }

                });

  //Backpressure
  Flowable
          .error(new Exception("Error"))
          .retryWhen(new RetryWithDelayOfFlowable(3, 2))//retry(http connect timeout)
          .subscribe(new ErrorHandleSubscriberOfFlowable<Object>(rxErrorHandler) {
                   @Override
               public void onNext(Object o) {

                  }
               });
```


## About Me
* **Email**: <jess.yan.effort@gmail.com>  
* **Home**: <http://jessyan.me>
* **掘金**: <https://juejin.im/user/57a9dbd9165abd0061714613>
* **简书**: <https://www.jianshu.com/u/1d0c0bc634db>  

## License
```
 Copyright 2016, jessyan               
  
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at   

       http://www.apache.org/licenses/LICENSE-2.0  

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License. 
```
