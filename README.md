# WeiqiTV - Watching Weiqi like on TV (α-Status)

Ubuntu Desktop, Firefox 19

![screenshot ubuntu](https://github.com/dgf/weiqitv-play/raw/master/screenshot.png)

GT-I9100, Android 4, Firefox 19

![screenshot android](https://github.com/dgf/weiqitv-play/raw/master/android.png)

## Requirements

* [play! 1.2.5](http://www.playframework.com/documentation/1.2.5/home)
* MySQL or a compatible RDBMS (relational database management system)

## run your own TV server

* clone the repo
* download and sync plugin dependencies: ```play deps --sync```
* create and configure a database, see *conf/application.conf*
* start the server with ```play run```

### create an admin user and initialize the database

* press the *power* button or open http://localhost:9000/login
  to login in with an arbitrary user account, "admin/foo" should do
* click on *DEV* at the bottom or open http://localhost:9000/admin/reset
  to initialize the database with two additional accounts and some test channels

### enjoy watching your own TV

open http://localhost:9000/ and have a good time
