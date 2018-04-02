# Book_GOOS
[実践テスト駆動開発](https://amzn.to/2GJPwSJ)を読みながら書いたサンプルコード

## 環境構築

### java
[ORACLE](http://www.oracle.com/technetwork/java/javase/downloads/index.html)からダウンロード

### eclipse
[https://www.eclipse.org](https://www.eclipse.org)からダウンロード

### java home
echo $(/usr/libexec/java_home)
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_161.jdk/Contents/Home

### ant
brew ant install

### windowlicker
git clone git@github.com:petercoulton/windowlicker.git
cp ./src/core/main/com/objogate/wl/keyboard/Mac-US ./src/core/main/com/objogate/wl/keyboard/Mac-JP
./build.sh
./build/jars/配下にできたjar、`windowlicker-core-DEV.jar`と`windowlicker-swing-DEV.jar`を使う

### Openfire
git clone https://github.com/igniterealtime/Openfire.git
cd ./build
ant

`./target/openfire/bin/openfire.sh`で起動
adminでログインし、必要なユーザーを作る

### smack
git clone git@github.com:rtreffer/smack.git
cd ./build
ant

./target/配下にできたjar、`smack.jar`と`smackx.jar`を使う