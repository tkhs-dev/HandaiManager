# Handai Manager

> **Note**
> このソフトウェアは非公式のものであり、大阪大学は一切関与していません。
> また、ソフトウェアを利用する際、大阪大学の認証システムの認証情報を入力する必要があります。
> 大阪大学はこれらの認証情報を公式サービス以外に送信することを推奨しておらず、これらの情報を送信することによって生じたいかなる損害についても制作者は責任を負いません。
> これらの事項に同意できる方のみ、ご自身でコンパイルし、自己責任の下でご利用ください。

## このソフトウェアについて
このソフトウェアは、CLEやKOANなどの大阪大学のオンラインシステムを統合し、より使いやすくすることを目的として作成されました。
現在、以下の機能が実装されています。
- 大阪大学の認証システムを利用したログイン

## 対応環境
現在サポートされているデバイスは以下の通りです。
- Windows
- macOS
- Linux
- Android

## 利用方法
ソフトウェアはご自身でコンパイルし、自己責任の下でご利用ください。
### Windows/macOS/Linux
1. Clone the repo to your computer

```text
git clone https://github.com/tkhs-dev/HandaiManager
```

2. Execute the following commands to build the installer

```text
cd HandaiManager
./gradlew :desktop:packageDistributionForCurrentOS
```

desktopApp/build/compose/binaries/main 以下にインストーラーファイルが生成されます。

### Android
ToDo

## コミュニケーション
質問等は [Discussions](https://github.com/tkhs-dev/HandaiManager/discussions) にて受け付けています。
また、バグ報告は [Issues](https://github.com/tkhs-dev/HandaiManager/issues) にて受け付けています。

## Contributing
ToDo