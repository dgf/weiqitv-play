# WeiqiTV - Watching Weiqi like on TV (α-Status)

![screenshot](https://github.com/dgf/weiqitv-play/raw/master/screenshot.png)

## Requirements

* [play! 1.2.5](http://www.playframework.com/documentation/1.2.5/home)

## run your own TV server

* clone the repo
* download and sync plugin dependencies with ```play deps --sync```
* start it with ```play run``` and open http://localhost:9000/

### create an admin user and initialize your database

* press the *power* button or open http://localhost:9000/login
  to login in with an arbitrary user account, "admin/foo" should do
* click on *DEV* at the bottom or open http://localhost:9000/admin/reset
  to initialize the database with two additional accounts and some test channels

### start the gatherer and configure a game channel

* press the *start* button on the http://localhost:9000/admin page
* check a channel state in the CRUD interface http://localhost:9000/crud
  and start the *next game* on it with http://localhost:9000/next?number=<CHANNEL_ID>

### power of by IGS

```
Exception in thread "Thread-7" java.lang.RuntimeException: java.net.SocketException: Connection reset
        at gatherer.TelnetUtil.run(TelnetUtil.java:153)
Caused by: java.net.SocketException: Connection reset
        at java.net.SocketInputStream.read(SocketInputStream.java:168)
        at java.io.BufferedInputStream.fill(BufferedInputStream.java:218)
        at java.io.BufferedInputStream.read(BufferedInputStream.java:237)
        at java.io.FilterInputStream.read(FilterInputStream.java:66)
        at java.io.PushbackInputStream.read(PushbackInputStream.java:122)
        at org.apache.commons.net.io.FromNetASCIIInputStream.__read(FromNetASCIIInputStream.java:77)
        at org.apache.commons.net.io.FromNetASCIIInputStream.read(FromNetASCIIInputStream.java:176)
        at java.io.BufferedInputStream.fill(BufferedInputStream.java:218)
        at java.io.BufferedInputStream.read(BufferedInputStream.java:237)
        at org.apache.commons.net.telnet.TelnetInputStream.__read(TelnetInputStream.java:137)
        at org.apache.commons.net.telnet.TelnetInputStream.run(TelnetInputStream.java:580)
        at java.lang.Thread.run(Thread.java:662)
```
